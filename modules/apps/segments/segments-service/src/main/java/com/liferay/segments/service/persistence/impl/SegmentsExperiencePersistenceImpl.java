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

package com.liferay.segments.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.segments.exception.NoSuchExperienceException;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.impl.SegmentsExperienceImpl;
import com.liferay.segments.model.impl.SegmentsExperienceModelImpl;
import com.liferay.segments.service.persistence.SegmentsExperiencePersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the segments experience service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @generated
 */
@ProviderType
public class SegmentsExperiencePersistenceImpl extends BasePersistenceImpl<SegmentsExperience>
	implements SegmentsExperiencePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SegmentsExperienceUtil</code> to access the segments experience persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SegmentsExperienceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the segments experiences where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<SegmentsExperience> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
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
	@Override
	public List<SegmentsExperience> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SegmentsExperience> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
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
	@Override
	public List<SegmentsExperience> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SegmentsExperience> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByGroupId;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<SegmentsExperience> list = null;

		if (retrieveFromCache) {
			list = (List<SegmentsExperience>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperience segmentsExperience : list) {
					if ((groupId != segmentsExperience.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<SegmentsExperience>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SegmentsExperience>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first segments experience in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByGroupId_First(long groupId,
		OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {
		SegmentsExperience segmentsExperience = fetchByGroupId_First(groupId,
				orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the first segments experience in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByGroupId_First(long groupId,
		OrderByComparator<SegmentsExperience> orderByComparator) {
		List<SegmentsExperience> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experience in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByGroupId_Last(long groupId,
		OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {
		SegmentsExperience segmentsExperience = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
	}

	/**
	 * Returns the last segments experience in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByGroupId_Last(long groupId,
		OrderByComparator<SegmentsExperience> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperience> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public SegmentsExperience[] findByGroupId_PrevAndNext(
		long segmentsExperienceId, long groupId,
		OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {
		SegmentsExperience segmentsExperience = findByPrimaryKey(segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, segmentsExperience,
					groupId, orderByComparator, true);

			array[1] = segmentsExperience;

			array[2] = getByGroupId_PrevAndNext(session, segmentsExperience,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperience getByGroupId_PrevAndNext(Session session,
		SegmentsExperience segmentsExperience, long groupId,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					segmentsExperience)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiences where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SegmentsExperience segmentsExperience : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(segmentsExperience);
		}
	}

	/**
	 * Returns the number of segments experiences where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "segmentsExperience.groupId = ?";
	private FinderPath _finderPathFetchByG_L_S;
	private FinderPath _finderPathCountByG_L_S;

	/**
	 * Returns the segments experience where groupId = &#63; and layoutUuid = &#63; and segmentsEntryId = &#63; or throws a <code>NoSuchExperienceException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching segments experience
	 * @throws NoSuchExperienceException if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience findByG_L_S(long groupId, String layoutUuid,
		long segmentsEntryId) throws NoSuchExperienceException {
		SegmentsExperience segmentsExperience = fetchByG_L_S(groupId,
				layoutUuid, segmentsEntryId);

		if (segmentsExperience == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", layoutUuid=");
			msg.append(layoutUuid);

			msg.append(", segmentsEntryId=");
			msg.append(segmentsEntryId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchExperienceException(msg.toString());
		}

		return segmentsExperience;
	}

	/**
	 * Returns the segments experience where groupId = &#63; and layoutUuid = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchByG_L_S(long groupId, String layoutUuid,
		long segmentsEntryId) {
		return fetchByG_L_S(groupId, layoutUuid, segmentsEntryId, true);
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
	@Override
	public SegmentsExperience fetchByG_L_S(long groupId, String layoutUuid,
		long segmentsEntryId, boolean retrieveFromCache) {
		layoutUuid = Objects.toString(layoutUuid, "");

		Object[] finderArgs = new Object[] { groupId, layoutUuid, segmentsEntryId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(_finderPathFetchByG_L_S, finderArgs,
					this);
		}

		if (result instanceof SegmentsExperience) {
			SegmentsExperience segmentsExperience = (SegmentsExperience)result;

			if ((groupId != segmentsExperience.getGroupId()) ||
					!Objects.equals(layoutUuid,
						segmentsExperience.getLayoutUuid()) ||
					(segmentsEntryId != segmentsExperience.getSegmentsEntryId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_L_S_GROUPID_2);

			boolean bindLayoutUuid = false;

			if (layoutUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_G_L_S_LAYOUTUUID_3);
			}
			else {
				bindLayoutUuid = true;

				query.append(_FINDER_COLUMN_G_L_S_LAYOUTUUID_2);
			}

			query.append(_FINDER_COLUMN_G_L_S_SEGMENTSENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindLayoutUuid) {
					qPos.add(layoutUuid);
				}

				qPos.add(segmentsEntryId);

				List<SegmentsExperience> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(_finderPathFetchByG_L_S, finderArgs,
						list);
				}
				else {
					SegmentsExperience segmentsExperience = list.get(0);

					result = segmentsExperience;

					cacheResult(segmentsExperience);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByG_L_S, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (SegmentsExperience)result;
		}
	}

	/**
	 * Removes the segments experience where groupId = &#63; and layoutUuid = &#63; and segmentsEntryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param segmentsEntryId the segments entry ID
	 * @return the segments experience that was removed
	 */
	@Override
	public SegmentsExperience removeByG_L_S(long groupId, String layoutUuid,
		long segmentsEntryId) throws NoSuchExperienceException {
		SegmentsExperience segmentsExperience = findByG_L_S(groupId,
				layoutUuid, segmentsEntryId);

		return remove(segmentsExperience);
	}

	/**
	 * Returns the number of segments experiences where groupId = &#63; and layoutUuid = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countByG_L_S(long groupId, String layoutUuid,
		long segmentsEntryId) {
		layoutUuid = Objects.toString(layoutUuid, "");

		FinderPath finderPath = _finderPathCountByG_L_S;

		Object[] finderArgs = new Object[] { groupId, layoutUuid, segmentsEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_L_S_GROUPID_2);

			boolean bindLayoutUuid = false;

			if (layoutUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_G_L_S_LAYOUTUUID_3);
			}
			else {
				bindLayoutUuid = true;

				query.append(_FINDER_COLUMN_G_L_S_LAYOUTUUID_2);
			}

			query.append(_FINDER_COLUMN_G_L_S_SEGMENTSENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindLayoutUuid) {
					qPos.add(layoutUuid);
				}

				qPos.add(segmentsEntryId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_L_S_GROUPID_2 = "segmentsExperience.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_L_S_LAYOUTUUID_2 = "segmentsExperience.layoutUuid = ? AND ";
	private static final String _FINDER_COLUMN_G_L_S_LAYOUTUUID_3 = "(segmentsExperience.layoutUuid IS NULL OR segmentsExperience.layoutUuid = '') AND ";
	private static final String _FINDER_COLUMN_G_L_S_SEGMENTSENTRYID_2 = "segmentsExperience.segmentsEntryId = ?";
	private FinderPath _finderPathWithPaginationFindByG_L_A;
	private FinderPath _finderPathWithoutPaginationFindByG_L_A;
	private FinderPath _finderPathCountByG_L_A;

	/**
	 * Returns all the segments experiences where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param active the active
	 * @return the matching segments experiences
	 */
	@Override
	public List<SegmentsExperience> findByG_L_A(long groupId,
		String layoutUuid, boolean active) {
		return findByG_L_A(groupId, layoutUuid, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
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
	@Override
	public List<SegmentsExperience> findByG_L_A(long groupId,
		String layoutUuid, boolean active, int start, int end) {
		return findByG_L_A(groupId, layoutUuid, active, start, end, null);
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
	@Override
	public List<SegmentsExperience> findByG_L_A(long groupId,
		String layoutUuid, boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {
		return findByG_L_A(groupId, layoutUuid, active, start, end,
			orderByComparator, true);
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
	@Override
	public List<SegmentsExperience> findByG_L_A(long groupId,
		String layoutUuid, boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean retrieveFromCache) {
		layoutUuid = Objects.toString(layoutUuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_L_A;
			finderArgs = new Object[] { groupId, layoutUuid, active };
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_L_A;
			finderArgs = new Object[] {
					groupId, layoutUuid, active,
					
					start, end, orderByComparator
				};
		}

		List<SegmentsExperience> list = null;

		if (retrieveFromCache) {
			list = (List<SegmentsExperience>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperience segmentsExperience : list) {
					if ((groupId != segmentsExperience.getGroupId()) ||
							!layoutUuid.equals(
								segmentsExperience.getLayoutUuid()) ||
							(active != segmentsExperience.isActive())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_L_A_GROUPID_2);

			boolean bindLayoutUuid = false;

			if (layoutUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_G_L_A_LAYOUTUUID_3);
			}
			else {
				bindLayoutUuid = true;

				query.append(_FINDER_COLUMN_G_L_A_LAYOUTUUID_2);
			}

			query.append(_FINDER_COLUMN_G_L_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindLayoutUuid) {
					qPos.add(layoutUuid);
				}

				qPos.add(active);

				if (!pagination) {
					list = (List<SegmentsExperience>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SegmentsExperience>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public SegmentsExperience findByG_L_A_First(long groupId,
		String layoutUuid, boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {
		SegmentsExperience segmentsExperience = fetchByG_L_A_First(groupId,
				layoutUuid, active, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutUuid=");
		msg.append(layoutUuid);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
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
	@Override
	public SegmentsExperience fetchByG_L_A_First(long groupId,
		String layoutUuid, boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator) {
		List<SegmentsExperience> list = findByG_L_A(groupId, layoutUuid,
				active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public SegmentsExperience findByG_L_A_Last(long groupId, String layoutUuid,
		boolean active, OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {
		SegmentsExperience segmentsExperience = fetchByG_L_A_Last(groupId,
				layoutUuid, active, orderByComparator);

		if (segmentsExperience != null) {
			return segmentsExperience;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutUuid=");
		msg.append(layoutUuid);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchExperienceException(msg.toString());
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
	@Override
	public SegmentsExperience fetchByG_L_A_Last(long groupId,
		String layoutUuid, boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator) {
		int count = countByG_L_A(groupId, layoutUuid, active);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperience> list = findByG_L_A(groupId, layoutUuid,
				active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public SegmentsExperience[] findByG_L_A_PrevAndNext(
		long segmentsExperienceId, long groupId, String layoutUuid,
		boolean active, OrderByComparator<SegmentsExperience> orderByComparator)
		throws NoSuchExperienceException {
		layoutUuid = Objects.toString(layoutUuid, "");

		SegmentsExperience segmentsExperience = findByPrimaryKey(segmentsExperienceId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperience[] array = new SegmentsExperienceImpl[3];

			array[0] = getByG_L_A_PrevAndNext(session, segmentsExperience,
					groupId, layoutUuid, active, orderByComparator, true);

			array[1] = segmentsExperience;

			array[2] = getByG_L_A_PrevAndNext(session, segmentsExperience,
					groupId, layoutUuid, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperience getByG_L_A_PrevAndNext(Session session,
		SegmentsExperience segmentsExperience, long groupId, String layoutUuid,
		boolean active,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_SEGMENTSEXPERIENCE_WHERE);

		query.append(_FINDER_COLUMN_G_L_A_GROUPID_2);

		boolean bindLayoutUuid = false;

		if (layoutUuid.isEmpty()) {
			query.append(_FINDER_COLUMN_G_L_A_LAYOUTUUID_3);
		}
		else {
			bindLayoutUuid = true;

			query.append(_FINDER_COLUMN_G_L_A_LAYOUTUUID_2);
		}

		query.append(_FINDER_COLUMN_G_L_A_ACTIVE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindLayoutUuid) {
			qPos.add(layoutUuid);
		}

		qPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					segmentsExperience)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperience> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiences where groupId = &#63; and layoutUuid = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param active the active
	 */
	@Override
	public void removeByG_L_A(long groupId, String layoutUuid, boolean active) {
		for (SegmentsExperience segmentsExperience : findByG_L_A(groupId,
				layoutUuid, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(segmentsExperience);
		}
	}

	/**
	 * Returns the number of segments experiences where groupId = &#63; and layoutUuid = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param active the active
	 * @return the number of matching segments experiences
	 */
	@Override
	public int countByG_L_A(long groupId, String layoutUuid, boolean active) {
		layoutUuid = Objects.toString(layoutUuid, "");

		FinderPath finderPath = _finderPathCountByG_L_A;

		Object[] finderArgs = new Object[] { groupId, layoutUuid, active };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SEGMENTSEXPERIENCE_WHERE);

			query.append(_FINDER_COLUMN_G_L_A_GROUPID_2);

			boolean bindLayoutUuid = false;

			if (layoutUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_G_L_A_LAYOUTUUID_3);
			}
			else {
				bindLayoutUuid = true;

				query.append(_FINDER_COLUMN_G_L_A_LAYOUTUUID_2);
			}

			query.append(_FINDER_COLUMN_G_L_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindLayoutUuid) {
					qPos.add(layoutUuid);
				}

				qPos.add(active);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_L_A_GROUPID_2 = "segmentsExperience.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_L_A_LAYOUTUUID_2 = "segmentsExperience.layoutUuid = ? AND ";
	private static final String _FINDER_COLUMN_G_L_A_LAYOUTUUID_3 = "(segmentsExperience.layoutUuid IS NULL OR segmentsExperience.layoutUuid = '') AND ";
	private static final String _FINDER_COLUMN_G_L_A_ACTIVE_2 = "segmentsExperience.active = ?";

	public SegmentsExperiencePersistenceImpl() {
		setModelClass(SegmentsExperience.class);

		setModelImplClass(SegmentsExperienceImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the segments experience in the entity cache if it is enabled.
	 *
	 * @param segmentsExperience the segments experience
	 */
	@Override
	public void cacheResult(SegmentsExperience segmentsExperience) {
		entityCache.putResult(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsExperienceImpl.class, segmentsExperience.getPrimaryKey(),
			segmentsExperience);

		finderCache.putResult(_finderPathFetchByG_L_S,
			new Object[] {
				segmentsExperience.getGroupId(),
				segmentsExperience.getLayoutUuid(),
				segmentsExperience.getSegmentsEntryId()
			}, segmentsExperience);

		segmentsExperience.resetOriginalValues();
	}

	/**
	 * Caches the segments experiences in the entity cache if it is enabled.
	 *
	 * @param segmentsExperiences the segments experiences
	 */
	@Override
	public void cacheResult(List<SegmentsExperience> segmentsExperiences) {
		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			if (entityCache.getResult(
						SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
						SegmentsExperienceImpl.class,
						segmentsExperience.getPrimaryKey()) == null) {
				cacheResult(segmentsExperience);
			}
			else {
				segmentsExperience.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all segments experiences.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SegmentsExperienceImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the segments experience.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SegmentsExperience segmentsExperience) {
		entityCache.removeResult(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsExperienceImpl.class, segmentsExperience.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SegmentsExperienceModelImpl)segmentsExperience,
			true);
	}

	@Override
	public void clearCache(List<SegmentsExperience> segmentsExperiences) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			entityCache.removeResult(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsExperienceImpl.class, segmentsExperience.getPrimaryKey());

			clearUniqueFindersCache((SegmentsExperienceModelImpl)segmentsExperience,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		SegmentsExperienceModelImpl segmentsExperienceModelImpl) {
		Object[] args = new Object[] {
				segmentsExperienceModelImpl.getGroupId(),
				segmentsExperienceModelImpl.getLayoutUuid(),
				segmentsExperienceModelImpl.getSegmentsEntryId()
			};

		finderCache.putResult(_finderPathCountByG_L_S, args, Long.valueOf(1),
			false);
		finderCache.putResult(_finderPathFetchByG_L_S, args,
			segmentsExperienceModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SegmentsExperienceModelImpl segmentsExperienceModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					segmentsExperienceModelImpl.getGroupId(),
					segmentsExperienceModelImpl.getLayoutUuid(),
					segmentsExperienceModelImpl.getSegmentsEntryId()
				};

			finderCache.removeResult(_finderPathCountByG_L_S, args);
			finderCache.removeResult(_finderPathFetchByG_L_S, args);
		}

		if ((segmentsExperienceModelImpl.getColumnBitmask() &
				_finderPathFetchByG_L_S.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					segmentsExperienceModelImpl.getOriginalGroupId(),
					segmentsExperienceModelImpl.getOriginalLayoutUuid(),
					segmentsExperienceModelImpl.getOriginalSegmentsEntryId()
				};

			finderCache.removeResult(_finderPathCountByG_L_S, args);
			finderCache.removeResult(_finderPathFetchByG_L_S, args);
		}
	}

	/**
	 * Creates a new segments experience with the primary key. Does not add the segments experience to the database.
	 *
	 * @param segmentsExperienceId the primary key for the new segments experience
	 * @return the new segments experience
	 */
	@Override
	public SegmentsExperience create(long segmentsExperienceId) {
		SegmentsExperience segmentsExperience = new SegmentsExperienceImpl();

		segmentsExperience.setNew(true);
		segmentsExperience.setPrimaryKey(segmentsExperienceId);

		segmentsExperience.setCompanyId(companyProvider.getCompanyId());

		return segmentsExperience;
	}

	/**
	 * Removes the segments experience with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperienceId the primary key of the segments experience
	 * @return the segments experience that was removed
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience remove(long segmentsExperienceId)
		throws NoSuchExperienceException {
		return remove((Serializable)segmentsExperienceId);
	}

	/**
	 * Removes the segments experience with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the segments experience
	 * @return the segments experience that was removed
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience remove(Serializable primaryKey)
		throws NoSuchExperienceException {
		Session session = null;

		try {
			session = openSession();

			SegmentsExperience segmentsExperience = (SegmentsExperience)session.get(SegmentsExperienceImpl.class,
					primaryKey);

			if (segmentsExperience == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchExperienceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(segmentsExperience);
		}
		catch (NoSuchExperienceException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected SegmentsExperience removeImpl(
		SegmentsExperience segmentsExperience) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(segmentsExperience)) {
				segmentsExperience = (SegmentsExperience)session.get(SegmentsExperienceImpl.class,
						segmentsExperience.getPrimaryKeyObj());
			}

			if (segmentsExperience != null) {
				session.delete(segmentsExperience);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (segmentsExperience != null) {
			clearCache(segmentsExperience);
		}

		return segmentsExperience;
	}

	@Override
	public SegmentsExperience updateImpl(SegmentsExperience segmentsExperience) {
		boolean isNew = segmentsExperience.isNew();

		if (!(segmentsExperience instanceof SegmentsExperienceModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(segmentsExperience.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(segmentsExperience);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in segmentsExperience proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SegmentsExperience implementation " +
				segmentsExperience.getClass());
		}

		SegmentsExperienceModelImpl segmentsExperienceModelImpl = (SegmentsExperienceModelImpl)segmentsExperience;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (segmentsExperience.getCreateDate() == null)) {
			if (serviceContext == null) {
				segmentsExperience.setCreateDate(now);
			}
			else {
				segmentsExperience.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!segmentsExperienceModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				segmentsExperience.setModifiedDate(now);
			}
			else {
				segmentsExperience.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (segmentsExperience.isNew()) {
				session.save(segmentsExperience);

				segmentsExperience.setNew(false);
			}
			else {
				segmentsExperience = (SegmentsExperience)session.merge(segmentsExperience);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SegmentsExperienceModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					segmentsExperienceModelImpl.getGroupId()
				};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByGroupId,
				args);

			args = new Object[] {
					segmentsExperienceModelImpl.getGroupId(),
					segmentsExperienceModelImpl.getLayoutUuid(),
					segmentsExperienceModelImpl.isActive()
				};

			finderCache.removeResult(_finderPathCountByG_L_A, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByG_L_A,
				args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(_finderPathWithoutPaginationFindAll,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((segmentsExperienceModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByGroupId.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						segmentsExperienceModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByGroupId,
					args);

				args = new Object[] { segmentsExperienceModelImpl.getGroupId() };

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByGroupId,
					args);
			}

			if ((segmentsExperienceModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByG_L_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						segmentsExperienceModelImpl.getOriginalGroupId(),
						segmentsExperienceModelImpl.getOriginalLayoutUuid(),
						segmentsExperienceModelImpl.getOriginalActive()
					};

				finderCache.removeResult(_finderPathCountByG_L_A, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByG_L_A,
					args);

				args = new Object[] {
						segmentsExperienceModelImpl.getGroupId(),
						segmentsExperienceModelImpl.getLayoutUuid(),
						segmentsExperienceModelImpl.isActive()
					};

				finderCache.removeResult(_finderPathCountByG_L_A, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByG_L_A,
					args);
			}
		}

		entityCache.putResult(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsExperienceImpl.class, segmentsExperience.getPrimaryKey(),
			segmentsExperience, false);

		clearUniqueFindersCache(segmentsExperienceModelImpl, false);
		cacheUniqueFindersCache(segmentsExperienceModelImpl);

		segmentsExperience.resetOriginalValues();

		return segmentsExperience;
	}

	/**
	 * Returns the segments experience with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the segments experience
	 * @return the segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience findByPrimaryKey(Serializable primaryKey)
		throws NoSuchExperienceException {
		SegmentsExperience segmentsExperience = fetchByPrimaryKey(primaryKey);

		if (segmentsExperience == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchExperienceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return segmentsExperience;
	}

	/**
	 * Returns the segments experience with the primary key or throws a <code>NoSuchExperienceException</code> if it could not be found.
	 *
	 * @param segmentsExperienceId the primary key of the segments experience
	 * @return the segments experience
	 * @throws NoSuchExperienceException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience findByPrimaryKey(long segmentsExperienceId)
		throws NoSuchExperienceException {
		return findByPrimaryKey((Serializable)segmentsExperienceId);
	}

	/**
	 * Returns the segments experience with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param segmentsExperienceId the primary key of the segments experience
	 * @return the segments experience, or <code>null</code> if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience fetchByPrimaryKey(long segmentsExperienceId) {
		return fetchByPrimaryKey((Serializable)segmentsExperienceId);
	}

	/**
	 * Returns all the segments experiences.
	 *
	 * @return the segments experiences
	 */
	@Override
	public List<SegmentsExperience> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<SegmentsExperience> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<SegmentsExperience> findAll(int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<SegmentsExperience> findAll(int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<SegmentsExperience> list = null;

		if (retrieveFromCache) {
			list = (List<SegmentsExperience>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SEGMENTSEXPERIENCE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SEGMENTSEXPERIENCE;

				if (pagination) {
					sql = sql.concat(SegmentsExperienceModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SegmentsExperience>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SegmentsExperience>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the segments experiences from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SegmentsExperience segmentsExperience : findAll()) {
			remove(segmentsExperience);
		}
	}

	/**
	 * Returns the number of segments experiences.
	 *
	 * @return the number of segments experiences
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(_finderPathCountAll,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SEGMENTSEXPERIENCE);

				count = (Long)q.uniqueResult();

				finderCache.putResult(_finderPathCountAll, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "segmentsExperienceId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SEGMENTSEXPERIENCE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SegmentsExperienceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the segments experience persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsExperienceModelImpl.FINDER_CACHE_ENABLED,
				SegmentsExperienceImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsExperienceModelImpl.FINDER_CACHE_ENABLED,
				SegmentsExperienceImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
				new String[0]);

		_finderPathCountAll = new FinderPath(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsExperienceModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
				new String[0]);

		_finderPathWithPaginationFindByGroupId = new FinderPath(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsExperienceModelImpl.FINDER_CACHE_ENABLED,
				SegmentsExperienceImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
				new String[] {
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsExperienceModelImpl.FINDER_CACHE_ENABLED,
				SegmentsExperienceImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
				new String[] { Long.class.getName() },
				SegmentsExperienceModelImpl.GROUPID_COLUMN_BITMASK |
				SegmentsExperienceModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsExperienceModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
				new String[] { Long.class.getName() });

		_finderPathFetchByG_L_S = new FinderPath(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsExperienceModelImpl.FINDER_CACHE_ENABLED,
				SegmentsExperienceImpl.class, FINDER_CLASS_NAME_ENTITY,
				"fetchByG_L_S",
				new String[] {
					Long.class.getName(), String.class.getName(),
					Long.class.getName()
				},
				SegmentsExperienceModelImpl.GROUPID_COLUMN_BITMASK |
				SegmentsExperienceModelImpl.LAYOUTUUID_COLUMN_BITMASK |
				SegmentsExperienceModelImpl.SEGMENTSENTRYID_COLUMN_BITMASK);

		_finderPathCountByG_L_S = new FinderPath(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsExperienceModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_L_S",
				new String[] {
					Long.class.getName(), String.class.getName(),
					Long.class.getName()
				});

		_finderPathWithPaginationFindByG_L_A = new FinderPath(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsExperienceModelImpl.FINDER_CACHE_ENABLED,
				SegmentsExperienceImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_L_A",
				new String[] {
					Long.class.getName(), String.class.getName(),
					Boolean.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByG_L_A = new FinderPath(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsExperienceModelImpl.FINDER_CACHE_ENABLED,
				SegmentsExperienceImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_L_A",
				new String[] {
					Long.class.getName(), String.class.getName(),
					Boolean.class.getName()
				},
				SegmentsExperienceModelImpl.GROUPID_COLUMN_BITMASK |
				SegmentsExperienceModelImpl.LAYOUTUUID_COLUMN_BITMASK |
				SegmentsExperienceModelImpl.ACTIVE_COLUMN_BITMASK |
				SegmentsExperienceModelImpl.PRIORITY_COLUMN_BITMASK);

		_finderPathCountByG_L_A = new FinderPath(SegmentsExperienceModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsExperienceModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_L_A",
				new String[] {
					Long.class.getName(), String.class.getName(),
					Boolean.class.getName()
				});
	}

	public void destroy() {
		entityCache.removeCache(SegmentsExperienceImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_SEGMENTSEXPERIENCE = "SELECT segmentsExperience FROM SegmentsExperience segmentsExperience";
	private static final String _SQL_SELECT_SEGMENTSEXPERIENCE_WHERE = "SELECT segmentsExperience FROM SegmentsExperience segmentsExperience WHERE ";
	private static final String _SQL_COUNT_SEGMENTSEXPERIENCE = "SELECT COUNT(segmentsExperience) FROM SegmentsExperience segmentsExperience";
	private static final String _SQL_COUNT_SEGMENTSEXPERIENCE_WHERE = "SELECT COUNT(segmentsExperience) FROM SegmentsExperience segmentsExperience WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "segmentsExperience.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SegmentsExperience exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SegmentsExperience exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SegmentsExperiencePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"active"
			});
}