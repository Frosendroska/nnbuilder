package org.hse.nnbuilder.queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * This class helps to execute tasks per TASK_RUN_INTERVAL_MS
 * and allows to have in thread pool only TASK_POOL_SIZE
 */
@Configuration
@Profile("!test")
public class ThreadPoolTaskSchedulerConfig {

    private static final int TASK_RUN_INTERVAL_MS = 2000;
    private static final int TASK_POOL_SIZE = 5;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(TASK_POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("TaskScheduler");

        threadPoolTaskScheduler.initialize();
        threadPoolTaskScheduler.scheduleAtFixedRate(
                () -> new TaskExecutor().processTask(),
                TASK_RUN_INTERVAL_MS
        );

        return threadPoolTaskScheduler;
    }
}
