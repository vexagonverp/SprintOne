package com.net.SprintOne.repositories;

import com.net.SprintOne.model.Employee;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository empRepo;
    private Employee employee;

    @Test
    void findByFullName() {
        employee = new Employee(
                "Tony Stark",
                "127/45 New York City",
                "Construction",
                "Male",
                "DI Engineer",
                "Ho Chi Minh City",
                "Asian",
                "NA",
                new Date(2000, 10, 22),
                new Date(2016, 8, 24),
                new Date(2016,9, 10),
                123456789
        );
        empRepo.save(employee);

        List<Employee> empList = empRepo.findByFullName("Tony Starko");

        assertThat(empList).isEqualTo(employee);
    }

    @Test
    @Disabled
    void findByCellPhone() {
    }
}