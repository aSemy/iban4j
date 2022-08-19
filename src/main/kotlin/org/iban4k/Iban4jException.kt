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

import org.iban4k.bban.BbanEntryType


/**
 * Base Runtime Exception Class for the library exceptions.
 */
abstract class Iban4jException : RuntimeException() {

  /**
   * Thrown to indicate that requested country is not supported.
   */
  class UnsupportedCountry(
    val countryCode: String? = null,
    override val message: String? = null,
    override val cause: Throwable? = null,
  ) : Iban4jException()

  /**
   * Thrown to indicate that an IBAN check digit is invalid
   */
  class InvalidCheckDigit(
    val actual: String? = null,
    val expected: String? = null,
  ) : Iban4jException()

  /**
   * Thrown to indicate that the application has attempted to convert a string to IBAN, but the
   * string does not have the appropriate format.
   */
  class InvalidIbanFormat(
    val formatViolation: IbanFormatViolation? = null,
    val expected: Any? = null,
    val actual: Any? = null,
    val bbanEntryType: BbanEntryType? = null,
    val invalidCharacter: Char? = null,
  ) : Iban4jException() {

    enum class IbanFormatViolation {
      UNKNOWN, IBAN_FORMATTING, IBAN_NOT_NULL, IBAN_NOT_EMPTY, IBAN_VALID_CHARACTERS, CHECK_DIGIT_ONLY_DIGITS, CHECK_DIGIT_TWO_DIGITS, COUNTRY_CODE_TWO_LETTERS, COUNTRY_CODE_UPPER_CASE_LETTERS, COUNTRY_CODE_EXISTS, COUNTRY_CODE_NOT_NULL, BBAN_LENGTH, BBAN_ONLY_DIGITS, BBAN_ONLY_UPPER_CASE_LETTERS, BBAN_ONLY_DIGITS_OR_LETTERS, BANK_CODE_NOT_NULL, ACCOUNT_NUMBER_NOT_NULL
    }
  }

  /**
   * Thrown to indicate that the application has attempted to convert
   * a string to Bic or to validate Bic's string representation, but the string does not
   * have the appropriate format.
   */
  class InvalidBicFormat(
    val formatViolation: BicFormatViolation? = null,
    val expected: Any? = null,
    val actual: Any? = null,
  ) : Iban4jException() {

    enum class BicFormatViolation {
      UNKNOWN,
      BIC_NOT_NULL,
      BIC_NOT_EMPTY,
      BIC_LENGTH_8_OR_11,
      BIC_ONLY_UPPER_CASE_LETTERS,
      BRANCH_CODE_ONLY_LETTERS_OR_DIGITS,
      LOCATION_CODE_ONLY_LETTERS_OR_DIGITS,
      BANK_CODE_ONLY_LETTERS,
      COUNTRY_CODE_ONLY_UPPER_CASE_LETTERS,
    }

  }

}
