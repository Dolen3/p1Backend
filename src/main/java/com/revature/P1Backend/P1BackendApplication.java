package com.revature.P1Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EntityScan("com.revature.P1Backend.models")
@ComponentScan("com.revature.P1Backend")
public class P1BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(P1BackendApplication.class, args);

		System.out.println("Employee Reimbursement System");
	}




}


