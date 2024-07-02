package com.university.wizard.repository;

import com.university.wizard.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
    @Query("select u from User u where username = :username")
    Optional<User> findByUsername(String username);

    @Query("select u from User u where email = :email")
    Optional<User> findByEmail(String email);
}
