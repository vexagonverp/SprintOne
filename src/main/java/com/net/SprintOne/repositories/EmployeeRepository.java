package com.net.SprintOne.repositories;

import com.net.SprintOne.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("FROM Employee WHERE full_name = :name")
    List<Employee> findByFullName(String name);

    @Query("FROM Employee WHERE cell_phone=:phone")
    List<Employee> findByCellPhone(int phone);
}
