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

package com.liferay.segments.service.impl;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.exception.SegmentsExperienceLayoutException;
import com.liferay.segments.exception.SegmentsExperienceSegmentException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.base.SegmentsExperienceLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The implementation of the segments experience local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.segments.service.SegmentsExperienceLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author David Arques
 * @see SegmentsExperienceLocalServiceBaseImpl
 */
public class SegmentsExperienceLocalServiceImpl
	extends SegmentsExperienceLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SegmentsExperience addSegmentsExperience(
			Map<Locale, String> nameMap, String layoutUuid,
			long segmentsEntryId, boolean active, ServiceContext serviceContext)

		throws PortalException {

		// Segments experience

		User user = userLocalService.getUser(serviceContext.getUserId());

		long groupId = serviceContext.getScopeGroupId();

		_validate(groupId, layoutUuid, segmentsEntryId);

		long segmentsExperienceId = counterLocalService.increment();

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.create(segmentsExperienceId);

		segmentsExperience.setGroupId(groupId);
		segmentsExperience.setCompanyId(user.getCompanyId());
		segmentsExperience.setUserId(user.getUserId());
		segmentsExperience.setUserName(user.getFullName());
		segmentsExperience.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		segmentsExperience.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));

		segmentsExperience.setNameMap(nameMap);
		segmentsExperience.setLayoutUuid(layoutUuid);
		segmentsExperience.setSegmentsEntryId(segmentsEntryId);
		segmentsExperience.setPriority(
			_getNextPriority(groupId, layoutUuid, segmentsEntryId));
		segmentsExperience.setActive(active);

		segmentsExperiencePersistence.update(segmentsExperience);

		// TODO Resources

		return segmentsExperience;
	}

	@Override
	public SegmentsExperience deleteSegmentsExperience(
			long segmentsExperienceId)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		return segmentsExperienceLocalService.deleteSegmentsExperience(
			segmentsExperience);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SegmentsExperience deleteSegmentsExperience(
			SegmentsExperience segmentsExperience)
		throws PortalException {

		// Segments experience

		segmentsExperiencePersistence.remove(segmentsExperience);

		// TODO Resources

		return segmentsExperience;
	}

	@Override
	public void deleteSegmentsExperiences(long groupId) throws PortalException {
		List<SegmentsExperience> segmentsExperiences =
			segmentsExperiencePersistence.findByGroupId(groupId);

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			segmentsExperienceLocalService.deleteSegmentsExperience(
				segmentsExperience.getSegmentsEntryId());
		}
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
		long groupId, String layoutUuid, boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		return segmentsExperiencePersistence.findByG_L_A(
			groupId, layoutUuid, active, start, end, orderByComparator);
	}

	private int _getMaxPriority(
		long groupId, String layoutUuid, long segmentsEntryId) {

		DynamicQuery dynamicQuery = dynamicQuery();

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(groupId));

		Property layoutUuidProperty = PropertyFactoryUtil.forName("layoutUuid");

		dynamicQuery.add(layoutUuidProperty.eq(layoutUuid));

		Property segmentsEntryIdProperty = PropertyFactoryUtil.forName(
			"segmentsEntryId");

		dynamicQuery.add(segmentsEntryIdProperty.eq(segmentsEntryId));

		Projection projection = ProjectionFactoryUtil.max("priority");

		dynamicQuery.setProjection(projection);

		List<Object> results = dynamicQuery(dynamicQuery);

		if (ListUtil.isNull(results)) {
			return 0;
		}

		return (int)results.get(0);
	}

	private int _getNextPriority(
		long groupId, String layoutUuid, long segmentsEntryId) {

		return _getMaxPriority(groupId, layoutUuid, segmentsEntryId) + 1;
	}

	private boolean _isDefaultSegment(long segmentsEntryId)
		throws PortalException {

		SegmentsEntry segmentsEntry =
			segmentsEntryLocalService.getSegmentsEntry(segmentsEntryId);

		return segmentsEntry.isDefaultSegment();
	}

	private void _validate(
			long groupId, String layoutUuid, long segmentsEntryId)
		throws PortalException {

		if (Validator.isNull(layoutUuid)) {
			throw new IllegalArgumentException("Layout UUID is null");
		}

		Layout privateLayout = layoutLocalService.fetchLayoutByUuidAndGroupId(
			layoutUuid, groupId, true);

		Layout publicLayout = layoutLocalService.fetchLayoutByUuidAndGroupId(
			layoutUuid, groupId, false);

		if ((privateLayout == null) && (publicLayout == null)) {
			throw new SegmentsExperienceLayoutException(
				"Unable to find layout with UUID " + layoutUuid);
		}

		if (!_isDefaultSegment(segmentsEntryId)) {
			return;
		}

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.fetchByG_L_S(
				groupId, layoutUuid, segmentsEntryId);

		if (segmentsExperience != null) {
			throw new SegmentsExperienceSegmentException(
				"The default experience already exists");
		}
	}

}