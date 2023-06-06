package com.nhn.academy.minidooray.gateway.controller.task.rest;

import com.nhn.academy.minidooray.gateway.domain.task.entity.ProjectStatus;
import com.nhn.academy.minidooray.gateway.domain.task.request.register.ProjectRegister;
import com.nhn.academy.minidooray.gateway.service.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor

public class TaskRestController {

  private final TaskService taskService;


  @PostMapping("/project/register")
  public String registerProject(ProjectRegister registerRequest) {
    taskService.createProject(registerRequest);
    return "redirect:/projects";
  }

  @GetMapping("/project/{projectNo}/status")
  public String showModifyProjectStatus(@PathVariable String projectNo, Model model) {
    model.addAttribute("projectNo", projectNo);
    return "project/status";
  }

  @PostMapping("/project/enumtest")
  public String test(@RequestBody ProjectStatus projectStatus) {
    return projectStatus.name();
  }

  /*
   * 프로젝트 멤버 초대
   * 나의 프로젝트 목록 - ADMIN, MEMBER 모두
   * 나의 태스크 목록
   * 프로젝트 조회
   * 프로젝트 수정
   * 프로젝트 삭제
   *
   * */

  /*
   * 태스크 생성
   * 태스크 간단 조회 - 태스크 ID, 속한 프로젝트 ID, 작성자 ID
   * 태스크 자세히 조회
   * 태스크 수정
   * 태스크 삭제
   *
   * */

  /*
   * 마일스톤 생성
   * 마일스톤 조회
   * 마일스톤 수정
   * 마일스톤 삭제
   *
   * */

  /*
   * 태그 생성
   * 태그 조회
   * 태그 수정
   * 태그 삭제
   *
   *
   * */

  /*
   * 댓글 생성
   * 댓글 조회
   * 댓글 수정
   * 댓글 삭제
   * */


  /*
   * 프로젝트 멤버 목록 출력
   * 태스크의 프로젝트 id 출력
   * 마일스톤의 프로젝트 id 출력
   * 태그의 프로젝트 id 출력
   * 댓글의 프로젝트 id 출력
   *
   *
   * */


}
