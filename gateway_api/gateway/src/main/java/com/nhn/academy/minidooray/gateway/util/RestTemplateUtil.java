package com.nhn.academy.minidooray.gateway.util;

import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RestTemplateUtil {

  private RestTemplateUtil() {
  }

  public static <T> ResponseEntity<T> createQuery(RestTemplate restTemplate, String url, String port, String path, HttpMethod method, String body, Class<T> clazz) {
    String clientId = System.getenv("client_secret");

    HttpHeaders headers = new HttpHeaders();
    headers.set("clientId", clientId);
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> body2 = new HttpEntity<>(body, headers);

    //body.getHeaders().add("clientId", clientId.toString());//clientid로 입력된다 - http haeder 는 대소문자를 구별하지 않으므로 자동으로 소문자로 맵핑한다
//RestTemplate 의 헤더는 소문자로 작성하는 것이 관례이다
    return restTemplate.exchange(
        url + ":" + port + path,  // 요청 URL
        method,                     // HTTP 메서드
        body2,                               // 요청 본문 데이터 (없음)
        clazz                        // 응답 데이터 타입
    );
  }


  public static <T> ResponseEntity<T> createQuery(RestTemplate restTemplate, String url, String port, String path, HttpMethod method, Class<T> clazz, Map<String, Object> params) {
    String clientId = System.getenv("client_secret");

    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url + ":" + port + path);
    for (Map.Entry<String, Object> entry : params.entrySet()) {
      builder.queryParam(entry.getKey(), entry.getKey());
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
