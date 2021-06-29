package com.net.SprintOne.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.net.SprintOne.model.Role;
import com.net.SprintOne.model.UserDto;
import com.net.SprintOne.model.User_Role;
import com.net.SprintOne.repositories.RoleRepository;
import com.net.SprintOne.repositories.UserRoleRepository;
import com.net.SprintOne.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/list")
public class MainController {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleRepository rp;
    @Autowired
    private UserRoleRepository urp;

    @RequestMapping(value = "/date", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ObjectNode sayHello() {
        Date d1 = new Date();
        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("currentDate", sdf.format(d1));
        return objectNode;
    }

    /*
    @RequestMapping(value = "/list/user", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<UserDto> findByNameUser(@RequestParam(name="name", required=false, defaultValue="all") String name){
        List<UserDto> users = userService.findByName(name);
        if(name.equals("all"))users= userService.findAll();
        return users;
    }
     */

    @RequestMapping(value = "/list/role", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Role> findByNameRole(@RequestParam(name="name", required=false, defaultValue="all") String name){
        List<Role> roles = rp.findByName(name);
        if(name.equals("all"))roles=rp.findAll();
        return roles;
    }

    @RequestMapping(value = "/list/user_role", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User_Role> findByNameUserRole(@RequestParam(name="name", required=false, defaultValue="0") String name){
        List<User_Role> users_roles = urp.findById(Long.parseLong(name));
        if(name.equals("0"))users_roles=urp.findAll();
        return users_roles;
    }

    //http://localhost:8080/api/list/user?name=all&include=name,email
    @RequestMapping(value = "/user", method= RequestMethod.GET)
    @ResponseBody
    public MappingJacksonValue getAllUsers(@RequestParam(name="name", required=false, defaultValue="all") String name,
                                           @RequestParam(name="exclude", required=false) String[] exclude,
                                           @RequestParam(name="include", required=false) String[] include){

        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.serializeAll();
        if(exclude != null){
            simpleBeanPropertyFilter =
                    SimpleBeanPropertyFilter.serializeAllExcept(exclude);
        }
        if(include !=null){
            simpleBeanPropertyFilter =
                    SimpleBeanPropertyFilter.filterOutAllExcept(include);
        }

        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("userFilter", simpleBeanPropertyFilter);

        List<UserDto> userList = userService.findByName(name);
        if(name.equals("all"))userList= userService.findAll();

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userList);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }
}
