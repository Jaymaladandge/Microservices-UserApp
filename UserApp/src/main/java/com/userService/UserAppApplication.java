package com.userService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableEurekaClient
@EnableFeignClients
@EnableTransactionManagement
@SpringBootApplication
public class UserAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAppApplication.class, args);
	}

}


//A Spring Boot application packaged as a standalone JAR file with an embedded Tomcat server can handle both static 
//and dynamic content.