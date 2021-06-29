package com.net.SprintOne.service.serviceImpl;

import com.net.SprintOne.model.Employee;
import com.net.SprintOne.model.EmployeeDto;
import com.net.SprintOne.repositories.EmployeeRepository;
import com.net.SprintOne.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ConvertServiceImpl convertService;

    @Override
    public List<EmployeeDto> findAll(){
        List<Employee> employees = employeeRepository.findAll();
        return convertService.convertEmployeeListToDto(employees);
    }

    public void save(Employee role){
        employeeRepository.save(role);
    }

    public List<EmployeeDto> findByName(String name){
        List<Employee> employees = employeeRepository.findByFullName(name);
        return convertService.convertEmployeeListToDto(employees);
    }

}
