/*
 * Copyright 2013 Artur Mkrtchyan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.iban4k

import org.iban4j.BicFormatException
import org.iban4j.UnsupportedCountryException

/**
 * Business Identifier Codes (also known as SWIFT-BIC, BIC code, SWIFT ID or SWIFT code).
 *
 * [ISO_9362](https://en.wikipedia.org/wiki/ISO_9362).
 *
 * @param[bankCode]string representation of Bic's institution code.
 * @param[locationCode]   representation of Bic's location code.
 * @param[countryCode]  representation of Bic's country code.
 * @param[branchCode] representation of Bic's branch code, null if Bic has no branch code.
 */
data class Bic(
  val bankCode: String,
  val countryCode: CountryCode?,
  val locationCode: String,
  val branchCode: String?,
) {

  private constructor(
    value: String,
  ) : this(
    bankCode = BicUtil.getBankCode(value),
    countryCode = CountryCode.getByCode(BicUtil.getCountryCode(value)),
    locationCode = BicUtil.getLocationCode(value),
    branchCode = BicUtil.getBranchCode(value),
  )

  companion object {
    /**
     * Returns a Bic object holding the value of the specified String.
     *
     * @param bic the String to be parsed.
     * @return a Bic object holding the value represented by the string argument.
     * @throws BicFormatException if the String doesn't contain parsable Bic.
     * @throws  UnsupportedCountryException if bic's country is not supported.
     */
    @Throws(BicFormatException::class, UnsupportedCountryException::class)
    fun valueOf(bic: String): Bic {
      BicUtil.validate(bic)
      return Bic(bic)
    }
  }
}
