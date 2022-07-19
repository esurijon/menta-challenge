package com.menta.challenge

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class CharCounterService(
    @Value("\${char-counter.default-locale}")
    private val defaultLocale: Locale,
    private val charMapper: CharmapGenerator
) {

    init {
        charMapper.getCharMapForLocale(defaultLocale)
    }

    fun countClosedSpaces(text: String, locale: Locale = defaultLocale): Int {
        val closedSpacesByChar: Map<Char, Int> = charMapper.getCharMapForLocale(defaultLocale)
        return text
            .mapIndexed { index, char ->
                val count = closedSpacesByChar[char]
                count ?: throw InvalidCharacterException(char, index)
            }
            .sum()
    }

}

class InvalidCharacterException(val char: Char, val position: Int) :
    Exception("Invalid character '$char' at position $position")