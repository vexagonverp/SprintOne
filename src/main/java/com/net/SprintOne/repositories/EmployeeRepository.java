package com.net.SprintOne.repositories;

import com.net.SprintOne.model.Employee;
import com.net.SprintOne.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("FROM Employee WHERE full_name = :name")
    List<Employee> findByFullName(String name);

}
