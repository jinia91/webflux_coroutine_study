package com.example.webflux_coroutine_study.basic

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = mu.KotlinLogging.logger {}

fun main(): Unit = runBlocking {
    repeat(100) {
        val a= async {
            log.info { "async1" }
            delay(1000)
        }
    }
}