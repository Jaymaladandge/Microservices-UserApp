package com.userService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.userService.entities.Hotel;
import com.userService.entities.Ratings;
import com.userService.entities.User;

public interface UserService {

	User saveUser(User user);
	
	Ratings saveRatingWithFeign(Ratings ratings);

	List<User> getAllUsers();
	

	void deleteUser(int userId);

	void deleteUsingQuery(int userId);
	
	String deleteRatingWithFeign(int ratingId);
	

	User getUser(int userId);

	User findUser(String email);

	User getByCustomFinder(String name, int userId);

	User getUserWithFeign(int userId);
	
	String updateHotelWithFeign(Hotel hotel);
	

	User updateUser(User user, int userId);

	String updateUser(String about, int userId);

	public String updateWithRest(Ratings rating);
}
