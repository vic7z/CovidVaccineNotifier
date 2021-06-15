package com.vic.io.covidvaccination.Service;

import com.vic.io.covidvaccination.Model.Centers;
import com.vic.io.covidvaccination.Model.SessionList;
import com.vic.io.covidvaccination.Model.User;
import com.vic.io.covidvaccination.Repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckData {
    @Autowired
    private getData getData;
    @Autowired
    private userRepo userRepo;
    @Autowired
    private SendNotification notification;

//    public void validate() {
//        List<Centers> centers = new ArrayList<>();
//        List<User> userList = userRepo.findAll();
//
//        for (User user : userList) {
//            System.out.println(user.toString());
//            centers = getData.getAvailablity(user.getDistrict_id());
//            for (Centers centers1 : centers) {
//                if (centers1.getFee_type().equals(user.getFee())) {
//                    for (SessionList sessionList : centers1.getSessions()) {
//                        if (sessionList.getMin_age_limit() >= user.getAge()) {
//                            if (user.getDosageType() == 1 && sessionList.getAvailable_capacity_dose1() != 0) {
////                                notification.snd(user, centers1, sessionList);
//                            } else if (user.getDosageType() == 2 && sessionList.getAvailable_capacity_dose2() != 0) {
//                                notification.snd(user, centers1, sessionList);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    public List<Centers> checkAvailability() {
        List<Centers> centers = new ArrayList<>();
        List<Centers> filteredCenter=new ArrayList<>();

        List<SessionList> sessionLists = new ArrayList<>();
        List<User> userList = userRepo.findAll();

        for (User user : userList) {
            System.out.println(user.toString());
            centers = getData.getAvailablity(user.getDistrict_id());
            for (Centers centers1 : centers) {
                if (centers1.getFee_type().equals(user.getFee()) && centers1.getPincode() == user.getPincode()) {
                    for (SessionList sessionList : centers1.getSessions()) {
                        if (sessionList.getMin_age_limit() >= user.getAge() &&
                                sessionList.getVaccine().equals(user.getVaccine()) &&
                                sessionList.getAvailable_capacity_dose1() != 0) {

                            sessionLists.add(sessionList);

                        }
                    }
                    if (!sessionLists.isEmpty()) {
                        centers1.setSessions(sessionLists);
                        filteredCenter.add(centers1);
//                        System.out.println(centers1);
                    }
                }
            }

        }
        filteredCenter.forEach(System.out::println);
        return filteredCenter;
    }

    public List<Centers> test(User user){
        List<Centers> centers = new ArrayList<>();
        List<Centers> centersList= new ArrayList<>();

        centers = getData.getAvailablity(user.getDistrict_id());
        centersList= centers.stream()
                .parallel()
                .filter(centers1 -> centers1.getFee_type().equals(user.getFee()))
                .filter(centers1 -> centers1.getSessions()
                        .stream()
                        .anyMatch(sessionList -> sessionList.getAvailable_capacity_dose2()!=0))
                .collect(Collectors.toList());


        centersList.forEach(System.out::println);
        return centersList;

    }
}

