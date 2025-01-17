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

package com.liferay.jenkins.results.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author Peter Yoo
 */
public class JenkinsConsoleTextLoader {

	public JenkinsConsoleTextLoader(String buildURL) {
		this.buildURL = JenkinsResultsParserUtil.getLocalURL(buildURL);

		consoleLogFileKey = JenkinsResultsParserUtil.combine(
			"jenkins_console_log-", String.valueOf(buildURL.hashCode()),
			".log");

		JenkinsResultsParserUtil.saveToCacheFile(consoleLogFileKey, "");

		serverLogSize = 0;
	}

	public String getConsoleText() {
		if (buildURL.startsWith("file:") || buildURL.contains("mirrors")) {
			try {
				return JenkinsResultsParserUtil.toString(
					buildURL + "/consoleText", false);
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}

		update();

		return JenkinsResultsParserUtil.getCachedText(consoleLogFileKey);
	}

	public int getLineCount() {
		String consoleLog = getConsoleText();

		String[] consoleLogLines = consoleLog.split("\n");

		return consoleLogLines.length;
	}

	public boolean hasMoreData() {
		return hasMoreData;
	}

	protected void update() {
		boolean hasMoreData = true;

		while (hasMoreData) {
			String url =
				buildURL + "/logText/progressiveHtml?start=" + serverLogSize;

			try {
				URL urlObject = new URL(
					JenkinsResultsParserUtil.getLocalURL(url));

				HttpURLConnection httpURLConnection =
					(HttpURLConnection)urlObject.openConnection();

				long latestServerLogSize = httpURLConnection.getHeaderFieldLong(
					"X-Text-Size", serverLogSize);

				if (latestServerLogSize == serverLogSize) {
					break;
				}

				try (BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(
							httpURLConnection.getInputStream()))) {

					String line = bufferedReader.readLine();

					while (line != null) {
						Matcher matcher = _anchorPattern.matcher(line);

						line = matcher.replaceAll("$1") + "\n";

						line = StringEscapeUtils.unescapeHtml(line);

						JenkinsResultsParserUtil.appendToCacheFile(
							consoleLogFileKey, line);

						line = bufferedReader.readLine();
					}

					hasMoreData = Boolean.parseBoolean(
						httpURLConnection.getHeaderField("X-More-Data"));

					serverLogSize = latestServerLogSize;
				}
			}
			catch (MalformedURLException malformedURLException) {
				throw new IllegalArgumentException(
					"Invalid buildURL " + buildURL, malformedURLException);
			}
			catch (IOException ioException) {
				System.out.println(
					"Unable to update console log for build: " + buildURL);

				ioException.printStackTrace();

				return;
			}
		}
	}

	protected String buildURL;
	protected String consoleLogFileKey;
	protected boolean hasMoreData = true;
	protected long serverLogSize;

	private static final Pattern _anchorPattern = Pattern.compile(
		"\\<a[^>]*\\>(?<text>[^<]*)\\</a\\>");

}