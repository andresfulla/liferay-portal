<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<div id="<%= renderResponse.getNamespace() + "-analytics-reports-root" %>">
		<div class="p-3 pt-5 text-center">
			<liferay-ui:icon
				alt="connect-to-analytics-cloud"
				src='<%= PortalUtil.getPathContext(request) + "/assets/ac-icon.svg" %>'
			/>

			<h4 class="mt-3"><liferay-ui:message key="connect-to-analytics-cloud" /></h4>

			<p><liferay-ui:message key="connect-to-analytics-cloud-help" /></p>

			<c:choose>
				<c:when test="SegmentsExperimentUtil.isAnalyticsEnabled(themeDisplay.getCompanyId())">
					<liferay-ui:icon
						label="<%= true %>"
						linkCssClass="btn btn-primary btn-sm mb-4"
						markupView="lexicon"
						message="open-analytics-cloud"
						target="_blank"
						url="segmentsExperimentDisplayContext.getLiferayAnalyticsURL(themeDisplay.getCompanyId())"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:icon
						label="<%= true %>"
						linkCssClass="btn btn-primary btn-sm mb-4"
						markupView="lexicon"
						message="start-free-trial"
						target="_blank"
						url="http://SegmentsExperimentUtil.ANALYTICS_CLOUD_TRIAL_URL"
					/>
				</c:otherwise>
			</c:choose>

			<portlet:actionURL name="/hide_analytics_reports_panel" var="hideAnalyticsReportsPanelURL">
				<portlet:param name="redirect" value="<%= themeDisplay.getLayoutFriendlyURL(layout) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				label="<%= true %>"
				linkCssClass="btn btn-secondary btn-sm mb-4"
				markupView="lexicon"
				message="hide-content-performance-panel"
				url="<%= hideAnalyticsReportsPanelURL %>"
			/>
		</div>


</div>
