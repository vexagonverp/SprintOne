package com.net.SprintOne.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name="User_Role")
@Table(name = "users_roles")
public class User_Role {

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name="id",
            updatable = false
    )
    private User_RoleKey id;

    @SuppressWarnings("JpaModelReferenceInspection")
    @ManyToOne
    @MapsId("users_id")
    @JoinColumn(name = "users_id",
            foreignKey = @ForeignKey(
            name = "user_userId_fk"
            )
    )
    private User users;

    @SuppressWarnings("JpaModelReferenceInspection")
    @ManyToOne
    @MapsId("roles_id")
    @JoinColumn(name = "roles_id",
            foreignKey = @ForeignKey(
                    name = "role_roleId_fk"
            )
    )
    private Role roles;

    public User_RoleKey getId() {
        return id;
    }

    public void setId(User_RoleKey id) {
        this.id = id;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }

    public User_Role(){}
    public User_Role(User users, Role roles) {
        this.users = users;
        this.roles = roles;
    }
}
