package com.temp.springrediscache.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import kotlin.properties.Delegates

@Configuration
@ConfigurationProperties("spring.data.redis")
class RedisPropertiesData {
    lateinit var host: String
    var port by Delegates.notNull<Int>()
}