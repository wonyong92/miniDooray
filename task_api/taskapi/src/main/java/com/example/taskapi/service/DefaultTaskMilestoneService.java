package com.example.taskapi.service;

import com.example.taskapi.domain.TaskMilestoneCreateDeleteRequest;
import com.example.taskapi.domain.TaskMilestoneCreateDeleteResponseDto;
import com.example.taskapi.entity.Milestone;
import com.example.taskapi.entity.Task;
import com.example.taskapi.entity.TaskMilestone;
import com.example.taskapi.exception.AlreadyExistedException;
import com.example.taskapi.exception.NotFoundException;
import com.example.taskapi.repository.MilestoneRepository;
import com.example.taskapi.repository.TaskMilestoneRepository;
import com.example.taskapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultTaskMilestoneService implements TaskMilestoneService {
    private final TaskMilestoneRepository taskMilestoneRepository;
    private final TaskRepository taskRepository;
    private final MilestoneRepository milestoneRepository;

    @Override
    @Transactional
    public TaskMilestoneCreateDeleteResponseDto createTaskMilestone(TaskMilestoneCreateDeleteRequest taskMilestoneCreateDeleteRequest) {
        if (taskMilestoneRepository.existsByTask_TaskId(taskMilestoneCreateDeleteRequest.getTaskId())) {
            throw new AlreadyExistedException("taskMilestone has already exists, taskId = " + taskMilestoneCreateDeleteRequest.getTaskId());
        }
        Task task = taskRepository.findById(taskMilestoneCreateDeleteRequest.getTaskId())
                .orElseThrow(() -> new NotFoundException("task not found, taskId = " + taskMilestoneCreateDeleteRequest.getTaskId()));
        Milestone milestone = milestoneRepository.findById(taskMilestoneCreateDeleteRequest.getMilestoneId())
                .orElseThrow(() -> new NotFoundException("milestone not found, milestoneId = " + taskMilestoneCreateDeleteRequest.getMilestoneId()));
        TaskMilestone taskMilestone = new TaskMilestone(new TaskMilestone.Pk(task.getTaskId(), milestone.getMilestoneId()),
                task, milestone);
        TaskMilestone save = taskMilestoneRepository.save(taskMilestone);
        return new TaskMilestoneCreateDeleteResponseDto(save.getTask().getTaskId(), save.getMilestone().getMilestoneId());
    }

    @Override
    @Transactional
    public TaskMilestoneCreateDeleteResponseDto deleteTaskMilestone(TaskMilestoneCreateDeleteRequest taskMilestoneCreateDeleteRequest) {

        Task task = taskRepository.findById(taskMilestoneCreateDeleteRequest.getTaskId())
                .orElseThrow(() -> new NotFoundException("task not found, taskId = " + taskMilestoneCreateDeleteRequest.getTaskId()));
        Milestone milestone = milestoneRepository.findById(taskMilestoneCreateDeleteRequest.getMilestoneId())
                .orElseThrow(() -> new NotFoundException("milestone not found, milestoneId = " + taskMilestoneCreateDeleteRequest.getMilestoneId()));
        TaskMilestone.Pk pk = new TaskMilestone.Pk(task.getTaskId(), milestone.getMilestoneId());
        TaskMilestone taskMilestone = taskMilestoneRepository.findById(pk)
                .orElseThrow(() -> new NotFoundException("taskMilestone not found, taskId = " + pk.getTaskId() + " milestoneId = " + pk.getMilestoneId()));
        taskMilestoneRepository.delete(taskMilestone);
        return new TaskMilestoneCreateDeleteResponseDto(pk.getTaskId(), pk.getMilestoneId());

    }
}
