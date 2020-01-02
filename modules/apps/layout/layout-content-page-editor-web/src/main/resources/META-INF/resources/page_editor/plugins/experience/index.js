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

import React from 'react';

import {Component} from '../../core/AppContext';
import {
	CREATE_SEGMENTS_EXPERIENCE,
	DELETE_SEGMENTS_EXPERIENCE,
	EDIT_SEGMENTS_EXPERIENCE,
	SELECT_SEGMENTS_EXPERIENCE,
	UPDATE_SEGMENTS_EXPERIENCE_PRIORITY
} from './actions';
import ExperienceToolbarSection from './components/ExperienceToolbarSection';

/**
 * Sets `lockedSegmentsExperience` in the state, depending on the Experience
 * @param {object} state
 * @param {string | null} state.selectedSidebarPanelId
 * @param {object} experience
 * @param {boolean} experience.hasLockedSegmentsExperiment
 * @return {object} nextState
 */
function _setExperienceLock(state, experience) {
	const lockedSegmentsExperience = experience.hasLockedSegmentsExperiment;

	//TODO selectedSidebarPanelId

	return {
		...state,
		lockedSegmentsExperience
	};
}

/**
 * Stores a the layout data of a new experience in layoutDataList
 * @param {object} state
 * @param {Array<{segmentsExperienceId: string}>} state.layoutDataList
 * @param {object} state.layoutData
 * @param {string} state.defaultSegmentsExperienceId
 * @param {string} segmentsExperienceId The segmentsExperience id that owns this LayoutData
 * @param {string} layoutData The new LayoutData to store
 * @returns {object}
 */
function _storeNewLayoutData(state, segmentsExperienceId, layoutData) {
	const nextState = state;
	nextState.layoutDataList.push({
		layoutData,
		segmentsExperienceId
	});
	return nextState;
}

/**
 * Updates the fragmentEntryLinks editableValues in State
 *
 * @param {object} state
 * @param {string} state.defaultSegmentsExperienceId
 * @param {object} state.fragmentEntryLinks
 * @param {string} fragmentEntryLinks
 * @returns {object}
 */
function _updateFragmentEntryLinksEditableValues(
	state,
	fragmentEntryLinks = {}
) {
	const updatedFragmentEntryLinks = state.fragmentEntryLinks;

	Object.entries(fragmentEntryLinks).forEach(([id, editableValues]) => {
		updatedFragmentEntryLinks[id].editableValues = editableValues;
	});

	return {
		...state,
		fragmentEntryLinks: updatedFragmentEntryLinks
	};
}

function editExperienceReducer(state, payload) {
	let nextState = state;

	const updatedExperience = payload;

	const experience =
		state.availableSegmentsExperiences[
			updatedExperience.segmentsExperienceId
		];

	if (experience) {
		nextState = {
			...nextState,
			availableSegmentsExperiences: {
				...nextState.availableSegmentsExperiences,
				[experience.segmentsExperienceId]: {
					...updatedExperience,
					priority: experience.priority
				}
			}
		};
	}

	return nextState;
}

function createExperienceReducer(state, payload) {
	let nextState = state;

	const {
		fragmentEntryLinks,
		layoutData,
		segmentsExperience: newExperience
	} = payload;

	nextState = {
		...nextState,
		availableSegmentsExperiences: {
			...nextState.availableSegmentsExperiences,
			[newExperience.segmentsExperienceId]: {
				...newExperience,
				hasLockedSegmentsExperiment: true
			}
		},
		segmentsExperienceId: newExperience.segmentsExperienceId

		//_updateFragmentEntryLinksEditableValues
		//_setExperienceLock
		//_switchLayoutDataList -> _updateFragmentEntryLinks and _setUsedWidgets
	};

	nextState = _storeNewLayoutData(
		nextState,
		newExperience.segmentsExperienceId,
		layoutData
	);

	// TODO update layoutData

	nextState = _updateFragmentEntryLinksEditableValues(
		nextState,
		fragmentEntryLinks
	);

	nextState = _setExperienceLock(nextState, {
		hasLockedSegmentsExperiment: true
	});

	//TODO _switchLayoutDataList -> {
	// updatePageEditorLayoutData
	//_updateFragmentEntryLinks
	//_setUsedWidgets
	//}

	return nextState;
}

function selectExperienceReducer(state, payload) {
	let nextState = state;

	const newExperience = payload;

	// TODO updatePageEditorLayoutData call

	const {layoutData: prevLayoutData} = nextState;

	// TODO refactor this pile of object
	nextState = {
		...nextState,
		layoutData: nextState.layoutDataList.find(
			layoutDataItem =>
				newExperience.segmentsExperienceId ===
				layoutDataItem.segmentsExperienceId
		).layoutData,
		layoutDataList: nextState.layoutDataList.map(layoutDataItem => {
			if (
				layoutDataItem.segmentsExperienceId ===
				nextState.segmentsExperienceId
			) {
				return {
					...layoutDataItem,
					layoutData: prevLayoutData
				};
			}
			return layoutDataItem;
		}),
		segmentsExperienceId: newExperience.segmentsExperienceId
	};

	return nextState;
}

function deleteExperienceReducer(state, payload) {
	let nextState = state;
	const {defaultExperienceId, segmentsExperienceId} = payload;

	const availableSegmentsExperiences = {
		...nextState.availableSegmentsExperiences
	};

	delete availableSegmentsExperiences[segmentsExperienceId];

	nextState = {
		...nextState,
		availableSegmentsExperiences
	};

	if (nextState.segmentsExperienceId === segmentsExperienceId) {
		nextState = {
			...nextState,
			segmentsExperienceId: defaultExperienceId
		};
	}

	return nextState;
}

function updateExperiencePriorityReducer(state, {subtarget, target}) {
	const experiences = state.availableSegmentsExperiences;

	const targetExperience = {
		...experiences[target.segmentsExperienceId],
		priority: target.priority
	};
	const subtargetExperience = {
		...experiences[subtarget.segmentsExperienceId],
		priority: subtarget.priority
	};

	const updatedExperiences = {
		...experiences,
		[target.segmentsExperienceId]: targetExperience,
		[subtarget.segmentsExperienceId]: subtargetExperience
	};

	return {
		...state,
		availableSegmentsExperiences: updatedExperiences
	};
}

/**
 * Entry-point for "Experience" (toolbar drop-down) functionality.
 */
export default class Experience {
	constructor({app, toolbarPlugin}) {
		this.Actions = app.Actions;
		this.Component = Component(app);
		this.dispatch = app.dispatch;

		this.toolbarId = app.config.toolbarId;
		this.toolbarPluginId = toolbarPlugin.toolbarPluginId;
	}

	activate() {
		const reducer = (state, action) => {
			let nextState = state;

			switch (action.type) {
				case CREATE_SEGMENTS_EXPERIENCE:
					nextState = createExperienceReducer(
						nextState,
						action.payload
					);
					break;
				case DELETE_SEGMENTS_EXPERIENCE:
					nextState = deleteExperienceReducer(
						nextState,
						action.payload
					);
					break;
				case EDIT_SEGMENTS_EXPERIENCE:
					nextState = editExperienceReducer(
						nextState,
						action.payload
					);
					break;
				case SELECT_SEGMENTS_EXPERIENCE:
					nextState = selectExperienceReducer(
						nextState,
						action.payload
					);
					break;
				case UPDATE_SEGMENTS_EXPERIENCE_PRIORITY:
					nextState = updateExperiencePriorityReducer(
						nextState,
						action.payload
					);
					break;
				default:
					break;
			}

			return nextState;
		};

		this.dispatch(this.Actions.loadReducer(reducer, Experience.name));
	}

	deactivate() {
		this.dispatch(this.Actions.unloadReducer(Experience.name));
	}

	renderToolbarSection() {
		const {Component} = this;

		const selectId = `${this.toolbarId}_${this.toolbarPluginId}`;

		return (
			<Component>
				<ExperienceToolbarSection selectId={selectId} />
			</Component>
		);
	}
}
