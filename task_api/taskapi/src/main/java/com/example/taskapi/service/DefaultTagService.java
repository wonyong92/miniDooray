package com.example.taskapi.service;

import com.example.taskapi.domain.*;
import com.example.taskapi.entity.Project;
import com.example.taskapi.entity.Tag;
import com.example.taskapi.exception.NotFoundException;
import com.example.taskapi.repository.ProjectRepository;
import com.example.taskapi.repository.TagRepository;
import com.example.taskapi.repository.TaskTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultTagService implements TagService {
    private final TagRepository tagRepository;
    private final TaskTagRepository taskTagRepository;
    private final ProjectRepository projectRepository;

    @Override
    public TagReadResponseDto readTag(Integer tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundException("tag not found, tagId = " + tagId));
        return new TagReadResponseDto(tag.getTagId(), tag.getName());
    }

    @Override
    @Transactional
    public TagCUDResponseDto createTag(TagCreateRequest tagCreateRequest) {
        Project project = projectRepository.findById(tagCreateRequest.getProjectId())
                .orElseThrow(() -> new NotFoundException("project not found, projectId = " + tagCreateRequest.getProjectId()));
        Tag tag = new Tag(tagCreateRequest.getName(), project);
        Tag save = tagRepository.save(tag);
        return new TagCUDResponseDto(save.getTagId(), save.getProject().getProjectId());
    }

    @Override
    @Transactional
    public TagCUDResponseDto updateTag(TagUpdateRequest tagUpdateRequest, Integer tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundException("tag not found, tagId = " + tagId));
        tag.updateTagWithDto(tagUpdateRequest);
        return new TagCUDResponseDto(tag.getTagId(), tag.getProject().getProjectId());
    }

    @Override
    @Transactional
    public TagCUDResponseDto deleteTag(Integer tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundException("tag not found, tagId = " + tagId));
        taskTagRepository.deleteAllByTag_TagId(tag.getTagId());
        tagRepository.delete(tag);
        return new TagCUDResponseDto(tag.getTagId(), tag.getProject().getProjectId());
    }

    @Override
    public TagAuthReadResponseDto readAuthTag(Integer tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundException("tag not found, tagId = " + tagId));
        return new TagAuthReadResponseDto(tag.getTagId(), tag.getProject().getProjectId());
    }
}
