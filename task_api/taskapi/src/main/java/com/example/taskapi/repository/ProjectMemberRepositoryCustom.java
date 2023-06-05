package com.example.taskapi.repository;

import com.example.taskapi.domain.MemberDto;
import com.example.taskapi.domain.ProjectNameDto;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ProjectMemberRepositoryCustom {
    List<MemberDto> findMembersByProjectId(Integer projectId);

    List<ProjectNameDto> findProjectNamesByMemberId(String memberId);
}
