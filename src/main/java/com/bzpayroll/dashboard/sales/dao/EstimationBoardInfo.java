package com.bzpayroll.dashboard.sales.dao;

import com.bzpayroll.common.db.SQLExecutor;
import com.bzpayroll.common.log.Loger;
import com.bzpayroll.dashboard.sales.forms.EstimationBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class EstimationBoardInfo {


	@Autowired
	private SQLExecutor db;


	public ArrayList EstimationRecordSearch(String compId, String oDate1,
			String oDate2, String saleDate1, String saleDate2, String marketID,
			String sOption1, String sOption2, String searchType, String searchTxt) {

		Loger.log("From EstimationInfo" + compId);
		Connection con = null ;
		Statement stmt = null, stmt1 = null, stmt2 = null,stmt4 = null;

		ArrayList<EstimationBoard> objList = new ArrayList<EstimationBoard>();
		ResultSet rs = null, rs2 = null, rs3 = null,rs4 = null;
		
		String mark = null;
		CustomerInfo cinfo = new CustomerInfo();
		try {
			con = db.getConnection();
			stmt = con.createStatement();
			stmt1 = con.createStatement();
			stmt2 = con.createStatement();
			stmt4 = con.createStatement();
			Loger.log("oDate1:" + oDate1 + " oDate2:" + oDate2);

			String sqlString = "select InvoiceID,OrderNum,PONum,RcvNum,EstNum," +
					"ClientVendorID,BSAddressID,date_format(DateAdded,'%m-%d-%Y') as DateAdded,orderid,date_format(DateConfirmed,'%m-%d-%Y') as DateConfirmed,IsPrinted,Shipped,Total,SalesRepID  " +
					"from bca_invoice as i where CompanyID ='"+compId+"' and invoiceStatus =0 ";// AND
			
			if (oDate1 != null && oDate2 != null && oDate1.trim().length() > 1
					&& oDate2.trim().length() > 1 ) {
				
				sqlString += "	and i.DateConfirmed between '"
						+ cinfo.string2date(oDate1) + "' and '"
						+ cinfo.string2date(oDate2) + "' ";
			}
			else if(oDate1 != null && oDate1.trim().length() > 1){
				sqlString += "	and i.DateConfirmed between '"
					+ cinfo.string2date(oDate1) + "' and '"
					+ cinfo.string2date("now()") + "' ";
			}
			else if(oDate2!=null && oDate2.trim().length() > 1){
				sqlString += "	and i.DateConfirmed <= '"+
					cinfo.string2date(oDate2) + "'  ";
				
			}
			if (saleDate1 != null && saleDate2 != null
					&& saleDate1.trim().length() > 1
					&& saleDate2.trim().length() > 1) {
				
				sqlString += "	and i.DateAdded between '"
						+ cinfo.string2date(saleDate1) + "' and '"
						+ cinfo.string2date(saleDate2) + "'  ";
			}
			else if(saleDate1 != null && saleDate1.trim().length() > 1){
				sqlString += "	and i.DateAdded between '"
					+ cinfo.string2date(saleDate1) + "' and '"
					+ cinfo.string2date("now()") + "' ";
			}
			else if(saleDate2!=null && saleDate2.trim().length() > 1){
				sqlString += "	and i.DateAdded <= '"+
					cinfo.string2date(saleDate2) + "'  ";
			}
			if(searchTxt != null && !searchTxt.trim().isEmpty()) {
				if (searchType.equals("2") || searchType.equals("3")) {
					sqlString += " AND EstNum LIKE '%" + searchTxt + "%' ";
				}
			}

//			if ("1".equalsIgnoreCase(marketID)) {
//				sqlString += "and i.OrderType=5";
//				mark = "Amazon Seller";
//
//				Loger.log("Amzon Saller" + marketID);
//			}
//			if ("2".equalsIgnoreCase(marketID)) {
//				sqlString += "and i.OrderType=6";
//				mark = "Amazon Market";
//			}

//			if ("3".equalsIgnoreCase(marketID)) {
//				sqlString += "and i.OrderType=7";
//				mark = "eBay";
//			}

//			if ("4".equalsIgnoreCase(marketID)) {
//				sqlString += "and i.OrderType=8";
//				mark = "Half.com";
//			}
//			if ("5".equalsIgnoreCase(marketID)) {
//				sqlString += "and i.OrderType=9";
//				mark = "Price Grabber";
//			}
			
		//	sqlString += "and i.EstNum > 0";
			
			sqlString += " and i.InvoiceTypeID=10"; // Estimation Order
			
			sqlString += " order by i.EstNum";

			stmt = con.createStatement();
			
			Loger.log(sqlString);
			rs = stmt.executeQuery(sqlString);

			do {
				if (!rs.next())
					break;
				EstimationBoard d = new EstimationBoard();

				d.setInvoiceID(rs.getInt("InvoiceID"));
				d.setOrderid(rs.getInt("orderid"));
				d.setOrderNum(rs.getLong("OrderNum"));
				d.setPo_no(rs.getLong("PONum"));
				d.setRcv_no(rs.getLong("RcvNum"));
				d.setEst_no(rs.getLong("EstNum"));
				d.setCvID(rs.getInt("ClientVendorID"));
				d.setBsAddressID(rs.getInt("BSAddressID"));
				d.setDateAdded(rs.getString("DateAdded"));
				d.setTransactionID(rs.getString("orderid"));
				d.setSaleDate(rs.getString("DateConfirmed"));
				d.setPrinted(rs.getBoolean("IsPrinted"));
				d.setShipped(rs.getInt("Shipped"));
				d.setMarketPlaceName(mark);
				
				d.setTotal(rs.getDouble("Total"));
				String rep=rs.getString("SalesRepID");
				 if(rep != null){
					 String sql4="select Name from bca_salesrep where SalesRepID ="+rep;  
					 rs4 = stmt4.executeQuery(sql4);
					 while(rs4.next()){
						 d.setRep(rs4.getString("Name"));
					 }
				   }

				String sql2 = " select a.LastName,a.FirstName,a.Email, b.Address1,b.Address2,b.City,b.State,b.Country,b.ZipCode,a.Name "
						+ " from bca_clientvendor a, bca_bsaddress b  where a.ClientVendorID = " + d.getCvID() + " and b.BSAddressID =" + d.getBsAddressID()
						+ " and a.Active = 1 and (a.Status = 'N' or a.Status = 'U') and a.Deleted = 0  and b.AddressType = 0 and (b.Status = 'N' or b.Status = 'U') ";
				if(searchTxt != null && !searchTxt.trim().isEmpty()){
					if(searchType.equals("1")){
						sql2 += " AND (a.FirstName LIKE '%"+searchTxt+"%' OR a.LastName LIKE '%"+searchTxt+"%')";
					}else if(searchType.equals("4")){
						sql2 += "AND (b.Address1 LIKE '%"+searchTxt+"%' OR b.Address2 LIKE '%"+searchTxt+"%' OR b.City LIKE '%"+searchTxt+"%' OR b.Country LIKE '%"+searchTxt+"%'))";
					}else if(searchType.equals("5")){
						sql2 += "AND a.Name LIKE '%"+searchTxt+"%'";
					}else if(searchType.equals("6")){
						sql2 += "AND a.Email LIKE '%"+searchTxt+"%'";
					}
				}

				for (rs2 = stmt1.executeQuery(sql2); rs2.next();) {
					d.setFirstName(rs2.getString("FirstName"));
					d.setLastName(rs2.getString("LastName"));
					d.setAddress1(rs2.getString("Address1"));
					d.setAddress2(rs2.getString("Address2"));
					d.setCity(rs2.getString("City"));
					d.setState(rs2.getString("State"));
					d.setCountry(rs2.getString("Country"));
					d.setZipCode(rs2.getString("ZipCode"));
					d.setEmail(rs2.getString("Email"));
					d.setCompanyName(rs2.getString("Name"));
				}
				if(searchTxt != null && !searchTxt.trim().isEmpty()){
					if(searchType.equals("1") && d.getFirstName()==null && d.getLastName()==null ){
						continue;
					}else if(searchType.equals("4") && d.getAddress1()==null && d.getAddress2()==null && d.getCity()==null && d.getCountry()==null){
						continue;
					}else if(searchType.equals("5") && d.getCompanyName()==null){
						continue;
					}else if(searchType.equals("6") && d.getEmail()==null){
						continue;
					}
				}
				
				String sql3 = " select InventoryName, Qty  from bca_cart  where InvoiceID ="
						+ d.getInvoiceID() + " and CompanyID = " + compId;

				rs3 = stmt2.executeQuery(sql3);
				int item_c = 0;
				do {
					if (!rs3.next())
						break;
					if (++item_c != 1)
						continue;
					d.setItemName(rs3.getString("InventoryName"));
					Loger.log("IName=" + rs3.getString("InventoryName"));
					break;
				} while (true);
				objList.add(d);

			} while (true);

		} catch (SQLException ee) {
			Loger.log(2,
					" SQL Error in Class TaxInfo and  method -getFederalTax "
							+ " " + ee.toString());
		}
		finally {
			try {
				if (rs != null) {
					db.close(rs);
					}
				if (stmt != null) {
					db.close(stmt);
					}
				if (rs2 != null) {
					db.close(rs2);
					}
				if (stmt1 != null) {
					db.close(stmt1);
					}
				if (rs3 != null) {
					db.close(rs3);
					}
				if (stmt2 != null) {
					db.close(stmt2);
					}
				if (rs4 != null) {
					db.close(rs4);
					}
				if (stmt4 != null) {
					db.close(stmt4);
					}
					if(con != null){
					db.close(con);
					}
				} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objList;

	}
	public java.sql.Date getdate(String d) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		Date d1 = null;
		try {
			d1 = sdf.parse(d);

		} catch (ParseException e) {
			Loger.log(2, "ParseException" + e.getMessage());
		}

		return (new java.sql.Date(d1.getTime()));

	}

	public boolean update(HttpServletRequest request) {
		boolean result = false;
		Connection con = null ;
		PreparedStatement pstmtUpdate = null;

		int size = Integer.parseInt(request.getParameter("Size"));
		String orderValue = request.getParameter("OrderValue");
		String status = request.getParameter("StatusValue");

		try {
			con = db.getConnection();
			for (int cnt = 0; cnt < size; cnt++) {
				int index1 = orderValue.indexOf(";");
				String temp1 = orderValue.substring(0, index1);
				long orderID = Long.parseLong(temp1);
				orderValue = orderValue.substring(index1 + 1);

				int index2 = status.indexOf(";");
				String temp2 = status.substring(0, index2);
				status = status.substring(index2 + 1);

				String updateQuery = "";

				if (temp2.equals("false"))
					updateQuery = "update bca_invoice set  Shipped = 0 where EstNum = ?";
				else
					updateQuery = "update bca_invoice set  Shipped = 1 where EstNum = ?";
				pstmtUpdate = con.prepareStatement(updateQuery);
				pstmtUpdate.setLong(1, orderID);
				int rows = pstmtUpdate.executeUpdate();
				if (rows > 0) {
					result = true;
				}

			}

		} catch (SQLException ee) {
			Loger.log(2,
					" SQL Error in Class TaxInfo and  method -getFederalTax "
							+ " " + ee.toString());
		}finally {
			try {
				if (pstmtUpdate != null) {
					db.close(pstmtUpdate);
					}
					if(con != null){
					db.close(con);
					}
				} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
