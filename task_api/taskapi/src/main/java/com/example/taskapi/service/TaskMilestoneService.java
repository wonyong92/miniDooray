package com.example.taskapi.service;

import com.example.taskapi.domain.TaskMilestoneCreateDeleteRequest;
import com.example.taskapi.domain.TaskMilestoneCreateDeleteResponseDto;

public interface TaskMilestoneService {

    TaskMilestoneCreateDeleteResponseDto createTaskMilestone(TaskMilestoneCreateDeleteRequest taskMilestoneCreateDeleteRequest);
    TaskMilestoneCreateDeleteResponseDto deleteTaskMilestone(TaskMilestoneCreateDeleteRequest taskMilestoneCreateDeleteRequest);
}
