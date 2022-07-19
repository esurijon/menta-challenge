package com.menta.challenge

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class CharmapGenerator(
    charmapGeneratorConfig: CharmapGeneratorConfig = CharmapGeneratorConfig(
        mapOf(
            Locale("es", "AR") to mapOf(
                "abcdefghijklmn" to 0,
                "abcdefghijklmn" to 1,
                "abcdefghijklmn" to 2,
            )
        )
    )
) {

    private val charMapsByLocale : Map<Locale, Map<Char, Int>>

    init { charMapsByLocale = charmapGeneratorConfig.mappingsByLocale.mapValues { (_, mappings) ->
            mappingToCharMap(mappings)
        }
    }

    private fun mappingToCharMap(mapping: Map<String, Int>): Map<Char, Int> = mapping
        .flatMap { (chars, count) ->
            chars.map {  it to count }
        }
        .toMap()

    fun getCharMapForLocale(locale: Locale): Map<Char, Int> = charMapsByLocale[locale]
        ?: throw InvalidLocaleExcpetion(locale)

}

//@ConfigurationProperties(prefix = "char-counter.mappings-by-locale")
data class CharmapGeneratorConfig(
    val mappingsByLocale: Map<Locale, Map<String, Int>>
)

class InvalidLocaleExcpetion(val locale: Locale)
    : Exception("No charmap found for locale $locale")