package com.menta.challenge

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class CharmapGenerator(
    @Value("\${char-counter.mappings-by-locale}")
    mappingsByLocale: String
) {


    private val charMapsByLocale : Map<Locale, Map<Char, Int>>

    init {
        charMapsByLocale = ObjectMapper()
            .readValue(mappingsByLocale, object : TypeReference<Map<Locale, List<String>>>() {})
            .let {
                it.mapValues { (_, mappings) ->
                    mappingToCharMap(mappings)
                }
            }
    }

    private fun mappingToCharMap(mapping: List<String>): Map<Char, Int> = mapping
        .flatMapIndexed { count, chars ->
            chars.map {  it to count }
        }
        .toMap()

    fun getCharMapForLocale(locale: Locale): Map<Char, Int> = charMapsByLocale[locale]
        ?: throw InvalidLocaleExcpetion(locale)

}

class InvalidLocaleExcpetion(val locale: Locale)
    : Exception("No charmap found for locale $locale")