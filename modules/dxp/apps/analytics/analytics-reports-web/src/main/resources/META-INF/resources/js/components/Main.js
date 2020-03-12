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

import BasicInformation from './BasicInformation';
import Chart from './Chart';
import Hint from './Hint';
import TotalCount from './TotalCount';
import TrafficSources from './TrafficSources';

export default function Main({authorName, pagePublishDate, pageTitle}) {
	return (
		<div className="p-3">
			<BasicInformation
				authorName={authorName}
				languageTag={languageTag}
				publishDate={pagePublishDate}
				title={pageTitle}
			/>

			<h5 className="mt-4 sheet-subtitle text-secondary">
				{Liferay.Language.get('reads-and-views')}
			</h5>

			<TotalCount
				className="mt-2"
				dataProvider={_handleTotalViews}
				label={Liferay.Util.sub(Liferay.Language.get('total-views'))}
				popoverHeader={Liferay.Language.get('total-views')}
				popoverMessage={Liferay.Language.get(
					'this-number-refers-to-the-total-number-of-views-since-the-content-was-published'
				)}
			/>

			<TotalCount
				className="mb-3 mt-2"
				dataProvider={_handleTotalReads}
				label={Liferay.Util.sub(Liferay.Language.get('total-reads'))}
				popoverHeader={Liferay.Language.get('total-reads')}
				popoverMessage={Liferay.Language.get(
					'this-number-refers-to-the-total-number-of-reads-since-the-content-was-published'
				)}
			/>

			<Chart
				dataProviders={[getHistoricalViews, getHistoricalReads]}
				defaultTimeSpanOption={defaultTimeSpanKey}
				languageTag={languageTag}
				timeSpanOptions={timeSpans}
			/>

			<h5 className="mt-4 sheet-subtitle text-secondary">
				{Liferay.Language.get('traffic-sources')}
				<Hint
					message={Liferay.Language.get(
						'traffic-sources-help-message'
					)}
					title={Liferay.Language.get('traffic-sources')}
				/>
			</h5>

			<TrafficSources
				dataProvider={api.getTrafficSources}
				languageTag={languageTag}
			/>
		</div>
	);
}
