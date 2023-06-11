package com.example.taskapi.service;

import com.example.taskapi.domain.TaskTagCreateDeleteResponseDto;
import com.example.taskapi.domain.TaskTagCreateDeleteRequest;


public interface TaskTagService {
    TaskTagCreateDeleteResponseDto createTaskTag(TaskTagCreateDeleteRequest taskTagCreateDeleteRequest);

    TaskTagCreateDeleteResponseDto deleteTaskTag(TaskTagCreateDeleteRequest taskTagCreateDeleteRequest);


}
