package org.a2cd.boot.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.a2cd.boot.component.CommonStreamListener;
import org.a2cd.boot.component.MoStreamListener;
import org.a2cd.boot.component.UsnStreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;

/**
 * @author a2cd
 * @since 2024-01-21
 */

@Slf4j
// @Configuration
@AllArgsConstructor
public class StreamConfig {

    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, String>> commonStreamMessageListenerContainer(RedisConnectionFactory factory, CommonStreamListener listener) {

        var operations = stringRedisTemplate.opsForStream();
        // 创建一个流
        try {
            operations.createGroup("common-stream", ReadOffset.from("0"), "group-0");
        } catch (Exception e) {
            // 流可能已存在，忽略异常
        }

        var opts = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ZERO)
                .targetType(String.class)
                .build();
        var container = StreamMessageListenerContainer.create(factory, opts);
        var streamOffset = StreamOffset.create("common-stream", ReadOffset.lastConsumed());
        var streamReq = StreamMessageListenerContainer.StreamReadRequest
                .builder(streamOffset)
                .consumer(Consumer.from("group-0", "your-consumer-name"))
                .cancelOnError(throwable -> {
                    return false; // 不管发生什么异常都不要cancel
                })
                .autoAcknowledge(false)
                .build();

        container.register(streamReq, listener);
        container.start();
        return container;
    }

    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, String>> moStreamMessageListenerContainer(RedisConnectionFactory factory, MoStreamListener listener) {

        var operations = stringRedisTemplate.opsForStream();
        // 创建一个流
        try {
            operations.createGroup("mo-stream", ReadOffset.from("0"), "group-0");
        } catch (Exception e) {
            // 流可能已存在，忽略异常
        }

        var opts = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ZERO)
                .targetType(String.class)
                .build();
        var container = StreamMessageListenerContainer.create(factory, opts);
        var streamOffset = StreamOffset.create("mo-stream", ReadOffset.lastConsumed());
        var streamReq = StreamMessageListenerContainer.StreamReadRequest
                .builder(streamOffset)
                .consumer(Consumer.from("group-0", "your-consumer-name"))
                .cancelOnError(throwable -> {
                    return false; // 不管发生什么异常都不要cancel
                })
                .autoAcknowledge(false)
                .build();

        container.register(streamReq, listener);
        container.start();
        return container;
    }

    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, String>> usnStreamMessageListenerContainer(RedisConnectionFactory factory, UsnStreamListener listener) {

        var operations = stringRedisTemplate.opsForStream();
        // 创建一个流
        try {
            operations.createGroup("usn-stream", ReadOffset.from("0"), "group-0");
        } catch (Exception e) {
            // 流可能已存在，忽略异常
        }

        var opts = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ZERO)
                .targetType(String.class)
                .build();
        var container = StreamMessageListenerContainer.create(factory, opts);
        var streamOffset = StreamOffset.create("usn-stream", ReadOffset.lastConsumed());
        var streamReq = StreamMessageListenerContainer.StreamReadRequest
                .builder(streamOffset)
                .consumer(Consumer.from("group-0", "your-consumer-name"))
                .cancelOnError(throwable -> {
                    return false; // 不管发生什么异常都不要cancel
                })
                .autoAcknowledge(false)
                .build();

        container.register(streamReq, listener);
        container.start();
        return container;
    }
}
