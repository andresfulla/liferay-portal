/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarImagePropertiesPanelDelegateTemplate.soy';
import {
	EDITABLE_FIELD_CONFIG_KEYS,
	TARGET_TYPES
} from '../../../utils/constants';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from '../../../actions/saveChanges.es';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import templates from './FloatingToolbarImagePropertiesPanel.soy';
import {UPDATE_CONFIG_ATTRIBUTES} from '../../../actions/actions.es';
import {prefixSegmentsExperienceId} from '../../../utils/prefixSegmentsExperienceId.es';

/**
 * FloatingToolbarImagePropertiesPanel
 */
class FloatingToolbarImagePropertiesPanel extends Component {
	/**
	 * Extracts segmented `imageTitle` and `imageAlt` from the `state`
	 * and adds them to the state to easier template use.
	 *
	 * @inheritdoc
	 * @param {object} state
	 * @review
	 */
	prepareStateForRender(state) {
		const {config} = state.item.editableValues;
		const segmentationData = {
			defaultExperienceKey: prefixSegmentsExperienceId(
				state.defaultSegmentsExperienceId
			),
			experienceKey: prefixSegmentsExperienceId(
				state.segmentsExperienceId
			)
		};

		let segmentedImageTitle = _getSegmentedConfigValue(
			config,
			'imageTitle',
			segmentationData
		);
		const segmentedImageAlt = _getSegmentedConfigValue(
			config,
			'alt',
			segmentationData
		);

		if (!segmentedImageTitle)
			segmentedImageTitle = _getSegmentedConfigValue(
				config,
				'imageSource',
				segmentationData
			);

		return {
			...state,
			imageAlt: segmentedImageAlt || '',
			imageTitle: segmentedImageTitle || ''
		};
	}
	/**
	 * Updates fragment configuration
	 * @param {object} config Configuration
	 * @private
	 * @review
	 */
	_updateFragmentConfig(config) {
		this.store
			.dispatch(enableSavingChangesStatusAction())
			.dispatch({
				config,
				editableId: this.item.editableId,
				fragmentEntryLinkId: this.item.fragmentEntryLinkId,
				type: UPDATE_CONFIG_ATTRIBUTES
			})
			.dispatch(updateLastSaveDateAction())
			.dispatch(disableSavingChangesStatusAction());
	}

	/**
	 * Handle alt text change
	 * @private
	 * @review
	 */
	_handleAltTextInputChange() {
		this._updateFragmentConfig({
			[EDITABLE_FIELD_CONFIG_KEYS.alt]: event.delegateTarget.value
		});
	}

	/**
	 * Handle select image button change
	 * @private
	 * @review
	 */
	_handleClearImageButtonClick() {
		this.emit('clearEditor');
	}

	/**
	 * Handle image link change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleImageLinkInputChange(event) {
		this._updateFragmentConfig({
			[EDITABLE_FIELD_CONFIG_KEYS.imageLink]: event.delegateTarget.value
		});
	}

	/**
	 * Handle image target option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleImageTargetOptionChange(event) {
		this._updateFragmentConfig({
			[EDITABLE_FIELD_CONFIG_KEYS.imageTarget]: event.delegateTarget.value
		});
	}

	/**
	 * Handle select image button change
	 * @private
	 * @review
	 */
	_handleSelectImageButtonClick() {
		this.emit('createProcessor');
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarImagePropertiesPanel.STATE = {
	/**
	 * @default TARGET_TYPES
	 * @memberOf FloatingToolbarImagePropertiesPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_imageTargetOptions: Config.array()
		.internal()
		.value(TARGET_TYPES),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarImagePropertiesPanel
	 * @review
	 * @type {string}
	 */
	defaultSegmentsExperienceId: Config.string(),

	/**
	 * @default ''
	 * @memberof FloatingToolbarImagePropertiesPanel
	 * @review
	 * @type {string}
	 */
	imageAlt: Config.string().value(''),

	/**
	 * Calculated at `prepareStateForRender` method
	 * based on segmentation
	 *
	 * @default ''
	 * @memberof FloatingToolbarImagePropertiesPanel
	 * @review
	 * @type {string}
	 */
	imageTitle: Config.string().value(''),

	/**
	 * @default undefined
	 * @memberOf FloatingToolbarImagePropertiesPanel
	 * @review
	 * @type {object}
	 */
	item: Config.object(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarImagePropertiesPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config.string().required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarImagePropertiesPanel
	 * @review
	 * @type {object}
	 */
	store: Config.object().value(null)
};

const ConnectedFloatingToolbarImagePropertiesPanel = getConnectedComponent(
	FloatingToolbarImagePropertiesPanel,
	['imageSelectorURL', 'portletNamespace', 'segmentsExperienceId']
);

Soy.register(ConnectedFloatingToolbarImagePropertiesPanel, templates);

export {
	ConnectedFloatingToolbarImagePropertiesPanel,
	FloatingToolbarImagePropertiesPanel
};
export default FloatingToolbarImagePropertiesPanel;

/**
 * Helper function to retrieve the value of a config object taking segmentation into account.
 *
 * By order of preference:
 * 1. config[experienceKey][valueKey]
 * 2. config[defaultExperienceKey][valueKey]
 * 3. config[valueKey]
 *
 * @param {object} config
 * @param {string} valueKey
 * @param {object} segmentationData
 * @param {string} segmentationData.defaultExperienceKey
 * @param {string} segmentationData.experienceKey
 * @return {string}
 * @review
 */
function _getSegmentedConfigValue(
	config,
	valueKey,
	{defaultExperienceKey, experienceKey}
) {
	let segmentedConfigValue =
		config && config[experienceKey] && config[experienceKey][valueKey];

	if (segmentedConfigValue === undefined)
		segmentedConfigValue =
			config[defaultExperienceKey] &&
			config[defaultExperienceKey][valueKey];

	if (segmentedConfigValue === undefined)
		segmentedConfigValue = config && config[valueKey];

	return segmentedConfigValue;
}
