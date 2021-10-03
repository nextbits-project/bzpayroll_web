/*
 * Author : Avibha IT Solutions Copyright 2006 Avibha IT Solutions. All rights
 * reserved. AVIBHA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.avibha.com
 *  
 */
package com.bzpayroll.common.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bzpayroll.common.db.SQLExecutor;
import com.bzpayroll.common.log.Loger;
import com.bzpayroll.dashboard.file.forms.CompanyInfoForm;
import com.bzpayroll.sales.forms.CustomerDto;

@Service
public class CountryState {

	@Autowired
	private SQLExecutor db;
	
	private String id = null;

	private String name = null;

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) 
	{
		this.name = name;
	}

	public ArrayList getCountry() {
		ArrayList<LabelValueBean> cList = new ArrayList<LabelValueBean>();
 		Connection con = db.getConnection();
		ResultSet rs = null;
		try {

			String sqlString = "select *  from  country ORDER BY CountryName";
			PreparedStatement pstmt = con.prepareStatement(sqlString);
			rs = pstmt.executeQuery();
			LabelValueBean defaultCountry = null;
			while (rs.next()) {
				if(defaultCountry == null && rs.getString("CountryName").equalsIgnoreCase("United States")){
					defaultCountry = new LabelValueBean(rs.getString("CountryName"), rs.getString("CountryID"));
				}else{
					cList.add(new LabelValueBean(rs.getString("CountryName"), rs.getString("CountryID")));
				}
			}
			if(defaultCountry != null){
				cList.add(0, defaultCountry);
			}
		} catch (SQLException ee) {
			Loger.log("Error in CountryState class and method:getCountry:  " + ee);
		} finally {
			db.close(con);
		}
		return cList;
	}
	
	public ArrayList getStates(String cid) 
	{
		ArrayList<CountryState> sList = new ArrayList<CountryState>();
 		Connection con = db.getConnection();
		ResultSet rs = null;
		try {

			/*String sqlString = "select *  from state where CountryID = ? ";*/
			String sqlString = "select * from state where CountryID = ? ";
			PreparedStatement pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, cid);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				CountryState cs = new CountryState();
				cs.setId(rs.getString("StateID"));
				cs.setName(rs.getString("StateName"));
				sList.add(cs);
			}

		} catch (SQLException ee) {
			Loger.log("Error in State Name Selection:  " + ee);
		} finally {
			db.close(con);
		}
		return sList;
	}


	public ArrayList getCStates(String cid)
	{
		ArrayList<CountryStateDto> sList = new ArrayList<CountryStateDto>();
 		Connection con = db.getConnection();
		ResultSet rs = null;
		try {

			/*String sqlString = "select *  from state where CountryID = ? ";*/
			String sqlString = "select * from state where CountryID = ? ";
			PreparedStatement pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, cid);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				CountryStateDto cs = new CountryStateDto();
				cs.setId(rs.getString("StateID"));
				cs.setName(rs.getString("StateName"));
				sList.add(cs);
			}

		} catch (SQLException ee) {
			Loger.log("Error in State Name Selection:  " + ee);
		} finally {
			db.close(con);
		}
		return sList;
	}
	
	//Added on 06-05-2020
	public ArrayList getStatesNew(String cid) 
	{
		ArrayList<CompanyInfoForm> sList = new ArrayList<CompanyInfoForm>();
 		Connection con = db.getConnection();
		ResultSet rs = null;
		try {

			/*String sqlString = "select ZIP_CODE,CITY_NAME from city_state_zip where STATE_NAME = ? ";*/
			String sqlString = "select ZIP_CODE,STATE_NAME,CITY_NAME from city_state_zip where ZIP_CODE = ? ";
			PreparedStatement pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, cid);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				CompanyInfoForm customer = new CompanyInfoForm();
				customer.setStateName(rs.getString(2));
				customer.setCity(rs.getString(3));
				sList.add(customer);
				//request.setAttribute("state_gen", rs.getString(2));
			}

		} catch (SQLException ee) {
			Loger.log("Error in State Name Selection:" + ee);
		} finally {
			db.close(con);
		}
		return sList;
	}
	
	//Added on 08-05-2020
	public String[] getCityState(String cid) 
	{
		String city = "",state = "";
 		Connection con = db.getConnection();
		ResultSet rs = null;
		try {

			String sqlString = "select ZIP_CODE,STATE_NAME,CITY_NAME from city_state_zip where ZIP_CODE = ? ";
			PreparedStatement pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, cid);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				state = rs.getString(2);
				city = rs.getString(3);
			}

		} catch (SQLException ee) {
			Loger.log("Error in State Name Selection:" + ee);
		} finally {
			db.close(con);
		}
		String[] values = {city,state};
		return values;
	}
	
	public ArrayList getCountryNew() {
		//ArrayList<LabelValueBean> cList = new ArrayList<LabelValueBean>();
		ArrayList<CompanyInfoForm> cList = new ArrayList<CompanyInfoForm>();
 		Connection con = db.getConnection();
		ResultSet rs = null;
		try {

			String sqlString = "select *  from  country ";
			PreparedStatement pstmt = con.prepareStatement(sqlString);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				 /*cList.add(new org.apache.struts.util.LabelValueBean(rs
						.getString("CountryName"), rs.getString("CountryID")));*/
				CompanyInfoForm customer = new CompanyInfoForm();
				customer.setCountryId(rs.getInt(1));
				customer.setCountry(rs.getString(2));
				cList.add(customer);
			}

		} catch (SQLException ee) {
			Loger.log("Error in CountryState class and method:getCountry:" + ee);
		} finally {
			db.close(con);
		}
		return cList;
	}
	// Added on 07-05-2020
	public int getStatesId(String sName) {
		int sId = 0;
 		Connection con = db.getConnection();
		ResultSet rs = null;
		try {

			String sqlString = "select StateID from state where StateName = ? ";
			PreparedStatement pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, sName);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				sId = rs.getInt(1);
			}

		} catch (SQLException ee) {
			Loger.log("Error in getStatesId:" + ee);
		} finally {
			db.close(con);
		}
		return sId;
	}
	
	public String getStatesName(String sid) {
		String sname = "";
 		Connection con = db.getConnection();
		ResultSet rs = null;
		try {

			String sqlString = "select StateName from state where StateID = ? ";
			PreparedStatement pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, sid);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				
				if(rs.getString(1).equals("California"))
				{
					sname = "CA";
				}
				else
				{
					sname = rs.getString(1);
					//System.out.println("State Name:"+sname);
				}
				//sname = rs.getString(1);
			}

		} catch (SQLException ee) {
			Loger.log("Error in State Name Selection:  " + ee);
		} finally {
			db.close(con);
		}
		return sname;
	}

	public String getCountryName(String cid) 
	{
		String cname = "";
 		Connection con = db.getConnection();
		ResultSet rs = null;
		try {

			String sqlString = "select CountryName from country where CountryID = ? ";
			PreparedStatement pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, cid);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				cname = rs.getString(1);
			}

		} catch (SQLException ee) {
			Loger.log("Error in Country Name Selection:  " + ee);
		} finally {
			db.close(con);
		}
		return cname;
	}
	
	
	public ArrayList getCountryBean() 
	{
		ArrayList<CountryState> cList = new ArrayList<CountryState>();
 		Connection con = db.getConnection();
		ResultSet rs = null;
		try {
			String sqlString = "select *  from  country ";
			PreparedStatement pstmt = con.prepareStatement(sqlString);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CountryState cs=new CountryState();
				cs.setId(rs.getString("CountryID"));
				cs.setName(rs.getString("CountryName"));
				cList.add(cs);
			}
		} catch (SQLException ee) {
			Loger.log("Error in Country getCountry:  " + ee);
		} finally {
			db.close(con);
		}
		return cList;
	}

	public CustomerDto getAddressDetailsByZipcode(String zipcode) {
 		Connection con = db.getConnection();
		ResultSet rs = null;
		CustomerDto customerDto = null;
		try {
			String sqlString = "SELECT cs.ZIP_CODE, cs.CITY_NAME, cs.STATE_NAME, s.StateID, s.CountryID FROM city_state_zip AS cs "
					+ " INNER JOIN state AS s ON s.StateName=cs.STATE_NAME WHERE ZIP_CODE = ? LIMIT 1;";
			PreparedStatement pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, zipcode);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				customerDto = new CustomerDto();
				customerDto.setZipCode(rs.getString("ZIP_CODE"));
				customerDto.setCity(rs.getString("CITY_NAME"));
				customerDto.setStateName(rs.getString("STATE_NAME"));
				customerDto.setState(rs.getString("StateID"));
				customerDto.setCountry(rs.getString("CountryID"));
			}
		} catch (SQLException ee) {
			Loger.log("Error in State Name Selection:" + ee);
		} finally {
			db.close(con);
		}
		return customerDto;
	}

}
