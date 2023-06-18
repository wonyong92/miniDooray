package com.example.taskapi.controller;

import com.example.taskapi.domain.*;
import com.example.taskapi.exception.ValidationFailedException;
import com.example.taskapi.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagRestController {

    private final TagService tagService;

    @GetMapping("/{tagId}")
    public TagReadResponseDto readTag(@PathVariable(name = "tagId") Integer tagId) {
        return tagService.readTag(tagId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagCUDResponseDto createTag(@RequestBody @Validated TagCreateRequest tagCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return tagService.createTag(tagCreateRequest);
    }

    @PutMapping("/{tagId}")
    public TagCUDResponseDto updateTag(@RequestBody @Validated TagUpdateRequest tagUpdateRequest, BindingResult bindingResult
            , @PathVariable(name = "tagId") Integer tagId) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return tagService.updateTag(tagUpdateRequest, tagId);

    }

    @DeleteMapping("/{tagId}")
    public TagCUDResponseDto deleteTag(@PathVariable(name = "tagId") Integer tagId) {
        return tagService.deleteTag(tagId);
    }

    @GetMapping("/auth/{tagId}")
    public TagAuthReadResponseDto readAuthTag(@PathVariable(name = "tagId") Integer tagId) {
        return tagService.readAuthTag(tagId);
    }

}
