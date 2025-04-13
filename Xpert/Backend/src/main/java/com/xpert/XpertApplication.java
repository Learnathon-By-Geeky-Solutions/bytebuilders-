package com.xpert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.xpert.repository.UserRepository;

@SpringBootApplication
public class XpertApplication {

	public static void main(String[] args) {
		ApplicationContext context =  SpringApplication.run(XpertApplication.class, args);
		
		UserRepository userRepository = context.getBean(UserRepository.class);
	
		userRepository.findAll().forEach(user ->
	    System.out.println("User: " + user.getFirstName() + " " + user.getLastName() + ", ID: " + user.getId())
	);


	

    }
		
	

}
