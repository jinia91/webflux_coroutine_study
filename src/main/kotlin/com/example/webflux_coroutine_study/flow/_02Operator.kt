package com.example.webflux_coroutine_study.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

suspend fun someCalc(i: Int): Int {
    return i * 2
}

fun main() = runBlocking<Unit> {
    (1..20).asFlow().transform {
        emit(it)
        emit(someCalc(it)) // filter + map + 여러번 emit 가능, filter 나 map 은 한번 emit이 자동강제되는 개념
    }.collect {
        println(it)
    }
}