package com.nhn.academy.minidooray.gateway.domain.task.request.register;

import lombok.Data;

@Data
public class CommentCreateDto {

  private Integer taskId;

  private String writerId;

  private String content;
}
