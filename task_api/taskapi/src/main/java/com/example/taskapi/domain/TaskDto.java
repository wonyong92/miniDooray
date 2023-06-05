package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class TaskDto {
    private Integer taskId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String writerId;

    private List<CommentDto> comments;
    public TaskDto(Integer taskId, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, String writerId) {
        this.taskId = taskId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.writerId = writerId;
    }
}
