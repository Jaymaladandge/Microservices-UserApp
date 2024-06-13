package com.userService.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Ratings {

	private int ratingId;
	private int userId;
	private int hotelId;
	private String rating;
	private String feedback;
	private Hotel hotel;
}
