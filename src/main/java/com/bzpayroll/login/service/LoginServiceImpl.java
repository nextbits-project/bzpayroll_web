package com.bzpayroll.login.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bzpayroll.login.dao.LoginDAO;
import com.bzpayroll.login.dao.LoginRepository;
import com.bzpayroll.login.forms.LoginForm;
import com.bzpayroll.login.forms.MultiUserForm;
import com.bzpayroll.login.entity.*;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDAO loginDAO;

	@Autowired
	private LoginRepository loginRepoObj;

	public List<BcaUser> checkUserRoleList(LoginForm loginObj) {

		String aUserName = loginObj.getUserName();
		String aPassword = loginObj.getUserPassword();

		// String userRole= loginDAO.checkUserRole(loginObj,request);
		List<BcaUser> userList = loginRepoObj.findByUserName(aUserName);

		return userList;
	}


	 public List<BcaUser> findByUserName(String username) {

		List<BcaUser> userList = loginRepoObj.findByUserName(username);

		return userList;
	}

	public String checkUserRole(LoginForm loginObj) {

		String aUserName = loginObj.getUserName();
		String aPassword = loginObj.getUserPassword();
		String userRole = null;
		List<Object[]> userObjList = loginRepoObj.checkUserRole(aUserName, aPassword);

		if (userObjList.size() == 0) {
			System.out.println("No data using this query");
		} else {
			System.out.println("data is present using this query");

			// userObjList.stream().forEach(System.out::println);
			// Stream<String> streamObj = userObjList.stream().map(Object::toString);

			// Optional<String> abc = streamObj.findFirst();
			// userRole=userObjList.get(0).toString();
			for (Object[] obj : userObjList) {
				userRole = (String) obj[0];
			}

			System.out.println("Role is:::" + userRole);

		}
		return userRole;
	}

	public String checkUserRole(BcaUser user) {

		String aUserName = user.getLoginId();
		String aPassword = user.getPassword();
		String userRole = null;
		List<Object[]> userObjList = loginRepoObj.checkUserRole(aUserName, aPassword);

		if (userObjList.size() == 0) {
			System.out.println("No data using this query");
		} else {
			System.out.println("data is present using this query");

			// userObjList.stream().forEach(System.out::println);
			// Stream<String> streamObj = userObjList.stream().map(Object::toString);

			// Optional<String> abc = streamObj.findFirst();
			// userRole=userObjList.get(0).toString();
			for (Object[] obj : userObjList) {
				userRole = (String) obj[0];
			}

			System.out.println("Role is:::" + userRole);

		}
		return userRole;
	}

	public MultiUserForm checkUserLogin(LoginForm loginObj,String userRole) { 
		MultiUserForm multiUserObj = null;
		boolean loginStatus = false;
		
		String aUserName = loginObj.getUserName();
		String aPassword = loginObj.getUserPassword();
		List<BcaUser> userBcaList = loginRepoObj.checkUserLogin(aUserName, aPassword);
		
		multiUserObj=new MultiUserForm();
		
		for(BcaUser bcaUserObj: userBcaList) {
			multiUserObj.setUserName(bcaUserObj.getEmailAddress());
			multiUserObj.setPassword(bcaUserObj.getPassword());
			
			String loginId= bcaUserObj.getLoginId();
			
			if(multiUserObj.getUserName().equalsIgnoreCase(aUserName) && multiUserObj.getPassword().equals(aPassword)) {
				loginStatus=true;
				multiUserObj.setLoginStatus(loginStatus);
			}else if(loginId.equalsIgnoreCase(aUserName)  && multiUserObj.getPassword().equals(aPassword)) {
				loginStatus=true;
				multiUserObj.setLoginStatus(loginStatus);
			}
			multiUserObj.setRole(userRole);
			multiUserObj.setUserId((bcaUserObj.getId()));
			multiUserObj.setLoginId(aUserName);
			multiUserObj.setCompanyID(bcaUserObj.getCompanyId());
			multiUserObj.setCompanyName(bcaUserObj.getCompanyName());
			multiUserObj.setMembershipLevel(bcaUserObj.getMembershipLevel());
			multiUserObj.setFirstName(bcaUserObj.getFirstName());
			multiUserObj.setLastName(bcaUserObj.getLastName());
		}

		return multiUserObj;
	}

	public List<BcaUser> checkUserLogin(String username  ,String password) {
		return loginRepoObj.checkUserLogin(username, password);
 	}

	public List<LoginForm> getAllCompany(String userRole){
		LoginForm loginFormObj=null;
		int companyId=0;
		
		List<LoginForm> companyFormList=new ArrayList<LoginForm>();
		
		List<Object[]> companyList = loginRepoObj.fetchCompanyList(userRole);
		loginFormObj=new LoginForm();
		for (Object[] obj : companyList) {
			companyId = (Integer) obj[0];
			
			String name= (String) obj[1];
			String address1 = (String) obj[2];
			
			loginFormObj.setCompanyId(String.valueOf(companyId));
			loginFormObj.setCompanyName(name);
			loginFormObj.setCompanyAddress(address1);
			companyFormList.add(loginFormObj);
		}
		return companyFormList;
	}
	
	public List<LoginForm> getCompanyDetails(){
		LoginForm loginFormObj=null;
		int companyId=0;
		int businessTypeId=0;
		
		List<LoginForm> companyFormList=new ArrayList<LoginForm>();
		
		List<Object[]> companyList = loginRepoObj.fetchCompanyDetails();
		loginFormObj=new LoginForm();
		for (Object[] obj : companyList) {
			companyId = (Integer) obj[0];
			
			String name= (String) obj[1];
			String companyLogoPath = (String) obj[2];
			companyId = (Integer) obj[3];
			
			loginFormObj.setCompanyId(String.valueOf(companyId));
			loginFormObj.setCompanyName(name);
			loginFormObj.setCompanyLogoPath(companyLogoPath);
			loginFormObj.setBusinessTypeId(businessTypeId);				
			companyFormList.add(loginFormObj);
		}
		return companyFormList;
		
	}
	
	public List<LoginForm> getCompanyDetails2(){
		LoginForm loginFormObj=null;
		int companyId=0;
		int businessTypeId=0;
		
		List<LoginForm> companyFormList=new ArrayList<LoginForm>();
		
		List<Object[]> companyList = loginRepoObj.fetchCompanyDetails2();
		loginFormObj=new LoginForm();
		for (Object[] obj : companyList) {
			companyId = (Integer) obj[0];
			
			String name= (String) obj[1];
			String companyLogoPath = (String) obj[2];
			companyId = (Integer) obj[3];
			
			loginFormObj.setCompanyId(String.valueOf(companyId));
			loginFormObj.setCompanyName(name);
			loginFormObj.setCompanyLogoPath(companyLogoPath);
			loginFormObj.setBusinessTypeId(businessTypeId);				
			companyFormList.add(loginFormObj);
		}
		return companyFormList;
		
	}

	public List<BcaCountries> getCountryDetails(){
		return loginRepoObj.fetchCountryDetails();
	}
	public List<BcaStates> getStateDetails(Integer countryId){
		return loginRepoObj.fetchStateDetails(countryId);
	}
	public List<BcaCities> getCityDetails(Integer stateId){
		return loginRepoObj.fetchCityDetails(stateId);
	}
}
