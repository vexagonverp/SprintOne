package com.net.SprintOne.repositories;

import com.net.SprintOne.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("FROM Role WHERE name = :name")
    List<Role> findByName(String name);
}
