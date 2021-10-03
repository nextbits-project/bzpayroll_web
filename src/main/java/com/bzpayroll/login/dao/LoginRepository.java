package com.bzpayroll.login.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bzpayroll.login.entity.BcaCities;
import com.bzpayroll.login.entity.BcaCountries;
import com.bzpayroll.login.entity.BcaStates;
import com.bzpayroll.login.entity.BcaUser;


@Repository
@Transactional
public interface LoginRepository extends JpaRepository<BcaUser, String> {
	@Query("SELECT bu FROM BcaUser bu  WHERE bu.loginId=(:aUserName)" )
    List<BcaUser> findByUserName(@Param("aUserName") String aUserName);
	
	
	@Query(name = "User.findRoleByEmailIdOrUserNameQuery", nativeQuery = true) 
	List<Object[]> checkUserRole(@Param("userName") String userName,@Param("password") String password );
	
	@Query(name = "User.findByEmailIdOrUserNameQuery") 
	List<BcaUser> checkUserLogin(@Param("userName") String userName,@Param("password") String password );
	
	@Query(name = "User.fetchCompanyListQuery") 
	List<Object[]> fetchCompanyList(@Param("userRole") String userRole );
	
	@Query(name = "User.fetchCompanyDetailsQuery") 
	List<Object[]> fetchCompanyDetails();
	
	@Query(name = "User.fetchCompanyDetails2Query") 
	List<Object[]> fetchCompanyDetails2();
	
	
	@Query(name = "User.fetchCompanyDetailsNativeQuery", nativeQuery = true) 
	List<Object[]> fetchCompanyDetailsNativeQuery();
	
	@Query(name = "User.fetchCountryDetailsQuery") 
	List<BcaCountries> fetchCountryDetails();
	
	@Query(name = "User.fetchStateDetailsQuery") 
	List<BcaStates> fetchStateDetails(@Param("countryId") Integer countryId);
	//List<BcaStates> fetchStateDetails();
	
	@Query(name = "User.fetchCityDetailsQuery") 
	List<BcaCities> fetchCityDetails(@Param("stateId") Integer stateId);
}
