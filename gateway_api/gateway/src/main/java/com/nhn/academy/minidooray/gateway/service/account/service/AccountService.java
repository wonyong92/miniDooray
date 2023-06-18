package com.nhn.academy.minidooray.gateway.service.account.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.academy.minidooray.gateway.config.properties.account.AccountApiServerProperties;
import com.nhn.academy.minidooray.gateway.domain.account.AccountDto;
import com.nhn.academy.minidooray.gateway.util.RestTemplateUtil;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class AccountService {

  final RestTemplate template;
  final AccountApiServerProperties accountApiServerProperties;
  String accountUrl;

  String accountPort;


  String accountApiFullUrl;
  String taskApiFullUrl;
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

    accountPort = accountApiServerProperties.getPort();

    accountApiFullUrl = accountApiServerProperties.getFullUrl();
  }

  public String createAccount(AccountDto dto) throws JsonProcessingException {
    dto.setPwd(encoder.encode(dto.getPwd()));
    String result = RestTemplateUtil.createQuery(template, accountUrl, accountPort, "/accounts", HttpMethod.POST, mapper.writeValueAsString(dto), String.class).getBody();
    return result;
  }

  public String getAccount(String id) {
//    String result = RestTemplateUtil.createQuery(template, accountUrl, accountPort, "/accounts", HttpMethod.GET, String.class, Map.of("id", id)).getBody();
//    if (result == null || result.equals("")) {
//      return "no_data";
//    }
    return template.getForObject(accountApiServerProperties.getFullUrl() + "/accounts/{memberId}/detail", String.class, id);
  }

  public String getAccountById(String id) {
//    String result = RestTemplateUtil.createQuery(template, accountUrl, accountPort, "/accounts", HttpMethod.GET, String.class, Map.of("id", id)).getBody();
//    if (result == null || result.equals("")) {
//      return "no_data";
//    }
    return template.getForObject(accountApiServerProperties.getFullUrl() + "/accounts/{memberId}", String.class, id);
  }


  public String getAllAccounts() {
//    String result = RestTemplateUtil.createQuery(template, accountUrl, accountPort, "/accounts", HttpMethod.GET, String.class, Map.of("id", id)).getBody();
//    if (result == null || result.equals("")) {
//      return "no_data";
//    }
    return template.getForObject(accountApiServerProperties.getFullUrl() + "/accounts", String.class);
  }

  public ResponseEntity<Boolean> checkExistId(String id) {
    ResponseEntity<Boolean> result = template.postForEntity(accountApiFullUrl + "/accounts/exist/id", new HttpEntity<Map<String, String>>(Map.of("clientId", id), null), Boolean.class);
    if (result.getStatusCodeValue() != 200) {
      log.info("status code check {}", result.getStatusCodeValue());
      return ResponseEntity.ok(false);
    }
    return ResponseEntity.ok(result.getBody());
  }

  public ResponseEntity<Boolean> checkExistEmail(String email) {
    ResponseEntity<Boolean> result = template.postForEntity(accountApiFullUrl + "/accounts/exist/email", new HttpEntity<Map<String, String>>(Map.of("clientEmail", email), null), Boolean.class);
    if (result.getStatusCodeValue() != 200) {
      log.info("status code check {}", result.getStatusCodeValue());
      return ResponseEntity.ok(false);
    }
    return ResponseEntity.ok(result.getBody());
  }

  public String getPwdById(String memberId) {
    return template.getForObject(accountApiServerProperties.getFullUrl() + "/accounts/{memberId}/password", String.class, memberId);
  }

  public String getEmailById(String memberId) {
    return template.getForObject(accountApiServerProperties.getFullUrl() + "/accounts/{memberId}/email", String.class, memberId);
  }

  public void putAccount(AccountDto dto, String memberId) {
    template.postForObject(accountApiServerProperties.getFullUrl() + "/accounts/{memberId}/update", dto, String.class, memberId);
  }

  public void deleteAccount(String memberId) {
    template.delete(accountApiServerProperties.getFullUrl() + "/accounts/{memberId}", memberId);
  }
}
