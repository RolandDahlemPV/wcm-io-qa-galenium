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
package io.wcm.qa.galenium.example.pageobjects;

import io.wcm.qa.galenium.selectors.Selector;
import io.wcm.qa.galenium.selectors.SelectorFactory;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Stage on homepage.
 */
public class Stage extends AbstractWebDriverPageObject {

  private static final Selector SELECTOR_STAGE = SelectorFactory.fromCss("#stage");
  private static final Selector SELECTOR_STAGE_HEADLINE = SelectorFactory.fromCss("div.stage-title > h2");
  private static final Selector SELECTOR_STAGE_DESCRIPTION = SelectorFactory.fromCss("div.stage-overlay div.stage-title > p");
  private static final Selector SELECTOR_STAGE_CTA_LINKS = SelectorFactory.fromCss("div.stage-cta-box > div.stageheaderLinkItem a.stage-cta");
  private WebElement stage;
  private List<LinkItem> ctaLinks;

  /**
   * @param driver web driver
   */
  public Stage(WebDriver driver) {
    super(driver);
  }

  public String getTitle() {
    return getStage().findElement(SELECTOR_STAGE_HEADLINE.asBy()).getText();
  }

  public String getDescription() {
    return getStage().findElement(SELECTOR_STAGE_DESCRIPTION.asBy()).getText();
  }

  /**
   * @return a list containing all CTA links from stage or empty list if none exist
   */
  public List<LinkItem> getCtaLinks() {
    if (ctaLinks == null) {
      ctaLinks = new ArrayList<LinkItem>();
      List<WebElement> elements = getStage().findElements(SELECTOR_STAGE_CTA_LINKS.asBy());
      for (WebElement webElement : elements) {
        ctaLinks.add(new LinkItem(webElement));
      }
    }
    return ctaLinks;
  }

  private WebElement getStage() {
    if (stage == null) {
      stage = getDriver().findElement(SELECTOR_STAGE.asBy());
    }
    return stage;
  }

}