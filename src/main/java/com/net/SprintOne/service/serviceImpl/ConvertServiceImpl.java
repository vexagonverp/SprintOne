package com.net.SprintOne.service.serviceImpl;

import com.net.SprintOne.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertServiceImpl {
    @Autowired
    private ModelMapper modelMapper;


    public RoleDto convertRoleToDto(Role roles) {
        RoleDto roleDto = modelMapper.map(roles, RoleDto.class);
        return roleDto;
    }
    public List<RoleDto> convertRoleListToDto(List<Role> roles){
        return roles.stream()
                .map(this::convertRoleToDto)
                .collect(Collectors.toList());
    }
    public Role convertRoleDtoToEntity(RoleDto roleDto){
        Role role = modelMapper.map(roleDto, Role.class);
        return role;
    }


    public UserDto convertUserToDto(User users) {
        UserDto userDto = modelMapper.map(users, UserDto.class);
        userDto.setCreatedAt(users.getCreatedAt());
        return userDto;
    }
    public List<UserDto> convertUserListToDto(List<User> users){
        return users.stream()
                .map(this::convertUserToDto)
                .collect(Collectors.toList());
    }

    public User convertUserDtoToEntity(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        user.setCreatedAt(userDto.getCreatedAt());
        return user;
    }


    public EmployeeDto convertEmployeeToDto(Employee employee) {
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        return employeeDto;
    }
    public List<EmployeeDto> convertEmployeeListToDto(List<Employee> employees){
        return employees.stream()
                .map(this::convertEmployeeToDto)
                .collect(Collectors.toList());
    }
    public Employee convertEmployeeDtoToEntity(EmployeeDto employeeDto){
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        return employee;
    }
}
