package com.temp.springrediscache.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration


@EnableCaching
@Configuration
class RedisConfig(
    private val redisPropertiesData: RedisPropertiesData
) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val host = redisPropertiesData.host
        val port = redisPropertiesData.port

        return LettuceConnectionFactory(host, port)
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().apply {
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            registerModule(JavaTimeModule())
        }
    }


    @Bean
    fun redisTemplate(objectMapper: ObjectMapper): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            connectionFactory = redisConnectionFactory()
            keySerializer = StringRedisSerializer()
            valueSerializer = GenericJackson2JsonRedisSerializer(objectMapper)
            hashKeySerializer = StringRedisSerializer()
            hashValueSerializer = GenericJackson2JsonRedisSerializer(objectMapper)
            afterPropertiesSet()
        }
    }

    @Bean
    fun cacheManager(
        connectionFactory: RedisConnectionFactory,
        objectMapper: ObjectMapper
    ): RedisCacheManager {
        val defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .prefixCacheNameWith("bookstore:")
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer(objectMapper)))
            .disableCachingNullValues()

        val cacheConfigurations = mapOf(
            "books" to defaultConfig.entryTtl(Duration.ofMinutes(30)),
            "allBooks" to defaultConfig.entryTtl(Duration.ofMinutes(5)),
            "booksByAuthor" to defaultConfig.entryTtl(Duration.ofMinutes(15)),
            "booksByTitle" to defaultConfig.entryTtl(Duration.ofMinutes(10)),
            "booksByYear" to defaultConfig.entryTtl(Duration.ofMinutes(20)),
            "booksByPriceRange" to defaultConfig.entryTtl(Duration.ofMinutes(5)),
            "cheapBooksByAuthor" to defaultConfig.entryTtl(Duration.ofMinutes(15))
        )
        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(defaultConfig)
            .withInitialCacheConfigurations(cacheConfigurations)
            .transactionAware()
            .build()
    }


}