import React from 'react';
import propTypes from 'prop-types';
import ClaySelect from '../shared/ClaySelect.es';
import getCN from 'classnames';

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
		const {options, value} = this.props;

		const classnames = getCN(
			'criterion-input',
			'form-control'
		);

		return (options.length === 0) ?
			<input
				className={classnames}
				onChange={this._onChange}
				type="text"
				value={value}
			/> :
			<ClaySelect
				className={classnames}
				onChange={this._onChange}
				options={options.map(
					o => ({
						label: o.label,
						value: o.value
					})
				)}
				selected={value}
			/>;
	}
}

TypedInput.propTypes = {
	onChange: propTypes.func,
	options: propTypes.array,
	type: propTypes.string.isRequired,
	value: propTypes.string
};

TypedInput.defaultProps = {
	options: []
};

export default TypedInput;