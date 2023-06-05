package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CommentDto {

    private Integer commentId;
    private String content;
    private String writerId;

}
