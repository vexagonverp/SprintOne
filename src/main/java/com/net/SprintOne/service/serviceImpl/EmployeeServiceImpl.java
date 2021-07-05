package com.net.SprintOne.service.serviceImpl;

import com.net.SprintOne.model.Employee;
import com.net.SprintOne.dtos.EmployeeDto;
import com.net.SprintOne.repositories.EmployeeRepository;
import com.net.SprintOne.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

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

    @Override
    public List<EmployeeDto> findAll(Pageable pageable){
        List<Employee> employees = employeeRepository.findAll(pageable).getContent();
        return convertService.convertEmployeeListToDto(employees);
    }
    
    public void save(Employee role){
        employeeRepository.save(role);
    }

    public List<EmployeeDto> findById(Long id, Pageable pageable){
        List<Employee> employees = employeeRepository.findById(id, pageable);
        return convertService.convertEmployeeListToDto(employees);
    }

    public List<EmployeeDto> findByName(String name, Pageable pageable){
        List<Employee> employees = employeeRepository.findByFullName(name, pageable);
        return convertService.convertEmployeeListToDto(employees);
    }

    public List<EmployeeDto> findByCellPhone(int phone){
        List<Employee> employees = employeeRepository.findByCellPhone(phone);
        return convertService.convertEmployeeListToDto(employees);
    }

    public List<EmployeeDto> findBySearch(String search, Pageable pageable){
        List<Employee> employees = employeeRepository.findBySearch(search, pageable);
        return convertService.convertEmployeeListToDto(employees);
    }

	public int totalItem() {
		return (int) employeeRepository.count();
	}

}
