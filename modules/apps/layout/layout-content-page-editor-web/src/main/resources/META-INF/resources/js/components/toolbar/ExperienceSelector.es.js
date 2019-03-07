import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';
import 'clay-button';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './ExperienceSelector.soy';
import {CREATE_EXPERIENCE, DELETE_EXPERIENCE, END_CREATE_EXPERIENCE, SELECT_EXPERIENCE, START_CREATE_EXPERIENCE} from '../../actions/actions.es';
import 'frontend-js-web/liferay/compat/modal/Modal.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';

/**
 * Tells if a priority an `obj2`
 * has higher, equal or lower priority
 * than `obj1`
 *
 * @review
 * @param {object} obj1
 * @param {object} obj2
 * @returns {1|0|-1}
 */
function comparePriority(obj1, obj2) {
	let result = 0;

	if (obj1.priority > obj2.priority) {
		result = -1;
	}

	if (obj1.priority < obj2.priority) {
		result = 1;
	}

	return result;
}

/**
 * Searchs for a segment based on its Id
 * and returns its label
 *
 * @review
 * @param {Array} segments
 * @param {string} segmentId
 * @returns {string|undefined}
 */
function findSegmentLabelById(segments, segmentId) {
	const mostWantedSegment = segments.find(
		segment => segment.segmentId === segmentId
	);

	return mostWantedSegment && mostWantedSegment.segmentLabel;
}

/**
 * ExperienceSelector
 */
class ExperienceSelector extends Component {

	/**
	 * Transforms `availableSegments` and `availableExperiences` objects into arrays
	 * Adds `activeExperienceLabel` to the component state
	 *
	 * @inheritDoc
	 * @review
	 */
	prepareStateForRender(state) {
		const availableExperiencesArray = Object.values(state.availableExperiences || [])
			.sort(comparePriority)
			.map(
				experience => {
					const label = findSegmentLabelById(
						Object.values(state.availableSegments),
						experience.segmentId
					);

					const updatedExperience = setIn(
						experience,
						['segmentLabel'],
						label
					);

					return updatedExperience;
				}
			);

		const selectedExperienceId = state.experienceId || state.defaultExperienceId;

		const activeExperience = availableExperiencesArray.find(
			experience => experience.experienceId === selectedExperienceId
		);

		const availableSegments = Object.values(state.availableSegments || [])
			.filter(
				segment => segment.segmentId !== state.defaultSegmentId
			);

		const innerState = Object.assign(
			{},
			state,
			{
				activeExperienceLabel: activeExperience && activeExperience.experienceLabel,
				availableExperiences: availableExperiencesArray,
				availableSegments,
				experienceId: selectedExperienceId
			}
		);

		return innerState;
	}

	/**
	 * Closes dropdown
	 * @memberof ExperienceSelector
	 * @private
	 * @review
	 */
	_closeDropdown() {
		this.openDropdown = false;
	}

	/**
	 * Closes modal
	 * @memberof ExperienceSelector
	 * @private
	 * @review
	 */
	_closeModal() {
		this.store.dispatchAction(
			END_CREATE_EXPERIENCE
		);
	}

	/**
	 * Dispatches action to create an experience
	 * @memberof ExperienceSelector
	 * @param {!string} experienceLabel
	 * @param {!string} segmentId
	 * @private
	 * @review
	 */
	_createExperience(experienceLabel, segmentId) {
		this.store.dispatchAction(
			CREATE_EXPERIENCE,
			{
				experienceLabel,
				segmentId
			}
		).dispatchAction(
			END_CREATE_EXPERIENCE
		);
	}

	/**
	 * Dispatches action to delete an experience
	 * @memberof ExperienceSelector
	 * @param {!string} experienceId
	 * @private
	 * @review
	 */
	_deleteExperience(experienceId) {
		this.store.dispatchAction(
			DELETE_EXPERIENCE,
			{
				experienceId
			}
		);
	}

	/**
	 * Callback that is executed on delete button click
	 * @memberof ExperienceSelector
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleDeleteButtonClick(event) {
		const confirmed = confirm(
			Liferay.Language.get('do-you-want-to-delete-this-experience')
		);
		const experienceId = event.currentTarget.getAttribute('data-experienceId');

		if (confirmed) {
			this._deleteExperience(
				experienceId
			);
		}
	}

	/**
	 * Callback that is executed on dropdown blur
	 * @memberof ExperienceSelector
	 * @private
	 * @review
	 */
	_handleDropdownBlur() {
		cancelAnimationFrame(this.willToggleDropdownId);

		this.willToggleDropdownId = requestAnimationFrame(
			() => {
				this._closeDropdown();
			}
		);
	}

	/**
	 * Callback that is executed on dropdown button click
	 * @memberof ExperienceSelector
	 * @private
	 * @review
	 */
	_handleDropdownButtonClick() {
		this._toggleDropdown();
	}

	/**
	 * Callback that is executed on dropdown focus
	 * @memberof ExperienceSelector
	 * @private
	 * @review
	 */
	_handleDropdownFocus() {
		cancelAnimationFrame(this.willToggleDropdownId);
	}

	/**
	 * Callback that is executed on experience click
	 * @param {Event} event
	 * @memberof ExperienceSelector
	 * @private
	 * @review
	 */
	_handleExperienceClick(event) {
		const experienceId = event.delegateTarget.dataset.experienceId;
		this._selectExperience(experienceId);
	}

	/**
	 * Submits a form
	 * @param {Event} event
	 * @memberof ExperienceSelector
	 * @private
	 * @review
	 */
	_handleFormSubmit(event) {
		event.preventDefault();

		this._createExperience(
			this.refs.modal.refs.experienceName.value,
			this.refs.modal.refs.experienceSegmentId.value
		);
	}

	/**
	 * Opens dropdown
	 * @memberof ExperienceSelector
	 * @private
	 * @review
	 */
	_openDropdown() {
		this.openDropdown = true;
	}

	/**
	 * Opens modal
	 * @memberof ExperienceSelector
	 * @private
	 * @review
	 */
	_openModal() {
		this.store.dispatchAction(
			START_CREATE_EXPERIENCE
		);
	}

	/**
	 * Dispatches action to select an experience
	 * @memberof ExperienceSelector
	 * @param {!string} experienceId
	 * @private
	 * @review
	 */
	_selectExperience(experienceId) {
		this.store.dispatchAction(
			SELECT_EXPERIENCE,
			{
				experienceId
			}
		);
	}

	/**
	 * Toggles dropdown
	 * @memberof ExperienceSelector
	 * @private
	 * @review
	 */
	_toggleDropdown() {
		const dropdownAction = this.openDropdown ?
			this._closeDropdown :
			this._openDropdown;

		dropdownAction.call(this);
	}

	/**
	 * Toggles modal
	 * @memberof ExperienceSelector
	 * @private
	 * @review
	 */
	_toggleModal() {
		const modalAction = this.experienceCreation.creatingExperience ?
			this._closeModal :
			this._openModal;

		modalAction.call(this);
	}

}

ExperienceSelector.STATE = {
	openDropdown: Config.bool().internal().value(false),
	segmentId: Config.string().internal()
};

const ConnectedExperienceSelector = getConnectedComponent(
	ExperienceSelector,
	[
		'classPK',
		'availableExperiences',
		'experienceId',
		'defaultSegmentId'
	]
);

Soy.register(ConnectedExperienceSelector, templates);

export {ConnectedExperienceSelector};
export default ConnectedExperienceSelector;