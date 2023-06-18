package com.example.taskapi.service;

import com.example.taskapi.domain.TaskTagCreateDeleteResponseDto;
import com.example.taskapi.domain.TaskTagCreateDeleteRequest;
import com.example.taskapi.entity.Tag;
import com.example.taskapi.entity.Task;
import com.example.taskapi.entity.TaskTag;
import com.example.taskapi.exception.NotFoundException;
import com.example.taskapi.repository.TagRepository;
import com.example.taskapi.repository.TaskRepository;
import com.example.taskapi.repository.TaskTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class DefaultTaskTagService implements TaskTagService{

    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final TaskTagRepository taskTagRepository;

    @Override
    public TaskTagCreateDeleteResponseDto createTaskTag(TaskTagCreateDeleteRequest taskTagCreateDeleteRequest) {
        Task task = taskRepository.findById(taskTagCreateDeleteRequest.getTaskId())
                .orElseThrow(() -> new NotFoundException("task not found, taskId = " + taskTagCreateDeleteRequest.getTaskId()));
        List<Integer> tagIds = taskTagCreateDeleteRequest.getTagIds();
        for (Integer tagId : tagIds) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new NotFoundException("tag not found, tagId = " + tagId));
            taskTagRepository.save(new TaskTag(new TaskTag.Pk(task.getTaskId(), tag.getTagId()), tag, task));
        }
        return new TaskTagCreateDeleteResponseDto(task.getTaskId(), tagIds);
    }

    @Override
    public TaskTagCreateDeleteResponseDto deleteTaskTag(TaskTagCreateDeleteRequest taskTagCreateDeleteRequest) {
        Task task = taskRepository.findById(taskTagCreateDeleteRequest.getTaskId())
                .orElseThrow(() -> new NotFoundException("task not found, taskId = " + taskTagCreateDeleteRequest.getTaskId()));
        List<Integer> tagIds = taskTagCreateDeleteRequest.getTagIds();
        for (Integer tagId : tagIds) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new NotFoundException("tag not found, tagId = " + tagId));
            TaskTag.Pk pk = new TaskTag.Pk(task.getTaskId(), tag.getTagId());
            TaskTag taskTag = taskTagRepository.findById(pk)
                    .orElseThrow(() -> new NotFoundException("taskTag not found, taskId = " + pk.getTaskId() + " tagId = " + pk.getTagId()));
            taskTagRepository.delete(taskTag);
        }
        return new TaskTagCreateDeleteResponseDto(task.getTaskId(), tagIds);
    }
}
