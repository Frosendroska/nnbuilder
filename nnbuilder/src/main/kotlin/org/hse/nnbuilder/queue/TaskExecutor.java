package org.hse.nnbuilder.queue;

import org.springframework.beans.factory.annotation.Autowired;

public class TaskExecutor {

    @Autowired
    private TaskQueuedStorage taskQueuedStorage;

    public void processTask() {
        try {
            TaskQueued task = taskQueuedStorage.getTaskToExecute();
            if (task == null) {
                return;
            }
            Process exec = Runtime.getRuntime().exec(new String[] {"python3", "main.py", task.getTaskId().toString()});
            exec.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
