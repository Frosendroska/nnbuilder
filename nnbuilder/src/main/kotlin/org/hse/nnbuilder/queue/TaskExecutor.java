package org.hse.nnbuilder.queue;

import java.time.OffsetDateTime;
import org.hse.nnbuilder.PythonPackageLauncher.PythonPackageInstaller;
import org.hse.nnbuilder.services.Tasksqueue.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskExecutor {

    @Autowired
    private TaskQueuedStorage taskQueuedStorage;

    @Autowired
    private PythonPackageInstaller pythonPackageInstaller;

    public void processTask() {
        try {
            TaskQueued task = taskQueuedStorage.getTaskToExecute();
            if (task == null) {
                return;
            }
            Process exec = Runtime.getRuntime().exec(
                    new String[] {
                            pythonPackageInstaller.getPackageExecutablePath(), task.getTaskId().toString()
                    }
            );

            // Wait till the end of the learning process
            exec.waitFor();
            //

            task.setFinishTaskTime(OffsetDateTime.now());
            if (task.getTaskStatus() != TaskStatus.Done) {
                task.setTaskStatus(TaskStatus.Failed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
