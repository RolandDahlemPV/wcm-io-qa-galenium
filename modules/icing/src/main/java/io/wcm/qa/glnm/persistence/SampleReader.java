package io.wcm.qa.glnm.persistence;
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

import io.wcm.qa.glnm.differences.base.Differences;

/**
 * <p>SampleReader interface.</p>
 *
 * @since 5.0.0
 */
public interface SampleReader<T> {

  /**
   * <p>readSample.</p>
   *
   * @param differences a {@link io.wcm.qa.glnm.differences.base.Differences} object.
   * @return a T object.
   * @since 5.0.0
   */
  T readSample(Differences differences);
}