package com.example.webflux_coroutine_study.basic

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val log = mu.KotlinLogging.logger { }

fun main() = runBlocking {

    val test = mutableListOf<String>("1번")

    launch {// launch로 스코프를 생성하면 부모와 함께 병렬로 상태머신이 돈다
        launch {
            test.add("2번")
            log.info { "in launch in launch test : $test ${this.coroutineContext}"}
            delay(1000L)
            log.info("A: Task from runBlocking")
        }
        log.info { "in launch in test : $test" }
        delay(200L)
        log.info("B: Task from runBlocking")
    }
    log.info { "in run block test : $test" }
    coroutineScope {// 코루틴 스코프 생성 코루틴 스코프 안의 코드가 다 처리되기전까진 블록 아래 코드가 실행되지 않는다
        launch {// 코루틴 스코프 내에서 새로운 코루틴 생성
            delay(500L)
            log.info("C: Task from nested launch")
        }
        delay(100L)
        log.info("D: Task from coroutine scope")
    }

    log.info("E: Coroutine launch is async")
    log.info { "in run block test later : $test" }

    coroutineScope {// 코루틴 스코프 생성 - 상태머신이 코루틴스코프 빌더 와 그 상단에서만 라벨링을 돈다
        launch {// 코루틴 스코프 내에서 새로운 코루틴 생성
            delay(500L)
            log.info("F: Task from nested launch")
        }
        delay(100L)
        log.info("G: Task from coroutine scope")
    }

    // 코루틴 스코프가 끝나면 상태머신이 다시 부모로 돌아간다
    log.info("H: Coroutine scope is group sync")
    log.info("I: Coroutine scope is over")
}