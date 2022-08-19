package org.iban4k.bban

sealed interface BbanStructureElement {

  val characterType: EntryCharacters
  val length: Int

  data class BankCode(
    override val characterType: EntryCharacters,
    override val length: Int
  ) : BbanStructureElement

  data class BranchCode(
    override val characterType: EntryCharacters,
    override val length: Int
  ) : BbanStructureElement

  data class AccountNumber(
    override val characterType: EntryCharacters,
    override val length: Int
  ) : BbanStructureElement

  data class NationalCheckDigit(
    override val characterType: EntryCharacters,
    override val length: Int
  ) : BbanStructureElement

  data class AccountType(
    override val characterType: EntryCharacters,
    override val length: Int
  ) : BbanStructureElement

  data class OwnerAccountNumber(
    override val characterType: EntryCharacters,
    override val length: Int
  ) : BbanStructureElement

  data class IdentificationNumber(
    override val characterType: EntryCharacters,
    override val length: Int
  ) : BbanStructureElement


  enum class EntryCharacters(val shortcode: Char) {
    /** Digits (numeric characters 0 to 9 only) */
    DIGITS('n'),
    /** Upper case letters (alphabetic characters A-Z only) */
    UPPERCASE('a'),
    /** upper and lower case alphanumeric characters (A-Z, a-z and 0-9) */
    ALPHANUMERIC('c'),
  }
}
