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
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

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
		Map<Locale, String> nameMap = RandomTestUtil.randomLocaleStringMap();
		Layout layout = LayoutTestUtil.addLayout(_group.getGroupId());
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				nameMap, layout.getUuid(), segmentsEntry.getSegmentsEntryId(),
				true, serviceContext);

		Assert.assertEquals(nameMap, segmentsExperience.getNameMap());
		Assert.assertEquals(
			layout.getUuid(), segmentsExperience.getLayoutUuid());
		Assert.assertEquals(
			segmentsEntry.getSegmentsEntryId(),
			segmentsExperience.getSegmentsEntryId());
		Assert.assertTrue(segmentsExperience.isActive());
		Assert.assertEquals(
			1, _segmentsExperienceLocalService.getSegmentsExperiencesCount());
	}

	@Test
	public void testDeleteSegmentsExperience() throws Exception {

		SegmentsExperience segmentsExperience = addSegmentsExperience();

		_segmentsExperienceLocalService.deleteSegmentsExperience(
			segmentsExperience.getSegmentsExperienceId());

		Assert.assertEquals(
			0, _segmentsExperienceLocalService.getSegmentsExperiencesCount());
	}

	private SegmentsExperience addSegmentsExperience() throws Exception {
		Map<Locale, String> nameMap = RandomTestUtil.randomLocaleStringMap();
		Layout layout = LayoutTestUtil.addLayout(_group.getGroupId());
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		return _segmentsExperienceLocalService.addSegmentsExperience(
			nameMap, layout.getUuid(), segmentsEntry.getSegmentsEntryId(),
			true, serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}