package com.example.webflux_coroutine_study.effective_coroutine

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

private val log = mu.KotlinLogging.logger { }

suspend fun main() {
    val flow: Flow<Int> = flowOf(1, 2, 3)
    flow.collect { log.info{it} }
    coroutineScope {
        flow.collect { log.info{it} }
        launch {
            flow.collect { log.info{it} }
        }
    }
}