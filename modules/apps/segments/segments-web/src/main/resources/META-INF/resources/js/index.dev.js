import '../css/main.scss';
import 'clay-css/lib/css/atlas.css';
import React from 'react';
import ReactDOM from 'react-dom';
import ThemeContext from './ThemeContext.es';
import {
	SUPPORTED_CONJUNCTIONS,
	SUPPORTED_OPERATORS,
	SUPPORTED_PROPERTY_TYPES,
} from './utils/constants.es';
import ContributorsBuilder from './components/criteria_builder/ContributorsBuilder.es';

const contributors = [
	{
		'inputId': '_com_liferay_segments_web_internal_portlet_SegmentsPortlet_criterionFilteruser',
		'initialQuery': '',
		'conjunctionId': '',
		'conjunctionInputId': '_com_liferay_segments_web_internal_portlet_SegmentsPortlet_criterionConjunctionuser',
		'propertyKey': 'user',
	},
	{
		'inputId': '_com_liferay_segments_web_internal_portlet_SegmentsPortlet_criterionFilteruser-organization',
		'initialQuery': '',
		'conjunctionId': '',
		'conjunctionInputId': '_com_liferay_segments_web_internal_portlet_SegmentsPortlet_criterionConjunctionuser-organization',
		'propertyKey': 'organization',
	},
];

const context = {
	'assetsPath': 'assets',
	'spritemap': '/o/admin-theme/images/lexicon/icons.svg',
};

const propertyGroups = [{
	name: 'User',
	propertyKey: 'user',
	properties: [
		{
			'name': 'ancestorOrganizationIds',
			'label': 'Ancestor Organization IDs',
			'type': 'string',
		},
		{
			'name': 'classPK',
			'label': 'Class PK',
			'type': 'string',
		},
		{
			'name': 'companyId',
			'label': 'Company ID',
			'type': 'string',
		},
		{
			'name': 'dateModified',
			'label': 'Date Modified',
			'type': 'string',
		},
		{
			'name': 'emailAddress',
			'label': 'Email Address',
			'type': 'string',
		},
		{
			'name': 'firstName',
			'label': 'First Name',
			'type': 'string',
		},
		{
			'name': 'groupId',
			'label': 'Group ID',
			'type': 'string',
		},
		{
			'name': 'groupIds',
			'label': 'Group IDs',
			'type': 'string',
		},
		{
			'name': 'jobTitle',
			'label': 'Job Title',
			'type': 'string',
		},
		{
			'name': 'lastName',
			'label': 'Last Name',
			'type': 'string',
		},
		{
			'name': 'organizationCount',
			'label': 'Organization Count',
			'type': 'string',
		},
		{
			'name': 'organizationIds',
			'label': 'Organization IDs',
			'type': 'string',
		},
		{
			'name': 'roleIds',
			'label': 'Role IDs',
			'type': 'string',
		},
		{
			'name': 'scopeGroupId',
			'label': 'Scope Group ID',
			'type': 'string',
		},
		{
			'name': 'screenName',
			'label': 'Screen Name',
			'type': 'string',
		},
		{
			'name': 'teamIds',
			'label': 'Team IDs',
			'type': 'string',
		},
		{
			'name': 'userGroupIds',
			'label': 'User Group IDs',
			'type': 'string',
		},
		{
			'name': 'userId',
			'label': 'User ID',
			'type': 'string',
		},
		{
			'name': 'userName',
			'label': 'User Name',
			'type': 'string',
		},
	],
},{
	name: 'User Organization',
	propertyKey: 'organization',
	properties: [
		{
			'name': 'classPK',
			'label': 'Class PK',
			'type': 'string',
		},
		{
			'name': 'companyId',
			'label': 'Company ID',
			'type': 'string',
		},
		{
			'name': 'dateModified',
			'label': 'Date Modified',
			'type': 'string',
		},
		{
			'name': 'name',
			'label': 'Name',
			'type': 'string',
		},
		{
			'name': 'nameTreePath',
			'label': 'Name Tree Path',
			'type': 'string',
		},
		{
			'name': 'organizationId',
			'label': 'Organization ID',
			'type': 'string',
		},
		{
			'name': 'parentOrganizationId',
			'label': 'Parent Organization ID',
			'type': 'string',
		},
		{
			'name': 'treePath',
			'label': 'Tree Path',
			'type': 'string',
		},
		{
			'name': 'type',
			'label': 'Type',
			'type': 'string',
		},
	],
}];

ReactDOM.render(
	<ThemeContext.Provider value={context}>
		<div className="segments-root">
			<ContributorsBuilder
				criterias={contributors}
				propertyGroups={propertyGroups}
				supportedConjunctions={SUPPORTED_CONJUNCTIONS}
				supportedOperators={SUPPORTED_OPERATORS}
				supportedPropertyTypes={SUPPORTED_PROPERTY_TYPES}
			/>
		</div>
	</ThemeContext.Provider>,
	document.getElementById('app')
);
