package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MilestoneAuthReadResponseDto {
    private Integer milestoneId;
    private Integer projectId;
}
