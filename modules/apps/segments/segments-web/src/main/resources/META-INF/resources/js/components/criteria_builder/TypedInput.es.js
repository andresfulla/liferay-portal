import React from 'react';
import propTypes from 'prop-types';
import ClayButton from '../shared/ClayButton.es';
import ClaySelect from '../shared/ClaySelect.es';
import getCN from 'classnames';

class TypedInput extends React.Component {
	_handleChange = event => {
		const {onChange} = this.props;
		const value = event.target.value;

		if (onChange) {
			onChange(value);
		}
	}

	_handleSelectEntity = () => {
		const {id, title, url} = this.props.selectEntity;

		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					destroyOnHide: true,
					modal: true
				},
				id,
				title,
				uri: url
			},
			event => {
				console.log('event', event);

				// @TODO Update value of input value
			}
		);
	}

	render() {
		const {options, selectEntity, value} = this.props;

		const classnames = getCN(
			'criterion-input',
			'form-control'
		);

		let inputRender = (
			<input
				className={classnames}
				onChange={this._handleChange}
				type="text"
				value={value}
			/>
		);

		if (options.length) {
			inputRender = (
				<ClaySelect
					className={classnames}
					onChange={this._handleChange}
					options={options.map(
						o => ({
							label: o.label,
							value: o.value
						})
					)}
					selected={value}
				/>
			);
		}
		else if (selectEntity) {
			inputRender = (
				<div className="input-group select-entity-input">
					<div className="input-group-item input-group-prepend">
						<input type="hidden" value={value} />

						<input className="form-control" readOnly value={value} />
					</div>

					<span className="input-group-append input-group-item input-group-item-shrink">
						<ClayButton
							label={Liferay.Language.get('select')}
							onClick={this._handleSelectEntity}
						/>
					</span>
				</div>
			);
		}

		return inputRender;
	}
}

TypedInput.propTypes = {
	onChange: propTypes.func,
	options: propTypes.array,
	selectEntity: propTypes.shape(
		{
			id: propTypes.string,
			title: propTypes.string,
			url: propTypes.string
		}
	),
	type: propTypes.string.isRequired,
	value: propTypes.string
};

TypedInput.defaultProps = {
	options: []
};

export default TypedInput;