import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * Updates active element data with the information sent.
 * @param {!object} state
 * @param {!object} payload
 * @param {string} payload.activeItemId
 * @param {string} payload.activeItemType
 * @return {object}
 * @review
 */
function updateActiveItemReducer(state, payload) {
	let nextState = state;

	nextState = setIn(nextState, ['activeItemId'], payload.activeItemId);
	nextState = setIn(nextState, ['activeItemType'], payload.activeItemType);

	return nextState;
}


/**
 * Updates active element data with the information sent.
 * @param {!object} state
 * @return {object}
 * @review
 */
function clearActiveItemReducer(state) {
	let nextState = state;
	nextState = setIn(nextState, ['activeItemId'], null);
	nextState = setIn(nextState, ['activeItemType'], null);

	return nextState;
}

/**
 * Updates drop target element with the information sent.
 * @param {!object} state
 * @param {!object} payload
 * @param {string} payload.dropTargetBorder
 * @param {string} payload.dropTargetItemId
 * @param {string} payload.dropTargetItemType
 * @return {object}
 * @review
 */
function updateDropTargetReducer(state, payload) {
	let nextState = state;

	nextState = setIn(nextState, ['dropTargetBorder'], payload.dropTargetBorder);
	nextState = setIn(nextState, ['dropTargetItemId'], payload.dropTargetItemId);
	nextState = setIn(nextState, ['dropTargetItemType'], payload.dropTargetItemType);

	return nextState;
}


/**
 * Updates drop target element with the information sent.
 * @param {!object} state
 * @return {object}
 * @review
 */
function clearDropTargetReducer(state) {
	let nextState = state;

	nextState = setIn(nextState, ['dropTargetBorder'], null);
	nextState = setIn(nextState, ['dropTargetItemId'], null);
	nextState = setIn(nextState, ['dropTargetItemType'], null);

	return nextState;
}

/**
 * Updates hovered element data with the information sent.
 * @param {!object} state
 * @param {!object} payload
 * @param {string} payload.hoveredItemId
 * @param {string} payload.hoveredItemType
 * @return {object}
 * @review
 */
function updateHoveredItemReducer(state, payload) {
	let nextState = state;

	nextState = setIn(nextState, ['hoveredItemId'], payload.hoveredItemId);
	nextState = setIn(nextState, ['hoveredItemType'], payload.hoveredItemType);

	return nextState;
}

/**
 * Updates hovered element data with the information sent.
 * @param {!object} state
 * @param {!object} payload
 * @param {string} payload.hoveredItemId
 * @param {string} payload.hoveredItemType
 * @return {object}
 * @review
 */
function updateHoveredItemReducer(state, payload) {
	let nextState = state;

	nextState = setIn(nextState, ['hoveredItemId'], payload.hoveredItemId);
	nextState = setIn(nextState, ['hoveredItemType'], payload.hoveredItemType);

	return nextState;
}

export {
	clearActiveItemReducer,
	clearDropTargetReducer,
	updateActiveItemReducer,
	updateDropTargetReducer,
	updateHoveredItemReducer
};