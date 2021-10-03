package com.bzpayroll.dashboard.purchase.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bzpayroll.common.db.SQLExecutor;
import com.bzpayroll.common.log.Loger;
 

@Service
public class CreditCard {
	
	@Autowired
	private SQLExecutor db;
	
	public ArrayList getCCTypeList(String CompanyID) {
		ArrayList<LabelValueBean> arr = new ArrayList<LabelValueBean>();
		// boolean ret = false;
		Connection con = null ;
		PreparedStatement pstmt=null;
		
		ResultSet rs = null;
		if (db == null)
			arr = null;
		con = db.getConnection();

		if (con == null)
			arr = null;

		try {
			String sqlString = "SELECT CCTypeID,Name FROM bca_cctype where CompanyID=? and Active=?";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, CompanyID);
			pstmt.setString(2, "1");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				arr.add(new org.apache.struts.util.LabelValueBean(rs
						.getString("Name"), rs.getString("CCTypeID")));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException ee) {
			Loger.log(2, "Error in  Class CreditCard and method -getCCTypeList "
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

		return arr;
	}

	/*		The method provides the name of credit card
	 * from its id.
	 */
	public String getCCType(String CCTypeID) {
		String CCType = null;
		Connection con = null ;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		if (db == null)
			CCType = null;
		con = db.getConnection();

		if (con == null)
			CCType = null;
		try {
			String sqlString = "SELECT Name FROM bca_cctype where CCTypeID=? ";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, CCTypeID);
			rs = pstmt.executeQuery();
			if (rs.next())
				CCType = rs.getString(1);
		} catch (SQLException ee) {
			Loger.log(2, "Error in  Class Rep and  method -getRep " + " "
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

		return CCType;
	}

	public Object getCreditTermList(String cid) 
	{
		ArrayList<LabelValueBean> arr = new ArrayList<LabelValueBean>();
		Connection con = null ;
		PreparedStatement pstmt=null;
		
		ResultSet rs = null;
		/*if (db == null)
			arr = null;*/
		con = db.getConnection();

		if (con == null)
			arr = null;

		try {
			String sqlString = "SELECT CreditTermId,Name,Days,isDefault "
					+ "FROM bca_lineofcreditterm "
					+ "WHERE CompanyID ="+cid+" "
					+ "AND Active = ? ORDER BY Name";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, cid);
			//pstmt.setString(2, "1");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				arr.add(new org.apache.struts.util.LabelValueBean(rs
						.getString("Name"), rs.getString("CreditTermId")));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException ee) {
			Loger.log(2, "Error in  Class CreditCard and  method -getCreditTermList "
					+ " " + ee.toString());
		} finally {
			db.close(con);

		}

		return arr;
	}

}
