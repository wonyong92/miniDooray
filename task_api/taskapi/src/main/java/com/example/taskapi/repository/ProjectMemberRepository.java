package com.example.taskapi.repository;

import com.example.taskapi.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMember.Pk>, ProjectMemberRepositoryCustom {

    Integer deleteAllByPk_ProjectId(Integer projectId);
}
