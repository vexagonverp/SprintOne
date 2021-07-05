package com.net.SprintOne.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.net.SprintOne.dtos.UserDto;
import com.net.SprintOne.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/list")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    //http://localhost:8080/api/list/user?field=name&search=all&include=name,email
    @RequestMapping(value = "/user", method= RequestMethod.GET)
    @ResponseBody
    public MappingJacksonValue getAllUsers(@RequestParam(name= "field", required=false, defaultValue="name") String field,
                                           @RequestParam(name= "search", required=false, defaultValue="all") String search,
                                           @RequestParam(name="include", required=false) String[] include,
                                           @RequestParam(name="exclude", required=false) String[] exclude){

        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.serializeAll();
        if(exclude !=null){
            simpleBeanPropertyFilter =
                    SimpleBeanPropertyFilter.serializeAllExcept(exclude);
        }
        if(include !=null){
            simpleBeanPropertyFilter =
                    SimpleBeanPropertyFilter.filterOutAllExcept(include);
        }

        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("userFilter", simpleBeanPropertyFilter);
        List<UserDto> userList= null;
        switch(field){
            case "name":
                userList = userService.findByName(search);
                break;
            case "email":
                userList = userService.findByEmail(search);
                break;
            case "all":
                userList = userService.findAll();
                break;
            default :
                userList = userService.findAll();
                break;
        }
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userList);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }
}
