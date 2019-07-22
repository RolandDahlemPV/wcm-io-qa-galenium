/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2019 wcm.io
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
package io.wcm.qa.glnm.sampling.network;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;

import io.wcm.qa.glnm.exceptions.GaleniumException;
import io.wcm.qa.glnm.sampling.network.base.JsoupBasedSampler;

/**
 * Samples cookies from a Jsoup network response.
 * @param <T>
 */
public class JsoupCookieSampler<T extends Map<String, String>> extends JsoupBasedSampler<T> {

  /**
   * @param url to fetch cookies from
   */
  public JsoupCookieSampler(String url) {
    super(url);
  }

  private Method method = Method.POST;

  @SuppressWarnings("unchecked")
  @Override
  public T sampleValue() {
    return (T)fetchCookies();
  }

  /**
   * @param requestMethod HTTP method to use for retrieval
   * @return this
   */
  public JsoupCookieSampler<T> setMethod(Method requestMethod) {
    this.method = requestMethod;
    return this;
  }

  protected Map<String, String> fetchCookies() {
    Connection jsoupConnection = getJsoupConnection();

    try {
      jsoupConnection.method(getMethod());
      jsoupConnection.execute();
    }
    catch (IOException ex) {
      throw new GaleniumException("Could not fetch cookies", ex);
    }

    return jsoupConnection.response().cookies();
  }

  protected Method getMethod() {
    return method;
  }

}
