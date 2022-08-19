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

import java.util.Random
import org.iban4j.IbanFormatException
import org.iban4j.InvalidCheckDigitException
import org.iban4j.UnsupportedCountryException

/**
 * International Bank Account Number
 *
 * [ISO_13616](https://en.wikipedia.org/wiki/ISO_13616).
 *
 * @param[countryCode]   iban's country code.
 * @param[checkDigit]   iban's check digit.
 * @param[accountNumber]   iban's account number.
 * @param[bankCode]   iban's bank code.
 * @param[branchCode]   iban's branch code.
 * @param[nationalCheckDigit]   iban's national check digit.
 * @param[accountType]   iban's account type.
 * @param[ownerAccountType]   iban's owner account type.
 * @param[identificationNumber]   iban's identification number.
 * @param[bban]   iban's bban (Basic Bank Account Number).
 */
data class Iban(
  private val rawValue: String,

  val countryCode: CountryCode?,
  val checkDigit: String,
  val accountNumber: String,
  val bankCode: String,
  val branchCode: String,
  val nationalCheckDigit: String,
  val accountType: String,
  val ownerAccountType: String,
  val identificationNumber: String,
  val bban: String,
) {
  constructor(
    rawValue: String,
  ) : this(
    rawValue = rawValue,
    countryCode = CountryCode.getByCode(IbanUtil.getCountryCode(rawValue)),
    checkDigit = IbanUtil.getCheckDigit(rawValue),
    accountNumber = IbanUtil.getAccountNumber(rawValue),
    bankCode = IbanUtil.getBankCode(rawValue),
    branchCode = IbanUtil.getBranchCode(rawValue),
    nationalCheckDigit = IbanUtil.getNationalCheckDigit(rawValue),
    accountType = IbanUtil.getAccountType(rawValue),
    ownerAccountType = IbanUtil.getOwnerAccountType(rawValue),
    identificationNumber = IbanUtil.getIdentificationNumber(rawValue),
    bban = IbanUtil.getBban(rawValue),
  )

  /**
   * Returns formatted version of Iban.
   *
   * @return A string representing formatted Iban for printing.
   */
  fun toFormattedString(): String {
    return IbanUtil.toFormattedString(rawValue)
  }

  companion object {
    const val DEFAULT_CHECK_DIGIT = "00"
    private val DEFAULT_RANDOM = Random()
    /**
     * Returns an Iban object holding the value of the specified String.
     *
     * @param iban the String to be parsed.
     * @return an Iban object holding the value represented by the string argument.
     * @throws IbanFormatException if the String doesn't contain parsable Iban
     * InvalidCheckDigitException if Iban has invalid check digit
     * UnsupportedCountryException if Iban's Country is not supported.
     */
    @Throws(
      IbanFormatException::class,
      InvalidCheckDigitException::class,
      UnsupportedCountryException::class
    )
    fun valueOf(iban: String): Iban {
      IbanUtil.validate(iban)
      return Iban(iban)
    }
    /**
     * Returns an Iban object holding the value of the specified String.
     *
     * @param iban the String to be parsed.
     * @param format the format of the Iban.
     * @return an Iban object holding the value represented by the string argument.
     * @throws IbanFormatException if the String doesn't contain parsable Iban
     * InvalidCheckDigitException if Iban has invalid check digit
     * UnsupportedCountryException if Iban's Country is not supported.
     */
    @Throws(
      IbanFormatException::class,
      InvalidCheckDigitException::class,
      UnsupportedCountryException::class
    )
    fun valueOf(iban: String, format: IbanFormat?): Iban {
      return when (format) {
        IbanFormat.Default -> {
          val ibanWithoutSpaces = iban.replace(" ", "")
          val ibanObj = valueOf(ibanWithoutSpaces)
          if (ibanObj.toFormattedString() == iban) {
            return ibanObj
          }
          throw IbanFormatException(
            IbanFormatException.IbanFormatViolation.IBAN_FORMATTING,
            "Iban must be formatted using 4 characters and space combination. Instead of [$iban]"
          )
        }

        else               -> valueOf(iban)
      }
    }

    @JvmOverloads
    fun random(random: Random = DEFAULT_RANDOM): Iban {
      return IbanBuilder(random).buildRandom()
    }

    fun random(cc: CountryCode?): Iban {
      return IbanBuilder(DEFAULT_RANDOM).countryCode(cc).buildRandom()
    }

    fun random(random: Random  = DEFAULT_RANDOM, cc: CountryCode? = null): Iban {
      return IbanBuilder(random).countryCode(cc).buildRandom()
    }
  }
}
