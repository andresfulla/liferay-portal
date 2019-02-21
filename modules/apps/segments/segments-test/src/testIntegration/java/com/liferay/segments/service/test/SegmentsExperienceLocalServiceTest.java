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

package com.liferay.segments.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class SegmentsExperienceLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddSegmentsExperience() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());
		long classPK = RandomTestUtil.randomInt();
		long segmentsEntryId = RandomTestUtil.randomLong();
		Map<Locale, String> nameMap = RandomTestUtil.randomLocaleStringMap();
		boolean active = RandomTestUtil.randomBoolean();
		int priority = RandomTestUtil.randomInt();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				classNameId, classPK, segmentsEntryId, nameMap, active,
				priority, serviceContext);

		Assert.assertEquals(
			1,
			_segmentsExperienceLocalService.getSegmentsExperiencesCount(
				_group.getGroupId()));

		Assert.assertEquals(classNameId, segmentsExperience.getClassNameId());
		Assert.assertEquals(classPK, segmentsExperience.getClassPK());
		Assert.assertEquals(
			segmentsEntryId, segmentsExperience.getSegmentsEntryId());
		Assert.assertEquals(nameMap, segmentsExperience.getNameMap());
		Assert.assertEquals(active, segmentsExperience.isActive());
	}

	@Test
	public void testDeleteSegmentsExperience() throws Exception {
		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		_segmentsExperienceLocalService.deleteSegmentsExperience(
			segmentsExperience.getSegmentsExperienceId());

		Assert.assertNull(
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId()));
	}

	@Test
	public void testDeleteSegmentsExperienceByGroupId() throws Exception {
		int count = 5;

		for (int i = 0; i < count; i++) {
			_addSegmentsExperience();
		}

		Assert.assertEquals(
			count,
			_segmentsExperienceLocalService.getSegmentsExperiencesCount(
				_group.getGroupId()));

		_segmentsExperienceLocalService.deleteSegmentsExperiences(
			_group.getGroupId());

		Assert.assertEquals(
			0,
			_segmentsExperienceLocalService.getSegmentsExperiencesCount(
				_group.getGroupId()));
	}

	@Test
	public void testUpdateSegmentsExperience() throws Exception {
		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		long segmentsEntryId = RandomTestUtil.randomLong();
		Map<Locale, String> nameMap = RandomTestUtil.randomLocaleStringMap();
		int priority = RandomTestUtil.randomInt();

		SegmentsExperience updatedSegmentsExperience =
			_segmentsExperienceLocalService.updateSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId(), segmentsEntryId,
				nameMap, priority, true);

		Assert.assertEquals(
			segmentsEntryId, updatedSegmentsExperience.getSegmentsEntryId());
		Assert.assertEquals(nameMap, updatedSegmentsExperience.getNameMap());
		Assert.assertEquals(priority, updatedSegmentsExperience.getPriority());
	}

	private SegmentsExperience _addSegmentsExperience() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());

		return _segmentsExperienceLocalService.addSegmentsExperience(
			classNameId, RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.randomLocaleStringMap(), true,
			RandomTestUtil.randomInt(), serviceContext);
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}