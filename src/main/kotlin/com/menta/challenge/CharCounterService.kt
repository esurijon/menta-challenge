package com.menta.challenge

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class CharCounterService(
    @Value("\${char-counter.default-locale}")
    private val defaultLocale: Locale,
    @Value("\${char-counter.skip-chars}")
    skipChars: String,
    private val charMapper: CharmapGenerator
) {

    private val skipChars = skipChars.toSet()

    init {
        charMapper.getCharMapForLocale(defaultLocale)
    }

    fun countClosedSpaces(text: String, locale: Locale = defaultLocale): Int {
        val closedSpacesByChar: Map<Char, Int> = charMapper.getCharMapForLocale(defaultLocale)
        return text
            .withIndex()
            .filterNot { (_, value) ->
                skipChars.contains(value)
            }.sumOf { (index, char) ->
                val count = closedSpacesByChar[char]
                count ?: throw InvalidCharacterException(char, index)
            }
    }

}

class InvalidCharacterException(val char: Char, val position: Int) :
    Exception("Invalid character '$char' at position $position")