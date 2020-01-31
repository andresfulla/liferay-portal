/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';

import BasicInformation from './components/BasicInformation.es';
import TotalCount from './components/TotalCount.es';
import APIService from './util/APIService.es';

export default function({context, props}) {
	const {endpoints, namespace, page} = context;
	const {authorName, publishDate, title} = props;

	const {getAnalyticsReportsTotalViewsURL} = endpoints;

	const api = APIService({
		endpoints: {
			getAnalyticsReportsTotalViewsURL
		},
		namespace,
		page
	});

	function _handleTotalViews() {
		return api.getTotalViews().then(response => {
			return response.analyticsReportsTotalViews;
		});
	}

	return (
		<div className="p-3">
			<BasicInformation
				authorName={authorName}
				publishDate={publishDate}
				title={title}
			/>

			<TotalCount
				dataProvider={_handleTotalViews}
				label={Liferay.Util.sub(Liferay.Language.get('total-views'))}
			/>
		</div>
	);
}
