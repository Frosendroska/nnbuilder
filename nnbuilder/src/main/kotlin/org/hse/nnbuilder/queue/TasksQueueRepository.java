package org.hse.nnbuilder.queue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksQueueRepository extends JpaRepository<TaskQueued, Long> {}
