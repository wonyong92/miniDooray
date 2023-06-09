package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
public class MilestoneReadResponseDto {

    private Integer milestoneId;
    private String name;
    private LocalDate startAt;
    private LocalDate endAt;
}
