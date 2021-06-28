package com.net.SprintOne.service.serviceImpl;

import com.net.SprintOne.model.User;
import com.net.SprintOne.model.UserDto;
import com.net.SprintOne.repositories.UserRepository;
import com.net.SprintOne.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    /*
    @Autowired
    private ModelMapper modelMapper;

    /*
    private UserDto convertToDto(User users) {
        UserDto userDto = modelMapper.map(users, UserDto.class);
        userDto.setCreatedAt(users.getCreatedAt());
        return userDto;
    }
     */

    @Override
    public List<User> findAll(){
        List<User> users = userRepository.findAll();
        return users;
    }

    public void save(User user){
        userRepository.save(user);
    }

    public List<User> findByName(String name){
        List<User> users = userRepository.findByName(name);
        return users;
    }
}
