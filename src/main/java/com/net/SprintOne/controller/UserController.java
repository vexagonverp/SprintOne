package com.net.SprintOne.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.net.SprintOne.model.Employee;
import com.net.SprintOne.model.EmployeeDto;
import com.net.SprintOne.model.User;
import com.net.SprintOne.model.UserDto;
import com.net.SprintOne.service.serviceImpl.ConvertServiceImpl;
import com.net.SprintOne.service.serviceImpl.EmployeeServiceImpl;
import com.net.SprintOne.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private EmployeeServiceImpl employeeService;
    @Autowired
    private ConvertServiceImpl convertService;

    @RequestMapping(
            value = "/signup",
            method = RequestMethod.POST)
    public ObjectNode process(@RequestBody Map<String, Object> payload)
            throws Exception {
        System.out.println(payload);
        int phone;
        try{
            String rawNum = payload.get("phone").toString();
            phone= Integer.parseInt(rawNum);
        }catch(Exception e){
            throw new Exception("Invalid phone number");
        }
        String email = null;
        String password = null;
        String fullName = null;
        try {
            email = payload.get("email").toString();
            password = payload.get("password").toString();
            fullName = payload.get("fullName").toString();
        }catch(Exception e){
            throw new Exception("Empty field");
        }
        if(!userService.findByEmail(email).isEmpty()||
                !employeeService.findByCellPhone(phone).isEmpty())throw new Exception(
                "There is already an account with the email address: " + email+
                        " or phone number: "+ phone);
        Date date = new Date();
        UserDto userDto = new UserDto(fullName,email,password,date);
        EmployeeDto employeeDto = new EmployeeDto(phone);
        User user = convertService.convertUserDtoToEntity(userDto);
        Employee employee = convertService.convertEmployeeDtoToEntity(employeeDto);
        employee.setUser(user);
        try{
            employeeService.save(employee);
            userService.save(user);
        }catch(Exception e){
            throw new Exception("Couldn't save to database");
        }
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("status", true);
        objectNode.put("message", "Account creation successful");
        return objectNode;
    }
}
