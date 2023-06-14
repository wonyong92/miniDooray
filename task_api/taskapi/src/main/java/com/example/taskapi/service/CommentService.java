package com.example.taskapi.service;

import com.example.taskapi.domain.*;

public interface CommentService {
    CommentReadResponseDto readComment(Integer commentId);
    CommentCreateUpdateResponseDto createComment(CommentCreateRequest commentCreateRequest);
    CommentCreateUpdateResponseDto updateComment(CommentUpdateRequest commentUpdateRequest, Integer commentId);
    CommentDeleteResponseDto deleteComment(Integer commentId);
    CommentAuthReadResponseDto readAuthComment(Integer commentId);
}

