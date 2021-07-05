package com.net.SprintOne.controller;

import com.net.SprintOne.controller.output.EmployeeOutput;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.net.SprintOne.dtos.EmployeeDto;
import com.net.SprintOne.service.serviceImpl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable; 

import java.util.ArrayList;
import java.util.List;

public class EmployeeController{

    @Autowired
    private EmployeeServiceImpl employeeService;

    //http://localhost:8080/api/list/employee?field=name&search=all&include=id,name
    @RequestMapping(value = "/employee", method= RequestMethod.GET)
    @ResponseBody
    public MappingJacksonValue getAllEmployees(@RequestParam(name= "field", required=false, defaultValue="name") String field,
                                           @RequestParam(name="search", required=false, defaultValue="all") String search,
                                           @RequestParam(name="include", required=false) String[] include,
                                           @RequestParam(name="exclude", required=false) String[] exclude,
                                           @RequestParam(value="page", required=false) Integer page,
                                           @RequestParam(value="limit", required=false) Integer limit){

        EmployeeOutput result = new EmployeeOutput();
        List<EmployeeDto> employeeList= null;
        if(page != null && limit != null) {
			result.setPage(page);
			Pageable pageable = PageRequest.of(page - 1, limit);
            switch(field){
                case "name":
                    employeeList = employeeService.findByName(search, pageable);
                    break;
                case "id":
                    employeeList = employeeService.findById(Long.parseLong(search), pageable);
                    break;
                case "all":
                    employeeList = employeeService.findAll(pageable);
                    break;
            }
			result.setTotalPage((int) Math.ceil((double) (employeeService.totalItem()) / limit));
		} else {
			result.setListResult(employeeService.findAll());
		}
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
                .addFilter("employeeFilter", simpleBeanPropertyFilter);
    
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(employeeList);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }
}