package com.nhn.academy.minidooray.gateway.util;

import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RestTemplateUtil {

  public static <T> ResponseEntity<T> createQuery(RestTemplate restTemplate, String url, String port, String path, HttpMethod method, HttpEntity<String> body, Class<T> clazz) {
    String clientId = System.getenv("client_secret");
    body.getHeaders().set("clientId", clientId);//clientid로 입력된다 - http haeder 는 대소문자를 구별하지 않으므로 자동으로 소문자로 맵핑한다
//RestTemplate 의 헤더는 소문자로 작성하는 것이 관례이다
    return restTemplate.exchange(
        url + ":" + port + path,  // 요청 URL
        method,                     // HTTP 메서드
        body,                               // 요청 본문 데이터 (없음)
        clazz                        // 응답 데이터 타입
    );
  }


  public static <T> ResponseEntity<T> createQuery(RestTemplate restTemplate, String url, String port, String path, HttpMethod method, Class<T> clazz, Map<String, Object> params) {
    String clientId = System.getenv("client_secret");
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + ":" + port + path);
    for (String key : params.keySet()) {
      builder.queryParam(key, params.get(key));
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("clientId", clientId);
    HttpEntity<Void> body = new HttpEntity<>(headers);

    return restTemplate.exchange(
        builder.toUriString(),  // 요청 URL
        method,                     // HTTP 메서드
        body,                               // 요청 본문 데이터 (없음)
        clazz                        // 응답 데이터 타입
    );
  }

}
