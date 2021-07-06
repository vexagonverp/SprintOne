package com.net.SprintOne.controller.output;
import com.net.SprintOne.dtos.EmployeeDto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable; 

public class EmployeeOutput extends AbstractOutput{
    private List<EmployeeDto> data = new ArrayList<>();
    public EmployeeOutput(){
        super();
    }
    public List<EmployeeDto> getData(){
        return data;
    }
    public void setListResult(List<EmployeeDto> data){
        this.data = data;
    }
}