package com.example.webflux_coroutine_study.basic

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

private val log = mu.KotlinLogging.logger {}

@OptIn(FlowPreview::class)
fun main(): Unit = runBlocking {

    val aFlow = flow<String> {
        while (true) {
            emit("A")
            delay(100) // 1 second delay
        }
    }

//    marketRepo.findAllByCategory(category)
//        .flatMapMerge { market ->
//            flow {
//                val clickCnt = marketClickRepo.findCountByMarket(market)
//                market.setClickCount(MarketClickCnt(clickCnt))
//                emit(MarketInfo.from(category, market))
//            }
//        }.toList()

    aFlow.flatMapMerge { a ->
        flow<String> {
            log.info("start $a ${LocalDateTime.now()}")
            delay(1000)
            log.info("end $a ${LocalDateTime.now()}")
            a
        }
    }.toList()


}