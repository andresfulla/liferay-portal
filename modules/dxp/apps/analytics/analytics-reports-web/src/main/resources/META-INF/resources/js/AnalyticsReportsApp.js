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

import {ClayButtonWithIcon} from '@clayui/button';
import React from 'react';

import BasicInformation from './components/BasicInformation';
import Chart from './components/Chart';
import Hint from './components/Hint';
import TotalCount from './components/TotalCount';
import TrafficSources from './components/TrafficSources';
import APIService from './utils/APIService';
import {numberFormat} from './utils/numberFormat';

export default function({context, props}) {
	const {languageTag, namespace, page} = context;
	const {defaultTimeSpanKey, timeSpans} = context;
	const {authorName, publishDate, title} = props;

	const {
		getAnalyticsReportsHistoricalReadsURL,
		getAnalyticsReportsHistoricalViewsURL,
		getAnalyticsReportsTotalReadsURL,
		getAnalyticsReportsTotalViewsURL,
	} = context.endpoints;

	const api = APIService({
		endpoints: {
			getAnalyticsReportsHistoricalReadsURL,
			getAnalyticsReportsHistoricalViewsURL,
			getAnalyticsReportsTotalReadsURL,
			getAnalyticsReportsTotalViewsURL,
		},
		namespace,
		page,
	});

	return (
		<Navigation
			api={api}
			authorName={authorName}
			defaultTimeSpanKey={defaultTimeSpanKey}
			languageTag={languageTag}
			pagePublishDate={publishDate}
			pageTitle={title}
			timeSpanOptions={timeSpans}
		/>
	);
}

function Navigation({
	api,
	authorName,
	defaultTimeSpanKey,
	languageTag,
	pagePublishDate,
	pageTitle,
	timeSpanOptions,
}) {
	const [currentPage, setCurrentPage] = React.useState({view: 'main'});

	function handleTrafficSourceClick(trafficSourceName) {
		api.getTrafficSourceDetails(trafficSourceName).then(
			trafficSourceData => {
				setCurrentPage({
					data: trafficSourceData,
					view: 'traffic-source-detail',
				});
			}
		);
	}

	const {getHistoricalReads, getHistoricalViews} = api;

	function _handleTotalReads() {
		return api.getTotalReads().then(response => {
			return numberFormat(
				languageTag,
				response.analyticsReportsTotalReads
			);
		});
	}

	function _handleTotalViews() {
		return api.getTotalViews().then(response => {
			return numberFormat(
				languageTag,
				response.analyticsReportsTotalViews
			);
		});
	}

	return (
		<div className="overflow-hidden p-3">
			{currentPage.view === 'main' && (
				<>
					<BasicInformation
						authorName={authorName}
						languageTag={languageTag}
						publishDate={pagePublishDate}
						title={pageTitle}
					/>

					<h5 className="mt-4 sheet-subtitle text-secondary">
						{Liferay.Language.get('views-and-reads')}
					</h5>

					<TotalCount
						className="mt-2"
						dataProvider={_handleTotalViews}
						label={Liferay.Util.sub(
							Liferay.Language.get('total-views')
						)}
						popoverHeader={Liferay.Language.get('total-views')}
						popoverMessage={Liferay.Language.get(
							'this-number-refers-to-the-total-number-of-views-since-the-content-was-published'
						)}
					/>

					<TotalCount
						className="mb-3 mt-2"
						dataProvider={_handleTotalReads}
						label={Liferay.Util.sub(
							Liferay.Language.get('total-reads')
						)}
						popoverHeader={Liferay.Language.get('total-reads')}
						popoverMessage={Liferay.Language.get(
							'this-number-refers-to-the-total-number-of-reads-since-the-content-was-published'
						)}
					/>

					<Chart
						dataProviders={[getHistoricalViews, getHistoricalReads]}
						defaultTimeSpanOption={defaultTimeSpanKey}
						languageTag={languageTag}
						publishDate={pagePublishDate}
						timeSpanOptions={timeSpanOptions}
					/>

					<h5 className="mt-4 sheet-subtitle text-secondary">
						{Liferay.Language.get('traffic-sources')}
						<Hint
							message={Liferay.Language.get(
								'traffic-sources-help'
							)}
							title={Liferay.Language.get('traffic-sources')}
						/>
					</h5>

					<TrafficSources
						dataProvider={api.getTrafficSources}
						languageTag={languageTag}
						onTrafficSourceClick={handleTrafficSourceClick}
					/>
				</>
			)}

			{currentPage.view === 'traffic-source-detail' && (
				<>
					<ClayButtonWithIcon
						displayType="secondary"
						onClick={() => setCurrentPage({view: 'main'})}
						small="true"
						symbol="angle-left"
					/>
					<h1>{currentPage.data.title}</h1>
				</>
			)}
		</div>
	);
}
