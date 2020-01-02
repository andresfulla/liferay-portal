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

import {
	cleanup,
	fireEvent,
	render,
	wait,
	waitForElement,
	within
} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {ConfigContext} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index';
import {StoreContext} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import AppContext from '../../../../../../src/main/resources/META-INF/resources/page_editor/core/AppContext';
import ExperienceToolbarSection from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/experience/components/ExperienceToolbarSection';

import '@testing-library/jest-dom/extend-expect';

import {
	UPDATE_SEGMENTS_EXPERIENCE_PRIORITY,
	EDIT_SEGMENTS_EXPERIENCE
} from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/experience/actions';

function renderExperienceToolbarSection(
	mockState = {},
	mockConfig = {},
	mockDispatch = () => {}
) {
	return render(
		<AppContext.Provider value={{dispatch: mockDispatch}}>
			<ConfigContext.Provider value={mockConfig}>
				<StoreContext.Provider value={mockState}>
					<ExperienceToolbarSection selectId="test-select-id" />
				</StoreContext.Provider>
			</ConfigContext.Provider>
		</AppContext.Provider>,
		{
			baseElement: document.body
		}
	);
}

const mockState = {
	availableSegmentsExperiences: {
		0: {
			active: true,
			hasLockedSegmentsExperiment: false,
			name: 'Default Experience',
			priority: -1,
			segmentsEntryId: 'test-segment-id-00',
			segmentsExperienceId: '0',
			segmentsExperimentStatus: undefined,
			segmentsExperimentURL: 'https//:default-experience.com'
		},
		'test-segment-id-01': {
			active: true,
			hasLockedSegmentsExperiment: false,
			name: 'Experience #1',
			priority: 3,
			segmentsEntryId: 'test-segment-id-00',
			segmentsExperienceId: 'test-experience-id-01',
			segmentsExperimentStatus: undefined,
			segmentsExperimentURL: 'https//:experience-1.com'
		},
		'test-segment-id-02': {
			active: true,
			hasLockedSegmentsExperiment: false,
			name: 'Experience #2',
			priority: 1,
			segmentsEntryId: 'test-segment-id-01',
			segmentsExperienceId: 'test-experience-id-02',
			segmentsExperimentStatus: undefined,
			segmentsExperimentURL: 'https//:experience-2.com'
		}
	},
	segmentsExperienceId: '0'
};

const mockConfig = {
	availableSegmentsEntries: {
		'test-segment-id-00': {
			name: 'A segment 0',
			segmentsEntryId: 'test-segment-id-00'
		},
		'test-segment-id-01': {
			name: 'A segment 1',
			segmentsEntryId: 'test-segment-id-01'
		}
	},
	classPK: 'test-classPK',
	defaultLanguageId: 'en_US',
	defaultSegmentsExperienceId: '0',
	hasUpdatePermissions: true
};

describe('ExperienceToolbarSection', () => {
	afterEach(cleanup);

	it('shows a list of Experiences ordered by priority', async () => {
		const {
			getAllByRole,
			getByLabelText,
			getByRole
		} = renderExperienceToolbarSection(mockState, mockConfig);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const listedExperiences = getAllByRole('listitem');

		expect(listedExperiences.length).toBe(3);

		/**
		 * Experiences are ordered by priority
		 */
		expect(
			within(listedExperiences[0]).getByText('Experience #1')
		).toBeInTheDocument();
		expect(
			within(listedExperiences[1]).getByText('Experience #2')
		).toBeInTheDocument();
		expect(
			within(listedExperiences[2]).getByText('Default Experience')
		).toBeInTheDocument();
	});

	it('displays a help hint on the locked icon for a locked Experience', async () => {
		const mockStateWithLockedExperience = {
			...mockState,
			availableSegmentsExperiences: {
				...mockState.availableSegmentsExperiences,
				'test-experience-id-03': {
					active: true,
					hasLockedSegmentsExperiment: true,
					name: 'Experience #3',
					priority: 5,
					segmentsEntryId: 'test-segment-id-00',
					segmentsExperienceId: 'test-experience-id-03',
					segmentsExperimentStatus: {
						label: 'running',
						value: 3
					},
					segmentsExperimentURL: 'https//:locked-experience.com'
				}
			}
		};
		const {
			getByLabelText,
			getByRole,
			getByText
		} = renderExperienceToolbarSection(
			mockStateWithLockedExperience,
			mockConfig
		);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const experience = getByText('Experience #3');

		const lockIcon = within(experience).getByRole('presentation');

		userEvent.click(lockIcon);

		getByText('experience-locked');
		getByText('edit-is-not-allowed-for-this-experience');
	});

	it('calls the backend to increase priority', async () => {
		window.Liferay.Service = jest.fn(() => Promise.resolve());

		const mockDispatch = jest.fn(() => {});

		const {
			getAllByRole,
			getByLabelText,
			getByRole
		} = renderExperienceToolbarSection(mockState, mockConfig, mockDispatch);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const experienceItems = getAllByRole('listitem');

		expect(experienceItems.length).toBe(3);

		expect(
			within(experienceItems[0]).getByText('Experience #1')
		).toBeInTheDocument();
		expect(
			within(experienceItems[1]).getByText('Experience #2')
		).toBeInTheDocument();

		const bottomExperiencePriorityButton = within(
			experienceItems[1]
		).getByTitle('prioritize-experience');
		const topExperiencePriorityButton = within(
			experienceItems[0]
		).getByTitle('prioritize-experience');

		/**
		 * Top Experience cannot be prioritized
		 */
		expect(topExperiencePriorityButton.disabled).toBe(true);

		/**
		 * Bottom Experience can be prioritized
		 */
		expect(bottomExperiencePriorityButton.disabled).toBe(false);

		userEvent.click(bottomExperiencePriorityButton);

		await wait(() =>
			expect(window.Liferay.Service).toHaveBeenCalledTimes(1)
		);

		expect(window.Liferay.Service).toHaveBeenCalledWith(
			expect.stringContaining(''),
			expect.objectContaining({
				newPriority: 3,
				segmentsExperienceId: 'test-experience-id-02'
			})
		);

		expect(mockDispatch).toHaveBeenCalledWith(
			expect.objectContaining({
				payload: {
					subtarget: {
						priority: 1,
						segmentsExperienceId: 'test-experience-id-01'
					},
					target: {
						priority: 3,
						segmentsExperienceId: 'test-experience-id-02'
					}
				},
				type: UPDATE_SEGMENTS_EXPERIENCE_PRIORITY
			})
		);
	});

	it('calls the backend to decrease priority', async () => {
		window.Liferay.Service = jest.fn(() => Promise.resolve());

		const mockDispatch = jest.fn(() => {});

		const {
			getAllByRole,
			getByLabelText,
			getByRole
		} = renderExperienceToolbarSection(mockState, mockConfig, mockDispatch);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const experienceItems = getAllByRole('listitem');

		expect(experienceItems.length).toBe(3);

		expect(
			within(experienceItems[0]).getByText('Experience #1')
		).toBeInTheDocument();
		expect(
			within(experienceItems[1]).getByText('Experience #2')
		).toBeInTheDocument();

		const bottomExperiencePriorityButton = within(
			experienceItems[1]
		).getByTitle('deprioritize-experience');
		const topExperiencePriorityButton = within(
			experienceItems[0]
		).getByTitle('deprioritize-experience');

		/**
		 * Top Experience can be deprioritized
		 */
		expect(topExperiencePriorityButton.disabled).toBe(false);

		/**
		 * Bottom Experience cannot be deprioritized
		 */
		expect(bottomExperiencePriorityButton.disabled).toBe(true);

		userEvent.click(topExperiencePriorityButton);

		await wait(() =>
			expect(window.Liferay.Service).toHaveBeenCalledTimes(1)
		);

		expect(window.Liferay.Service).toHaveBeenCalledWith(
			expect.stringContaining(''),
			expect.objectContaining({
				newPriority: 1,
				segmentsExperienceId: 'test-experience-id-01'
			})
		);

		expect(mockDispatch).toHaveBeenCalledWith(
			expect.objectContaining({
				payload: {
					subtarget: {
						priority: 3,
						segmentsExperienceId: 'test-experience-id-02'
					},
					target: {
						priority: 1,
						segmentsExperienceId: 'test-experience-id-01'
					}
				},
				type: UPDATE_SEGMENTS_EXPERIENCE_PRIORITY
			})
		);
	});

	it('calls the backend to edit the experience', async () => {
		window.Liferay.Service = jest.fn(() => Promise.resolve());

		const mockDispatch = jest.fn(() => {});

		const {
			getAllByRole,
			getByLabelText,
			getByRole,
			getByText
		} = renderExperienceToolbarSection(mockState, mockConfig, mockDispatch);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const experienceItems = getAllByRole('listitem');

		expect(experienceItems.length).toBe(3);

		expect(
			within(experienceItems[0]).getByText('Experience #1')
		).toBeInTheDocument();

		const editExperienceButton = within(experienceItems[0]).getByTitle(
			'edit-experience'
		);

		expect(editExperienceButton.disabled).toBe(false);

		userEvent.click(editExperienceButton);

		await waitForElement(() => getByLabelText('name'));

		const nameInput = getByLabelText('name');
		const segmentSelect = getByLabelText('audience');

		expect(nameInput.value).toBe('Experience #1');
		expect(segmentSelect.value).toBe('test-segment-id-00');

		userEvent.type(nameInput, 'New Experience #1');
		fireEvent.change(segmentSelect, {
			target: {value: 'test-segment-id-01'}
		});

		expect(nameInput.value).toBe('New Experience #1');
		expect(segmentSelect.value).toBe('test-segment-id-01');

		userEvent.click(getByText('save'));

		await wait(() =>
			expect(window.Liferay.Service).toHaveBeenCalledTimes(1)
		);

		expect(window.Liferay.Service).toHaveBeenCalledWith(
			expect.stringContaining(''),
			expect.objectContaining({
				active: true,
				nameMap: '{"en_US":"New Experience #1"}',
				segmentsEntryId: 'test-segment-id-01',
				segmentsExperienceId: 'test-experience-id-01'
			})
		);

		expect(mockDispatch).toHaveBeenCalledWith(
			expect.objectContaining({
				payload: {
					name: 'New Experience #1',
					segmentsEntryId: 'test-segment-id-01',
					segmentsExperienceId: 'test-experience-id-01'
				},
				type: EDIT_SEGMENTS_EXPERIENCE
			})
		);
	});
});
