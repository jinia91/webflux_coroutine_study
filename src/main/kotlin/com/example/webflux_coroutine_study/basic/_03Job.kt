package com.example.webflux_coroutine_study.basic

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@DelicateCoroutinesApi
fun main() = runBlocking {
//    val job : Job = GlobalScope.launch { // GlobalScope.launch는 runBlocking의 자식 스코프가 아니므로 생명주기를 책임지지 않는다.
//        delay(1000L)
//        println("World!")
//    }
    val job : Job = launch {// runBlocking의 자식 스코프이므로 생명주기를 책임진다.
        delay(1000L)
        println("World!")
    }
    println("Hello,")
//    job.join() // async await 느낌, globalScope라면 join을 해줘야함
}