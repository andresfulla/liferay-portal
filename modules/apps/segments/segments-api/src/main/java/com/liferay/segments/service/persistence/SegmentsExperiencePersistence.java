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

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.segments.exception.NoSuchExperienceException;
import com.liferay.segments.model.SegmentsExperience;

/**
 * The persistence interface for the segments experience service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsExperienceUtil
 * @generated
 */
@ProviderType
public interface SegmentsExperiencePersistence extends BasePersistence<SegmentsExperience> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SegmentsExperienceUtil} to access the segments experience persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the segments experiences where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching segments experiences
	*/
	public java.util.List<SegmentsExperience> findByGroupId(long groupId);

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
	public java.util.List<SegmentsExperience> findByGroupId(long groupId,
		int start, int end);

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
	public java.util.List<SegmentsExperience> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator);

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
	public java.util.List<SegmentsExperience> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first segments experience in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments experience
	* @throws NoSuchExperienceException if a matching segments experience could not be found
	*/
	public SegmentsExperience findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException;

	/**
	* Returns the first segments experience in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments experience, or <code>null</code> if a matching segments experience could not be found
	*/
	public SegmentsExperience fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator);

	/**
	* Returns the last segments experience in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments experience
	* @throws NoSuchExperienceException if a matching segments experience could not be found
	*/
	public SegmentsExperience findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException;

	/**
	* Returns the last segments experience in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments experience, or <code>null</code> if a matching segments experience could not be found
	*/
	public SegmentsExperience fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator);

	/**
	* Returns the segments experiences before and after the current segments experience in the ordered set where groupId = &#63;.
	*
	* @param segmentsExperienceId the primary key of the current segments experience
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next segments experience
	* @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	*/
	public SegmentsExperience[] findByGroupId_PrevAndNext(
		long segmentsExperienceId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException;

	/**
	* Removes all the segments experiences where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of segments experiences where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching segments experiences
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the segments experience where groupId = &#63; and layoutUuid = &#63; and segmentsEntryId = &#63; or throws a <code>NoSuchExperienceException</code> if it could not be found.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param segmentsEntryId the segments entry ID
	* @return the matching segments experience
	* @throws NoSuchExperienceException if a matching segments experience could not be found
	*/
	public SegmentsExperience findByG_L_S(long groupId, String layoutUuid,
		long segmentsEntryId) throws NoSuchExperienceException;

	/**
	* Returns the segments experience where groupId = &#63; and layoutUuid = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param segmentsEntryId the segments entry ID
	* @return the matching segments experience, or <code>null</code> if a matching segments experience could not be found
	*/
	public SegmentsExperience fetchByG_L_S(long groupId, String layoutUuid,
		long segmentsEntryId);

	/**
	* Returns the segments experience where groupId = &#63; and layoutUuid = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param segmentsEntryId the segments entry ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching segments experience, or <code>null</code> if a matching segments experience could not be found
	*/
	public SegmentsExperience fetchByG_L_S(long groupId, String layoutUuid,
		long segmentsEntryId, boolean retrieveFromCache);

	/**
	* Removes the segments experience where groupId = &#63; and layoutUuid = &#63; and segmentsEntryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param segmentsEntryId the segments entry ID
	* @return the segments experience that was removed
	*/
	public SegmentsExperience removeByG_L_S(long groupId, String layoutUuid,
		long segmentsEntryId) throws NoSuchExperienceException;

	/**
	* Returns the number of segments experiences where groupId = &#63; and layoutUuid = &#63; and segmentsEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param segmentsEntryId the segments entry ID
	* @return the number of matching segments experiences
	*/
	public int countByG_L_S(long groupId, String layoutUuid,
		long segmentsEntryId);

	/**
	* Returns all the segments experiences where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	* @return the matching segments experiences
	*/
	public java.util.List<SegmentsExperience> findByG_L_A(long groupId,
		String layoutUuid, boolean active);

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
	public java.util.List<SegmentsExperience> findByG_L_A(long groupId,
		String layoutUuid, boolean active, int start, int end);

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
	public java.util.List<SegmentsExperience> findByG_L_A(long groupId,
		String layoutUuid, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator);

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
	public java.util.List<SegmentsExperience> findByG_L_A(long groupId,
		String layoutUuid, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator,
		boolean retrieveFromCache);

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
	public SegmentsExperience findByG_L_A_First(long groupId,
		String layoutUuid, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException;

	/**
	* Returns the first segments experience in the ordered set where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching segments experience, or <code>null</code> if a matching segments experience could not be found
	*/
	public SegmentsExperience fetchByG_L_A_First(long groupId,
		String layoutUuid, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator);

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
	public SegmentsExperience findByG_L_A_Last(long groupId, String layoutUuid,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException;

	/**
	* Returns the last segments experience in the ordered set where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching segments experience, or <code>null</code> if a matching segments experience could not be found
	*/
	public SegmentsExperience fetchByG_L_A_Last(long groupId,
		String layoutUuid, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator);

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
	public SegmentsExperience[] findByG_L_A_PrevAndNext(
		long segmentsExperienceId, long groupId, String layoutUuid,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException;

	/**
	* Removes all the segments experiences where groupId = &#63; and layoutUuid = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	*/
	public void removeByG_L_A(long groupId, String layoutUuid, boolean active);

	/**
	* Returns the number of segments experiences where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param layoutUuid the layout uuid
	* @param active the active
	* @return the number of matching segments experiences
	*/
	public int countByG_L_A(long groupId, String layoutUuid, boolean active);

	/**
	* Caches the segments experience in the entity cache if it is enabled.
	*
	* @param segmentsExperience the segments experience
	*/
	public void cacheResult(SegmentsExperience segmentsExperience);

	/**
	* Caches the segments experiences in the entity cache if it is enabled.
	*
	* @param segmentsExperiences the segments experiences
	*/
	public void cacheResult(
		java.util.List<SegmentsExperience> segmentsExperiences);

	/**
	* Creates a new segments experience with the primary key. Does not add the segments experience to the database.
	*
	* @param segmentsExperienceId the primary key for the new segments experience
	* @return the new segments experience
	*/
	public SegmentsExperience create(long segmentsExperienceId);

	/**
	* Removes the segments experience with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param segmentsExperienceId the primary key of the segments experience
	* @return the segments experience that was removed
	* @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	*/
	public SegmentsExperience remove(long segmentsExperienceId)
		throws NoSuchExperienceException;

	public SegmentsExperience updateImpl(SegmentsExperience segmentsExperience);

	/**
	* Returns the segments experience with the primary key or throws a <code>NoSuchExperienceException</code> if it could not be found.
	*
	* @param segmentsExperienceId the primary key of the segments experience
	* @return the segments experience
	* @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	*/
	public SegmentsExperience findByPrimaryKey(long segmentsExperienceId)
		throws NoSuchExperienceException;

	/**
	* Returns the segments experience with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param segmentsExperienceId the primary key of the segments experience
	* @return the segments experience, or <code>null</code> if a segments experience with the primary key could not be found
	*/
	public SegmentsExperience fetchByPrimaryKey(long segmentsExperienceId);

	/**
	* Returns all the segments experiences.
	*
	* @return the segments experiences
	*/
	public java.util.List<SegmentsExperience> findAll();

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
	public java.util.List<SegmentsExperience> findAll(int start, int end);

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
	public java.util.List<SegmentsExperience> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator);

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
	public java.util.List<SegmentsExperience> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the segments experiences from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of segments experiences.
	*
	* @return the number of segments experiences
	*/
	public int countAll();
}