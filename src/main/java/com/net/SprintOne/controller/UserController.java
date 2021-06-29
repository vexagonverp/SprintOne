package com.net.SprintOne.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(
            value = "/signup",
            method = RequestMethod.POST)
    public ObjectNode process(@RequestBody Map<String, Object> payload)
            throws Exception {
        System.out.println(payload);
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("status", true);
        return objectNode;
    }
}
