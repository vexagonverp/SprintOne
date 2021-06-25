package com.net.SprintOne.repositories;

import com.net.SprintOne.model.Role;
import com.net.SprintOne.model.User;
import com.net.SprintOne.model.User_Role;
import com.net.SprintOne.model.User_RoleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<User_Role, User_RoleKey> {

    @Query("FROM User_Role WHERE users.id =:num")
    List<User_Role> findById(long num);
}
