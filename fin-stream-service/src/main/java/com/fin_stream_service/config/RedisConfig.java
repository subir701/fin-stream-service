package com.fin_stream_service.config;

import com.fin_stream_service.common.dto.StockUpdate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, StockUpdate> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory){
        // 1. Step JSON Serializer for the StockUpdate DTO
        Jackson2JsonRedisSerializer<StockUpdate> serializer = new Jackson2JsonRedisSerializer<>(StockUpdate.class);

        // 2. Build the Serialization Context
        RedisSerializationContext.RedisSerializationContextBuilder<String, StockUpdate> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        // 3. Define how Keys, Values, and Hash fields are serialized
        RedisSerializationContext<String, StockUpdate> context = builder
                .value(serializer)
                .hashValue(serializer)
                .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
