package com.net.SprintOne.repositories;

import com.net.SprintOne.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    List<Role> findByName(String name);
}
