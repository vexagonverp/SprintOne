package com.net.SprintOne.service.serviceImpl;

import com.net.SprintOne.model.User;
import com.net.SprintOne.dtos.UserDto;
import com.net.SprintOne.repositories.UserRepository;
import com.net.SprintOne.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConvertServiceImpl convertService;
    @Autowired
    private JavaMailSender javaMailSender;

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

    public User findUserByEmail(String email) {
        List<User> users = userRepository.findByEmail(email);
        return users.get(0);
    }

    public User findUserByToken(UUID token){
        List<User> users = userRepository.findByToken(token);
        return users.get(0);
    }
    
}
