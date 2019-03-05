import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * @param {!object} state
 * @return {object}
 * @review
 */
function hideFragmentsEditorSidebarReducer(state) {
	let nextState = state;

	nextState = setIn(nextState, ['fragmentsEditorSidebarVisible'], false);

	return nextState;
}

/**
 * @param {!object} state
 * @return {object}
 * @review
 */
function toggleFragmentsEditorSidebarReducer(state) {
	let nextState = state;

	nextState = setIn(
		nextState,
		['fragmentsEditorSidebarVisible'],
		!nextState.fragmentsEditorSidebarVisible
	);

	return nextState;
}

export {
	hideFragmentsEditorSidebarReducer,
	toggleFragmentsEditorSidebarReducer
};