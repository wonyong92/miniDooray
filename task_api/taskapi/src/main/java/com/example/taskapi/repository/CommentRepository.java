package com.example.taskapi.repository;

import com.example.taskapi.domain.CommentDto;
import com.example.taskapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT new com.example.taskapi.domain.CommentDto(m.commentId, m.content, m.member.memberId) FROM Comment m where m.task.taskId = ?1")
    List<CommentDto> findAllCommentDtoByTaskId(Integer taskId);

}
