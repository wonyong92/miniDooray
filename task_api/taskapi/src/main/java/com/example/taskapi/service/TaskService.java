package com.example.taskapi.service;

import com.example.taskapi.domain.*;

public interface TaskService {

    TaskDetailReadResponseDto readTask(Integer taskId);
    TaskCreateResponseDto createTask(TaskCreateRequest taskCreateRequest);
    TaskUpdateResponseDto updateTask(TaskUpdateRequest taskUpdateRequest, Integer taskId);
    TaskDeleteResponseDto deleteTask(Integer taskId);
    TaskAuthReadResponseDto readAuthTask(Integer taskId);
}
