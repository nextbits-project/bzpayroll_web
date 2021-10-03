package com.bzpayroll.register.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.bzpayroll.login.forms.MultiUserForm;
import com.bzpayroll.register.form.ZipDTO;

public interface RegisterService {
	
	public boolean isEmailExists(String emailId,HttpServletRequest request);
	public boolean checkLoginIdExists(String loginId,HttpServletRequest request);
	public int addUserInformation(MultiUserForm multiUserObj);
	public ZipDTO getStateCityByZipCode(String zipcodeStr);
	public MultiUserForm getRecoveryDetails(String userName);

}
