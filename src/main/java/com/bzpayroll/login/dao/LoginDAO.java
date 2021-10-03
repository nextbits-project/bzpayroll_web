package com.bzpayroll.login.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public interface LoginDAO {
	public String checkUserRole(String username, String password,String companyid,HttpServletRequest request);
	public boolean checkUserLogin(String username, String password,String companyid,HttpServletRequest request);
	public boolean checkUserLoginforCom(String username, String password,int companyid,HttpServletRequest request);
	ArrayList getAllUserlist(HttpServletRequest request);
}
