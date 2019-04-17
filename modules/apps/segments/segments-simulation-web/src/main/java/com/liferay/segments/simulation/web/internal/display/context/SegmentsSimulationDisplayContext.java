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

package com.liferay.segments.simulation.web.internal.display.context;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryService;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class SegmentsSimulationDisplayContext {

	public SegmentsSimulationDisplayContext(
		HttpServletRequest request, RenderRequest renderRequest,
		RenderResponse renderResponse,
		SegmentsEntryService segmentsEntryService) {

		_request = request;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_segmentsEntryService = segmentsEntryService;

		_liferayPortletRequest = PortalUtil.getLiferayPortletRequest(
			renderRequest);
		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			renderResponse);

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public PortletURL getDeactivateSimulationURL() {
		PortletURL portletURL = _liferayPortletResponse.createActionURL(
			SegmentsPortletKeys.SEGMENTS_SIMULATION);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "deactivateSimulation");

		return portletURL;
	}

	public String getPortletNamespace() {
		return PortalUtil.getPortletNamespace(
			SegmentsPortletKeys.SEGMENTS_SIMULATION);
	}

	public List<SegmentsEntry> getSegmentsEntries() {
		if (_segmentsEntries != null) {
			return _segmentsEntries;
		}

		_segmentsEntries = _segmentsEntryService.getSegmentsEntries(
			_themeDisplay.getScopeGroupId(), true);

		return _segmentsEntries;
	}

	public PortletURL getSimulateSegmentsEntriesURL() {
		PortletURL portletURL = _liferayPortletResponse.createActionURL(
			SegmentsPortletKeys.SEGMENTS_SIMULATION);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "simulateSegmentsEntries");

		return portletURL;
	}

	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private List<SegmentsEntry> _segmentsEntries;
	private final SegmentsEntryService _segmentsEntryService;
	private final ThemeDisplay _themeDisplay;

}