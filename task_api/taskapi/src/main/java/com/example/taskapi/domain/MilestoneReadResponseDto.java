package com.example.taskapi.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MilestoneReadResponseDto {

    private Integer milestoneId;
    private String name;
    private LocalDate startAt;
    private LocalDate endAt;
}
