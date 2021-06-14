package com.vic.io.covidvaccination;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vic.io.covidvaccination.Model.Centers;
import com.vic.io.covidvaccination.Model.Response;
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
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class CovidvaccinationApplication implements CommandLineRunner {
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    @Autowired
    private getData getData;

    public static void main(String[] args) {
        SpringApplication.run(CovidvaccinationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        ResponseEntity<List<Response>> listResponseEntity=restTemplate.exchange(apiUrl, HttpMethod.GET,null,new ParameterizedTypeReference<List<Response>>(){});
//        List<Response> centers=listResponseEntity.getBody();
//        System.out.println(centers.get(1));

//        Response[] forNow = restTemplate.getForObject(apiUrl, Response[].class);
//        System.out.println(forNow[1]);

//        String forNow = restTemplate.getForObject(apiUrl, String.class);
////        System.out.println(forNow);
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode rootNode = mapper.readTree(forNow);
//
//// Start by checking if this is a list -> the order is important here:
//
//        if (rootNode instanceof ArrayNode) {
//            // Read the json as a list:
//            Response[] objects = mapper.readValue(rootNode.toString(), Response[].class);
//            System.out.println(objects[0].toString());
//        }
//        Collection<Centers> readValues = new ObjectMapper().readValue(
//                forNow, new TypeReference<Collection<Centers>>() { }
//        );

//        RestOperations restTemplate=new RestTemplate();
//        String apiUrl="https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?district_id=296&date=12-06-2021";
//        List<Centers> centersList=restTemplate.getForObject(apiUrl, Response.class).getCenters();
//        System.out.println(centersList.get(1).toString());
        getData.getDetails();

    }

}
