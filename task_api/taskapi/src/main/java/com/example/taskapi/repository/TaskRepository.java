package com.example.taskapi.repository;

import com.example.taskapi.domain.TaskReadResponseDto;
import com.example.taskapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByProject_ProjectId(Integer projectId);
}
