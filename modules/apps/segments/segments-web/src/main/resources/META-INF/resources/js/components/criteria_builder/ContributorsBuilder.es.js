import getCN from 'classnames';
import React from 'react';
import PropTypes from 'prop-types';
import {DragDropContext as dragDropContext} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';
import CriteriaSidebar from '../criteria_sidebar/CriteriaSidebar.es';
import {sub} from '../../utils/utils.es';
import CriteriaBuilder from './CriteriaBuilder.es';
import {buildQueryString, translateQueryToCriteria} from '../../utils/odata.es';
import Conjunction from './Conjunction.es';

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
		inputId: 'exxample',

		// TODO inputId generator

		properties: propertiesArray,
		propertyKey,
		query: ''
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
	constructor(props) {
		super(props);
		this.classes = getCN(
			'criteria-builder-root',
			{
				'read-only': false
			}
		);
		const {initialContributors} = props;
		const contributors = initialContributors && initialContributors.map(
			c => {
				const propertyGroup = this.props.propertyGroups.find(t => c.propertyKey === t.propertyKey);

				return {
					criteriaMap: c.initialQuery ?
						translateQueryToCriteria(c.initialQuery) :
						null,
					inputId: c.inputId,
					properties: propertyGroup && propertyGroup.properties,
					propertyKey: c.propertyKey,
					query: c.initialQuery
				};
			}
		);
		this.onCriteriaEdit = this.onCriteriaEdit.bind(this);
		this.onCriteriaChange = this.onCriteriaChange.bind(this);
		this.state = {
			conjunctionName: 'and',
			contributors,
			editing: undefined,
			newPropertyKey: this.props.propertyGroups.length &&
				this.props.propertyGroups[0].propertyKey
		};
		this._handleRootConjunctionClick = this._handleRootConjunctionClick.bind(this);
		this._createNewContributor = this._createNewContributor.bind(this);
		this._handleSelectorChange = this._handleSelectorChange.bind(this);
		this._onPropertySelection = this._onPropertySelection.bind(this);
	}

	onCriteriaEdit(id, editing) {
		this.setState(
			{
				editing: editing ? undefined : id
			}
		);
	}

	_handleSelectorChange(e) {
		const newPropertyKey = e.target.value;

		this.setState(
			prevState => (
				{
					...prevState,
					newPropertyKey
				}
			)
		);
	}

	onCriteriaChange(criteriaChange, index) {
		this.setState(
			state => {
				let newState;
				if (state.editing !== index) {
					newState = state;
				}
				else {
					newState = {
						contributors: state.contributors.map(
							(c, i) => {
								return (index === i) ? {
									...c,
									criteriaMap: criteriaChange,
									query: buildQueryString([criteriaChange])
								} : c;
							}
						)
					};
				}
				return newState;
			}
		);
	}

	_handleRootConjunctionClick(event) {
		event.preventDefault();

		this.setState(
			(prevState, prevProps) => {
				const {supportedConjunctions} = prevProps;
				const {conjunctionName} = prevState;
				const conjunctionIndex = supportedConjunctions.findIndex(
					item => item.name === conjunctionName
				);
				const conjunctionSelected = (conjunctionIndex === supportedConjunctions.length - 1) ?
					supportedConjunctions[0].name :
					supportedConjunctions[conjunctionIndex + 1].name;

				return {
					...prevState,
					conjunctionName: conjunctionSelected
				};
			}
		);
	}

	/**
	 *
	 *
	 * @memberof CriteriaMultiBuilderComp
	 */
	_createNewContributor() {
		this.setState(
			(prevState, prevProps) => {
				const propertyGroup = prevProps.propertyGroups
					.find(t => prevState.newPropertyKey === t.propertyKey);

				const contributors = [
					...prevState.contributors,
					createContributor(
						prevState.newPropertyKey,
						propertyGroup && propertyGroup.properties
					)
				];

				return {
					...prevState,
					contributors
				};
			}
		);
	}

	/**
	 *
	 *
	 * @param {*} property
	 * @param {*} id
	 * @memberof ContributorsBuilderComp
	 */
	_onPropertySelection(property, id) {
		this.setState(
			prevState => {
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
					contributors: prevContributors.map(
						(p, i) => {
							return (i === id) ? updatedContributor : p;
						}
					)
				};
			}
		);
	}

	render() {
		const {
			propertyGroups,
			supportedConjunctions,
			supportedOperators,
			supportedPropertyTypes
		} = this.props;
		const currentEditing = this.state.editing;
		const selectedContributor = this.state.contributors[currentEditing];
		const selectedProperty = selectedContributor && propertyGroups.find(c => selectedContributor.propertyKey === c.propertyKey);

		return (
			<div className={this.classes}>
				<div className="criteria-builder-section-main">
					{
						this.state.contributors.map(
							(criteria, i) => {
								return (
									<React.Fragment key={i}>
										<div className={'sheet-lg'}>
											{
												(i !== 0) &&
												<Conjunction
													className={'ml-0'}
													conjunctionName={this.state.conjunctionName}
													editing={true}
													handleConjunctionClick={this._handleRootConjunctionClick}
													supportedConjunctions={supportedConjunctions}
												/>
											}
										</div>
										<CriteriaBuilder
											criteria={criteria.criteriaMap}
											editing={currentEditing === i}
											id={i}
											initialQuery={criteria.query}
											inputId={criteria.inputId}
											modelLabel={criteria.modelLabel}
											onChange={this.onCriteriaChange}
											onEditionToggle={this.onCriteriaEdit}
											onPropertyGroupSelection={this._onPropertySelection}
											propertyKey={criteria.propertyKey}
											supportedConjunctions={supportedConjunctions}
											supportedOperators={supportedOperators}
											supportedProperties={criteria.properties}
											supportedPropertyGroups={this.props.propertyGroups.map(
												p => ({
													label: p.name,
													value: p.propertyKey
												})
											)}
											supportedPropertyTypes={supportedPropertyTypes}
										/>
										<div className="form-group">
											<input
												className="field form-control"
												data-testid="query-input"
												id={criteria.inputId}
												name={criteria.inputId}
												readOnly
												type="hidden"
												value={criteria.query}
											/>
										</div>
									</React.Fragment>
								);
							}
						)
					}
					<div className={'sheet-lg'}>
						{
							this.state.contributors &&
							this.state.contributors.map(
								(c, i) => {
									return (i !== 0 && c.query !== '') ? ` ${this.state.conjunctionName} ${c.query}` : c.query;
								}
							)
						}
					</div>
				</div>
				<div className="criteria-builder-section-sidebar">
					{
						<CriteriaSidebar
							propertyKey={selectedProperty && selectedProperty.propertyKey}
							supportedProperties={selectedProperty && selectedProperty.properties}
							title={
								sub(
									Liferay.Language.get('x-properties'),
									[selectedProperty && selectedProperty.name]
								)
							}
						/>
					}
				</div>
			</div>
		);
	}
}

const conjuctions = PropTypes.shape(
	{
		label: PropTypes.string.isRequired,
		name: PropTypes.string.isRequired
	}
);
const initialContributor = PropTypes.shape(
	{
		conjunctionId: PropTypes.string.isRequired,
		conjunctionInputId: PropTypes.string.isRequired,
		initialQuery: PropTypes.string.isRequired,
		inputId: PropTypes.string.isRequired,
		propertyKey: PropTypes.string.isRequired
	}
);
const operators = PropTypes.shape(
	{
		label: PropTypes.string.isRequired,
		name: PropTypes.string.isRequired
	}
);
const property = PropTypes.shape(
	{
		label: PropTypes.string.isRequired,
		name: PropTypes.string.isRequired,
		type: PropTypes.string.isRequired
	}
);
const propertyGroup = PropTypes.shape(
	{
		name: PropTypes.string.isRequired,
		properties: PropTypes.arrayOf(property),
		propertyKey: PropTypes.string.isRequired
	}
);
const propertyTypes = PropTypes.shape(
	{
		boolean: PropTypes.arrayOf(PropTypes.string).isRequired,
		date: PropTypes.arrayOf(PropTypes.string).isRequired,
		number: PropTypes.arrayOf(PropTypes.string).isRequired,
		string: PropTypes.arrayOf(PropTypes.string).isRequired
	}
);

ContributorsBuilderComp.propTypes = {
	initialContributors: PropTypes.arrayOf(initialContributor),
	propertyGroups: PropTypes.arrayOf(propertyGroup),
	supportedConjunctions: PropTypes.arrayOf(conjuctions).isRequired,
	supportedOperators: PropTypes.arrayOf(operators).isRequired,
	supportedPropertyTypes: propertyTypes.isRequired
};

export default dragDropContext(HTML5Backend)(ContributorsBuilderComp);