import getCN from 'classnames';
import React from 'react';
import {DragDropContext as dragDropContext} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';
import CriteriaSidebar from '../criteria_sidebar/CriteriaSidebar.es';
import {sub} from '../../utils/utils.es';
import CriteriaBuilder from './CriteriaBuilder.es';
import ClayButton from '../shared/ClayButton.es';
import {buildQueryString, translateQueryToCriteria} from '../../utils/odata.es';
import Conjunction from './conjunction.es';

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
		this.state = {
			editing: undefined,
		};
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
				};
			}),
		};
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
			return {
				contributors: state.contributors.map((c, i) => {
					if (index === i) {
						const newState = {
							criteriaMap: criteriaChange,
							query: buildQueryString([criteriaChange]),
						};

						return newState;
					}

					return c;
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
		} = this.props;
		const currentEditting = this.state.editing;
		const selectedCriteria = this.props.criterias[currentEditting];
		const criteriaProps = this.props.criterias;

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
											conjunctionName={'AND'}
											editing={true}
											groupId={'groupId'}
											onMove={'onMove'}
											supportedConjunctions={supportedConjunctions}
											index={'index'}
											_getConjunctionLabel={() => 'AND'}
											_handleCriterionAdd={this._handleCriterionAdd}
											_handleConjunctionClick={this._handleConjunctionClick}

										/>
									}
									<CriteriaBuilder
										initialQuery={criteria.query}
										criteria={criteria.criteriaMap}
										inputId={criteriaProps[i].inputId}
										modelLabel={criteriaProps[i].modelLabel}
										supportedProperties={criteriaProps[i].properties}
										supportedConjunctions={supportedConjunctions}
										supportedOperators={supportedOperators}
										supportedPropertyTypes={supportedPropertyTypes}
										onEditionToggle={this.onCriteriaEdit}
										onChange={this.onCriteriaChange}
										editing={currentEditting === i}
										id={i}
									/>
								</React.Fragment>
							);
						})
					}
					{/* TODO this button should be ghost */}
					<ClayButton style='primary' className="mt-4">Add More Filters</ClayButton>
				</div>
				<div className="criteria-builder-section-sidebar">
					{selectedCriteria && <CriteriaSidebar
						supportedProperties={selectedCriteria.properties}
						title={sub(
							Liferay.Language.get('x-properties'),
							[selectedCriteria.modelLabel]
						)}
					/>}
				</div>
			</div>
		);
	}
}

export default dragDropContext(HTML5Backend)(CriteriaMultiBuilderComp);
