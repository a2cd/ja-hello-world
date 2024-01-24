package org.a2cd.boot.component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author a2cd
 * @since 2024-01-21
 */


@Slf4j
@Component
@AllArgsConstructor
public class MoStreamListener implements StreamListener<String, ObjectRecord<String, String>> {
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onMessage(ObjectRecord<String, String> message) {
        String stream = message.getStream();
        String messageId = message.getId().toString();
        String messageBody = message.getValue();

        log.info("MO received: ");
        log.info("stream: {}", stream);
        log.info("msgId: {}", messageId);
        log.info("messageBody: {}", messageBody);
        stringRedisTemplate.opsForStream().acknowledge("group-0", message);
    }
}
