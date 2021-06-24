package com.net.SprintOne.model;

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
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name="id",
            updatable = false
    )
    private long id;
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



    @OneToMany(mappedBy="users_roles",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore //rubber band fix for now
    private Set<User_Role> users_id;

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

    public Set<User_Role> getUsers_id() {
        return users_id;
    }

    public void setUsers_id(Set<User_Role> users_id) {
        this.users_id = users_id;
    }
    public User(){}
    public User(String name, String email, Date createdAt, Date updatedAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
