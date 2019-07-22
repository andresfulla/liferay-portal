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

package com.liferay.segments.experiment.web.internal.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.segments.service.SegmentsExperimentService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class SegmentsExperimentDisplayContext {

	public SegmentsExperimentDisplayContext(
		HttpServletRequest httpServletRequest,
		SegmentsExperienceService segmentsExperienceService,
		SegmentsExperimentService segmentsExperimentService) {

		_httpServletRequest = httpServletRequest;
		_segmentsExperienceService = segmentsExperienceService;
		_segmentsExperimentService = segmentsExperimentService;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public JSONArray getSegmentsExperiencesJSONArray(Locale locale)
		throws PortalException {

		List<SegmentsExperience> segmentsExperiences =
			_segmentsExperienceService.getSegmentsExperiences(
				_themeDisplay.getScopeGroupId(),
				PortalUtil.getClassNameId(Layout.class),
				_themeDisplay.getPlid(), true);

		JSONArray segmentsExperiencesJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			Optional<SegmentsExperiment> segmentsExperimentOptional =
				_getDraftSegmentsExperimentOptional(segmentsExperience);

			JSONObject segmentsExperienceJSONObject = JSONUtil.put(
				"segmentsExperienceId",
				segmentsExperience.getSegmentsExperienceId()
			).put(
				"name", segmentsExperience.getName(locale)
			).put(
				"segmentsExperiment",
				_getSegmentsExperimentJSONObject(segmentsExperimentOptional)
			);

			segmentsExperiencesJSONArray.put(segmentsExperienceJSONObject);
		}

		return segmentsExperiencesJSONArray;
	}

	public JSONObject getSegmentsExperimentJSONObject() throws PortalException {
		Optional<SegmentsExperiment> segmentsExperimentOptional =
			_getDraftSegmentsExperimentOptional(
				getSelectedSegmentsExperience());

		return _getSegmentsExperimentJSONObject(segmentsExperimentOptional);
	}

	public SegmentsExperience getSelectedSegmentsExperience()
		throws PortalException {

		if (_segmentsExperience != null) {
			return _segmentsExperience;
		}

		_segmentsExperience = _segmentsExperienceService.getSegmentsExperience(
			getSelectedSegmentsExperienceId());

		return _segmentsExperience;
	}

	public long getSelectedSegmentsExperienceId() {
		if (Validator.isNotNull(_segmentsExperienceId)) {
			return _segmentsExperienceId;
		}

		_segmentsExperienceId = ParamUtil.getLong(
			_httpServletRequest, "segmentsExperienceId",
			SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		return _segmentsExperienceId;
	}

	private Optional<SegmentsExperiment> _getDraftSegmentsExperimentOptional(
			SegmentsExperience segmentsExperience)
		throws PortalException {

		List<SegmentsExperiment> segmentsExperienceSegmentsExperiments =
			_segmentsExperimentService.getSegmentsExperienceSegmentsExperiments(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(),
				SegmentsConstants.SEGMENTS_EXPERIMENT_STATUS_DRAFT);

		Stream<SegmentsExperiment> segmentsExperienceSegmentsExperimentsStream =
			segmentsExperienceSegmentsExperiments.stream();

		return segmentsExperienceSegmentsExperimentsStream.findFirst();
	}

	private JSONObject _getSegmentsExperimentJSONObject(
		Optional<SegmentsExperiment> segmentsExperimentOptional) {

		if (!segmentsExperimentOptional.isPresent()) {
			return null;
		}

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentOptional.get();

		return JSONUtil.put(
			"segmentsExperimentId", segmentsExperiment.getSegmentsExperimentId()
		).put(
			"name", segmentsExperiment.getName()
		).put(
			"description", segmentsExperiment.getDescription()
		).put(
			"segmentsExperienceId", segmentsExperiment.getSegmentsExperienceId()
		);
	}

	private final HttpServletRequest _httpServletRequest;
	private SegmentsExperience _segmentsExperience;
	private Long _segmentsExperienceId;
	private final SegmentsExperienceService _segmentsExperienceService;
	private final SegmentsExperimentService _segmentsExperimentService;
	private final ThemeDisplay _themeDisplay;

}