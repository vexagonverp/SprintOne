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

    String query = "SELECT e FROM employeesdetail e JOIN users u ON e.id = u.id WHERE e.id LIKE %?1% OR e.fullname LIKE %?1% ";
    @Query(value = query + "AND u.active = true", nativeQuery = true)
    List<Employee> findBySearchActive(String search, Pageable pageable);

    @Query(value =query + "AND u.active = false", nativeQuery = true)
    List<Employee> findBySearchDisabled(String search, Pageable pageable);
}
