package com.example.webflux_coroutine_study.basic

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
fun main() {
    GlobalScope.launch {
        delay(1000L) // 1초간 논블로킹으로 기다린다.
        println("World!") // 지연 후 출력
    }
    println("Hello,") // 메인 스레드가 코루틴이 지연되는 동안 계속 실행된다.
    Thread.sleep(2000L) // JVM이 종료되지 않게 메인 스레드를 블로킹한다.
}