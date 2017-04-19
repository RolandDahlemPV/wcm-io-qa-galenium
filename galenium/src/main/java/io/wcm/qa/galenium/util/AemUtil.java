/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2017 wcm.io
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
package io.wcm.qa.galenium.util;

import static io.wcm.qa.galenium.reporting.GaleniumReportUtil.getLogger;
import static io.wcm.qa.galenium.util.InteractionUtil.click;
import static io.wcm.qa.galenium.util.InteractionUtil.enterText;
import static io.wcm.qa.galenium.util.InteractionUtil.loadUrl;
import static io.wcm.qa.galenium.util.InteractionUtil.waitForUrl;

import io.wcm.qa.galenium.selectors.Selector;
import io.wcm.qa.galenium.selectors.SelectorFactory;

/**
 * AEM specific utility methods.
 */
public class AemUtil {

  private static final Selector DIV_LOGIN_BOX = SelectorFactory.fromCss("div#login-box");
  private static final String LOGIN_AUTHOR_NAME = "admin";
  private static final String LOGIN_AUTHOR_PASS = "admin";
  private static final Selector SELECTOR_AUTHOR_INPUT_PASSWORD = SelectorFactory.fromCss("#password");
  private static final Selector SELECTOR_AUTHOR_INPUT_USERNAME = SelectorFactory.fromCss("#username");
  private static final Selector SELECTOR_AUTHOR_LOGIN_BUTTON = SelectorFactory.fromCss("#submit-button");

  private AemUtil() {
    // do not instantiate
  }

  public static boolean isAuthorLogin() {
    return InteractionUtil.getElementVisible(DIV_LOGIN_BOX) != null;
  }

  public static void loginToAuthor(String targetUrl) {
    loadUrl(targetUrl);
    if (isAuthorLogin()) {
      loginToAuthor();
      waitForUrl(targetUrl);
    }
  }

  public static void loginToAuthor() {
    if (isAuthorLogin()) {
      getLogger().info("Logging in to author instance");
      enterText(SELECTOR_AUTHOR_INPUT_USERNAME, LOGIN_AUTHOR_NAME);
      enterText(SELECTOR_AUTHOR_INPUT_PASSWORD, LOGIN_AUTHOR_PASS);
      click(SELECTOR_AUTHOR_LOGIN_BUTTON);
    }
  }

}
