/* globals describe, test, jest, expect, beforeAll, afterAll */

import {createExperienceReducer, endCreateExperience, startCreateExperience, deleteExperienceReducer} from '../../../src/main/resources/META-INF/resources/js/reducers/experiences.es';
import {CREATE_EXPERIENCE, END_CREATE_EXPERIENCE, START_CREATE_EXPERIENCE, DELETE_EXPERIENCE} from '../../../src/main/resources/META-INF/resources/js/actions/actions.es';

const EXPERIENCE_ID = 'EXPERIENCE_ID';

const EXPERIENCE_ID_SECOND = 'EXPERIENCE_ID_SECOND';

describe(
	'experiences reducers',
	() => {
		test(
			'createExperienceReducer communicates with API and updates the state',
			() => {
				const EXPERIENCES_LIST = [EXPERIENCE_ID, EXPERIENCE_ID_SECOND];
				let experiencesCount = -1;
				let prevLiferayGlobal = null;

				prevLiferayGlobal = {...global.Liferay};
				global.Liferay = {
					Service(
						URL,
						{
							classNameId,
							classPK,
							segmentsEntryId,
							nameMap,
							active,
							priority
						},
						callbackFunc,
						errorFunc
					) {
						return callbackFunc(
							{
								active,
								nameCurrentValue: JSON.parse(nameMap).en_US,
								priority,
								segmentsEntryId,
								segmentsExperienceId: (experiencesCount++, EXPERIENCES_LIST[experiencesCount])
							}
						);
					}
				};


				const availableExperiences = {};
				const classNameId = 'test-class-name-id';
				const classPK = 'test-class-p-k';
				const spy = jest.spyOn(global.Liferay, 'Service');

				const prevState = {
					availableExperiences,
					classNameId,
					classPK,
					defaultLanguageId: 'en_US'
				};

				const payload = {
					experienceLabel: 'test experience label',
					segmentId: 'test-segment-id'
				};

				const nextState = {
					...prevState,
					availableExperiences: {
						[EXPERIENCE_ID]: {
							active: true,
							experienceId: EXPERIENCE_ID,
							experienceLabel: payload.experienceLabel,
							priority: 0,
							segmentId: payload.segmentId
						}
					},
					experienceId: EXPERIENCE_ID
				};

				const liferayServiceParams = {
					active: true,
					classNameId: prevState.classNameId,
					classPK: prevState.classPK,
					nameMap: JSON.stringify({en_US: payload.experienceLabel}),
					priority: 0,
					segmentsEntryId: payload.segmentId
				};

				expect.assertions(4);

				createExperienceReducer(prevState, CREATE_EXPERIENCE, payload)
					.then(
						response => {
							expect(response).toEqual(nextState);
						}
					);

				expect(spy).toHaveBeenCalledWith(
					expect.stringContaining(''),
					liferayServiceParams,
					expect.objectContaining({}),
					expect.objectContaining({})
				)

				const secondPayload = {
					experienceLabel: 'second test experience label',
					segmentId: 'test-segment-id'
				};

				const secondNextState = {
					...nextState,
					availableExperiences: {
						...nextState.availableExperiences,
						[EXPERIENCE_ID_SECOND]: {
							active: true,
							experienceId: EXPERIENCE_ID_SECOND,
							experienceLabel: secondPayload.experienceLabel,
							priority: 1,
							segmentId: secondPayload.segmentId
						}
					},
					experienceId: EXPERIENCE_ID_SECOND
				};

				const secondLiferayServiceParams = {
					active: true,
					classNameId: prevState.classNameId,
					classPK: prevState.classPK,
					nameMap: JSON.stringify({en_US: secondPayload.experienceLabel}),
					priority: 1,
					segmentsEntryId: secondPayload.segmentId
				};

				createExperienceReducer(
					nextState,
					CREATE_EXPERIENCE,
					secondPayload
				).then(
					response => {
						expect(response).toEqual(secondNextState);
					}
				);

				expect(spy).toHaveBeenLastCalledWith(
					expect.stringContaining(''),
					secondLiferayServiceParams,
					expect.objectContaining({}),
					expect.objectContaining({})
				);
				global.Liferay = prevLiferayGlobal;
			}
		);

		test(
			'startCreateExperience and endCreateExperience switch states',
			() => {
				const prevState = {};

				const creatingExperienceState = startCreateExperience(prevState, START_CREATE_EXPERIENCE);
				expect(creatingExperienceState).toMatchObject(
					{
						experienceCreation: {
							creatingExperience: true,
							error: null
						}
					}
				);
				const notEdtingState = endCreateExperience(creatingExperienceState, END_CREATE_EXPERIENCE);

				expect(notEdtingState).toMatchObject(
					{
						experienceCreation: {
							creatingExperience: false,
							error: null
						}
					}
				);
			}
		);

		test(
			'deleteExperience communicates with API and updates the state',
			() => {
				const EXPERIENCE_ID_DEFAULT = 'EXPERIENCE_ID_DEFAULT';

				const availableExperiences = {
					[EXPERIENCE_ID_DEFAULT]: {
						experienceId: EXPERIENCE_ID,
						experienceLabel: 'A default test experience',
						segmentId: 'notRelevantSegmentId'
					},
					[EXPERIENCE_ID]: {
						experienceId: EXPERIENCE_ID,
						experienceLabel: 'A test experience',
						segmentId: 'notRelevantSegmentId'
					},
					[EXPERIENCE_ID_SECOND]: {
						experienceId: EXPERIENCE_ID_SECOND,
						experienceLabel: 'A second test experience',
						segmentId: 'notRelevantSegmentId'
					}
				}

				const classNameId = 'test-class-name-id';
				const classPK = 'test-class-p-k';

				const prevState = {
					availableExperiences,
					classNameId,
					classPK,
					defaultLanguageId: 'en_US',
					experienceId: EXPERIENCE_ID_SECOND,
					defaultExperienceId: EXPERIENCE_ID_DEFAULT
				};

				const nextState = {
					...prevState,
					availableExperiences: {
						[EXPERIENCE_ID_DEFAULT]: prevState.availableExperiences[EXPERIENCE_ID_DEFAULT],
						[EXPERIENCE_ID_SECOND]: prevState.availableExperiences[EXPERIENCE_ID_SECOND],
					}
				};

				const secondNextState = {
					...nextState,
					availableExperiences: {
						[EXPERIENCE_ID_DEFAULT]: prevState.availableExperiences[EXPERIENCE_ID_DEFAULT]
					},
					experienceId: EXPERIENCE_ID_DEFAULT
				}

				global.Liferay = {
					Service(
						URL,
						{
							segmentsExperienceId,
						},
						callbackFunc,
						errorFunc
					) {
						return callbackFunc(
							{
								segmentsExperienceId,
							}
						);
					}
				};

				const spy = jest.spyOn(global.Liferay, 'Service');

				
				expect.assertions(6);

				deleteExperienceReducer(prevState, DELETE_EXPERIENCE, { experienceId: EXPERIENCE_ID })
					.then(state => {
						expect(state).toEqual(nextState);
					})

				expect(spy).toHaveBeenCalledTimes(1);
				expect(spy).toHaveBeenLastCalledWith(
					expect.stringContaining(''),
					{segmentsExperienceId: EXPERIENCE_ID},
					expect.objectContaining({}),
					expect.objectContaining({})
				);
				
				deleteExperienceReducer(nextState, DELETE_EXPERIENCE, { experienceId: EXPERIENCE_ID_SECOND })
					.then(state => {
						expect(state).toEqual(secondNextState);
					});

				expect(spy).toHaveBeenCalledTimes(2);
				expect(spy).toHaveBeenLastCalledWith(
					expect.stringContaining(''),
					{segmentsExperienceId: EXPERIENCE_ID_SECOND},
					expect.objectContaining({}),
					expect.objectContaining({})
				);
			}
		)
	}
);
