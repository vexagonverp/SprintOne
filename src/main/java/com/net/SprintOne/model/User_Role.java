package com.net.SprintOne.model;

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

    @ManyToOne
    @MapsId("users_id")
    @JoinColumn(name = "users_id",
            foreignKey = @ForeignKey(
            name = "user_userId_fk"
            )
    )
    private User users;

    @ManyToOne
    @MapsId("roles_id")
    @JoinColumn(name = "roles_id",
            foreignKey = @ForeignKey(
                    name = "role_roleId_fk"
            )
    )
    private Role roles;

    public User_Role(){}
    public User_Role(User users, Role roles) {
        this.users = users;
        this.roles = roles;
    }
}
