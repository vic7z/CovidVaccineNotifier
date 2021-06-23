package com.vic.io.covidvaccination.Notification;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.vic.io.covidvaccination.Model.SessionList;
import com.vic.io.covidvaccination.Model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// FIXME: 17/06/21 list null value check
// TODO: 18/06/21 implement twilio api

@Service
@Log4j2
public class Notify {

    private final TwilioConfig twilioConfig;

    @Autowired
    public Notify(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    public boolean SendSms(User user){
        if (!user.getAvailableCenters().isEmpty()){
            String message=user.getAvailableCenters().size() +" vaccination centers found for "+
                    (user.getDosageType()==1?"1st":"2nd") +" dose "+ user.getUserName()+" \n"+
                    "centers :\n" +this.centerDetails(user);
            user.setEnable(false);
//            Message.creator(new PhoneNumber(user.getPhoneNo()),
//                    new PhoneNumber(twilioConfig.getMyNumber()),message)
//                    .create();
            log.info(message);
      }else {
//            Message.creator(new PhoneNumber(user.getPhoneNo()),
//                    new PhoneNumber(twilioConfig.getMyNumber()),"No centers were found")
//                    .create();
            log.info("no centers where found");

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
