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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
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
    log.info("요청 URI : {}", requestUri);
    log.info("account full : " + accountApiServerProperties.getFullUrl());
    if (requestUri.matches("^.*(project|projects).*$")) {
      System.out.println("프로젝트 권한 확인");
      switch (request.getMethod()) {
        case "GET":
          break;
        case "POST":
        case "PUT":
        case "UPDATE":
        case "DELELTE":
          System.out.println("작성자인지 확인");
          break;
        default:
          System.out.println("no method");
      }

    } else if (requestUri.matches("^.*(task|tasks).*$")) {
      System.out.println("태스크 권한 확인");
    } else if (requestUri.matches("^.*(account|accounts).*$")) {
      log.info("어카운트 권한 확인 ");
      switch (request.getMethod()) {
        case "GET":
          log.info("프로젝트 멤버 확인 : {}", SecurityContextHolder.getContext().getAuthentication().getName());
          boolean result = checkMemberOfProject(SecurityContextHolder.getContext().getAuthentication().getName(), "1");
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

    if (handler instanceof HandlerMethod) {
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      // 컨트롤러 메소드에 접근 권한 어노테이션 확인
      if (handlerMethod.hasMethodAnnotation(PreAuthorize.class)) {
        // 접근 권한 체크 로직 수행
        // 필요한 경우 접근 거부 응답 처리 등을 수행할 수 있음
        System.out.println(handlerMethod.getMethodAnnotation(PreAuthorize.class).value());
      }
    }

    return true;
  }

  public boolean checkMemberOfProject(String clientId, String projectId) throws JsonProcessingException {
    String requestUrl = taskApiServerProperties.getFullUrl() + "/projects/members?proejctId=" + projectId;
    log.debug("check task api - get pr members full url : " + requestUrl);
    return Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(requestUrl, JsonNode[].class, projectId)))
        .anyMatch(node ->
            node.get("id").toString().equals("\"" + clientId + "\"")
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
