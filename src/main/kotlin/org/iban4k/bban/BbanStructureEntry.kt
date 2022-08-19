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
package org.iban4k.bban

import java.util.Random


/**
 * Bban Structure Entry representation.
 */
class BbanStructureEntry private constructor(
  val entryType: BbanEntryType,
  val characterType: EntryCharacterType,
  val length: Int
) {

  enum class EntryCharacterType {
    n,  // Digits (numeric characters 0 to 9 only)
    a,  // Upper case letters (alphabetic characters A-Z only)
    c // upper and lower case alphanumeric characters (A-Z, a-z and 0-9)
  }

  val random: String
    get() = getRandom(DEFAULT_RANDOM)

  fun getRandom(random: Random): String {
    val s = StringBuilder("")
    val charChoices = charByCharacterType[characterType]
      ?: throw RuntimeException(
        String.format(
          "programmer has not implemented choices for character type %s",
          characterType.name
        )
      )
    for (i in 0 until length) {
      s.append(charChoices[random.nextInt(charChoices.size)])
    }
    return s.toString()
  }

  companion object {
    private val charByCharacterType: MutableMap<EntryCharacterType, CharArray> = mutableMapOf()
    private val DEFAULT_RANDOM = Random()

    init {
      val charTypeN = StringBuilder()
      run {
        var ch = '0'
        while (ch <= '9') {
          charTypeN.append(ch)
          ch++
        }
      }
      charByCharacterType[EntryCharacterType.n] = charTypeN.toString().toCharArray()
      val charTypeA = StringBuilder()
      var ch = 'A'
      while (ch <= 'Z') {
        charTypeA.append(ch)
        ++ch
      }
      charByCharacterType[EntryCharacterType.a] =
        charTypeA.toString().toCharArray()
      charByCharacterType[EntryCharacterType.c] =
        (charTypeN.toString() + charTypeA.toString()).toCharArray()
    }

    fun bankCode(length: Int, characterType: Char): BbanStructureEntry {
      return BbanStructureEntry(
        BbanEntryType.bank_code,
        EntryCharacterType.valueOf(characterType.toString()), length
      )
    }

    fun branchCode(length: Int, characterType: Char): BbanStructureEntry {
      return BbanStructureEntry(
        BbanEntryType.branch_code,
        EntryCharacterType.valueOf(characterType.toString()), length
      )
    }

    fun accountNumber(length: Int, characterType: Char): BbanStructureEntry {
      return BbanStructureEntry(
        BbanEntryType.account_number,
        EntryCharacterType.valueOf(characterType.toString()), length
      )
    }

    fun nationalCheckDigit(length: Int, characterType: Char): BbanStructureEntry {
      return BbanStructureEntry(
        BbanEntryType.national_check_digit,
        EntryCharacterType.valueOf(characterType.toString()), length
      )
    }

    fun accountType(length: Int, characterType: Char): BbanStructureEntry {
      return BbanStructureEntry(
        BbanEntryType.account_type,
        EntryCharacterType.valueOf(characterType.toString()), length
      )
    }

    fun ownerAccountNumber(length: Int, characterType: Char): BbanStructureEntry {
      return BbanStructureEntry(
        BbanEntryType.owner_account_number,
        EntryCharacterType.valueOf(characterType.toString()), length
      )
    }

    fun identificationNumber(length: Int, characterType: Char): BbanStructureEntry {
      return BbanStructureEntry(
        BbanEntryType.identification_number,
        EntryCharacterType.valueOf(characterType.toString()), length
      )
    }
  }
}
