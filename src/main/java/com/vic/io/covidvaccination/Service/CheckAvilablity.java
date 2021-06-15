package com.vic.io.covidvaccination.Service;

import com.vic.io.covidvaccination.Model.Centers;
import com.vic.io.covidvaccination.Model.SessionList;
import com.vic.io.covidvaccination.Model.User;
import com.vic.io.covidvaccination.Repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckAvilablity {
    @Autowired
    private getData getData;
    @Autowired
    private com.vic.io.covidvaccination.Repository.userRepo userRepo;

    List<Centers> filteredCenter =new ArrayList<>();

    public void test(User user){
        List<Centers> centers=getData.getAvailablity(user.getDistrict_id());
        filteredCenter=centers.stream()
                .parallel()
                .filter(centers1 -> centers1.getPincode()== user.getPincode()&&centers1.getFee_type().equals(user.getFee()))
                .collect(Collectors.toList());
    }
    public void checkSection(){
        List<SessionList> sessionLists=new ArrayList<>();
        List<Centers> centersList=new ArrayList<>();
        for (Centers centers : filteredCenter){
            for (SessionList sessionList: centers.getSessions()){
                if (sessionList.getMin_age_limit()>20 ){
                    sessionLists.add(sessionList);
                }
            }
           if (!sessionLists.isEmpty()){
               centers.setSessions(sessionLists);
               centersList.add(centers);
               sessionLists.clear();
           }

        }
        centersList.forEach(System.out::println);


    }

}
