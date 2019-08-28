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

import {render} from 'frontend-js-react-web';
import React from 'react';
import SegmentsExperimentsUtil from './util/index.es';
import SegmentsExperimentsSidebar from './components/SegmentsExperimentsSidebar.es';
import SegmentsExperimentsContext from './context.es';

export default function segmentsExperimentsApp(id, props, context) {
	const rootElement = document.getElementById(id);

	const {page, endpoints} = context;
	const {
		createSegmentsExperimentURL,
		createSegmentsVariantURL,
		deleteSegmentsVariantURL,
		editSegmentsExperimentStatusURL,
		editSegmentsExperimentURL,
		editSegmentsVariantLayoutURL,
		editSegmentsVariantURL,
		runSegmentsExperimentURL
	} = endpoints;

	render(
		<SegmentsExperimentsContext.Provider
			value={{
				editVariantLayoutURL: editSegmentsVariantLayoutURL,
				page,
				segmentsExperimentsUtil: SegmentsExperimentsUtil({
					contentPageEditorNamespace:
						context.contentPageEditorNamespace,
					endpoints: {
						createSegmentsExperimentURL,
						createSegmentsVariantURL,
						deleteSegmentsVariantURL,
						editSegmentsExperimentStatusURL,
						editSegmentsExperimentURL,
						editSegmentsVariantURL,
						runSegmentsExperimentURL
					},
					namespace: context.namespace
				})
			}}
		>
			<SegmentsExperimentsSidebar
				initialGoals={props.segmentsExperimentGoals}
				initialSegmentsExperiences={props.segmentsExperiences}
				initialSegmentsExperiment={props.segmentsExperiment}
				initialSegmentsVariants={props.initialSegmentsVariants}
				initialSelectedSegmentsExperienceId={
					props.selectedSegmentsExperienceId
				}
			/>
		</SegmentsExperimentsContext.Provider>,
		rootElement
	);
}
