package com.vic.io.covidvaccination.Repository;

import com.vic.io.covidvaccination.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends MongoRepository<User,String> {
}
