package com.vic.io.covidvaccination.Service;

import com.vic.io.covidvaccination.Model.Centers;
import com.vic.io.covidvaccination.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class getData {
    @Autowired
    private RestTemplate restTemplate;

    private String apiUrl ="https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict";

    public void getDetails(){
       UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
              .queryParam("district_id","296")
              .queryParam("date","12-06-2021");
       List<Centers> centersList=restTemplate.getForObject(builder.toUriString(), Response.class).getCenters();
       List<Centers> paid= centersList.stream().filter(t ->t.getFee_type().equals("Free"))
              .collect(Collectors.toList());
   }


}

