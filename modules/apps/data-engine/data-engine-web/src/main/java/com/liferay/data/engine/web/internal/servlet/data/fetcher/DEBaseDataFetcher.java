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

package com.liferay.data.engine.web.internal.servlet.data.fetcher;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import graphql.GraphQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 */
public abstract class DEBaseDataFetcher {

	protected String getActionMessage(String languageId, String... actionIds) {
		ResourceBundle resourceBundle = getResourceBundle(languageId);

		return Stream.of(
			actionIds
		).map(
			action -> LanguageUtil.get(resourceBundle, "action." + action)
		).collect(
			Collectors.joining(StringPool.COMMA_AND_SPACE)
		);
	}

	protected String getMessage(String languageId, String languageKey) {
		ResourceBundle resourceBundle = getResourceBundle(languageId);

		return LanguageUtil.get(resourceBundle, languageKey);
	}

	protected String getMessage(
		String languageId, String languageKey, Object... parameters) {

		ResourceBundle resourceBundle = getResourceBundle(languageId);

		return LanguageUtil.format(
			resourceBundle, languageKey, parameters, false);
	}

	protected abstract Portal getPortal();

	protected ResourceBundle getResourceBundle(String languageId) {
		List<ResourceBundle> resourceBundles = new ArrayList<>();

		Portal portal = getPortal();

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		ResourceBundle portalResourceBundle = portal.getResourceBundle(locale);

		resourceBundles.add(portalResourceBundle);

		_collectResourceBundles(getClass(), resourceBundles, locale);

		ResourceBundle[] resourceBundlesArray = resourceBundles.toArray(
			new ResourceBundle[resourceBundles.size()]);

		return new AggregateResourceBundle(resourceBundlesArray);
	}

	protected void handleErrorMessage(String errorMessage) {
		throw new GraphQLException(StringPool.DOUBLE_DOLLAR + errorMessage);
	}

	private void _collectResourceBundles(
		Class<?> clazz, List<ResourceBundle> resourceBundles, Locale locale) {

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			_collectResourceBundles(interfaceClass, resourceBundles, locale);
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, clazz.getClassLoader());

		if (resourceBundle != null) {
			resourceBundles.add(resourceBundle);
		}
	}

}