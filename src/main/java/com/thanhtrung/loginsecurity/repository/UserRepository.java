package com.thanhtrung.loginsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.thanhtrung.loginsecurity.entity.User;
import com.thanhtrung.loginsecurity.entity.UserStatus;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	public boolean existsByUserName(String userName);

	public boolean existsByEmail(String email);
	
	@Query("	SELECT 	status 		"
			+ "	FROM 	User 		"
			+ " WHERE 	email = :email")
	public UserStatus findStatusByEmail(String email);

	public User findByUserName(String name);
	
	public User findByEmail(String email);


}
