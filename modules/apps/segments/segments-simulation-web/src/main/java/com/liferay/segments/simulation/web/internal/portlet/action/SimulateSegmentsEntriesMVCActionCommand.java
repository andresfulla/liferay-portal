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

package com.liferay.segments.simulation.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.simulator.SegmentsEntrySimulator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = {
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS_SIMULATION,
		"mvc.command.name=simulateSegmentsEntries"
	},
	service = MVCActionCommand.class
)
public class SimulateSegmentsEntriesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		long userId = _portal.getUserId(actionRequest);

		long[] selectedSegmentsEntryIds = ParamUtil.getLongValues(
			actionRequest, "segmentsEntryId");

		_segmentsEntrySimulator.setSimulatedSegmentsEntryIds(
			userId, selectedSegmentsEntryIds);
	}

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntrySimulator _segmentsEntrySimulator;

}