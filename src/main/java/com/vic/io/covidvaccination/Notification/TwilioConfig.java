package com.vic.io.covidvaccination.Notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twilio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TwilioConfig {

  private String myNumber;
  private String AuthToken;
  private String AccountSid;
}
