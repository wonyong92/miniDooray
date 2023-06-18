package com.nhn.academy.minidooray.gateway.controller.gateway.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.academy.minidooray.gateway.config.properties.account.AccountApiServerProperties;
import com.nhn.academy.minidooray.gateway.service.account.service.AccountService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller

public class GatewayRestController {

  final RestTemplate template;
  final AccountApiServerProperties accountApiServerProperties;
  String accountUrl;
  String taskUrl;
  String accountPort;
  String taskPort;
  @Autowired
  ObjectMapper mapper;
  @Autowired
  PasswordEncoder encoder;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  AccountService accountService;

  @Autowired
  public GatewayRestController(RestTemplate template, AccountApiServerProperties accountApiServerProperties) {
    this.template = template;
    this.accountApiServerProperties = accountApiServerProperties;

    accountUrl = accountApiServerProperties.getUrl();
    taskUrl = accountApiServerProperties.getUrl();
    accountPort = accountApiServerProperties.getPort();
    taskPort = accountApiServerProperties.getPort();
  }

  @GetMapping("/")
  public String index(Authentication authentication, HttpSession session, @CookieValue(name = "ACCESS_TOKEN", required = false) String ACCESSTOKEN) throws JsonProcessingException {
    JsonNode jsonNode = mapper.readTree(mapper.writeValueAsString(session.getAttribute("Attribute")));
    System.out.println("private_email : " + jsonNode.get("private_email"));
    System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
    System.out.println(ACCESSTOKEN);
    return "index";
  }


}
