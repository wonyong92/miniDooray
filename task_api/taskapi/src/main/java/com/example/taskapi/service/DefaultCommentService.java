package com.example.taskapi.service;

import com.example.taskapi.domain.*;
import com.example.taskapi.entity.Comment;
import com.example.taskapi.entity.Member;
import com.example.taskapi.entity.Task;
import com.example.taskapi.exception.NotFoundException;
import com.example.taskapi.repository.CommentRepository;
import com.example.taskapi.repository.MemberRepository;
import com.example.taskapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultCommentService implements CommentService{
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    @Override
    public CommentReadResponseDto readComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("comment not found, commentId = " + commentId));
        return new CommentReadResponseDto(comment.getCommentId(), comment.getMember().getMemberId(), comment.getContent(), comment.getModifiedAt().toString());
    }

    @Override
    @Transactional
    public CommentCreateUpdateResponseDto createComment(CommentCreateRequest commentCreateRequest) {
        Task task = taskRepository.findById(commentCreateRequest.getTaskId())
                .orElseThrow(() -> new NotFoundException("task not found, taskId = " + commentCreateRequest.getTaskId()));
        Member member = memberRepository.findById(commentCreateRequest.getWriterId())
                .orElseThrow(() -> new NotFoundException("member not found, memberId = " + commentCreateRequest.getWriterId()));
        Comment comment = new Comment(commentCreateRequest.getContent(), LocalDateTime.now(), LocalDateTime.now(), member, task);
        Comment save = commentRepository.save(comment);
        return new CommentCreateUpdateResponseDto(save.getCommentId(), task.getTaskId());
    }

    @Override
    @Transactional
    public CommentCreateUpdateResponseDto updateComment(CommentUpdateRequest commentUpdateRequest, Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("comment not found, commentId = " + commentId));
        comment.updateCommentWithDto(commentUpdateRequest);
        return new CommentCreateUpdateResponseDto(comment.getCommentId(), comment.getTask().getTaskId());
    }

    @Override
    @Transactional
    public CommentDeleteResponseDto deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("comment not found, commentId = " + commentId));
        commentRepository.delete(comment);
        return new CommentDeleteResponseDto(comment.getCommentId());
    }

    @Override
    public CommentAuthReadResponseDto readAuthComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("comment not found, commentId = " + commentId));
        return new CommentAuthReadResponseDto(comment.getCommentId(), comment.getMember().getMemberId(), comment.getTask().getProject().getProjectId());
    }
}
