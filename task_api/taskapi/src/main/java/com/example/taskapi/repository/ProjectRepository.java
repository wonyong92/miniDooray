package com.example.taskapi.repository;

import com.example.taskapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
