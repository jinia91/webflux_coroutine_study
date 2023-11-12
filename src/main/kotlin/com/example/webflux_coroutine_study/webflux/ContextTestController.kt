package com.example.webflux_coroutine_study.webflux

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.ReactorContext
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val log = mu.KotlinLogging.logger { }

suspend fun getLocalMap() = currentCoroutineContext()[ReactorContext]?.context?.get<MutableMap<String,String>>("local")

@RestController
class ContextTestController {

    @RequestMapping("/context")
    suspend fun context(): String = coroutineScope {

        val local = getLocalMap()!!
        local["parent"] = "parent"
        log.info { "1 $local" }

        launch {
            val sub = getLocalMap()!!
            sub["sub"] = "sub"
            log.info { "2 $sub" }
            delay(1000)
        }
        delay(1000)
        val parent = getLocalMap()!!
        log.info { "3 $parent" }
        getLocalMap()!!["sub"]!!
    }
}