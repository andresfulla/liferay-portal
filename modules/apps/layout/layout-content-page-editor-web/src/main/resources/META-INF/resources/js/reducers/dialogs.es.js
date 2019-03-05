import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * @param {!object} state
 * @return {object}
 * @review
 */
function openAssetTypeDialogReducer(state) {
	let nextState = state;

	nextState = setIn(nextState, ['selectMappingTypeDialogVisible'], true);

	return nextState;
}

/**
 * @param {!object} state
 * @param {!object} payload
 * @param {!string} payload.editableId
 * @param {!string} payload.editableType
 * @param {!string} payload.mappedFieldId
 * @return {object}
 * @review
 */
function openMappingFieldsDialogReducer(state, payload) {
	let nextState = state;

	nextState = setIn(nextState, ['selectMappingDialogEditableId'], payload.editableId);
	nextState = setIn(nextState, ['selectMappingDialogEditableType'], payload.editableType);
	nextState = setIn(nextState, ['selectMappingDialogMappedFieldId'], payload.mappedFieldId);

	nextState = setIn(
		nextState,
		['selectMappingDialogFragmentEntryLinkId'],
		payload.fragmentEntryLinkId
	);

	if (nextState.selectedMappingTypes && nextState.selectedMappingTypes.type) {
		nextState = setIn(nextState, ['selectMappingDialogVisible'], true);
	}
	else {
		nextState = setIn(nextState, ['selectMappingTypeDialogVisible'], true);
	}

	return nextState;
}

/**
 * @param {!object} state
 * @param {!object} payload
 * @param {!string} payload.selectedMappingSubtypeId
 * @param {!string} payload.selectedMappingTypeId
 * @return {object}
 * @review
 */
function selectMappeableTypeReducer(state, payload) {
	return new Promise(
		resolve => {
			let nextState = state;

			_selectMappingType(
				nextState.classPK,
				nextState.portletNamespace,
				payload.selectedMappingSubtypeId,
				payload.selectedMappingTypeId,
				nextState.updateLayoutPageTemplateEntryAssetTypeURL
			)
				.then(
					() => {
						nextState = setIn(
							nextState,
							['selectedMappingTypes'],
							payload.mappingTypes
						);

						if (
							nextState.selectMappingDialogFragmentEntryLinkId &&
							nextState.selectMappingDialogEditableId
						) {
							nextState = setIn(nextState, ['selectMappingDialogVisible'], true);
						}

						resolve(nextState);
					}
				)
				.catch(
					() => {
						resolve(nextState);
					}
				);
		}
	);
}

/**
 * @param {!object} state
 * @return {object}
 * @review
 */
function hideMappingDialogReducer(state) {
	let nextState = state;

	nextState = setIn(nextState, ['selectMappingDialogVisible'], false);

	return nextState;
}

/**
 * @param {!object} state
 * @return {object}
 * @review
 */
function hideMappingTypeDialogReducer(state) {
	let nextState = state;

	nextState = setIn(nextState, ['selectMappingTypeDialogVisible'], false);

	return nextState;
}

/**
 * @param {string} classPK
 * @param {string} portletNamespace
 * @param {string} selectedMappingSubtypeId
 * @param {string} selectedMappingTypeId
 * @param {string} updateLayoutPageTemplateEntryAssetTypeURL
 * @return {object}
 * @review
 */
function _selectMappingType(
	classPK,
	portletNamespace,
	selectedMappingSubtypeId,
	selectedMappingTypeId,
	updateLayoutPageTemplateEntryAssetTypeURL
) {
	const formData = new FormData();

	formData.append(`${portletNamespace}classTypeId`, selectedMappingSubtypeId);
	formData.append(`${portletNamespace}classNameId`, selectedMappingTypeId);
	formData.append(`${portletNamespace}classPK`, classPK);

	return fetch(
		updateLayoutPageTemplateEntryAssetTypeURL,
		{
			body: formData,
			credentials: 'include',
			method: 'POST'
		}
	).then(
		response => response.json()
	);
}

export {
	hideMappingDialogReducer,
	hideMappingTypeDialogReducer,
	openAssetTypeDialogReducer,
	openMappingFieldsDialogReducer,
	selectMappeableTypeReducer
};