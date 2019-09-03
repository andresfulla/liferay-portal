<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
EditSegmentsEntryDisplayContext editSegmentsEntryDisplayContext = (EditSegmentsEntryDisplayContext)request.getAttribute(SegmentsWebKeys.EDIT_SEGMENTS_ENTRY_DISPLAY_CONTEXT);

String redirect = ParamUtil.getString(request, "redirect", editSegmentsEntryDisplayContext.getRedirect());

String backURL = ParamUtil.getString(request, "backURL", redirect);

SegmentsEntry segmentsEntry = editSegmentsEntryDisplayContext.getSegmentsEntry();

long segmentsEntryId = editSegmentsEntryDisplayContext.getSegmentsEntryId();

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}

renderResponse.setTitle(editSegmentsEntryDisplayContext.getTitle(locale));
%>

<liferay-ui:error embed="<%= false %>" exception="<%= SegmentsEntryCriteriaException.class %>" message="invalid-criteria" />
<liferay-ui:error embed="<%= false %>" exception="<%= SegmentsEntryKeyException.class %>" message="key-is-already-used" />
<liferay-ui:error embed="<%= false %>" exception="<%= SegmentsEntryNameException.class %>" message="please-enter-a-valid-name" />

<portlet:actionURL name="updateSegmentsEntry" var="updateSegmentsEntryActionURL" />

<aui:form action="<%= updateSegmentsEntryActionURL %>" method="post" name="editSegmentFm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveSegmentsEntry();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="segmentsEntryId" type="hidden" value="<%= segmentsEntryId %>" />
	<aui:input name="segmentsEntryKey" type="hidden" value="<%= editSegmentsEntryDisplayContext.getSegmentsEntryKey() %>" />
	<aui:input name="type" type="hidden" value="<%= editSegmentsEntryDisplayContext.getType() %>" />
	<aui:input name="dynamic" type="hidden" value="<%= true %>" />

	<%
	String segmentEditRootElementId = renderResponse.getNamespace() + "-segment-edit-root";
	%>

	<div id="<%= segmentEditRootElementId %>">
		<div class="inline-item my-5 p-5 w-100">
			<span aria-hidden="true" class="loading-animation"></span>
		</div>

		<portlet:renderURL var="previewMembersURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="previewSegmentsEntryUsers" />
			<portlet:param name="segmentsEntryId" value="<%= String.valueOf(segmentsEntryId) %>" />
		</portlet:renderURL>

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getSegmentsEntryClassPKsCount" var="getSegmentsEntryClassPKsCountURL" />
		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getSegmentsFieldValueName" var="getSegmentsFieldValueNameURL" />

		<%
		Map<String, Object> context = new HashMap<>();
		context.put("assetsPath", PortalUtil.getPathContext(request) + "/assets");
		context.put("namespace", renderResponse.getNamespace());
		context.put("requestFieldValueNameURL", getSegmentsFieldValueNameURL);

		Map<String, String> availableLocales = new HashMap<>();

		for (Locale availableLocale : editSegmentsEntryDisplayContext.getAvailableLocales()) {
			String availableLanguageId = LocaleUtil.toLanguageId(availableLocale);

			availableLocales.put(availableLanguageId, availableLocale.getDisplayName(locale));
		}

		Map<String, Object> props = new HashMap<>();
		props.put("availableLocales", availableLocales);
		props.put("contributors", editSegmentsEntryDisplayContext.getContributorsJSONArray());
		props.put("defaultLanguageId", editSegmentsEntryDisplayContext.getDefaultLanguageId());
		props.put("formId", renderResponse.getNamespace() + "editSegmentFm");
		props.put("hasUpdatePermission", editSegmentsEntryDisplayContext.hasUpdatePermission());
		props.put("initialMembersCount", editSegmentsEntryDisplayContext.getSegmentsEntryClassPKsCount());
		props.put("initialSegmentActive", (segmentsEntry == null) ? false : segmentsEntry.isActive());

		if (segmentsEntry != null) {
			JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

			String initialSegmentName = JSONFactoryUtil.createJSONObject(jsonSerializer.serializeDeep(segmentsEntry.getNameMap())).toString();

			props.put("initialSegmentName", initialSegmentName);
		}

		props.put("locale", locale.toString());
		props.put("portletNamespace", renderResponse.getNamespace());
		props.put("previewMembersURL", previewMembersURL.toString());
		props.put("propertyGroups", editSegmentsEntryDisplayContext.getPropertyGroupsJSONArray(locale));
		props.put("redirect", HtmlUtil.escape(redirect));
		props.put("requestMembersCountURL", getSegmentsEntryClassPKsCountURL.toString());
		props.put("showInEditMode", editSegmentsEntryDisplayContext.isShowInEditMode());
		props.put("source", editSegmentsEntryDisplayContext.getSource());

		Map<String, Object> data = new HashMap<>();
		data.put("context", context);
		data.put("props", props);
		%>

		<react:component
			data="<%= data %>"
			module="js/SegmentsApp.es"
		/>
	</div>
</aui:form>

<aui:script>
	function <portlet:namespace />saveSegmentsEntry() {
		submitForm(document.<portlet:namespace />editSegmentFm);
	}
</aui:script>