package com.example.webflux_coroutine_study.channel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.sync.*

// channel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlin.system.measureTimeMillis
//
//fun main() : Unit = runBlocking {
//    val channel = Channel<Int>()
//
//    val producer = launch {
//        repeat(1_000_000) { // 100만 번 반복
//            channel.send(it)
//        }
//        channel.close()
//    }
//
//    val time = measureTimeMillis {
//        val consumer = launch {
//            var sum = 0
//            for (item in channel) {
//                sum += item
//            }
//            println("Sum: $sum")
//        }
//
//        producer.join()
//        consumer.join()
//    }
//
//    println("Execution time: $time ms") // 4xx ms
//}

// mutex
//
//fun main() = runBlocking {
//    val mutex = Mutex()
//    var sum = 0
//
//    val jobs = List(1_000_000) {
//        launch {
//            mutex.withLock {
//                sum += it
//            }
//        }
//    }
//
//    val time = measureTimeMillis {
//        jobs.forEach { it.join() }
//    }
//
//    println("Sum: $sum")
//    println("Execution time: $time ms") // 2xx ms
//}
//
