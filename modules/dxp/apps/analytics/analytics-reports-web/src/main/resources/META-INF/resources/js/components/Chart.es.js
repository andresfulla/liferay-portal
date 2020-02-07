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

import {useIsMounted} from 'frontend-js-react-web';
import React from 'react';
import {
	CartesianGrid,
	Legend,
	LineChart,
	Line,
	Tooltip,
	XAxis,
	YAxis
} from 'recharts';

const {useEffect, useMemo, useState} = React;

const AXIS_COLOR = '#6B6C7E';
const SECONDARY_COLOR = '#4B9BFF';
const CHART_SIZE = {height: 180, width: 280};

function keyToTranslatedLabelValue(key) {
	if (key === 'analyticsReportsHistoricalViews') {
		return Liferay.Language.get('views-metric');
	} else {
		return key;
	}
}

function transformDataToDataSet(key, data, previousDataset = {}) {
	const langLabel = keyToTranslatedLabelValue(key);
	const result = {totals: {}, ...previousDataset};

	result.keyList = [key];
	result.totals = {
		...result.totals,
		[key]: data.value
	};
	result.histogram = data.histogram.map(d => ({
		[key]: d.value,
		label: d.key,
		langLabel
	}));

	return result;
}

const generateDateFormatters = key => {
	function formatChartTitle([initialDate, finalDate]) {
		const dateFormatter = (
			date,
			options = {
				day: 'numeric',
				month: 'short',
				year: 'numeric'
			}
		) => Intl.DateTimeFormat([key], options).format(date);

		const equalMonth = initialDate.getMonth() === finalDate.getMonth();
		const equalYear = initialDate.getYear() === finalDate.getYear();

		const initialDateOptions = {
			day: 'numeric',
			month: equalMonth && equalYear ? undefined : 'short',
			year: equalYear ? undefined : 'numeric'
		};

		return `${dateFormatter(
			initialDate,
			initialDateOptions
		)} - ${dateFormatter(finalDate)}`;
	}

	function formatLongDate(value) {
		return Intl.DateTimeFormat([key], {
			dateStyle: 'long'
		}).format(new Date(value));
	}

	function formatNumericDay(value) {
		return Intl.DateTimeFormat([key], {
			day: 'numeric'
		}).format(new Date(value));
	}

	return {
		formatChartTitle,
		formatLongDate,
		formatNumericDay
	};
};

/**
 * Helper to deal with the differnce in language keys for
 * human reading, svg consumption and keys
 *
 * @param {string} [keyLang='']
 * @param {boolean} [lowercase=true]
 * @returns {string}
 */
function keyLangToLanguageTag(keyLang = '', lowercase = true) {
	let langTag = keyLang.replace(/_/g, '-');
	if (lowercase) {
		langTag = langTag.toLowerCase();
	}
	return langTag;
}

function legendFormatterGenerator(totals) {
	return value => (
		<span>
			<span className="text-secondary">
				{keyToTranslatedLabelValue(value)}
			</span>
			<b>{' ' + totals[value]}</b>
		</span>
	);
}

export default function Chart({languageId, dataProviders = []}) {
	const [dataSet, setDataSet] = useState(null);
	const isMounted = useIsMounted();

	useEffect(() => {
		dataProviders.map(getter => {
			getter().then(data => {
				if (isMounted()) {
					Object.keys(data).map(key => {
						const normalizedData = transformDataToDataSet(
							key,
							data[key],
							dataSet
						);

						setDataSet(normalizedData);
					});
				}
			});
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dataProviders]);

	const dateFormatters = useMemo(() => {
		const keyLang = keyLangToLanguageTag(languageId);

		return generateDateFormatters(keyLang);
	}, [languageId]);

	const title = useMemo(() => {
		if (dataSet && dataSet.histogram) {
			const firstDateLabel = dataSet.histogram[0].label;
			const lastDateLabel =
				dataSet.histogram[dataSet.histogram.length - 1].label;

			return dateFormatters.formatChartTitle([
				new Date(firstDateLabel),
				new Date(lastDateLabel)
			]);
		}
	}, [dataSet, dateFormatters]);

	const legendFormatter = dataSet && legendFormatterGenerator(dataSet.totals);

	return dataSet ? (
		<>
			{title && <h4>{title}</h4>}

			<LineChart
				data={dataSet.histogram}
				height={CHART_SIZE.height}
				width={CHART_SIZE.width}
			>
				<Legend
					align={'left'}
					formatter={legendFormatter}
					height={36}
					verticalAlign="top"
				/>

				<CartesianGrid strokeDasharray="0 0" vertical={false} />

				<XAxis
					axisLine={false}
					dataKey="label"
					stroke={AXIS_COLOR}
					tickFormatter={dateFormatters.formatNumericDay}
					tickLine={false}
				/>

				<YAxis
					allowDecimals={false}
					minTickGap={3}
					stroke={AXIS_COLOR}
					tickFormatter={function(value) {
						if (value > 1000) {
							return value / 1000 + 'K';
						}
						return value;
					}}
					tickLine={false}
					width={25}
				/>

				<Tooltip
					formatter={(value, name, {payload}) => {
						return [value, payload.langLabel];
					}}
					labelFormatter={dateFormatters.formatLongDate}
					separator={': '}
				/>

				{dataSet.keyList.map(keyName => {
					return (
						<Line
							activeDot={{r: 6, strokeWidth: 0}}
							dataKey={keyName}
							fill={SECONDARY_COLOR}
							key={keyName}
							stroke={SECONDARY_COLOR}
							strokeWidth={2}
							type="monotone"
						/>
					);
				})}
			</LineChart>
		</>
	) : null;
}
