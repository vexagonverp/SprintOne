package com.net.SprintOne.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Set;

@JsonFilter("userFilter")
public class RoleDto implements Serializable {

    private long id;
    private String name;
    @JsonIgnore
    private Set<UserDto> users;

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

    public Set<UserDto> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDto> users) {
        this.users = users;
    }

    public RoleDto(){ }

    public RoleDto(String name) {
        this.name = name;
    }
}
