package com.example.webflux_coroutine_study.basic

import reactor.core.publisher.Flux
import java.time.Duration

private val log = mu.KotlinLogging.logger {}

fun main(args: Array<String>) {
    // 첫 번째 Publisher: 매 초마다 증가하는 Long 값을 생성합니다.
    val firstPublisher = Flux.interval(Duration.ofSeconds(1))
        .map { i: Long -> i + 1 }
        .doOnNext { i: Long -> log.info(" Publisher get $i and emits: $i") }
        .log()
        .publish()
        .autoConnect()

    val secondPublisher = firstPublisher
        .map { i: Long -> i * 2 }
        .doOnNext { i: Long -> log.info("Mediator double processing: $i") }
        .log()
        .publish()
        .autoConnect()


    secondPublisher
        .log()
        .subscribe(
            { i: Long ->
                val endTime = System.currentTimeMillis() + 5000
                while (System.currentTimeMillis() < endTime) {}
                log.info("Subscriber received total data: $i")
            },
            { error: Throwable -> System.err.println("Error: $error") }
        ) { println("Stream Completed") }

    try {
        // 약간의 시간 동안 메인 스레드를 대기 상태로 유지하여
        // 비동기적으로 실행되는 Flux가 값을 방출할 수 있게 합니다.
        Thread.sleep(50000)
    } catch (e: InterruptedException) {
        Thread.currentThread().interrupt()
    }
}

