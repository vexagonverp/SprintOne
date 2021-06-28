package com.net.SprintOne.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity(name="User")
@Table(
        name="users",
        uniqueConstraints = {
                @UniqueConstraint(name="email_unique",columnNames = "email")
        }
)
@JsonFilter("userFilter")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name="id",
            updatable = false
    )
    private long id;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Employee employeeId;
    @Column(
            name = "name",
            length = 50,
            nullable = false
    )
    private String name;

    @Column(
            name = "email",
            length = 50,
            nullable = false
    )
    private String email;

    @Column(
            name = "created_at",
            nullable = false
    )
    private Date createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private Date updatedAt;
    @Column(
            name = "activate",
            nullable = false
    )
    private boolean activate;



    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    @JsonIgnore
    private Set<Role> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User(){}
    public User(String name, String email, Date updatedAt) {
        this.name = name;
        this.email = email;
        this.createdAt = new Date();
        this.updatedAt = updatedAt;
    }
}
