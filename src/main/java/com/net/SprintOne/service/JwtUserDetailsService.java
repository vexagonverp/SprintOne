package com.net.SprintOne.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        return new User("foo", "" +
//                "$2y$12$inS7H7gB0vxsGnwFQm3Gh.TUZ3IRKp9PKD/eAJEVkLc8IzqggFzJm", new ArrayList<>());
        return new User("foo", "foo", new ArrayList<>());
    }
}
