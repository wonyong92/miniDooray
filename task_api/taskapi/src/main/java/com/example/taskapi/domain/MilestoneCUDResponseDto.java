package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MilestoneCUDResponseDto {
    private Integer projectId;
    private Integer milestoneId;
}
