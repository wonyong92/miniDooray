package com.example.taskapi.service;

import com.example.taskapi.domain.*;

public interface TagService {

    TagReadResponseDto readTag(Integer tagId);
    TagCUDResponseDto createTag(TagCreateRequest tagCreateRequest);
    TagCUDResponseDto updateTag(TagUpdateRequest tagUpdateRequest, Integer tagId);
    TagCUDResponseDto deleteTag(Integer tagId);
    TagAuthReadResponseDto readAuthTag(Integer tagId);
}
