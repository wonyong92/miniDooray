package com.example.taskapi.repository;

import com.example.taskapi.entity.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTag.Pk> {

    List<TaskTag> findAllTagByTask_TaskId(Integer taskId);

    void deleteAllByTask_TaskId(Integer taskId);

    void deleteAllByTag_TagId(Integer tagId);

}
