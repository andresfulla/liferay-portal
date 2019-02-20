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

package com.liferay.segments.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SegmentsExperienceService}.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperienceService
 * @generated
 */
@ProviderType
public class SegmentsExperienceServiceWrapper
	implements SegmentsExperienceService,
		ServiceWrapper<SegmentsExperienceService> {
	public SegmentsExperienceServiceWrapper(
		SegmentsExperienceService segmentsExperienceService) {
		_segmentsExperienceService = segmentsExperienceService;
	}

	@Override
	public com.liferay.segments.model.SegmentsExperience addSegmentsExperience(
		java.util.Map<java.util.Locale, String> nameMap, String layoutUuid,
		long segmentsEntryId, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _segmentsExperienceService.addSegmentsExperience(nameMap,
			layoutUuid, segmentsEntryId, active, serviceContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _segmentsExperienceService.getOSGiServiceIdentifier();
	}

	@Override
	public SegmentsExperienceService getWrappedService() {
		return _segmentsExperienceService;
	}

	@Override
	public void setWrappedService(
		SegmentsExperienceService segmentsExperienceService) {
		_segmentsExperienceService = segmentsExperienceService;
	}

	private SegmentsExperienceService _segmentsExperienceService;
}