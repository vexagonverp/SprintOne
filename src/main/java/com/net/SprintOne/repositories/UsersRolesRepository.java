package com.net.SprintOne.repositories;

import com.net.SprintOne.model.User_Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UsersRolesRepository extends JpaRepository<User_Role, Long> {
}
