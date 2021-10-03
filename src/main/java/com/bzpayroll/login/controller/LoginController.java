package com.bzpayroll.login.controller;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.bzpayroll.common.ConstValue;
import com.bzpayroll.common.utility.BzPayRollConstants;
import com.bzpayroll.common.utility.Path;
import com.bzpayroll.login.entity.BcaUser;
import com.bzpayroll.login.forms.LoginForm;
import com.bzpayroll.login.forms.MultiUserForm;
import com.bzpayroll.login.service.LoginService;
import com.bzpayroll.register.form.RegisterDTO;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Autowired
	ObjectFactory<HttpSession> httpSessionFactory;
	String forward = BzPayRollConstants.FORWARDSUCCESS;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model,@RequestParam(required = false) String tabid,@RequestParam(required = false) String selectedCompanyId) {
		
		//this will be taken as part of next iteration since login post do not exactly require it
		//Path p = new Path();
		//p.setPathvalue(request.getContextPath());
		//request.getSession().setAttribute("path", p);
		if(null != selectedCompanyId ) {
			ConstValue.setCompanyId(Integer.parseInt(selectedCompanyId));
		}
		
		return "login";
	}

	@RequestMapping(value = "/login111", method = RequestMethod.POST)
	public String showWelcomePage(ModelMap model, @RequestParam String swpm_user_name,
			@RequestParam String swpm_password, @RequestParam(required = false) String rememberme,
			@RequestParam(required = false) String tabid, HttpServletResponse response) {

		HttpSession session = httpSessionFactory.getObject();
		// commenting this path variable as of now since in login module as of now it is
		// not used
		// Path pathObj = new Path();
		// pathObj.setPathvalue(getServletContext);
		
		//Will need in other modules as of now not used so commenting it
		//String cID = request.getParameter("selectedCompanyId");

		String action = tabid;
		LoginForm loginObj = new LoginForm();
		loginObj.setUserName(swpm_user_name);
		loginObj.setUserPassword(swpm_password);
		loginObj.setRememberMe(rememberme);
		
		System.out.println("Entered Username:" + swpm_user_name + "\nPassword:" + swpm_password + "\ntabid:" + tabid);
		if(action.equalsIgnoreCase("chkLoginDetails")){

			// this userList is newly introduced to optimize the use of variable,set
			// username,password and status in one Object loginObj
			List<BcaUser> userList = loginService.checkUserRoleList(loginObj);
			String userRole = loginService.checkUserRole(loginObj);
			if (userList.size()!=0 && userRole.equalsIgnoreCase(BzPayRollConstants.USERROLESUPERADMIN)) {

				MultiUserForm multiUserObj = loginService.checkUserLogin(loginObj, userRole);

				List<LoginForm> companyList = loginService.getAllCompany(userRole);
				session.setAttribute("cList", companyList);

				System.out.println("companyList::" + companyList.size());
				//this jsp will be devloped in the next iteration,so commenting
				//forward = "/admin/admindashboard";
				forward = "/include/dashboard";
			} else {
				MultiUserForm multiUserObj = loginService.checkUserLogin(loginObj, userRole);
				if (!multiUserObj.isLoginStatus()) {
					if (userList.size() == 0) {
						model.put("errorMessage", "Invalid User Name or Password");
						loginObj.setUserName("");
						loginObj.setUserPassword("");

						forward = BzPayRollConstants.FORWARDLOGIN;

						return forward;
					}
				} else {
					if (forward == "/index") {
						System.out.println("Inside Failure");
						loginObj.setUserName("");
						loginObj.setUserPassword("");
						loginObj.setCompanyId("");
					}

					String companyId = "1";
					session.setAttribute("CID", companyId);
					session.setAttribute("password", loginObj.getUserPassword());

					List<LoginForm> companyDetailsList1 = loginService.getCompanyDetails();
					List<LoginForm> companyDetailsList2 = loginService.getCompanyDetails2();

					session.setAttribute("Email_Address", multiUserObj.getUserName());
					session.setAttribute("LoginID", multiUserObj.getUserId());
					session.setAttribute("userRole", userRole);
					session.setAttribute("Role", userRole);
					session.setAttribute("CID", String.valueOf(multiUserObj.getCompanyID()));
					session.setAttribute("user", multiUserObj.getCompanyName());
					session.setAttribute("userID", multiUserObj.getUserId());
					session.setAttribute("membershipLevel", multiUserObj.getMembershipLevel());
					session.setAttribute("username", multiUserObj.getFirstName() + " " + multiUserObj.getLastName());

					model.addAttribute("acList", companyDetailsList1);
					model.addAttribute("acList2", companyDetailsList2);
					setCookiesForRememberMe(rememberme, loginObj, response);

					System.out.println("Membership Level:" + session.getAttribute("membershipLevel"));

					if (session.getAttribute("membershipLevel") != null
							&& session.getAttribute("membershipLevel").equals("standard")) {
						System.out.println("Inside checking membershipLevel session and value is:"
								+ session.getAttribute("membershipLevel"));
						forward="/standardDashboard";
					} else {
						//forward="/welcomescreen";
						//this has been commented for welcomescreen.jsp coimn in picture,yet to develop
						forward="redirect:/dashboard?tabid=Dashboard";
					}
				}
			}
		}
			

		 if(action.equalsIgnoreCase("loginPage")) {
			System.out.println("Going Towards loginPage..");
			List<LoginForm> companyDetailsList1 = loginService.getCompanyDetails();
			model.addAttribute("acList", companyDetailsList1);
			
			forward = "/loginPage1";
		}

		

		return forward;
	}

	private void setCookiesForRememberMe(String remembermeStr, LoginForm loginObj, HttpServletResponse response) {
		String userName = loginObj.getUserName();
		String password = loginObj.getUserPassword();
		boolean rememberMe = false;

		if (null != remembermeStr) {
			rememberMe = remembermeStr.equals("on") ? true : false;
		}

		Cipher cipher = null;
		KeyGenerator keyGenerator;
		SecretKey secretKey = null;
		String decryptedText = null;
		String encryptedText = null;

		try {
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // SunJCE provider AES algorithm, mode(optional) and padding schema(optional)

		try {
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128); // block size is 128bits
			secretKey = keyGenerator.generateKey();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] plainTextByte = password.getBytes();
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptedByte = null;
			try {
				encryptedByte = cipher.doFinal(plainTextByte);
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Base64.Encoder encoder = Base64.getEncoder();
			encryptedText = encoder.encodeToString(encryptedByte);

			Base64.Decoder decoder = Base64.getDecoder();
			byte[] encryptedTextByte = decoder.decode(encryptedText);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decryptedByte = null;
			try {
				decryptedByte = cipher.doFinal(encryptedTextByte);
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			decryptedText = new String(decryptedByte);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (remembermeStr != null && remembermeStr.equalsIgnoreCase("on")) {
			loginObj.setRememberMe("on");
			rememberMe = true;
		}

		if (rememberMe) {
			// If your checkbox value is true
			Cookie cookieUsername = new Cookie("cookieLoginUser", loginObj.getUserName());
			Cookie cookiePassword = new Cookie("cookieLoginPassword", encryptedText);
			Cookie cookiePassword1 = new Cookie("cookieLoginPassword1", decryptedText);
			Cookie cookieRememberMe = new Cookie("cookieRememberMe", "on");

			// Make the cookie 15 days last
			cookieUsername.setMaxAge(60 * 60 * 24 * 15);
			cookiePassword.setMaxAge(60 * 60 * 24 * 15);
			cookiePassword1.setMaxAge(60 * 60 * 24 * 15);
			cookieRememberMe.setMaxAge(60 * 60 * 24 * 15);
			response.addCookie(cookieUsername);
			response.addCookie(cookiePassword);
			response.addCookie(cookiePassword1);
			response.addCookie(cookieRememberMe);
		} else {
			loginObj.setRememberMe("off");
			Cookie cookieRememberMe = new Cookie("cookieRememberMe", "off");
			cookieRememberMe.setMaxAge(60 * 60 * 24 * 15);
			response.addCookie(cookieRememberMe);
		}
	}
	
	 @RequestMapping(value = "/register", method = RequestMethod.GET)
	 public String showRegister(RegisterDTO registerDTO, Model model) {
		 System.out.println("Inside Register condition");
		 Integer countryId = 231;
		 Integer stateId = 3924;
		 registerDTO.setCountryDr(countryId+"");
		 registerDTO.setAddress_stateDr(stateId+"");

		 model.addAttribute("countryList", loginService.getCountryDetails());
		 model.addAttribute("stateList", loginService.getStateDetails(countryId));
		 model.addAttribute("cityList", loginService.getCityDetails(stateId));
		 model.addAttribute("registerDTO", registerDTO);
		 return "register";
	 }

	@RequestMapping(value = "/Logout")
	public String showLogoutPage() {
		HttpSession session  = httpSessionFactory.getObject();
		session.removeAttribute("CID");
		session.removeAttribute("path");
		session.removeAttribute("username");
		session.removeAttribute("user");
		session.removeAttribute("org.apache.struts.action.LOCALE");
		session.invalidate();
		return "redirect:/";
	}
}
