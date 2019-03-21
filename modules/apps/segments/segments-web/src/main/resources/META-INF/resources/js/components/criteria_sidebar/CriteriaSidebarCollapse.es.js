import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ClayIcon from '../shared/ClayIcon.es';
import CriteriaSidebarItem from './CriteriaSidebarItem.es';
import { PROPERTY_TYPES } from '../../utils/constants.es';
import { jsDatetoYYYYMMDD } from '../../utils/utils.es';

/**
 * Returns a default value for a property provided.
 * @param {Object} property
 * @returns
 */
function getDefaultValue(property) {
	const { options, type } = property;

	let defaultValue = '';

	if (type === PROPERTY_TYPES.STRING && options && options.length) {
		defaultValue = options[0].value;
	}
	else if (type === PROPERTY_TYPES.DATE) {
		defaultValue = jsDatetoYYYYMMDD((new Date()));
	}
	else if (type === PROPERTY_TYPES.DATE_TIME) {
		defaultValue = (new Date()).toISOString();
	}
	else if (type === PROPERTY_TYPES.BOOLEAN) {
		defaultValue = 'true';
	}
	else if (type === PROPERTY_TYPES.INTEGER) {
		defaultValue = 0;
	}
	else if (type === PROPERTY_TYPES.DOUBLE) {
		defaultValue = '0.00';
	}

	return defaultValue;
}

class CriteriaSidebarCollapse extends Component {
	static propTypes = {
		propertyKey: PropTypes.string,
		propertyGroups: PropTypes.array,
		searchValue: PropTypes.string,
		onCollapseClick: PropTypes.func,
	};

	_filterProperties = searchValue => {
		const propertyGroup = this.props.propertyGroups.find(
			propertyGroup => this.props.propertyKey === propertyGroup.propertyKey
		);

		const properties = (propertyGroup ? propertyGroup.properties : []);

		return properties.filter(
			property => {
				const propertyLabel = property.label.toLowerCase();

				return propertyLabel.includes(searchValue.toLowerCase());
			}
		);
	}

	_handleClick = (key, editing) => () => {
		this.props.onCollapseClick(key, editing);
	}

	render() {
		const { propertyKey, propertyGroups, searchValue } = this.props;

		const propertyGroup = propertyGroups.find(
			propertyGroup => propertyKey === propertyGroup.propertyKey
		);

		const filteredProperties = searchValue ? this._filterProperties(searchValue) :
			(propertyGroup ? propertyGroup.properties : []);

		return (
			<div className="sidebar-collapse-groups">
				{propertyGroups.map(propertyGroup => {
					return <div key={propertyGroup.propertyKey} className={`sidebar-collapse-${propertyGroup.propertyKey}`}>
						<div onClick={this._handleClick(propertyGroup.propertyKey, propertyGroup.propertyKey === propertyKey ? true : false)} className="sidebar-header">
							{propertyGroup.name}
							<ClayIcon iconName="angle-down" />
						</div>
						<ul className="properties-list">
						{propertyGroup.propertyKey === propertyKey && filteredProperties.length === 0 ?
								<li className="empty-message">
									{Liferay.Language.get('no-results-were-found')}
								</li>
							: propertyGroup.propertyKey === propertyKey && filteredProperties.length &&
								filteredProperties.map(
									({ label, name, options, type }) => {
										const defaultValue = getDefaultValue(
											{
												label,
												name,
												options,
												type
											}
										);

										return (
											<CriteriaSidebarItem
												className={`color--${propertyGroup.propertyKey}`}
												defaultValue={defaultValue}
												key={name}
												label={label}
												name={name}
												propertyKey={propertyGroup.propertyKey}
												type={type}
											/>
										);
									}
								)
							}
						</ul>
					</div>
				})}
			</div>
		);
	}
}

export default CriteriaSidebarCollapse;