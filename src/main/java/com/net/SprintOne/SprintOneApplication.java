package com.net.SprintOne;

import com.github.javafaker.Faker;
import com.net.SprintOne.model.*;
import com.net.SprintOne.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class SprintOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprintOneApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
			UserRepository userRepository,
			RoleRepository roleRepository,
			UsersRolesRepository usersRolesRepository) {
		return args -> {
			Faker faker = new Faker();
			Date date = new Date();
			for(int i = 0;i<10;i++) {
				Role role = new Role(faker.job().title());
				User user = new User(faker.name().fullName(), faker.name().username()+"@gmail.com", date, date);
				User_Role user_role = new User_Role(user, role);
				userRepository.save(user);
				roleRepository.save(role);
				usersRolesRepository.save(user_role);
			}
		};
	}

}
