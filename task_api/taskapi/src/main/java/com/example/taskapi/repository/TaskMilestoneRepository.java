package com.example.taskapi.repository;

import com.example.taskapi.entity.TaskMilestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TaskMilestoneRepository extends JpaRepository<TaskMilestone, TaskMilestone.Pk> {

    Optional<TaskMilestone> findByTask_TaskId(Integer taskId);
    void deleteAllByTask_TaskId(Integer taskId);
}
