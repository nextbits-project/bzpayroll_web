/*
 * Author : Avibha IT Solutions Copyright 2006 Avibha IT Solutions. All rights
 * reserved. AVIBHA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.avibha.com
 */

package com.bzpayroll.dashboard.purchase.dao;

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
public class VendorCategory {

	@Autowired
	private SQLExecutor db;

	public ArrayList getCVCategoryList(String CompanyID) {
		ArrayList<LabelValueBean> arr = new ArrayList<LabelValueBean>();
		// boolean ret = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if (db == null)
			arr = null;
		con = db.getConnection();

		if (con == null)
			arr = null;

		try {
			String sqlString = "SELECT CVCategoryID,Name FROM bca_cvcategory where CompanyID=? and Active=?";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, CompanyID);
			pstmt.setString(2, "1");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				arr.add(new LabelValueBean(rs.getString("Name"), rs.getString("CVCategoryID")));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException ee) {
			Loger.log(2, "Error in  Class VendorCategory and  method -getCVCategoryList " + " " + ee.toString());
		} finally {
			db.close(con);
		}
		return arr;
	}

	public String CVCategory(String CVCategoryID) {
		String CVCategory = null;
		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs = null;
		if (db == null)
			CVCategory = null;
		con = db.getConnection();

		if (con == null)
			CVCategory = null;
		try {
			String sqlString = "select Name from bca_cvcategory where CVCategoryID=? ";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, CVCategoryID);
			rs = pstmt.executeQuery();
			if (rs.next())
				CVCategory = rs.getString(1);
		} catch (SQLException ee) {
			Loger.log(2, "Error in  VendorCategory and  method -CVCategory " + " " + ee.toString());
		} finally {
			db.close(con);
		}
		return CVCategory;
	}
}