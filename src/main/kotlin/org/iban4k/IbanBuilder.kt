package org.iban4k

import java.util.Random
import org.iban4j.IbanFormatException
import org.iban4j.UnsupportedCountryException
import org.iban4k.Iban.Companion.DEFAULT_CHECK_DIGIT
import org.iban4k.bban.BbanEntryType
import org.iban4k.bban.BbanStructure

/**
 * Iban Builder Class
 */
class IbanBuilder {
  private var countryCode: CountryCode? = null
  private var bankCode: String? = null
  private var branchCode: String? = null
  private var nationalCheckDigit: String? = null
  private var accountType: String? = null
  private var accountNumber: String? = null
  private var ownerAccountType: String? = null
  private var identificationNumber: String? = null
  private val random: Random
  /**
   * Creates an Iban Builder instance.
   */
  constructor() {
    random = Random()
  }
  /**
   * Creates an Iban Builder instance.
   */
  constructor(random: Random) {
    this.random = random
  }
  /**
   * Sets iban's country code.
   *
   * @param countryCode CountryCode
   * @return builder Builder
   */
  fun countryCode(countryCode: CountryCode?): IbanBuilder {
    this.countryCode = countryCode
    return this
  }
  /**
   * Sets iban's bank code.
   *
   * @param bankCode String
   * @return builder Builder
   */
  fun bankCode(bankCode: String?): IbanBuilder {
    this.bankCode = bankCode
    return this
  }
  /**
   * Sets iban's branch code.
   *
   * @param branchCode String
   * @return builder Builder
   */
  fun branchCode(branchCode: String?): IbanBuilder {
    this.branchCode = branchCode
    return this
  }
  /**
   * Sets iban's account number.
   *
   * @param accountNumber String
   * @return builder Builder
   */
  fun accountNumber(accountNumber: String?): IbanBuilder {
    this.accountNumber = accountNumber
    return this
  }
  /**
   * Sets iban's national check digit.
   *
   * @param nationalCheckDigit String
   * @return builder Builder
   */
  fun nationalCheckDigit(nationalCheckDigit: String?): IbanBuilder {
    this.nationalCheckDigit = nationalCheckDigit
    return this
  }
  /**
   * Sets iban's account type.
   *
   * @param accountType String
   * @return builder Builder
   */
  fun accountType(accountType: String?): IbanBuilder {
    this.accountType = accountType
    return this
  }
  /**
   * Sets iban's owner account type.
   *
   * @param ownerAccountType String
   * @return builder Builder
   */
  fun ownerAccountType(ownerAccountType: String?): IbanBuilder {
    this.ownerAccountType = ownerAccountType
    return this
  }
  /**
   * Sets iban's identification number.
   *
   * @param identificationNumber String
   * @return builder Builder
   */
  fun identificationNumber(identificationNumber: String?): IbanBuilder {
    this.identificationNumber = identificationNumber
    return this
  }
  /**
   * Builds new iban instance.
   *
   * @param validate boolean indicates if the generated IBAN needs to be
   * validated after generation
   * @return new iban instance.
   * @exception IbanFormatException if values are not parsable by Iban Specification
   * [ISO_13616](https://en.wikipedia.org/wiki/ISO_13616)
   * @exception UnsupportedCountryException if country is not supported
   */
  /**
   * Builds new iban instance. This methods validates the generated IBAN.
   *
   * @return new iban instance.
   * @exception IbanFormatException if values are not parsable by Iban Specification
   * [ISO_13616](https://en.wikipedia.org/wiki/ISO_13616)
   * @exception UnsupportedCountryException if country is not supported
   */
  @JvmOverloads
  @Throws(
    IbanFormatException::class,
    IllegalArgumentException::class,
    UnsupportedCountryException::class
  )
  fun build(validate: Boolean = true): Iban {

    // null checks
    require(countryCode, bankCode, accountNumber)

    // iban is formatted with default check digit.
    val formattedIban = formatIban()
    val checkDigit = IbanUtil.calculateCheckDigit(formattedIban)

    // replace default check digit with calculated check digit
    val ibanValue = IbanUtil.replaceCheckDigit(formattedIban, checkDigit)
    if (validate) {
      IbanUtil.validate(ibanValue)
    }
    return Iban(ibanValue)
  }
  /**
   * Builds random iban instance.
   *
   * @return random iban instance.
   * @exception IbanFormatException if values are not parsable by Iban Specification
   * [ISO_13616](https://en.wikipedia.org/wiki/ISO_13616)
   * @exception UnsupportedCountryException if country is not supported
   */
  @Throws(
    IbanFormatException::class,
    IllegalArgumentException::class,
    UnsupportedCountryException::class
  )
  fun buildRandom(): Iban {
    if (countryCode == null) {
      val countryCodes = BbanStructure.supportedCountries()
      countryCode(countryCodes[random.nextInt(countryCodes.size)])
    }
    fillMissingFieldsRandomly(random)
    return build()
  }
  /**
   * Returns formatted bban string.
   */
  private fun formatBban(): String {
    val sb = StringBuilder()
    val structure = BbanStructure.forCountry(countryCode!!)
      ?: throw Iban4jException.UnsupportedCountry(
        countryCode.toString(),
        "Country code is not supported."
      )
    for (entry in structure.entries) {
      when (entry.entryType) {
        BbanEntryType.bank_code   -> sb.append(bankCode)
        BbanEntryType.branch_code    -> sb.append(branchCode)
        BbanEntryType.account_number       -> sb.append(accountNumber)
        BbanEntryType.national_check_digit -> sb.append(nationalCheckDigit)
        BbanEntryType.account_type         -> sb.append(accountType)
        BbanEntryType.owner_account_number  -> sb.append(ownerAccountType)
        BbanEntryType.identification_number -> sb.append(identificationNumber)
      }
    }
    return sb.toString()
  }
  /**
   * Returns formatted iban string with default check digit.
   */
  private fun formatIban(): String {
    val sb = StringBuilder()
    sb.append(countryCode?.alpha2)
    sb.append(DEFAULT_CHECK_DIGIT)
    sb.append(formatBban())
    return sb.toString()
  }

  @Throws(IbanFormatException::class)
  private fun require(
    countryCode: CountryCode?,
    bankCode: String?,
    accountNumber: String?
  ) {
    if (countryCode == null) {
      throw Iban4jException.InvalidIbanFormat(
        Iban4jException.InvalidIbanFormat.IbanFormatViolation.COUNTRY_CODE_NOT_NULL,
        "countryCode is required; it cannot be null"
      )
    }
    if (bankCode == null) {
      throw Iban4jException.InvalidIbanFormat(
        Iban4jException.InvalidIbanFormat.IbanFormatViolation.BANK_CODE_NOT_NULL,
        "bankCode is required; it cannot be null"
      )
    }
    if (accountNumber == null) {
      throw Iban4jException.InvalidIbanFormat(
        Iban4jException.InvalidIbanFormat.IbanFormatViolation.ACCOUNT_NUMBER_NOT_NULL,
        "accountNumber is required; it cannot be null"
      )
    }
  }

  private fun fillMissingFieldsRandomly(random: Random) {
    val structure = BbanStructure.forCountry(countryCode!!)
      ?: throw UnsupportedCountryException(
        countryCode.toString(),
        "Country code is not supported."
      )
    for (entry in structure.entries) {
      when (entry.entryType) {
        BbanEntryType.bank_code -> if (bankCode == null) {
          bankCode = entry.getRandom(random)
        }

        BbanEntryType.branch_code -> if (branchCode == null) {
          branchCode = entry.getRandom(random)
        }

        BbanEntryType.account_number -> if (accountNumber == null) {
          accountNumber = entry.getRandom(random)
        }

        BbanEntryType.national_check_digit -> if (nationalCheckDigit == null) {
          nationalCheckDigit = entry.getRandom(random)
        }

        BbanEntryType.account_type -> if (accountType == null) {
          accountType = entry.getRandom(random)
        }

        BbanEntryType.owner_account_number -> if (ownerAccountType == null) {
          ownerAccountType = entry.getRandom(random)
        }

        BbanEntryType.identification_number -> if (identificationNumber == null) {
          identificationNumber = entry.getRandom(random)
        }
      }
    }
  }
}
