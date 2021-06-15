package com.vic.io.covidvaccination;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vic.io.covidvaccination.Model.Centers;
import com.vic.io.covidvaccination.Model.Response;
import com.vic.io.covidvaccination.Model.SessionList;
import com.vic.io.covidvaccination.Model.User;
import com.vic.io.covidvaccination.Repository.userRepo;
import com.vic.io.covidvaccination.Service.CheckAvilablity;
import com.vic.io.covidvaccination.Service.CheckData;
import com.vic.io.covidvaccination.Service.getData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class CovidvaccinationApplication implements CommandLineRunner {
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    @Autowired
    private getData getData;
    @Autowired
    private CheckData checkData;
    @Autowired
    private userRepo userRepo;
    @Autowired
    private CheckAvilablity checkAvilablity;

    public static void main(String[] args) {
        SpringApplication.run(CovidvaccinationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        List<Centers> centers = getData.getAvailablity("296");
        List<User> userList=userRepo.findAll();
//        System.out.println("centers");
//        centers.forEach(System.out::println);
//        checkData.checkAvailability();
//        System.out.println("centers");
        for (User user:userList){
            System.out.println(user.toString());
           checkData.test(user);
//
        }

    }

}
