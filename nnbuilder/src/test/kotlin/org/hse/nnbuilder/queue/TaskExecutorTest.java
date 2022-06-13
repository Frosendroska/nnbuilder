package org.hse.nnbuilder.queue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
public class TaskExecutorTest {

    @Autowired
    ScheduledTaskExecutor taskExecutor;

    @Test
    void callProcessTaskTest() {
        taskExecutor.processScheduledTask();
    }

    @Test
    void callTwoProcessTaskTest() {
        taskExecutor.processScheduledTask();
        taskExecutor.processScheduledTask();
    }
}
