package com.example.taskapi.repository;

import com.example.taskapi.domain.TagReadResponseDto;
import com.example.taskapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    List<TagReadResponseDto> findAllByProject_ProjectId(Integer projectId);

    Optional<Tag> findTagByProject_projectIdAndTagId(Integer projectId, Integer tagId);
}
