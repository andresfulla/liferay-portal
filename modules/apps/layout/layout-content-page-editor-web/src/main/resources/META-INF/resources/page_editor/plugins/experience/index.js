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
	SELECT_SEGMENTS_EXPERIENCE,
	UPDATE_SEGMENTS_EXPERIENCE,
	UPDATE_SEGMENTS_EXPERIENCE_PRIORITY
} from './actions';
import ExperienceToolbarSection from './components/ExperienceToolbarSection';
import createExperienceReducer from './reducers/createExperience';
import deleteExperienceReducer from './reducers/deleteExperience';
import selectExperienceReducer from './reducers/selectExperience';
import updateExperienceReducer from './reducers/updateExperience';
import updateExperiencePriorityReducer from './reducers/updateExperiencePriority';

function renderExperiencesSection() {
	const {Component} = this;

	const selectId = `${this.toolbarId}_${this.toolbarPluginId}`;

	return (
		<Component>
			<ExperienceToolbarSection selectId={selectId} />
		</Component>
	);
}

function experiencesActivate() {
	const reducer = (state, action) => {
		let nextState = state;

		switch (action.type) {
			case CREATE_SEGMENTS_EXPERIENCE:
				nextState = createExperienceReducer(nextState, action.payload);
				break;
			case DELETE_SEGMENTS_EXPERIENCE:
				nextState = deleteExperienceReducer(nextState, action.payload);
				break;
			case UPDATE_SEGMENTS_EXPERIENCE:
				nextState = updateExperienceReducer(nextState, action.payload);
				break;
			case SELECT_SEGMENTS_EXPERIENCE:
				nextState = selectExperienceReducer(nextState, action.payload);
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

		if (app.store.availableSegmentsExperiences !== null) {
			this.activate = experiencesActivate.bind(this);
			this.renderToolbarSection = renderExperiencesSection.bind(this);
		}
	}

	deactivate() {
		this.dispatch(this.Actions.unloadReducer(Experience.name));
	}
}
