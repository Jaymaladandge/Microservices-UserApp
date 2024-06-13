package com.userService.external.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import com.userService.entities.Hotel;

@FeignClient("HOTEL-APP")
public interface HotelService {

	
	@PostMapping("/hotel/update")
	String updateHotel(Hotel hotel);
}
