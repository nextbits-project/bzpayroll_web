/*
 * Author : Avibha IT Solutions Copyright 2006 Avibha IT Solutions. All rights
 * reserved. AVIBHA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.avibha.com
 */

package com.bzpayroll.dashboard.employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bzpayroll.common.db.SQLExecutor;
import com.bzpayroll.common.log.Loger;
import com.bzpayroll.common.utility.LabelValueBean;

@Service
public class EmployeeType {

	@Autowired
	private SQLExecutor db;
	
	/*
	 * Put method comments
	 */
	public ArrayList getEmployeeTypeList(String CompanyID) {
		ArrayList<LabelValueBean> arr = new ArrayList<LabelValueBean>();

		Connection con = null ;
		PreparedStatement pstmt = null;
		 
		ResultSet rs = null;
		if (db == null)
			arr = null;
		con = db.getConnection();

		if (con == null)
			arr = null;

		try {
			String sqlString = "select EmployeeTypeID,EmployeeType from bcp_employeetype where CompanyID=? and Active=?";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, CompanyID);
			pstmt.setString(2, "1");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				arr.add(new LabelValueBean(rs
						.getString("EmployeeType"), rs
						.getString("EmployeeTypeID")));
			}

		} catch (SQLException ee) {
			Loger.log(2, "Error in  method - getEmployeeTypeList " + " "
					+ ee.toString());
		}finally {
			try {
				if (rs != null) {
					db.close(rs);
					}
				if (pstmt != null) {
					db.close(pstmt);
					}
					if(con != null){
					db.close(con);
					}
				} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return arr;
	}

	public String getEmployeeType(String empTypeID) {
		String empType = null;
		Connection con = null ;
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		if (db == null)
			empType = null;
		con = db.getConnection();

		if (con == null)
			empType = null;
		try {
			String sqlString = "select EmployeeType from bcp_employeetype where EmployeeTypeID=? ";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, empTypeID);
			rs = pstmt.executeQuery();
			if (rs.next())
				empType = rs.getString(1);
		} catch (SQLException ee) {
			Loger.log(2, "Error in  Class Title and  method -getEmployeeType"
					+ " " + ee.toString());
		} finally {
			try {
				if (rs != null) {
					db.close(rs);
					}
				if (pstmt != null) {
					db.close(pstmt);
					}
					if(con != null){
					db.close(con);
					}
				} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return empType;
	}

}
