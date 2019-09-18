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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsExperimentLocalService;
import com.liferay.segments.service.SegmentsExperimentRelLocalService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS_EXPERIMENT,
		"mvc.command.name=/calculate_segments_experiment_estimated_days_duration"
	},
	service = MVCActionCommand.class
)
public class CalculateSegmentsExperimentEstimatedDurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = null;

		try {
			jsonObject = _calculateSegmentsExperimentEstimatedDaysDuration(
				actionRequest);
		}
		catch (Throwable t) {
			_log.error(t, t);

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			jsonObject = JSONUtil.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		hideDefaultSuccessMessage(actionRequest);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private JSONObject _calculateSegmentsExperimentEstimatedDaysDuration(
			ActionRequest actionRequest)
		throws PortalException {

		long segmentsExperimentId = ParamUtil.getLong(
			actionRequest, "segmentsExperimentId");

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.getSegmentsExperiment(
				segmentsExperimentId);

		String segmentsExperimentRels = ParamUtil.getString(
			actionRequest, "segmentsExperimentRels");

		JSONObject segmentsExperimentRelsJSONObject =
			JSONFactoryUtil.createJSONObject(segmentsExperimentRels);

		Iterator<String> iterator = segmentsExperimentRelsJSONObject.keys();

		Map<String, Double> segmentsExperienceKeySplitMap = new HashMap<>();

		while (iterator.hasNext()) {
			String key = iterator.next();

			SegmentsExperimentRel segmentsExperimentRel =
				_segmentsExperimentRelLocalService.getSegmentsExperimentRel(
					GetterUtil.getLong(key));

			segmentsExperienceKeySplitMap.put(
				segmentsExperimentRel.getSegmentsExperienceKey(),
				segmentsExperimentRelsJSONObject.getDouble(key));
		}

		Long segmentsExperimentEstimatedDaysDuration =
			_calculateSegmentsExperimentEstimatedDurationMVCCommandHelper.
				calculateExperimentEstimatedDaysDuration(
					segmentsExperiment,
					ParamUtil.getDouble(actionRequest, "confidenceLevel"),
					segmentsExperienceKeySplitMap);

		return JSONUtil.put(
			"segmentsExperimentEstimatedDaysDuration",
			segmentsExperimentEstimatedDaysDuration);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CalculateSegmentsExperimentEstimatedDurationMVCActionCommand.class);

	@Reference
	private CalculateSegmentsExperimentEstimatedDurationMVCCommandHelper
		_calculateSegmentsExperimentEstimatedDurationMVCCommandHelper;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

	@Reference
	private SegmentsExperimentRelLocalService
		_segmentsExperimentRelLocalService;

}