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
import {ClaySelect} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React from 'react';
import {
	CartesianGrid,
	Legend,
	Line,
	LineChart,
	Tooltip,
	XAxis,
	YAxis
} from 'recharts';

import {useChartState} from '../utils/chartState';
import {numberFormat} from '../utils/numberFormat';
import CustomTooltip from './CustomTooltip';

const {useEffect, useMemo} = React;

const CHART_SIZE = {height: 220, width: 280};

const LAST_24_HOURS = 'last-24-hours';

function keyToTranslatedLabelValue(key) {
	if (key === 'analyticsReportsHistoricalViews') {
		return Liferay.Language.get('views-metric');
	}
	else if (key === 'analyticsReportsHistoricalReads') {
		return Liferay.Language.get('reads-metric');
	}
	else {
		return key;
	}
}

function keyToHexColor(key) {
	if (key === 'analyticsReportsHistoricalViews') {
		return '#4B9BFF';
	}
	else if (key === 'analyticsReportsHistoricalReads') {
		return '#50D2A0';
	}
	else {
		return '#666666';
	}
}

/*
 * If a number is bigger than 1000 it will transform it to kilos
 *
 * 4 => 4
 * 4000 => '4K'
 */
function thousandsToKilosFormater(value) {
	if (value > 1000) {
		return value / 1000 + 'K';
	}

	return value;
}

/*
 * It generates a set of functions used to produce
 * internationalized date related content.
 */
const generateDateFormatters = key => {
	/*
	 * Given 2 date objects it produces a user friendly date interval
	 *
	 * For 'en-US'
	 * [Date, Date] => '16 - Jun 21, 2020'
	 */
	function formatChartTitle([initialDate, finalDate]) {
		const singleDayDateRange =
			finalDate - initialDate <= 1000 * 60 * 60 * 24;

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

		if (singleDayDateRange) {
			return dateFormatter(finalDate);
		}

		return `${dateFormatter(
			initialDate,
			initialDateOptions
		)} - ${dateFormatter(finalDate)}`;
	}

	/*
	 * Given a date like string it produces a internationalized long date
	 *
	 * For 'en-US'
	 * String => 'June 16, 2020'
	 */
	function formatLongDate(value) {
		return Intl.DateTimeFormat([key]).format(new Date(value));
	}

	/*
	 * Given a date like string produces the day of the month
	 *
	 * For 'en-US'
	 * String => '16'
	 */
	function formatNumericDay(value) {
		return Intl.DateTimeFormat([key], {
			day: 'numeric'
		}).format(new Date(value));
	}

	/*
	 * Given a date like string produces the hour of the day
	 *
	 * For 'en-US'
	 * String => '04 AM'
	 */
	function formatNumericHour(value) {
		return Intl.DateTimeFormat([key], {
			hour: 'numeric'
		}).format(new Date(value));
	}

	return {
		formatChartTitle,
		formatLongDate,
		formatNumericDay,
		formatNumericHour
	};
};

function legendFormatterGenerator(totals, languageTag) {
	return value => (
		<span>
			<span className="text-secondary">
				{keyToTranslatedLabelValue(value)}
			</span>
			<b>{' ' + numberFormat(languageTag, totals[value])}</b>
		</span>
	);
}

export default function Chart({
	dataProviders = [],
	defaultTimeSpanOption,
	languageTag,
	timeSpanOptions
}) {
	const {actions, state: chartState} = useChartState({
		defaultTimeSpanOption
	});
	const isMounted = useIsMounted();

	useEffect(() => {
		let gone = false;

		actions.setLoading();

		dataProviders.map(getter => {
			getter({
				timeSpanKey: chartState.timeSpanOption,
				timeSpanOffset: chartState.timeSpanOffset
			}).then(data => {
				if (!gone) {
					if (isMounted()) {
						Object.keys(data).map(key => {
							actions.addDataSetItem({
								dataSetItem: data[key],
								key
							});
						});
					}
				}
			});
		});

		return () => {
			gone = true;
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [chartState.timeSpanOption, chartState.timeSpanOffset]);

	const dateFormatters = useMemo(() => generateDateFormatters(languageTag), [
		languageTag
	]);

	const {dataSet} = chartState;

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

	const handleTimeSpanChange = event => {
		const {value} = event.target;

		actions.changeTimeSpanOption({key: value});
	};
	const handlePreviousTimeSpanClick = () => {
		actions.previousTimeSpan();
	};
	const handleNextTimeSpanClick = () => {
		actions.nextTimeSpan();
	};

	const legendFormatter =
		dataSet && legendFormatterGenerator(dataSet.totals, languageTag);

	const disabledNextTimeSpan = chartState.timeSpanOffset === 0;

	const xAxisFormatter =
		chartState.timeSpanOption === LAST_24_HOURS
			? dateFormatters.formatNumericHour
			: dateFormatters.formatNumericDay;

	return (
		<>
			{timeSpanOptions.length && (
				<div className="d-flex mb-3">
					<ClaySelect
						aria-label={Liferay.Language.get('select-date-range')}
						onChange={handleTimeSpanChange}
						value={chartState.timeSpanOption}
					>
						{timeSpanOptions.map(option => {
							return (
								<ClaySelect.Option
									key={option.key}
									label={option.label}
									value={option.key}
								/>
							);
						})}
					</ClaySelect>

					<div className="d-flex ml-2">
						<ClayButtonWithIcon
							ariaLabel={Liferay.Language.get('previous-period')}
							className="mr-1"
							displayType="secondary"
							onClick={handlePreviousTimeSpanClick}
							symbol="angle-left"
						/>
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get('next-period')}
							disabled={disabledNextTimeSpan}
							displayType="secondary"
							onClick={handleNextTimeSpanClick}
							symbol="angle-right"
						/>
					</div>
				</div>
			)}

			{dataSet ? (
				<div
					className="position-relative"
					style={{
						opacity: chartState.loading ? 0.8 : 1,
						pointerEvents: chartState.loading ? 'none' : 'all'
					}}
				>
					{chartState.loading && (
						<ClayLoadingIndicator
							small
							style={{
								left: 0,
								position: 'absolute',
								right: 0,
								top: 'calc(50% - 1rem)'
							}}
						/>
					)}

					{title && <h4>{title}</h4>}

					<div className="mt-3">
						<LineChart
							data={dataSet.histogram}
							height={CHART_SIZE.height}
							width={CHART_SIZE.width}
						>
							<Legend
								formatter={legendFormatter}
								iconSize={'10px'}
								iconType="circle"
								layout="vertical"
								verticalAlign="top"
								wrapperStyle={{left: 0, paddingBottom: '1rem'}}
							/>

							<CartesianGrid
								stroke="#E7E7ED"
								strokeDasharray="0 0"
								vertical={true}
								verticalPoints={[CHART_SIZE.width - 5]}
							/>

							<XAxis
								axisLine={{
									stroke: '#E7E7ED'
								}}
								dataKey="label"
								tickFormatter={xAxisFormatter}
								tickLine={false}
							/>

							<YAxis
								allowDecimals={false}
								axisLine={{
									stroke: '#E7E7ED'
								}}
								minTickGap={3}
								tickFormatter={thousandsToKilosFormater}
								tickLine={false}
								width={40}
							/>

							<Tooltip
								content={<CustomTooltip />}
								formatter={(value, name) => {
									return [
										numberFormat(languageTag, value),
										keyToTranslatedLabelValue(name)
									];
								}}
								labelFormatter={dateFormatters.formatLongDate}
								separator={': '}
							/>

							{dataSet.keyList.map(keyName => {
								const color = keyToHexColor(keyName);

								return (
									<Line
										activeDot={{r: 5, strokeWidth: 0}}
										dataKey={keyName}
										fill={color}
										key={keyName}
										stroke={color}
										strokeWidth={2}
										type="monotone"
									/>
								);
							})}
						</LineChart>
					</div>
				</div>
			) : null}
		</>
	);
}

Chart.propTypes = {
	dataProviders: PropTypes.arrayOf(PropTypes.func).isRequired,
	defaultTimeSpanKey: PropTypes.string.isRequired,
	languageTag: PropTypes.string.isRequired,
	timeSpans: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired
		})
	).isRequired
};
