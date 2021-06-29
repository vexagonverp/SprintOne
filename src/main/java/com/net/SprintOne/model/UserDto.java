package com.net.SprintOne.model;

import com.fasterxml.jackson.annotation.JsonFilter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@JsonFilter("userFilter")
public class UserDto implements Serializable {

    private long id;
    private String name;
    private String email;
    private Date createdAt;
    private Date updatedAt;
    private boolean activate;
    private EmployeeDto employeeId;
    private Set<RoleDto> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public EmployeeDto getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(EmployeeDto employeeId) {
        this.employeeId = employeeId;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    public UserDto(){};
    public UserDto(String name, String email, Date updatedAt) {
        this.name = name;
        this.email = email;
        this.createdAt = new Date();
        this.updatedAt = updatedAt;
    }

}
