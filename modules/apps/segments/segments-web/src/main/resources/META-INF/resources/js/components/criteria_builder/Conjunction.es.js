import React from 'react';
import PropTypes from 'prop-types';
import ClayButton from '../shared/ClayButton.es';

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

		return (<React.Fragment>
			{editing ?
				<ClayButton
					className={`btn-sm conjunction-button ${className}`}
					label={this._getConjunctionLabel(
						conjunctionName,
						supportedConjunctions
					)}
					onClick={this.props.handleConjunctionClick}
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

Conjunction.propTypes = {
	className: PropTypes.string,
	conjunctionName: PropTypes.string.isRequired,
	editing: PropTypes.bool.isRequired,
	handleConjunctionClick: PropTypes.func,
	supportedConjunctions: PropTypes.array.isRequired
};

export default Conjunction;