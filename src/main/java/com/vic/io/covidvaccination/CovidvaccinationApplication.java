package com.vic.io.covidvaccination;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication

@EnableScheduling
public class CovidvaccinationApplication  {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }



    public static void main(String[] args) {
        SpringApplication.run(CovidvaccinationApplication.class, args);
    }



}
