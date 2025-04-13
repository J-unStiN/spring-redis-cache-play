package com.temp.springrediscache

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringRedisCacheApplication

fun main(args: Array<String>) {
    runApplication<SpringRedisCacheApplication>(*args)
}
