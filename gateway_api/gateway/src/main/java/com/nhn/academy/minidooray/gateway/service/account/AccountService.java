package com.nhn.academy.minidooray.gateway.service.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.nhn.academy.minidooray.gateway.config.account.AccountApiServerProperties;
import com.nhn.academy.minidooray.gateway.domain.account.AccountDto;
import com.nhn.academy.minidooray.gateway.util.RestTemplateUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountService {

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
  public AccountService(RestTemplate template, AccountApiServerProperties accountApiServerProperties) {
    this.template = template;
    this.accountApiServerProperties = accountApiServerProperties;

    accountUrl = accountApiServerProperties.getUrl();
    taskUrl = accountApiServerProperties.getUrl();
    accountPort = accountApiServerProperties.getPort();
    taskPort = accountApiServerProperties.getPort();
  }

  public String createAccount(AccountDto dto) throws JsonProcessingException {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Accept", "application/json");
    headers.setContentType(MediaType.APPLICATION_JSON);
    String result = RestTemplateUtil.createQuery(template, accountUrl, accountPort, "/accounts", HttpMethod.POST, new HttpEntity<>(mapper.writeValueAsString(dto), headers), String.class).getBody();
    return result;
  }

  public String getAccount(String id) {
    String result = RestTemplateUtil.createQuery(template, accountUrl, accountPort, "/accounts", HttpMethod.GET, String.class, Map.of("id", id)).getBody();
    if (result == null || result.equals("")) {
      return "no_data";
    }
    return result;
  }
}
