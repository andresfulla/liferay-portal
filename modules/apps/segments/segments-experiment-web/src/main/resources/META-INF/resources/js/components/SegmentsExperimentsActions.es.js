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

import React, {useState} from 'react';
import PropTypes from 'prop-types';
import ClayButton from '@clayui/button';
import {ReviewExperimentModal} from './ReviewExperimentModal.es';
import {SegmentsExperimentType, SegmentsVariantType} from '../types.es';
import {
	STATUS_DRAFT,
	STATUS_PAUSED,
	STATUS_RUNNING,
	STATUS_TERMINATED,
	STATUS_FINISHED_WINNER
} from '../util/statuses.es';

function _experimentReady(experiment, variants) {
	if (variants.length <= 1) return false;
	if (experiment.goal.value === 'click' && !experiment.goal.target)
		return false;
	return true;
}

function SegmentsExperimentsActions({
	onEditSegmentsExperimentStatus,
	onExperimentDiscard,
	onWinnerExperiencePublishing,
	onRunExperiment,
	segmentsExperiment,
	variants
}) {
	const [reviewModalVisible, setReviewModalVisible] = useState(false);

	const readyToRun = _experimentReady(segmentsExperiment, variants);

	return (
		<>
			{segmentsExperiment.status.value === STATUS_DRAFT && (
				<ClayButton
					className="w-100"
					disabled={!readyToRun}
					onClick={() => setReviewModalVisible(true)}
				>
					{Liferay.Language.get('review-and-run-test')}
				</ClayButton>
			)}

			{segmentsExperiment.status.value === STATUS_RUNNING && (
				<>
					<ClayButton className="w-100 mb-3" disabled>
						{Liferay.Language.get('view-data-in-analytics-cloud')}
					</ClayButton>
					<ClayButton
						className="w-100 mb-3"
						displayType="secondary"
						onClick={() =>
							onEditSegmentsExperimentStatus(
								segmentsExperiment,
								STATUS_PAUSED
							)
						}
					>
						{Liferay.Language.get('pause-test')}
					</ClayButton>
					<ClayButton
						className="w-100"
						displayType="secondary"
						onClick={() => {
							const confirmed = confirm(
								Liferay.Language.get(
									'are-you-sure-you-want-to-delete-this'
								)
							);

							if (confirmed)
								onEditSegmentsExperimentStatus(
									segmentsExperiment,
									STATUS_TERMINATED
								);
						}}
					>
						{Liferay.Language.get('terminate-test')}
					</ClayButton>
				</>
			)}

			{segmentsExperiment.status.value === STATUS_PAUSED && (
				<>
					<ClayButton
						className="w-100"
						onClick={() =>
							onEditSegmentsExperimentStatus(
								segmentsExperiment,
								STATUS_RUNNING
							)
						}
					>
						{Liferay.Language.get('restart-test')}
					</ClayButton>
				</>
			)}

			{segmentsExperiment.status.value === STATUS_FINISHED_WINNER && (
				<>
					<ClayButton
						className="w-100 mb-3"
						onClick={onWinnerExperiencePublishing}
					>
						{Liferay.Language.get('publish-winner-as-experience')}
					</ClayButton>

					<ClayButton
						className="w-100 mb-3"
						displayType="secondary"
						onClick={onExperimentDiscard}
					>
						{Liferay.Language.get('discard-experiment')}
					</ClayButton>
				</>
			)}

			{reviewModalVisible && (
				<ReviewExperimentModal
					onRun={onRunExperiment}
					setVisible={setReviewModalVisible}
					variants={variants}
					visible={reviewModalVisible}
				/>
			)}
		</>
	);
}

SegmentsExperimentsActions.propTypes = {
	onEditSegmentsExperimentStatus: PropTypes.func.isRequired,
	onExperimentDiscard: PropTypes.func.isRequired,
	onRunExperiment: PropTypes.func.isRequired,
	onWinnerExperiencePublishing: PropTypes.func.isRequired,
	segmentsExperiment: SegmentsExperimentType,
	variants: PropTypes.arrayOf(SegmentsVariantType)
};

export default SegmentsExperimentsActions;
