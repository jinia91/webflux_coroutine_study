package com.example.webflux_coroutine_study.effective_coroutine

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext

private val log = mu.KotlinLogging.logger { }

suspend fun main() {
    val getSuperContext = currentCoroutineContext()
    log.info { getSuperContext } // 기본적으로 EmptyCoroutineContext
    val customContext = CoroutineName("new context data")

    withContext(customContext) {
        val getSubContext = currentCoroutineContext()
        log.info { getSubContext }
    }

    val getSuperContext2 = currentCoroutineContext()
    log.info { getSuperContext2 } // 자식 컨텍스트는 추가가 안됨,
    // 웹플럭스와 함께 사용한다면 리액터 컨텍스트가 최상단부터 존재하므로 자식과 부모간 컨텍스트를 공유하는 객체가 존재하는셈
    // 이걸 활용하면 aop 동작시 스레드로컬처럼 사용할 방법이 있을듯? - 테스트해볼예정
}

// webflux사용시엔 ReactorContext가 내부에 들어있음!
