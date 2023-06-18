package com.example.taskapi.service;

import com.example.taskapi.domain.*;

public interface ProjectService {

    ProjectCreateResponseDto createProject(ProjectCreateRequest projectCreateRequest);
    ProjectUpdateResponseDto updateProject(ProjectUpdateRequest projectUpdateRequest, Integer projectId);
    ProjectDeleteResponseDto deleteProject(Integer projectId);
    ProjectDetailReadResponseDto readProject(Integer projectId);
    ProjectMemberReadResponseDto readProjectMembers(Integer projectId);
    ProjNameForMemReadResponseDto readProjNamesForMem(String memberId);
}
