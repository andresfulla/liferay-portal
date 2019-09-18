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

package com.liferay.segments.asah.connector.internal.portlet.action;

import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientFactory;
import com.liferay.segments.asah.connector.internal.client.model.DXPVariantSettings;
import com.liferay.segments.asah.connector.internal.client.model.ExperimentSettings;
import com.liferay.segments.model.SegmentsExperiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	immediate = true,
	service = CalculateSegmentsExperimentEstimatedDurationMVCCommandHelper.class
)
public class CalculateSegmentsExperimentEstimatedDurationMVCCommandHelper {

	protected Long calculateExperimentEstimatedDaysDuration(
		SegmentsExperiment segmentsExperiment, double confidenceLevel,
		Map<String, Double> segmentsExperienceKeySplitMap) {

		Optional<AsahFaroBackendClient> asahFaroBackendClientOptional =
			_asahFaroBackendClientFactory.createAsahFaroBackendClient();

		if (!asahFaroBackendClientOptional.isPresent()) {
			return null;
		}

		_asahFaroBackendClient = asahFaroBackendClientOptional.get();

		return _asahFaroBackendClient.calculateExperimentEstimatedDaysDuration(
			segmentsExperiment.getSegmentsExperienceKey(),
			_createExperimentSettings(
				confidenceLevel, segmentsExperienceKeySplitMap,
				segmentsExperiment));
	}

	private DXPVariantSettings _createDXPVariantSettings(
		String controlSegmentsExperienceKey, String segmentsExperienceKey,
		Double split) {

		DXPVariantSettings dxpVariantSettings = new DXPVariantSettings();

		dxpVariantSettings.setControl(
			Objects.equals(
				controlSegmentsExperienceKey, segmentsExperienceKey));

		dxpVariantSettings.setTrafficSplit(split);

		dxpVariantSettings.setDXPVariantId(segmentsExperienceKey);

		return dxpVariantSettings;
	}

	private ExperimentSettings _createExperimentSettings(
		double confidenceLevel,
		Map<String, Double> segmentsExperienceKeySplitMap,
		SegmentsExperiment segmentsExperiment) {

		ExperimentSettings experimentSettings = new ExperimentSettings();

		experimentSettings.setConfidenceLevel(confidenceLevel);

		List<DXPVariantSettings> dxpVariantsSettings = new ArrayList<>();

		segmentsExperienceKeySplitMap.forEach(
			(segmentsExperienceKey, split) -> dxpVariantsSettings.add(
				_createDXPVariantSettings(
					segmentsExperiment.getSegmentsExperimentKey(),
					segmentsExperienceKey, split)));
		experimentSettings.setDXPVariantsSettings(dxpVariantsSettings);

		return experimentSettings;
	}

	private AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private AsahFaroBackendClientFactory _asahFaroBackendClientFactory;

}