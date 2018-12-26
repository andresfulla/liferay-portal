import React from 'react';
import ClayButton from '../shared/ClayButton.es';

/**
 *
 *
 * @export
 * @class Conjunction
 * @extends {React.Component}
 */
export default class Conjunction extends React.Component {
	/**
	 *
	 *
	 * @param {*} conjunctionName
	 * @param {*} conjunctions
	 * @return {*}
	 * @memberof Conjunction
	 */
	_getConjunctionLabel(conjunctionName, conjunctions) {
		const conjunction = conjunctions.find(
			({name}) => name === conjunctionName
		);

		return conjunction ? conjunction.label : undefined;
	}
	/**
	 *
	 *
	 * @memberof Conjunction
	 * @return {Node}
	 */
	render() {
		const {
			conjunctionName,
			editing,
			supportedConjunctions,
			className,
		} = this.props;

		return (<React.Fragment>
			{editing ?
				<ClayButton
					className={`btn-sm conjunction-button ${className}`}
					label={this._getConjunctionLabel(
						conjunctionName,
						supportedConjunctions
					)}
					onClick={this.props._handleConjunctionClick}
				/> :
				<div className="conjunction-label">
					{this._getConjunctionLabel(
						conjunctionName,
						supportedConjunctions
					)}
				</div>
			}
		</React.Fragment>);
	}
}
