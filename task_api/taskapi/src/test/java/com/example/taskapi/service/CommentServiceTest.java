package com.example.taskapi.service;

import com.example.taskapi.domain.*;
import com.example.taskapi.entity.Comment;
import com.example.taskapi.entity.Member;
import com.example.taskapi.entity.Project;
import com.example.taskapi.entity.Task;
import com.example.taskapi.exception.NotFoundException;
import com.example.taskapi.repository.CommentRepository;
import com.example.taskapi.repository.MemberRepository;
import com.example.taskapi.repository.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(value = MockitoExtension.class)
class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private DefaultCommentService commentService;

    @Test
    void readComment() {
        Comment comment = new Comment();
        ReflectionTestUtils.setField(comment, "commentId", 1);
        ReflectionTestUtils.setField(comment, "content", "test");
        ReflectionTestUtils.setField(comment, "modifiedAt", LocalDateTime.now());
        ReflectionTestUtils.setField(comment, "member", mock(Member.class));

        when(commentRepository.findById(any(Integer.class)))
                .thenReturn(Optional.of(comment));
        CommentReadResponseDto actual = commentService.readComment(1);
        verify(commentRepository, atMostOnce()).findById(any(Integer.class));
        Assertions.assertThat(actual.getCommentId()).isEqualTo(comment.getCommentId());
    }

    @Test
    void readNotFoundComment() {
        when(commentRepository.findById(any(Integer.class)))
                .thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> commentService.readComment(1))
                .isInstanceOf(NotFoundException.class);
        verify(commentRepository, atMostOnce()).findById(any(Integer.class));
    }

    @Test
    void createComment() {
        CommentCreateRequest testRequest = new CommentCreateRequest(1, "tester", "testContent");
        Task task = new Task();
        Comment comment = new Comment();
        ReflectionTestUtils.setField(comment, "commentId", 1);
        ReflectionTestUtils.setField(task, "taskId", 1);
        when(taskRepository.findById(anyInt()))
                .thenReturn(Optional.of(task));
        when(memberRepository.findById(anyString()))
                .thenReturn(Optional.of(mock(Member.class)));
        when(commentRepository.save(any(Comment.class)))
                .thenReturn(comment);
        CommentCreateUpdateResponseDto actual = commentService.createComment(testRequest);
        Assertions.assertThat(actual.getCommentId()).isEqualTo(comment.getCommentId());
        Assertions.assertThat(actual.getTaskId()).isEqualTo(task.getTaskId());

    }

    @Test
    void createNotFoundTaskIdComment() {
        when(taskRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        CommentCreateRequest invalidRequest = new CommentCreateRequest(11221, "nikki", "..");
        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> commentService.createComment(invalidRequest));

    }

    @Test
    void createNotFoundMemberIdComment() {
        Task task = new Task();
        ReflectionTestUtils.setField(task, "taskId", 1);
        when(taskRepository.findById(anyInt()))
                .thenReturn(Optional.of(task));
        when(memberRepository.findById(anyString()))
                .thenReturn(Optional.empty());
        CommentCreateRequest invalidRequest = new CommentCreateRequest(1, "XXX", "..");
        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> commentService.createComment(invalidRequest));

    }

    @Test
    void updateComment() {
        Comment comment = new Comment();
        Task task = new Task();
        CommentUpdateRequest testRequest = new CommentUpdateRequest("testContent");
        ReflectionTestUtils.setField(task, "taskId", 1);
        ReflectionTestUtils.setField(comment, "commentId", 1);
        ReflectionTestUtils.setField(comment, "task", task);
        when(commentRepository.findById(any(Integer.class)))
                .thenReturn(Optional.of(comment));
        CommentCreateUpdateResponseDto actual = commentService.updateComment(testRequest, 1);
        Assertions.assertThat(actual.getCommentId()).isEqualTo(comment.getCommentId());
        Assertions.assertThat(actual.getTaskId()).isEqualTo(comment.getTask().getTaskId());
    }

    @Test
    void updateNotFoundComment() {

        when(commentRepository.findById(any(Integer.class)))
                .thenReturn(Optional.empty());
        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> commentService.updateComment(mock(CommentUpdateRequest.class), 12212));
        verify(commentRepository, atMostOnce()).findById(anyInt());
    }

    @Test
    void deleteComment() {
        Comment comment = new Comment();
        ReflectionTestUtils.setField(comment, "commentId", 1);
        when(commentRepository.findById(any(Integer.class)))
                .thenReturn(Optional.of(comment));
        doNothing()
                .when(commentRepository).delete(any(Comment.class));
        CommentDeleteResponseDto actual = commentService.deleteComment(1);
        Assertions.assertThat(actual.getCommentId()).isEqualTo(comment.getCommentId());
    }

    @Test
    void deleteNotFoundComment() {

        when(commentRepository.findById(any(Integer.class)))
                .thenReturn(Optional.empty());
        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> commentService.deleteComment(121212));

    }

    @Test
    void readAuthComment() {
        Comment comment = new Comment();
        Member member = new Member();
        Task task = new Task();
        Project project = new Project();
        ReflectionTestUtils.setField(project, "projectId", 1);
        ReflectionTestUtils.setField(task, "project", project);
        ReflectionTestUtils.setField(member, "memberId", "tester");
        ReflectionTestUtils.setField(comment, "commentId", 1);
        ReflectionTestUtils.setField(comment, "member", member);
        ReflectionTestUtils.setField(comment, "task", task);
        when(commentRepository.findById(anyInt()))
                .thenReturn(Optional.of(comment));
        CommentAuthReadResponseDto actual = commentService.readAuthComment(1);
        Assertions.assertThat(actual.getCommentId()).isEqualTo(comment.getCommentId());
        Assertions.assertThat(actual.getWriterId()).isEqualTo(member.getMemberId());
        Assertions.assertThat(actual.getProjectId()).isEqualTo(task.getProject().getProjectId());
    }

    @Test
    void readAuthNotFoundComment() {
        when(commentRepository.findById(any()))
                .thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> commentService.readAuthComment(1111))
                .isInstanceOf(NotFoundException.class);
    }
}