package com.net.SprintOne.service;

import com.net.SprintOne.dtos.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> findAll();
}
