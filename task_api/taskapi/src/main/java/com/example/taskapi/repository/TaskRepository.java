package com.example.taskapi.repository;

import com.example.taskapi.domain.TaskDto;
import com.example.taskapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT new com.example.taskapi.domain.TaskDto(t.taskId, t.title, t.content, t.createdAt, t.modifiedAt, t.member.memberId) from Task t where t.project.projectId = ?1")
    List<TaskDto> findAllTaskDtoByProjectId(Integer projectId);
}
