import React from 'react';
import ReactDOM from 'react-dom';
import SegmentEdit from './components/segment_edit/SegmentEdit.es';
import ThemeContext from './ThemeContext.es';

const altProps = {
	'contributors': [
		{
			'conjunctionId': '',
			'conjunctionInputId': '_com_liferay_segments_web_internal_portlet_SegmentsPortlet_criterionConjunctionuser',
			'initialQuery': '',
			'inputId': '_com_liferay_segments_web_internal_portlet_SegmentsPortlet_criterionFilteruser',
			'propertyKey': 'user'
		},
		{
			'conjunctionId': '',
			'conjunctionInputId': '_com_liferay_segments_web_internal_portlet_SegmentsPortlet_criterionConjunctionuser-organization',
			'initialQuery': '',
			'inputId': '_com_liferay_segments_web_internal_portlet_SegmentsPortlet_criterionFilteruser-organization',
			'propertyKey': 'user-organization'
		}
	],
	'initialMembersCount': 0,
	'initialSegmentActive': false,
	'initialSegmentName': '',
	'locale': 'en_US',
	'portletNamespace': '_com_liferay_segments_web_internal_portlet_SegmentsPortlet_',
	'propertyGroups': [{
		name: 'User',
		properties: [{
			'label': 'Ancestor Organization IDs',
			'name': 'ancestorOrganizationIds',
			'type': 'string'
		},
		{
			'label': 'Class PK',
			'name': 'classPK',
			'type': 'string'
		},
		{
			'label': 'Company ID',
			'name': 'companyId',
			'type': 'string'
		},
		{
			'label': 'Date Modified',
			'name': 'dateModified',
			'type': 'date'
		},
		{
			'label': 'Email Address',
			'name': 'emailAddress',
			'type': 'string'
		},
		{
			'label': 'First Name',
			'name': 'firstName',
			'type': 'string'
		},
		{
			'label': 'Group ID',
			'name': 'groupId',
			'type': 'string'
		},
		{
			'label': 'Group IDs',
			'name': 'groupIds',
			'type': 'string'
		},
		{
			'label': 'Job Title',
			'name': 'jobTitle',
			'type': 'string'
		},
		{
			'label': 'Last Name',
			'name': 'lastName',
			'type': 'string'
		},
		{
			'label': 'Organization Count',
			'name': 'organizationCount',
			'type': 'string'
		},
		{
			'label': 'Organization IDs',
			'name': 'organizationIds',
			'type': 'string'
		},
		{
			'label': 'Role IDs',
			'name': 'roleIds',
			'type': 'string'
		},
		{
			'label': 'Scope Group ID',
			'name': 'scopeGroupId',
			'type': 'string'
		},
		{
			'label': 'Screen Name',
			'name': 'screenName',
			'type': 'string'
		},
		{
			'label': 'Team IDs',
			'name': 'teamIds',
			'type': 'string'
		},
		{
			'label': 'User Group IDs',
			'name': 'userGroupIds',
			'type': 'string'
		},
		{
			'label': 'User ID',
			'name': 'userId',
			'type': 'string'
		},
		{
			'label': 'User Name',
			'name': 'userName',
			'type': 'string'
		}],
		propertyKey: 'user'
	}, {
		name: 'User Organization',
		properties: [{
			'label': 'Class PK',
			'name': 'classPK',
			'type': 'string'
		},
		{
			'label': 'Company ID',
			'name': 'companyId',
			'type': 'string'
		},
		{
			'label': 'Date Modified',
			'name': 'dateModified',
			'type': 'date'
		},
		{
			'label': 'Name',
			'name': 'name',
			'type': 'string'
		},
		{
			'label': 'Name Tree Path',
			'name': 'nameTreePath',
			'type': 'string'
		},
		{
			'label': 'Organization ID',
			'name': 'organizationId',
			'type': 'id',
			'selectEntity': {
				'id': '_com_liferay_users_admin_web_portlet_selectOrganization',
				'title': 'Select Organization',
				'uri': 'http://localhost:8080/group/control_panel/manage?p_p_id=com_liferay_users_admin_web_portlet_UsersAdminPortlet&p_p_lifecycle=0&p_p_state=pop_up&_com_liferay_users_admin_web_portlet_UsersAdminPortlet_mvcPath=%2Fselect_organization.jsp'
			}
		},
		{
			'label': 'Parent Organization ID',
			'name': 'parentOrganizationId',
			'type': 'string',
		},
		{
			'label': 'Tree Path',
			'name': 'treePath',
			'type': 'string'
		},
		{
			'label': 'Type',
			'name': 'type',
			'type': 'string',
			'options': [
				{
					label: 'User',
					value: 'user'
				},
				{
					label: 'Organization',
					value: 'organization'
				}
			]
		}],
		propertyKey: 'user-organization'
	}],
	'redirect': 'http://localhost:8080/group/guest/~/control_panel/manage/-/segments/entries?p_p_auth=1EwOzg1e'
};

export default function(id, props, context) {
	ReactDOM.render(
		<ThemeContext.Provider value={context}>
			<div className="segments-root">
				<SegmentEdit {...altProps} />
			</div>
		</ThemeContext.Provider>,
		document.getElementById(id)
	);
}