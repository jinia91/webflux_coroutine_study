package com.example.webflux_coroutine_study.webflux

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

private val log = mu.KotlinLogging.logger { }

@RestController
class SampleController {

    @RequestMapping("/test01")
    suspend fun test01(): String = coroutineScope {

        log.info { "scope start" }

        val firstDataDeferred = async {
            getRandomValueInRepo()
        }
        val secondDataDeferred = async {
            getRandomValueInRepo()
        }

        val firstData = firstDataDeferred.await()
        val secondData = secondDataDeferred.await()

        launch {
            log.info { "something Task with datas" }
            delay(5000L)
            log.info { "$firstData + $secondData" }
        }

        // cpu bounded task
        launch {
            val currentTime = System.currentTimeMillis()
            while (System.currentTimeMillis() - currentTime < 10000L) {
            }
        }
        firstData + secondData
    }

    private suspend fun getRandomValueInRepo(): String {
        log.info { "getRandomValueInRepo" }
        delay(5000L)
        return UUID.randomUUID().toString()
    }
}