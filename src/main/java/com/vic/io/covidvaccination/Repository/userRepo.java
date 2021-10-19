package com.vic.io.covidvaccination.Repository;

import com.vic.io.covidvaccination.Model.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends MongoRepository<User, String> {

  @Query("{ 'phoneNo' : ?0 }")
  Optional<User> findByPhoneNo(String phoneNo);
}
