package com.bzpayroll.register.service;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bzpayroll.login.entity.BcaUser;
import com.bzpayroll.login.entity.BcaUsermapping;
import com.bzpayroll.login.forms.MultiUserForm;
import com.bzpayroll.register.dao.RegisterRepository;
import com.bzpayroll.register.dao.UserMappingRepository;
import com.bzpayroll.register.form.ZipDTO;
import com.bzpayroll.sales.forms.CustomerDto;

@Service
public class RegisterServiceImpl implements RegisterService {
	
	@Autowired
	private RegisterRepository registerRepo;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserMappingRepository userMappingRepo;
		
	public BcaUser createUserMember(BcaUser bcaUserObj) {
		return registerRepo.save(bcaUserObj);
	}

	public boolean isEmailExists(String emailId, HttpServletRequest request) {
		boolean isEmailExists =false;
		Locale locale = LocaleContextHolder.getLocale();
		String email=null;
		List<Object[]> emailObjList = registerRepo.fetchExistingEmail(emailId);
		if (emailObjList.size() == 0) {
			System.out.println("Inside fetch Duplicate email check");
			 isEmailExists =false;
		} else {
			for (Object[] obj : emailObjList) {
				email = (String) obj[0];
			}
			System.out.println("Email is present:::" + email);
			isEmailExists =true;
			request.setAttribute("errorMsg", messageSource.getMessage("err.registration.emailaddress.duplicate", new Object[]{},locale));
		}
		return isEmailExists;	
	}
	
	public boolean checkLoginIdExists(String loginId, HttpServletRequest request) {
		boolean isUserExists = false;
		Locale locale = LocaleContextHolder.getLocale();
		String login = null;
		List<Object[]> loginObjList = registerRepo.fetchDuplicateUserQuery(loginId);
		if (loginObjList.size() == 0) {
			System.out.println("Inside fetchDuplicateUserQuery check");
			isUserExists =false;
		} else {
			for (Object[] logObj : loginObjList) {
				login = (String) logObj[0];
			}
			System.out.println("LoginId is present:::" + login);
			isUserExists =true;
			request.setAttribute("errorMsg", messageSource.getMessage("err.registration.username.duplicate", new Object[]{},locale));
		}
		return isUserExists;
	}

	@Transactional
	public int addUserInformation(MultiUserForm multiUserObj) {
		BcaUser bcaUserObj = new BcaUser();
		bcaUserObj.setLoginId(multiUserObj.getUserName());
		bcaUserObj.setPassword(multiUserObj.getPassword());
		bcaUserObj.setConfirmPassword(multiUserObj.getConfirmPassword());
		bcaUserObj.setEmailAddress(multiUserObj.getEmailAddress());
		bcaUserObj.setCompanyName(multiUserObj.getCompanyName());
		bcaUserObj.setTaxId(multiUserObj.getTaxID());
		bcaUserObj.setAddress1(multiUserObj.getAddress1());
		bcaUserObj.setCity(multiUserObj.getCity());
		bcaUserObj.setState(multiUserObj.getState());
		bcaUserObj.setZip(multiUserObj.getZip());
		bcaUserObj.setCountry(multiUserObj.getCountry());
		bcaUserObj.setPhone(multiUserObj.getPhone());
		bcaUserObj.setCellPhone(multiUserObj.getCellPhone());
		bcaUserObj.setFax(multiUserObj.getFax());
		bcaUserObj.setProvince(multiUserObj.getState());
		bcaUserObj.setFirstName(multiUserObj.getFirstName());
		bcaUserObj.setLastName(multiUserObj.getLastName());
		bcaUserObj.setMembershipLevel(multiUserObj.getMembershipLevel());
		bcaUserObj.setJobPosition(multiUserObj.getJobPosition());
		bcaUserObj.setCompanyId(1);
		//bcaUserObj.setPasswordHint(multiUserObj.getPasswordhint());
		//bcaUserObj.setPasswordAns(multiUserObj.getPasswordAns());
		//bcaUserObj.setWebAddress("Testing");
		
		BcaUser bcaUserObjAfterSave = registerRepo.save(bcaUserObj);
		int generatedIdAfterSave = bcaUserObjAfterSave.getId();
		if(bcaUserObjAfterSave.getId()!=0) {
			BcaUsermapping bcaUserMapObj= new BcaUsermapping();
			bcaUserMapObj.setCompanyId(1);
			bcaUserMapObj.setUserGroupId(1);
			bcaUserMapObj.setUserId(generatedIdAfterSave);
			bcaUserMapObj.setRole("User");
			bcaUserMapObj.setActive(1);
			userMappingRepo.save(bcaUserMapObj);
		}
		int userId = bcaUserObjAfterSave.getId();
		return userId;
	}
	
	public ZipDTO getStateCityByZipCode(String zipcodeStr) {
		ZipDTO zipdtoObj = new ZipDTO();
		List<Object[]> zipObjList = registerRepo.fetchCitybyZipCode(Integer.parseInt(zipcodeStr));
		if (zipObjList.size() == 0) {
			System.out.println("Inside getStateCityByZipCode check");
		} else {
			for (Object[] zipObj : zipObjList) {
				zipdtoObj.setZipcode((Integer) zipObj[0]);
				zipdtoObj.setCityName((String) zipObj[1]);
				zipdtoObj.setStateName((String) zipObj[2]);
				zipdtoObj.setStateId((Integer) zipObj[3]);
				zipdtoObj.setCountryId((Integer) zipObj[4]);
				zipdtoObj.setStateCode((String )zipObj[5]);
				zipdtoObj.setCountry((String)zipObj[6]);
			}
		}
		return zipdtoObj;
	}
	
	public MultiUserForm getRecoveryDetails(String userName) {
		
		MultiUserForm multiObj= new MultiUserForm();
		
		List<BcaUser> bcaUserList=registerRepo.fetchRecoveryDetails(userName);
		for(BcaUser bcaUserObj: bcaUserList) {
			multiObj.setUserName(bcaUserObj.getLoginId());	
			multiObj.setPassword(bcaUserObj.getPassword());
			multiObj.setEmailAddress(bcaUserObj.getEmailAddress());
			
		}
		return multiObj;
	}
}
	
