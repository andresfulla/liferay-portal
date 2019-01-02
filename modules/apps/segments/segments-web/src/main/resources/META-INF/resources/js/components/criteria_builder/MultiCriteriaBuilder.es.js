import getCN from 'classnames';
import React from 'react';
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
 * @property {Array<Criteria>} criterias
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
class CriteriaMultiBuilderComp extends React.Component {
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
			contributors: this.props.criterias.map(c => {
				return {
					criteriaMap: c.initialQuery && c.initialQuery !== '()' ?
						translateQueryToCriteria(c.initialQuery) :
						null,
					query: c.initialQuery,
					inputId: c.inputId,
					properties: c.properties,
					modelLabel: c.modelLabel,
					propertyKey: c.propertyKey,
				};
			}),
			editing: undefined,
			conjunctionName: 'and',
			newContributor: this.props.contributorsTypes[0],
		};
		this._handleRootConjunctionClick = this._handleRootConjunctionClick.bind(this);
		this._createNewContributor = this._createNewContributor.bind(this);
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
	 *
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
			const conjunctionSelected = index === supportedConjunctions.length - 1 ?
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
		const propertyType = this.state.newContributor;

		this.setState((prevState) => {
			const contributors = [
				...prevState.contributors,
				{
					criteriaMap: null,
					query: '',
					inputId: 'exxample',
					modelLabel: propertyType.name,
					properties: propertyType.properties,
				},
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
	 * @return {Node}
	 * @memberof CriteriaMultiBuilderComp
	 */
	render() {
		const {
			supportedConjunctions,
			supportedOperators,
			supportedPropertyTypes,
		} = this.props;
		const currentEditing = this.state.editing;
		const selectedCriteria = this.state.contributors[currentEditing];

		return (
			<div className={this.classes}>
				<div className="criteria-builder-section-main">
					{
						this.state.contributors.map((criteria, i) => {
							return (
								<React.Fragment key={i}>
									{
										(i !== 0) &&
										<Conjunction
											conjunctionName={this.state.conjunctionName}
											editing={true}
											supportedConjunctions={supportedConjunctions}
											_handleCriterionAdd={() => this._handleCriterionAdd}
											_handleConjunctionClick={this._handleRootConjunctionClick}
										/>
									}
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
										propertyKey={criteria.propertyKey}
									/>
									{criteria.propertyKey}
								</React.Fragment>
							);
						})
					}
					{
						this.state.contributors &&
						this.state.contributors.map((c, i) => {
							if (i !== 0 && c.query !== '') return ` ${this.state.conjunctionName} ` + c.query;

							return c.query;
						})
					}
					<ClaySelect
						className={`mt-4 mw10`}
						options={this.props.contributorsTypes.map(type => ({
							label: type.name,
							value: type.propertyKey,
						}))}
						selected={selectedCriteria && selectedCriteria.propertyKey}
						onChange={() => {}}
					></ClaySelect>
					<ClayButton style='primary' className="mt-4" onClick={this._createNewContributor}>Add More Filters</ClayButton>
				</div>
				<div className="criteria-builder-section-sidebar">
					{<CriteriaSidebar
						supportedProperties={selectedCriteria && selectedCriteria.properties}
						title={sub(
							Liferay.Language.get('x-properties'),
							[selectedCriteria && selectedCriteria.modelLabel]
						)}
						propertyKey={selectedCriteria && selectedCriteria.propertyKey}
					/>}
				</div>
			</div>
		);
	}
}

export default dragDropContext(HTML5Backend)(CriteriaMultiBuilderComp);
