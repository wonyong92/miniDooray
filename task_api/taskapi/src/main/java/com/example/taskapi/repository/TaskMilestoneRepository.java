package com.example.taskapi.repository;

import com.example.taskapi.entity.TaskMilestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TaskMilestoneRepository extends JpaRepository<TaskMilestone, TaskMilestone.Pk> {

    Optional<TaskMilestone> findByTask_TaskId(Integer taskId);
    void deleteAllByTask_TaskId(Integer taskId);
    // Task 는 1개의 마일스톤만 가질수 있음. Task가 milestone 가지고 있는지 여부
    boolean existsByTask_TaskId(Integer taskId);
}
