package org.hse.nnbuilder.queue;

import java.time.OffsetDateTime;
import javax.transaction.Transactional;
import org.hse.nnbuilder.PythonPackageLauncher.PythonPackageInstaller;
import org.hse.nnbuilder.services.Tasksqueue.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("TaskExecutor")
public class TaskExecutor {

    @Autowired
    private TaskQueuedStorage taskQueuedStorage;

    @Autowired
    private PythonPackageInstaller pythonPackageInstaller;

    public TaskExecutor() {}

    public void processTask() {
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
            exec.waitFor();
            //

            setFinishTaskTime(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void setFinishTaskTime(TaskQueued task) {
        task.setFinishTaskTime(OffsetDateTime.now());
        if (task.getTaskStatus() != TaskStatus.Done) {
            task.setTaskStatus(TaskStatus.Failed);
        }
        taskQueuedStorage.saveTaskToRepository(task);
    }
}
