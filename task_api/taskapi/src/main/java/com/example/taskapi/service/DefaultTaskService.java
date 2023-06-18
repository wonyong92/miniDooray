package com.example.taskapi.service;

import com.example.taskapi.domain.*;
import com.example.taskapi.entity.*;
import com.example.taskapi.exception.NotFoundException;
import com.example.taskapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultTaskService implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskTagRepository taskTagRepository;
    private final MemberRepository memberRepository;
    private final TaskMilestoneRepository taskMilestoneRepository;
    private final ProjectRepository projectRepository;
    private final CommentRepository commentRepository;

    @Override
    public TaskDetailReadResponseDto readTask(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task not found, taskId = " + taskId));
        List<TaskTag> taskTags = taskTagRepository.findAllTagByTask_TaskId(task.getTaskId());
        List<TagReadResponseDto> tags = taskTags
                .stream()
                .map(TaskTag::getTag)
                .map(tag -> new TagReadResponseDto(tag.getTagId(), tag.getName()))
                .collect(Collectors.toList());
        Optional<TaskMilestone> milestone =
                taskMilestoneRepository.findByTask_TaskId(task.getTaskId());
        List<CommentReadResponseDto> comments = commentRepository.findAllByTask_TaskId(taskId)
                .stream()
                .map(comment -> new CommentReadResponseDto(comment.getCommentId(),
                        comment.getMember().getMemberId(), comment.getContent(),
                        comment.getModifiedAt().toString()))
                .collect(Collectors.toList());
        if (milestone.isPresent()) {
            TaskMilestone present = milestone.get();
            return new TaskDetailReadResponseDto(task.getTaskId(), task.getTitle(),
                    task.getContent(), task.getCreatedAt(), task.getModifiedAt(), tags, new MilestoneReadResponseDto(present.getMilestone().getMilestoneId(),
                    present.getMilestone().getName(), present.getMilestone().getStartAt(), present.getMilestone().getEndAt())
            ,comments);
        }
        return new TaskDetailReadResponseDto(task.getTaskId(), task.getTitle(), task.getContent(),
                task.getCreatedAt(), task.getModifiedAt(), tags, null, comments);
    }

    @Override
    @Transactional
    public TaskCreateResponseDto createTask(TaskCreateRequest taskCreateRequest) {
        Member member = memberRepository.findById(taskCreateRequest.getWriterId())
                .orElseThrow(() -> new NotFoundException("user not found, userId = " + taskCreateRequest.getWriterId()));
        Project project = projectRepository.findById(taskCreateRequest.getProjectId())
                .orElseThrow(() -> new NotFoundException("project not found , projectId = " + taskCreateRequest.getProjectId()));

        Task task = new Task(taskCreateRequest.getTitle(),
                taskCreateRequest.getContent(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                member,
                project
        );
        Task save = taskRepository.save(task);
        return new TaskCreateResponseDto(save.getTaskId(), save.getProject().getProjectId());
    }

    @Override
    @Transactional
    public TaskUpdateResponseDto updateTask(TaskUpdateRequest taskUpdateRequest, Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task not found  taskId = "
                        + taskId));

        task.updateTaskWithDto(taskUpdateRequest);
        return new TaskUpdateResponseDto(task.getProject().getProjectId(), task.getTaskId());
    }

    @Override
    @Transactional
    public TaskDeleteResponseDto deleteTask(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task not found, taskId = " + taskId));
        taskTagRepository.deleteAllByTask_TaskId(taskId);
        taskMilestoneRepository.deleteAllByTask_TaskId(taskId);
        commentRepository.deleteAllByTask_TaskId(taskId);
        taskRepository.delete(task);
        return new TaskDeleteResponseDto(task.getTaskId());
    }

    @Override
    public TaskAuthReadResponseDto readAuthTask(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("task not found, taskId = " + taskId));
        return new TaskAuthReadResponseDto(task.getTaskId(), task.getProject().getProjectId());
    }
}
