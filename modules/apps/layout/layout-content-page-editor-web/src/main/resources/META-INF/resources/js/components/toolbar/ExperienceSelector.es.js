import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './ExperienceSelector.soy';
import {CREATE_EXPERIENCE, END_CREATE_EXPERIENCE, SELECT_EXPERIENCE, START_CREATE_EXPERIENCE} from '../../actions/actions.es';
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
	 * @private
	 * @review
	 * @memberof ExperienceSelector
	 */
	_closeDropdown() {
		this.openDropdown = false;
	}

	/**
	 * @private
	 * @review
	 * @memberof ExperienceSelector
	 */
	_closeModal() {
		this.store.dispatchAction(
			END_CREATE_EXPERIENCE
		);
	}

	/**
	 * @private
	 * @review
	 * @param {!string} experienceLabel
	 * @param {!string} segmentId
	 * @memberof ExperienceSelector
	 */
	_createExperience(experienceLabel, segmentId) {
		this.store.dispatchAction(
			CREATE_EXPERIENCE,
			{
				experienceLabel,
				segmentId
			}
		).then(dispatchAction => {
			dispatchAction(
				END_CREATE_EXPERIENCE
			)
		});
	}

	/**
	 * @private
	 * @review
	 * @memberof ExperienceSelector
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
	 * @private
	 * @review
	 * @memberof ExperienceSelector
	 */
	_handleDropdownButtonClick() {
		this._toggleDropdown();
	}

	/**
	 * @private
	 * @review
	 * @memberof ExperienceSelector
	 */
	_handleDropdownFocus() {
		cancelAnimationFrame(this.willToggleDropdownId);
	}

	/**
	 * @private
	 * @review
	 * @param {Event} event
	 * @memberof ExperienceSelector
	 */
	_handleExperienceClick(event) {
		const experienceId = event.delegateTarget.dataset.experienceId;
		this._selectExperience(experienceId);
	}

	/**
	 *
	 * @review
	 * @param {Event} event
	 * @memberof ExperienceSelector
	 */
	_handleFormSubmit(event) {
		event.preventDefault();

		this._createExperience(
			this.refs.modal.refs.experienceName.value,
			this.refs.modal.refs.experienceSegmentId.value
		);
	}

	/**
	 * @private
	 * @review
	 * @memberof ExperienceSelector
	 */
	_openDropdown() {
		this.openDropdown = true;
	}

	/**
	 * @private
	 * @review
	 * @memberof ExperienceSelector
	 */
	_openModal() {
		this.store.dispatchAction(
			START_CREATE_EXPERIENCE
		);
	}

	/**
	 * @private
	 * @review
	 * @param {!string} experienceId
	 * @memberof ExperienceSelector
	 */
	_selectExperience(experienceId) {
		this.store.dispatchAction(
			SELECT_EXPERIENCE,
			{
				experienceId
			}
		).then(dispatch => {
			setTimeout(() => {
				dispatch(
					SELECT_EXPERIENCE,
					{
						experienceId: "36893"
					}
				)
			}, 2000)
		});
	}

	/**
	 * @private
	 * @review
	 * @memberof ExperienceSelector
	 */
	_toggleModal() {
		const modalAction = this.experienceCreation.creatingExperience ?
			this._closeModal :
			this._openModal;

		modalAction.call(this);
	}

	/**
	 * @private
	 * @review
	 * @memberof ExperienceSelector
	 */
	_toggleDropdown() {
		const dropdownAction = this.openDropdown ?
			this._closeDropdown :
			this._openDropdown;

		dropdownAction.call(this);
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