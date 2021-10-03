package com.bzpayroll.register.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bzpayroll.login.entity.BcaUser;
import com.bzpayroll.login.entity.BcaUsermapping;

@Repository
@Transactional
public interface RegisterRepository extends CrudRepository<BcaUser, String> ,JpaRepository<BcaUser, String> {
	@Query(name = "User.checkDuplicateEmailQuery") 
	List<Object[]> fetchExistingEmail(@Param("emailId") String emailId );
	
	@Query(name = "User.findByDuplicateUserQuery") 
	List<Object[]> fetchDuplicateUserQuery(@Param("loginId") String loginId);

	
	//@Query(name = "User.fetchCityStateByZipCodeQuery") 
	//List<Object[]> fetchCitybyZipCode(@Param("zipcode") int zipcode);
	
	@Query(name = "User.fetchCityStateByZipCodeQuery", nativeQuery = true) 
	List<Object[]> fetchCitybyZipCode(@Param("zipcode") int zipcode);
	
	@Query(name = "User.fetchRecoverUserPassword") 
	List<BcaUser> fetchRecoveryDetails(@Param("userName") String userName);

}

