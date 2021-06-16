package com.vic.io.covidvaccination.Notification;

import com.vic.io.covidvaccination.Model.Centers;
import com.vic.io.covidvaccination.Model.SessionList;
import com.vic.io.covidvaccination.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Notify {

    private final TwilioConfig twilioConfig;

    @Autowired
    public Notify(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    public boolean SendSms(User user){
        if (user.isEnable()){
            String message=user.getAvailableCenters().size() +" vaccination centers found for "+
                    (user.getDosageType()==1?"1st":"2nd") +" dose \n"+
                    "centers :\n" +this.centerDetails(user);
            user.setEnable(false);
            System.out.println(message);
      }
        return true;
    }


    private String centerDetails(User user){
        return "Name: "+user.getAvailableCenters().get(1).getName()
                +",Address :"+user.getAvailableCenters().get(1).getAddress()
                +" ,Pincode :"+user.getAvailableCenters().get(1).getPincode()
                +" available dosage "+getDosage(user);
    }

    private int getDosage(User user) {
        int sum;
        if (user.getDosageType()==1){
            sum = user.getAvailableCenters().get(1).getSessions().stream().mapToInt(SessionList::getAvailable_capacity_dose1).sum();
        }else {
            sum = user.getAvailableCenters().get(1).getSessions().stream().mapToInt(SessionList::getAvailable_capacity_dose2).sum();
        }
        return sum;
    }

}
