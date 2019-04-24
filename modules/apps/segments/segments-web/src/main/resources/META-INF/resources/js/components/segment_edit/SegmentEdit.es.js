import ClayButton from '../shared/ClayButton.es';
import ClayToggle from '../shared/ClayToggle.es';
import ContributorBuilder from '../criteria_builder/ContributorBuilder.es';
import LocalizedInput from '../title_editor/LocalizedInput.es';
import PropTypes from 'prop-types';
import React, {Component} from 'react';
import ThemeContext from '../../ThemeContext.es';
import {FieldArray, withFormik} from 'formik';
import {
	SOURCES,
	SUPPORTED_CONJUNCTIONS,
	SUPPORTED_OPERATORS,
	SUPPORTED_PROPERTY_TYPES
} from '../../utils/constants.es';

const DEFAULT_SEGMENT_NAME = Liferay.Language.get('unnamed-segment');

class SegmentEdit extends Component {
	static contextType = ThemeContext;

	static propTypes = {
		availableLocales: PropTypes.object,
		contributors: PropTypes.arrayOf(
			PropTypes.shape(
				{
					conjunctionId: PropTypes.string,
					conjunctionInputId: PropTypes.string,
					initialQuery: PropTypes.string,
					inputId: PropTypes.string,
					propertyKey: PropTypes.string
				}
			)
		),
		errors: PropTypes.object,
		defaultLanguageId: PropTypes.string,
		formId: PropTypes.string,
		handleBlur: PropTypes.func,
		handleChange: PropTypes.func,
		initialMembersCount: PropTypes.number,
		initialSegmentActive: PropTypes.bool,
		initialSegmentName: PropTypes.object,
		locale: PropTypes.string.isRequired,
		portletNamespace: PropTypes.string,
		previewMembersURL: PropTypes.string,
		propertyGroups: PropTypes.array,
		redirect: PropTypes.string.isRequired,
		requestMembersCountURL: PropTypes.string,
		setValues: PropTypes.func,
		showInEditMode: PropTypes.bool,
		source: PropTypes.string,
		validateForm: PropTypes.func,
		values: PropTypes.object
	};

	static defaultProps = {
		contributors: [],
		initialSegmentActive: true,
		portletNamespace: ''
	};

	constructor(props) {
		super(props);

		this.state = {
			disabledSave: this._isQueryEmpty(),
			editing: this.props.showInEditMode
		};
	}

	_handleCriteriaEdit = () => {
		this.setState(
			{
				editing: !this.state.editing
			}
		);
	}

	_handleQueryChange = () => {
		this.setState(
			{
				disabledSave: this._isQueryEmpty()
			},
		);
	};

	_handleSegmentNameBlur = event => {
		const {
			handleBlur
		} = this.props;

		handleBlur(event);
	};

	_handleSourceIconMouseOver = event => {
		const message = this.props.source === SOURCES.ASAH_FARO_BACKEND.name ?
			SOURCES.ASAH_FARO_BACKEND.label :
			SOURCES.DEFAULT.label;

		Liferay.Portal.ToolTip.show(event.currentTarget, message);
	};

	/**
	 * Checks if every query in each contributor has a value.
	 * @return {boolean} True if none of the contributor's queries have a value.
	 */
	_isQueryEmpty = () => this.props.contributors.every(
		({initialQuery, inputId}) => {
			const input = document.getElementById(inputId);

			return input ? !input.value : !initialQuery;
		}
	);

	_renderContributors = () => {
		const {contributors, formId, initialMembersCount, previewMembersURL, propertyGroups, requestMembersCountURL, values} = this.props;

		const {editing} = this.state;

		const emptyContributors = this._isQueryEmpty();

		return (
			(propertyGroups && contributors) ?
				<ContributorBuilder
					editing={editing}
					emptyContributors={emptyContributors}
					formId={formId}
					initialContributors={contributors}
					membersCount={initialMembersCount}
					onQueryChange={this._handleQueryChange}
					previewMembersURL={previewMembersURL}
					propertyGroups={propertyGroups}
					requestMembersCountURL={requestMembersCountURL}
					supportedConjunctions={SUPPORTED_CONJUNCTIONS}
					supportedOperators={SUPPORTED_OPERATORS}
					supportedPropertyTypes={SUPPORTED_PROPERTY_TYPES}
					values={values}
				/> :
				null
		);
	};

	/**
	 * Validates fields with validation and prevents the default form submission
	 * if there are any errors.
	 *
	 * Form submission is defined by the action attribute on the <form> element
	 * outside of this component. Since we are leveraging the <aui:form> taglib
	 * to handle submission and formik to handle value changes and validation,
	 * this method uses formik to validate and prevents the taglib form action
	 * from being called.
	 * @param {Class} event Event to prevent a form submission from occurring.
	 */
	_handleValidate = event => {
		const {validateForm} = this.props;

		event.persist();

		validateForm().then(
			errors => {
				const errorMessages = Object.values(errors);

				if (errorMessages.length) {
					event.preventDefault();

					errorMessages.forEach(
						message => {
							Liferay.Util.openToast(
								{
									message,
									title: Liferay.Language.get('error'),
									type: 'danger'
								}
							);
						}
					);
				}
			}
		);
	};

	render() {
		const {
			availableLocales,
			defaultLanguageId,
			handleChange,
			locale,
			portletNamespace,
			redirect,
			source,
			values
		} = this.props;

		const {disabledSave, editing} = this.state;

		const {assetsPath} = this.context;

		return (
			<div className="segment-edit-page-root">
				<input
					name={`${portletNamespace}active`}
					type="hidden"
					value={values.active}
				/>

				<div className="form-header">
					<div className="container-fluid container-fluid-max-xl form-header-container">
						<div className="form-header-section-left">
							<FieldArray
								name="values.name"
								render={
									() => {
										const langs = Object.entries(values.name);
										return (
											langs.map(([key, value]) => {
												if (key === locale) {
													return (
														<React.Fragment key={key}>
															<input
																name={`${portletNamespace}name_${key}`}
																readOnly
																type="hidden"
																value={value}
															/>
															<input
																name={`${portletNamespace}key`}
																readOnly
																type="hidden"
																value={values.name}
															/>
															<input
																name={`${portletNamespace}name`}
																readOnly
																type="hidden"
																value={value}
															/>
														</React.Fragment>
													);
												}
												return (
													<React.Fragment key={key}>
														<input
															name={`${portletNamespace}name_${key}`}
															readOnly
															type="hidden"
															value={value}
														/>
													</React.Fragment>
												);
											})
										);
									}
								}
							/>

							<LocalizedInput
								availableLanguages={availableLocales}
								defaultLang={defaultLanguageId}
								initialLang={defaultLanguageId}
								initialOpen={false}
								initialValues={values.name}
								onChange={(event, newValues) => {
									this.props.setFieldValue('name', newValues);
								}}
								portletNamespace={portletNamespace}
								readOnly={!editing}
							/>

							<img
								className="source-icon"
								data-testid="source-icon"
								onMouseOver={this._handleSourceIconMouseOver}
								src={source === SOURCES.ASAH_FARO_BACKEND.name ?
									`${assetsPath}${SOURCES.ASAH_FARO_BACKEND.icon}` :
									`${assetsPath}${SOURCES.DEFAULT.icon}`
								}
							/>
						</div>

						<div className="form-header-section-right">
							<div className="btn-group">
								<div className="btn-group-item mr-2">
									<ClayToggle
										checked={editing}
										className="toggle-editing"
										iconOff="pencil"
										iconOn="pencil"
										onChange={this._handleCriteriaEdit}
									/>
								</div>
							</div>

							<div className="btn-group">
								<div className="btn-group-item">
									<ClayButton
										className="text-capitalize"
										href={redirect}
										label={Liferay.Language.get('cancel')}
										size="sm"
									/>
								</div>

								<div className="btn-group-item">
									<ClayButton
										className="text-capitalize"
										disabled={disabledSave}
										label={Liferay.Language.get('save')}
										onClick={this._handleValidate}
										size="sm"
										style="primary"
										type="submit"
									/>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div className="form-body">
					<FieldArray
						name="contributors"
						render={this._renderContributors}
					/>
				</div>
			</div>
		);
	}
}

export default withFormik(
	{
		mapPropsToValues: props => (
			{
				active: props.initialSegmentActive || true,
				contributors: props.contributors || [],
				name: props.initialSegmentName || {}
			}
		),
		validate: values => {
			const errors = {};

			if (!values.name) {
				errors.name = Liferay.Language.get('segment-name-is-required');
			}

			return errors;
		}
	}
)(SegmentEdit);