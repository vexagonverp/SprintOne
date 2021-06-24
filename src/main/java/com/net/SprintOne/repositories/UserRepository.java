package com.net.SprintOne.repositories;

import com.net.SprintOne.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User WHERE name = :name")
    List<User> findByName(String name);
}
