import React from 'react';
import PropTypes from 'prop-types';
import ClayButton from '../shared/ClayButton.es';
import getCN from 'classnames';

class Conjunction extends React.Component {
	_getConjunctionLabel(conjunctionName, conjunctions) {
		const conjunction = conjunctions.find(
			({name}) => name === conjunctionName
		);

		return conjunction ? conjunction.label : undefined;
	}
	render() {
		const {
			className,
			conjunctionName,
			editing,
			supportedConjunctions
		} = this.props;

		const classnames = getCN(
			{
				'btn-sm conjunction-button': editing,
				'conjunction-label': !editing
			},
			className
		);

		return (
			editing ?
				<ClayButton
					className={classnames}
					label={this._getConjunctionLabel(
						conjunctionName,
						supportedConjunctions
					)}
					onClick={this.props.handleConjunctionClick}
				/> :
				<div className={classnames}>
					{this._getConjunctionLabel(
						conjunctionName,
						supportedConjunctions
					)}
				</div>
		);
	}
}

Conjunction.propTypes = {
	className: PropTypes.string,
	conjunctionName: PropTypes.string.isRequired,
	editing: PropTypes.bool.isRequired,
	handleConjunctionClick: PropTypes.func,
	supportedConjunctions: PropTypes.array.isRequired
};

export default Conjunction;