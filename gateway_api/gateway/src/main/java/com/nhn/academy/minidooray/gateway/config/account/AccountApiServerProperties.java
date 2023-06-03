package com.nhn.academy.minidooray.gateway.config.account;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.account")
@Getter
@Setter
public class AccountApiServerProperties {
    String url;
    String port;
}
