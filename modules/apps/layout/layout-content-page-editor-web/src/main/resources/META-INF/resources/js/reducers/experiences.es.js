import {setIn} from '../utils/utils.es';

const CREATE_EXPERIENCE_URL = '/segments.segmentsexperience/add-segments-experience';

/**
 * @param {!object} state
 * @param {!object} payload
 * @param {string} payload.segmentId
 * @param {string} payload.experienceLabel
 * @return {object}
 * @review
 */
export function createExperienceReducer(state, payload) {
	return new Promise(
		resolve => {
			let nextState = state;
			const {experienceLabel, segmentId} = payload;

			const {
				classNameId,
				classPK
			} = nextState;

			const nameMap = JSON.stringify(
				{
					[state.defaultLanguageId]: experienceLabel
				}
			);
			const priority = Object.values(nextState.availableExperiences || []).length;

			Liferay.Service(
				CREATE_EXPERIENCE_URL,
				{
					active: true,
					classNameId,
					classPK,
					nameMap,
					priority,
					segmentsEntryId: segmentId
				},
				(obj) => {

					const {
						active,
						nameCurrentValue,
						priority,
						segmentsEntryId,
						segmentsExperienceId
					} = obj;

					nextState = setIn(
						nextState,
						[
							'availableExperiences',
							segmentsExperienceId
						],
						{
							active,
							experienceId: segmentsExperienceId,
							experienceLabel: nameCurrentValue,
							priority,
							segmentId: segmentsEntryId
						}
					);

					nextState = setIn(
						nextState,
						['experienceId'],
						segmentsExperienceId
					);

					resolve(nextState);
				},
				error => {
					resolve(nextState);
				}
			);
		}
	);
}

/**
 * @param {!object} state
 * @param {object} [payload]
 * @return {object}
 * @review
 */
export function startCreateExperience(state, payload) {
	let nextState = state;

	nextState = setIn(
		nextState,
		['experienceCreation'],
		{
			creatingExperience: true,
			error: null
		}
	);

	return nextState;
}

/**
 * @param {!object} state
 * @param {object} [payload]
 * @return {object}
 * @review
 */
export function endCreateExperience(state, payload) {
	let nextState = state;

	nextState = setIn(
		nextState,
		['experienceCreation'],
		{
			creatingExperience: false,
			error: null
		}
	);
	return nextState;
}

/**
 *
 *
 * @export
 * @param {!object} state
 * @param {!object} payload
 * @param {!string} payload.experienceId
 * @returns
 */
export function selectExperienceReducer(state, payload) {
	let nextState = state;
	
	nextState = setIn(
		nextState,
		['experienceId'],
		payload.experienceId,
	);

	return nextState;
}

export {
	createExperienceReducer,
	startCreateExperience,
	endCreateExperience,
	selectExperienceReducer
};