package com.vic.io.covidvaccination.Notification;

import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.vic.io.covidvaccination.Btly.BitlyHelper;
import com.vic.io.covidvaccination.Model.SessionList;
import com.vic.io.covidvaccination.Model.User;
import com.vic.io.covidvaccination.Repository.userRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

// FIXME: 17/06/21 list null value check
// TODO: 18/06/21 implement twilio api

@Service
@Log4j2
public class Notify {

    private final TwilioConfig twilioConfig;
    @Autowired
    private final userRepo userRepo;
    @Autowired
    private final BitlyHelper btly;

    private String uri="https://notifier1.azurewebsites.net/data";

    @Autowired
    public Notify(TwilioConfig twilioConfig, com.vic.io.covidvaccination.Repository.userRepo userRepo, BitlyHelper btly) {
        this.twilioConfig = twilioConfig;
        this.userRepo = userRepo;
        this.btly = btly;
    }

    public void SendSms(User user){

        if (!user.getAvailableCenters().isEmpty()){
            String message="Hi "+user.getUserName()+". \r\n"+ user.getAvailableCenters().size() +" vaccination centers found for "+
                    (user.getDosageType()==1?"1st":"2nd") +" dose\r\n"+
                    "more details @ "+ this.getUri(user);
            user.setEnable(false);
            sndMessage(user, message);
            log.info(message);
        }else {
            String message="hi "+user.getUserName()+"\n"+"Couldn't find any avilable centers for you";
            sndMessage(user,message);
            log.info("no centers where found");

        }
    }

    private void sndMessage(User user, String message) {
        System.out.println("msg snd");
        try{
            Message.creator(
                    new PhoneNumber(user.getPhoneNo()),
                    new PhoneNumber(twilioConfig.getMyNumber()), message)
                    .create();

        }catch (final ApiException e){
            log.warn(e);
        }
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

    public String getUri(User user){
        User user1=user;
        if (user.getId()==null){
             user1=userRepo.findByPhoneNo(user1.getPhoneNo()).get();
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.uri)
                .queryParam("id",user1.getId());

        return btly.shorten(builder.toUriString());


    }

}
