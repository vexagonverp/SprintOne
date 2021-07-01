package com.net.SprintOne.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@JsonFilter("userFilter")
public class UserDto implements Serializable {

    private long id;
    private String name;
    private String password;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean activate;
    private EmployeeDto employeeId;
    private Set<RoleDto> roles;
    private UUID token;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
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

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public UserDto(){};
    public UserDto(String email,String password){
        this.email = email;
        this.password = password;
    }
    public UserDto(String name, String password, String email, LocalDateTime updatedAt) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = updatedAt;
    }

}
