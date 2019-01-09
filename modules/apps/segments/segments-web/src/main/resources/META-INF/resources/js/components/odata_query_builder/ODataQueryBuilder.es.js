import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CriteriaBuilder from '../criteria_builder/CriteriaBuilder.es';
import {buildQueryString, translateQueryToCriteria} from '../../utils/odata.es';
import {
	SUPPORTED_CONJUNCTIONS,
	SUPPORTED_OPERATORS,
	SUPPORTED_PROPERTY_TYPES,
} from '../../utils/constants.es';
import '../../libs/odata-parser.js';

/**
 *
 *
 * @class ODataQueryBuilder
 * @extends {Component}
 */
class ODataQueryBuilder extends Component {
	static propTypes = {
		initialQuery: PropTypes.string,
		inputId: PropTypes.string,
		modelLabel: PropTypes.string,
		properties: PropTypes.array.isRequired
	};

	/**
	 *Creates an instance of ODataQueryBuilder.
	 * @param {*} props
	 * @memberof ODataQueryBuilder
	 */
	constructor(props) {
		super(props);

		const {initialQuery} = props;

		this.state = {
			criteriaMap: initialQuery && initialQuery !== '()' ?
				translateQueryToCriteria(initialQuery) :
				null,
			query: initialQuery,
		};
		this._handleChange = this._handleChange.bind(this);
	}

	/**
	 *
	 *
	 * @param {*} newCriteriaMap
	 * @memberof ODataQueryBuilder
	 */
	_handleChange(newCriteriaMap) {
		this.setState(
			{
				criteriaMap: newCriteriaMap,
				query: buildQueryString([newCriteriaMap]),
			}
		);
	}

	/**
	 *
	 *
	 * @return {Node}
	 * @memberof ODataQueryBuilder
	 */
	render() {
		const {inputId, modelLabel, properties} = this.props;

		const {criteriaMap} = this.state;

		return (
			<div className="clay-query-builder-root">
				<div className="form-group">
					<CriteriaBuilder
						criteria={criteriaMap}
						modelLabel={modelLabel}
						onChange={this._handleChange}
						supportedConjunctions={SUPPORTED_CONJUNCTIONS}
						supportedOperators={SUPPORTED_OPERATORS}
						supportedProperties={properties}
						supportedPropertyTypes={SUPPORTED_PROPERTY_TYPES}
					/>
				</div>

				<div className="form-group">
					<input
						className="field form-control"
						data-testid="query-input"
						id={inputId}
						name={inputId}
						type="hidden"
						value={criteriaMap ? buildQueryString([criteriaMap]) : ''}
					/>
				</div>
			</div>
		);
	}
}

export default ODataQueryBuilder;
