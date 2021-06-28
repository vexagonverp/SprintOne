package com.net.SprintOne;

import static org.assertj.core.api.Assertions.assertThat;

import com.net.SprintOne.model.*;
import com.net.SprintOne.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@ActiveProfiles("test")
@DataJpaTest
public class JPAUnitTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    public void emptyUserRepository() {
        Iterable<User> users = userRepository.findAll();
        assertThat(users).isEmpty();
    }
    @Test
    public void emptyRoleRepository() {
        Iterable<Role> roles = roleRepository.findAll();
        assertThat(roles).isEmpty();
    }
    @Test
    public void emptyUserRoleRepository() {
        Iterable<User_Role> userRoles = userRoleRepository.findAll();
        assertThat(userRoles).isEmpty();
    }

    @Test
    public void userStoring() {
        Date date = new Date();
        User user = userRepository.save(new User("admin","admin@gmail.com",date));

        assertThat(user).hasFieldOrPropertyWithValue("name", "admin");
        assertThat(user).hasFieldOrPropertyWithValue("email", "admin@gmail.com");
        assertThat(user).hasFieldOrPropertyWithValue("createdAt", date);
        assertThat(user).hasFieldOrPropertyWithValue("updatedAt", date);
    }

    @Test
    public void roleStoring(){
        Role role = roleRepository.save(new Role("admin"));
        assertThat(role).hasFieldOrPropertyWithValue("name","admin");
    }
    /* Pain
    @Test
    public void userRoleStoring(){
        Date date = new Date();
        User user = userRepository.save(new User("admin","admin@gmail.com",date,date));
        Role role = roleRepository.save(new Role("admin"));
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        user.setRoles(roles);

    }
   */

}
