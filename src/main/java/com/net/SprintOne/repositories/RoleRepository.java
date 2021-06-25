package com.net.SprintOne.repositories;

import com.net.SprintOne.model.Role;
import com.net.SprintOne.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("FROM Role WHERE name = :name")
    List<Role> findByName(String name);
}
