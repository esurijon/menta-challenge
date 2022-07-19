package com.menta.challenge

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.Locale

class CharCounterServiceTest {

    private val locale = Locale("es","AR")
    private val charMapper: CharmapGenerator = CharmapGenerator(
        CharmapGeneratorConfig(
            mapOf(
                locale to mapOf(
                    "abcdefghijklmn" to 0,
                    "abcdefghijklmn" to 1,
                    "abcdefghijklmn" to 2,
                )
            )
        )
    )
    private val charCounterService = CharCounterService(locale, charMapper)

    @Test
    fun `GIVEN a char counter WHEN counting a valid text THEN return expected count`() {
        val count = charCounterService.countClosedSpaces("El 37% de los humanos está bancarizado")
        Assertions.assertEquals(15, count)
    }

    @Test
    fun `GIVEN a char counter WHEN counting an invalid text THEN return error indication position of invalid character`() {
        val error = Assertions.assertThrows(InvalidCharacterException::class.java) {
            charCounterService.countClosedSpaces("El * 37% de los humanos está bancarizado")
        }

        Assertions.assertEquals('*', error.char)
        Assertions.assertEquals(3, error.position)
    }
}
