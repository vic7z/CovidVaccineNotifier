package com.vic.io.covidvaccination.Service;

import com.vic.io.covidvaccination.Model.User;
import com.vic.io.covidvaccination.Repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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

    // FIXME: 18/06/21 lot of things to check

    @Scheduled(fixedDelay = 5_000)
    public void checkData(){
        List<User> userList=userRepo.findAll();
        for (User user:userList){
            user.setAvailableCenters(this.getAvailability.getCenters(user));
            if (!user.getAvailableCenters().isEmpty()) {
                if (LocalDate.parse(user.getTo(), dataFormatter).isBefore(date)) {
                    System.out.println(user.getUserName() + " date ok");
                    if (!this.centerCheck.extractName(user.getAvailableCenters()).equals(this.centerCheck.extractName(getAvailability.getCenters(user)))) {
                        System.out.println(getAvailability.getCenters(user).size());
                        if (!getAvailability.getCenters(user).isEmpty()) {
                            user.setAvailableCenters(getAvailability.getCenters(user));
                            user.setEnable(true);
                            List<String> date = this.centerCheck.setDate(user);
                            user.setFrom(date.get(0));
                            user.setTo(date.get(date.size() - 1));
                            userRepo.save(user);
                            centerCheck.snd(user);
                        } else {
                            user.setEnable(false);
                            userRepo.save(user);
                        }
                    }
                }
            }else {
                user.setEnable(false);
                userRepo.save(user);
            }
        }
    }
}
