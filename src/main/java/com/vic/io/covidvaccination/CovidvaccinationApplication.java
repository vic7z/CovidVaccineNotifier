package com.vic.io.covidvaccination;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication

@EnableScheduling
//@EnableCaching
public class CovidvaccinationApplication  {

    @Bean
    public RestTemplateBuilder getRestTemplateBuilder(){
        return new RestTemplateBuilder();
    }



    public static void main(String[] args) {
        SpringApplication.run(CovidvaccinationApplication.class, args);
    }



}
