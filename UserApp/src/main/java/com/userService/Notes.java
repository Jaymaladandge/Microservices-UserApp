package com.userService;

public class Notes {
/*
	
	 Spring 5.3 + Hibernate 5.5.x  + MySQL Connector/J version 8.0.x
	
There are two primary types of communication methods used in microservices: synchronous and asynchronous. 	

1. Synchronous Communication
In synchronous communication, the client sends a request and waits for a response from the server before proceeding. 
This type of communication is typically used when an immediate response is required.
	
2. Asynchronous Communication
In asynchronous communication, the client sends a request without waiting for an immediate response. Instead, 
the response is processed whenever it becomes available. This type of communication is useful for decoupling services and 
improving system resilience.	
	
RestTemplate: 
Used for synchronous communication in Spring applications. The calling thread waits for the response, 
which means it's a blocking operation.

WebClient: 
Preferred for asynchronous communication in Spring applications. It allows non-blocking calls and better 
resource utilization, especially useful in reactive programming and high-concurrency scenarios.

When designing microservices, the choice between synchronous (RestTemplate) and asynchronous (WebClient) communication 
depends on the specific use case, system requirements, and performance considerations.	
	
	
	
	
@SpringBootApplication = @EnableAutoConfiguration + @SpringBootConfiguration + @ComponentScan


@EnableAutoConfiguration :-

designed to minimize the boilerplate configuration needed to get a Spring application up and running.
it scans the classpath for various configurations and automatically applies them based on the dependencies and properties present. 
It can configure data sources, JPA, web servers, etc., without explicit configuration.

Exclude Auto-configuration Classes
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)

Overriding Auto-configuration
For instance, if you define your DataSource bean, it will override the auto-configured one.
@Configuration
public class MyConfiguration {

    @Bean
    public DataSource dataSource() {
        // return your custom datasource
    }
}


 @Configuration :-
 
 annotation which indicates that the class has @Bean definition methods. 
 So Spring container can process the class and generate Spring Beans to be used in the application.
 
 
 
 @SpringBootConfiguration: 
 
 This annotation is used to indicate that the class is a Spring Boot configuration class.
  It is similar to @Configuration but specific to Spring Boot applications.
  
  
  
 @ComponentScan :-
 
 used in the Spring Framework for auto-detecting and registering Spring-managed components (e.g. beans, controllers, services, repositories, etc.) 
 within a specified package or set of packages. 
 Spring will scan the specified package(s) for classes annotated with @Component (or any of its specialisations, such as @Controller, @Service, or @Repository) and 
 automatically create instances of these classes as beans in the Spring container. These beans can then be injected into other beans or components 
 using dependency injection. 
 @ComponentScan("com.example.myapp") This will instruct Spring to scan the com.example.myapp package (and its sub-packages) for all classes annotated with 
 @Component or its specialisations, and register them as beans in the Spring container.
 you can customize the behavior of @ComponentScan by using other attributes, such as basePackages, basePackageClasses, excludeFilters, and includeFilters
 If you have not specified @ComponentScan in a Spring Boot project, Spring Boot will use its default component scanning behavior to detect and register Spring-managed components in your application.
 By default, Spring Boot will perform component scanning starting from the package of your main class (the class annotated with @SpringBootApplication). Spring Boot will automatically detect and register
 any components annotated with @Component or its specializations (@Controller, @Service, @Repository, etc.) in this package and its sub-packages. 
  
  
  
 @EnableFeignClients :-
 
 is an annotation in Spring Cloud that enables Feign declarative REST client support. Feign is a lightweight framework 
 that simplifies the creation of REST clients. It allows you to write HTTP clients declaratively using annotations, making it easier to integrate 
 with other microservices or HTTP APIs. 
 Enabling Feign Clients: 
 Typically used on a configuration class (usually the main application class annotated with @SpringBootApplication).
 It scans for interfaces annotated with @FeignClient within specified base packages. 
 This annotation is used to enable Feign clients in a Spring Boot application. Feign clients are interfaces annotated
 with @FeignClient that specify the microservice or HTTP API to communicate with. 
 It sets up necessary beans and configurations to enable Feign clients to make HTTP requests to the specified services.
  
 
 
 You might use the Retry Mechanism in the below Scenarios.
 
 1. Network or Communication Failures:If your application communicates with external services or databases over the network, network issues or service unavailability can occur. 
 2. Database Operations: When working with databases deadlocks may occur.
 3. Custom Exception Handling: You might have "specific exceptions" that you want to retry based on certain conditions.
 
 
 When choosing between the two, consider the broader requirements of your application. 
 If you need multiple resilience patterns, Resilience4j might be a better fit(@Retry Part of Resilience4j). 
 If you need simple retry functionality, Spring Retry's @Retryable(Part of Spring Retry) can be straightforward and effective.
 
 1)Add the @EnableRetry annotation to one of your configuration classes to Enable Retry Support.
  
 2)@Retryable: Configuration can be directly embedded within the annotation.
     @Retryable(
        value = {RuntimeException.class},
        maxAttempts = 3, // Retry up to 3 times
        backoff = @Backoff(delay = 1000)) // Wait 1 second between retries
     public String fetchData(String param1, String param2) {}
        
maxAttempts: Maximum number of retry attempts.
value: Specify the exceptions to retry on.
backoff: Define backoff options such as delay and multiplier.       

3)@Recover
It specifies the fallback method if all retries fail for a @Retryable method. The method accepts the exception that occurred in the Retryable method and its arguments in the same order.
 @Recover
  public String getBackendResponseFallback(RemoteServiceNotAvailableException e, String param1, String param2);
  
 
 @Retry: Configuration is often done via external configuration files (application.properties or application.yml), although it can also be partially configured through annotations.
 
 
 
 They manage the rate of traffic from clients or services, limiting the number of requests allowed within a specified period.
 If the request count exceeds the set limit defined by the rate limiter, all the excess calls are blocked.
 
 Prevent resource shortages by malicious attacks (e.g. DoS or DDoS)
 Prevent servers from being overloaded. Rate limiters can filter out extra requests caused by bots or users’ misbehavior.
	


  Dependency : spring-cloud-starter-openfeign

 @EnableFeignClients :-
 
 is an annotation in Spring Cloud that enables Feign declarative REST client support. Feign is a lightweight framework 
 that simplifies the creation of REST clients. It allows you to write HTTP clients declaratively using annotations, making it easier to integrate 
 with other microservices or HTTP APIs. 
 Enabling Feign Clients: 
 Typically used on a configuration class (usually the main application class annotated with @SpringBootApplication).
 It scans for interfaces annotated with @FeignClient within specified base packages. 
 This annotation is used to enable Feign clients in a Spring Boot application. Feign clients are interfaces annotated
 with @FeignClient that specify the microservice or HTTP API to communicate with. 
 It sets up necessary beans and configurations to enable Feign clients to make HTTP requests to the specified services.


 Configuration: Customize Feign clients by providing properties such as timeouts, retries, error handling, etc., through configuration options.
 
Introduction to @FeignClient
In today’s world of microservices and cloud-native applications, communication between different services is more of 
a rule rather than an exception. Microservices often have to interact with each other to fulfill business logic, query data, 
or handle transactions. However, the process of enabling this communication can become complex and error-prone if 
not managed correctly. This is where Spring Cloud’s @FeignClient comes into play, offering a robust, streamlined solution 
for inter-service communication.

Why is @FeignClient Important?
While traditional monolithic architectures consist of tightly-coupled, single-codebase applications, microservices are 
essentially the opposite. They are a collection of loosely-coupled services, each responsible for a specific piece of 
functionality. These services must communicate efficiently to deliver a cohesive user experience.

When a microservice wants to call another service’s API, developers often use HTTP clients or REST templates to make those 
calls. Although these are functional methods, they entail a lot of boilerplate code, making the codebase harder to maintain 
and understand.

The @FeignClient annotation streamlines this process by abstracting the HTTP client layer, allowing developers to focus 
more on business logic and less on infrastructural concerns.

Key Advantages
Declarative Approach
The most compelling advantage of using @FeignClient is its declarative nature. You define an interface and annotate it 
with @FeignClient, and Spring takes care of the rest. You don't have to write code for HTTP calls, connection settings, 
or response parsing; Spring Boot handles all these concerns behind the scenes.

Built-In Load Balancing
Microservices often run in a distributed environment where multiple instances of a service may exist. 
The @FeignClient annotation, when used in conjunction with Spring Cloud and a service registry like Eureka, offers 
built-in client-side load balancing. This means that requests are automatically routed to different service instances, 
providing both redundancy and efficient resource utilization.

Integrated Security
@FeignClient also integrates well with Spring Security, enabling you to easily secure inter-service communication. 
This ensures that services are authenticated and authorized before they can communicate with each other.

Fallback Mechanisms
In a microservices environment, service failures are not uncommon. To build a resilient system, you can define fallback 
methods that get triggered when a service is unavailable. This contributes to the fault-tolerance of your application.

How Does It Work?
The @FeignClient annotation works by dynamically creating a proxy of the annotated interface at runtime. Each method 
in this interface corresponds to an HTTP request to the service specified in the annotation. When a method of the interface 
is called, Spring intercepts this call and translates it into an HTTP request, including URL mapping, request and 
response body conversion, and header setting. It then sends this request to the target service, processes the response, 
and returns it back as the return value of the method.

Integration with Spring Cloud
@FeignClient is an integral part of the Spring Cloud ecosystem, which is a set of tools for building cloud-native 
applications. When used in a Spring Cloud project, the Feign client gains additional capabilities like centralized 
configuration and easy integration with other Spring Cloud modules, such as Spring Cloud Stream or Spring Cloud Config.

Setting Up the Environment
To take advantage of Spring’s @FeignClient, you'll first need to set up your development environment correctly. 
This section outlines a step-by-step approach to get you started with a Spring Boot application and how to incorporate 
the Feign client.
	
		  
		  
		  
		  
		  
		  
For smaller, simpler applications, a standalone application server may be sufficient and more convenient. For larger, more complex applications, using a dedicated web server in conjunction with an application server can provide better performance, scalability, and security.		  
		  
		  
*/	
}
