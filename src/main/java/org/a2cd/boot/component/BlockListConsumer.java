package org.a2cd.boot.component;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.*;

/**
 * @author a2cd
 * @since 2024-01-19
 */

@Slf4j
@Component
@AllArgsConstructor
public class BlockListConsumer implements InitializingBean, DisposableBean {

    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void afterPropertiesSet() {
        var tp = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), r -> {
            var t = new Thread(r);
            t.setDaemon(true);
            t.setName("block-list-thread");
            return t;
        });
        tp.execute(() -> {
            while (true) {
                try {
                    var s = stringRedisTemplate.opsForList().rightPop("block-list", Duration.ofSeconds(0));
                    log.info("val={}, thread={}", s, Thread.currentThread().getName());
                } catch (Exception e) {
                    log.error("err: ", e);
                }
            }
        });
    }

    @Override
    public void destroy() throws Exception {

    }
}
