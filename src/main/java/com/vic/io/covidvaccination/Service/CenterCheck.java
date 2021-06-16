package com.vic.io.covidvaccination.Service;

import com.vic.io.covidvaccination.Model.Centers;
import com.vic.io.covidvaccination.Model.SessionList;
import com.vic.io.covidvaccination.Model.User;
import com.vic.io.covidvaccination.Notification.Notify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CenterCheck {
    private final Notify notify;

    private static final Logger log= LoggerFactory.getLogger(CenterCheck.class);

    @Autowired
    public CenterCheck(Notify notify) {
        this.notify = notify;
    }

    public void snd(User user){
        if (user.isEnable()) {
            notify.SendSms(user);
            setDate(user);

        }
    }

    private void setDate(User user){
        List<String> dates=new ArrayList<>();
        for (Centers centers1: user.getAvailableCenters()){
            for (SessionList sessionList: centers1.getSessions()){
                dates.add(sessionList.getDate());
            }
        }
        System.out.println(dates.stream().distinct().sorted());
    }

    private List<String> extractName(List<Centers> centers){
        List<String> centreNames=new ArrayList<>();
        for (Centers centers1:centers){
            centreNames.add(centers1.getName());
        }
        return centreNames;
    }

}
