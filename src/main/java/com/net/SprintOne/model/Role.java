package com.net.SprintOne.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name="Role")
@Table(name="roles")
public class Role implements Serializable {
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

    @OneToMany(mappedBy="roles_users",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<User_Role> role_id;

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

    public Set<User_Role> getRole_id() {
        return role_id;
    }

    public void setRole_id(Set<User_Role> role_id) {
        this.role_id = role_id;
    }

    public Role(){}

    public Role(String name) {
        this.name = name;
    }
}
