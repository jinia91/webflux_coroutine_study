package com.example.webflux_coroutine_study.basic

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    coroutineScope {
        launch {
            delay(1000L)
            println("Inside coroutineScope's launch")
        }
        println("Inside coroutineScope")
    }

    launch {
        delay(1000L)
        println("Outside coroutineScope's launch")
    }
}
