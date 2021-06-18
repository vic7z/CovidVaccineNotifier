package com.vic.io.covidvaccination.Notification;

import com.vic.io.covidvaccination.Model.SessionList;
import com.vic.io.covidvaccination.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// FIXME: 17/06/21 list null value check
// TODO: 18/06/21 implement twilio api

@Service
public class Notify {

    private final TwilioConfig twilioConfig;

    @Autowired
    public Notify(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    public boolean SendSms(User user){
        if (user.isEnable() && !user.getAvailableCenters().isEmpty()){
            String message=user.getAvailableCenters().size() +" vaccination centers found for "+
                    (user.getDosageType()==1?"1st":"2nd") +" dose "+ user.getUserName()+" \n"+
                    "centers :\n" +this.centerDetails(user);
            user.setEnable(false);
            System.out.println(message);
      }else {
            System.out.println("nothing to snd");
        }
        return true;
    }
    public boolean greetings(User user){
        System.out.println("welcome "+user.getUserName());
        return true;
    }


    private String centerDetails(User user){
        return "Name: "+user.getAvailableCenters().get(0).getName()
                +",Address :"+user.getAvailableCenters().get(0).getAddress()
                +" ,Pincode :"+user.getAvailableCenters().get(0).getPincode()
                +" available dosage "+getDosage(user);
    }

    private int getDosage(User user) {
        int sum;
        if (user.getDosageType()==1){
            sum = user.getAvailableCenters().get(0).getSessions().stream().mapToInt(SessionList::getAvailable_capacity_dose1).sum();
        }else {
            sum = user.getAvailableCenters().get(0).getSessions().stream().mapToInt(SessionList::getAvailable_capacity_dose2).sum();
        }
        return sum;
    }

}
