package com.vic.io.covidvaccination.Service;

import com.vic.io.covidvaccination.Model.User;
import com.vic.io.covidvaccination.Repository.userRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Log4j2
@Service
public class Scheduler {

    private final GetAvailability getData;
    private final userRepo userRepo;
    private final CenterCheck centerCheck;

    private final LocalDate date=LocalDate.now();
    DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Autowired
    public Scheduler(GetAvailability getData, userRepo userRepo, CenterCheck centerCheck) {
        this.getData = getData;
        this.userRepo = userRepo;
        this.centerCheck = centerCheck;
    }


    @Scheduled(fixedDelay = 5_000)
    public void check(){
        List<User> userList=userRepo.findAll();

        for (User user:userList){

            if (user.getAvailableCenters().isEmpty()){
               if (this.getData.getCenters(user).isEmpty()){
                   log.info(user.getUserName()+": center empty");
                   user.setEnable(false);
               }else {
                   user.setAvailableCenters(this.getData.getCenters(user));
                   user.setEnable(true);
                   log.info(user.getUserName()+": updated center    ");
                   snd(user);

               }
                userRepo.save(user);
            }else if (user.isEnable() &&
                    !this.centerCheck.extractName(user.getAvailableCenters()).equals(this.centerCheck.extractName(getData.getCenters(user)))
            && LocalDate.parse(user.getTo(), dataFormatter).isBefore(date)){
                snd(user);
            }

        }
    }

    private void snd(User user) {
        log.info(user.getUserName()+ " sending msg");
        user.setAvailableCenters(this.getData.getCenters(user));
        List<String> date = this.centerCheck.setDate(user);
        user.setFrom(date.get(0));
        user.setTo(date.get(date.size() - 1));
        userRepo.save(user);
        this.centerCheck.snd(user);
    }
}
