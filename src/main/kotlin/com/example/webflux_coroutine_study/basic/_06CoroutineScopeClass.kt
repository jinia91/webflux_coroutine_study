package com.example.webflux_coroutine_study.basic

import kotlinx.coroutines.*

private val log = mu.KotlinLogging.logger { }


// 코루틴을 관리하는 접근법 : 명시적으로 CoroutineScope을 필드로 갖는 클래스 만들기
class MyCoroutineClass : CoroutineScope by CoroutineScope(Dispatchers.Default) {

    fun startCoroutines() {
        launch {
            log.info("Starting coroutine 1")
            delay(1000)
            log.info("Coroutine 1 is done")
        }

        launch {
            log.info("Starting coroutine 2")
            delay(2000)
            log.info("Coroutine 2 is done")
        }
    }

    // 클래스가 더 이상 사용되지 않을 때 코루틴을 정리합니다.
    fun cleanUp() {
        cancel()
    }
}

fun main() = runBlocking {
    val myCoroutineClass = MyCoroutineClass()

    myCoroutineClass.startCoroutines()

    // 일부 시간 대기하여 코루틴이 완료될 수 있도록 합니다.
    delay(3000)

    myCoroutineClass.cleanUp()
}