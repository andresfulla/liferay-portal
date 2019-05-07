import dateFns from 'date-fns';
import propTypes from 'prop-types';
import React from 'react';
import {PROPERTY_TYPES} from 'utils/constants.es';

const INPUT_DATE_FORMAT = 'YYYY-MM-DD';

class DateTimeInput extends React.Component {
	static propTypes = {
		onChange: propTypes.func.isRequired,
		value: propTypes.string
	};

	state = {
		value: dateFns.format(this.props.value, INPUT_DATE_FORMAT)
	}

	_handleDateChange = event => {
		const value = event.target.value ||
			dateFns.format(new Date(), INPUT_DATE_FORMAT);

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

		const domStringDate = dateFns.format(date, INPUT_DATE_FORMAT);

		if (domStringDate !== 'Invalid Date') {
			this.setState(
				{
					value: domStringDate
				}, () => {
					this.props.onChange(
						{
							type: PROPERTY_TYPES.DATE,
							value: dateFns.parse(domStringDate, INPUT_DATE_FORMAT).toISOString()
						}
					);
				}
			);
		}
		else {
			this.setState(
				{
					value: dateFns.format(new Date(), INPUT_DATE_FORMAT)
				}, () => {
					this.props.onChange(
						{
							type: PROPERTY_TYPES.DATE,
							value: dateFns.parse(new Date(), INPUT_DATE_FORMAT).toISOString()
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

export default DateTimeInput;