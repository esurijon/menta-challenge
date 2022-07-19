package com.menta.challenge

import org.springframework.stereotype.Service
import java.util.Locale

@Service
class CharmapGenerator(
    charmapGeneratorConfig: CharmapGeneratorConfig = CharmapGeneratorConfig(
        mapOf(
            Locale("es", "AR") to listOf(
                "123457cfhíklmnñrstuúvwxyzCEÉFGHIÍJKLMNÑSTUÚVWXYZ#/()=_-",
                "469aábdeégijoópqAÁDOÓPQR@#&?¿",
                "08B%"
            )
        )
    )
) {

    private val charMapsByLocale : Map<Locale, Map<Char, Int>>

    init { charMapsByLocale = charmapGeneratorConfig.mappingsByLocale.mapValues { (_, mappings) ->
            mappingToCharMap(mappings)
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

//@ConfigurationProperties(prefix = "char-counter.mappings-by-locale")
data class CharmapGeneratorConfig(
    val mappingsByLocale: Map<Locale, List<String>>
)

class InvalidLocaleExcpetion(val locale: Locale)
    : Exception("No charmap found for locale $locale")