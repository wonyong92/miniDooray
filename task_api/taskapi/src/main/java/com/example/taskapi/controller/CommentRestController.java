package com.example.taskapi.controller;

import com.example.taskapi.domain.*;
import com.example.taskapi.exception.ValidationFailedException;
import com.example.taskapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentRestController {
    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public CommentReadResponseDto readComment(@PathVariable(name = "commentId") Integer commentId) {
        return commentService.readComment(commentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentCreateUpdateResponseDto createComment(@RequestBody @Validated CommentCreateRequest commentCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return commentService.createComment(commentCreateRequest);
    }

    @PutMapping("/{commentId}")
    public CommentCreateUpdateResponseDto updateComment(@PathVariable(name = "commentId") Integer commentId, @RequestBody @Validated CommentUpdateRequest commentUpdateRequest
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return commentService.updateComment(commentUpdateRequest, commentId);
    }

    @DeleteMapping("/{commentId}")
    public CommentDeleteResponseDto deleteComment(@PathVariable(name = "commentId") Integer commentId) {
        return commentService.deleteComment(commentId);
    }

    @GetMapping("/auth/{commentId}")
    public CommentAuthReadResponseDto readAuthComment(@PathVariable(name = "commentId") Integer commentId) {
        return commentService.readAuthComment(commentId);
    }
}
