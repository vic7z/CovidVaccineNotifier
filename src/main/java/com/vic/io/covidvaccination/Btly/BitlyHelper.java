package com.vic.io.covidvaccination.Btly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;
import java.util.Objects;

@Service
public class BitlyHelper {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${btly}")
    private String BITLY_TOKEN;

    private static final String endpoint = "https://api-ssl.bitly.com/v4/shorten";

    public String shorten(String longUrl){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", String.format("Bearer %s", BITLY_TOKEN));

        ShortenRequest request = new ShortenRequest(longUrl);
        HttpEntity<ShortenRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ShortenResponse> bitlyResponse = restTemplate.exchange(endpoint, HttpMethod.POST, entity, ShortenResponse.class);
        return Objects.requireNonNull(bitlyResponse.getBody()).getLink();
    }
}
