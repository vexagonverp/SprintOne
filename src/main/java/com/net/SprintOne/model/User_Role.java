package com.net.SprintOne.model;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name="User_Role")
@Table(name="users_roles")
public class User_Role implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name="id",
            updatable = false
    )
    private long id;


    @ManyToOne
    @JoinColumn(
            name="user_id",
            nullable=false,
            foreignKey = @ForeignKey(
            name = "user_userId_fk"
        )
    )
    private User users_roles;

    @ManyToOne
    @JoinColumn(
            name="role_id",
            nullable=false,
            foreignKey = @ForeignKey(
                    name = "role_roleId_fk"
            )
    )
    private Role roles_users;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUsers_roles() {
        return users_roles;
    }

    public void setUsers_roles(User users_roles) {
        this.users_roles = users_roles;
    }

    public Role getRoles_users() {
        return roles_users;
    }

    public void setRoles_users(Role roles_users) {
        this.roles_users = roles_users;
    }
    public User_Role(){}
    public User_Role(User users_roles, Role roles_users) {
        this.users_roles = users_roles;
        this.roles_users = roles_users;
    }
}
