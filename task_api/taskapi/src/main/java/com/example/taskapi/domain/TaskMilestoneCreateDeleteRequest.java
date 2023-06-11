package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class TaskMilestoneCreateDeleteRequest {
    @NotNull
    @Positive
    private Integer taskId;
    @NotNull
    @Positive
    private Integer milestoneId;
}
