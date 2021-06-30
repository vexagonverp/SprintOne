package com.net.SprintOne.service.serviceImpl;

import com.net.SprintOne.model.User;
import com.net.SprintOne.dtos.UserDto;
import com.net.SprintOne.repositories.UserRepository;
import com.net.SprintOne.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConvertServiceImpl convertService;


    @Override
    public List<UserDto> findAll(){
        List<User> users = userRepository.findAll();
        return convertService.convertUserListToDto(users);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public List<UserDto> findByName(String name){
        List<User> users = userRepository.findByName(name);
        return convertService.convertUserListToDto(users);
    }

    public List<UserDto> findByEmail(String email){
        List<User> users = userRepository.findByEmail(email);
        return convertService.convertUserListToDto(users);
    }

    public User findUserByEmail(String email){
        List<User> users = userRepository.findByEmail(email);
        return users.get(0);
    }
    
}
