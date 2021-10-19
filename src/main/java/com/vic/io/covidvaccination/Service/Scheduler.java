package com.vic.io.covidvaccination.Service;


import com.vic.io.covidvaccination.Model.User;
import com.vic.io.covidvaccination.Notification.Notify;
import com.vic.io.covidvaccination.Repository.userRepo;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class Scheduler {

  private final GetAvailability getData;
  private final userRepo userRepo;
  private final CenterCheck centerCheck;
  private final Notify notify;

  DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

  @Autowired
  public Scheduler(GetAvailability getData, userRepo userRepo, CenterCheck centerCheck,
      Notify notify) {
    this.getData = getData;
    this.userRepo = userRepo;
    this.centerCheck = centerCheck;
    this.notify = notify;
  }


  @Scheduled(cron = "0 0/5 * * * ?")
  //    @Scheduled(fixedRate = 5000)
  public void check() {
    ZonedDateTime date = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
    log.info(date.format(dataFormatter));
    List<User> userList = userRepo.findAll();
    for (User user : userList) {

      if (user.getAvailableCenters().isEmpty()) {

        if (this.getData.getCenters(user).isEmpty()) {
          log.info(user.getUserName() + ": center empty");
          user.setEnable(false);
        } else {
          user.setAvailableCenters(this.getData.getCenters(user));
          log.info(user.getUserName() + ": updated center    ");
          snd(user);

        }
      //                userRepo.save(user);

      } else {
        if (user.isEnable() && LocalDate.parse(user.getTo(), dataFormatter)
            .isBefore(date.toLocalDate()) && !getData.getCenters(user).isEmpty()) {
          snd(user);
        } else if (!this.centerCheck.extractName(user.getAvailableCenters())
            .equals(this.centerCheck.extractName(getData.getCenters(user))) && !getData
            .getCenters(user).isEmpty()) {
          user.setAvailableCenters(this.getData.getCenters(user));
          userRepo.save(user);
        }
      }

    }
  }

  private void snd(User user) {
    log.info(user.getUserName() + " sending msg");
    user.setAvailableCenters(this.getData.getCenters(user));
    List<String> date = this.centerCheck.setDate(user);
    user.setFrom(date.get(date.size() - 1));
    user.setTo(date.get(0));
    user.setEnable(true);
    userRepo.save(user);
    this.notify.sendSms(user);
  }
}
