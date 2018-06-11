/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2018 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.wcm.qa.galenium.mediaquery;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import io.wcm.qa.galenium.exceptions.GaleniumException;
import io.wcm.qa.galenium.reporting.GaleniumReportUtil;
import io.wcm.qa.galenium.util.ConfigurationUtil;
import io.wcm.qa.galenium.util.GaleniumConfiguration;
import io.wcm.qa.galenium.util.GaleniumContext;

public final class MediaQueryUtil {

  private static final int MIN_WIDTH = GaleniumConfiguration.getMediaQueryMinimalWidth();
  private static final int MAX_WIDTH = GaleniumConfiguration.getMediaQueryMaximalWidth();
  private static final MediaQuery DEFAULT_MEDIA_QUERY = getMediaQuery("DEFAULT_MQ", MIN_WIDTH, MAX_WIDTH);

  private MediaQueryUtil() {
    // do not instantiate
  }

  public static Collection<MediaQuery> getMediaQueries() {
    String propertiesFilePath = GaleniumConfiguration.getMediaQueryPropertiesPath();
    if (StringUtils.isBlank(propertiesFilePath)) {
      throw new GaleniumException("path to media query properties is empty");
    }
    return getMediaQueries(propertiesFilePath);
  }

  public static Collection<MediaQuery> getMediaQueries(File mediaQueryPropertyFile) {
    if (mediaQueryPropertyFile == null) {
      throw new GaleniumException("media query properties file is null.");
    }
    if (!mediaQueryPropertyFile.isFile()) {
      throw new GaleniumException("media query properties file is not a file.");
    }
    return getMediaQueries(mediaQueryPropertyFile.getPath());
  }

  public static Collection<MediaQuery> getMediaQueries(Properties mediaQueryProperties) {
    Collection<MediaQuery> mediaQueries = new ArrayList<MediaQuery>();
    SortedMap<Integer, String> sortedMediaQueryMap = getSortedMediaQueryMap(mediaQueryProperties);
    Set<Entry<Integer, String>> entrySet = sortedMediaQueryMap.entrySet();
    int lowerBound = MIN_WIDTH;
    for (Entry<Integer, String> entry : entrySet) {
      String mediaQueryName = entry.getValue();
      int upperBound = entry.getKey();
      MediaQueryInstance mediaQuery = getMediaQuery(mediaQueryName, lowerBound, upperBound);
      mediaQueries.add(mediaQuery);
      lowerBound = upperBound + 1;
    }
    if (getLogger().isDebugEnabled()) {
      getLogger().debug("generated " + mediaQueries.size() + " media queries");
      for (MediaQuery mediaQuery : mediaQueries) {
        getLogger().debug("  " + mediaQuery);
      }
    }
    return mediaQueries;
  }

  public static MediaQueryInstance getMediaQuery(String mediaQueryName, int lowerBound, int upperBound) {
    if (lowerBound < MIN_WIDTH) {
      throw new GaleniumException("MediaQuery: illegally low lower bound for '" + mediaQueryName + "': " + lowerBound + " < " + MIN_WIDTH);
    }
    if (upperBound < MIN_WIDTH) {
      throw new GaleniumException("MediaQuery: illegally low upper bound for '" + mediaQueryName + "': " + upperBound + " < " + MIN_WIDTH);
    }
    if (upperBound > MAX_WIDTH) {
      throw new GaleniumException("MediaQuery: illegally high upper bound for '" + mediaQueryName + "': " + upperBound + " > " + MAX_WIDTH);
    }
    if (lowerBound > upperBound) {
      throw new GaleniumException("illegal media query lower and upper bound combination for '" + mediaQueryName + "': " + lowerBound + " > " + upperBound);
    }
    return new MediaQueryInstance(mediaQueryName, lowerBound, upperBound);
  }

  public static MediaQuery getCurrentMediaQuery() {
    WebDriver driver = GaleniumContext.getDriver();
    if (driver == null) {
      return DEFAULT_MEDIA_QUERY;
    }
    //    if ()
    Collection<MediaQuery> mediaQueries = getMediaQueries();
    MediaQuery mediaQuery = null;

    return mediaQuery;
  }

  public static Collection<MediaQuery> getMediaQueries(String propertyFilePath) {
    Properties mediaQueryProperties = ConfigurationUtil.loadProperties(propertyFilePath);
    return getMediaQueries(mediaQueryProperties);
  }

  private static Integer getIntegerValue(Entry<Object, Object> entry) {
    Object value = entry.getValue();
    if (value == null) {
      throw new GaleniumException("value null for '" + entry.getKey() + "'");
    }
    try {
      return Integer.parseInt(value.toString());
    }
    catch (NumberFormatException ex) {
      throw new GaleniumException("could not parse to integer: '" + value, ex);
    }
  }

  private static Logger getLogger() {
    return GaleniumReportUtil.getLogger();
  }

  private static SortedMap<Integer, String> getSortedMediaQueryMap(Properties mediaQueryProperties) {
    SortedMap<Integer, String> mediaQueryMap = new TreeMap<Integer, String>();
    Set<Entry<Object, Object>> mqEntries = mediaQueryProperties.entrySet();
    for (Entry<Object, Object> entry : mqEntries) {
      Integer intValue = getIntegerValue(entry);
      String mediaQueryName = entry.getKey().toString();
      mediaQueryMap.put(intValue, mediaQueryName);
    }
    return mediaQueryMap;
  }

  private static final class MediaQueryInstance implements MediaQuery {

    private int high;
    private int low;
    private String name;

    public MediaQueryInstance(String mediaQueryName, int lowerBound, int upperBound) {
      name = mediaQueryName;
      low = lowerBound;
      high = upperBound;
    }

    @Override
    public int getLowerBound() {
      return low;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public int getUpperBound() {
      return high;
    }

    @Override
    public String toString() {
      return name + "(lower: " + low + ", upper: " + high + ")";
    }
  }

}