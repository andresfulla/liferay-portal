import {addFragmentEntryLinkReducer, moveFragmentEntryLinkReducer, removeFragmentEntryLinkReducer, updateEditableValueReducer, updateFragmentEntryLinkConfigReducer} from './fragments.es';
import {addPortletReducer} from './portlets.es';
import {addSectionReducer, moveSectionReducer, removeSectionReducer, updateSectionConfigReducer} from './sections.es';
import {hideFragmentsEditorSidebarReducer, toggleFragmentsEditorSidebarReducer} from './sidebar.es';
import {hideMappingDialogReducer, hideMappingTypeDialogReducer, openAssetTypeDialogReducer, openMappingFieldsDialogReducer, selectMappeableTypeReducer} from './dialogs.es';
import {languageIdReducer, translationStatusReducer} from './translations.es';
import {saveChangesReducer, saveChangesStatusReducer} from './changes.es';
import {clearDropTargetReducer, clearActiveItemReducer, updateActiveItemReducer, updateDropTargetReducer, updateHoveredItemReducer} from './placeholders.es';
import {createExperienceReducer, endCreateExperience, selectExperienceReducer, startCreateExperience} from './experiences.es';
import * as actions from '../actions/actions.es';

function reducer(state, actionType, payload = {}) {
	let nextState = state;
	
	try {
		switch(actionType) {
			case actions.ADD_FRAGMENT_ENTRY_LINK:
				nextState = addFragmentEntryLinkReducer(nextState, payload);
				nextState = translationStatusReducer(nextState, payload);
			break;
			
			case actions.ADD_PORTLET:
				nextState = addPortletReducer(nextState, payload);
			break;
			
			case actions.ADD_SECTION:
				nextState = addSectionReducer(nextState, payload);
			break;
			
			case actions.HIDE_SIDEBAR:
				nextState = hideFragmentsEditorSidebarReducer(nextState, payload);
			break;
			
			case actions.HIDE_MAPPING_DIALOG:
				nextState = hideMappingDialogReducer(nextState, payload);
			break;
			
			case actions.HIDE_MAPPING_TYPE_DIALOG:
				nextState = hideMappingTypeDialogReducer(nextState, payload);
			break;
			
			case actions.CHANGE_LANGUAGE_ID:
				nextState = languageIdReducer(nextState, payload);
			break;
			
			case actions.MOVE_FRAGMENT_ENTRY_LINK:
				nextState = moveFragmentEntryLinkReducer(nextState, payload);
			break;
			
			case actions.MOVE_SECTION:
				nextState = moveSectionReducer(nextState, payload);
			break;
			
			case actions.OPEN_ASSET_TYPE_DIALOG:
				nextState = openAssetTypeDialogReducer(nextState, payload);
			break;
			
			case actions.OPEN_MAPPING_FIELDS_DIALOG:
				nextState = openMappingFieldsDialogReducer(nextState, payload);
			break;
			
			case actions.REMOVE_FRAGMENT_ENTRY_LINK:
				nextState = removeFragmentEntryLinkReducer(nextState, payload);
				nextState = translationStatusReducer(nextState, payload);
			break;
			
			case actions.REMOVE_SECTION:
				nextState = removeSectionReducer(nextState, payload);
			break;
			
			case actions.UPDATE_LAST_SAVE_DATE:
				nextState = saveChangesReducer(nextState, payload);
			break;
			case actions.UPDATE_SAVING_CHANGES_STATUS:
				nextState = saveChangesStatusReducer(nextState, payload);
			break;
			
			case actions.SELECT_MAPPEABLE_TYPE:
				nextState = selectMappeableTypeReducer(nextState, payload);
			break;
			
			case actions.SELECT_EXPERIENCE:
				nextState = selectExperienceReducer(nextState, payload);
				nextState = translationStatusReducer(nextState, payload);
			break;
			
			case actions.CREATE_EXPERIENCE:
				nextState = createExperienceReducer(nextState, payload);
				nextState = translationStatusReducer(nextState, payload);
			break;
			
			case actions.END_CREATE_EXPERIENCE:
				nextState = endCreateExperience(nextState, payload);
			break;
			
			case actions.START_CREATE_EXPERIENCE:
				nextState = startCreateExperience(nextState, payload);
			break;
			
			case actions.TOGGLE_SIDEBAR:
				nextState = toggleFragmentsEditorSidebarReducer(nextState, payload);
			break;
			
			case actions.UPDATE_TRANSLATION_STATUS:
				nextState = translationStatusReducer(nextState, payload);
			break;
			case actions.CLEAR_ACTIVE_ITEM:
				nextState = clearActiveItemReducer(nextState, payload);
			break;
			
			case actions.UPDATE_ACTIVE_ITEM:
				nextState = updateActiveItemReducer(nextState, payload);
			break;
			
			case actions.CLEAR_DROP_TARGET:
				nextState = clearDropTargetReducer(nextState, payload);
			break;
			
			case actions.UPDATE_DROP_TARGET:
				nextState = updateDropTargetReducer(nextState, payload);
			break;
			
			case actions.UPDATE_EDITABLE_VALUE:
				nextState = updateEditableValueReducer(nextState, payload);
			break;
			
			case actions.UPDATE_HOVERED_ITEM:
				nextState = updateHoveredItemReducer(nextState, payload);
			break;

			case actions.CLEAR_HOVERED_ITEM:
				nextState = updateHoveredItemReducer(nextState, payload);
			break;
			
			case actions.UPDATE_SECTION_CONFIG:
				nextState = updateSectionConfigReducer(nextState, payload);
			break;
			
		}
	} catch(e) {
		console.error(e);
		nextState = state;
	}
	return nextState;
}

export {reducer};
export default reducer;