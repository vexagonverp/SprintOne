package com.net.SprintOne.repositories;

import com.net.SprintOne.model.User_Role;
import com.net.SprintOne.model.User_RoleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<User_Role, User_RoleKey> {

    @Query("FROM User_Role WHERE users.id =:num")
    public List<User_Role> findById(long num);

}
