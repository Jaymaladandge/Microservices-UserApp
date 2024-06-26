package com.userService.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {

	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
	
	/*By using @LoadBalanced with RestTemplate, you enable client-side load balancing in your Spring Boot application. 
	  This allows your application to distribute requests across multiple instances of a service, which can improve 
	  availability and scalability. The key steps are to add the necessary dependencies, configure your application 
	  as a Eureka client, and define a load-balanced RestTemplate bean.
	  
	  The @LoadBalanced annotation essentially wraps the RestTemplate with a LoadBalancerInterceptor.
	  
	   
	*/
	
}
