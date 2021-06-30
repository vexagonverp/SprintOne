package com.net.SprintOne.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.net.SprintOne.dtos.AuthenticationResponse;
import com.net.SprintOne.model.Employee;
import com.net.SprintOne.dtos.EmployeeDto;
import com.net.SprintOne.model.User;
import com.net.SprintOne.dtos.UserDto;
import com.net.SprintOne.service.JwtUserDetailsService;
import com.net.SprintOne.service.serviceImpl.ConvertServiceImpl;
import com.net.SprintOne.service.serviceImpl.EmployeeServiceImpl;
import com.net.SprintOne.service.serviceImpl.UserServiceImpl;
import com.net.SprintOne.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtTokenUtils;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private EmployeeServiceImpl employeeService;
    @Autowired
    private ConvertServiceImpl convertService;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> payload){
        String email = null;
        String password = null;
        try {
            email = payload.get("email").toString();
            password = payload.get("password").toString();
        }catch(Exception e){
            return new ResponseEntity<>(new Exception("Missing value field !")
                    , HttpStatus.FORBIDDEN);
        }
        UserDto userDto = new UserDto(email,password);
        return createAuthenticationToken(userDto);
    }

    private ResponseEntity<?> createAuthenticationToken(UserDto userDto) {
        String encodedPassword = userDto.getPassword();
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), encodedPassword)
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new BadCredentialsException("Incorrect username or password")
                    , HttpStatus.FORBIDDEN);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(userDto.getEmail());
        final String jwt = jwtTokenUtils.generateToken(userDetails);

        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/signup",
            method = RequestMethod.POST)
    public ResponseEntity<?> process(@RequestBody Map<String, Object> payload)
            throws Exception {
        System.out.println(payload);
        int phone;
        try{
            String rawNum = payload.get("phone").toString();
            phone= Integer.parseInt(rawNum);
        }catch(Exception e){
            return new ResponseEntity<>(new Exception("Invalid phone format")
                    , HttpStatus.FORBIDDEN);
        }
        String email = null;
        String password = null;
        String fullName = null;
        try {
            email = payload.get("email").toString();
            password = payload.get("password").toString();
            password = bCryptPasswordEncoder.encode(password);
            System.out.println(password);
            fullName = payload.get("fullName").toString();
        }catch(Exception e){
            return new ResponseEntity<>(new Exception("Missing value field !")
                    , HttpStatus.FORBIDDEN);
        }
        if(!userService.findByEmail(email).isEmpty()||
                !employeeService.findByCellPhone(phone).isEmpty())return new ResponseEntity<>(new Exception(
                "There is already an account with the email address: " + email+
                        " or phone number: "+ phone), HttpStatus.FORBIDDEN);
        Date date = new Date();
        UserDto userDto = new UserDto(fullName,password,email,date);
        EmployeeDto employeeDto = new EmployeeDto(phone);
        User user = convertService.convertUserDtoToEntity(userDto);
        Employee employee = convertService.convertEmployeeDtoToEntity(employeeDto);
        employee.setUser(user);
        try{
            employeeService.save(employee);
            userService.save(user);
        }catch(Exception e){
            return new ResponseEntity<>(new Exception("Couldn't save to database"),HttpStatus.FORBIDDEN);
        }
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("status", true);
        objectNode.put("message", "Account creation successful");
        //return objectNode;
        return new ResponseEntity<>(objectNode, HttpStatus.OK);
    }
}
