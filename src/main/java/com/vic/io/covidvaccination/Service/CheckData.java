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


    public List<Centers> test(User user){
        List<Centers> centers;
        List<Centers> centersList;
        List<Centers> centersList1= new ArrayList<>();


        centers = getData.getAvailablity(user.getDistrict_id());

        centersList= centers.stream()
                .parallel()
                .filter(centers1 -> centers1.getFee_type().equals(user.getFee()))
                .filter(centers1 -> centers1.getPincode()== user.getPincode())
                .collect(Collectors.toList());

//        for (Centers centers1:centersList){
//
//            List<SessionList> collect = centers1.getSessions()
//                    .stream()
//                    .parallel()
//                    .filter(sessionList -> sessionList.getMin_age_limit() <= user.getAge())
//                    .filter(sessionList -> sessionList.getVaccine().equals(user.getVaccine()))
//                    .filter(sessionList -> sessionList.getAvailable_capacity_dose2()>=1)
//                    .collect(Collectors.toList());
//            if(user.getDosageType()==1){
//                collect=collect.stream().filter(sessionList -> sessionList.getAvailable_capacity_dose1()!=0).collect(Collectors.toList());
//            }else {
//                collect=collect.stream().filter(sessionList -> sessionList.getAvailable_capacity_dose2()!=0).collect(Collectors.toList());
//            }
//
//            if (!collect.isEmpty()) {
//                centers1.setSessions(collect);
//                centersList1.add(centers1);
//            }
//
//        }

        centersList1=filterCenter(centersList,user);

    if (centersList1.isEmpty() && !centers.isEmpty()){
       centersList1=filterCenter(centers,user);
    }

       centersList1.forEach(System.out::println);
        return centersList1;

    }

    private List<Centers> filterCenter(List<Centers> centersList,User user){
        List<Centers> centersList1= new ArrayList<>();
        for (Centers centers1:centersList){

            List<SessionList> collect = centers1.getSessions()
                    .stream()
                    .parallel()
                    .filter(sessionList -> sessionList.getMin_age_limit() <= user.getAge())
                    .filter(sessionList -> sessionList.getVaccine().equals(user.getVaccine()))
                    .filter(sessionList -> sessionList.getAvailable_capacity_dose2()>=1)
                    .collect(Collectors.toList());
            if(user.getDosageType()==1){
                collect=collect.stream().filter(sessionList -> sessionList.getAvailable_capacity_dose1()!=0).collect(Collectors.toList());
            }else {
                collect=collect.stream().filter(sessionList -> sessionList.getAvailable_capacity_dose2()!=0).collect(Collectors.toList());
            }

            if (!collect.isEmpty()) {
                centers1.setSessions(collect);
                centersList1.add(centers1);
            }

        }
        return centersList1;

    }
}

