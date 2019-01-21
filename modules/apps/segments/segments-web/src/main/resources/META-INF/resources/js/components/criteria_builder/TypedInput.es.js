import React from 'react';
import propTypes from 'prop-types';
import ClayButton from '../shared/ClayButton.es';
import ClaySelect from '../shared/ClaySelect.es';
import dateFns from 'date-fns';
import {BOOLEAN_OPTIONS, PROPERTY_TYPES} from '../../utils/constants.es.js';

const INPUT_DATE_FORMAT = 'YYYY-MM-DD';

class TypedInput extends React.Component {
	static propTypes = {
		onChange: propTypes.func.isRequired,
		options: propTypes.array,
		selectEntity: propTypes.shape(
			{
				id: propTypes.string,
				title: propTypes.string,
				uri: propTypes.string
			}
		),
		type: propTypes.string.isRequired,
		value: propTypes.oneOfType(
			[
				propTypes.string,
				propTypes.number
			]
		)
	};

	static defaultProps = {
		options: []
	};

	constructor(props) {
		super(props);

		const {value} = props;

		this.state = {
			decimal: value
		};
	}

	_getInputRenderer(type) {
		const renderers = {
			[PROPERTY_TYPES.BOOLEAN]: this._renderBooleanType,
			[PROPERTY_TYPES.DATE]: this._renderDateType,
			[PROPERTY_TYPES.DOUBLE]: this._renderDecimalType,
			[PROPERTY_TYPES.ID]: this._renderIdType,
			[PROPERTY_TYPES.INTEGER]: this._renderIntegerType,
			[PROPERTY_TYPES.STRING]: this._renderStringType
		};

		return renderers[type] || renderers[PROPERTY_TYPES.STRING];
	}

	_handleChange = event => {
		const {onChange} = this.props;

		const value = event.target.value;

		onChange(value);
	}

	_handleDateChange = event => {
		const {onChange} = this.props;

		const value = event.target.value ||
			dateFns.format(new Date(), INPUT_DATE_FORMAT);

		const iSOStringValue = dateFns
			.parse(value, INPUT_DATE_FORMAT)
			.toISOString();

		onChange(iSOStringValue, PROPERTY_TYPES.DATE);
	}

	_handleDecimalBlur = event => {
		const {onChange} = this.props;

		const value = Number.parseFloat(event.target.value).toFixed(2);

		this.setState(
			{
				decimal: value
			},
			onChange(value)
		);
	}

	_handleDecimalChange = event => {
		const value = event.target.value;

		this.setState(
			{
				decimal: value
			}
		);
	}

	_handleIntegerChange = event => {
		const {onChange} = this.props;
		const value = parseInt(event.target.value, 10);

		if (!isNaN(value)) {
			onChange(value.toString());
		}
	}

	_handleSelectEntity = () => {
		const {
			onChange,
			selectEntity: {id, title, uri}
		} = this.props;

		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					destroyOnHide: true,
					modal: true
				},
				id,
				title,
				uri
			},
			event => {
				onChange(event.entityid);
			}
		);
	}

	_renderBooleanType = () => {
		const {value} = this.props;

		return (
			<ClaySelect
				className="criterion-input form-control"
				data-testid="options-boolean"
				onChange={this._handleChange}
				options={BOOLEAN_OPTIONS}
				selected={value}
			/>);
	}

	_renderDateType = () => {
		const {value} = this.props;

		const date = new Date(value);

		const domStringDate = dateFns.format(date, INPUT_DATE_FORMAT);

		return (
			<div className="criterion-input date-input">
				<input
					className="form-control"
					data-testid="date-input"
					onChange={this._handleDateChange}
					type="date"
					value={domStringDate}
				/>
			</div>
		);
	}

	_renderDecimalType = () => {
		const {decimal} = this.state;

		return (
			<input
				className="criterion-input form-control"
				data-testid="decimal-number"
				onBlur={this._handleDecimalBlur}
				onChange={this._handleDecimalChange}
				step="0.01"
				type="number"
				value={decimal}
			/>
		);
	}

	_renderIntegerType = () => {
		const {value} = this.props;

		return (
			<input
				className="criterion-input form-control"
				data-testid="integer-number"
				onChange={this._handleIntegerChange}
				type="number"
				value={value}
			/>);
	}

	_renderIdType = () => {
		const {value} = this.props;

		return (
			<div className="criterion-input input-group select-entity-input">
				<div className="input-group-item input-group-prepend">
					<input type="hidden" value={value} />

					<input
						className="form-control"
						data-testid="entity-select-input"
						readOnly
						value={value}
					/>
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

	_renderStringType = () => {
		const {options, value} = this.props;

		return (options.length === 0) ?
			<input
				className="criterion-input form-control"
				data-testid="simple-string"
				onChange={this._handleChange}
				type="text"
				value={value}
			/> :
			<ClaySelect
				className="criterion-input form-control"
				data-testid="options-string"
				onChange={this._handleChange}
				options={options.map(
					o => ({
						label: o.label,
						value: o.value
					})
				)}
				selected={value}
			/>;
	}

	render() {
		const {type} = this.props;

		const inputRenderer = this._getInputRenderer(type);

		return inputRenderer();
	}
}

export default TypedInput;