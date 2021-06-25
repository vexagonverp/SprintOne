package com.net.SprintOne.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class User_RoleKey implements Serializable {
    @Column(name = "roles_id")
    Long role_Id;

    @Column(name = "users_id")
    Long user_Id;


}
