package org.a2cd.boot.component;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.a2cd.boot.consts.RedisKey;
import org.redisson.api.RedissonClient;
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
    private RedissonClient redissonClient;

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
                var rlock = redissonClient.getLock("app-0");
                try {
                    // waitTime 未获取到锁等待多久再次获取
                    // leaseTime 预计需要获取锁多少时间，配置此项不会有watchdog续命
                    // time是waitTime
                    var acquired = rlock.tryLock( 30, TimeUnit.SECONDS);
                    if (!acquired) {
                        continue;
                    }
                    var s = stringRedisTemplate.opsForList().rightPop(RedisKey.BLOCK_LIST, Duration.ofSeconds(0));
                    log.info("val={}, thread={}", s, Thread.currentThread().getName());

                    Thread.sleep(30000);

                } catch (Exception e) {
                    log.error("err: ", e);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                } finally {
                    if(rlock.isHeldByCurrentThread()) {
                        rlock.unlock();
                    }
                }
            }
        });
    }

    @Override
    public void destroy() throws Exception {

    }
}
