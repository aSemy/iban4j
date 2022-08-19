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

import java.util.Collections
import java.util.EnumMap
import org.iban4k.CountryCode

/**
 * Class which represents bban structure
 */
class BbanStructure(
    val entries: Set<BbanStructureEntry>
) {

  constructor(vararg entries: BbanStructureEntry) : this(entries.toSet())

  /**
   * Returns the length of bban.
   *
   * @return int length
   */
  val bbanLength: Int
    get() {
      var length = 0
      for (entry in entries) {
        length += entry.length
      }
      return length
    }

  companion object {
    private val structures: EnumMap<CountryCode, BbanStructure> = EnumMap(CountryCode::class.java)

    /** French sub-territories may use their own country code (BL,RE,NC,...) or FR for their IBAN. Structure is the same, only the IBAN checksum differ.  */
    private val FRENCH_STRUCTURE = BbanStructure(
      BbanStructureEntry.bankCode(5, 'n'),
      BbanStructureEntry.branchCode(5, 'n'),
      BbanStructureEntry.accountNumber(11, 'c'),
      BbanStructureEntry.nationalCheckDigit(2, 'n')
    )

    init {
      structures[CountryCode.AL] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.branchCode(4, 'n'),
        BbanStructureEntry.nationalCheckDigit(1, 'n'),
        BbanStructureEntry.accountNumber(16, 'c')
      )
      structures[CountryCode.AD] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'n'),
        BbanStructureEntry.branchCode(4, 'n'),
        BbanStructureEntry.accountNumber(12, 'c')
      )
      structures[CountryCode.AT] = BbanStructure(
        BbanStructureEntry.bankCode(5, 'n'),
        BbanStructureEntry.accountNumber(11, 'n')
      )
      structures[CountryCode.AZ] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.accountNumber(20, 'c')
      )
      structures[CountryCode.BH] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.accountNumber(14, 'c')
      )
      structures[CountryCode.BE] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.accountNumber(7, 'n'),
        BbanStructureEntry.nationalCheckDigit(2, 'n')
      )
      structures[CountryCode.BA] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.branchCode(3, 'n'),
        BbanStructureEntry.accountNumber(8, 'n'),
        BbanStructureEntry.nationalCheckDigit(2, 'n')
      )
      structures[CountryCode.BR] = BbanStructure(
        BbanStructureEntry.bankCode(8, 'n'),
        BbanStructureEntry.branchCode(5, 'n'),
        BbanStructureEntry.accountNumber(10, 'n'),
        BbanStructureEntry.accountType(1, 'a'),
        BbanStructureEntry.ownerAccountNumber(1, 'c')
      )
      structures[CountryCode.BG] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.branchCode(4, 'n'),
        BbanStructureEntry.accountType(2, 'n'),
        BbanStructureEntry.accountNumber(8, 'c')
      )
      structures[CountryCode.BL] = FRENCH_STRUCTURE
      structures[CountryCode.BY] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'c'),
        BbanStructureEntry.branchCode(4, 'n'),
        BbanStructureEntry.accountNumber(16, 'c')
      )
      structures[CountryCode.CR] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'n'),
        BbanStructureEntry.accountNumber(14, 'n')
      )
      structures[CountryCode.DE] = BbanStructure(
        BbanStructureEntry.bankCode(8, 'n'),
        BbanStructureEntry.accountNumber(10, 'n')
      )
      structures[CountryCode.HR] = BbanStructure(
        BbanStructureEntry.bankCode(7, 'n'),
        BbanStructureEntry.accountNumber(10, 'n')
      )
      structures[CountryCode.CY] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.branchCode(5, 'n'),
        BbanStructureEntry.accountNumber(16, 'c')
      )
      structures[CountryCode.CZ] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'n'),
        BbanStructureEntry.accountNumber(16, 'n')
      )
      structures[CountryCode.DK] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'n'),
        BbanStructureEntry.accountNumber(10, 'n')
      )
      structures[CountryCode.DO] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'c'),
        BbanStructureEntry.accountNumber(20, 'n')
      )
      structures[CountryCode.EE] = BbanStructure(
        BbanStructureEntry.bankCode(2, 'n'),
        BbanStructureEntry.branchCode(2, 'n'),
        BbanStructureEntry.accountNumber(11, 'n'),
        BbanStructureEntry.nationalCheckDigit(1, 'n')
      )
      structures[CountryCode.FO] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'n'),
        BbanStructureEntry.accountNumber(9, 'n'),
        BbanStructureEntry.nationalCheckDigit(1, 'n')
      )
      structures[CountryCode.FI] = BbanStructure(
        BbanStructureEntry.bankCode(6, 'n'),
        BbanStructureEntry.accountNumber(7, 'n'),
        BbanStructureEntry.nationalCheckDigit(1, 'n')
      )
      structures[CountryCode.FR] = FRENCH_STRUCTURE
      structures[CountryCode.GE] = BbanStructure(
        BbanStructureEntry.bankCode(2, 'a'),
        BbanStructureEntry.accountNumber(16, 'n')
      )
      structures[CountryCode.GF] = FRENCH_STRUCTURE
      structures[CountryCode.GI] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.accountNumber(15, 'c')
      )
      structures[CountryCode.GL] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'n'),
        BbanStructureEntry.accountNumber(10, 'n')
      )
      structures[CountryCode.GP] = FRENCH_STRUCTURE
      structures[CountryCode.GR] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.branchCode(4, 'n'),
        BbanStructureEntry.accountNumber(16, 'c')
      )
      structures[CountryCode.GT] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'c'),
        BbanStructureEntry.accountNumber(20, 'c')
      )
      structures[CountryCode.HU] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.branchCode(4, 'n'),
        BbanStructureEntry.accountNumber(16, 'n'),
        BbanStructureEntry.nationalCheckDigit(1, 'n')
      )
      structures[CountryCode.IS] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'n'),
        BbanStructureEntry.branchCode(2, 'n'),
        BbanStructureEntry.accountNumber(6, 'n'),
        BbanStructureEntry.identificationNumber(10, 'n')
      )
      structures[CountryCode.IE] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.branchCode(6, 'n'),
        BbanStructureEntry.accountNumber(8, 'n')
      )
      structures[CountryCode.IL] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.branchCode(3, 'n'),
        BbanStructureEntry.accountNumber(13, 'n')
      )
      structures[CountryCode.IR] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.accountNumber(19, 'n')
      )
      structures[CountryCode.IT] = BbanStructure(
        BbanStructureEntry.nationalCheckDigit(1, 'a'),
        BbanStructureEntry.bankCode(5, 'n'),
        BbanStructureEntry.branchCode(5, 'n'),
        BbanStructureEntry.accountNumber(12, 'c')
      )
      structures[CountryCode.JO] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.branchCode(4, 'n'),
        BbanStructureEntry.accountNumber(18, 'c')
      )
      structures[CountryCode.KZ] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.accountNumber(13, 'c')
      )
      structures[CountryCode.KW] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.accountNumber(22, 'c')
      )
      structures[CountryCode.LC] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.accountNumber(24, 'c')
      )
      structures[CountryCode.LV] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.accountNumber(13, 'c')
      )
      structures[CountryCode.LB] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'n'),
        BbanStructureEntry.accountNumber(20, 'c')
      )
      structures[CountryCode.LI] = BbanStructure(
        BbanStructureEntry.bankCode(5, 'n'),
        BbanStructureEntry.accountNumber(12, 'c')
      )
      structures[CountryCode.LT] = BbanStructure(
        BbanStructureEntry.bankCode(5, 'n'),
        BbanStructureEntry.accountNumber(11, 'n')
      )
      structures[CountryCode.LU] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.accountNumber(13, 'c')
      )
      structures[CountryCode.MF] = FRENCH_STRUCTURE
      structures[CountryCode.MK] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.accountNumber(10, 'c'),
        BbanStructureEntry.nationalCheckDigit(2, 'n')
      )
      structures[CountryCode.MT] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.branchCode(5, 'n'),
        BbanStructureEntry.accountNumber(18, 'c')
      )
      structures[CountryCode.MR] = BbanStructure(
        BbanStructureEntry.bankCode(5, 'n'),
        BbanStructureEntry.branchCode(5, 'n'),
        BbanStructureEntry.accountNumber(11, 'n'),
        BbanStructureEntry.nationalCheckDigit(2, 'n')
      )
      structures[CountryCode.MU] = BbanStructure(
        BbanStructureEntry.bankCode(6, 'c'),
        BbanStructureEntry.branchCode(2, 'n'),
        BbanStructureEntry.accountNumber(18, 'c')
      )
      structures[CountryCode.MD] = BbanStructure(
        BbanStructureEntry.bankCode(2, 'c'),
        BbanStructureEntry.accountNumber(18, 'c')
      )
      structures[CountryCode.MC] = FRENCH_STRUCTURE
      structures[CountryCode.ME] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.accountNumber(13, 'n'),
        BbanStructureEntry.nationalCheckDigit(2, 'n')
      )
      structures[CountryCode.MQ] = FRENCH_STRUCTURE
      structures[CountryCode.NC] = FRENCH_STRUCTURE
      structures[CountryCode.NL] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.accountNumber(10, 'n')
      )
      structures[CountryCode.NO] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'n'),
        BbanStructureEntry.accountNumber(6, 'n'),
        BbanStructureEntry.nationalCheckDigit(1, 'n')
      )
      structures[CountryCode.PF] = FRENCH_STRUCTURE
      structures[CountryCode.PK] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'c'),
        BbanStructureEntry.accountNumber(16, 'n')
      )
      structures[CountryCode.PM] = FRENCH_STRUCTURE
      structures[CountryCode.PS] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.accountNumber(21, 'c')
      )
      structures[CountryCode.PL] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.branchCode(4, 'n'),
        BbanStructureEntry.nationalCheckDigit(1, 'n'),
        BbanStructureEntry.accountNumber(16, 'n')
      )
      structures[CountryCode.PT] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'n'),
        BbanStructureEntry.branchCode(4, 'n'),
        BbanStructureEntry.accountNumber(11, 'n'),
        BbanStructureEntry.nationalCheckDigit(2, 'n')
      )
      structures[CountryCode.RE] = FRENCH_STRUCTURE
      structures[CountryCode.RO] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.accountNumber(16, 'c')
      )
      structures[CountryCode.QA] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.accountNumber(21, 'c')
      )
      structures[CountryCode.SC] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.branchCode(4, 'n'),
        BbanStructureEntry.accountNumber(16, 'n'),
        BbanStructureEntry.accountType(3, 'a')
      )
      structures[CountryCode.SM] = BbanStructure(
        BbanStructureEntry.nationalCheckDigit(1, 'a'),
        BbanStructureEntry.bankCode(5, 'n'),
        BbanStructureEntry.branchCode(5, 'n'),
        BbanStructureEntry.accountNumber(12, 'c')
      )
      structures[CountryCode.ST] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'n'),
        BbanStructureEntry.branchCode(4, 'n'),
        BbanStructureEntry.accountNumber(13, 'n')
      )
      structures[CountryCode.SA] = BbanStructure(
        BbanStructureEntry.bankCode(2, 'n'),
        BbanStructureEntry.accountNumber(18, 'c')
      )
      structures[CountryCode.RS] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.accountNumber(13, 'n'),
        BbanStructureEntry.nationalCheckDigit(2, 'n')
      )
      structures[CountryCode.SK] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'n'),
        BbanStructureEntry.accountNumber(16, 'n')
      )
      structures[CountryCode.SI] = BbanStructure(
        BbanStructureEntry.bankCode(2, 'n'),
        BbanStructureEntry.branchCode(3, 'n'),
        BbanStructureEntry.accountNumber(8, 'n'),
        BbanStructureEntry.nationalCheckDigit(2, 'n')
      )
      structures[CountryCode.SV] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.accountNumber(20, 'n')
      )
      structures[CountryCode.ES] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'n'),
        BbanStructureEntry.branchCode(4, 'n'),
        BbanStructureEntry.nationalCheckDigit(2, 'n'),
        BbanStructureEntry.accountNumber(10, 'n')
      )
      structures[CountryCode.SE] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.accountNumber(17, 'n')
      )
      structures[CountryCode.CH] = BbanStructure(
        BbanStructureEntry.bankCode(5, 'n'),
        BbanStructureEntry.accountNumber(12, 'c')
      )
      structures[CountryCode.TF] = FRENCH_STRUCTURE
      structures[CountryCode.TN] = BbanStructure(
        BbanStructureEntry.bankCode(2, 'n'),
        BbanStructureEntry.branchCode(3, 'n'),
        BbanStructureEntry.accountNumber(15, 'c')
      )
      structures[CountryCode.TR] = BbanStructure(
        BbanStructureEntry.bankCode(5, 'n'),
        BbanStructureEntry.nationalCheckDigit(1, 'c'),
        BbanStructureEntry.accountNumber(16, 'c')
      )
      structures[CountryCode.UA] = BbanStructure(
        BbanStructureEntry.bankCode(6, 'n'),
        BbanStructureEntry.accountNumber(19, 'n')
      )
      structures[CountryCode.GB] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.branchCode(6, 'n'),
        BbanStructureEntry.accountNumber(8, 'n')
      )
      structures[CountryCode.AE] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.accountNumber(16, 'c')
      )
      structures[CountryCode.VA] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.accountNumber(15, 'n')
      )
      structures[CountryCode.VG] = BbanStructure(
        BbanStructureEntry.bankCode(4, 'a'),
        BbanStructureEntry.accountNumber(16, 'n')
      )
      structures[CountryCode.TL] = BbanStructure(
        BbanStructureEntry.bankCode(3, 'n'),
        BbanStructureEntry.accountNumber(14, 'n'),
        BbanStructureEntry.nationalCheckDigit(2, 'n')
      )
      structures[CountryCode.WF] = FRENCH_STRUCTURE
      structures[CountryCode.XK] = BbanStructure(
        BbanStructureEntry.bankCode(2, 'n'),
        BbanStructureEntry.branchCode(2, 'n'),
        BbanStructureEntry.accountNumber(10, 'n'),
        BbanStructureEntry.nationalCheckDigit(2, 'n')
      )
      structures[CountryCode.YT] = FRENCH_STRUCTURE
    }
    /**
     * @param countryCode the country code.
     * @return BbanStructure for specified country or null if country is not supported.
     */
    fun forCountry(countryCode: CountryCode): BbanStructure? {
      return structures[countryCode]
    }

    fun supportedCountries(): List<CountryCode> {
      val countryCodes: MutableList<CountryCode> = ArrayList(
        structures.size
      )
      countryCodes.addAll(structures.keys)
      return Collections.unmodifiableList(countryCodes)
    }
  }
}
