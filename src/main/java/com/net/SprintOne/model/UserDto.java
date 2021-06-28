package com.net.SprintOne.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.net.SprintOne.model.Role;
import com.sun.xml.bind.v2.schemagen.episode.SchemaBindings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class UserDto implements Serializable {

    private String name;
    private String email;
    private Date createdAt;
    private Date updatedAt;
    private boolean activate;
    private Set<Role> roles;

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public UserDto(){};
    public UserDto(String name, String email, Date updatedAt, Set<Role> roles) {
        this.name = name;
        this.email = email;
        this.createdAt = new Date();
        this.updatedAt = updatedAt;
        this.activate = false;
        this.roles = roles;
    }




}
