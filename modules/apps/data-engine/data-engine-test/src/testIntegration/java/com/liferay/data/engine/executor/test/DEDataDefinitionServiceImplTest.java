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

package com.liferay.data.engine.executor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.service.DEDataDefinitionCountRequest;
import com.liferay.data.engine.service.DEDataDefinitionCountResponse;
import com.liferay.data.engine.service.DEDataDefinitionDeleteRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetResponse;
import com.liferay.data.engine.service.DEDataDefinitionListRequest;
import com.liferay.data.engine.service.DEDataDefinitionListResponse;
import com.liferay.data.engine.service.DEDataDefinitionRequestBuilder;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(Arquillian.class)
public class DEDataDefinitionServiceImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupOwnerUser(_group);

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testCount() throws Exception {
		Map<String, String> field1Labels = new HashMap() {
			{
				put("en_US", "Field 1");
			}
		};

		DEDataDefinitionField deDataDefinitionField1 =
			new DEDataDefinitionField("field1", "string");

		deDataDefinitionField1.addLabels(field1Labels);

		DEDataDefinition deDataDefinition1 = new DEDataDefinition(
			Arrays.asList(deDataDefinitionField1));

		deDataDefinition1.addName(LocaleUtil.US, "Definition Test 1");
		deDataDefinition1.setStorageType("json");

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequest1 =
			DEDataDefinitionRequestBuilder.saveBuilder(
				deDataDefinition1
			).onBehalfOf(
				_user.getUserId()
			).inGroup(
				_group.getGroupId()
			).build();

		Map<String, String> field2Labels = new HashMap() {
			{
				put("en_US", "Field 2");
			}
		};

		DEDataDefinitionField deDataDefinitionField2 =
			new DEDataDefinitionField("field2", "number");

		deDataDefinitionField2.addLabels(field2Labels);

		DEDataDefinition deDataDefinition2 = new DEDataDefinition(
			Arrays.asList(deDataDefinitionField2));

		deDataDefinition2.addName(LocaleUtil.US, "Definition Test 2");
		deDataDefinition2.setStorageType("json");

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequest2 =
			DEDataDefinitionRequestBuilder.saveBuilder(
				deDataDefinition2
			).onBehalfOf(
				_user.getUserId()
			).inGroup(
				_group.getGroupId()
			).build();

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			_deDataDefinitionService.execute(deDataDefinitionSaveRequest1);

			_deDataDefinitionService.execute(deDataDefinitionSaveRequest2);

			DEDataDefinitionCountRequest deDataDefinitionCountRequest =
				DEDataDefinitionRequestBuilder.countBuilder(
				).inCompany(
					_group.getCompanyId()
				).inGroup(
					_group.getGroupId()
				).build();

			DEDataDefinitionCountResponse deDataDefinitionCountResponse =
				_deDataDefinitionService.execute(deDataDefinitionCountRequest);

			Assert.assertEquals(2, deDataDefinitionCountResponse.getTotal());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testCountAfterDelete() throws Exception {
		Map<String, String> field1Labels = new HashMap() {
			{
				put("en_US", "Field 1");
			}
		};

		DEDataDefinitionField deDataDefinitionField1 =
			new DEDataDefinitionField("field1", "string");

		deDataDefinitionField1.addLabels(field1Labels);

		DEDataDefinition deDataDefinition1 = new DEDataDefinition(
			Arrays.asList(deDataDefinitionField1));

		deDataDefinition1.addName(LocaleUtil.US, "Definition Test 1");
		deDataDefinition1.setStorageType("json");

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequest1 =
			DEDataDefinitionRequestBuilder.saveBuilder(
				deDataDefinition1
			).onBehalfOf(
				_user.getUserId()
			).inGroup(
				_group.getGroupId()
			).build();

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deDataDefinitionService.execute(deDataDefinitionSaveRequest1);

			long deDataDefinitionId =
				deDataDefinitionSaveResponse.getDEDataDefinitionId();

			DEDataDefinitionCountRequest deDataDefinitionCountRequest =
				DEDataDefinitionRequestBuilder.countBuilder(
				).inCompany(
					_group.getCompanyId()
				).inGroup(
					_group.getGroupId()
				).build();

			DEDataDefinitionCountResponse deDataDefinitionCountResponse =
				_deDataDefinitionService.execute(deDataDefinitionCountRequest);

			Assert.assertEquals(1, deDataDefinitionCountResponse.getTotal());

			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
				DEDataDefinitionRequestBuilder.deleteBuilder(
				).byId(
					deDataDefinitionId
				).build();

			_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);

			DEDataDefinitionCountRequest
				deDataDefinitionCountRequestAfterDelete =
					DEDataDefinitionRequestBuilder.countBuilder(
					).inCompany(
						_group.getCompanyId()
					).inGroup(
						_group.getGroupId()
					).build();

			DEDataDefinitionCountResponse
				deDataDefinitionCountResponseAfterDelete =
					_deDataDefinitionService.execute(
						deDataDefinitionCountRequestAfterDelete);

			Assert.assertEquals(
				0, deDataDefinitionCountResponseAfterDelete.getTotal());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testCountWithNoRecords() throws Exception {
		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionCountRequest deDataDefinitionCountRequest =
				DEDataDefinitionRequestBuilder.countBuilder(
				).inCompany(
					_group.getCompanyId()
				).inGroup(
					_group.getGroupId()
				).build();

			DEDataDefinitionCountResponse deDataDefinitionCountResponse =
				_deDataDefinitionService.execute(deDataDefinitionCountRequest);

			Assert.assertEquals(0, deDataDefinitionCountResponse.getTotal());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testCountWithoutPermission() throws Exception {
		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			Map<String, String> field1Labels = new HashMap() {
				{
					put("en_US", "Field 1");
				}
			};

			DEDataDefinitionField deDataDefinitionField1 =
				new DEDataDefinitionField("field1", "string");

			deDataDefinitionField1.addLabels(field1Labels);

			DEDataDefinition deDataDefinition1 = new DEDataDefinition(
				Arrays.asList(deDataDefinitionField1));

			deDataDefinition1.addName(LocaleUtil.US, "Definition Test 1");
			deDataDefinition1.setStorageType("json");

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					deDataDefinition1
				).onBehalfOf(
					_user.getUserId()
				).inGroup(
					_group.getGroupId()
				).build();

			_deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			Group otherGroup = GroupTestUtil.addGroup();

			DEDataDefinitionCountRequest deDataDefinitionCountRequest =
				DEDataDefinitionRequestBuilder.countBuilder(
				).inCompany(
					otherGroup.getCompanyId()
				).inGroup(
					otherGroup.getGroupId()
				).build();

			DEDataDefinitionCountResponse deDataDefinitionCountResponse =
				_deDataDefinitionService.execute(deDataDefinitionCountRequest);

			Assert.assertEquals(0, deDataDefinitionCountResponse.getTotal());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataDefinitionException.NoSuchDataDefinition.class)
	public void testDelete() throws Exception {
		Map<String, String> expectedNameLabels = new HashMap() {
			{
				put("pt_BR", "Nome");
				put("en_US", "Name");
			}
		};

		DEDataDefinitionField deDataDefinitionField = new DEDataDefinitionField(
			"name", "string");

		deDataDefinitionField.addLabels(expectedNameLabels);

		DEDataDefinition deDataDefinition = new DEDataDefinition(
			Arrays.asList(deDataDefinitionField));

		deDataDefinition.addName(LocaleUtil.US, "Definition 1");
		deDataDefinition.setStorageType("json");

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
			DEDataDefinitionRequestBuilder.saveBuilder(
				deDataDefinition
			).onBehalfOf(
				_user.getUserId()
			).inGroup(
				_group.getGroupId()
			).build();

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			long deDataDefinitionId =
				deDataDefinitionSaveResponse.getDEDataDefinitionId();

			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
				DEDataDefinitionRequestBuilder.deleteBuilder(
				).byId(
					deDataDefinitionId
				).build();

			_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);

			DEDataDefinitionGetRequest deDataDefinitionGetRequest =
				DEDataDefinitionRequestBuilder.getBuilder(
				).byId(
					deDataDefinitionId
				).build();

			_deDataDefinitionService.execute(deDataDefinitionGetRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testGet() throws Exception {
		Map<String, String> field1Labels = new HashMap() {
			{
				put("en_US", "Field 1");
			}
		};

		DEDataDefinitionField deDataDefinitionField1 =
			new DEDataDefinitionField("field1", "string");

		deDataDefinitionField1.addLabels(field1Labels);

		Map<String, String> field2Labels = new HashMap() {
			{
				put("en_US", "Field 2");
			}
		};

		DEDataDefinitionField deDataDefinitionField2 =
			new DEDataDefinitionField("field2", "number");

		deDataDefinitionField2.addLabels(field2Labels);

		Map<String, String> field3Labels = new HashMap() {
			{
				put("en_US", "Field 3");
			}
		};

		DEDataDefinitionField deDataDefinitionField3 =
			new DEDataDefinitionField("field3", "date");

		deDataDefinitionField3.addLabels(field3Labels);

		DEDataDefinition expectedDEDataDefinition = new DEDataDefinition(
			Arrays.asList(
				deDataDefinitionField1, deDataDefinitionField2,
				deDataDefinitionField3));

		expectedDEDataDefinition.addName(LocaleUtil.US, "Definition 2");
		expectedDEDataDefinition.setStorageType("json");

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
			DEDataDefinitionRequestBuilder.saveBuilder(
				expectedDEDataDefinition
			).onBehalfOf(
				_user.getUserId()
			).inGroup(
				_group.getGroupId()
			).build();

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			long deDataDefinitionId =
				deDataDefinitionSaveResponse.getDEDataDefinitionId();

			expectedDEDataDefinition.setPrimaryKeyObj(deDataDefinitionId);

			DEDataDefinitionGetRequest deDataDefinitionGetRequest =
				DEDataDefinitionRequestBuilder.getBuilder(
				).byId(
					deDataDefinitionId
				).build();

			DEDataDefinitionGetResponse deDataDefinitionGetResponse =
				_deDataDefinitionService.execute(deDataDefinitionGetRequest);

			Assert.assertEquals(
				expectedDEDataDefinition,
				deDataDefinitionGetResponse.getDeDataDefinition());

			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
				DEDataDefinitionRequestBuilder.deleteBuilder(
				).byId(
					deDataDefinitionId
				).build();

			_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testInsert() throws Exception {
		Map<String, String> expectedNameLabels = new HashMap() {
			{
				put("pt_BR", "Nome");
				put("en_US", "Name");
			}
		};

		DEDataDefinitionField expectedDEDataDefinitionField1 =
			new DEDataDefinitionField("name", "string");

		expectedDEDataDefinitionField1.addLabels(expectedNameLabels);

		Map<String, String> expectedEmailLabels = new HashMap() {
			{
				put("pt_BR", "Endereço de Email");
				put("en_US", "Email Address");
			}
		};

		DEDataDefinitionField expectedDEDataDefinitionField2 =
			new DEDataDefinitionField("email", "string");

		expectedDEDataDefinitionField1.addLabels(expectedEmailLabels);

		DEDataDefinition expectedDEDataDefinition = new DEDataDefinition(
			Arrays.asList(
				expectedDEDataDefinitionField1,
				expectedDEDataDefinitionField2));

		expectedDEDataDefinition.addDescription(
			LocaleUtil.US, "Contact description");
		expectedDEDataDefinition.addDescription(
			LocaleUtil.BRAZIL, "Descrição do contato");
		expectedDEDataDefinition.addName(LocaleUtil.US, "Contact");
		expectedDEDataDefinition.addName(LocaleUtil.BRAZIL, "Contato");
		expectedDEDataDefinition.setStorageType("json");

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
			DEDataDefinitionRequestBuilder.saveBuilder(
				expectedDEDataDefinition
			).onBehalfOf(
				_user.getUserId()
			).inGroup(
				_group.getGroupId()
			).build();

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			long deDataDefinitionId =
				deDataDefinitionSaveResponse.getDEDataDefinitionId();

			expectedDEDataDefinition.setPrimaryKeyObj(deDataDefinitionId);

			DEDataDefinitionGetRequest deDataDefinitionGetRequest =
				DEDataDefinitionRequestBuilder.getBuilder(
				).byId(
					deDataDefinitionId
				).build();

			DEDataDefinitionGetResponse deDataDefinitionGetResponse =
				_deDataDefinitionService.execute(deDataDefinitionGetRequest);

			DEDataDefinition deDataDefinition =
				deDataDefinitionGetResponse.getDeDataDefinition();

			Assert.assertEquals(expectedDEDataDefinition, deDataDefinition);

			Role ownerRole = _roleLocalService.getRole(
				_group.getCompanyId(), RoleConstants.OWNER);

			ResourcePermission resourcePermission =
				_resourcePermissionLocalService.fetchResourcePermission(
					_group.getCompanyId(), DEDataDefinition.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(deDataDefinitionId), ownerRole.getRoleId());

			Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));

			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
				DEDataDefinitionRequestBuilder.deleteBuilder(
				).byId(
					deDataDefinitionId
				).build();

			_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testListPaginatedMiddleInsideRange() throws Exception {
		int dataDefinitionTotal = 5;

		List<DEDataDefinition> deDataDefinitionsExpected = new ArrayList<>();

		List<DEDataDefinitionSaveRequest> dataDefinitionSaveRequests =
			new ArrayList<>();

		for (int i = 0; i < dataDefinitionTotal; i++) {
			DEDataDefinition dataDefinition = createDataDefinitionDefault(i);

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					dataDefinition
				).onBehalfOf(
					_user.getUserId()
				).inGroup(
					_group.getGroupId()
				).build();

			deDataDefinitionsExpected.add(dataDefinition);

			dataDefinitionSaveRequests.add(deDataDefinitionSaveRequest);
		}

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			for (DEDataDefinitionSaveRequest deDataDefinitionSaveRequest :
					dataDefinitionSaveRequests) {

				_deDataDefinitionService.execute(deDataDefinitionSaveRequest);
			}

			DEDataDefinitionListRequest deDataDefinitionListRequest =
				DEDataDefinitionRequestBuilder.listBuilder(
				).startingAt(
					2
				).endingAt(
					4
				).inCompany(
					_group.getCompanyId()
				).inGroup(
					_group.getGroupId()
				).build();

			DEDataDefinitionListResponse deDataDefinitionListResponse =
				_deDataDefinitionService.execute(deDataDefinitionListRequest);

			Assert.assertEquals(
				2, deDataDefinitionListResponse.getDEDataDefinitions().size());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testListPaginatedMiddleOutOfRange() throws Exception {
		int dataDefinitionTotal = 5;

		List<DEDataDefinition> deDataDefinitionsExpected = new ArrayList<>();

		List<DEDataDefinitionSaveRequest> dataDefinitionSaveRequests =
			new ArrayList<>();

		for (int i = 0; i < dataDefinitionTotal; i++) {
			DEDataDefinition dataDefinition = createDataDefinitionDefault(i);

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					dataDefinition
				).onBehalfOf(
					_user.getUserId()
				).inGroup(
					_group.getGroupId()
				).build();

			deDataDefinitionsExpected.add(dataDefinition);

			dataDefinitionSaveRequests.add(deDataDefinitionSaveRequest);
		}

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			for (DEDataDefinitionSaveRequest deDataDefinitionSaveRequest :
					dataDefinitionSaveRequests) {

				_deDataDefinitionService.execute(deDataDefinitionSaveRequest);
			}

			DEDataDefinitionListRequest deDataDefinitionListRequest =
				DEDataDefinitionRequestBuilder.listBuilder(
				).startingAt(
					3
				).endingAt(
					7
				).inCompany(
					_group.getCompanyId()
				).inGroup(
					_group.getGroupId()
				).build();

			DEDataDefinitionListResponse deDataDefinitionListResponse =
				_deDataDefinitionService.execute(deDataDefinitionListRequest);

			Assert.assertEquals(
				2, deDataDefinitionListResponse.getDEDataDefinitions().size());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testListPaginatedOutOfRange() throws Exception {
		int dataDefinitionTotal = 5;

		List<DEDataDefinition> deDataDefinitionsExpected = new ArrayList<>();

		List<DEDataDefinitionSaveRequest> dataDefinitionSaveRequests =
			new ArrayList<>();

		for (int i = 0; i < dataDefinitionTotal; i++) {
			DEDataDefinition dataDefinition = createDataDefinitionDefault(i);

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					dataDefinition
				).onBehalfOf(
					_user.getUserId()
				).inGroup(
					_group.getGroupId()
				).build();

			deDataDefinitionsExpected.add(dataDefinition);

			dataDefinitionSaveRequests.add(deDataDefinitionSaveRequest);
		}

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			for (DEDataDefinitionSaveRequest deDataDefinitionSaveRequest :
					dataDefinitionSaveRequests) {

				_deDataDefinitionService.execute(deDataDefinitionSaveRequest);
			}

			DEDataDefinitionListRequest deDataDefinitionListRequest =
				DEDataDefinitionRequestBuilder.listBuilder(
				).startingAt(
					7
				).endingAt(
					10
				).inCompany(
					_group.getCompanyId()
				).inGroup(
					_group.getGroupId()
				).build();

			DEDataDefinitionListResponse deDataDefinitionListResponse =
				_deDataDefinitionService.execute(deDataDefinitionListRequest);

			Assert.assertEquals(
				0, deDataDefinitionListResponse.getDEDataDefinitions().size());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testListWithNoRecords() throws Exception {
		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionListRequest deDataDefinitionListRequest =
				DEDataDefinitionRequestBuilder.listBuilder(
				).inCompany(
					_group.getCompanyId()
				).inGroup(
					_group.getGroupId()
				).build();

			DEDataDefinitionListResponse deDataDefinitionListResponse =
				_deDataDefinitionService.execute(deDataDefinitionListRequest);

			Assert.assertTrue(
				deDataDefinitionListResponse.getDEDataDefinitions().isEmpty());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testListWithRecords() throws Exception {
		DEDataDefinition deDataDefinitionContact =
			createDataDefinitionContact();
		DEDataDefinition deDataDefinitionPet = createDataDefinitionPet();
		DEDataDefinition deDataDefinitionOrder = createDataDefinitionOrder();

		List<DEDataDefinition> deDataDefinitionsExpected = new ArrayList<>();

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequestContact =
			DEDataDefinitionRequestBuilder.saveBuilder(
				deDataDefinitionContact
			).onBehalfOf(
				_user.getUserId()
			).inGroup(
				_group.getGroupId()
			).build();

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequestPet =
			DEDataDefinitionRequestBuilder.saveBuilder(
				deDataDefinitionPet
			).onBehalfOf(
				_user.getUserId()
			).inGroup(
				_group.getGroupId()
			).build();

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequestOrder =
			DEDataDefinitionRequestBuilder.saveBuilder(
				deDataDefinitionOrder
			).onBehalfOf(
				_user.getUserId()
			).inGroup(
				_group.getGroupId()
			).build();

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponseContact =
				_deDataDefinitionService.execute(
					deDataDefinitionSaveRequestContact);

			deDataDefinitionContact.setPrimaryKeyObj(
				deDataDefinitionSaveResponseContact.getDEDataDefinitionId());

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponsePet =
				_deDataDefinitionService.execute(
					deDataDefinitionSaveRequestPet);

			deDataDefinitionPet.setPrimaryKeyObj(
				deDataDefinitionSaveResponsePet.getDEDataDefinitionId());

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponseOrder =
				_deDataDefinitionService.execute(
					deDataDefinitionSaveRequestOrder);

			deDataDefinitionOrder.setPrimaryKeyObj(
				deDataDefinitionSaveResponseOrder.getDEDataDefinitionId());

			deDataDefinitionsExpected.add(deDataDefinitionContact);
			deDataDefinitionsExpected.add(deDataDefinitionPet);
			deDataDefinitionsExpected.add(deDataDefinitionOrder);

			DEDataDefinitionListRequest deDataDefinitionListRequest =
				DEDataDefinitionRequestBuilder.listBuilder(
				).inCompany(
					_group.getCompanyId()
				).inGroup(
					_group.getGroupId()
				).build();

			DEDataDefinitionListResponse deDataDefinitionListResponse =
				_deDataDefinitionService.execute(deDataDefinitionListRequest);

			Assert.assertEquals(
				3, deDataDefinitionListResponse.getDEDataDefinitions().size());

			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequestContact =
				DEDataDefinitionRequestBuilder.deleteBuilder(
				).byId(
					deDataDefinitionSaveResponseContact.getDEDataDefinitionId()
				).build();

			_deDataDefinitionService.execute(
				deDataDefinitionDeleteRequestContact);

			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequestPet =
				DEDataDefinitionRequestBuilder.deleteBuilder(
				).byId(
					deDataDefinitionSaveResponsePet.getDEDataDefinitionId()
				).build();

			_deDataDefinitionService.execute(deDataDefinitionDeleteRequestPet);

			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequestOrder =
				DEDataDefinitionRequestBuilder.deleteBuilder(
				).byId(
					deDataDefinitionSaveResponseOrder.getDEDataDefinitionId()
				).build();

			_deDataDefinitionService.execute(
				deDataDefinitionDeleteRequestOrder);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testUpdate() throws Exception {
		Map<String, String> expectedTitleLabels = new HashMap() {
			{
				put("pt_BR", "Título");
				put("en_US", "Title");
			}
		};

		DEDataDefinitionField deDataDefinitionField1 =
			new DEDataDefinitionField("title", "string");

		deDataDefinitionField1.addLabels(expectedTitleLabels);
		deDataDefinitionField1.setLocalizable(true);

		DEDataDefinition expectedDEDataDefinition = new DEDataDefinition(
			Arrays.asList(deDataDefinitionField1));

		expectedDEDataDefinition.addName(LocaleUtil.US, "Story");
		expectedDEDataDefinition.addName(LocaleUtil.BRAZIL, "Estória");
		expectedDEDataDefinition.setStorageType("json");

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
			DEDataDefinitionRequestBuilder.saveBuilder(
				expectedDEDataDefinition
			).onBehalfOf(
				_user.getUserId()
			).inGroup(
				_group.getGroupId()
			).build();

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			long deDataDefinitionId =
				deDataDefinitionSaveResponse.getDEDataDefinitionId();

			expectedDEDataDefinition.setPrimaryKeyObj(deDataDefinitionId);

			Map<String, String> expectedDescriptionLabels = new HashMap() {
				{
					put("pt_BR", "Descrição");
					put("en_US", "Description");
				}
			};

			DEDataDefinitionField deDataDefinitionField2 =
				new DEDataDefinitionField("description", "string");

			deDataDefinitionField2.addLabels(expectedDescriptionLabels);
			deDataDefinitionField2.setLocalizable(true);

			expectedDEDataDefinition = new DEDataDefinition(
				Arrays.asList(deDataDefinitionField1, deDataDefinitionField2));

			expectedDEDataDefinition.setDEDataDefinitionId(deDataDefinitionId);
			expectedDEDataDefinition.addName(LocaleUtil.US, "Story");
			expectedDEDataDefinition.addName(LocaleUtil.BRAZIL, "Estória");
			expectedDEDataDefinition.setStorageType("json");

			deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					expectedDEDataDefinition
				).onBehalfOf(
					_user.getUserId()
				).inGroup(
					_group.getGroupId()
				).build();

			_deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			DEDataDefinitionGetRequest deDataDefinitionGetRequest =
				DEDataDefinitionRequestBuilder.getBuilder(
				).byId(
					deDataDefinitionId
				).build();

			DEDataDefinitionGetResponse deDataDefinitionGetResponse =
				_deDataDefinitionService.execute(deDataDefinitionGetRequest);

			DEDataDefinition deDataDefinition =
				deDataDefinitionGetResponse.getDeDataDefinition();

			Assert.assertEquals(expectedDEDataDefinition, deDataDefinition);

			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
				DEDataDefinitionRequestBuilder.deleteBuilder(
				).byId(
					deDataDefinitionId
				).build();

			_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected DEDataDefinition createDataDefinitionContact() {
		Map<String, String> field1Labels = new HashMap() {
			{
				put("en_US", "Name");
			}
		};

		DEDataDefinitionField deDataDefinitionField1 =
			new DEDataDefinitionField("name", "string");

		deDataDefinitionField1.addLabels(field1Labels);

		Map<String, String> field2Labels = new HashMap() {
			{
				put("en_US", "Email Address");
			}
		};

		DEDataDefinitionField deDataDefinitionField2 =
			new DEDataDefinitionField("email", "string");

		deDataDefinitionField2.addLabels(field2Labels);

		Map<String, String> field3Labels = new HashMap() {
			{
				put("en_US", "Phone Number");
			}
		};

		DEDataDefinitionField deDataDefinitionField3 =
			new DEDataDefinitionField("phone", "number");

		deDataDefinitionField3.addLabels(field3Labels);

		DEDataDefinition deDataDefinition3 = new DEDataDefinition(
			Arrays.asList(
				deDataDefinitionField1, deDataDefinitionField2,
				deDataDefinitionField3));

		deDataDefinition3.addName(LocaleUtil.US, "Contact");
		deDataDefinition3.setStorageType("json");

		return deDataDefinition3;
	}

	protected DEDataDefinition createDataDefinitionDefault(int instance) {
		Map<String, String> field1Labels = new HashMap() {
			{
				put("en_US", "Field Default");
			}
		};

		DEDataDefinitionField deDataDefinitionField = new DEDataDefinitionField(
			"fieldDefault", "string");

		deDataDefinitionField.addLabels(field1Labels);

		DEDataDefinition deDataDefinition = new DEDataDefinition(
			Arrays.asList(deDataDefinitionField));

		deDataDefinition.addName(LocaleUtil.US, "Default" + instance);
		deDataDefinition.setStorageType("json");

		return deDataDefinition;
	}

	protected DEDataDefinition createDataDefinitionOrder() {
		Map<String, String> field1Labels = new HashMap() {
			{
				put("en_US", "Order Number");
			}
		};

		DEDataDefinitionField deDataDefinitionField1 =
			new DEDataDefinitionField("orderNumber", "number");

		deDataDefinitionField1.addLabels(field1Labels);

		DEDataDefinition deDataDefinition5 = new DEDataDefinition(
			Arrays.asList(deDataDefinitionField1));

		deDataDefinition5.addName(LocaleUtil.US, "Order");
		deDataDefinition5.setStorageType("json");

		return deDataDefinition5;
	}

	protected DEDataDefinition createDataDefinitionPet() {
		Map<String, String> field1Labels = new HashMap() {
			{
				put("en_US", "Name");
			}
		};

		DEDataDefinitionField deDataDefinitionField1 =
			new DEDataDefinitionField("name", "string");

		deDataDefinitionField1.addLabels(field1Labels);

		Map<String, String> field2Labels = new HashMap() {
			{
				put("en_US", "Breed");
			}
		};

		DEDataDefinitionField deDataDefinitionField2 =
			new DEDataDefinitionField("breed", "string");

		deDataDefinitionField2.addLabels(field2Labels);

		DEDataDefinition deDataDefinition4 = new DEDataDefinition(
			Arrays.asList(deDataDefinitionField1, deDataDefinitionField2));

		deDataDefinition4.addName(LocaleUtil.US, "Pet");
		deDataDefinition4.setStorageType("json");

		return deDataDefinition4;
	}

	protected ModelPermissions createModelPermissions() {
		ModelPermissions modelPermissions = new ModelPermissions();

		modelPermissions.addRolePermissions(
			RoleConstants.OWNER, ActionKeys.VIEW);

		return modelPermissions;
	}

	protected ServiceContext createServiceContext(
		Group group, User user, ModelPermissions modelPermissions) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);
		serviceContext.setCompanyId(group.getCompanyId());
		serviceContext.setModelPermissions(modelPermissions);
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	@Inject(type = DEDataDefinitionService.class)
	private DEDataDefinitionService _deDataDefinitionService;

	@DeleteAfterTestRun
	private Group _group;

	private PermissionChecker _originalPermissionChecker;

	@Inject(type = ResourcePermissionLocalService.class)
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject(type = RoleLocalService.class)
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

}