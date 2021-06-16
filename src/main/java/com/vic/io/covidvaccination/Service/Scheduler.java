package com.vic.io.covidvaccination.Service;

import com.vic.io.covidvaccination.Model.User;
import com.vic.io.covidvaccination.Repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

// FIXME: 16/06/21 i have no idea


@Service
public class Scheduler {
    private final userRepo userRepo;
    private final GetAvailability getAvailability;
    private final CenterCheck centerCheck;

    private final LocalDate date=LocalDate.now();
    DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Autowired
    public Scheduler(userRepo userRepo, GetAvailability getAvailability, CenterCheck centerCheck) {
        this.userRepo = userRepo;
        this.getAvailability = getAvailability;
        this.centerCheck = centerCheck;
    }

    private void checkData(){
        List<User> userList=userRepo.findAll();
        for (User user:userList){
            if (user.getAvailableCenters().equals(getAvailability.getCenters(user))){
                if (LocalDate.parse(user.getTo(), dataFormatter).isBefore(date)){
                    user.setEnable(true);
                    centerCheck.snd(user);
                }
            }
        }
    }
}
