/*
 * Author : Avibha IT Solutions Copyright 2007 Avibha IT Solutions. All rights
 * reserved. AVIBHA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.avibha.com
 */
package com.bzpayroll.dashboard.sales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bzpayroll.common.ConstValue;
import com.bzpayroll.common.db.SQLExecutor;
import com.bzpayroll.common.log.Loger;
import com.bzpayroll.common.utility.CountryState;
import com.bzpayroll.common.utility.DateInfo;
import com.bzpayroll.common.utility.JProjectUtil;
import com.bzpayroll.dashboard.file.forms.ClientVendor;
import com.bzpayroll.dashboard.purchase.dao.PurchaseInfo;
import com.bzpayroll.dashboard.purchase.dao.VendorCategory;
import com.bzpayroll.dashboard.sales.forms.CustomerForm;
import com.bzpayroll.dashboard.sales.forms.EstimationForm;
import com.bzpayroll.dashboard.sales.forms.UpdateInvoiceForm;
import com.bzpayroll.sales.forms.CustomerDto;

/*
 * 
 */
@Service
public class CustomerInfo {

	@Autowired
	private SQLExecutor db;
	
	@Autowired
	private CountryState cs;


	public ArrayList customerDetails(String compId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		ArrayList<CustomerForm> objList = new ArrayList<CustomerForm>();
		ResultSet rs = null;

		try {
			con = db.getConnection();
			/*
			 * String sqlString =
			 * "select distinct ClientVendorID,Name,FirstName,LastName, Address1,Address2,"
			 * +
			 * "City,State,ZipCode, Phone,CellPhone,Fax,Email,date_format(DateAdded,'%m-%d-%Y') as DateAdded,Country from bca_clientvendor  "
			 * +
			 * "where  (Status like 'N' or Status like 'U')  and  (CVTypeID = '1' or CVTypeID = '2') "
			 * + "and ( Deleted = '0') and CompanyID='" + compId +
			 * "'   order by ClientVendorID ";
			 */

			String sqlString = "SELECT distinct ClientVendorID,Name,FirstName,LastName,Address1,Address2,City,State,ZipCode,Phone,CellPhone,Fax,"
					+ "Email,date_format(DateAdded,'%m-%d-%Y') as DateAdded,Country "
					+ "FROM bca_clientvendor WHERE CompanyID = " + compId
					+ " AND Status IN ('U', 'N' ) AND Deleted = 0 AND Active = 1 ORDER BY Name";
			pstmt = con.prepareStatement(sqlString);
			// Loger.log(sqlString);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CustomerForm customer = new CustomerForm();
				customer.setClientVendorID(rs.getString(1));
				customer.setCname(rs.getString(2) + "(" + rs.getString(3) + " " + rs.getString(4) + ")");
				customer.setFirstName(rs.getString(3));
				customer.setLastName(rs.getString(4));
				customer.setAddress1(rs.getString(5));
				customer.setAddress2(rs.getString(6));
				customer.setCity(rs.getString(7));

				customer.setStateName(cs.getStatesName(rs.getString(8)));

				customer.setZipCode(rs.getString(9));
				customer.setPhone(rs.getString(10));
				customer.setCellPhone(rs.getString(11));
				customer.setFax(rs.getString(12));
				customer.setEmail(rs.getString(13));
				customer.setDateAdded(rs.getString(14));
				customer.setCountry(cs.getCountryName(rs.getString(15)));
				customer.setFullName(rs.getString(3) + " " + rs.getString(4)); // changed by pritesh 10-09-2018
				customer.setBillTo(rs.getString(3) + rs.getString(4)); // changed by jay 05-11-2018
				objList.add(customer);
			}

		} catch (SQLException ee) {
			Loger.log(2, " SQL Error in Class CustomerInfo and  method -customerDetails " + " " + ee.toString());
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
		return objList;
	}

	public ArrayList getTransactionList(String datesCombo, String fromDate, String toDate, String sortBy,
			String companyID, HttpServletRequest request, CustomerForm cForm) {
		Connection con = null;
		PreparedStatement pstmt = null;

		ResultSet rs = null;
		con = db.getConnection();
		ArrayList<CustomerForm> objList = new ArrayList<CustomerForm>();
		String dateBetween = "";
		ArrayList<Date> selectedRange = new ArrayList<>();
		CustomerInfo cInfo = new CustomerInfo();
		DateInfo dInfo = new DateInfo();
		if (datesCombo != null && !datesCombo.equals("8")) {
			if (datesCombo != null && !datesCombo.equals("")) {
				selectedRange = dInfo.selectedIndex(Integer.parseInt(datesCombo));
				if (!selectedRange.isEmpty() && selectedRange != null) {
					cForm.setFromDate(cInfo.date2String(selectedRange.get(0)));
					cForm.setToDate(cInfo.date2String(selectedRange.get(1)));
				}
				if (selectedRange != null && !selectedRange.isEmpty()) {
					dateBetween = " AND DateAdded BETWEEN Timestamp ('"
							+ JProjectUtil.getDateFormaterCommon().format(selectedRange.get(0)) + "') AND Timestamp ('"
							+ JProjectUtil.getDateFormaterCommon().format(selectedRange.get(1)) + "')";
				}
			}
		} else if (datesCombo != null && datesCombo.equals("8")) {
			if (fromDate.equals("") && toDate.equals("")) {
				dateBetween = "";
			} else if (!fromDate.equals("") && toDate.equals("")) {
				dateBetween = " AND cv.DateAdded >= Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(fromDate) + "')");
			} else if (fromDate.equals("") && !toDate.equals("")) {
				dateBetween = " AND cv.DateAdded <= Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(toDate) + "')");
			} else {
				dateBetween = " AND cv.DateAdded BETWEEN Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(fromDate))
						+ "') AND Timestamp ('" + JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(toDate))
						+ "')";
			}
		}
		try {

			String sql = "" + "SELECT cv.NAME, " + "       cv.firstname, " + "       cv.lastname, "
					+ "       date_format(cv.DateAdded,'%m-%d-%Y') as DateAdded, " + "       cv.detail AS Details, "
					+ "       cv.customeropendebit, " + "       cv.clientvendorid, " + "       inv.dateadded, "
					+ "       inv.ordernum, " + "       inv.memo, " + "       inv.total "
					+ " FROM   bca_clientvendor AS cv " + "       INNER JOIN bca_invoice AS inv "
					+ "               ON cv.clientvendorid = inv.clientvendorid " + " WHERE  cvtypeid IN ( 1, 2 ) "
					+ "       AND ( status = 'U' " + "              OR status = 'N' ) "
					+ "       AND inv.invoicetypeid NOT IN ( 10, 15, 7 ) " + "       AND deleted = 0 "
					+ "       AND cv.companyid = '" + companyID + "'" + dateBetween + " ORDER  BY cv.dateadded, "
					+ "          cv.NAME";

			pstmt = con.prepareStatement(sql);
			Loger.log(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CustomerForm iForm = new CustomerForm();
				iForm.setCname(rs.getString(1));
				iForm.setDate(rs.getString(4));
				iForm.setOrderNo(rs.getString(9));
				iForm.setMemo(rs.getString(10));
				iForm.setTotal(rs.getDouble(11));
				objList.add(iForm);

			}

		} catch (Exception e) {
			Loger.log(2, "SQL error in getTransactionList" + " " + e.toString());
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
		return objList;
	}

	public ArrayList getBalanceSummaryList(String datesCombo, String fromDate, String toDate, String sortBy,
			String companyID, HttpServletRequest request, CustomerForm cForm) {
		Connection con = null;
		PreparedStatement pstmt = null;

		ResultSet rs = null;
		con = db.getConnection();
		StringBuffer sb = new StringBuffer();
		ArrayList<CustomerForm> objList = new ArrayList();
		ArrayList<Date> selectedRange = new ArrayList<>();
		CustomerInfo cInfo = new CustomerInfo();
		DateInfo dInfo = new DateInfo();
		String dateBetween = "";
		if (datesCombo != null && !datesCombo.equals("8")) {
			if (datesCombo != null && !datesCombo.equals("")) {
				selectedRange = dInfo.selectedIndex(Integer.parseInt(datesCombo));
				if (!selectedRange.isEmpty() && selectedRange != null) {
					cForm.setFromDate(cInfo.date2String(selectedRange.get(0)));
					cForm.setToDate(cInfo.date2String(selectedRange.get(1)));
				}
				if (selectedRange != null && !selectedRange.isEmpty()) {
					dateBetween = " AND DateAdded BETWEEN Timestamp ('"
							+ JProjectUtil.getDateFormaterCommon().format(selectedRange.get(0)) + "') AND Timestamp ('"
							+ JProjectUtil.getDateFormaterCommon().format(selectedRange.get(1)) + "')";
				}
			}
		} else if (datesCombo != null && datesCombo.equals("8")) {
			if (fromDate.equals("") && toDate.equals("")) {
				dateBetween = "";
			} else if (!fromDate.equals("") && toDate.equals("")) {
				dateBetween = " AND cv.DateAdded >= Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(fromDate) + "')");
			} else if (fromDate.equals("") && !toDate.equals("")) {
				dateBetween = " AND cv.DateAdded <= Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(toDate) + "')");
			} else {
				dateBetween = " AND cv.DateAdded BETWEEN Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(fromDate))
						+ "') AND Timestamp ('" + JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(toDate))
						+ "')";
			}
		}
		sb.append("SELECT " + " cv.Name," + " cv.FirstName," + // by ss
				" cv.LastName," + // by ss
				" cv.ClientVendorID" + " FROM " + " bca_clientvendor AS cv " + " WHERE "
				+ " (cv.Status = 'U' OR cv.Status = 'N') AND cv.Deleted = 0  " +
				// " AND cv.CVTypeID IN (1,2) " +
				" AND CVTypeID IN (" + getCustomerTypeId(ConstValue.CUSTOMER) + ","
				+ getCustomerTypeId(ConstValue.DEALER) + "," + getCustomerTypeId(ConstValue.CustVenBoth) + ","
				+ getCustomerTypeId(ConstValue.DealerVenBoth) + ")" + " AND cv.CompanyID = '" + companyID + "'");
		sb.append(dateBetween);
		sb.append(" ORDER By cv.Name");

		try {
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CustomerForm form = new CustomerForm();
				form.setFullName(rs.getString(2) + " " + rs.getString(3) + "(" + rs.getString(1) + ")");
				form.setTotal(Double.parseDouble(getBalance(rs.getInt(4), Integer.parseInt(companyID))));

				objList.add(form);
			}

		} catch (Exception e) {
			e.printStackTrace();
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
		return objList;
	}

	public ArrayList getBalanceDetail(String datesCombo, String fromDate, String toDate, String sortBy,
			String companyID, HttpServletRequest request, CustomerForm cForm) {
		Connection con = null;
		PreparedStatement pstmt = null;

		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		ArrayList<CustomerForm> form = new ArrayList<CustomerForm>();
		String dateBetween = "";
		ArrayList<Date> selectedRange = new ArrayList<>();
		CustomerInfo cInfo = new CustomerInfo();
		DateInfo dInfo = new DateInfo();
		if (datesCombo != null && !datesCombo.equals("8")) {
			if (datesCombo != null && !datesCombo.equals("")) {
				selectedRange = dInfo.selectedIndex(Integer.parseInt(datesCombo));
				if (!selectedRange.isEmpty() && selectedRange != null) {
					cForm.setFromDate(cInfo.date2String(selectedRange.get(0)));
					cForm.setToDate(cInfo.date2String(selectedRange.get(1)));
				}
				if (selectedRange != null && !selectedRange.isEmpty()) {
					dateBetween = " AND cv.DateAdded BETWEEN Timestamp ('"
							+ JProjectUtil.getDateFormaterCommon().format(selectedRange.get(0)) + "') AND Timestamp ('"
							+ JProjectUtil.getDateFormaterCommon().format(selectedRange.get(1)) + "')";
				}
			}
		} else if (datesCombo != null && datesCombo.equals("8")) {
			if (fromDate.equals("") && toDate.equals("")) {
				dateBetween = "";
			} else if (!fromDate.equals("") && toDate.equals("")) {
				dateBetween = " AND cv.DateAdded >= Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(fromDate) + "')");
			} else if (fromDate.equals("") && !toDate.equals("")) {
				dateBetween = " AND cv.DateAdded <= Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(toDate) + "')");
			} else {
				dateBetween = " AND cv.DateAdded BETWEEN Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(fromDate))
						+ "') AND Timestamp ('" + JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(toDate))
						+ "')";
			}
		}

		sb.append(
				"SELECT cv.ClientVendorID,cv.Name,cv.FirstName,cv.LastName,date_format(cv.dateadded,'%m-%d-%Y') AS Dateadded, "
						+ "cv.CustomerOpenDebit, cv.ClientVendorID,"
						+ " inv.InvoiceID,inv.DateAdded, inv.OrderNum, inv.AdjustedTotal, inv.Balance,inv.InvoiceTypeID"
						+ " FROM bca_clientvendor AS cv INNER JOIN bca_invoice AS inv"
						+ " ON cv.ClientVendorID = inv.ClientVendorID" + " WHERE CVTypeID IN (1,2,4,5) "
						+ " AND (Status = 'U' OR Status = 'N')  " + " AND Deleted = 0 "
						+ " AND InvoiceTypeID IN (1,11) " + " AND inv.invoiceStatus IN (0,2) " + " AND inv.CompanyID="
						+ companyID);
		sb.append(dateBetween);
		sb.append(" ORDER BY cv.dateadded DESC, cv.Name ");

		con = db.getConnection();
		try {
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CustomerForm f = new CustomerForm();
				f.setCname(rs.getString(2));
				f.setDateAdded(rs.getString(5));
				f.setOrderNo(rs.getString(10));
				f.setTotal(rs.getDouble(11));
				f.setBalance(rs.getDouble(12));
				form.add(f);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return form;
	}

	public String getBalance(int cvId, int companyId) {
		String balance = "";
		StringBuffer sb = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rst = null;

		Connection con = null;
		con = db.getConnection();

		sb.append("SELECT SUM(cv.Balance) FROM " + " bca_invoice AS cv" + " WHERE cv.CompanyID = " + companyId
				+ " AND cv.ClientVendorID=" + cvId + " AND InvoiceTypeID IN(1,11,17,13) AND invoiceStatus NOT IN(1,2)");

		try {
			ps = con.prepareStatement(sb.toString());
			rst = ps.executeQuery();

			while (rst.next()) {
				balance = new DecimalFormat("#0.00").format(rst.getDouble(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rst != null) {
					db.close(rst);
				}
				if (ps != null) {
					db.close(ps);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return balance;
	}

	public int getCustomerTypeId(String customerType) {
		int cvTypeId = 0;
		Statement stmt = null;
		ResultSet rst = null;

		Connection con = null;
		con = db.getConnection();

		try {
			String strSql = "SELECT CVTypeID from bca_cvtype " + "WHERE name='" + customerType + "'";
			stmt = con.createStatement();
			rst = stmt.executeQuery(strSql);
			if (rst.next()) {
				cvTypeId = rst.getInt("CVTypeID");
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				if (rst != null) {
					db.close(rst);
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
		return cvTypeId;
	}

	public ArrayList getSalesByCustomerSummary(String datesCombo, String fromDate, String toDate, String sortBy,
			String companyID, HttpServletRequest request, CustomerForm cForm) {
		Connection con = null;
		PreparedStatement pstmt = null;

		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		ArrayList<CustomerForm> objList = new ArrayList<CustomerForm>();
		String dateBetween = "";
		ArrayList<Date> selectedRange = new ArrayList<>();
		CustomerInfo cInfo = new CustomerInfo();
		DateInfo dInfo = new DateInfo();
		if (datesCombo != null && !datesCombo.equals("8")) {
			if (datesCombo != null && !datesCombo.equals("")) {
				selectedRange = dInfo.selectedIndex(Integer.parseInt(datesCombo));
				if (!selectedRange.isEmpty() && selectedRange != null) {
					cForm.setFromDate(cInfo.date2String(selectedRange.get(0)));
					cForm.setToDate(cInfo.date2String(selectedRange.get(1)));
				}
				if (selectedRange != null && !selectedRange.isEmpty()) {
					dateBetween = " AND cv.DateAdded BETWEEN Timestamp ('"
							+ JProjectUtil.getDateFormaterCommon().format(selectedRange.get(0)) + "') AND Timestamp ('"
							+ JProjectUtil.getDateFormaterCommon().format(selectedRange.get(1)) + "')";
				}
			}
		} else if (datesCombo != null && datesCombo.equals("8")) {
			if (fromDate.equals("") && toDate.equals("")) {
				dateBetween = "";
			} else if (!fromDate.equals("") && toDate.equals("")) {
				dateBetween = " AND cv.DateAdded >= Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(fromDate) + "')");
			} else if (fromDate.equals("") && !toDate.equals("")) {
				dateBetween = " AND cv.DateAdded <= Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(toDate) + "')");
			} else {
				dateBetween = " AND cv.DateAdded BETWEEN Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(fromDate))
						+ "') AND Timestamp ('" + JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(toDate))
						+ "')";
			}
		}

		sb.append("SELECT cv.ClientVendorID, cv.Name,cv.FirstName,cv.LastName FROM ");
		sb.append("bca_clientvendor AS cv ");
		sb.append("WHERE cv.ClientVendorID IN (SELECT ClientVendorID FROM bca_invoice)");
		sb.append(" AND (cv.Status = 'U' OR cv.Status = 'N') " + " AND cv.Deleted = 0 "
				+ " AND cv.CVTypeID IN (1,2,4,5) " + " AND cv.CompanyID = '" + companyID + "'");
		sb.append(dateBetween);
		sb.append(" ORDER BY cv.NAME");
		Loger.log(sb.toString());
		try {
			con = db.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CustomerForm f = new CustomerForm();
				f.setCname(rs.getString(2));
				f.setTotal(getBalanceForSalesCustomerSummary(rs.getInt(1), companyID));
				objList.add(f);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return objList;
	}

	public double getBalanceForSalesCustomerSummary(int cvId, String compId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		ResultSet rs = null;
		double bal = 0.00;

		String sql = "SELECT SUM(inv.Balance) AS Bal " + "FROM bca_invoice AS inv "
				+ "WHERE NOT (inv.invoiceStatus = 1) " + "AND inv.ClientVendorID = " + cvId + " "
				+ "AND inv.InvoiceTypeID NOT IN (15,7) " + "AND inv.CompanyID = '" + compId + "'";
		try {
			con = db.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bal = rs.getDouble(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return bal;
	}

	public ArrayList getIncomeByCustomerSymmary(String datesCombo, String fromDate, String toDate, String sortBy,
			String companyID, HttpServletRequest request, CustomerForm cForm) {
		Connection con = null;
		PreparedStatement pstmt = null;

		ResultSet rs = null;
		ArrayList<CustomerForm> objlist = new ArrayList<CustomerForm>();
		Long cvId = 0L;
		double balance = 0.00;
		String dateBetween = "";
		ArrayList<Date> selectedRange = new ArrayList<>();
		CustomerInfo cInfo = new CustomerInfo();
		DateInfo dInfo = new DateInfo();
		if (datesCombo != null && !datesCombo.equals("8")) {
			if (datesCombo != null && !datesCombo.equals("")) {
				selectedRange = dInfo.selectedIndex(Integer.parseInt(datesCombo));
				if (!selectedRange.isEmpty() && selectedRange != null) {
					cForm.setFromDate(cInfo.date2String(selectedRange.get(0)));
					cForm.setToDate(cInfo.date2String(selectedRange.get(1)));
				}
				if (selectedRange != null && !selectedRange.isEmpty()) {
					dateBetween = " AND cv.DateAdded BETWEEN Timestamp ('"
							+ JProjectUtil.getDateFormaterCommon().format(selectedRange.get(0)) + "') AND Timestamp ('"
							+ JProjectUtil.getDateFormaterCommon().format(selectedRange.get(1)) + "')";
				}
			}
		} else if (datesCombo != null && datesCombo.equals("8")) {
			if (fromDate.equals("") && toDate.equals("")) {
				dateBetween = "";
			} else if (!fromDate.equals("") && toDate.equals("")) {
				dateBetween = " AND cv.DateAdded >= Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(fromDate) + "')");
			} else if (fromDate.equals("") && !toDate.equals("")) {
				dateBetween = " AND cv.DateAdded <= Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(toDate) + "')");
			} else {
				dateBetween = " AND cv.DateAdded BETWEEN Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(fromDate))
						+ "') AND Timestamp ('" + JProjectUtil.getDateFormaterCommon().format(cInfo.string2date(toDate))
						+ "')";
			}
		}
		String sql = "SELECT cv.ClientVendorID, CustomerOpenDebit, " + " cv.DateAdded as cvDateAdded"
				+ " FROM bca_clientvendor AS cv" + " WHERE Status IN ('U','N')" + " AND CompanyID = '" + companyID + "'"
				+ " AND CVTypeID IN (1,2,4,5)" + dateBetween + " ORDER BY Name";
		try {
			con = db.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			Loger.log(sql);
			while (rs.next()) {
				CustomerForm f = new CustomerForm();
				cvId = rs.getLong(1);
				ClientVendor cv = getCv(cvId, companyID);
				f.setCname(cv.getName().equals("") ? cv.getFirstName() + " " + cv.getLastName() : cv.getName());
				double amount = 0.0;
				amount = rs.getDouble(2);
				balance = amount;
				Date date = rs.getDate(3);
				balance += getInvoiceAmount(cvId, companyID);
				balance = balance - getCostOfGoodsSold(cvId, companyID);
				balance = balance + getCustomerOpeningBal(cvId, companyID);
				if (balance >= 0) {
					f.setBalance(balance);
				} else {
					f.setBalance(0.00);
				}
				objlist.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		return objlist;
	}

	public double getCustomerOpeningBal(long cvId, String compId) {
		Statement stmt = null;
		ResultSet rs = null;
		double amt = 0.0;

		Connection con = null;

		String sql = "SELECT CustomerOpenDebit as total " + " FROM bca_clientvendor" + " WHERE Status IN ('U','N')"
				+ " AND CompanyID = '" + compId + "'" + " AND ClientVendorID = " + cvId + " AND CVTypeID IN (1,2,4,5)";

		try {
			con = db.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				amt = rs.getDouble(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
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
		return amt;
	}

	public double getCostOfGoodsSold(long cvId, String compId) {
		Statement stmt = null;
		double amount = 0.0;
		String sql = "";
		Connection con = null;
		ResultSet rs = null;

		sql = "SELECT  (cart.Qty * inventory.PurchasePrice) as total "
				+ " FROM (bca_cart AS cart INNER JOIN bca_invoice AS inv ON cart.InvoiceID = inv.InvoiceID) "
				+ " INNER JOIN bca_iteminventory AS inventory ON cart.InventoryID = inventory.InventoryID "
				+ " WHERE  inv.InvoiceTypeID IN (1,11) " + " AND inv.invoiceStatus=0 " + " AND inv.CompanyID='" + compId
				+ "'" + " AND inv.ClientVendorID=" + cvId;
		try {
			con = db.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				amount += rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		return amount;
	}

	public double getInvoiceAmount(long cvId, String compId) {
		Statement stmt = null, stmt1 = null;

		double amount = 0.0;
		double UpFrontamount = 0.0;
		String sql = "";
		String Sql = "";
		ResultSet rs = null;
		Connection con = null;

		sql = " SELECT SUM(AdjustedTotal) " + " FROM bca_invoice  " + " WHERE InvoiceTypeID IN (1,11) "
				+ " AND CompanyID='" + compId + "'" + " AND ClientVendorID=" + cvId;
		try {
			con = db.getConnection();
			stmt = con.createStatement();
			stmt1 = con.createStatement();

			ResultSet resultSet = stmt.executeQuery(sql);
			if (resultSet.next()) {
				amount = resultSet.getDouble(1);
			}

			Sql = " Select sum(UpfrontAmount) as ufAmt " + " FROM bca_invoice  " + " WHERE InvoiceTypeID IN (1) "
					+ " AND CompanyID='" + compId + "'" + " AND ClientVendorID=" + cvId;

			rs = stmt1.executeQuery(Sql);
			if (rs.next()) {
				UpFrontamount = rs.getDouble(1);
			}
			amount += UpFrontamount;
		} catch (Exception e) {
			e.printStackTrace();
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
		return amount;
	}

	public ClientVendor getCv(Long cvID, String compId) {
		Statement stmt = null;
		Connection con = null;

		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		ClientVendor cv = null;

		sql.append("SELECT FirstName,LastName,Name,Email,State,City,Province,ZipCode,Email,Address1,ClientVendorID "
				+ " FROM bca_clientvendor" + " WHERE  ClientVendorID =" + cvID + " AND Status='N'" + " AND CompanyID= '"
				+ compId + "'");
		try {
			con = db.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());

			while (rs.next()) {
				cv = new ClientVendor();
				String name = rs.getString("Name");
				cv.setName(name.equals("") ? name : name.trim());
				cv.setFirstName(rs.getString("FirstName"));
				cv.setLastName(rs.getString("LastName"));
				cv.setCity(rs.getString("City"));
				cv.setState(rs.getString("State"));
				cv.setProvince(rs.getString("Province"));
				cv.setZipCode(rs.getString("ZipCode"));
				cv.setEmail(rs.getString("Email"));
				cv.setAddress1(rs.getString("Address1"));
				cv.setCvID(rs.getInt("ClientVendorID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		return cv;
	}

	public void getLabel(int lblId, CustomerForm label) {
		Connection con = null;
		PreparedStatement pstmt_lbl = null;

		ResultSet rs = null;
		con = db.getConnection();

		try {
			pstmt_lbl = con.prepareStatement(
					"select ID,LabelType,Mar_Top,Mar_Left,Size_Width,Size_Height,Spacing_Hor,Spacing_Vert from bca_label where ID=?");
			pstmt_lbl.setInt(1, lblId);
			rs = pstmt_lbl.executeQuery();
			if (rs.next()) {

				label.setLabelType(rs.getInt("ID"));
				label.setLabelName(rs.getString("LabelType"));
				label.setTopMargin(rs.getString("Mar_Top"));
				label.setLeftMargin(rs.getString("Mar_Left"));
				label.setLabelWidth(rs.getString("Size_Width"));
				label.setLabelHeight(rs.getString("Size_Height"));
				label.setVertical(rs.getString("Spacing_Vert"));
				label.setHorizon(rs.getString("Spacing_Hor"));

			}

		} catch (SQLException ee) {
			Loger.log(2, " SQL Error in Class TaxInfo and  method -getFederalTax " + " " + ee.toString());
		} finally {
			try {
				if (rs != null) {
					db.close(rs);
				}
				if (pstmt_lbl != null) {
					db.close(pstmt_lbl);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList labelTypeDetails() {
		Connection con = null;
		PreparedStatement pstmt = null;

		ArrayList<CustomerForm> objList = new ArrayList<CustomerForm>();
		ResultSet rs = null;
		con = db.getConnection();

		try {
			pstmt = con.prepareStatement(
					"select ID,LabelType,Mar_Top,Mar_Left,Size_Width,Size_Height,Spacing_Hor,Spacing_Vert from bca_label order by LabelType");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CustomerForm label = new CustomerForm();
				label.setLabelType(rs.getInt("ID"));
				label.setLabelName(rs.getString("LabelType"));
				label.setTopMargin(rs.getString("Mar_Top"));
				label.setLeftMargin(rs.getString("Mar_Left"));
				label.setLabelWidth(rs.getString("Size_Width"));
				label.setLabelHeight(rs.getString("Size_Height"));
				label.setVertical(rs.getString("Spacing_Vert"));
				label.setHorizon(rs.getString("Spacing_Hor"));
				objList.add(label);

			}

		} catch (SQLException ee) {
			Loger.log(2, " SQL Error in Class TaxInfo and  method -getFederalTax " + " " + ee.toString());
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
		return objList;
	}

	public void saveLabel(CustomerForm form) {
		Connection con = null;
		PreparedStatement pstmt = null, pstmt1;

		ResultSet rs = null;
		con = db.getConnection();

		try {
			int labelID = 0;
			pstmt1 = con.prepareStatement("select max(ID)+1 from bca_label");
			rs = pstmt1.executeQuery();
			if (rs.next()) {
				labelID = rs.getInt(1);
			}
			pstmt = con.prepareStatement("insert into bca_label values(?,\"" + form.getLabelName() + "\",?,?,?,?,?,?)");
			pstmt.setInt(1, labelID);
			pstmt.setString(2, form.getTopMargin());
			pstmt.setString(3, form.getLeftMargin());
			pstmt.setString(4, form.getLabelWidth());
			pstmt.setString(5, form.getLabelHeight());
			pstmt.setString(6, form.getHorizon());
			pstmt.setString(7, form.getVertical());
			pstmt.executeUpdate();

		} catch (SQLException ee) {
			Loger.log(2, " SQL Error in Class TaxInfo and  method -getFederalTax " + " " + ee.toString());
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
	}

	public void deleteLabel(int lblId, CustomerForm form) {
		Connection con = null;
		PreparedStatement pstmt_delete = null, pstmt_id = null;

		con = db.getConnection();
		ResultSet rs_id = null;
		try {
			pstmt_delete = con.prepareStatement("delete from bca_label where ID=?");
			pstmt_delete.setInt(1, lblId);
			int del = pstmt_delete.executeUpdate();
			if (del > 0) {
				pstmt_id = con.prepareStatement("select ID from bca_label where ID >? order by ID asc");
				pstmt_id.setInt(1, lblId);
				rs_id = pstmt_id.executeQuery();
				if (rs_id.next())
					getLabel(rs_id.getInt("ID"), form);
				else {
					pstmt_id = con.prepareStatement("select ID from bca_label");
					rs_id = pstmt_id.executeQuery();
					if (rs_id.next()) {
						getLabel(rs_id.getInt("ID"), form);
					}

				}
			}
		} catch (SQLException ee) {
			Loger.log(2, "  SQL Error in Class CustomerInfo and  method -deleteLabel" + " " + ee.toString());
		} finally {
			try {
				if (rs_id != null) {
					db.close(rs_id);
				}
				if (pstmt_delete != null) {
					db.close(pstmt_delete);
				}
				if (pstmt_id != null) {
					db.close(pstmt_id);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception ee) {
				Loger.log(2, " SQL Error in Class CustomerInfo and  method -deleteLabel and in finally " + " "
						+ ee.toString());
			}
		}
	}

	public void updateLabel(int labelID, CustomerForm form) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = db.getConnection();

		try {
			if (form.getTopMargin().equals("")) {
				form.setTopMargin("0");
			}
			if (form.getLeftMargin().equals("")) {
				form.setLeftMargin("0");
			}
			if (form.getLabelWidth().equals("")) {
				form.setLabelWidth("0");
			}
			if (form.getLabelHeight().equals("")) {
				form.setLabelHeight("0");
			}
			if (form.getHorizon().equals("")) {
				form.setHorizon("0");
			}
			if (form.getVertical().equals("")) {
				form.setTopMargin("0");
			}
			Loger.log("TOP_____________" + form.getTopMargin());
			Loger.log("NAME_____________" + form.getLabelName());
			Loger.log("HERTICAL_____________" + form.getHorizon());
			pstmt = con.prepareStatement("update bca_label set LabelType=\"" + form.getLabelName()
					+ "\",Mar_Top=?,Mar_Left=?,Size_Width=?,Size_Height=?,Spacing_Hor=?,Spacing_Vert=? where ID=?");
			pstmt.setString(1, form.getTopMargin());
			pstmt.setString(2, form.getLeftMargin());
			pstmt.setString(3, form.getLabelWidth());
			pstmt.setString(4, form.getLabelHeight());
			pstmt.setString(5, form.getHorizon());
			pstmt.setString(6, form.getVertical());
			pstmt.setInt(7, labelID);
			pstmt.executeUpdate();

		} catch (SQLException ee) {
			Loger.log(2, " SQL Error in Class TaxInfo and  method -getFederalTax " + " " + ee.toString());
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
	}

	public ArrayList SearchCustomer(String compId, String cvId, CustomerForm form) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;

		ArrayList<CustomerForm> objList = new ArrayList<CustomerForm>();
		ResultSet rs = null;
		ResultSet rs1 = null;
		con = db.getConnection();

		try {
			StringBuffer sqlString = new StringBuffer();
			sqlString.append(" select distinct bca_clientvendor.ClientVendorID,bca_clientvendor.Name,");
			sqlString.append("bca_clientvendor.FirstName, bca_clientvendor.LastName, ");
			sqlString.append("bca_clientvendor.Address1, bca_clientvendor.Address2,bca_clientvendor.City,");
			sqlString.append(" bca_clientvendor.State, bca_clientvendor.Province, bca_clientvendor.Country,");
			sqlString.append(" bca_clientvendor.ZipCode, bca_clientvendor.Phone, bca_clientvendor.CellPhone,");
			sqlString.append("bca_clientvendor.Fax, bca_clientvendor.Email,bca_clientvendor.HomePage,");
			sqlString.append(
					"bca_clientvendor.CustomerTitle,bca_clientvendor.ResellerTaxID,bca_clientvendor.VendorOpenDebit,");
			sqlString.append("bca_clientvendor.VendorAllowedCredit,bca_clientvendor.Detail,bca_clientvendor.Taxable,");
			sqlString.append("bca_clientvendor.CVTypeID,max(bca_clientvendor.DateAdded)");
			sqlString.append(",bca_creditcard.CardNumber ,bca_creditcard.CardExpMonth ,");
			sqlString.append(
					"bca_creditcard.CardCW2 ,bca_creditcard.CardHolderName,bca_creditcard.CardBillingAddress,bca_creditcard.CardBillingZipCode,");
			sqlString.append("bca_bsaddress.Name,bca_bsaddress.FirstName,");
			sqlString.append(
					"bca_bsaddress.LastName,bca_bsaddress.Address1,bca_bsaddress.Address2,bca_bsaddress.City,bca_bsaddress.ZipCode,bca_bsaddress.Country,");
			sqlString.append("bca_bsaddress.State,bca_bsaddress.Province,bca_bsaddress.AddressType,");
			sqlString.append(
					"bca_clientvendorfinancecharges.UseIndividual ,bca_clientvendorfinancecharges.AnnualInterestRate ,bca_clientvendorfinancecharges.MinimumFinanceCharge ,");
			sqlString.append(
					"bca_clientvendorfinancecharges.GracePeriod ,bca_clientvendorfinancecharges.AssessFinanceCharge ,bca_clientvendorfinancecharges.MarkFinanceCharge ");
			sqlString.append(
					"from  bca_clientvendor left join ( bca_creditcard ,bca_bsaddress ,bca_clientvendorfinancecharges )");
			sqlString.append(
					" on (bca_creditcard.ClientVendorID= bca_clientvendor.ClientVendorID and bca_bsaddress.ClientVendorID= ");
			sqlString.append(
					"bca_clientvendor.ClientVendorID and bca_clientvendorfinancecharges.ClientVendorID= bca_clientvendor.ClientVendorID )");
			sqlString.append(
					" where (bca_clientvendor.Status like 'N' or bca_clientvendor.Status like 'U')  and  (bca_clientvendor.CVTypeID = '1' or ");
			sqlString.append(
					"bca_clientvendor.CVTypeID = '2')and ( bca_clientvendor.Deleted = '0') and CompanyID='1' and bca_clientvendor.ClientVendorID ='"
							+ cvId + "' group by ( bca_clientvendor.ClientVendorID )");
			sqlString.append(" order by bca_clientvendor.ClientVendorID ");

			pstmt = con.prepareStatement(sqlString.toString());
			Loger.log(sqlString);
			rs = pstmt.executeQuery();
			String addresstype = "";
			if (rs.next()) {
				CustomerForm customer = (CustomerForm) form;
				customer.setClientVendorID(rs.getString(1));
				customer.setCname(rs.getString(2));
				customer.setFirstName(rs.getString(3));
				customer.setLastName(rs.getString(4));
				customer.setAddress1(rs.getString(5));
				customer.setAddress2(rs.getString(6));
				customer.setCity(rs.getString(7));
				customer.setState(rs.getString(8));
				customer.setProvince(rs.getString(9));
				customer.setCountry(rs.getString(10));
				customer.setZipCode(rs.getString(11));
				customer.setPhone(rs.getString(12));
				customer.setCellPhone(rs.getString(13));
				customer.setFax(rs.getString(14));
				customer.setEmail(rs.getString(15));
				customer.setHomePage(rs.getString(16));
				customer.setTitle(rs.getString(17));
				customer.setTexID(rs.getString(18));
				customer.setOpeningUB(rs.getString(19));
				customer.setExtCredit(rs.getString(20));
				customer.setMemo(rs.getString(21));
				customer.setTaxAble(rs.getString(22));
				customer.setIsclient(rs.getString(23));
				customer.setDateAdded(rs.getString(24));
				customer.setCardNo(rs.getString(25));
				customer.setExpDate(rs.getString(26));
				customer.setCw2(rs.getString(27));
				customer.setCardHolderName(rs.getString(28));
				customer.setCardBillAddress(rs.getString(29));
				customer.setCardZip(rs.getString(30));

				if ("0".equalsIgnoreCase(rs.getString(41))) {
					addresstype = "0";
					customer.setBscname(rs.getString(31));
					customer.setBsfirstName(rs.getString(32));
					customer.setBslastName(rs.getString(33));
					customer.setBsaddress1(rs.getString(34));
					customer.setBsaddress2(rs.getString(35));
					customer.setBscity(rs.getString(36));
					customer.setBszipCode(rs.getString(37));
					customer.setBscountry(rs.getString(38));
					customer.setBsstate(rs.getString(39));
					customer.setBsprovince(rs.getString(40));
				} else if ("1".equalsIgnoreCase(rs.getString(41))) {
					addresstype = "1";
					customer.setShcname(rs.getString(31));
					customer.setShfirstName(rs.getString(32));
					customer.setShlastName(rs.getString(33));
					customer.setShaddress1(rs.getString(34));
					customer.setShaddress2(rs.getString(35));
					customer.setShcity(rs.getString(36));
					customer.setShzipCode(rs.getString(37));
					customer.setShcountry(rs.getString(38));
					customer.setShstate(rs.getString(39));
					customer.setShprovince(rs.getString(40));
				}
				customer.setFsUseIndividual(rs.getString(42));
				customer.setAnnualIntrestRate(rs.getString(43));
				customer.setMinFCharges(rs.getString(44));
				customer.setGracePrd(rs.getString(45));
				String str1 = rs.getString(46);
				String str2 = rs.getString(47);
				Loger.log("STR1 ---- ASSESS--->  " + str1);
				Loger.log("STR2 ---- MARK --> " + str2);
				customer.setFsAssessFinanceCharge(str1);
				customer.setFsMarkFinanceCharge(str2);

				// System.out.println("\n\n\n***\nasssessFinance=="+customer.getFsAssessFinanceCharge());
				// System.out.println("markFinance=="+customer.getFsMarkFinanceCharge()+"\n***\n\n\n");

				if ("1".equalsIgnoreCase(addresstype)) {
					StringBuffer sqlString1 = new StringBuffer();
					sqlString1.append("select bca_bsaddress.Name,bca_bsaddress.FirstName,");
					sqlString1.append(
							"bca_bsaddress.LastName,bca_bsaddress.Address1,bca_bsaddress.Address2,bca_bsaddress.City,bca_bsaddress.ZipCode,bca_bsaddress.Country,");
					sqlString1.append("bca_bsaddress.State,bca_bsaddress.Province ");
					sqlString1.append(" from bca_bsaddress ");
					sqlString1.append(" where ClientVendorID like '" + cvId
							+ "' and AddressType like '0' and Status in ('N','U')");
					pstmt1 = con.prepareStatement(sqlString1.toString());
					Loger.log(sqlString1);
					rs1 = pstmt1.executeQuery();
					while (rs1.next()) {
						customer.setBscname(rs1.getString(1));
						customer.setBsfirstName(rs1.getString(2));
						customer.setBslastName(rs1.getString(3));
						customer.setBsaddress1(rs1.getString(4));
						customer.setBsaddress2(rs1.getString(5));
						customer.setBscity(rs1.getString(6));
						customer.setBszipCode(rs1.getString(7));
						customer.setBscountry(rs1.getString(8));
						customer.setBsstate(rs1.getString(9));
						customer.setBsprovince(rs1.getString(10));
					}
				} else if ("0".equalsIgnoreCase(addresstype)) {
					StringBuffer sqlString1 = new StringBuffer();
					sqlString1.append(" select bca_bsaddress.Name,bca_bsaddress.FirstName,");
					sqlString1.append(
							"bca_bsaddress.LastName,bca_bsaddress.Address1,bca_bsaddress.Address2,bca_bsaddress.City,bca_bsaddress.ZipCode,bca_bsaddress.Country,");
					sqlString1.append("bca_bsaddress.State,bca_bsaddress.Province ");
					sqlString1.append(" from bca_bsaddress ");
					sqlString1.append(" where ClientVendorID like '" + cvId
							+ "' and AddressType like '1'  and Status in ('N','U')");

					pstmt1 = con.prepareStatement(sqlString1.toString());
					Loger.log(sqlString1);
					pstmt1 = con.prepareStatement(sqlString1.toString());
					Loger.log(sqlString1);
					rs1 = pstmt1.executeQuery();
					while (rs1.next()) {
						customer.setShcname(rs1.getString(1));
						customer.setShfirstName(rs1.getString(2));
						customer.setShlastName(rs1.getString(3));
						customer.setShaddress1(rs1.getString(4));
						customer.setShaddress2(rs1.getString(5));
						customer.setShcity(rs1.getString(6));
						customer.setShzipCode(rs1.getString(7));
						customer.setShcountry(rs1.getString(8));
						customer.setShstate(rs1.getString(9));
						customer.setShprovince(rs1.getString(10));
					}
				}
				objList.add(customer);
			}
		} catch (SQLException ee) {
			Loger.log(2, " SQL Error in Class TaxInfo and  method -getFederalTax " + " " + ee.toString());
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

	public boolean UpdateCustomer(String compId, String cvId) {
		Connection con = null;

		boolean valid = false;
		// ResultSet rs = null;
		con = db.getConnection();
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			StringBuffer sqlString = new StringBuffer();
			sqlString.append("update bca_clientvendor set status='O' where ClientVendorID = '" + cvId
					+ "' and ( status like 'N' or status like 'U') ");
			Loger.log(sqlString);
			int count = stmt.executeUpdate(sqlString.toString());
			if (count > 0) {
				valid = true;
				Loger.log("status updated successfully");
			}
			// Loger.log("!!!!!!!!!!!!!!!!!!!!!!!!");

		} catch (SQLException ee) {
			Loger.log(2, " SQL Error in Class CustomerInfo and  method -UpdateCustomer " + ee.toString());
		} finally {
			try {

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
		return valid;
	}

	public boolean insertCustomer(String cvId, CustomerForm c, String compID, int istaxable, int isAlsoClient,
			int useIndividualFinanceCharges, int AssessFinanceChk, int FChargeInvoiceChk, String status) {
		boolean ret = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement ps = null;

		if (db == null)
			return ret;
		con = db.getConnection();
		if (con == null)
			return ret;

		try {
			String oBal = "0";
			String exCredit = "0";
			PurchaseInfo pinfo = new PurchaseInfo();
			// Loger.log("istaxable:" + istaxable);
			// Loger.log("isAlsoClient:" + isAlsoClient);

			int cvID = Integer.parseInt(cvId); // pinfo.getLastClientVendorID()
			// + 1;

			if (isAlsoClient == 1) {
				isAlsoClient = 1;
			} else
				isAlsoClient = 2;

			if (c.getOpeningUB() != null && c.getOpeningUB().trim().length() > 0)
				oBal = c.getOpeningUB();

			if (c.getExtCredit() != null && c.getExtCredit().trim().length() > 0)
				exCredit = c.getExtCredit();

			VendorCategory vc = new VendorCategory();
			String vcName = vc.CVCategory(c.getType());

			String sqlString = "insert into bca_clientvendor(ClientVendorID, Name,DateAdded, CustomerTitle, FirstName, LastName, Address1, Address2,"
					+ " City, State, Province, Country, ZipCode, Phone, CellPhone,Fax,HomePage, Email, CompanyID,"
					+ " ResellerTaxID,VendorOpenDebit,VendorAllowedCredit,Detail,Taxable,CVTypeID,CVCategoryID,CVCategoryName,Active,Deleted,Status) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

			pstmt = con.prepareStatement(sqlString);
			pstmt.setInt(1, cvID);

			pstmt.setString(2, c.getCname());
			pstmt.setDate(3, (c.getDateAdded() == null || c.getDateAdded().equals("")) ? string2date(" now() ")
					: string2date(c.getDateAdded()));
			pstmt.setString(4, c.getTitle());
			pstmt.setString(5, c.getFirstName());
			pstmt.setString(6, c.getLastName());
			pstmt.setString(7, c.getAddress1());
			pstmt.setString(8, c.getAddress2());
			pstmt.setString(9, c.getCity());
			pstmt.setString(10, c.getState());
			pstmt.setString(11, c.getProvince());
			pstmt.setString(12, c.getCountry());
			pstmt.setString(13, c.getZipCode());
			pstmt.setString(14, c.getPhone());
			pstmt.setString(15, c.getCellPhone());
			pstmt.setString(16, c.getFax());
			pstmt.setString(17, c.getHomePage());
			pstmt.setString(18, c.getEmail());
			pstmt.setString(19, compID);
			pstmt.setString(20, c.getTexID());
			pstmt.setString(21, oBal);
			pstmt.setString(22, exCredit);
			pstmt.setString(23, c.getMemo());
			pstmt.setInt(24, istaxable);
			pstmt.setInt(25, isAlsoClient);
			pstmt.setString(26, c.getType());
			pstmt.setString(27, vcName);
			pstmt.setString(28, "1");
			pstmt.setString(29, "0");
			pstmt.setString(30, status);

			Loger.log(sqlString);

			int num = pstmt.executeUpdate();

			if (num > 0) {
				ret = true;
			}
			if (c.getShipping() != null && c.getShipping().trim().length() > 0)
				pinfo.updateClientVendor("ShipCarrierID", c.getShipping(), cvID);

			if (c.getPaymentType() != null && c.getPaymentType().trim().length() > 0)
				pinfo.updateClientVendor("PaymentTypeID", c.getPaymentType(), cvID);

			if (c.getRep() != null && c.getRep().trim().length() > 0)
				pinfo.updateClientVendor("SalesRepID", c.getRep(), cvID);

			if (c.getTerm() != null && c.getTerm().trim().length() > 0)
				pinfo.updateClientVendor("TermID", c.getTerm(), cvID);

			if (c.getCcType() != null && c.getCcType().trim().length() > 0) {
				pinfo.updateClientVendor("CCTypeID", c.getCcType(), cvID);
			}

			pinfo.insertVendorCreditCard(c.getCcType(), cvID, c.getCardNo(), c.getExpDate(), c.getCw2(),
					c.getCardHolderName(), c.getCardBillAddress(), c.getCardZip());
			int bsAddID = pinfo.getLastBsAdd() + 1;

			if (c.getSetdefaultbs().equals("0")) {
				pinfo.insertVendorBSAddress(cvID, bsAddID, c.getBscname(), c.getBsfirstName(), c.getBslastName(),
						c.getBsaddress1(), c.getBsaddress2(), c.getBscity(), c.getBsstate(), c.getBsprovince(),
						c.getBscountry(), c.getBszipCode(), "1");

				pinfo.insertVendorBSAddress(cvID, bsAddID, c.getShcname(), c.getShfirstName(), c.getShlastName(),
						c.getShaddress1(), c.getShaddress2(), c.getShcity(), c.getShstate(), c.getShprovince(),
						c.getShcountry(), c.getShzipCode(), "0");
			} else {
				pinfo.insertVendorBSAddress(cvID, bsAddID, c.getCname(), c.getFirstName(), c.getLastName(),
						c.getAddress1(), c.getAddress2(), c.getCity(), c.getState(), c.getProvince(), c.getCountry(),
						c.getZipCode(), "1");

				pinfo.insertVendorBSAddress(cvID, bsAddID, c.getCname(), c.getFirstName(), c.getLastName(),
						c.getAddress1(), c.getAddress2(), c.getCity(), c.getState(), c.getProvince(), c.getCountry(),
						c.getZipCode(), "0");
			}

			pinfo.insertVFCharge(cvID, useIndividualFinanceCharges, c.getAnnualIntrestRate(), c.getMinFCharges(),
					c.getGracePrd(), AssessFinanceChk, FChargeInvoiceChk);

			// --------------------------code to save services
			// -------------------------------------START-------
			int i;
			String sql;
			String serviceID = c.getTable_serID();

			String serviceBal = c.getTable_bal();
			String defaultser = c.getTable_defaultVal();

			String invStyleID = c.getTable_invId();

			// Loger.log("SERVICE----------------------->");

			String temp[] = null, temp2[] = null, temp3[] = null;
			if ((serviceID != "" && serviceID != null)
					&& (invStyleID != "" && invStyleID != null) & (serviceBal != "" && serviceBal != null)) {
				temp = serviceID.split(";"); // serviceID is in form like
				// 3;6;8;
				temp2 = invStyleID.split(";");
				temp3 = serviceBal.split(";");
			}

			if ((temp != null) || (temp2 != null) || (temp3 != null)) {
				java.sql.Date d = new java.sql.Date(new java.util.Date().getTime());

				for (i = 0; i < temp.length; i++) {
					sql = "insert into bca_clientvendorservice values (?,?,?,?,?,?,?)";
					ps = con.prepareStatement(sql);
					ps.setInt(1, cvID);
					ps.setDate(2, d);
					ps.setInt(3, Integer.parseInt(compID));
					ps.setInt(4, Integer.parseInt(temp2[i]));
					ps.setFloat(5, java.lang.Float.parseFloat(temp3[i]));
					if (Integer.parseInt(temp[i]) == Integer.parseInt(defaultser))
						ps.setInt(6, 1);
					else
						ps.setInt(6, 0);
					ps.setInt(7, Integer.parseInt(temp[i]));

					ps.executeUpdate();
				}
			}
			// }
			// --------------------------code to save services
			// -------------------------------------END-------

		} catch (SQLException ee) {
			Loger.log(2, "SQLException in Class CustomerInfo,  method -insertCustomer " + ee.toString());
		} finally {
			try {
				if (ps != null) {
					db.close(ps);
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
		return ret;
	}

	public boolean updateInsertCustomer(String cvId, CustomerDto c, String compID, int istaxable, int isAlsoClient,
			int useIndividualFinanceCharges, int AssessFinanceChk, String status) {
		boolean ret = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement ps = null;

		if (db == null)
			return ret;
		con = db.getConnection();
		if (con == null)
			return ret;

		try {
			String oBal = "0";
			String exCredit = "0";
			PurchaseInfo pinfo = new PurchaseInfo();
			Loger.log("istaxable:" + istaxable);
			Loger.log("isAlsoClient:" + isAlsoClient);

			int cvID = Integer.parseInt(cvId);
			if (c.getOpeningUB() != null && c.getOpeningUB().trim().length() > 0)
				oBal = c.getOpeningUB();

			if (c.getExtCredit() != null && c.getExtCredit().trim().length() > 0)
				exCredit = c.getExtCredit();

			// Loger.log("Type______________________________" + c.getType());
			if (c.getType() == null || c.getType().equals(""))
				c.setType("0");

			VendorCategory vc = new VendorCategory();
			String vcName = vc.CVCategory(c.getType());

			/*
			 * String sqlString =
			 * "insert into bca_clientvendor(ClientVendorID, Name,DateAdded, CustomerTitle, "
			 * + " FirstName, LastName, Address1, Address2," +
			 * " City, State, Province, Country, ZipCode, Phone, CellPhone,Fax,HomePage, Email, CompanyID,"
			 * +
			 * " ResellerTaxID,VendorOpenDebit,VendorAllowedCredit,Detail,Taxable,CVTypeID, "
			 * + "CVCategoryID, CVCategoryName,Active,Deleted,Status,CCTypeID) " +
			 * " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,1,0,?,? )";
			 */ // total=31

			String sqlString = "UPDATE bca_clientvendor SET Name=?,DateAdded=?,CustomerTitle=?, FirstName=?,LastName=?,Address1=?,Address2=?,"
					+ " City=?,State=?,Province=?,Country=?,ZipCode=?,Phone=?,CellPhone=?, Fax=?,HomePage=?,Email=?,CompanyID=?,"
					+ " ResellerTaxID=?,VendorOpenDebit=?,VendorAllowedCredit=?,Detail=?, Taxable=?,CVTypeID=?, "
					+ " CVCategoryID=?,CVCategoryName=?,Active=1,Deleted=0,Status=?,CCTypeID=?,isMobilePhoneNumber=?, "
					+ " MiddleName=?, DateInput=?, DateTerminated=?, isTerminated=? " + " WHERE ClientVendorID=" + cvID;

			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, c.getCname());
			pstmt.setDate(2, ((c.getDateAdded() == null || c.getDateAdded().equals("")) ? string2date("now()")
					: string2date(c.getDateAdded())));
			pstmt.setString(3, c.getTitle());
			pstmt.setString(4, c.getFirstName());
			pstmt.setString(5, c.getLastName());
			pstmt.setString(6, c.getAddress1());
			pstmt.setString(7, c.getAddress2());
			pstmt.setString(8, c.getCity());
			pstmt.setString(9, c.getState());
			pstmt.setString(10, c.getProvince());
			pstmt.setString(11, c.getCountry());
			pstmt.setString(12, c.getZipCode());
			pstmt.setString(13, c.getPhone());
			pstmt.setString(14, c.getCellPhone());
			pstmt.setString(15, c.getFax());
			pstmt.setString(16, c.getHomePage());
			pstmt.setString(17, c.getEmail());
			pstmt.setString(18, compID);
			pstmt.setString(19, c.getTexID());
			pstmt.setString(20, oBal);
			pstmt.setString(21, exCredit);
			pstmt.setString(22, c.getMemo()); // detail
			pstmt.setInt(23, istaxable); // taxable
			pstmt.setInt(24, isAlsoClient); // cvtypeid
			pstmt.setInt(25, Integer.parseInt(c.getType())); // cvcategoryid
			pstmt.setString(26, vcName); // CVCategoryName
			pstmt.setString(27, status); // may be {N, U, 0(zero)}
			int cct = (c.getCcType() == null || c.getCcType().equals("") ? 0 : Integer.parseInt(c.getCcType()));
			pstmt.setInt(28, cct); // credit card type
			pstmt.setBoolean(29, c.isIsMobilePhoneNumber());
			pstmt.setString(30, c.getMiddleName());
			pstmt.setDate(31, (c.getDateInput() == null || c.getDateInput().trim().equals("")) ? null
					: string2date(c.getDateInput()));
			pstmt.setDate(32, (c.getTerminatedDate() == null || c.getTerminatedDate().trim().equals("")) ? null
					: string2date(c.getTerminatedDate()));
			pstmt.setBoolean(33, c.isTerminated());

			Loger.log(sqlString);
			int num = pstmt.executeUpdate();
			if (num > 0) {
				System.out.println(num + " Record updated!!!");
				ret = true;
			}
			if (c.getShipping() != null && c.getShipping().trim().length() > 0)
				pinfo.updateClientVendor("ShipCarrierID", c.getShipping(), cvID);

			if (c.getPaymentType() != null && c.getPaymentType().trim().length() > 0)
				pinfo.updateClientVendor("PaymentTypeID", c.getPaymentType(), cvID);

			if (c.getRep() != null && c.getRep().trim().length() > 0)
				pinfo.updateClientVendor("SalesRepID", c.getRep(), cvID);

			if (c.getTerm() != null && c.getTerm().trim().length() > 0)
				pinfo.updateClientVendor("TermID", c.getTerm(), cvID);

			// ......updating----billing-shipping------------------------------------------START-------
			pinfo.updateVendorCreditCard(cvID, c.getCcType(), c.getCardNo(), c.getExpDate(), c.getCw2(),
					c.getCardHolderName(), c.getCardBillAddress(), c.getCardZip());

			// change status of old record...........
			pstmt = con.prepareStatement(
					"update bca_bsaddress set status='0' where clientvendorid=? and status in ('N','U')");
			pstmt.setInt(1, cvID);
			pstmt.executeUpdate();
			pstmt.close();
			// ......................status change finished.........

			int bsAddID = pinfo.getLastBsAdd() + 1;
			pinfo.insertVendorBSAddress(cvID, bsAddID, c.getBscname(), c.getBsfirstName(), c.getBslastName(),
					c.getBsaddress1(), c.getBsaddress2(), c.getBscity(), c.getBsstate(), c.getBsprovince(),
					c.getBscountry(), c.getBszipCode(), "1");

			System.out.println("bsAddressID:" + bsAddID);

			pinfo.insertVendorBSAddress(cvID, bsAddID, c.getShcname(), c.getShfirstName(), c.getShlastName(),
					c.getShaddress1(), c.getShaddress2(), c.getShcity(), c.getShstate(), c.getShprovince(),
					c.getShcountry(), c.getShzipCode(), "0");

			/*
			 * Loger.log("ANU RATE___" + c.getAnnualIntrestRate());
			 * Loger.log("MIN ____________ " + c.getMinFCharges());
			 * Loger.log("Grace Period ____________ " + c.getGracePrd());
			 */

			pinfo.insertVFCharge(cvID, useIndividualFinanceCharges, c.getAnnualIntrestRate(), c.getMinFCharges(),
					c.getGracePrd(), AssessFinanceChk, 0);

			// billing-shipping------------------------------------------END------

			// --------code to save services--------------------------START---
			int i;
			String sql;
			String serviceID = c.getTable_serID();

			String serviceBal = c.getTable_bal();
			String defaultser = c.getTable_defaultVal();
			if (defaultser == null || defaultser.isEmpty()) {
				defaultser = "0";
			}
			// Loger.log("DEFAULT______________________________________"+ defaultser);

			String invStyleID = c.getTable_invId();

			sql = "delete from bca_clientvendorservice where ClientVendorID = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, cvID);
			ps.executeUpdate();

			if (!(serviceID.equals("") || invStyleID.equals("") || serviceBal.equals(""))) {
				String temp[] = null, temp2[] = null, temp3[] = null;
				if ((serviceID != "" && serviceID != null)
						&& (invStyleID != "" && invStyleID != null) & (serviceBal != "" && serviceBal != null)) {
					temp = serviceID.split(";"); // serviceID is in form like 3;6;8;
					temp2 = invStyleID.split(";");
					temp3 = serviceBal.split(";");
				}
				java.sql.Date d = new java.sql.Date(new java.util.Date().getTime());

				for (i = 0; i < temp.length; i++) {
					/*
					 * commented on 13-05-2020 sql =
					 * "insert into bca_clientvendorservice values (?,?,?,?,?,?,?)";
					 */
					sql = "insert into bca_clientvendorservice "
							+ "(ClientVendorID,DateAdded,CompanyID,InvoiceStyleID,ServiceBalance,DEFAULTService) "
							+ "values (?,?,?,?,?,?)";
					ps = con.prepareStatement(sql);
					ps.setInt(1, cvID);
					ps.setDate(2, d);
					ps.setInt(3, Integer.parseInt(compID));
					ps.setInt(4, Integer.parseInt(temp2[i]));
					ps.setFloat(5, java.lang.Float.parseFloat(temp3[i]));
					if (Integer.parseInt(temp[i]) == Integer.parseInt(defaultser)) {
						ps.setInt(6, 1);
						/* Loger.log("EQUAL-------------------->>"); */
					} else
						ps.setInt(6, 0);
					ps.setInt(7, Integer.parseInt(temp[i]));
					ps.executeUpdate();
				}
			}
			// --------------------------code to save services
			// -------------------------------------END-------

		} catch (SQLException ee) {
			Loger.log(2, "SQLException in Class CustomerInfo," + "method -updateInsertCustomer " + ee.toString());
			ee.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					db.close(ps);
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
		return ret;
	}

	public boolean deleteCustomer(String cvID, String compId) {
		/*
		 * Function to delete the particular Customer Do not actualy DELETE the record;
		 * just UPDATE the value of deleted attribute to 1 deleted=0 means the record is
		 * not deleted, deleted=1 means deleted user can see only undeleted records
		 */

		boolean ret = false;

		Connection con = null;
		PreparedStatement pstmt = null;
		SQLExecutor db = null;
		// int rec=0;
		String sqlString = null;

		try {
 			con = db.getConnection();

			// update bca_clientvendor.....
			sqlString = "update bca_clientvendor set active=0, status='0', deleted=1 " + " where clientvendorId=?"
					+ " and status in ('U','N') and CompanyID=?";

			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, cvID);
			pstmt.setString(2, compId);
			pstmt.executeUpdate();
			pstmt.close();

			// update bca_bsaddress....
			sqlString = "update bca_bsaddress set status='0' where clientvendorid=?" + " and status in ('N','U')";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, cvID);
			pstmt.executeUpdate();
			pstmt.close();

			// update bca_creditcard....
			sqlString = "update bca_creditcard set bca_creditcard.active=0 where clientvendorid=?";

			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, cvID);
			pstmt.executeUpdate();
			pstmt.close();

			// set flag to indicate success & return value...
			ret = true;
		} catch (Exception e) {
			Loger.log(2, "Exception... CustomerInfo.deleteCustomer(). --->" + e.getMessage());
			ret = false;
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

	public void getServices(HttpServletRequest request, String compId) {

		ArrayList<UpdateInvoiceForm> serviceList = new ArrayList<UpdateInvoiceForm>();
		ArrayList<UpdateInvoiceForm> invoiceName = new ArrayList<UpdateInvoiceForm>();
		// ArrayList balenceDetails = new ArrayList();
		ResultSet rs = null;
		ResultSet rs1 = null;
		Connection con = null;

		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		con = db.getConnection();

		String sqlString = "select * from bca_servicetype";
		String sqlString1 = "select  * from bca_invoicestyle where Active=1";

		try {
			pstmt = con.prepareStatement(sqlString);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				UpdateInvoiceForm uform = new UpdateInvoiceForm();
				uform.setServiceID(rs.getInt(1));
				uform.setServiceName(rs.getString(2));
				uform.setInvoiceStyleId(rs.getInt(3));
				serviceList.add(uform);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		request.setAttribute("ServiceList", serviceList);

		try {
			con = db.getConnection();
			pstmt1 = con.prepareStatement(sqlString1);
			rs1 = pstmt1.executeQuery();
			while (rs1.next()) {
				UpdateInvoiceForm uform = new UpdateInvoiceForm();
				// Loger.log("The Incoice style id is " + rs1.getString(1));
				uform.setInvoiceStyleId(rs1.getInt(1));
				// Loger.log("The Invoice Style name is " + rs1.getString(2));
				uform.setInvoiceStyle(rs1.getString(2));
				invoiceName.add(uform);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
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
	}

	public java.sql.Date string2date(String d) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

		Date d1 = null;
		try {

			d1 = sdf.parse(d);

		} catch (ParseException e) {
			Loger.log(2, "ParseException" + e.getMessage());

		}

		return (d1 != null ? new java.sql.Date(d1.getTime()) : new java.sql.Date(new Date().getTime()));

	}

	public String date2String(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String dateString = sdf.format(date);
		return dateString;
	}

	public String getStatesName(String sid) {
		String sname = "";

		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			String sqlString = "select StateName  from state where StateID = ? ";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, sid);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				sname = rs.getString(1);
			}

		} catch (SQLException ee) {
			Loger.log("Error in State Name Selection" + ee);
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
		return sname;
	}

	public ArrayList getAccountPayableReport(String cId, HttpServletRequest request, String datesCombo, String fromDate,
			String toDate, String sortBy, CustomerForm form) {
		Connection con = null;
		Statement stmt = null;

		con = db.getConnection();
		ArrayList<CustomerForm> objList = new ArrayList<>();
		ResultSet rs = null;
		double totalBalance = 0.00;
		String sql = "";
		CustomerInfo cinfo = new CustomerInfo();
		String dateBetween = "";
		ArrayList<Date> selectedRange = new ArrayList<>();
		DateInfo dInfo = new DateInfo();

		if (datesCombo != null && !datesCombo.equals("8")) {
			if (datesCombo != null && !datesCombo.equals("")) {
				selectedRange = dInfo.selectedIndex(Integer.parseInt(datesCombo));
				if (!selectedRange.isEmpty() && selectedRange != null) {
					form.setFromDate(cinfo.date2String(selectedRange.get(0)));
					form.setToDate(cinfo.date2String(selectedRange.get(1)));
				}
				if (selectedRange != null && !selectedRange.isEmpty()) {
					dateBetween = " AND inv.DateAdded BETWEEN Timestamp ('"
							+ JProjectUtil.getDateFormaterCommon().format(selectedRange.get(0)) + "') AND Timestamp ('"
							+ JProjectUtil.getDateFormaterCommon().format(selectedRange.get(1)) + "')";
				}
			}
		} else if (datesCombo != null && datesCombo.equals("8")) {
			if (fromDate.equals("") && toDate.equals("")) {
				dateBetween = "";
			} else if (!fromDate.equals("") && toDate.equals("")) {
				dateBetween = " AND inv.DateAdded >= Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cinfo.string2date(fromDate) + "')");
			} else if (fromDate.equals("") && !toDate.equals("")) {
				dateBetween = " AND inv.DateAdded <= Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cinfo.string2date(toDate) + "')");
			} else {
				dateBetween = " AND inv.DateAdded BETWEEN Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(cinfo.string2date(fromDate))
						+ "') AND Timestamp ('" + JProjectUtil.getDateFormaterCommon().format(cinfo.string2date(toDate))
						+ "')";
			}
		}

		try {
			sql += "SELECT inv.invoiceid, " + "       inv.ordernum, " + "       inv.ponum, "
					+ "       date_format(inv.dateadded,'%m-%d-%Y')AS DateAdded, " + "       inv.clientvendorid, "
					+ "       inv.adjustedtotal, " + "       inv.balance, " + "       cv.NAME  AS cvName, "
					+ "       cv.firstname, " + "       cv.lastname, " + "       rep.NAME AS repName "
					+ "FROM   ( bca_clientvendor AS cv " + "         INNER JOIN bca_invoice AS inv "
					+ "                 ON inv.clientvendorid = cv.clientvendorid ) "
					+ "       LEFT JOIN bca_salesrep AS rep " + "              ON rep.salesrepid = inv.salesrepid "
					+ "WHERE  inv.companyid = '" + cId + "'" + "       AND inv.invoicetypeid IN ( 2 ) "
					+ "       AND ( cv.status = 'U' " + "              OR cv.status = 'N' ) "
					+ "       AND cv.deleted = 0 " + dateBetween + "ORDER  BY inv.dateadded DESC";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CustomerForm c = new CustomerForm();
				c.setPoNum(rs.getInt(3));
				c.setInvoiceId(rs.getInt(1));
				c.setDateAdded(rs.getString(4));
				String name = rs.getString(8);
				String firstName = rs.getString(9);
				String lastName = rs.getString(10);
				c.setFullName(name.equals("") ? firstName + " " + lastName : name);
				c.setTotal(rs.getDouble(6));
				c.setBalance(rs.getDouble(7));
				totalBalance += rs.getDouble(7);
				objList.add(c);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
		request.setAttribute("totalBalance", totalBalance);
		return objList;
	}

	public void getProfitLossDetailReport(String datesCombo, String fromDate, String toDate, String sortBy, String cId,
			HttpServletRequest request, CustomerForm form) {
		Connection con = null;
		Statement stmt1 = null, stmt2 = null, stmt3 = null;
		ResultSet rs1 = null, rs2 = null, rs3 = null;

		ArrayList<CustomerForm> AccountReceivable = new ArrayList<>();
		ArrayList<CustomerForm> AccountPayable = new ArrayList<>();
		ArrayList<CustomerForm> temp = new ArrayList<>();
		ArrayList<CustomerForm> billPayable = new ArrayList<>();
		con = db.getConnection();
		DateInfo dInfo = new DateInfo();
		String dateBetween = "";
		ArrayList<Date> selectedRange = new ArrayList<>();
		String sql1 = "";
		double totalUncategorisedIncome = 0D;
		double totalCOGS = 0D;
		double grossProfit = 0D;
		double totalBillAmount = 0D;
		double totalVendorOpBal = 0D;
		double netIncome = 0D;
		double amt = 0D;
		if (datesCombo != null && !datesCombo.equals("8")) {
			if (datesCombo != null && !datesCombo.equals("")) {
				selectedRange = dInfo.selectedIndex(Integer.parseInt(datesCombo));
				if (!selectedRange.isEmpty() && selectedRange != null) {
					form.setFromDate(date2String(selectedRange.get(0)));
					form.setToDate(date2String(selectedRange.get(1)));
				}
				if (selectedRange != null && !selectedRange.isEmpty()) {
					dateBetween = " AND DateAdded BETWEEN Timestamp ('"
							+ JProjectUtil.getDateFormaterCommon().format(selectedRange.get(0)) + "') AND Timestamp ('"
							+ JProjectUtil.getDateFormaterCommon().format(selectedRange.get(1)) + "')";
				}
			}
		} else if (datesCombo != null && datesCombo.equals("8")) {
			if (fromDate.equals("") && toDate.equals("")) {
				dateBetween = "";
			} else if (!fromDate.equals("") && toDate.equals("")) {
				dateBetween = " AND DateAdded >= Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(string2date(fromDate) + "')");
			} else if (fromDate.equals("") && !toDate.equals("")) {
				dateBetween = " AND DateAdded <= Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(string2date(toDate) + "')");
			} else {
				dateBetween = " AND DateAdded BETWEEN Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(string2date(fromDate)) + "') AND Timestamp ('"
						+ JProjectUtil.getDateFormaterCommon().format(string2date(toDate)) + "')";
			}
		}
		try {
			stmt1 = con.createStatement();
			stmt2 = con.createStatement();
			stmt3 = con.createStatement();

			sql1 += "SELECT clientvendorid, " + "       NAME, " + "       customeropendebit, "
					+ "       vendoropendebit, " + "       cvtypeid " + "FROM   bca_clientvendor "
					+ "WHERE  status = 'N' " + "       AND active = 1 " + "       AND companyid = '" + cId + "'"
					+ dateBetween + "ORDER  BY dateadded";

			rs1 = stmt1.executeQuery(sql1);
			while (rs1.next()) {
				CustomerForm c = new CustomerForm();
				c.setCvTypeID(rs1.getInt("CVTypeID"));
				if (c.getCvTypeID() == 2) {
					amt = rs1.getDouble("CustomerOpenDebit");
					if (amt == 0) {
						continue;
					}
					c.setClientVendorID(rs1.getString("ClientVendorID"));
					c.setFirstName(rs1.getString("Name"));
					c.setTotal(amt);
					c.setType("Customer");
					c.setMemo("Opening Balance");
					totalUncategorisedIncome += amt;
					AccountReceivable.add(c);
				} else {
					amt = rs1.getDouble("VendorOpenDebit");
					if (amt == 0) {
						continue;
					}
					CustomerForm c1 = new CustomerForm();
					c1.setClientVendorID(rs1.getString("ClientVendorID"));
					c1.setFirstName(rs1.getString("Name"));
					c1.setTotal(amt);
					c1.setType("Vendor");
					c1.setMemo("Opening Balance");
					totalVendorOpBal += amt;
					/* temp.add(c1); */
					/* AccountReceivable.add(c1); */
				}
			}

			String sql2 = "SELECT DISTINCT( ordernum ) AS ID," + "               inv.dateadded, "
					+ "               cv.NAME                           AS NAME, "
					+ "               ( item.purchaseprice * cart.qty ) AS PP, "
					+ "               ( item.saleprice * cart.qty )     AS SP " + "FROM   bca_iteminventory AS item "
					+ "       INNER JOIN (bca_clientvendor AS cv " + "                   INNER JOIN (bca_cart AS cart "
					+ "                               INNER JOIN bca_invoice AS inv "
					+ "                                       ON cart.invoiceid = inv.invoiceid) "
					+ "                           ON cv.clientvendorid = inv.clientvendorid) "
					+ "               ON item.inventoryid = cart.inventoryid " + "WHERE  inv.invoicestatus <> 1 "
					+ dateBetween.replaceAll("DateAdded", "inv.DateAdded") + "       AND invoicetypeid = 1 "
					+ "       AND inv.companyid = '" + cId + "'" + "       AND item.itemtypeid <> 6 "
					+ "       AND status = 'N' " + "ORDER  BY inv.dateadded";

			rs2 = stmt2.executeQuery(sql2);
			while (rs2.next()) {
				CustomerForm c = new CustomerForm();
				CustomerForm pPrice = new CustomerForm();
				pPrice.setNumber(rs2.getString("ID"));
				c.setNumber(rs2.getString("ID"));
				c.setFirstName(rs2.getString("Name"));
				pPrice.setFirstName(rs2.getString("Name"));
				c.setTotal(rs2.getDouble("SP"));
				pPrice.setTotal(rs2.getDouble("PP"));
				c.setType("Invoice");
				pPrice.setType("Invoice");
				c.setMemo("Account Receivable");
				pPrice.setMemo("Account Payable");
				totalUncategorisedIncome += c.getTotal();
				totalCOGS += pPrice.getTotal();
				AccountPayable.add(pPrice);
				AccountReceivable.add(c);
			}
			String sql3 = "SELECT b.billnum        AS ID, " + "       c.NAME           AS NAME, "
					+ "       bd.expenseamount AS AMOUNT " + "FROM   bca_category AS c "
					+ "       INNER JOIN (bca_bill AS b " + "                   INNER JOIN bca_billdetail AS bd "
					+ "                           ON b.billnum = bd.billnum) "
					+ "               ON c.categoryid = bd.expenseacctid " + "WHERE  bd.expenseamount <> 0 "
					+ "       AND bd.inventoryid = 0 " + "       AND bd.companyid = '" + cId + "'"
					+ dateBetween.replaceAll("DateAdded", "b.DateAdded");
			rs3 = stmt3.executeQuery(sql3);
			while (rs3.next()) {
				CustomerForm f = new CustomerForm();
				f.setNumber(rs3.getString("ID"));
				f.setFirstName(rs3.getString("NAME"));
				f.setTotal(rs3.getDouble("AMOUNT"));
				f.setType("Bill");
				f.setMemo("Account Payable");
				totalBillAmount += f.getTotal();
				billPayable.add(f);
			}
			grossProfit = totalUncategorisedIncome - totalCOGS;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs1 != null) {
					db.close(rs1);
				}
				if (stmt1 != null) {
					db.close(stmt1);
				}
				if (rs2 != null) {
					db.close(rs2);
				}
				if (stmt2 != null) {
					db.close(stmt2);
				}
				if (rs3 != null) {
					db.close(rs3);
				}
				if (stmt3 != null) {
					db.close(stmt3);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("AccountReceivable", AccountReceivable);
		request.setAttribute("AccountPayable", AccountPayable);
		request.setAttribute("GrossProfit", grossProfit);
		request.setAttribute("Total_COGS", totalCOGS);
		request.setAttribute("totalUncategorisedIncome", totalUncategorisedIncome);
		request.setAttribute("BillPayable", billPayable);
	}

	public ArrayList customerDetailsSortByFirstName(String compId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		ArrayList<CustomerForm> objList = new ArrayList<CustomerForm>();
		ResultSet rs = null;
		con = db.getConnection();
 		try {
			String sqlString = "select distinct ClientVendorID,Name,FirstName,LastName, Address1,Address2,"
					+ "City,State,ZipCode, Phone,CellPhone,Fax,Email,date_format(DateAdded,'%m-%d-%Y') as DateAdded,Country from bca_clientvendor  "
					+ "where  (Status like 'N' or Status like 'U')  and  (CVTypeID = '1' or CVTypeID = '2') "
					+ "and ( Deleted = '0') and CompanyID='" + compId + "'   order by FirstName ";

			pstmt = con.prepareStatement(sqlString);
			Loger.log(sqlString);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CustomerForm customer = new CustomerForm();
				customer.setClientVendorID(rs.getString(1));
				customer.setCname(rs.getString(2) + "(" + rs.getString(3) + " " + rs.getString(4) + ")");
				customer.setFirstName(rs.getString(3));
				customer.setLastName(rs.getString(4));
				customer.setAddress1(rs.getString(5));
				customer.setAddress2(rs.getString(6));
				customer.setCity(rs.getString(7));
				customer.setStateName(cs.getStatesName(rs.getString(8)));
				customer.setZipCode(rs.getString(9));
				customer.setPhone(rs.getString(10));
				customer.setCellPhone(rs.getString(11));
				customer.setFax(rs.getString(12));
				customer.setEmail(rs.getString(13));
				customer.setDateAdded(rs.getString(14));
				customer.setCountry(cs.getCountryName(rs.getString(15)));
				customer.setFullName(rs.getString(3) + " " + rs.getString(4)); // changed by pritesh 10-09-2018
				customer.setBillTo(rs.getString(3) + rs.getString(4)); // changed by jay 05-11-2018
				objList.add(customer);
			}

		} catch (SQLException ee) {
			Loger.log(2, "SQL Error in Class TaxInfo and  method -customerDetailsSortByFirstName " + ee.toString());
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
			} catch (Exception ex) {
				Loger.log(2, "SQL Error in Class TaxInfo and  method -customerDetailsSortByFirstName " + ex.toString());

			}
		}
		return objList;
	}

	public ArrayList<CustomerForm> customerDetailsSortByLastName(String compId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		ArrayList<CustomerForm> objList = new ArrayList<CustomerForm>();
		ResultSet rs = null;
		con = db.getConnection();
 		try {
			String sqlString = "select distinct ClientVendorID,Name,FirstName,LastName, Address1,Address2,"
					+ "City,State,ZipCode, Phone,CellPhone,Fax,Email,date_format(DateAdded,'%m-%d-%Y') as DateAdded,Country from bca_clientvendor  "
					+ "where  (Status like 'N' or Status like 'U')  and  (CVTypeID = '1' or CVTypeID = '2') "
					+ "and ( Deleted = '0') and CompanyID='" + compId + "'   order by LastName ";

			pstmt = con.prepareStatement(sqlString);
			// Loger.log(sqlString);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CustomerForm customer = new CustomerForm();
				customer.setClientVendorID(rs.getString(1));
				customer.setCname(rs.getString(2) + "(" + rs.getString(3) + " " + rs.getString(4) + ")");
				customer.setFirstName(rs.getString(3));
				customer.setLastName(rs.getString(4));
				customer.setAddress1(rs.getString(5));
				customer.setAddress2(rs.getString(6));
				customer.setCity(rs.getString(7));
				customer.setStateName(cs.getStatesName(rs.getString(8)));
				customer.setZipCode(rs.getString(9));
				customer.setPhone(rs.getString(10));
				customer.setCellPhone(rs.getString(11));
				customer.setFax(rs.getString(12));
				customer.setEmail(rs.getString(13));
				customer.setDateAdded(rs.getString(14));
				customer.setCountry(cs.getCountryName(rs.getString(15)));
				customer.setFullName(rs.getString(3) + " " + rs.getString(4)); // changed by pritesh 10-09-2018
				customer.setBillTo(rs.getString(3) + rs.getString(4)); // changed by jay 05-11-2018
				objList.add(customer);
			}

		} catch (SQLException ee) {
			Loger.log(2, "SQL Error in Class TaxInfo and  method -customerDetailsSortByLastName " + ee.toString());
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
			} catch (Exception ex) {
				Loger.log(2, "SQL Error in Class TaxInfo and  method -customerDetailsSortByLastName " + ex.toString());

			}
		}
		return objList;
	}

	public ArrayList<CustomerForm> customerDetailsSort(String compId, String sort) {
		Connection con = null;
		PreparedStatement pstmt = null;

		ArrayList<CustomerForm> objList = new ArrayList<CustomerForm>();
		ResultSet rs = null;
		con = db.getConnection();
 		try {
			String sqlString = "select distinct ClientVendorID,Name,FirstName,LastName, Address1,Address2,"
					+ "City,State,ZipCode, Phone,CellPhone,Fax,Email,date_format(DateAdded,'%m-%d-%Y') as DateAdded,Country from bca_clientvendor  "
					+ "where  (Status like 'N' or Status like 'U')" + "and ( Deleted = '0') and CompanyID='" + compId
					+ "' order by " + sort;

			pstmt = con.prepareStatement(sqlString);
			// Loger.log(sqlString);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CustomerForm customer = new CustomerForm();
				customer.setClientVendorID(rs.getString(1));
				customer.setCname(rs.getString(2) + "(" + rs.getString(3) + " " + rs.getString(4) + ")");
				customer.setFirstName(rs.getString(3));
				customer.setLastName(rs.getString(4));
				customer.setAddress1(rs.getString(5));
				customer.setAddress2(rs.getString(6));
				customer.setCity(rs.getString(7));
				customer.setStateName(cs.getStatesName(rs.getString(8)));
				customer.setZipCode(rs.getString(9));
				customer.setPhone(rs.getString(10));
				customer.setCellPhone(rs.getString(11));
				customer.setFax(rs.getString(12));
				customer.setEmail(rs.getString(13));
				customer.setDateAdded(rs.getString(14));
				customer.setCountry(cs.getCountryName(rs.getString(15)));
				customer.setFullName(rs.getString(3) + " " + rs.getString(4)); // changed by pritesh 10-09-2018
				customer.setBillTo(rs.getString(3) + rs.getString(4)); // changed by jay 05-11-2018
				objList.add(customer);
			}

		} catch (SQLException ee) {
			Loger.log(2, "SQL Error in Class TaxInfo and  method -customerDetailsSort " + ee.toString());
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
			} catch (Exception ex) {
				Loger.log(2, "SQL Error in Class TaxInfo and  method -customerDetailsSortByLastName " + ex.toString());

			}
		}
		return objList;
	}

	public ArrayList<EstimationForm> sortCustomer(String compId, String sort) {
		Connection con = null;
		PreparedStatement pstmt = null;

		ArrayList<EstimationForm> objList = new ArrayList<EstimationForm>();
		ResultSet rs = null;
		con = db.getConnection();
 		try {
			String sqlString = "select distinct ClientVendorID,Name,FirstName,LastName, Address1,Address2,"
					+ "City,State,ZipCode, Phone,CellPhone,Fax,Email,date_format(DateAdded,'%m-%d-%Y') as DateAdded,Country from bca_clientvendor  "
					+ "where  (Status like 'N' or Status like 'U')" + "and ( Deleted = '0') and CompanyID='" + compId
					+ "' order by " + sort;

			pstmt = con.prepareStatement(sqlString);
			// Loger.log(sqlString);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				EstimationForm customer = new EstimationForm();
				customer.setClientVendorID(rs.getString(1));
				customer.setCname(rs.getString(2) + "(" + rs.getString(3) + " " + rs.getString(4) + ")");
				customer.setFirstName(rs.getString(3));
				customer.setLastName(rs.getString(4));
				customer.setAddress1(rs.getString(5));
				customer.setAddress2(rs.getString(6));
				customer.setCity(rs.getString(7));
				customer.setStateName(cs.getStatesName(rs.getString(8)));
				customer.setZipCode(rs.getString(9));
				customer.setPhone(rs.getString(10));
				customer.setCellPhone(rs.getString(11));
				customer.setFax(rs.getString(12));
				customer.setEmail(rs.getString(13));
				customer.setDateAdded(rs.getString(14));
				customer.setCountry(cs.getCountryName(rs.getString(15)));
				customer.setFullName(rs.getString(3) + " " + rs.getString(4)); // changed by pritesh 10-09-2018
				customer.setBillTo(rs.getString(3) + rs.getString(4)); // changed by jay 05-11-2018
				objList.add(customer);
			}

		} catch (SQLException ee) {
			Loger.log(2, "SQL Error in Class TaxInfo and  method -customerDetailsSort " + ee.toString());
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
			} catch (Exception ex) {
				Loger.log(2, "SQL Error in Class TaxInfo and  method -customerDetailsSortByLastName " + ex.toString());

			}
		}
		return objList;
	}

	public void setNewUnitPrice(String companyID, int itemId, double price) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = db.getConnection();
		try {
			String sqlString = "update bca_iteminventory set SalePrice=? where InventoryID =? and CompanyID=?";
			pstmt = con.prepareStatement(sqlString);
			price = Double.parseDouble(new DecimalFormat("##.##").format(price));
			pstmt.setDouble(1, price);
			pstmt.setInt(2, itemId);
			pstmt.setString(3, companyID);
			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println(count + " Row updated...");
			}
		} catch (SQLException ee) {
			Loger.log(2, "SQL Error in Class CustomerInfo and  method -setNewUnitPrice " + ee.toString());
		}

		finally {
			try {
				if (pstmt != null) {
					db.close(pstmt);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception ex) {
				Loger.log(2, "SQL Error in Class CustomerInfo and method -setNewUnitPrice " + ex.toString());

			}
		}
	}

	public void setNewitemName(String companyID, int itemId, String itemName) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = db.getConnection();
		try {
			/*
			 * String sqlString =
			 * "update bca_inventory set InventoryName=?,InventoryDescription=? where InventoryID =? and CompanyID=?"
			 * ;
			 */
			String sqlString = "update bca_iteminventory set InventoryName=? where InventoryID =? and CompanyID=?";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, itemName);
			/* pstmt.setString(2,itemName); */
			pstmt.setInt(2, itemId);
			pstmt.setString(3, companyID);
			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println(count + " Row updated...");
			}
		} catch (SQLException ee) {
			Loger.log(2, "SQL Error in Class CustomerInfo and  method -setNewitemName " + ee.toString());
		} finally {
			try {
				if (pstmt != null) {
					db.close(pstmt);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception ex) {
				Loger.log(2, "SQL Error in Class CustomerInfo and method -setNewitemName " + ex.toString());

			}
		}
	}

	public void setNewitemNameEstimation(String companyID, int itemId, String itemName) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = db.getConnection();
		try {
			/*
			 * String sqlString =
			 * "update bca_inventory set InventoryName=?,InventoryDescription=? where InventoryID =? and CompanyID=?"
			 * ;
			 */
			String sqlString = "update bca_iteminventory set InventoryName=? where InventoryID =? and CompanyID=?";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, itemName);
			/* pstmt.setString(2,itemName); */
			pstmt.setInt(2, itemId);
			pstmt.setString(3, companyID);
			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println(count + " Row updated...");
			}
		} catch (SQLException ee) {
			Loger.log(2, "SQL Error in Class CustomerInfo and  method -setNewitemNameEstimation " + ee.toString());
		} finally {
			try {
				if (pstmt != null) {
					db.close(pstmt);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception ex) {
				Loger.log(2, "SQL Error in Class CustomerInfo and method -setNewitemNameEstimation " + ex.toString());

			}
		}
	}

	public void setUnitPriceEstimation(String companyID, int itemId, double price) {
		Connection con = null;
		PreparedStatement pstmt = null;

		con = db.getConnection();
		try {
			String sqlString = "update bca_iteminventory set SalePrice=? where InventoryID =? and CompanyID=?";
			pstmt = con.prepareStatement(sqlString);
			price = Double.parseDouble(new DecimalFormat("##.##").format(price));
			pstmt.setDouble(1, price);
			pstmt.setInt(2, itemId);
			pstmt.setString(3, companyID);
			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println(count + " Row updated...");
			}
		} catch (SQLException ee) {
			Loger.log(2, "SQL Error in Class CustomerInfo and  method -setNewUnitPrice " + ee.toString());
		} finally {
			try {
				if (pstmt != null) {
					db.close(pstmt);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception ex) {
				Loger.log(2, "SQL Error in Class CustomerInfo and method -setUnitPriceEstimation " + ex.toString());
			}
		}
	}
}
