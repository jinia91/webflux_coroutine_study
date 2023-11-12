package com.example.webflux_coroutine_study.basic

import kotlinx.coroutines.*

suspend fun `취소가 안되는 코루틴`() = coroutineScope {
    val job1 = launch {
        withContext (NonCancellable) {
            println("launch1: ${Thread.currentThread().name}")
            delay(1000L)
            println("3!")
        }
        println("job1: end") // cancel 수신 시점이 없음, 불가능
    }

    val job2 = launch {
        coroutineScope {// 코루틴 스코프 내에서는 취소가 가능함
            println("launch1: ${Thread.currentThread().name}")
            delay(1000L)
            println("3!")
        }
        println("job2: end")
    }

    val job3 = launch {
        withContext(NonCancellable) {
            println("launch1: ${Thread.currentThread().name}")
            delay(1000L)
            println("1!")
        }
        delay(1000L) // cancel 수신 가능함
        println("job3: end")
    }

    val job4 = launch {
        withContext(NonCancellable) {
            println("launch1: ${Thread.currentThread().name}")
            delay(1000L)
            println("2!")
        }

        blockingWaiting(1000L) // cancel 수신 불가능함

        println("job4: end")
    }

    delay(800L)
    job1.cancel()
    job2.cancel()
    job3.cancel()
    job4.cancel()
    println("4!")
}

fun blockingWaiting(ms : Long) {
    var i = 1
    var nextTime = System.currentTimeMillis() + 100L
    while (i <= ms / 100L) {
        val currentTime = System.currentTimeMillis()
        if (currentTime >= nextTime) {
            nextTime = currentTime + 100L
            i++
        }
    }
}

fun main() = runBlocking {
    `취소가 안되는 코루틴`()
    println("runBlocking: ${Thread.currentThread().name}")
    println("5!")
    delay(1000L)
}