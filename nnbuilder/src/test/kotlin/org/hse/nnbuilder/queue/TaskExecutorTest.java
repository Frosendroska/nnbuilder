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
    TaskExecutor taskExecutor;

    @Test
    void callProcessTaskTest() {
        taskExecutor.processTask();
    }

    @Test
    void callTwoProcessTaskTest() {
        taskExecutor.processTask();
        taskExecutor.processTask();
    }
}
