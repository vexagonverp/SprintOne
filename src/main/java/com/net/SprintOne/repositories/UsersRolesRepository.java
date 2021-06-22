package com.net.SprintOne.repositories;

import com.net.SprintOne.model.User_Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface UsersRolesRepository extends CrudRepository<User_Role, Integer> {

}
