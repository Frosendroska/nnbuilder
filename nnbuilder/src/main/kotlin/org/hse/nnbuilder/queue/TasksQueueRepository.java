package org.hse.nnbuilder.queue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksQueueRepository extends JpaRepository<TaskQueued, Long> {
    @Query(value = "SELECT * FROM tasksqueue AS task WHERE task.taskStatus = HaveNotStarted ORDERED BY task.addTaskTime ASC LIMIT 1",
            nativeQuery = true)
    TaskQueued findOldestTaskInTable();
}
