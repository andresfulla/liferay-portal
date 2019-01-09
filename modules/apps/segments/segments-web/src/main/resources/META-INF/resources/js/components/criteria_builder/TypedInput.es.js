import React from 'react';
import propTypes from 'prop-types';
import ClaySelect from '../shared/ClaySelect.es';

class TypedInput extends React.Component {
	constructor(props) {
		super(props);
		this._onChange = this._onChange.bind(this);
	}
	_onChange(event) {
		const {onChange} = this.props;
		const value = event.target.value;

		if (onChange) {
			onChange(value);
		}
	}
	render() {
		const {value, options, className} = this.props;
		const isOption = options.length;

		return (
			<div className={className}>
				{
					(isOption === 0) ?
						<input
							className="criterion-input form-control"
							onChange={this._onChange}
							type="text"
							value={value}
						/> :
						<ClaySelect
							selected={value}
							options={options.map(o => ({
								label: o.label,
								value: o.value
							}))}
							onChange={this._onChange}
						/>
				}
			</div>
		);
	}
};

TypedInput.propTypes = {
	className: propTypes.string,
	onChange: propTypes.func,
	options: propTypes.array,
	type: propTypes.string.isRequired,
	value: propTypes.string
};

TypedInput.defaultProps = {
	options: []
};

export default TypedInput;