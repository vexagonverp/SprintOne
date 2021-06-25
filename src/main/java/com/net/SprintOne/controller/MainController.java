package com.net.SprintOne.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.net.SprintOne.model.*;
import com.net.SprintOne.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository up;
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

    @RequestMapping(value = "/list/user", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User> findByNameUser(@RequestParam(name="name", required=false, defaultValue="admin") String name){
        List<User> user = up.findByName(name);
        user.forEach((User temp) ->{
            //temp.setUsers_id(null);
        });
        return user;
    }

    @RequestMapping(value = "/list/role", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Role> findByNameRole(@RequestParam(name="name", required=false, defaultValue="admin") String name){
        List<Role> roles = rp.findByName(name);
        roles.forEach((Role temp) ->{
            //temp.setUsers_id(null);
        });
        return roles;
    }

    @RequestMapping(value = "/list/userrole", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User_Role> findByNameUserRole(@RequestParam(name="name", required=false, defaultValue="1") String name){
        List<User_Role> users_roles = urp.findById(Long.parseLong(name));
        users_roles.forEach((User_Role temp) ->{
            //temp.setUsers_id(null);
        });
        return users_roles;
    }
}
