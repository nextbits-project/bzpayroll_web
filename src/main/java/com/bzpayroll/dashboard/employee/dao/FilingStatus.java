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
public class FilingStatus {
	@Autowired
	private SQLExecutor db;/*
	 * 
	 */
	public ArrayList getFilingStatusList(String CompanyID) {
		ArrayList<LabelValueBean> arr = new ArrayList<LabelValueBean>();
		// boolean ret = false;
		Connection con = null ;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		if (db == null)
			arr = null;
		con = db.getConnection();

		if (con == null)
			arr = null;

		try {
			String sqlString = "select FilingStatusID,FilingStatus from bcp_filingstatus where CompanyID=? and Active=?";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, CompanyID);
			pstmt.setString(2, "1");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				arr.add(new LabelValueBean(rs
						.getString("FilingStatus"), rs
						.getString("FilingStatusID")));
			}

		} catch (SQLException ee) {
			Loger.log(2, "Error in  method - getFilingStatusList " + " "
					+ ee.toString());
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

		return arr;
	}

	/*
	 * 
	 */
	public String getFilingStatus(String filingStatusID) {
		String Title = null;
		Connection con = null ;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		if (db == null)
			Title = null;
		con = db.getConnection();

		if (con == null)
			Title = null;
		try {
			String sqlString = "select FilingStatus from bcp_filingstatus  where FilingStatusID=? ";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, filingStatusID);
			rs = pstmt.executeQuery();
			if (rs.next())
				Title = rs.getString(1);
		} catch (SQLException ee) {
			Loger.log(2, "Error in  Class Title and  method -getFilingStatus "
					+ " " + ee.toString());
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

		return Title;
	}
}
