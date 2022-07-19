package com.menta.challenge

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.TextNode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/char-counter")
class CharCounterController(
    private val charCounterService: CharCounterService
) {

    @PostMapping
    private fun getEmployeeById(@RequestBody text: String): Mono<Int> = charCounterService
        .countClosedSpaces(text)
        .let { Mono.just(it) }

    @ExceptionHandler(InvalidCharacterException::class)
    fun handle(ex: Exception): ResponseEntity<JsonNode> = ResponseEntity
        .badRequest()
        .body(TextNode.valueOf(ex.message))

}