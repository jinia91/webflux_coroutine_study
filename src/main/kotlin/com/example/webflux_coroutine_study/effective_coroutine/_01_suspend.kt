package com.example.webflux_coroutine_study.effective_coroutine

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private val log = mu.KotlinLogging.logger { }

private val timer = Executors.newSingleThreadScheduledExecutor {
    Thread(it, "timer").apply { isDaemon = true }
}

/**
 * delay 함수 재현해보기
 * - suspendCoroutine 함수를 통해 코루틴을 일시정지 시키고
 * - timer를 통해 일정시간이 지난 후에 코루틴을 재개시킨다
 *
 */
//suspend fun main() {
//    coroutineScope {
//        log.info { "start" }
//        delay(5000L)
//        log.info { "end" }
//    }
//    log.info { "exit" }
//}

/**
 * suspendCoroutine 함수는 코루틴이 아니다
 * coroutine 빌더 사용 x
 * 멈춰진 코루틴의 부모 코루틴에서 resume을 한다면 가능
 *
 */
suspend fun main() {
    coroutineScope {

        var stoppedContinuation : Continuation<Unit>? = null

        launch {
            log.info { "start" }
            suspendCoroutine<Unit> {
                log.info {"delay"}
                stoppedContinuation = it
            }
            log.info { "end" }
        }

        delay(1000L)

        stoppedContinuation?.resume(Unit)
        log.info { "exit" }
    }
}

private suspend fun delay(time: Long) {
    suspendCoroutine<Unit> {
        log.info { "suspendCoroutine" }
        timer.schedule(
            {
                log.info { "timer" }
                it.resume(Unit)
            },
            time,
            TimeUnit.MILLISECONDS
        )
    }
}
