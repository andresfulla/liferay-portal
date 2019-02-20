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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for SegmentsExperience. This utility wraps
 * <code>com.liferay.segments.service.impl.SegmentsExperienceServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperienceService
 * @generated
 */
@ProviderType
public class SegmentsExperienceServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsExperienceServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.segments.model.SegmentsExperience addSegmentsExperience(
		java.util.Map<java.util.Locale, String> nameMap, String layoutUuid,
		long segmentsEntryId, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSegmentsExperience(nameMap, layoutUuid, segmentsEntryId,
			active, serviceContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static SegmentsExperienceService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SegmentsExperienceService, SegmentsExperienceService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SegmentsExperienceService.class);

		ServiceTracker<SegmentsExperienceService, SegmentsExperienceService> serviceTracker =
			new ServiceTracker<SegmentsExperienceService, SegmentsExperienceService>(bundle.getBundleContext(),
				SegmentsExperienceService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}