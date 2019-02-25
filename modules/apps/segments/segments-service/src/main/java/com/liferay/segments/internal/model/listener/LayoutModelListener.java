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

package com.liferay.segments.internal.model.listener;

import com.liferay.layout.constants.LayoutConstants;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.exception.SegmentsExperienceSegmentsEntryException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author David Arques
 */
@Component(service = ModelListener.class)
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onAfterCreate(Layout layout) throws ModelListenerException {
		if (!_isContentLayout(layout)) {
			return;
		}

		try {
			SegmentsEntry segmentsEntry = _getDefaultSegment(
				layout.getGroupId());

			_addDefaultExperience(
				layout.getGroupId(), segmentsEntry.getSegmentsEntryId(),
				_classNameLocalService.getClassNameId(Layout.class),
				layout.getPrimaryKey());
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterRemove(Layout layout) throws ModelListenerException {
		if (!_isContentLayout(layout)) {
			return;
		}

		try {
			_segmentsExperienceLocalService.deleteSegmentsExperiences(
				layout.getGroupId(),
				_classNameLocalService.getClassNameId(Layout.class),
				layout.getPrimaryKey());
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	private SegmentsExperience _addDefaultExperience(
			long groupId, long segmentsEntryId, long classNameId, long classPK)
		throws PortalException {

		Map<Locale, String> nameMap = ResourceBundleUtil.getLocalizationMap(
			_resourceBundleLoader, "default-experience");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		Group group = _groupLocalService.getGroup(groupId);

		long defaultUserId = _userLocalService.getDefaultUserId(
			group.getCompanyId());

		serviceContext.setUserId(defaultUserId);

		return _segmentsExperienceLocalService.addSegmentsExperience(
			segmentsEntryId, classNameId, classPK, nameMap, 0, true,
			serviceContext);
	}

	private SegmentsEntry _getDefaultSegment(long groupId)
		throws PortalException {

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				groupId, SegmentsConstants.KEY_DEFAULT, true);

		if (segmentsEntry == null) {
			throw new SegmentsExperienceSegmentsEntryException(
				"Unable to find default segment");
		}

		return segmentsEntry;
	}

	private boolean _isContentLayout(Layout layout) {
		if (Objects.equals(
				layout.getType(), LayoutConstants.LAYOUT_TYPE_CONTENT)) {

			return true;
		}

		return false;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.segments.lang)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}