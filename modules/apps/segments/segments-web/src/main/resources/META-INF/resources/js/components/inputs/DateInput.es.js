import propTypes from 'prop-types';
import React from 'react';
import {jsDatetoYYYYMMDD} from '../../utils/utils.es';
import {PROPERTY_TYPES} from 'utils/constants.es';

class DateInput extends React.Component {
	static propTypes = {
		onChange: propTypes.func.isRequired,
		value: propTypes.string
	};

	state = {
		value: this.props.value
	}

	_handleDateChange = event => {
		const value = event.target.value ||
			jsDatetoYYYYMMDD(new Date());

		if (value !== 'Invalid Date') {
			this.setState(
				{
					value
				}
			);
		}
	}

	_handleDateBlur = event => {
		const date = new Date(event.target.value);

		const domStringDate = jsDatetoYYYYMMDD(date);

		if (domStringDate !== 'Invalid Date') {
			this.setState(
				{
					value: domStringDate
				}, () => {
					this.props.onChange(
						{
							type: PROPERTY_TYPES.DATE,
							value: domStringDate
						}
					);
				}
			);
		}
		else {
			this.setState(
				{
					value: jsDatetoYYYYMMDD(new Date())
				}, () => {
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