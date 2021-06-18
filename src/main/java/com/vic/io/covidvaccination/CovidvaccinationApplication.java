package com.vic.io.covidvaccination;

import com.vic.io.covidvaccination.Repository.userRepo;
import com.vic.io.covidvaccination.Service.CenterCheck;
import com.vic.io.covidvaccination.Service.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication

@EnableScheduling
public class CovidvaccinationApplication implements CommandLineRunner {
    @Autowired
    private CenterCheck centerCheck;
    @Autowired
    private userRepo userRepo;
    @Autowired
    private Scheduler scheduler;
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


    public static void main(String[] args) {
        SpringApplication.run(CovidvaccinationApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
//        scheduler.checkData();
    }
}
