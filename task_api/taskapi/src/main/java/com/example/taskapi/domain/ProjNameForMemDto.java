package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProjNameForMemDto {
    String memberId;
    List<ProjectNameDto> projectNames;
}
