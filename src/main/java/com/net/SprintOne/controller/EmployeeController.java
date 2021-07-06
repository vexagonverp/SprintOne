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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/list")
public class EmployeeController{

    @Autowired
    private EmployeeServiceImpl employeeService;

    //http://localhost:8080/api/list/employee?field=name&search=all&include=id,name
    @RequestMapping(value = "/employee", method= RequestMethod.GET)
    @ResponseBody
    public MappingJacksonValue getAllEmployees(@RequestParam(name= "filter", required=false, defaultValue="all") String filter,
                                           @RequestParam(name="search", required=false, defaultValue="all") String search,
                                           @RequestParam(name="include", required=false) String[] include,
                                           @RequestParam(name="exclude", required=false) String[] exclude,
                                           @RequestParam(value= "page", required = false) Integer page,
                                           @RequestParam(value= "limit", required=false) Integer limit){

        EmployeeOutput result = new EmployeeOutput();
        List<EmployeeDto> employeeList= null;
        int count = (int) Math.ceil((double) (employeeService.totalItem()));
        result.setCount(count);
        if(page != null && limit != null) {
            result.setPageLimit((count / limit)+1);
            result.setCurrentPage(page);
			Pageable pageable = PageRequest.of(page - 1, limit);
            switch(filter){
                case "active":
                    employeeList = employeeService.findBySearchActive(search, pageable);
                    break;
                case "disabled":
                    employeeList = employeeService.findBySearchDisabled(search, pageable);
                    break;
                default:
                    employeeList = employeeService.findAll(pageable);
                    break;
            }
            count = employeeList.size();            
            result.setTotalItems(count);
            result.setListResult(employeeList);			
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
    
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }
}