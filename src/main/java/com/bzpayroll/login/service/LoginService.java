package com.bzpayroll.login.service;

import java.util.List;

import com.bzpayroll.login.entity.BcaCities;
import com.bzpayroll.login.entity.BcaCountries;
import com.bzpayroll.login.entity.BcaStates;
import com.bzpayroll.login.entity.BcaUser;
import com.bzpayroll.login.forms.LoginForm;
import com.bzpayroll.login.forms.MultiUserForm;

public interface LoginService {

	public List<BcaUser> checkUserRoleList(LoginForm loginObj);
	public String checkUserRole(LoginForm loginObj);
	public MultiUserForm  checkUserLogin(LoginForm loginObj,String userRole);
	public List<LoginForm> getAllCompany(String userRole);
	public List<LoginForm> getCompanyDetails();
	public List<LoginForm> getCompanyDetails2();
	public List<BcaUser> findByUserName(String username);
	public String checkUserRole(BcaUser user);
	public List<BcaUser> checkUserLogin(String username  ,String password);
	public List<BcaCountries> getCountryDetails();
	public List<BcaStates> getStateDetails(Integer countryId);
	public List<BcaCities> getCityDetails(Integer stateId);

}
