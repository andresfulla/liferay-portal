import React from 'react';
import propTypes from 'prop-types';

class DecimalInput extends React.Component {
	static propTypes = {
		onChange: propTypes.func.isRequired,
		value: propTypes.oneOfType(
			[
				propTypes.string,
				propTypes.number
			]
		)
	};

	constructor(props) {
		super(props);

		this.state = {
			decimal: props.value
		};
	}

	_handleDecimalBlur = event => {
		const value = Number.parseFloat(event.target.value).toFixed(2);

		this.setState(
			{
				decimal: value
			},
			this.props.onChange(value)
		);
	}

	_handleDecimalChange = event => {
		this.setState(
			{
				decimal: event.target.value
			}
		);
	}

	render() {
		return (
			<input
				className="criterion-input form-control"
				data-testid="decimal-number"
				onBlur={this._handleDecimalBlur}
				onChange={this._handleDecimalChange}
				step="0.01"
				type="number"
				value={this.state.decimal}
			/>
		);
	}
}

export default DecimalInput;