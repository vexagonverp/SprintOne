package com.net.SprintOne.repositories;

import com.net.SprintOne.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    
    @Query("FROM Employee WHERE id = :id")
    List<Employee> findById(Long id, Pageable pageable);

    @Query("FROM Employee WHERE full_name = :name")
    List<Employee> findByFullName(String name, Pageable pageable);

    @Query("FROM Employee WHERE cell_phone=:phone")
    List<Employee> findByCellPhone(int phone);

    String query = "FROM Employee WHERE id LIKE CONCAT('%',:search,'%')OR fullname LIKE CONCAT('%',:search,'%')";
    @Query(query)
    List<Employee> findBySearch(String search, Pageable pageable);

}
