package com.nhn.academy.minidooray.gateway.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.academy.minidooray.gateway.config.properties.account.AccountApiServerProperties;
import com.nhn.academy.minidooray.gateway.config.properties.task.TaskApiServerProperties;
import java.util.Arrays;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
public class AuthorityCheckerInterceptor implements HandlerInterceptor {

  final RestTemplate restTemplate;
  final AccountApiServerProperties accountApiServerProperties;
  final TaskApiServerProperties taskApiServerProperties;
  final ObjectMapper objectMapper;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    String requestUri = request.getRequestURI() + request.getServletPath();
    log.info("요청 URI : {}  {}", requestUri, request.getServletPath());
    log.info("account full : " + accountApiServerProperties.getFullUrl());

    if (request.getParameter("projectId") == null || SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains("ROLE_ADMIN")) {
      return true;
    }

    if (requestUri.matches("^.*(project|projects).*$")) {
      if (request.getMethod().equals("POST")) {
        return true;
      }

      log.info("프로젝트 멤버 확인_현재 로그인 아이디: {} {} ", request.getParameter("projectId"), SecurityContextHolder.getContext().getAuthentication().getName());
      boolean result = checkMemberOfProject(request.getParameter("projectId"));
      log.info("인증 확인 : {}", result);
      return result;
    } else if (requestUri.matches("^.*(task|tasks).*$")) {
      System.out.println("태스크 권한 확인");
    } else if (requestUri.matches("^.*(account|accounts).*$")) {
      log.info("어카운트 권한 확인 ");
      switch (request.getMethod()) {
        case "GET":
          log.info("프로젝트 멤버 확인_현재 로그인 아이디: {}", SecurityContextHolder.getContext().getAuthentication().getName());
          boolean result = checkMemberOfProject("1");
          log.info("인증 확인 : {}", result);
          return result;

        case "POST":
        case "PUT":
        case "UPDATE":
        case "DELELTE":
          System.out.println("작성자인지 확인");
          break;
        default:
          System.out.println("no method");
      }
    }
    return true;
  }

  public boolean checkMemberOfProject(String projectId) throws JsonProcessingException {
    String clientId = SecurityContextHolder.getContext().getAuthentication().getName();

    String requestUrl = taskApiServerProperties.getFullUrl() + "/projects/" + projectId + "/members";
    log.info("check task api - get pr members full url : {}  {} ", requestUrl, clientId);
    JsonNode result = restTemplate.getForObject(requestUrl, JsonNode.class);
    System.out.println("result : " + result.get("members"));

    JsonNode[] members = objectMapper.readValue(result.get("members").toPrettyString(), JsonNode[].class);

    return Arrays.stream(members)
        .anyMatch(node ->
            node.get("memberId").toString().equals("\"" + clientId + "\"")
        );
  }

  public boolean checkAmIMemberOfProject(String projectId) throws JsonProcessingException {
    String requestUrl = taskApiServerProperties.getFullUrl() + "/projects/members?proejctId=" + projectId;
    log.debug("check task api - get pr members full url : " + requestUrl);
    return Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(requestUrl, JsonNode[].class, projectId)))
        .anyMatch(node ->
            node.get("id").toString().equals("\"" + SecurityContextHolder.getContext().getAuthentication().getName() + "\"")
        );
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
  }
}
