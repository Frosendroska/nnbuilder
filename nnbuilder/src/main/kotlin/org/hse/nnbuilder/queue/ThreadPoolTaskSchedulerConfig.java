package org.hse.nnbuilder.queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@Profile("!test")
public class ThreadPoolTaskSchedulerConfig {

    private static final int TASK_RUN_INTERVAL_MS = 2000;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("TaskScheduler");

        threadPoolTaskScheduler.initialize();
        threadPoolTaskScheduler.scheduleAtFixedRate(
                () -> new TaskExecutor().processTask(),
                TASK_RUN_INTERVAL_MS
        );

        return threadPoolTaskScheduler;
    }
}
