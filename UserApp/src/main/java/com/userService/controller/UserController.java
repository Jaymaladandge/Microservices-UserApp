package com.userService.controller;

import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userService.entities.Hotel;
import com.userService.entities.Ratings;
import com.userService.entities.User;
import com.userService.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	private int retryCount = 1;
	

	
	@PostMapping("/save")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}
	
	
	@PostMapping("/feignUser/rating")
	public ResponseEntity<Ratings> createUser(@RequestBody Ratings ratings) {
		Ratings savedRatings = userService.saveRatingWithFeign(ratings);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedRatings);
	} 
	
	
	@GetMapping("/getUser/{userId}")
	@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallBack")
	//@Retry(name = "ratingHotelRetry", fallbackMethod = "ratingHotelFallBack")
	//@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallBack")
	public ResponseEntity<User> getSingleUser(@PathVariable int userId){
		logger.info("=========Retry count==========="+retryCount);
		retryCount++;
		User user = userService.getUser(userId);
		return new ResponseEntity<>(user, HttpStatus.OK); 
	}
	
	
	public ResponseEntity<User> ratingHotelFallBack(int userId, Exception ex){
		logger.info("======Fallback is executed because service is down======",ex.getMessage());
		User user = User.builder().about("This is dummy user. service is down").email("dummy@gmail.com").name("Dummy").build();
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	
	@GetMapping("/feignUser/{userId}")
	public ResponseEntity<User> getUserByFeign(@PathVariable int userId){
		User user = userService.getUserWithFeign(userId);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		logger.info("===================getAllUsers()====================");
		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	
	
	@GetMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable int userId){
		userService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	@DeleteMapping("/feignUser/rating/{ratingId}")
	public ResponseEntity<String> deleteRatingByFeign(@PathVariable int ratingId){
		String status = userService.deleteRatingWithFeign(ratingId);
		return ResponseEntity.ok(status);
	}
	
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable int userId){
		User updatedUser = userService.updateUser(user, userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
	}
	
	@PostMapping("/update")
	public ResponseEntity<String> updateUser(String about, int userId){
		String response = userService.updateUser(about, userId);
		return ResponseEntity.ok(response);
	}
	
	
	@PutMapping("/feignUser/hotel")
	public ResponseEntity<String> updateHotelByFeign(@RequestBody Hotel hotel){
		String msg = userService.updateHotelWithFeign(hotel);
		return ResponseEntity.status(HttpStatus.OK).body(msg);
	}
	
	
	
	
	@PostMapping("/find")
	public ResponseEntity<User> findUserByEmail(String email){
		System.out.println("=======---=======email======----========="+email);
		User user = userService.findUser(email);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/findBy")
	public ResponseEntity<User> findUserByNamEntityAndId(String name, int userId){
		User user = userService.getByCustomFinder(name, userId);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/findAll")
	public ResponseEntity<List<User>> findAll(){
		List<User> user = userService.getAllUsers();
		return ResponseEntity.ok(user);
	}
	
	
	@PostMapping("/delete")
	public ResponseEntity<String> delete(int userId){
		userService.deleteUsingQuery(userId);
		return ResponseEntity.ok("User deleted");
	}
	
}



//Fallback method should match return type and parameter list with method to whom this fallback method is written(here getSingleUser()).

