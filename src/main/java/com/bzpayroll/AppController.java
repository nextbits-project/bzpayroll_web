package com.bzpayroll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AppController {

	 //@GetMapping(value = "/login")
	 //   public String showLogin() {
		// return "login";
	   // }
	 
	 @GetMapping(value = "/forgotPassword")
	    public String showForgotPassword() {
		 return "forgotPassword";
	    }
	 
	 @GetMapping(value = "/createCompany")
	    public String createCompany() {
		 return "createCompany";
	    }
	 
		/*
		 * @GetMapping(value = "/register") public String showRegister() { return
		 * "register"; }
		 */		/*
		 * @GetMapping(value = "/dashboard") public String showDashboard() { return
		 * "/include/dashboard"; }
		 */
}
