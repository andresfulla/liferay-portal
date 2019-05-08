import propTypes from 'prop-types';
import React from 'react';
import {jsDatetoYYYYMMDD} from '../../utils/utils.es';
import {PROPERTY_TYPES} from 'utils/constants.es';

class DateInput extends React.Component {
	static propTypes = {
		onChange: propTypes.func.isRequired,
		value: propTypes.string
	};

	constructor(props) {
		super(props);

		const date = new Date(props.value);

		this.state = {
			value: jsDatetoYYYYMMDD(date)
		};
	}

	shouldComponentUpdate(nextProps, nextState) {
		if (this.props.value != nextProps.value) {
			this.setState({value: nextProps.value});
		}

		return this.props.value == nextProps.value;
	}

	_handleDateChange = event => {
		const value = event.target.value ||
			jsDatetoYYYYMMDD(new Date());

		this.setState({value});
	}

	_handleDateBlur = event => {
		const date = jsDatetoYYYYMMDD(event.target.value);

		if (date !== 'Invalid Date') {
			this.setState(
				{value: date},
				() => {
					this.props.onChange(
						{
							type: PROPERTY_TYPES.DATE,
							value: date
						}
					);
				}
			);
		}
		else {
			this.setState(
				{value: jsDatetoYYYYMMDD(new Date())},
				() => {
					this.props.onChange(
						{
							type: PROPERTY_TYPES.DATE,
							value: jsDatetoYYYYMMDD(new Date())
						}
					);
				}
			);
		}
	}

	render() {
		const {value} = this.state;

		return (
			<div className="criterion-input date-input">
				<input
					className="form-control"
					data-testid="date-input"
					onBlur={this._handleDateBlur}
					onChange={this._handleDateChange}
					type="date"
					value={value}
				/>
			</div>
		);
	}
}

export default DateInput;