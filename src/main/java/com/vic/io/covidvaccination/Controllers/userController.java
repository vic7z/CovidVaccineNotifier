package com.vic.io.covidvaccination.Controllers;

import com.vic.io.covidvaccination.Model.Centers;
import com.vic.io.covidvaccination.Model.User;
import com.vic.io.covidvaccination.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class userController {
    private final UserService userService;

    @Autowired
    public userController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return userService.setUser(user);
    }

    @GetMapping("/get-center")
    private ResponseEntity<List<Centers>> getCenters(@Param(value = "id")String id){
        return this.userService.getCenter(id);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser( @Param(value = "phoneNo")Optional<String> phoneNo){
            return this.userService.deleteByPhone(phoneNo.get());

    }

}