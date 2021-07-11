package com.vic.io.covidvaccination.Service;

import com.vic.io.covidvaccination.ErrorHandler.RestTemplateResponseErrorHandler;
import com.vic.io.covidvaccination.Model.Centers;
import com.vic.io.covidvaccination.Model.Response;
import com.vic.io.covidvaccination.Model.SessionList;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class getData {
    private final RestTemplate restTemplate;


    DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    @Autowired
    public getData(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    // FIXME: 17/06/21 Null Pointer Exception

//    @Cacheable("centers")
    public List<Centers> getDetails(String district_id){

        ZonedDateTime date = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        String getByDistrict = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getByDistrict)
              .queryParam("district_id",district_id)
              .queryParam("date",date.format(dataFormatter));
        return restTemplate.getForObject(builder.toUriString(), Response.class).getCenters();
   }


//check for available centers

   public List<Centers> getAvailability(String district_id){
        List<Centers> centers=getDetails(district_id);
       List<Centers> centersList1=new ArrayList<>();

       for (Centers centers1:centers){
            List<SessionList> sessionLists=centers1.getSessions();
            List<SessionList> collect = sessionLists.stream()
                    .filter(sessionList -> sessionList.getAvailable_capacity() >= 1)
                    .collect(Collectors.toList());

            if (!collect.isEmpty()){
                centers1.setSessions(collect);
                centersList1.add(centers1);
            }
        }


       return centersList1;
   }



}

