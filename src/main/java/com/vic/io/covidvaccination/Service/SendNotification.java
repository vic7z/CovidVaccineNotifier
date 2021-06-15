package com.vic.io.covidvaccination.Service;

import com.vic.io.covidvaccination.Model.Centers;
import com.vic.io.covidvaccination.Model.SessionList;
import com.vic.io.covidvaccination.Model.User;
import org.springframework.stereotype.Service;

@Service
public class SendNotification {
    public void snd(User user, Centers centers1, SessionList sessionList) {
        System.out.println(user.toString());
        System.out.println(centers1.toString());
    }
}
