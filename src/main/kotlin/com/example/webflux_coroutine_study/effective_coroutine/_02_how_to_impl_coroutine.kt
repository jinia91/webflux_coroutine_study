package com.example.webflux_coroutine_study.effective_coroutine

import com.example.webflux_coroutine_study.basic.blockingWaiting
import kotlinx.coroutines.delay
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume

private val log = mu.KotlinLogging.logger { }

suspend fun foo1() {
    log.info { "Start" }
    delay(1000L)
    log.info { "End" }
}

// => cps 로 실제로 구현해보기


/**
 * 코루틴 핵심 인터페이스 Continuation 구현체
 * - 코루틴이 suspend 되는 경우의 상태 캡쳐 객체
 * - 그 순간의 진행 지점과 필요 정보들을 저장하며 여러 suspend함수들이 동기적으로 호출되도록 배치되어있을때 Continuation은 그 순서마다 데코레이팅하여
 * 일종의 콜백을 임의로 만들어 진행 흐름을 관리한다.
 */
open class MyContinuation(
    val continuation: Continuation<Unit> = StartContinuation<Unit>(
        EmptyCoroutineContext
    ),
) : Continuation<Unit> {
    // 실제 코루틴의 메타정보들을 저장하는 객체, 어떤 스레드에 진행되고 현재 코루틴이 어떤 상태고 어떤 부모를 가지는지 등등
    override val context: CoroutineContext
        get() = continuation.context

    // 필요하다면 해당 함수에 필요한 정보를 저장할 수 있다, 스택영역에 있어야할 로컬 변수들을 저장하는 셈

    var label: Int = 0
    var result: Result<Any>? = null

    override fun resumeWith(result: Result<Unit>) {
        this.result = result
        val res = try {
            val r = fooAsCps(this)
            if (r == COROUTINE_SUSPENDED) return
            Result.success(r as Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
        continuation.resumeWith(res)
    }
}

class StartContinuation<Unit>(override val context: CoroutineContext) : Continuation<Unit> {
    override fun resumeWith(result: Result<Unit>) {}
}

const val COROUTINE_SUSPENDED = "COROUTINE_SUSPENDED"

// 지연 및 디스패칭 된 이후 작업을 실행하는 스레드
fun delay(time: Long, continuation: Continuation<Unit>): Any {
    val timer = Executors.newSingleThreadScheduledExecutor {
        Thread(it, "timer").apply { isDaemon = true }
    }

    timer.schedule({
        continuation.resume(Unit)
    }, time, TimeUnit.MILLISECONDS)

    return COROUTINE_SUSPENDED
}

// 실제 코루틴
fun fooAsCps(continuation: MyContinuation): Any {
    val continuation = continuation as? MyContinuation
        ?: MyContinuation(continuation)

    if (continuation.label == 0) {
        log.info { "Start" }
        continuation.label = 1
        if (delay(1000L, continuation) == COROUTINE_SUSPENDED) return COROUTINE_SUSPENDED
    }
    if (continuation.label == 1) {
        log.info { "End" }
    }
    return Unit
}

fun main() {
    fooAsCps(MyContinuation())
}

/**
 * suspend 함수가 여러번 호출되는 코드의 경우 동기적인 순서로 동작해야한다면
 * 각각의 suspend 함수가 호출될때마다 기존의 continuation을 데코레이터로 감싸면서 일종의 콜스택을 만든다
 * 해당 continutaion은 해당 suspend함수에 필요한 정보를 가지고 있으므로 stack 영역에 해당 로컬 변수들이 저장되지 않아도 / 다른 스레드가 이어가도 괜찮다
 */