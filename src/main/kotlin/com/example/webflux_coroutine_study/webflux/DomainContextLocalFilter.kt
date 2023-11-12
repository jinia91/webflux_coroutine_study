package com.example.webflux_coroutine_study.webflux

import kotlinx.coroutines.reactor.asCoroutineContext
import kotlinx.coroutines.withContext
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Configuration
import org.springframework.util.ConcurrentReferenceHashMap
import org.springframework.web.server.CoWebFilter
import org.springframework.web.server.CoWebFilterChain
import org.springframework.web.server.ServerWebExchange
import reactor.util.context.Context
import java.util.concurrent.ConcurrentHashMap

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
class DomainContextLocalFilter : CoWebFilter() {

    override suspend fun filter(exchange: ServerWebExchange, chain: CoWebFilterChain) {

        val map = ConcurrentHashMap<String, String>()
        val context = Context.of("local", map).asCoroutineContext()

        withContext(context) {
            chain.filter(exchange)
        }
    }
}