package com.userService.external.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.userService.entities.Ratings;


@FeignClient("RATING-APP")
public interface RatingService {

	
	@GetMapping("/rating/user/{userId}")
	List<Ratings> getRatings(@PathVariable int userId);
	
	
	@PostMapping("/rating/save")
	Ratings saveRating(Ratings ratings);
	
	
	@GetMapping("/rating/delete/{ratingId}")
	String deleteRating(@PathVariable int ratingId);
}
