import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * @param {!object} state
 * @param {!object} payload
 * @param {boolean} payload.savingChanges
 * @param {Date} payload.lastSaveDate
 * @return {object}
 * @review
 */
function saveChangesReducer(state, payload) {
	let nextState = state;

	const newDate = payload.lastSaveDate.toLocaleTimeString(
		Liferay.ThemeDisplay.getBCP47LanguageId()
	);

	nextState = setIn(nextState, ['lastSaveDate'], newDate);

	return nextState;
}


/**
 * @param {!object} state
 * @param {!object} payload
 * @param {boolean} payload.savingChanges
 * @param {Date} payload.lastSaveDate
 * @return {object}
 * @review
 */
function saveChangesStatusReducer(state, payload) {
	let nextState = state;
	nextState = setIn(nextState, ['savingChanges'], Boolean(payload.savingChanges));

	return nextState;
}

export {saveChangesReducer, saveChangesStatusReducer};