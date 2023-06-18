package com.example.taskapi.repository;

import com.example.taskapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    void deleteAllByTask_TaskId(Integer taskId);

    List<Comment> findAllByTask_TaskId(Integer taskId);
}
