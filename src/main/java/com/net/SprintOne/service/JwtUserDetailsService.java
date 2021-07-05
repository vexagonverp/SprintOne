package com.net.SprintOne.service;

import com.net.SprintOne.dtos.CustomUserDetails;
import com.net.SprintOne.model.User;
import com.net.SprintOne.repositories.UserRepository;
import com.net.SprintOne.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        return new User("foo", "" +
//                "$2y$12$inS7H7gB0vxsGnwFQm3Gh.TUZ3IRKp9PKD/eAJEVkLc8IzqggFzJm", new ArrayList<>());
        User user = userService.findUserByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        return new CustomUserDetails(user);
    }
}
