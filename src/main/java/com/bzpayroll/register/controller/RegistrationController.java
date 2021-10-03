package com.bzpayroll.register.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
	
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.bzpayroll.login.forms.MultiUserForm;
import com.bzpayroll.login.forms.MultiUserFormValidator;
import com.bzpayroll.login.service.LoginService;
import com.bzpayroll.register.form.City;
import com.bzpayroll.register.form.Country;
import com.bzpayroll.register.form.RegisterDTO;
import com.bzpayroll.register.form.State;
import com.bzpayroll.register.form.ZipDTO;
import com.bzpayroll.register.service.RegisterService;

@Controller
public class RegistrationController {

	@Autowired
	private RegisterService registerService;
	@Autowired
	private LoginService loginService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/addUserMember", method = RequestMethod.POST)
	public String addNewUser(@ModelAttribute("registerDTO") RegisterDTO registerDTO, HttpServletRequest request, Model model) {

		MultiUserForm multiuserObj = setMultiUserObject(registerDTO);
		Locale locale = LocaleContextHolder.getLocale();
		MultiUserFormValidator validator = new MultiUserFormValidator();
		if (validator.isMultiUserValidValue(multiuserObj, messageSource, request)
				|| registerService.isEmailExists(multiuserObj.getEmailAddress(), request)
				|| registerService.checkLoginIdExists(multiuserObj.getUserName(), request)) {
            model.addAttribute("countryList", loginService.getCountryDetails());
			model.addAttribute("registerDTO", registerDTO);
			return "register";
		}
		int userId = registerService.addUserInformation(multiuserObj);
		if (userId <= 0) {
			model.addAttribute("countryList", loginService.getCountryDetails());
			model.addAttribute("registerDTO", registerDTO);
			request.setAttribute("errorMsg", messageSource.getMessage("err.registration.error", new Object[] {}, locale));
			return "register";
		}
		request.setAttribute("successMsg", messageSource.getMessage("err.registration.success", new Object[] {}, locale));
		boolean isEmailSent = sendEmailOnSuccessRegister(request, multiuserObj.getEmailAddress(), multiuserObj.getUserName());
		if (isEmailSent) {
			request.getSession().setAttribute("username", multiuserObj.getFirstName()+" "+multiuserObj.getLastName());
			return "welcomeUser";
		} else {
			return "login";
		}

	}

	private List<City> populateCities(List<MultiUserForm> muFormList) {
		List<City> cityList = new ArrayList<>();
		for (MultiUserForm mForm : muFormList) {
			City city = new City();
			city.setCityName(mForm.getCity());
			cityList.add(city);
			// logger.info("City :"+city.getCityName());
		}
		return cityList;

	}

	private List<State> populateStates(List<MultiUserForm> multiUserFormList) {
		List<State> stateList = new ArrayList<>();
		for (MultiUserForm mForm : multiUserFormList) {
			State state = new State();
			state.setStateId(mForm.getStateId());
			state.setState(mForm.getState());
			stateList.add(state);
		}
		return stateList;

	}

	private List<Country> populateCountries(List<MultiUserForm> multiUserFormList) {
		List<Country> countryList = new ArrayList<>();

		for (MultiUserForm mForm : multiUserFormList) {
			Country country = new Country();
			country.setCountryId(mForm.getCountryId());
			country.setCountryName(mForm.getCountry());
			country.setPhoneCode(String.valueOf(mForm.getPhonecode()));

			// logger.info("Country is :"+country.toString());

			countryList.add(country);
		}
		return countryList;
	}

	private MultiUserForm setMultiUserObject(RegisterDTO regDTO) {
		MultiUserForm multiUserObj = new MultiUserForm();
		multiUserObj.setMembershipLevel(regDTO.getMembership_levelDr());
		multiUserObj.setFirstName(regDTO.getFirst_name());
		multiUserObj.setLastName(regDTO.getLast_name());
		multiUserObj.setEmailAddress(regDTO.getEmail());
		multiUserObj.setJobPosition(regDTO.getPositionDr());

		multiUserObj.setCompanyName(regDTO.getCompany_name());
		multiUserObj.setUserName(regDTO.getUser_name());
		multiUserObj.setAddress1(regDTO.getAddress_street());
		multiUserObj.setAddress2(regDTO.getAddress_street2());
		multiUserObj.setZip(regDTO.getAddress_zipcode());
		multiUserObj.setCity(regDTO.getAddress_city());
		multiUserObj.setState(regDTO.getAddress_stateDr());
		multiUserObj.setCountry(regDTO.getCountryDr());
		String mobileNumeric = regDTO.getPhone().replaceAll("[^0-9.]", "");
		multiUserObj.setPhone(mobileNumeric);
		multiUserObj.setCellPhone(regDTO.getMobile());
		multiUserObj.setFax(regDTO.getFax());
		multiUserObj.setTaxID(regDTO.getTaxId());

		multiUserObj.setPassword(regDTO.getPassword());
		multiUserObj.setConfirmPassword(regDTO.getPassword_re());
		//multiUserObj.setLoginId(regDTO.getLoginName());
		//multiUserObj.setPasswordhint(regDTO.getPasswordhintDr());
		//multiUserObj.setPasswordAns(regDTO.getPasswordAns());
		return multiUserObj;
	}

	@ResponseBody
	@RequestMapping(value = "/RestApi", method = {RequestMethod.GET, RequestMethod.POST})
	public Object getRestApisData(HttpServletRequest request) {
		String action = request.getParameter("tabid");
		if(action.equalsIgnoreCase("getAddressDetailsByZipcode")) {
			String zipcode = request.getParameter("zipcode");
			return registerService.getStateCityByZipCode(zipcode);
		}
		else if(action.equalsIgnoreCase("loadStatesByCountryID")) {
			Integer countryId = Integer.parseInt(request.getParameter("countryId"));
			return loginService.getStateDetails(countryId);
		}
		else if(action.equalsIgnoreCase("loadCitiesByStateID")) {
			Integer stateId = Integer.parseInt(request.getParameter("stateId"));
			return loginService.getCityDetails(stateId);
		}else{
			return "Success";
		}
	}
	
	 
	 @RequestMapping(value = "/recover", method = RequestMethod.GET)
	    public String showRegister(ModelMap model, @RequestParam(name = "textfieldVal") String textfieldVal) {
		 System.out.println("Inside recover condition");
		 System.out.println("Added email:: " + textfieldVal);
		 MultiUserForm recoveryObj =registerService.getRecoveryDetails(textfieldVal);
		 
		 if(null!=recoveryObj ) {
			 sendMail(recoveryObj); 
			 model.addAttribute("recoveryObj", recoveryObj);
			 return "login";
		 }else {
			 model.put("errorMessage", "Something unexpected happened,please contact your Administrator");
			 return "forgotPassword";
		 }
		
	    }

	private boolean sendEmailOnSuccessRegister(HttpServletRequest request, String emailAddress, String Username) {
		String email = emailAddress;
		String to = email;
		boolean isEmailSent = false;

		final String authAddress = "support@bzpayroll.com";
		final String authPassword = "Bizpayroll@956";
		String smtpServer = "mail.bzpayroll.com";
		String smtpPort = "465";

		 Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", "localhost");
	      final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	      // Get a Properties object
	         Properties props = System.getProperties();
	         props.put("mail.smtp.host", smtpServer);
	         props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	         props.setProperty("mail.smtp.socketFactory.fallback", "false");
	         props.put("mail.smtp.port", smtpPort);
	         props.setProperty("mail.smtp.socketFactory.port", "465");
	         props.put("mail.smtp.auth", true);
	         props.put("mail.debug", true);
	         props.put("mail.store.protocol", "pop3");
	         props.put("mail.transport.protocol", "smtp");
	         

			Session session = Session.getInstance(props, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(authAddress, authPassword);
				}
			});
			session.setDebug(true);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(authAddress));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject("BzPayroll - Please verify your account");
			message.setText("We're glad you're here," + to + "\nThank you for registering to BzPayroll.\n"
					+ "If you still cannot verify, please copy this link and paste it in your browser :<a href='https://bzpayroll.com/login?tabid=confirmSubscribe?address="
					+ to + "'></a>");

			Transport.send(message);
			isEmailSent = true;
			// forward = "/successContact";
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
			// forward = "/successContactError";
		}
		return isEmailSent;
	}
	
	private void sendMail(MultiUserForm multiUserObj) {
		
		String subject="Password Information from BzPayroll";
		String strMailType= "html";
		
		String messageBody = "Hello, <br/>Below is your login info for the BzPayroll";
		messageBody += "<br/><b>Your User Id:</b>&nbsp; " + multiUserObj.getUserName();
		messageBody += "<br/><b>Password:</b>&nbsp; " + multiUserObj.getPassword();
		messageBody += "<br/><br/> Thank you,<br/>Support<br/> BzPayroll";

		final String authAddress = "support@bzpayroll.com";
		final String authPassword = "Bizpayroll@956";
		String smtpServer = "mail.bzpayroll.com";
		String smtpPort = "465";
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		

		try {
			Properties props = new Properties();
			 props.put("mail.smtp.host", smtpServer);
	         props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	         props.setProperty("mail.smtp.socketFactory.fallback", "false");
	         props.put("mail.smtp.port", smtpPort);
	         props.setProperty("mail.smtp.socketFactory.port", "465");
	         props.put("mail.smtp.auth", true);
	         props.put("mail.debug", true);
	         props.put("mail.store.protocol", "pop3");
	         props.put("mail.transport.protocol", "smtp");
			

			// create some properties and get the default Session
			Session sessionMail = Session.getInstance(props, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(authAddress, authPassword);
				}
			});

			sessionMail.setDebug(true);

			// create a message
			Message msg = new MimeMessage(sessionMail);

			if (strMailType.equalsIgnoreCase("text"))
				{
					msg.setContent(messageBody, "text/plain");
				}
			else if (strMailType.equalsIgnoreCase("html"))
				{
					msg.setContent(messageBody, "text/html");
				}

			// set the from and to address
			InternetAddress addressFrom = new InternetAddress(authAddress);
			msg.setFrom(addressFrom);

			InternetAddress[] addressTo = new InternetAddress[1];
			addressTo[0] = new InternetAddress(multiUserObj.getEmailAddress());
			msg.setRecipients(Message.RecipientType.TO, addressTo);

			msg.setSubject(subject);
			
			Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
