package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TaskMilestoneCreateDeleteResponseDto {
    private Integer taskId;
    private Integer milestoneId;
}
