package com.net.SprintOne.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="Role")
@Table(name="roles")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(
            nullable = false
    )
    private String name;

    @ManyToOne
    @JoinColumn(name="roles_id", nullable=false)
    private User_Role users_roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
