package com.net.SprintOne.service;

import com.net.SprintOne.dtos.EmployeeDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> findAll();
    List<EmployeeDto> findAll(Pageable pageable);
    List<EmployeeDto> findBySearch(String search, Pageable pageable);
}
