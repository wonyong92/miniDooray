package com.example.taskapi.service;

import com.example.taskapi.domain.*;

import java.util.List;

public interface ProjectService {

    ProjectCreateResponseDto creatProject(ProjectCreateRequest projectCreateRequest);

    ProjectUpdateResponseDto updateProject(ProjectUpdateRequest projectUpdateRequest, Integer projectId);

    Integer deleteProject(Integer projectId);


    ProjectDetailDto findProjectDtoById(Integer projectId);

    List<MemberDto> findAllMembersById(Integer projectId);

    ProjNameForMemDto findProjNamesByMemberId(String memberId);
}
