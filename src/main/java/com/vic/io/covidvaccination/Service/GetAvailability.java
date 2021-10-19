package com.vic.io.covidvaccination.Service;


// FIXME: 16/06/21 please send help

// FIXME: 23/06/21  API already exist for this things :/

import com.vic.io.covidvaccination.Model.Centers;
import com.vic.io.covidvaccination.Model.SessionList;
import com.vic.io.covidvaccination.Model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class GetAvailability {

  private final getData getData;

  @Autowired
  public GetAvailability(getData getData) {
    this.getData = getData;
  }

  // return the centers based on the given data
  // works most of the time ig

  public List<Centers> getCenters(User user) {
    List<Centers> centers;
    List<Centers> centersList;
    List<Centers> centersList1;

    if (!getData.getAvailability(user.getDistrict_id()).isEmpty()) {
      centers = getData.getAvailability(user.getDistrict_id());

      if (user.getFee().equals("Paid")) {
        centersList = centers;

      } else {
        centersList = centers.stream()
            .parallel()
            .filter(centers1 -> centers1.getFee_type().equals("Free"))
            .collect(Collectors.toList());
      }

      //log.info(user.getUserName() +" "+centersList);

      if (centersList.isEmpty() && !centers.isEmpty()) {
        centersList1 = filterCenter(centers, user);
      } else {
        centersList1 = filterCenter(centersList, user);
      }

      //please dont judge
      List<Centers> centersList2 = centersList1.stream()
          .filter(centers1 -> centers1.getPincode() == user.getPincode())
          .collect(Collectors.toList());
      if (centersList2.isEmpty()) {
        return centersList1;
      } else {
        return centersList2;
      }
    }
    return new ArrayList<>();

  }

  private List<Centers> filterCenter(List<Centers> centersList, User user) {
    List<Centers> centersList1 = new ArrayList<>();
    List<SessionList> collect;
    for (Centers centers1 : centersList) {
      if (!user.getVaccine().equalsIgnoreCase("any")) {
        collect = centers1.getSessions()
            .stream()
            .parallel()
            .filter(sessionList -> sessionList.getMin_age_limit() <= user.getAge())
            .filter(sessionList -> sessionList.getVaccine().equalsIgnoreCase(user.getVaccine()))
            //.filter(sessionList -> sessionList.getAvailable_capacity_dose2()>=1)
            .collect(Collectors.toList());
      } else {
        collect = centers1.getSessions()
            .stream()
            .parallel()
            .filter(sessionList -> sessionList.getMin_age_limit() <= user.getAge())
            // .filter(sessionList -> sessionList.getVaccine().equalsIgnoreCase(user.getVaccine()))
            .collect(Collectors.toList());
      }
      if (user.getDosageType() == 1) {
        collect = collect.stream()
            .filter(sessionList -> sessionList.getAvailable_capacity_dose1() != 0)
            .collect(Collectors.toList());
      } else {
        collect = collect.stream()
            .filter(sessionList -> sessionList.getAvailable_capacity_dose2() != 0)
            .collect(Collectors.toList());
      }

      if (!collect.isEmpty()) {
        centers1.setSessions(collect);
        centersList1.add(centers1);
      }
    }

    return centersList1;

  }
}

