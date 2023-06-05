package com.example.taskapi.repository;

import com.example.taskapi.domain.MilestoneDto;
import com.example.taskapi.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Integer> {

    @Query("SELECT new com.example.taskapi.domain.MilestoneDto(m.milestoneId, m.name, m.startAt, m.endAt) FROM Milestone m where m.project.projectId =:projectId")
    List<MilestoneDto> findAllMilestoneDtoByProjectId(@Param("projectId") Integer projectId);


}
