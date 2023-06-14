package com.nhn.academy.minidooray.gateway.controller.task.rest.comment;

import com.nhn.academy.minidooray.gateway.domain.task.request.modify.CommentUpdateRequestDto;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.CommentCreateDto;
import com.nhn.academy.minidooray.gateway.service.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentRestController {

  final TaskService taskService;


  @GetMapping("/{commentId}")
  public ResponseEntity<String> readComment(@PathVariable(name = "commentId") Integer commentId) {
    return ResponseEntity.ok(taskService.readComment(commentId));
  }

  @PutMapping("/{commentId}")
  public ResponseEntity<String> updateComment(@RequestBody CommentUpdateRequestDto commentCreateUpdateRequest,
      @PathVariable(name = "commentId") String commentId) {
    taskService.updateComment(commentCreateUpdateRequest, commentId);
    return ResponseEntity.status(200).build();
  }

  @PostMapping
  public ResponseEntity<String> createComment(@RequestBody CommentCreateDto dto) {
    return ResponseEntity.status(200).body(taskService.registerComment(dto));
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable(name = "commentId") Integer commentId) {
    taskService.deleteComment(commentId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
