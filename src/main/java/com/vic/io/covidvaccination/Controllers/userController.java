package com.vic.io.covidvaccination.Controllers;

import com.vic.io.covidvaccination.Model.Centers;
import com.vic.io.covidvaccination.Model.User;
import com.vic.io.covidvaccination.Repository.userRepo;
import com.vic.io.covidvaccination.Service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/user")
public class userController {
    private final UserService userService;
    @Autowired
    private userRepo userRepo;

    @Autowired
    public userController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return userService.setUser(user);
    }

    @GetMapping(value = "/get-center",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Centers>> getCenters(@Param(value = "id") String id){
        return this.userService.getCenter(id);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser( @Param(value = "phoneNo")Optional<String> phoneNo){
            return this.userService.deleteByPhone(phoneNo.get());

    }

//    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<User> getUser(){
//        return userRepo.findAll();
//    }

}
