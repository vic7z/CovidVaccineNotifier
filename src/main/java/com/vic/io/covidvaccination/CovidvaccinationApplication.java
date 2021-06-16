package com.vic.io.covidvaccination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication

//@EnableScheduling
public class CovidvaccinationApplication {
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
//    @Autowired
//    private getData getData;
//    @Autowired
//    private GetAvailability getAvailability;
//    @Autowired
//    private userRepo userRepo;
//    @Autowired
//    private CenterCheck centerCheck;

    public static void main(String[] args) {
        SpringApplication.run(CovidvaccinationApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//
//        List<User> userList=userRepo.findAll();
////        User phone=userRepo.findByPhoneNo("9074960891").orElse(null);
////        System.out.println(phone.getAvailableCenters().size());
////
////   for (User user:userList){
////       centerCheck.snd(user);
////   }
//
//
//    }

}
