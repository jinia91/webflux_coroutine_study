package com.example.webflux_coroutine_study.flow

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

@OptIn(FlowPreview::class)
fun main() = runBlocking<Unit> {
    (1..100).asFlow().collect { println(it) }

    val list = MutableList(101) { it }
    for (i in 1..100) {
        list[i] = i
    }

    list.asFlow().collect() { println(it) }

}