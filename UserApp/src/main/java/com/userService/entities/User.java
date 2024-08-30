package com.userService.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String userId;
	private String name;
	private String email;
	private String about;
	
	@Transient
	private List<Ratings> ratings =new ArrayList<Ratings>();

}


//@Transient annotation is used to indicate that a particular field of a class should not be persisted in the database. 
//This annotation is applied to fields or properties of an entity class and tells the persistence provider to ignore that 
//field when performing persistence operations.
//Fields that are part of business logic but not part of the database schema can be annotated with @Transient.