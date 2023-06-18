package com.nhn.academy.minidooray.gateway.config.properties.account;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.account")
@Getter
@Setter
public class AccountApiServerProperties {

  String url;
  String port;
  String getMemberDetail;
  String postMembers;
  String getMembers;
  String getMemberById;
  String getMemberPassword;
  String getMemberEmail;
  String getMemberLogin;
  String postMemberUpdate;
  String postMemberDelete;

  public String getFullUrl() {
    return getUrl() + ":" + getPort();
  }
}
