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

/*
The @Modifying annotation is used to enhance the @Query annotation so that we can execute not only SELECT queries, 
but also INSERT, UPDATE, DELETE, and even DDL queries.
Methods using @Modifying should be executed within a transactional context, which is why the @Transactional annotation 
is often used. This ensures the operation is executed as part of a transaction.

In many cases, when using JpaRepository or CrudRepository from Spring Data JPA, you don't need to explicitly annotate 
your repository methods with @Transactional. This is because Spring Data JPA provides transaction management by default 
for these repository methods. However, there are some cases where you might still need to use @Transactional.
Custom Queries, Multiple Operations in a Service Method :If you have multiple repository operations within a single 
service method that you want to execute in a single transaction, you should annotate the service method with 
@Transactional., Custom Transaction Settings needs.
*/


//In Custom finder we just need to follow method convention. method starts with findBy and follow by property name.

//Spring uses its @Repository annotation to automatically translate persistence-related exceptions into Spring's own 
//DataAccessException hierarchy. This means that regardless of the underlying data access technology (JPA, JDBC, Hibernate etc.), 
//you can handle data access exceptions in a consistent manner.
//When a @Repository annotated class throws a database-specific exception, Spring catches it and converts it into a more generic DataAccessException.
//Spring's exception translation mechanism intercepts it and translates it.
//BadSqlGrammarException : Thrown when SQL syntax errors occur.
//DataIntegrityViolationException (ConstraintViolationException in Hibernate): Violations of database constraints, such as unique constraints or foreign key constraints.
//DuplicateKeyException :  Inserting a duplicate key in a table with a unique constraint. etc





//No Need for @Repository. when you extend JpaRepository, this is handled by the Spring Data infrastructure. The proxy 
//that Spring Data creates for the repository interface is already marked as a repository 
//and will participate in exception translation.




//When a method is indicated with @Transactional annotation, it indicates that the particular method should be executed 
//within the context of that transaction. If the transaction becomes successful then the changes made to the database 
//are committed, if any transaction fails, all the changes made to that particular transaction can be rollback and it 
//will ensure that the database remains in a consistent state.

//Note: To use @Transactional annotation, you need to configure transaction management by using @EnableTransactionManagement 
//to your main class of Spring Boot application.
// By default, only unchecked exceptions (subclasses of RuntimeException) trigger a rollback. 
//You can customize this behavior using the rollbackFor and noRollbackFor attributes.