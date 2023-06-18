package com.example.taskapi.repository;

import com.example.taskapi.domain.MemberReadResponseDto;
import com.example.taskapi.domain.ProjectNameReadResponseDto;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ProjectMemberRepositoryCustom {
    List<MemberReadResponseDto> findMembersByProjectId(Integer projectId);

    List<ProjectNameReadResponseDto> findProjectNamesByMemberId(String memberId);
}
