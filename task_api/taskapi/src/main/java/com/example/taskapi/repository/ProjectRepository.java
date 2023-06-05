package com.example.taskapi.repository;

import com.example.taskapi.domain.ProjectDto;
import com.example.taskapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Optional<ProjectDto> findProjectDtoByProjectId(Integer projectId);
}
