package com.example.taskapi.service;

import com.example.taskapi.domain.MemberDto;
import com.example.taskapi.domain.ProjectDto;

import java.util.List;

public interface ProjectService {

    List<ProjectDto> findAllProjectDto();

    ProjectDto findProjectDtoById(Integer projectId);

    List<MemberDto> findAllMembersById(Integer projectId);
}
