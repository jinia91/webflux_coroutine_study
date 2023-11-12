package com.example.webflux_coroutine_study.channel

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ObsoleteCoroutinesApi::class)
fun main() = runBlocking {
    val broadcastChannel = BroadcastChannel<Int>(10)

    val job1 = launch {
        println("Producer1")
        broadcastChannel.openSubscription().consumeEach {
            println("Consumer 1: $it")
        }
    }

    val job2 = launch {
        println("Producer2")
        broadcastChannel.openSubscription().consumeEach {
            println("Consumer 2: $it")
        }
    }
    delay(1000)
    broadcastChannel.send(1)
    broadcastChannel.send(2)
    delay(2000)

    job1.cancel()
    job2.cancel()

    broadcastChannel.close()
    Unit
}