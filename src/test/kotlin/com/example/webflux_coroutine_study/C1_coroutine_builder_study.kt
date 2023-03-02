package com.example.webflux_coroutine_study

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import kotlin.coroutines.coroutineContext

private val log = KotlinLogging.logger { }

class C1_coroutine_builder_study {

    @Test
    fun helloWorldCoroutine() {
        log.info { this }  // CoroutineStudy 인스턴스
        log.info { "1. Hello" }
        runBlockingFoo()
        log.info { "3. World" }
    }

    // 블로킹
    private fun runBlockingFoo() = runBlocking {
        log.info { "2. Hello World" }
        log.info { "$this is CoroutineScope instance!" } // blockingCoroutine 객체 새로 생성해서 그 수신 객체가 코드블록을 실행
        // 블로킹 코루틴은 코루틴스코프의 서브클래스, 코루틴은 코루틴스코프가 실행한다!
    }

    @Test
    fun `코루틴 빌더`() {
//        runBlockingFoo2()
        runLaunchFoo()
    }

    private fun runBlockingFoo2() = runBlocking {
        log.info { coroutineContext } // 코루틴 스코프의 프로퍼티
        // [CoroutineId(1), "coroutine#1":BlockingCoroutine{Active}@32c8e539, BlockingEventLoop@73dce0e6]
        log.info { this }
    }

    /**
     * Run launch foo
     *
     * 할수 있다면 다른 코루틴 코드를 같이 수행시키는 코루틴 빌더
     *
     * 다만 해당 예제에서는 하나의 스레드에서만 실행하므로 결국 코루틴은 작성한 순서대로 큐에 들어가고 FIFO로 실행되는 사실상 동기 블로킹로직이라 봐야함
     *
     * 또한 코루틴 스코프는 하위 코루틴스코프를 책임지기때문에(계층적) runBlocking 스코프는 하위 런치들이 끝나기전까진 끝나지 않음
     *
     */
    private fun runLaunchFoo() = runBlocking {
        log.info { "CoroutineId(1)" }
        log.info { coroutineContext }
        log.info { this }
        log.info { "1. Hello" }
        launch {
            log.info { "CoroutineId(2)" }
            log.info { coroutineContext }
            log.info { this }
            log.info { "6. World" }
        }
        launch {
            log.info { "CoroutineId(3)" }
            log.info { coroutineContext }
            log.info { this }
            log.info { "7. World" }
        }
        launch {
            log.info { "CoroutineId(4)" }
            log.info { coroutineContext }
            log.info { this }
            log.info { "8. World" }
        }
        log.info { "2. Hello" }
        log.info { "3. Hello" }
        /**
         *  Main Thread가 CoroutineId(1)를 실행하다가 delay함수를 만나 쉬게되면 해당 스코프가 잠시 멈추며 다른 코루틴로직인 CoroutineId(2)를
         *  실행하게된다!
         *
         *  만약 스레드 슬립을걸면 실행할 스레드가 없어서 진행안됨
         *
         */
        delay(1000)
        log.info { "4. Hello" }
        log.info { "5. Hello" }
    }

    @Test
    fun `suspend 사용해보기`() {
        val result = runBlocking<Long> {
            log.info { "1. runBlocking : $this" }
            launch {
                doTwo()
            }
            launch {
                doOne()
            }
            launch {
                doThree()
            }
            delay(2000)
            log.info { "5. finish" }
            5L
        }
        log.info { result }
    }

    private suspend fun doOne(){
        log.info { "2. launch 1 " }
    }

    private suspend fun doTwo(){
        delay(500)
        log.info { "3. launch 2 " }
    }

    private suspend fun doThree(){
        delay(1000)
        log.info { "4. launch 3 " }
    }

}