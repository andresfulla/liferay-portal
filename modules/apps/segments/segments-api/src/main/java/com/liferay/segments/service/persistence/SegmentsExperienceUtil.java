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

package com.liferay.segments.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.segments.model.SegmentsExperience;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the segments experience service. This utility wraps <code>com.liferay.segments.service.persistence.impl.SegmentsExperiencePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsExperiencePersistence
 * @generated
 */
@ProviderType
public class SegmentsExperienceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(SegmentsExperience segmentsExperience) {
		getPersistence().clearCache(segmentsExperience);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, SegmentsExperience> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SegmentsExperience> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SegmentsExperience> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SegmentsExperience> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SegmentsExperience update(
		SegmentsExperience segmentsExperience) {
		return getPersistence().update(segmentsExperience);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SegmentsExperience update(
		SegmentsExperience segmentsExperience, ServiceContext serviceContext) {
		return getPersistence().update(segmentsExperience, serviceContext);
	}

	/**
	* Returns all the segments experiences where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching segments experiences
	*/
	public static List<SegmentsExperience> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the segments experiences where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of segments experiences
	* @param end the upper bound of the range of segments experiences (not inclusive)
	* @return the range of matching segments experiences
	*/
	public static List<SegmentsExperience> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the segments experiences where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of segments experiences
	* @param end the upper bound of the range of segments experiences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments experiences
	*/
	public static List<SegmentsExperience> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the segments experiences where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of segments experiences
	* @param end the upper bound of the range of segments experiences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments experiences
	*/
	public static List<SegmentsExperience> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first segments experience in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments experience
	* @throws NoSuchExperienceException if a matching segments experience could not be found
	*/
	public static SegmentsExperience findByGroupId_First(long groupId,
		OrderByComparator<SegmentsExperience> orderByComparator)
		throws com.liferay.segments.exception.NoSuchExperienceException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first segments experience in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments experience, or <code>null</code> if a matching segments experience could not be found
	*/
	public static SegmentsExperience fetchByGroupId_First(long groupId,
		OrderByComparator<SegmentsExperience> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last segments experience in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments experience
	* @throws NoSuchExperienceException if a matching segments experience could not be found
	*/
	public static SegmentsExperience findByGroupId_Last(long groupId,
		OrderByComparator<SegmentsExperience> orderByComparator)
		throws com.liferay.segments.exception.NoSuchExperienceException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last segments experience in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments experience, or <code>null</code> if a matching segments experience could not be found
	*/
	public static SegmentsExperience fetchByGroupId_Last(long groupId,
		OrderByComparator<SegmentsExperience> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the segments experiences before and after the current segments experience in the ordered set where groupId = &#63;.
	*
	* @param segmentsExperienceId the primary key of the current segments experience
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments experience
	* @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	*/
	public static SegmentsExperience[] findByGroupId_PrevAndNext(
		long segmentsExperienceId, long groupId,
		OrderByComparator<SegmentsExperience> orderByComparator)
		throws com.liferay.segments.exception.NoSuchExperienceException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(segmentsExperienceId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the segments experiences where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of segments experiences where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching segments experiences
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the segments experience where groupId = &#63; and layoutUuid = &#63; and segmentsEntryId = &#63; or throws a <code>NoSuchExperienceException</code> if it could not be found.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param segmentsEntryId the segments entry ID
	* @return the matching segments experience
	* @throws NoSuchExperienceException if a matching segments experience could not be found
	*/
	public static SegmentsExperience findByG_L_S(long groupId,
		String layoutUuid, long segmentsEntryId)
		throws com.liferay.segments.exception.NoSuchExperienceException {
		return getPersistence().findByG_L_S(groupId, layoutUuid, segmentsEntryId);
	}

	/**
	* Returns the segments experience where groupId = &#63; and layoutUuid = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param segmentsEntryId the segments entry ID
	* @return the matching segments experience, or <code>null</code> if a matching segments experience could not be found
	*/
	public static SegmentsExperience fetchByG_L_S(long groupId,
		String layoutUuid, long segmentsEntryId) {
		return getPersistence()
				   .fetchByG_L_S(groupId, layoutUuid, segmentsEntryId);
	}

	/**
	* Returns the segments experience where groupId = &#63; and layoutUuid = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param segmentsEntryId the segments entry ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching segments experience, or <code>null</code> if a matching segments experience could not be found
	*/
	public static SegmentsExperience fetchByG_L_S(long groupId,
		String layoutUuid, long segmentsEntryId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_L_S(groupId, layoutUuid, segmentsEntryId,
			retrieveFromCache);
	}

	/**
	* Removes the segments experience where groupId = &#63; and layoutUuid = &#63; and segmentsEntryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param segmentsEntryId the segments entry ID
	* @return the segments experience that was removed
	*/
	public static SegmentsExperience removeByG_L_S(long groupId,
		String layoutUuid, long segmentsEntryId)
		throws com.liferay.segments.exception.NoSuchExperienceException {
		return getPersistence()
				   .removeByG_L_S(groupId, layoutUuid, segmentsEntryId);
	}

	/**
	* Returns the number of segments experiences where groupId = &#63; and layoutUuid = &#63; and segmentsEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param segmentsEntryId the segments entry ID
	* @return the number of matching segments experiences
	*/
	public static int countByG_L_S(long groupId, String layoutUuid,
		long segmentsEntryId) {
		return getPersistence()
				   .countByG_L_S(groupId, layoutUuid, segmentsEntryId);
	}

	/**
	* Returns all the segments experiences where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	* @return the matching segments experiences
	*/
	public static List<SegmentsExperience> findByG_L_A(long groupId,
		String layoutUuid, boolean active) {
		return getPersistence().findByG_L_A(groupId, layoutUuid, active);
	}

	/**
	* Returns a range of all the segments experiences where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	* @param start the lower bound of the range of segments experiences
	* @param end the upper bound of the range of segments experiences (not inclusive)
	* @return the range of matching segments experiences
	*/
	public static List<SegmentsExperience> findByG_L_A(long groupId,
		String layoutUuid, boolean active, int start, int end) {
		return getPersistence()
				   .findByG_L_A(groupId, layoutUuid, active, start, end);
	}

	/**
	* Returns an ordered range of all the segments experiences where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	* @param start the lower bound of the range of segments experiences
	* @param end the upper bound of the range of segments experiences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching segments experiences
	*/
	public static List<SegmentsExperience> findByG_L_A(long groupId,
		String layoutUuid, boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {
		return getPersistence()
				   .findByG_L_A(groupId, layoutUuid, active, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the segments experiences where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	* @param start the lower bound of the range of segments experiences
	* @param end the upper bound of the range of segments experiences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching segments experiences
	*/
	public static List<SegmentsExperience> findByG_L_A(long groupId,
		String layoutUuid, boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_L_A(groupId, layoutUuid, active, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first segments experience in the ordered set where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments experience
	* @throws NoSuchExperienceException if a matching segments experience could not be found
	*/
	public static SegmentsExperience findByG_L_A_First(long groupId,
		String layoutUuid, boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator)
		throws com.liferay.segments.exception.NoSuchExperienceException {
		return getPersistence()
				   .findByG_L_A_First(groupId, layoutUuid, active,
			orderByComparator);
	}

	/**
	* Returns the first segments experience in the ordered set where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments experience, or <code>null</code> if a matching segments experience could not be found
	*/
	public static SegmentsExperience fetchByG_L_A_First(long groupId,
		String layoutUuid, boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator) {
		return getPersistence()
				   .fetchByG_L_A_First(groupId, layoutUuid, active,
			orderByComparator);
	}

	/**
	* Returns the last segments experience in the ordered set where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments experience
	* @throws NoSuchExperienceException if a matching segments experience could not be found
	*/
	public static SegmentsExperience findByG_L_A_Last(long groupId,
		String layoutUuid, boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator)
		throws com.liferay.segments.exception.NoSuchExperienceException {
		return getPersistence()
				   .findByG_L_A_Last(groupId, layoutUuid, active,
			orderByComparator);
	}

	/**
	* Returns the last segments experience in the ordered set where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments experience, or <code>null</code> if a matching segments experience could not be found
	*/
	public static SegmentsExperience fetchByG_L_A_Last(long groupId,
		String layoutUuid, boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator) {
		return getPersistence()
				   .fetchByG_L_A_Last(groupId, layoutUuid, active,
			orderByComparator);
	}

	/**
	* Returns the segments experiences before and after the current segments experience in the ordered set where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	*
	* @param segmentsExperienceId the primary key of the current segments experience
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments experience
	* @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	*/
	public static SegmentsExperience[] findByG_L_A_PrevAndNext(
		long segmentsExperienceId, long groupId, String layoutUuid,
		boolean active, OrderByComparator<SegmentsExperience> orderByComparator)
		throws com.liferay.segments.exception.NoSuchExperienceException {
		return getPersistence()
				   .findByG_L_A_PrevAndNext(segmentsExperienceId, groupId,
			layoutUuid, active, orderByComparator);
	}

	/**
	* Removes all the segments experiences where groupId = &#63; and layoutUuid = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	*/
	public static void removeByG_L_A(long groupId, String layoutUuid,
		boolean active) {
		getPersistence().removeByG_L_A(groupId, layoutUuid, active);
	}

	/**
	* Returns the number of segments experiences where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	* @return the number of matching segments experiences
	*/
	public static int countByG_L_A(long groupId, String layoutUuid,
		boolean active) {
		return getPersistence().countByG_L_A(groupId, layoutUuid, active);
	}

	/**
	* Caches the segments experience in the entity cache if it is enabled.
	*
	* @param segmentsExperience the segments experience
	*/
	public static void cacheResult(SegmentsExperience segmentsExperience) {
		getPersistence().cacheResult(segmentsExperience);
	}

	/**
	* Caches the segments experiences in the entity cache if it is enabled.
	*
	* @param segmentsExperiences the segments experiences
	*/
	public static void cacheResult(List<SegmentsExperience> segmentsExperiences) {
		getPersistence().cacheResult(segmentsExperiences);
	}

	/**
	* Creates a new segments experience with the primary key. Does not add the segments experience to the database.
	*
	* @param segmentsExperienceId the primary key for the new segments experience
	* @return the new segments experience
	*/
	public static SegmentsExperience create(long segmentsExperienceId) {
		return getPersistence().create(segmentsExperienceId);
	}

	/**
	* Removes the segments experience with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param segmentsExperienceId the primary key of the segments experience
	* @return the segments experience that was removed
	* @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	*/
	public static SegmentsExperience remove(long segmentsExperienceId)
		throws com.liferay.segments.exception.NoSuchExperienceException {
		return getPersistence().remove(segmentsExperienceId);
	}

	public static SegmentsExperience updateImpl(
		SegmentsExperience segmentsExperience) {
		return getPersistence().updateImpl(segmentsExperience);
	}

	/**
	* Returns the segments experience with the primary key or throws a <code>NoSuchExperienceException</code> if it could not be found.
	*
	* @param segmentsExperienceId the primary key of the segments experience
	* @return the segments experience
	* @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	*/
	public static SegmentsExperience findByPrimaryKey(long segmentsExperienceId)
		throws com.liferay.segments.exception.NoSuchExperienceException {
		return getPersistence().findByPrimaryKey(segmentsExperienceId);
	}

	/**
	* Returns the segments experience with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param segmentsExperienceId the primary key of the segments experience
	* @return the segments experience, or <code>null</code> if a segments experience with the primary key could not be found
	*/
	public static SegmentsExperience fetchByPrimaryKey(
		long segmentsExperienceId) {
		return getPersistence().fetchByPrimaryKey(segmentsExperienceId);
	}

	/**
	* Returns all the segments experiences.
	*
	* @return the segments experiences
	*/
	public static List<SegmentsExperience> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the segments experiences.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of segments experiences
	* @param end the upper bound of the range of segments experiences (not inclusive)
	* @return the range of segments experiences
	*/
	public static List<SegmentsExperience> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the segments experiences.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of segments experiences
	* @param end the upper bound of the range of segments experiences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of segments experiences
	*/
	public static List<SegmentsExperience> findAll(int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the segments experiences.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperienceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of segments experiences
	* @param end the upper bound of the range of segments experiences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of segments experiences
	*/
	public static List<SegmentsExperience> findAll(int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the segments experiences from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of segments experiences.
	*
	* @return the number of segments experiences
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SegmentsExperiencePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SegmentsExperiencePersistence, SegmentsExperiencePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SegmentsExperiencePersistence.class);

		ServiceTracker<SegmentsExperiencePersistence, SegmentsExperiencePersistence> serviceTracker =
			new ServiceTracker<SegmentsExperiencePersistence, SegmentsExperiencePersistence>(bundle.getBundleContext(),
				SegmentsExperiencePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}