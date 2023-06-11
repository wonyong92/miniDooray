package com.nhn.academy.minidooray.gateway.config.properties.task;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.task")
@Getter
@Setter
public class TaskApiServerProperties {

  String url;
  String port;

  public String getFullUrl() {
    return getUrl() + ":" + getPort();
  }

  String getProject;
  String putProject;
  String delProject;
}
