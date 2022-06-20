package org.hse.nnbuilder.queue;

import java.time.OffsetDateTime;
import javax.transaction.Transactional;
import org.hse.nnbuilder.PythonPackageLauncher.PythonPackageInstaller;
import org.hse.nnbuilder.services.Tasksqueue.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTaskExecutor {

    @Autowired
    private TaskQueuedStorage taskQueuedStorage;

    @Autowired
    private PythonPackageInstaller pythonPackageInstaller;

    public void processScheduledTask() {
        try {
            TaskQueued task = taskQueuedStorage.getTaskToExecute();
            if (task == null) {
                return;
            }
            Process exec = Runtime.getRuntime().exec(new String[] {
                pythonPackageInstaller.getPackageExecutablePath(),
                task.getTaskId().toString()
            });

            // Wait till the end of the learning process
            int resultCode = exec.waitFor();
            //

            saveModifiedTaskToStorage(task, resultCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void saveModifiedTaskToStorage(TaskQueued task, int resultCode) {
        task.setFinishTaskTime(OffsetDateTime.now());
        if (resultCode == 0) {
            task.setTaskStatus(TaskStatus.Done);
        } else {
            task.setTaskStatus(TaskStatus.Failed);
        }
        taskQueuedStorage.saveTaskToRepository(task);
    }
}
