import getCN from 'classnames';
import React from 'react';
import PropTypes from 'prop-types';
import {DragDropContext as dragDropContext} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';
import CriteriaSidebar from '../criteria_sidebar/CriteriaSidebar.es';
import {sub} from '../../utils/utils.es';
import CriteriaBuilder from './CriteriaBuilder.es';
import ClayButton from '../shared/ClayButton.es';
import {buildQueryString, translateQueryToCriteria} from '../../utils/odata.es';
import Conjunction from './Conjunction.es';
import ClaySelect from '../shared/ClaySelect.es';

/**
 *
 *
 * @param {string} propertyKey
 * @param {Array} propertiesArray
 * @return {*}
 */
function createContributor(propertyKey, propertiesArray = []) {
	return {
		criteriaMap: null,
		query: '',
		inputId: 'exxample', // TODO inputId generator
		propertyKey: propertyKey,
		properties: propertiesArray,
	};
}

/**
 *
 * @typedef Criteria
 * @property {string} inputId
 * @property {string} initialQuery
 * @property {array} properties
 * @property {string} conjunctionId
 * @property {string} modelLabel
 */

/**
 *
 * @typedef CriteriaMultiBuilderProps
 * @property {Array<Criteria>} initialContributors
 * @property {*} supportedConjunctions // TODO to be typed and documented
 * @property {*} supportedOperators // TODO to be typed and documented
 * @property {*} supportedPropertyTypes // TODO to be typed and documented
 */

/**
 *
 * @typedef CriteriaMultiBuilderState
 * @property {number} editing
 */


/**
 *
 *
 * @class CriteriaMultiBuilderComp
 * @extends {React.Component<CriteriaMultiBuilderProps, CriteriaMultiBuilderState>}
 * @property {string} classes
 * @property {CriteriaMultiBuilderState} state
 */
class ContributorsBuilderComp extends React.Component {
	/**
	 *Creates an instance of CriteriaMultiBuilderComp.
	 * @param {CriteriaMultiBuilderProps} props
	 * @memberof CriteriaMultiBuilderComp
	 */
	constructor(props) {
		super(props);
		this.classes = getCN(
			'criteria-builder-root',
			{
				'read-only': false,
			}
		);
		this.onCriteriaEdit = this.onCriteriaEdit.bind(this);
		this.onCriteriaChange = this.onCriteriaChange.bind(this);
		this.state = {
			contributors: this.props.initialContributors.map(c => {
				const propertyGroup = this.props.propertyGroups.find(t => c.propertyKey === t.propertyKey);

				return {
					criteriaMap: c.initialQuery ?
						translateQueryToCriteria(c.initialQuery) :
						null,
					query: c.initialQuery,
					inputId: c.inputId,
					propertyKey: c.propertyKey,
					properties: propertyGroup && propertyGroup.properties,
				};
			}),
			editing: undefined,
			conjunctionName: 'and',
			newPropertyKey: this.props.propertyGroups.length &&
				this.props.propertyGroups[0].propertyKey,
		};
		this._handleRootConjunctionClick = this._handleRootConjunctionClick.bind(this);
		this._createNewContributor = this._createNewContributor.bind(this);
		this._handleSelectorChange = this._handleSelectorChange.bind(this);
	}

	/**
	 *
	 *
	 * @param {number} id
	 * @param {boolean} editing
	 * @memberof CriteriaMultiBuilderComp
	 */
	onCriteriaEdit(id, editing) {
		this.setState({
			editing: editing ? undefined : id,
		});
	}

	/**
	 *
	 *
	 * @param {*} e
	 * @memberof CriteriaMultiBuilderComp
	 */
	_handleSelectorChange(e) {
		const newPropertyKey = e.target.value;

		this.setState(prevState => ({
			...prevState,
			newPropertyKey,
		}));
	}

	/**
	 *
	 *
	 * @param {*} criteriaChange
	 * @param {*} index
	 * @memberof CriteriaMultiBuilderComp
	 */
	onCriteriaChange(criteriaChange, index) {
		this.setState(state => {
			if (state.editing !== index) return state;

			return {
				contributors: state.contributors.map((c, i) => {
					if (index === i) {
						return {
							...c,
							criteriaMap: criteriaChange,
							query: buildQueryString([criteriaChange]),
						};
					}

					return c;
				}),
			};
		});
	}

	/**
	 *
	 * @param {Event} event
	 * @memberof CriteriaMultiBuilderComp
	 */
	_handleRootConjunctionClick(event) {
		event.preventDefault();

		this.setState((prevState) => {
			const {supportedConjunctions} = this.props;
			const conjunctionName = prevState.conjunctionName;
			const index = supportedConjunctions.findIndex(
				item => item.name === conjunctionName
			);
			const conjunctionSelected =
			(index === supportedConjunctions.length - 1) ?
				supportedConjunctions[0].name :
				supportedConjunctions[index + 1].name;

			return {
				...prevState,
				conjunctionName: conjunctionSelected,
			};
		});
	}

	/**
	 *
	 *
	 * @memberof CriteriaMultiBuilderComp
	 */
	_createNewContributor() {
		this.setState((prevState, props) => {
			const propertyGroup = props.propertyGroups
				.find(t => prevState.newPropertyKey === t.propertyKey);
			const contributors = [
				...prevState.contributors,
				createContributor(
					prevState.newPropertyKey,
					propertyGroup && propertyGroup.properties,
				),
			];

			return {
				...prevState,
				contributors,
			};
		});
	}

	/**
	 *
	 *
	 * @param {*} property
	 * @param {*} id
	 * @memberof ContributorsBuilderComp
	 */
	_onPropertySelection(property, id) {
		this.setState((prevState) => {
			const prevContributors = prevState.contributors;
			const prevContributor = prevContributors[id];
			const propertyGroup = this.props.propertyGroups
				.find(t => prevContributor.propertyKey === t.propertyKey);
			const updatedContributor = createContributor(
				property,
				propertyGroup && propertyGroup.properties
			);

			return {
				...prevState,
				contributors: prevContributors.map((p, i) => {
					if (i === id) return updatedContributor;

					return p;
				}),
			};
		});
	}
	/**
	 *
	 *
	 * @return {Node}
	 * @memberof CriteriaMultiBuilderComp
	 */
	render() {
		const {
			supportedConjunctions,
			supportedOperators,
			supportedPropertyTypes,
			propertyGroups,
		} = this.props;
		const currentEditing = this.state.editing;
		const selectedContributor = this.state.contributors[currentEditing];
		const selectedProperty = selectedContributor && propertyGroups.find(c => selectedContributor.propertyKey === c.propertyKey);

		return (
			<div className={this.classes}>
				<div className="criteria-builder-section-main">
					{
						this.state.contributors.map((criteria, i) => {
							return (
								<React.Fragment key={i}>
									<div className={`sheet-lg`}>
										{
											(i !== 0) &&
											<Conjunction
												className={`ml-0`}
												conjunctionName={this.state.conjunctionName}
												editing={true}
												supportedConjunctions={supportedConjunctions}
												_handleCriterionAdd={() => this._handleCriterionAdd}
												_handleConjunctionClick={this._handleRootConjunctionClick}
											/>
										}
									</div>
									<CriteriaBuilder
										initialQuery={criteria.query}
										criteria={criteria.criteriaMap}
										inputId={criteria.inputId}
										modelLabel={criteria.modelLabel}
										supportedProperties={criteria.properties}
										supportedConjunctions={supportedConjunctions}
										supportedOperators={supportedOperators}
										supportedPropertyTypes={supportedPropertyTypes}
										onEditionToggle={this.onCriteriaEdit}
										onChange={this.onCriteriaChange}
										editing={currentEditing === i}
										id={i}
										onPropertyGroupSelection={this._onPropertySelection.bind(this)}
										supportedPropertyGroups={this.props.propertyGroups.map(p => ({value: p.propertyKey, label: p.name}))}
										propertyKey={criteria.propertyKey}
									/>
									<div className="form-group">
										<input
											className="field form-control"
											data-testid="query-input"
											id={criteria.inputId}
											name={criteria.inputId}
											type="hidden"
											readOnly
											value={criteria.query}
										/>
									</div>
								</React.Fragment>
							);
						})
					}
					<div className={`sheet-lg`}>
						{
							this.state.contributors &&
							this.state.contributors.map((c, i) => {
								if (i !== 0 && c.query !== '') return ` ${this.state.conjunctionName} ` + c.query;

								return c.query;
							})
						}
						<ClaySelect
							className={`mt-4 mw15`}
							options={this.props.propertyGroups.map(type => ({
								label: type.name,
								value: type.propertyKey,
							}))}
							selected={this.state.newPropertyKey}
							onChange={this._handleSelectorChange}
						></ClaySelect>
						<ClayButton style='primary' className="mt-4" onClick={this._createNewContributor}>Add More Filters</ClayButton>
					</div>
				</div>
				<div className="criteria-builder-section-sidebar">
					{<CriteriaSidebar
						supportedProperties={selectedProperty && selectedProperty.properties}
						title={sub(
							Liferay.Language.get('x-properties'),
							[selectedProperty && selectedProperty.modelLabel]
						)}
						propertyKey={selectedProperty && selectedProperty.propertyKey}
					/>}
				</div>
			</div>
		);
	}
}

const property = PropTypes.shape({
	name: PropTypes.string.isRequired,
	label: PropTypes.string.isRequired,
	type: PropTypes.string.isRequired,
});
const propertyGroup = PropTypes.shape({
	name: PropTypes.string.isRequired,
	propertyKey: PropTypes.string.isRequired,
	properties: PropTypes.arrayOf(property),
});
const initialContributor = PropTypes.shape({
	inputId: PropTypes.string.isRequired,
	initialQuery: PropTypes.string.isRequired,
	conjunctionId: PropTypes.string.isRequired,
	conjunctionInputId: PropTypes.string.isRequired,
	propertyKey: PropTypes.string.isRequired,
});

const propertyTypes = PropTypes.shape({
	boolean: PropTypes.arrayOf(PropTypes.string).isRequired,
	date: PropTypes.arrayOf(PropTypes.string).isRequired,
	number: PropTypes.arrayOf(PropTypes.string).isRequired,
	string: PropTypes.arrayOf(PropTypes.string).isRequired,
});

const operators = PropTypes.shape({
	label: PropTypes.string.isRequired,
	name: PropTypes.string.isRequired,
});

const conjuctions = PropTypes.shape({
	label: PropTypes.string.isRequired,
	name: PropTypes.string.isRequired,
});


ContributorsBuilderComp.propTypes = {
	initialContributors: PropTypes.arrayOf(initialContributor),
	propertyGroups: PropTypes.arrayOf(propertyGroup),
	supportedConjunctions: PropTypes.arrayOf(conjuctions).isRequired,
	supportedOperators: PropTypes.arrayOf(operators).isRequired,
	supportedPropertyTypes: propertyTypes.isRequired,
};

export default dragDropContext(HTML5Backend)(ContributorsBuilderComp);
