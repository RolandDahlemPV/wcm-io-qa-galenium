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
package io.wcm.qa.galenium.verification;

import io.wcm.qa.galenium.selectors.Selector;

public class InvisibilityVerification extends VisibilityVerification {

  protected InvisibilityVerification(Selector selector) {
    super(selector);
  }

  @Override
  protected Boolean doVerification() {
    return !super.doVerification();
  }

  @Override
  protected String getFailureMessage() {
    return super.getSuccessMessage();
  }

  @Override
  protected String getSuccessMessage() {
    return super.getFailureMessage();
  }
}
