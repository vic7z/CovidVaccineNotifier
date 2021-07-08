package com.vic.io.covidvaccination.Service;

import com.vic.io.covidvaccination.Model.Centers;
import com.vic.io.covidvaccination.Model.User;
import com.vic.io.covidvaccination.Notification.Notify;
import com.vic.io.covidvaccination.Repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final userRepo userRepo;
    private final CenterCheck centerCheck;
    private final Notify notify;
    ZonedDateTime now = ZonedDateTime.now();
    ZonedDateTime date = now.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
    DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    @Autowired
    public UserService(userRepo userRepo, CenterCheck centerCheck, Notify notify) {
        this.userRepo = userRepo;
        this.centerCheck = centerCheck;
        this.notify=notify;
    }

    public ResponseEntity<User> setUser(User newUser){
        Optional<User> user=userRepo.findByPhoneNo(newUser.getPhoneNo());

        if (user.isEmpty()){
            User user1 = centerCheck.setData(newUser);
            userRepo.save(user1);
            notify.SendSms(user1);

            return ResponseEntity.ok(newUser);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    public ResponseEntity<List<Centers>> getCenter(String id) {
        Optional<User> user=userRepo.findById(id);
        return user.map(value -> ResponseEntity.ok(value.getAvailableCenters())).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
//        if (user.isPresent()){
//            return  ResponseEntity.ok(user.get().getAvailableCenters());
//        }else {
//           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
    }

    public ResponseEntity<Void> deleteByPhone(String s) {
        User user=userRepo.findByPhoneNo(s).orElse(null);
        if (user!=null) {
            userRepo.delete(user);
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
