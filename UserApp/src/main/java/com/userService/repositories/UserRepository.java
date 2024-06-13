package com.userService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.userService.entities.User;

//@Repository
public interface UserRepository extends JpaRepository<User, String> {

	// @Query("select u from User u where u.email = ?1")
	@Query("select u from User u where u.email = :email")
	User findUserWithEmailAdd(@Param(value = "email") String email);

	
	
	@Transactional
	@Modifying
	@Query("update User u set u.about = :about where u.userId = :userId")
	void updateUser(@Param(value = "about") String about, @Param(value = "userId") int userId);

	
	@Transactional
	@Modifying
	@Query("delete from User u where u.userId = :userId")
	int deleteUserWithQuery(@Param(value ="userId") int userId); 
	
	
	//Custom finder
	public User findByNameAndUserId(String name, int userId);
}
