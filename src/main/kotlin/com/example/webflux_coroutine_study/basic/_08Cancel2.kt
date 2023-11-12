package com.example.webflux_coroutine_study.basic

import kotlinx.coroutines.*

/*
suspend fun doCount() = coroutineScope {
    val job1 = launch {
        var i = 1
        var nextTime = System.currentTimeMillis() + 100L

        while (i <= 10) {
            val currentTime = System.currentTimeMillis()
            if (currentTime >= nextTime) {
                println(i)
                nextTime = currentTime + 100L
                i++
            }
        }
    }
    println("launch finish")
    delay(200L)
    job1.cancel()
    println("doCount Done!")
}
*/

// suspend 지점이 없으면 cancel이 안된다.
// 왜나면 cps 스타일을 고려할 때 suspend 지점이 없다면 외부의 job cancel 호출을 Continuation 인터페이스가 받을 수 없기 때문이다.


//suspend fun doCount() = coroutineScope {
//    val job1 = launch {
//        var i = 1
//        var nextTime = System.currentTimeMillis() + 100L
//
//        while (i <= 10) {
//            val currentTime = System.currentTimeMillis()
//            if (currentTime >= nextTime) {
//                println(i)
//                delay(100L)
//                i++
//            }
//        }
//    }
//    println("launch finish")
//    delay(200L)
//    job1.cancel()
//    println("doCount Done!")
//}

// suspend지점을 delay 로 만드는 방법 이외에도

suspend fun doCount() = coroutineScope {
    val job1 = launch {
        var i = 1
        var nextTime = System.currentTimeMillis() + 100L

        while (i <= 10 && isActive) {
            val currentTime = System.currentTimeMillis()
            if (currentTime >= nextTime) {
                println(i)
                delay(100L)
                i++
            }
        }
    }
    println("launch finish")
    delay(200L)
//    job1.cancel()
    job1.cancelAndJoin()
    println("doCount Done!")
}

// isActive 를 사용하는 방법도 있다.
// 또한 cancelAndJoin 을 사용하면 cancel 후 join 을 한번에 할 수 있다.

fun main() = runBlocking {
    doCount()
}