package com.net.SprintOne.repositories;

import com.net.SprintOne.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User WHERE name = :name")
    List<User> findByName(String name);


    @Query("FROM User WHERE email = :email")
    List<User> findByEmail(String email);

    @Query("FROM User WHERE token = :token")
    List<User> findByToken(UUID token);
}
