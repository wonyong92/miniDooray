package com.nhn.academy.minidooray.gateway.controller.task.rest.tag;

import com.nhn.academy.minidooray.gateway.domain.task.request.modify.TagUpdateRequestDto;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.TagCreateDto;
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
@RequestMapping("/tags")
public class TagRestController {

  final TaskService taskService;


  @GetMapping("/{tagId}")
  public ResponseEntity<String> readTag(@PathVariable(name = "tagId") Integer tagId) {
    return ResponseEntity.ok(taskService.readTag(tagId));
  }

  @PutMapping("/{tagId}")
  public ResponseEntity<String> updateTag(@RequestBody TagUpdateRequestDto tagCreateUpdateRequest,
      @PathVariable(name = "tagId") Integer tagId) {
    taskService.updateTag(tagCreateUpdateRequest, tagId);
    return ResponseEntity.status(200).build();
  }

  @PostMapping
  public ResponseEntity<String> createTag(@RequestBody TagCreateDto dto) {
    return ResponseEntity.status(200).body(taskService.registerTag(dto));
  }

  @DeleteMapping("/{tagId}")
  public ResponseEntity<Void> deleteTag(@PathVariable(name = "tagId") Integer tagId) {
    taskService.deleteTag(tagId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
