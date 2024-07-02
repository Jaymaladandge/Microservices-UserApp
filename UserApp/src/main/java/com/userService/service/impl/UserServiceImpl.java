package com.userService.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.userService.entities.Hotel;
import com.userService.entities.Ratings;
import com.userService.entities.User;
import com.userService.exceptions.ResourceNotFoundException;
import com.userService.external.service.HotelService;
import com.userService.external.service.RatingService;
import com.userService.repositories.UserRepository;
import com.userService.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private HotelService hotelService;
	
	

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public Ratings saveRatingWithFeign(Ratings ratings) {
		return ratingService.saveRating(ratings);
	}
	
	
	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(int userId) {

		User user = userRepository.findById(String.valueOf(userId))
				.orElseThrow(() -> new ResourceNotFoundException("user with given id not found on server : " + userId));

		ParameterizedTypeReference<ArrayList<Ratings>> responseType = new ParameterizedTypeReference<ArrayList<Ratings>>() {
		};
		ResponseEntity<ArrayList<Ratings>> response = restTemplate.exchange("http://RATING-APP/rating/user/" + userId,
				HttpMethod.GET, null, responseType);
		ArrayList<Ratings> ratings = response.getBody();

		List<Ratings> list = ratings.stream().map(rating -> {
			Hotel hotel = restTemplate.getForObject("http://HOTEL-APP/hotel/" + rating.getHotelId(), Hotel.class);
			rating.setHotel(hotel);
			return rating;
		}).collect(Collectors.toList());

		user.setRatings(list);
		logger.info("===============user in getUser()=================" + user);
		return user;
	}

	public User getUserWithFeign(int userId) {

		User user = userRepository.findById(String.valueOf(userId)).orElseThrow(() -> {
			throw new ResourceNotFoundException("resource not found");
		});
		
		List<Ratings> ratings = ratingService.getRatings(userId);
		user.setRatings(ratings);
		logger.info("======================getUserWithFeign() user====================="+user);
		
		return user;
	}
	
	
	@Override
	public User getByCustomFinder(String name, int userId) {
		return userRepository.findByNameAndUserId(name, userId);
	}
	
	
	@Override
	public User findUser(String email) {
		return userRepository.findUserWithEmailAdd(email);
	}

	
	
	@Override
	public void deleteUsingQuery(int userId) {
		userRepository.deleteUserWithQuery(userId);
	}
	
	@Override
	public void deleteUser(int userId) {
		userRepository.deleteById(String.valueOf(userId));
	}
	
	public String deleteRatingWithFeign(int ratingId) {
		return ratingService.deleteRating(ratingId);
	}
	

	@Override
	public String updateUser(String about, int userId) {

		Ratings ratings = Ratings.builder().ratingId(1).feedback("good").rating("4").build();
		String response = updateWithRest(ratings);
		logger.info("=================Response from Rating API=================" + response);

		userRepository.updateUser(about, userId);

		return response;
	}

	@Override
	public User updateUser(User user, int userId) {

		// Update data with RestTemplate (providing data with @PathVariable &
		// @RequestBody)

		logger.info("=================inside updateUser=================" + user+"========"+userId);
		
		Ratings ratings = Ratings.builder().feedback("very good").rating("5").build();

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		HttpEntity<Ratings> requestEntity = new HttpEntity<Ratings>(ratings, new HttpHeaders(headers));

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("ratingId", 3);

		ResponseEntity<Ratings> updatedRating = restTemplate.exchange("http://RATING-APP/rating/update/{ratingId}",
				HttpMethod.PUT, requestEntity, Ratings.class, map);
		logger.info("===============updatedRating in updateUser()==================" + updatedRating.getBody());
		List<Ratings> list = new ArrayList<Ratings>();
		list.add(updatedRating.getBody());

		User[] updatedUser = { null };
		userRepository.findById(String.valueOf(userId)).ifPresentOrElse(userObj -> {
			logger.info("===============existing userObj in updateUser()==================" + userObj);
			userObj.setAbout(user.getAbout());
			userObj.setEmail(user.getEmail());
			userObj.setName(user.getName());
			updatedUser[0] = userRepository.save(userObj);
			logger.info("===============updatedUser object in updateUser()==================" + updatedUser[0]);

		}, () -> {
			throw new ResourceNotFoundException("user with given id not found on server : " + userId);
		});

		updatedUser[0].setRatings(list);
		return updatedUser[0];
	}

	public String updateWithRest(Ratings rating) {

		// Update data with RestTemplate providing data with @RequestBody

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Ratings> httpEntity = new HttpEntity<Ratings>(rating, headers);

		ResponseEntity<String> entity = restTemplate.exchange("http://RATING-APP/rating/update", HttpMethod.POST,
				httpEntity, String.class);

		if (entity.getStatusCode().is2xxSuccessful()) {
			return entity.getBody();
		}
		return "success false";
	}

	public String updateHotelWithFeign(Hotel hotel) {
		String response =  hotelService.updateHotel(hotel);
		return response;
	}
	
}
