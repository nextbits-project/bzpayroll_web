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
import com.bzpayroll.dashboard.accounting.bean.TblPaymentType;
 
@Service
public class PayMethod {
	
	@Autowired
	private SQLExecutor db;
	
	public ArrayList getPaymentTypeList(String CompanyID) {
		//ArrayList<LabelValueBean> arr = new ArrayList<LabelValueBean>();
		ArrayList<TblPaymentType> arr1 = new ArrayList<TblPaymentType>();
		Connection con = null ;
		PreparedStatement pstmt=null;
		
		ResultSet rs = null;
		if (db == null)
			//arr = null;
			arr1 = null;
		con = db.getConnection();

		if (con == null)
			//arr = null;
			arr1 = null;
		try {
			String sqlString = "select paymentTypeID,Name from bca_paymenttype  where CompanyID=? and Active=?";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, CompanyID);
			pstmt.setString(2, "1");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				//arr.add(new org.apache.struts.util.LabelValueBean(rs
						//.getString("Name"), rs.getString("paymentTypeID")));
				TblPaymentType tblpaymnt = new TblPaymentType();
				tblpaymnt.setId(Integer.parseInt(rs.getString("paymentTypeID")));
				tblpaymnt.setTypeName(rs.getString("Name"));
				arr1.add(tblpaymnt);
			}
			pstmt.close();
			rs.close();
		} catch (SQLException ee) {
			Loger.log(2, "Error in  Class PayMethod and  method -getPaymentTypeList "+ ee.toString());
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

		return arr1;
	}

	/*		The method provides the name of payment type
	 * from its id. 
	 * 
	 */
	public String getPaymentType(String paymentTypeID) {
		String paymentType = null;
		Connection con = null ;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		if (db == null)
			paymentType = null;
		con = db.getConnection();

		if (con == null)
			paymentType = null;
		try {
			String sqlString = "select Name from bca_paymenttype where paymentTypeID=? ";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, paymentTypeID);
			rs = pstmt.executeQuery();
			if (rs.next())
				paymentType = rs.getString(1);
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

		return paymentType;
	}

}
