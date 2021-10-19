package com.vic.io.covidvaccination.Btly;


import com.vic.io.covidvaccination.ErrorHandler.RestTemplateResponseErrorHandler;
import java.util.Collections;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BitlyHelper {

  private final RestTemplate restTemplate;

  @Value("${btly}")
  private String bitlyToken;

  private static final String endpoint = "https://api-ssl.bitly.com/v4/shorten";

  @Autowired
  public BitlyHelper(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder
        .errorHandler(new RestTemplateResponseErrorHandler())
        .build();
  }

  public String shorten(String longUrl) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", String.format("Bearer %s", bitlyToken));

    ShortenRequest request = new ShortenRequest(longUrl);
    HttpEntity<ShortenRequest> entity = new HttpEntity<>(request, headers);

    ResponseEntity<ShortenResponse> bitlyResponse = restTemplate
        .exchange(endpoint, HttpMethod.POST, entity, ShortenResponse.class);
    return Objects.requireNonNull(bitlyResponse.getBody()).getLink();
  }
}
