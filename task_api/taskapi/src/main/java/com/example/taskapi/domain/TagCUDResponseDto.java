package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TagCUDResponseDto {
     private Integer tagId;
     private Integer projectId;
}
