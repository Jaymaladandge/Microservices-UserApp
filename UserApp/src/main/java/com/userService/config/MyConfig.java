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
	  
	   
	   
	  If the RestTemplate is not configured with @LoadBalanced, the URL http://user-service/users/ will be treated as a literal
	  URL. The RestTemplate will try to connect to a host named user-service directly, which will typically result in a 
	  failure unless there is a DNS or network configuration that resolves user-service to an actual IP address.
	   
	  With @LoadBalanced: The RestTemplate uses client-side load balancing and service discovery to resolve logical service names.
	  
	  also its mendatory to use logical service names in your HTTP requests support load balancing.
	  String url = "http://user-service/users/" + userId;
	  restTemplate.getForObject(url, User.class); 
	   
	*/
	
}
