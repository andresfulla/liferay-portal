import getCN from 'classnames';
import LocalizedDropdown from './LocalizedDropdown.es';
import PropTypes from 'prop-types';
import React from 'react';

export default class LocalizedInput extends React.Component {
	static propsTypes = {
		availableLanguages: PropTypes.object,
		initialLang: PropTypes.string,
		initialOpen: PropTypes.bool,
		initialValues: PropTypes.object,
		onChange: PropTypes.func,
		readOnly: PropTypes.bool
	}
	static defaultProps = {
		availableLanguages: {},
		initialOpen: false,
		onChange: () => {},
		readOnly: false
	}

	state = {
		availableLanguages: Object.entries(this.props.availableLanguages).map(
			([key, value]) => {
				return {
					hasValue: !!this.props.initialValues[key],
					key,
					value
				};
			}
		),
		currentLang: this.props.initialLang,
		currentValue: this.props.initialValues[this.props.initialLang] || '',
		values: this.props.initialValues
	}

	_handleLanguageChange = langKey => {
		this.setState(
			prevState => {
				return {
					currentLang: langKey,
					currentValue: prevState.values[langKey] || ''
				};
			}
		);
	}

	_onChange = (event, hasError) => {
		this.props.onChange(
			event,
			this.state.values,
			hasError
		);
	}

	_handleInputChange = event => {
		event.persist();

		let hasError = false;
		
		this.setState(
			prevState => {
				const newValues = {
					...prevState.values,
					[prevState.currentLang]: event.target.value
				};

				hasError = !this._validateValues(newValues);

				return {
					availableLanguages: prevState.availableLanguages.map(
						lang => {
							let newLang = lang;
							if (lang.key === prevState.currentLang) {
								newLang = {
									...lang,
									hasValue: event.target.value !== ''
								};
							}
							return newLang;
						}
					),
					currentValue: event.target.value,
					hasError,
					values: newValues
				};
			},
			() => this._onChange(event, hasError)
		);
	}

	_validateValues(values) {
		const {defaultLang} = this.props;
		return !!values[defaultLang];
	}

	render() {
		const {
			defaultLang,
			initialLang,
			initialOpen,
			readOnly
		} = this.props;

		const {
			availableLanguages,
			currentValue,
			hasError
		} = this.state;

		const inputGroupItemClasses = getCN(
			'input-group-item ml-3',
			{
				'has-error': hasError
			}
		);

		return (
			<div className="input-group input-localized input-localized-input">
				<LocalizedDropdown
					availableLanguages={availableLanguages}
					defaultLang={defaultLang}
					initialLang={initialLang}
					initialOpen={initialOpen}
					onLanguageChange={this._handleLanguageChange}
				/>
				<div className={inputGroupItemClasses}>
					<input
						className="rounded form-control language-value field form-control-inline form-control"
						onChange={
							this._handleInputChange
						}
						readOnly={readOnly}
						type="text"
						value={currentValue}
					/>

				</div>
			</div>
		);
	}
}