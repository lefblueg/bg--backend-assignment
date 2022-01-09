package com.marscolony.user.oauth20.repositories;

import com.marscolony.user.core.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{'account.userName': ?0}")
    Optional<User> findByUserName(String userName);
}
