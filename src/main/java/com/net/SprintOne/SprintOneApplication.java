package com.net.SprintOne;

import com.github.javafaker.Faker;
import com.net.SprintOne.model.*;
import com.net.SprintOne.repositories.*;
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

	@Profile("!test")
	@Bean
	CommandLineRunner commandLineRunner(
			UserRepository userRepository,
			RoleRepository roleRepository,
			UserRoleRepository userRoleRepository
			) {
		return args -> {
			Faker faker = new Faker();
			Date date = new Date();

			for(int i = 0;i<10;i++) {
				Role role = new Role(faker.job().title());
				User user = new User(faker.name().fullName(), faker.name().username()+"@gmail.com", date, date);
				Set<Role> roles = new HashSet<>();
				roles.add(role);
				user.setRoles(roles);
				userRepository.save(user);
				roleRepository.save(role);
			}

		};
	}

}
