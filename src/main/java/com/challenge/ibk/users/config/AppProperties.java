package com.challenge.ibk.users.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.validation")
public class AppProperties {

  private String emailRegex;
  private String passwordRegex;
}