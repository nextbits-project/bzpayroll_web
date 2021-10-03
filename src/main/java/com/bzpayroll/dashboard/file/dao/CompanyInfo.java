package com.bzpayroll.dashboard.file.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bzpayroll.common.db.SQLExecutor;
import com.bzpayroll.common.log.Loger;
import com.bzpayroll.common.utility.CountryState;
import com.bzpayroll.dashboard.file.forms.CompanyInfoDto;
import com.bzpayroll.dashboard.sales.forms.InvoiceDto;
import com.bzpayroll.dashboard.sales.forms.ItemDto;

@Service
public class CompanyInfo {

	@Autowired
	private  SQLExecutor db;

	public ArrayList<CompanyInfoDto> SearchCompany(String compId, int userID, CompanyInfoDto customer,
			HttpServletRequest request) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null, pstmt2;
		ArrayList<CompanyInfoDto> objList = new ArrayList<CompanyInfoDto>();
		ResultSet rs = null;
		ResultSet rs1 = null, rs2 = null;
		con = db.getConnection();
		CountryState cs = new CountryState();
		try {
			StringBuffer sqlString = new StringBuffer();
			sqlString.append("select NAME,NickName,FirstName,LastName,Address1,Address2,City,State,"
					+ "Zipcode,Province,Phone1,Phone2,Fax1,Email,Country,BusinessTypeID "
					+ "FROM bca_company WHERE CompanyId='" + compId + "'");

			String sql = "SELECT LoginID,PASSWORD,Confirm_Password FROM bca_user WHERE  ID='" + userID + "'";
			pstmt = con.prepareStatement(sqlString.toString());
			rs = pstmt.executeQuery();
			pstmt1 = con.prepareStatement(sql);
			rs1 = pstmt1.executeQuery();
			if (rs.next()) {
				customer.setCompanyName(replaceNullWithBlank(rs.getString(1)));
				customer.setNickName(replaceNullWithBlank(rs.getString(2)));
				customer.setFirstName(replaceNullWithBlank(rs.getString(3)));
				customer.setLastName(replaceNullWithBlank(rs.getString(4)));
				customer.setAddress1(replaceNullWithBlank(rs.getString(5)));
				customer.setAddress2(replaceNullWithBlank(rs.getString(6)));
				customer.setCity(replaceNullWithBlank(rs.getString(7)));
				customer.setState(replaceNullWithBlank(rs.getString(8)));
				request.setAttribute("state_gen", replaceNullWithBlank(rs.getString(8)));
				customer.setZip(replaceNullWithBlank(rs.getString(9)));
				customer.setProvince(replaceNullWithBlank(rs.getString(10)));
				customer.setPhone(replaceNullWithBlank(rs.getString(11)));
				customer.setCellPhone(replaceNullWithBlank(rs.getString(12)));
				customer.setFax(replaceNullWithBlank(rs.getString(13)));
				customer.setEmail(replaceNullWithBlank(rs.getString(14)));
				customer.setCountry(replaceNullWithBlank(rs.getString(15)));
				customer.setBusinessTypeId(rs.getInt(16));
				// int businessTypeId = rs.getInt(16);

				if (rs1.next()) {
					customer.setUserName(rs1.getString("LoginID"));
					customer.setPassword(rs1.getString("PASSWORD"));
					customer.setConfirmPassword(rs1.getString("Confirm_Password"));
				}
				int businessTypeId = customer.getBusinessTypeId();
				String sql2 = "select BusinessName from bca_businesstype where BusinessTypeID = " + businessTypeId;
				pstmt2 = con.prepareStatement(sql2);
				rs2 = pstmt2.executeQuery();

				if (rs2.next()) {
					customer.setType(rs2.getString("BusinessName"));
				}
				objList.add(customer);
			}
		} catch (SQLException ee) {
			Loger.log(2, " SQL Error in Class CompanyInfo and  method -SearchCompany " + " " + ee.toString());
		} finally {
			try {
				if (rs != null) {
					db.close(rs);
				}
				if (pstmt != null) {
					db.close(pstmt);
				}
				if (rs1 != null) {
					db.close(rs1);
				}
				if (pstmt1 != null) {
					db.close(pstmt1);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objList;
	}

	private String replaceNullWithBlank(String s) {
		return s == null || s.equalsIgnoreCase("null") ? "" : s;
	}
	// Below methods are added on 18-05-2020

	public ArrayList<InvoiceDto> selectPurchaseOrders(String compId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		ArrayList<InvoiceDto> objPurchaseList = new ArrayList<InvoiceDto>();
		ResultSet rs = null;
		con = db.getConnection();

		try {
			StringBuffer sqlString = new StringBuffer();
			sqlString.append("SELECT bca_invoice.PONum,bca_clientvendor.DateAdded,bca_clientvendor.FirstName,"
					+ "bca_clientvendor.LastName,bca_invoice.Total " + "FROM bca_invoice,bca_clientvendor "
					+ "WHERE bca_invoice.CompanyID = " + compId + " "
					+ "AND invoiceStatus = 0 AND ( bca_clientvendor.status = 'U'   OR bca_clientvendor.status = 'N' )  "
					+ "AND InvoiceTypeID IN (2, 6) "
					+ "AND bca_clientvendor.ClientVendorID = bca_invoice.ClientVendorID "
					+ "AND bca_clientvendor.CompanyID = " + compId + " " + "ORDER BY bca_invoice.PONum DESC");

			pstmt = con.prepareStatement(sqlString.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				InvoiceDto purchaseOrder = new InvoiceDto();
				purchaseOrder.setPoNum(rs.getString(1));
				purchaseOrder.setDateAdded(rs.getString(2));
				purchaseOrder.setFirstName(rs.getString(3));
				purchaseOrder.setLastName(rs.getString(4));
				purchaseOrder.setTotal(rs.getDouble("Total"));
				objPurchaseList.add(purchaseOrder);
			}
		} catch (SQLException ee) {
			Loger.log(2, " SQL Error in Class CompanyInfo and  method -selectPurchaseOrders " + " " + ee.toString());
		} finally {
			try {
				if (rs != null) {
					db.close(rs);
				}
				if (pstmt != null) {
					db.close(pstmt);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objPurchaseList;
	}

	public ArrayList<InvoiceDto> selectSalesOrders(String compId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		ArrayList<InvoiceDto> objSalesOrderList = new ArrayList<InvoiceDto>();
		ResultSet rs = null;
		con = db.getConnection();
		try {
			StringBuffer sqlString = new StringBuffer();
			sqlString.append("SELECT distinct bca_invoice.SONum,bca_invoice.DateAdded,bca_clientvendor.FirstName,"
					/* + "bca_clientvendor.LastName,(bca_invoice.AdjustedTotal) as Total " */
					+ "bca_clientvendor.LastName,bca_invoice.Total " + "FROM bca_invoice,bca_clientvendor "
					+ "WHERE bca_invoice.CompanyID = " + compId
					/* + " AND StoreID = -1 AND NOT (invoiceStatus = 1 ) " */
					+ " AND InvoiceTypeID IN (7,18)  AND ( bca_clientvendor.status = 'U'   OR bca_clientvendor.status = 'N' ) "
					+ "AND  bca_clientvendor.ClientVendorID = bca_invoice.ClientVendorID "
					+ "AND bca_clientvendor.CompanyID = " + compId + " ORDER BY bca_invoice.SONum DESC");

			pstmt = con.prepareStatement(sqlString.toString());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				InvoiceDto salesOrder = new InvoiceDto();
				salesOrder.setOrderNo(rs.getString(1));
				salesOrder.setDateAdded(rs.getString(2));
				salesOrder.setFirstName(rs.getString(3));
				salesOrder.setLastName(rs.getString(4));
				salesOrder.setTotal(rs.getDouble("Total"));

				objSalesOrderList.add(salesOrder);

			}
		} catch (SQLException ee) {
			Loger.log(2, " SQL Error in Class CompanyInfo and  method -selectSalesOrders " + " " + ee.toString());
		} finally {
			try {
				if (rs != null) {
					db.close(rs);
				}
				if (pstmt != null) {
					db.close(pstmt);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objSalesOrderList;
	}

	public ArrayList<InvoiceDto> selectInvoiceDetails(String compId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		ArrayList<InvoiceDto> objSalesOrderList = new ArrayList<InvoiceDto>();
		ResultSet rs = null;
		con = db.getConnection();

		try {
			StringBuffer sqlString = new StringBuffer();
			sqlString.append("SELECT distinct bca_invoice.OrderNum,bca_clientvendor.DateAdded,"
					+ "bca_clientvendor.FirstName,bca_clientvendor.LastName,bca_invoice.Total "
					+ "FROM bca_invoice,bca_clientvendor " + "WHERE bca_invoice.CompanyID = " + compId + " "
					+ "AND bca_clientvendor.ClientVendorID = bca_invoice.ClientVendorID "
					+ " and bca_clientvendor.Active = 1 and (bca_clientvendor.Status = 'N' or bca_clientvendor.Status = 'U') and bca_clientvendor.Deleted = 0 "
					+ "AND bca_clientvendor.CompanyID = " + compId + " AND bca_invoice.OrderNum <> 0 "
					+ "AND bca_invoice.OrderNum <> -1 " + "ORDER BY bca_invoice.OrderNum DESC");

			pstmt = con.prepareStatement(sqlString.toString());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				InvoiceDto salesOrder = new InvoiceDto();
				salesOrder.setOrderNo(rs.getString(1));
				salesOrder.setDateAdded(rs.getString(2));
				salesOrder.setFirstName(rs.getString(3));
				salesOrder.setLastName(rs.getString(4));
				salesOrder.setTotal(rs.getDouble(5));

				objSalesOrderList.add(salesOrder);

			}
		} catch (SQLException ee) {
			Loger.log(2, " SQL Error in Class CompanyInfo and  method -selectSalesOrders " + " " + ee.toString());
		} finally {
			try {
				if (rs != null) {
					db.close(rs);
				}
				if (pstmt != null) {
					db.close(pstmt);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objSalesOrderList;
	}

	public ArrayList<InvoiceDto> selectEstimateDetails(String compId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		ArrayList<InvoiceDto> objSalesOrderList = new ArrayList<InvoiceDto>();
		ResultSet rs = null;
		con = db.getConnection();

		try {
			StringBuffer sqlString = new StringBuffer();
			sqlString.append("SELECT distinct bca_invoice.EstNum,bca_clientvendor.DateAdded,"
					+ "bca_clientvendor.FirstName,bca_clientvendor.LastName,(bca_invoice.AdjustedTotal) as Total "
					+ "FROM bca_invoice,bca_clientvendor " + "WHERE bca_invoice.CompanyID = " + compId
					+ " AND NOT (invoiceStatus = 1 )  AND ( bca_clientvendor.status = 'U'   OR bca_clientvendor.status = 'N' )  "
					+ "AND InvoiceTypeID = 10 " + "AND bca_clientvendor.ClientVendorID = bca_invoice.ClientVendorID "
					+ "AND bca_clientvendor.CompanyID = " + compId + " ORDER BY bca_invoice.EstNum DESC");

			pstmt = con.prepareStatement(sqlString.toString());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				InvoiceDto salesOrder = new InvoiceDto();
				salesOrder.setOrderNo(rs.getString(1));
				salesOrder.setDateAdded(rs.getString(2));
				salesOrder.setFirstName(rs.getString(3));
				salesOrder.setLastName(rs.getString(4));
				salesOrder.setTotal(rs.getDouble(5));

				objSalesOrderList.add(salesOrder);

			}
		} catch (SQLException ee) {
			Loger.log(2, " SQL Error in Class CompanyInfo and  method -selectEstimateDetails " + " " + ee.toString());
		} finally {
			try {
				if (rs != null) {
					db.close(rs);
				}
				if (pstmt != null) {
					db.close(pstmt);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objSalesOrderList;
	}

	public ArrayList<ItemDto> getItemListDetails(String compId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1, pstmt2;

		ArrayList<ItemDto> objItemListList = new ArrayList<ItemDto>();
		ResultSet rs = null;
		ResultSet rs1, rs2 = null;
		con = db.getConnection();
		CountryState cs = new CountryState();
		try {
			StringBuffer sqlString = new StringBuffer();
			/*
			 * sqlString
			 * .append("select NAME,NickName,FirstName,LastName,Address1,Address2,City,State,"
			 * + "Zipcode,Province,Phone1,Phone2,Fax1,Email,Country,BusinessTypeID " +
			 * "FROM bca_company WHERE CompanyId='"+ compId+ "'");
			 */
			sqlString.append(
					"select InventoryCode, ItemTypeID,InventoryName,Qty from bca_iteminventory where CompanyID like '"
							+ compId + "' and Active like '1' and ItemtypeId not like '0' order by parentid");
			String sql = "";
			pstmt = con.prepareStatement(sqlString.toString());

			rs = pstmt.executeQuery();
			/*
			 * pstmt1=con.prepareStatement(sql); rs1=pstmt1.executeQuery();
			 */
			while (rs.next()) {
				ItemDto itemList = new ItemDto();
				itemList.setItemCode(rs.getString(1));
				itemList.setItemType(rs.getString(2));
				itemList.setItemName(rs.getString(3));
				itemList.setQty(rs.getString(4));
				objItemListList.add(itemList);
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
			Loger.log(2, " SQL Error in Class CompanyInfo and  method -getItemListDetails " + " " + ee.toString());
		} finally {
			try {
				if (rs != null) {
					db.close(rs);
				}
				if (pstmt != null) {
					db.close(pstmt);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objItemListList;
	}

	public boolean updateComapanyinfo(CompanyInfoDto c, int userID, String compId) {

		Connection con = null;

		boolean valid = false;
		Statement stmt1 = null, stmt2 = null;
		Statement stmt = null, stmt3 = null;
		ResultSet rs2 = null;
		con = db.getConnection();
		int InvoiceStyleID = 0;
		int BusinessTypeID = c.getBusinessTypeId();
		String BusinessTypeName = null;
		String sql = "select businessname from bca_businesstype where BusinessTypeID =" + BusinessTypeID;
		try {
			stmt2 = con.createStatement();
			rs2 = stmt2.executeQuery(sql);
			while (rs2.next()) {
				BusinessTypeName = rs2.getString(1);
			}
			if (BusinessTypeName.equals("Manufacturer")) {
				InvoiceStyleID = 3;

			} else if (BusinessTypeName.equals("Finance")) {
				InvoiceStyleID = 6;

			} else if (BusinessTypeName.equals("Retail")) {
				InvoiceStyleID = 4;

			} else if (BusinessTypeName.equals("Wholesale")) {
				InvoiceStyleID = 4;

			} else if (BusinessTypeName.equals("Service")) {
				InvoiceStyleID = 1;

			} else {
				InvoiceStyleID = 7;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		try {
			stmt = con.createStatement();
			stmt1 = con.createStatement();
			StringBuffer sqlString = new StringBuffer();
			/*
			 * sqlString
			 * .append("update bca_company set  Name='"+c.getCompanyName()+"',NickName='"+c.
			 * getNickName()+"',FirstName='"+c.getFirstName()+"'" + ""
			 * +",LastName='"+c.getLastName()+"',Address1='"+c.getAddress1()+"',Address2='"+
			 * c.getAddress2()+"'" +
			 * ""+",City='"+c.getCity()+"',Zipcode='"+c.getZip()+"',Province='"+c.
			 * getProvince()+"',Phone1='"+c.getPhone()+"'" +
			 * ",Phone2='"+c.getCellPhone()+"',Fax1='"+c.getFax()+"',Email='"+c.getEmail()+
			 * "',State='"+c.getState()+"'"+
			 * ",Country='"+c.getCountry()+"' where CompanyID='"+ compId+ "'");
			 */
			sqlString.append("update bca_company set  Name='" + c.getCompanyName() + "',NickName='" + c.getNickName()
					+ "',FirstName='" + c.getFirstName() + "'" + "" + ",LastName='" + c.getLastName() + "',Address1='"
					+ c.getAddress1() + "',Address2='" + c.getAddress2() + "'" + "" + ",City='" + c.getCity()
					+ "',Zipcode='" + c.getZip() + "',Province='" + c.getProvince() + "',Phone1='" + c.getPhone() + "'"
					+ ",Phone2='" + c.getCellPhone() + "',Fax1='" + c.getFax() + "',Email='" + c.getEmail()
					+ "',State='" + c.getiState() + "'" + ",Country='" + c.getCountry() + "', BusinessTypeID='"
					+ c.getBusinessTypeId() + "' where CompanyID='" + compId + "'");

			// String sql1="update bca_user set
			// Password='"+c.getPassword()+"',Confirm_Password='"+c.getConfirmPassword()+"'
			// where ID='"+userID+"'";

			int count = stmt.executeUpdate(sqlString.toString());
			if (count > 0) {
				valid = true;
				Loger.log("updated successfully");
			}
			/*
			 * int count1=stmt1.executeUpdate(sql1.toString()); if(count1>0) { valid=true; }
			 */
			stmt3 = con.createStatement();
			String SqlUpdate = "update bca_preference set InvoiceStyleID = " + InvoiceStyleID + " where CompanyID ="
					+ compId;
			stmt3.executeUpdate(SqlUpdate);
			System.out.println(stmt3);
			/* Loger.log("!!!!!!!!!!!!!!!!!!!!!!!!"); */

		} catch (SQLException ee) {
			Loger.log(2, " SQL Error in Class CompanyInfo and  method -updateComapanyinfo " + " " + ee.toString());
		} finally {
			try {

				if (stmt != null) {
					db.close(stmt);
				}
				if (stmt1 != null) {
					db.close(stmt1);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return valid;
	}

	public boolean updateInsertComapany(CompanyInfoDto c, String compId) {
		boolean ret = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		if (db == null)
			return ret;
		con = db.getConnection();
		if (con == null)
			return ret;

		try {
			String sqlString = "insert into bca_company(NAME,NickName,FirstName,LastName,Address1,Address2,City,Zipcode,Province,Phone1,Phone2,Fax1,Email) "
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
//			String sqlString = "insert into bca_clientvendor(NAME,NickName,FirstName,LastName,Address1,Address2,City,Zipcode,Province,Phone1,Phone2,Fax1,Email,State,Country) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sqlString);

			pstmt.setString(1, c.getCompanyName());
			pstmt.setString(2, c.getNickName());
			pstmt.setString(3, c.getFirstName());
			pstmt.setString(4, c.getLastName());
			pstmt.setString(5, c.getAddress1());
			pstmt.setString(6, c.getAddress2());
			pstmt.setString(7, c.getCity());
			pstmt.setString(8, c.getZip());
			pstmt.setString(9, c.getProvince());
			pstmt.setString(10, c.getPhone());
			pstmt.setString(11, c.getCellPhone());
			pstmt.setString(12, c.getFax());
			pstmt.setString(13, c.getEmail());
//			pstmt.setString(14, c.getState());
//			pstmt.setString(15, c.getCountry());
			int num = pstmt.executeUpdate();

			if (num > 0) {
				ret = true;
			}
		} catch (SQLException ee) {
			Loger.log(2, "SQLException in Class CustomerInfo,  method -insertCustomer " + ee.toString());
		} finally {
			try {
				if (pstmt != null) {
					db.close(pstmt);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public   boolean deleteCompany(String compId) {
		boolean loginStatus = false;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		con = db.getConnection();

		String sql = "update bca_company set Active = 0 where CompanyID = " + compId;
		String sql1 = " DELETE FROM bca_preference WHERE CompanyID=" + compId;
		try {

			stmt = con.createStatement();
			/* rs = stmt.executeQuery(sql); */
			stmt.executeUpdate(sql);
			int count = stmt.executeUpdate(sql.toString());
			if (count > 0) {
				loginStatus = true;
				Loger.log("company updated from bca_company successfully");
			}
			int countDel = stmt.executeUpdate(sql1.toString());
			if (countDel > 0) {
				loginStatus = true;
				Loger.log(countDel + " company deleted successfully");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in delete company:" + e.getMessage());
		} finally {
			try {
				if (rs != null) {
					db.close(rs);
				}
				if (stmt != null) {
					db.close(stmt);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return loginStatus;
	}
}
