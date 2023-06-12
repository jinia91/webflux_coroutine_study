package com.example.webflux_coroutine_study.basic

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging

private val log = KotlinLogging.logger { }

@DelicateCoroutinesApi
fun main() {
    GlobalScope.launch {  // GlobalScope.launch는 메인스레드를 블로킹하지 않는다.
        delay(1000L)
        log.info("World!")
    }
    log.info("Hello,")
    runBlocking { // runBlocking은 메인스레드를 블로킹한다.
        delay(2000L)
        log.info("!!")
    }
}