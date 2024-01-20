package org.a2cd.boot.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author a2cd
 * @since 2024-01-20
 */

// @Configuration
public class ThreadPoolConfig {

    @Primary
    @Bean
    public ThreadPoolTaskExecutor mainThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("main-thread-");
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setQueueCapacity(50);
        // Only when the buffer queue was full would the number of the core threads be exceeded
        taskExecutor.setMaxPoolSize(20);

        // Other threads time to live
        taskExecutor.setKeepAliveSeconds(120);

        // When the queue is full and the tasks exceed maxPoolSize, the rejection policy will be executed
        // AbortPolicy - abort the task and threw out RejectedExecutionException
        // DiscardPolicy - discard the task but not threw Exception
        // DiscardOldestPolicy - discard the oldest task
        // CallerRunsPolicy - polling add tasks
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }

    // @Bean
    // public ThreadPoolTaskExecutor minAsyncPoolTaskExecutor() {
    //     ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    //     taskExecutor.setThreadNamePrefix("minAsync-");
    //     taskExecutor.setCorePoolSize(5);
    //     taskExecutor.setQueueCapacity(20);
    //     // Only when the buffer queue was full would the number of the core threads be exceeded
    //     taskExecutor.setMaxPoolSize(20);
    //
    //     // Other threads time to live
    //     taskExecutor.setKeepAliveSeconds(200);
    //
    //     // When the queue is full and the tasks exceed maxPoolSize, the rejection policy will be executed
    //     // AbortPolicy - abort the task and threw out RejectedExecutionException
    //     // DiscardPolicy - discard the task but not threw Exception
    //     // DiscardOldestPolicy - discard the oldest task
    //     // CallerRunsPolicy - polling add tasks
    //     taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    //     taskExecutor.initialize();
    //     return taskExecutor;
    // }
}
