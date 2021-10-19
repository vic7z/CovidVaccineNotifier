package com.vic.io.covidvaccination.Notification;

import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class twilioInit {

  private static final Logger logger = LoggerFactory.getLogger(twilioInit.class);


  @Autowired
  public twilioInit(TwilioConfig configuration) {
    Twilio.init(
        configuration.getAccountSid(),
        configuration.getAuthToken()
    );
    logger.info("Twilio init");
  }
}
