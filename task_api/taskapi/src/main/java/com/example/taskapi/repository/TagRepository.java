package com.example.taskapi.repository;

import com.example.taskapi.domain.TagDto;
import com.example.taskapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    List<TagDto> findAllByProject_ProjectId(Integer projectId);

}
