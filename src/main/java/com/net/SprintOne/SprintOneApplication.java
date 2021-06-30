package com.net.SprintOne;

import com.github.javafaker.Faker;
import com.net.SprintOne.model.*;
import com.net.SprintOne.service.serviceImpl.ConvertServiceImpl;
import com.net.SprintOne.service.serviceImpl.EmployeeServiceImpl;
import com.net.SprintOne.service.serviceImpl.RoleServiceImpl;
import com.net.SprintOne.service.serviceImpl.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SprintOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprintOneApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	@Bean
	public ConvertServiceImpl convertService(){ return new ConvertServiceImpl();}
	@Bean
	public PasswordEncoder Encoder() {
		return new BCryptPasswordEncoder();
	}
	@Profile("!test")
	@Bean
	CommandLineRunner commandLineRunner(
			UserServiceImpl userService,
			EmployeeServiceImpl employeeService,
			RoleServiceImpl roleService,
			ConvertServiceImpl convertService,
			BCryptPasswordEncoder bCryptPasswordEncoder
			) {
		return args -> {
			Faker faker = new Faker();
			Date date = new Date();
			RoleDto roleDto = new RoleDto(faker.job().title());
			Role convertRole = convertService.convertRoleDtoToEntity(roleDto);
			for(int i = 0;i<20;i++) {
				UserDto userDto = new UserDto(faker.name().fullName(),bCryptPasswordEncoder.encode(faker.color().name()),faker.name().username()+"@gmail.com", date);
				EmployeeDto employeeDto = new EmployeeDto(faker.random().nextInt(0,1999999999));
				User user = convertService.convertUserDtoToEntity(userDto);
				Employee employee = convertService.convertEmployeeDtoToEntity(employeeDto);
				Set<Role> roles = new HashSet<>();
				roles.add(convertRole);
				employee.setUser(user);
				user.setRoles(roles);
				employeeService.save(employee);
				userService.save(user);
				roleService.save(convertRole);
			}
			/*
			Always convert a DTO before setting a relationship
			Example : convert RoleDto into Role
			convert UserDto into User
			Role.setUser(User)
			Never give DTO a relationship however DTO can still display relationship
			if original object had one
			 */

		};
	}

}
