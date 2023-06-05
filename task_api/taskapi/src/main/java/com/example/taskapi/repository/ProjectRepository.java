package com.example.taskapi.repository;

import com.example.taskapi.domain.ProjectDto;
import com.example.taskapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query("SELECT new com.example.taskapi.domain.ProjectDto(p.projectId, p.name, p.status, p.member.memberId) FROM Project p where p.projectId = ?1")
    Optional<ProjectDto> findProjectDtoByProjectId(Integer projectId);

    @Query("SELECT new com.example.taskapi.domain.ProjectDto (p.projectId, p.name, p.status, p.member.memberId) FROM Project p")
    List<ProjectDto> findAllProjectDto();
}
