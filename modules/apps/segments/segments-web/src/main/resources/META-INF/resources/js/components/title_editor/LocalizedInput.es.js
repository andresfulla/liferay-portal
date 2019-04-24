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
		availableLanguages: [],
		onChange: () => {}
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

	_onLocalizedChange = event => {
		this.props.onChange(
			event,
			this.state.values
		);
	}

	_handleInputChange = event => {
		event.persist();
		this.setState(
			prevState => {
				const newValues = {
					...prevState.values,
					[prevState.currentLang]: event.target.value
				};

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
					values: newValues
				};
			},
			() => this._onLocalizedChange(event)
		);
	}

	render() {
		const {
			defaultLang,
			initialLang,
			initialOpen,
			readOnly
		} = this.props;

		return (
			<React.Fragment>
				<div className="input-group input-localized input-localized-input">
					<LocalizedDropdown
						availableLanguages={this.state.availableLanguages}
						defaultLang={defaultLang}
						initialLang={initialLang}
						initialOpen={initialOpen}
						onLanguageChange={this._handleLanguageChange}
					/>
					<input
						className="ml-3 rounded form-control language-value field form-control-inline form-control"
						onChange={
							this._handleInputChange
						}
						readOnly={readOnly}
						type="text"
						value={this.state.currentValue}
					/>
				</div>
			</React.Fragment>
		);
	}
}