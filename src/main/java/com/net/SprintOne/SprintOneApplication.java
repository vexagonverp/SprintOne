package com.net.SprintOne;

import com.github.javafaker.Faker;
import com.net.SprintOne.model.*;
import com.net.SprintOne.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

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
	@Profile("!test")
	@Bean
	CommandLineRunner commandLineRunner(
			UserRepository userRepository,
			RoleRepository roleRepository,
			UserRoleRepository userRoleRepository,
			EmployeeRepository employeeRepository
			) {
		return args -> {
			Faker faker = new Faker();
			Date date = new Date();

			for(int i = 0;i<20;i++) {
				Role role = new Role(faker.job().title());
				User user = new User(faker.name().fullName(), faker.name().username()+"@gmail.com", date);
				Set<Role> roles = new HashSet<>();
				Employee employee = new Employee(user);
				employee.setCell_phone(faker.random().nextInt(0,1999999999));
				roles.add(role);
				user.setRoles(roles);
				employeeRepository.save(employee);
				userRepository.save(user);
				roleRepository.save(role);
			}

		};
	}

}
