package org.a2cd.boot.component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author a2cd
 * @since 2024-01-19
 */

@Slf4j
@Component
@AllArgsConstructor
public class BlockingQueueConsumer implements ApplicationRunner {
    private RedissonClient redissonClient;

    /**
     * 从队列尾部阻塞读取消息，若没有消息，线程就会阻塞等待新消息插入，防止 CPU 空转
     */
    @Override
    public void run(ApplicationArguments args) {
        var tp = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), r -> {
            var t = new Thread(r);
            t.setDaemon(true);
            t.setName("blocking-queue-thread");
            return t;
        });
        tp.execute(() -> {
            var blockingDeque = redissonClient.getBlockingDeque("blocking-queue");
            while (true) {
                try {
                    var msg = blockingDeque.takeLast();
                    log.info("从 blocking-queue 中读取到消息：{}", msg);
                } catch (Exception e) {
                    log.error("err: ", e);
                }
            }
        });

    }

}
