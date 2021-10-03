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
public class Shipping {
	@Autowired
	private SQLExecutor db;
	
	public ArrayList getShipCarrierList(String CompanyID) {
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
			String sqlString = "select ShipCarrierID,Name from bca_shipcarrier where CompanyID=? and Active=?";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, CompanyID);
			pstmt.setString(2, "1");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				arr.add(new org.apache.struts.util.LabelValueBean(rs
						.getString("Name"), rs.getString("ShipCarrierID")));
			}
		} catch (SQLException ee) {
			Loger.log(2, "Error in  Class Shipping and  method -getShipCarrierList "
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

	/*
	 * 
	 */
	public String getShipCarrier(String ShipCarrierID) {
		String ShipCarrier = null;
		Connection con = null ;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		if (db == null)
			ShipCarrier = null;
		con = db.getConnection();

		if (con == null)
			ShipCarrier = null;
		try {
			String sqlString = "select Name from bca_shipcarrier where ShipCarrierID=? ";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, ShipCarrierID);
			rs = pstmt.executeQuery();
			if (rs.next())
				ShipCarrier = rs.getString(1);
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
		return ShipCarrier;
	}

}
