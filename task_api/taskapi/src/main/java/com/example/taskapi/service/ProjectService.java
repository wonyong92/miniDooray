package com.example.taskapi.service;

import com.example.taskapi.domain.MemberDto;
import com.example.taskapi.domain.ProjNameForMemDto;
import com.example.taskapi.domain.ProjectDetailDto;

import java.util.List;

public interface ProjectService {

    List<ProjectDetailDto> findAllProjectDto();

    ProjectDetailDto findProjectDtoById(Integer projectId);

    List<MemberDto> findAllMembersById(Integer projectId);

    ProjNameForMemDto findProjNamesByMemberId(String memberId);
}
