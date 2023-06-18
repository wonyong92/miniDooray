package com.example.taskapi.repository;

import com.example.taskapi.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Integer> {

    List<Milestone> findAllByProject_ProjectId(Integer projectId);

}
