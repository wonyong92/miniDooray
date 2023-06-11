package com.example.taskapi.domain;

import com.example.taskapi.entity.Milestone;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDetailReadResponseDto {
    private Integer taskId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<TagReadResponseDto> tags;
    private MilestoneReadResponseDto milestone;
    private List<CommentReadResponseDto> comments;

}
