package com.userService;

public class Profiling {

	/*
	
	  ‘application.properties’ file is the default profile.
	  
	  1) application-dev.properties
	  2) application-test.properties
	  3) application-prod.properties
	  
	  property files at the same location where ‘application.properties’ file resides.
	  Please note the naming convention: application-<environment>.properties
	  
	  we will specify which profile is active by setting the value of property ‘spring.profiles.active’ in ‘application.properties’
	  spring.profiles.active = dev
	  
	  
	  --------------------------------------------------------------------
	  
	  we can specify the dev, test, and production properties in the same application.properties file known as multi-document file.
	  
	  app.info= This is the multi-document file
	  spring.config.activate.on-profile=dev
	  spring.datasource.userName=sa
	  spring.datasource.password=sa
	 
	 
	  spring.config.activate.on-profile=test
	  spring.datasource.userName=root
	  spring.datasource.password=Root@123
	  
	  spring.config.activate.on-profile=prod 
	  spring.datasource.username=username 
	  spring.datasource.password=password
	  
	  
	  How does Spring Container read multi-document file?
	  Spring Container reads this file from top to bottom order. That is, if some property, say ‘spring.datasource.url’, 
	  occurs more than once in the file, the container will consider the endmost value. In the above example, based on the 
	  endmost value, it is clear that the prod profile is active as the value of property ‘spring.config.activate.on-profile’ 
	  is set to ‘prod’.

	  Note: One point to note here is that starting Spring Boot 2.4, property ‘spring.config.activate.on-profile’ can’t 
	  be used in combination with property  ‘spring.profiles.active’. On doing so, we can see a ConfigDataException.
	  
	  
	  
	  
	  @Configuration
	public class DataSourceConfig {

    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:h2:mem:devDb");
        dataSource.setUsername("devUser");
        dataSource.setPassword("devPass");
        return dataSource;
    }
   }
   When the dev profile is active, the devDataSource bean will be used; 	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	*/
	
	
}
