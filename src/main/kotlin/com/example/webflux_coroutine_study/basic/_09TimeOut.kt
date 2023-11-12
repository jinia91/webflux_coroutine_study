package com.example.webflux_coroutine_study.basic

import kotlinx.coroutines.*

suspend fun `타임아웃`() = coroutineScope {
    val job1 = launch(Dispatchers.Default) {
        var i = 1
        var nextTime = System.currentTimeMillis() + 100L

        while (i <= 10 && isActive) { // 코루틴 논블로킹 waiting
            val currentTime = System.currentTimeMillis()
            if (currentTime >= nextTime) {
                println(i)
                nextTime = currentTime + 100L
                i++
            }
        }
    }
}

//fun main() = runBlocking {
//    withTimeout(500L) {
//        `타임아웃`()
//    }
//}

fun main() = runBlocking {
    val result = withTimeoutOrNull(500L) {
        `타임아웃`()
        true
    } ?: false
    println(result)
}