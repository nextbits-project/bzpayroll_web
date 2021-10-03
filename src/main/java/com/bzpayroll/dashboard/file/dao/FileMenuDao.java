package com.bzpayroll.dashboard.file.dao;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts.util.LabelValueBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bzpayroll.common.ClientVendorHasService;
import com.bzpayroll.common.ConstValue;
import com.bzpayroll.common.CustomercsvBean;
import com.bzpayroll.common.TblStore;
import com.bzpayroll.common.db.SQLExecutor;
import com.bzpayroll.common.utility.JProjectUtil;
import com.bzpayroll.dashboard.file.forms.ClientVendor;
import com.bzpayroll.dashboard.file.forms.CompanyInfoDto;
 
@Service
public class FileMenuDao {

	@Autowired
	private SQLExecutor db;

	/* It checks the number of pages */
	public boolean validation(HttpServletRequest request, CompanyInfoDto companyInfoDto) throws ParseException {

		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		int a = companyInfoDto.getInvoiceNo();

		String fromdate = companyInfoDto.getFromDate();
		String todate = companyInfoDto.getToDate();

		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

		if (fromdate == null) {
			return true;
		} else {
			Date parsed = format.parse(fromdate);
			if (a == 0) {
				System.out.println("Multi-Print.Please_select_at_least_one_number_of_copy");
				return false;
			} else if (a != 0 && a <= 0) {
				System.out.println("Multi-Print.Please_select_at_least_one_number_of_copy");
				return false;
			} /**
				 * Code By : $K Description : Following codeis used to diaplay alert when the
				 * number of pages in To<From.
				 */
			else if (companyInfoDto.getPrintInvoiceFrom() > companyInfoDto.getPrintInvoiceTo()) {
				System.out.println("Multi-Print.To_field_must_be_greater_than_from_field");
				return false;
			} else if (parsed == null) {
				System.out.println("Multi-Print.Date_To_field_must_be_greater_than_Date_from_field");
				return false;
			} else {

				if ((companyInfoDto.isShippingMethodCheckBox()
						&& companyInfoDto.getTxtShippingMethodPriority().equals(""))
						|| (companyInfoDto.isItemTitleCheckBox() && companyInfoDto.getTxtItemTitlePriority().equals(""))
						|| (companyInfoDto.isDestinationCheckBox()
								&& companyInfoDto.getTxtDestinationPriority().equals(""))
						|| (companyInfoDto.isSpecialHandingCheckBox()
								&& companyInfoDto.getTxtSpecialHandlingPriority().equals(""))
						|| (companyInfoDto.isLocationCheckBox()
								&& companyInfoDto.getTxtLocationPriority().equals(""))) {
					System.out.println("Multi-Print.Please_set_priority");
					return false;
				}
			}
		}

		return true;
	}

	private void write() {
		File file = null;
		String fullpath = null;
		ObjectOutputStream objectoutputstream = null;

		file = new File(System.getProperty("user.dir") + "\\settings");

		if (!file.exists()) {
			if (!file.mkdir()) {
				System.out.println("failed to create dir");
			}
		}
		try {
			fullpath = System.getProperty("user.dir") + "\\settings\\MultiPrint.conf";
			objectoutputstream = new ObjectOutputStream(new FileOutputStream(fullpath));
			// objectoutputstream.writeObject(prop);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		} finally {
			if (objectoutputstream != null) {
				try {
					objectoutputstream.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

	}

	private void updateProperties2DB(HttpServletRequest request, CompanyInfoDto companyInfoDto) {

		// properties.printByOrder = printRadioButton.isSelected();
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");

		Connection con = null;
		int InvoiceTypeID = companyInfoDto.getInvoiceNo();

		try {
			if (InvoiceTypeID == 7) { // For Sales Order
				companyInfoDto.printSalesOrderFrom = companyInfoDto.getPrintInvoiceFrom();
			} else {
				companyInfoDto.printInvoiceFrom = companyInfoDto.getPrintInvoiceFrom();
			}
		} catch (NumberFormatException e) {
			companyInfoDto.printSalesOrderFrom = 0;
			companyInfoDto.printInvoiceFrom = 0;
		}

		try {
			if (InvoiceTypeID == 7) { // For Sales Order
				companyInfoDto.printSalesOrderTo = companyInfoDto.getPrintInvoiceTo();
			} else {
				companyInfoDto.printInvoiceTo = companyInfoDto.getPrintInvoiceTo();
			}
		} catch (NumberFormatException e) {
			companyInfoDto.printInvoiceTo = 0;
			companyInfoDto.printSalesOrderTo = 0;
		}

		companyInfoDto.printDateFrom = companyInfoDto.getFromDate();
		companyInfoDto.printDateTo = companyInfoDto.getToDate();

		Statement stmt = null;
		ResultSet rs = null;

		String sql = "" + "UPDATE bca_preference " + "SET    printbyorder = '" + companyInfoDto.isPrintByOrder() + "', "
				+ "       printinvoicefrom = '" + companyInfoDto.getPrintInvoiceFrom() + "', "
				+ "       printinvoiceto = '" + companyInfoDto.getPrintInvoiceTo() + "', " + "       printsalesfrom = '"
				+ companyInfoDto.getPrintSalesOrderFrom() + "', " + "       printsalesto = '"
				+ companyInfoDto.getPrintSalesOrderTo() + "', " + "       printdatefrom = '"
				+ companyInfoDto.getPrintDateFrom() + "', " + "       printdateto = '" + companyInfoDto.getPrintDateTo()
				+ "', " + "       invoicestoprint = '" + companyInfoDto.getInvoiceNo() + "', "
				+ "       pagesort_shippingmethod = '" + companyInfoDto.getSortByShippingMethodPriority() + "', "
				+ "       pagesort_itemtitle = '" + companyInfoDto.getTxtItemTitlePriority() + "', "
				+ "       pagesort_destination = '" + companyInfoDto.getSortbyLocationPriority() + "', "
				+ "       pagesort_specialhanding = '" + companyInfoDto.getSortbySpecialHandingPriority() + "', "
				+ "       pagesort_location = '" + companyInfoDto.isLocationCheckBox() + "', "
				+ "       skipprinted = '" + companyInfoDto.isSkipPrinted() + "', " + "       packingslipstoprint = '"
				+ companyInfoDto.getPackingSlipNo() + "', " + "       printpapertype = '" + companyInfoDto.getPaper()
				+ "', " + "       printcoupon = '" + companyInfoDto.isPrintCoupon() + "', "
				+ "       couponlocation = '" + companyInfoDto.getCouponLocation() + "', "
				+ "       printgiftmessage = '" + companyInfoDto.isPrintAmzaonGiftMessage() + "', "
				+ "       printtestpage = '" + (companyInfoDto.isPrintTestPage() ? 1 : 0) + "' "
				+ "WHERE  companyid = '" + compId + "'";

		try {
			con = db.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
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

	}

	public boolean uploadCustomerFile(MultipartFile selectedFile, HttpServletRequest request, String type) {
		File file = new File(selectedFile.getOriginalFilename());
		ArrayList al = new ArrayList();
		String line = "";
		String cvsSplitBy = ",";
		boolean b = false;
		try {
			OutputStream os = new FileOutputStream(file);
			InputStream is = new BufferedInputStream(selectedFile.getInputStream());
			int count;
			byte buf[] = new byte[4096];
			while ((count = is.read(buf)) > -1) {
				os.write(buf, 0, count);
			}
			is.close();
			os.close();
			String[] name = file.toString().split("\\.");
			if (name[1].equals("csv")) {
				int count1 = 0;
				BufferedReader Bufferederreader = null;
				Bufferederreader = new BufferedReader(new FileReader(file));
				while ((line = Bufferederreader.readLine()) != null) {
					String[] country = line.split(cvsSplitBy);
					System.out.println("-----" + country[0]);
					try {
						al.add(0, country[0]);
					} catch (Exception e) {
						al.add(0, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(1, country[1]);
					} catch (Exception e) {
						al.add(1, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(2, country[2]);
					} catch (Exception e) {
						al.add(2, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(3, country[3]);
					} catch (Exception e) {

						al.add(3, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(4, country[4]);
					} catch (Exception e) {
						al.add(4, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(5, country[5]);
					} catch (Exception e) {
						al.add(5, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(6, country[6]);
					} catch (Exception e) {
						al.add(6, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(7, country[7]);
					} catch (Exception e) {
						al.add(7, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(8, country[8]);
					} catch (Exception e) {
						al.add(8, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(9, country[9]);
					} catch (Exception e) {
						al.add(9, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(10, country[10]);
					} catch (Exception e) {
						al.add(10, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(11, country[11]);
					} catch (Exception e) {
						al.add(11, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(12, country[12]);
					} catch (Exception e) {
						al.add(12, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(13, country[13]);
					} catch (Exception e) {
						al.add(13, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(14, country[14]);
					} catch (Exception e) {
						al.add(14, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(15, country[15]);
					} catch (Exception e) {
						al.add(15, 0);
						System.out.println("data not added properly");
					}
					// al.add(16, country[16]);

					try {
						b = insertdataintodatabase(al, request, type);

					} catch (Exception ex) {

						System.err.println(
								"exception bcoz not upload proper csv file in customer upload internalpanelcustomer.java"
										+ ex);

					}
					al.clear();
				}
				;
				System.out.println(al);
			}

			else {
				if (name[1].equals("xlsx")) {
					/*
					 * FileInputStream inputStream=null; XSSFWorkbook workbook=null; inputStream =
					 * new FileInputStream(file); workbook=new XSSFWorkbook(inputStream); XSSFSheet
					 * firstSheet = workbook.getSheetAt(0); Iterator<Row> iterator =
					 * firstSheet.iterator(); //row int count1=0;
					 */
				} else {
					FileInputStream inputStream = null;
					HSSFWorkbook workbook = null;
					inputStream = new FileInputStream(file);
					workbook = new HSSFWorkbook(inputStream);
					HSSFSheet firstSheet = workbook.getSheetAt(0);
					Iterator<Row> iterator = firstSheet.rowIterator();
					int count2 = 0;
					String Data;
					while (iterator.hasNext()) {
						HSSFRow nextRow = (HSSFRow) iterator.next();
						Iterator<Cell> cellIterator = nextRow.cellIterator();
						while (cellIterator.hasNext()) {
							HSSFCell cell = (HSSFCell) cellIterator.next();
							try {
								Data = cell.getStringCellValue();
							} catch (Exception e) {
								// TODO: handle exception
								Data = "0";
							}
							if (!((Data.equalsIgnoreCase("Company Name")) || (Data.equalsIgnoreCase("Mr/Ms"))
									|| (Data.equalsIgnoreCase("Company")) || (Data.equalsIgnoreCase("FirstName"))
									|| (Data.equalsIgnoreCase("LastName")) || (Data.equalsIgnoreCase("Address1"))
									|| (Data.equalsIgnoreCase("Address2")) || (Data.equalsIgnoreCase("City"))
									|| (Data.equalsIgnoreCase("State")) || (Data.equalsIgnoreCase("ZipCode"))
									|| (Data.equalsIgnoreCase("Country")) || (Data.equalsIgnoreCase("Phone"))
									|| (Data.equalsIgnoreCase("CellPhone")) || (Data.equalsIgnoreCase("Fax"))
									|| (Data.equalsIgnoreCase("Email")) || (Data.equalsIgnoreCase("DateAdded"))
									|| (Data.equalsIgnoreCase("Active")) || (Data.equalsIgnoreCase("COMPANY NAME")))) {
								System.out.println("cell++++" + Data);
								System.out.println("cell1" + Data);
								if (Data != null) {
									al.add(count2, Data);
									System.out.println(" al.get(0)" + al.get(count2));
									count2++;
								}
							} else {
								al.add(count2, Data);
							}

						}
						al.get(13).toString();
						b = insertdataintodatabase(al, request, type);
						al.clear();
						count2 = 0;
						inputStream.close();
					}

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return b;
	}

	public boolean uploadVendorFile(MultipartFile selectedFile, HttpServletRequest request, String type) {
		File file = new File(selectedFile.getOriginalFilename());
		ArrayList al = new ArrayList();
		String line = "";
		String cvsSplitBy = ",";
		boolean b = false;
		try {
			OutputStream os = new FileOutputStream(file);
			InputStream is = new BufferedInputStream(selectedFile.getInputStream());
			int count;
			byte buf[] = new byte[4096];
			while ((count = is.read(buf)) > -1) {
				os.write(buf, 0, count);
			}
			is.close();
			os.close();
			String[] name = file.toString().split("\\.");
			if (name[1].equals("csv")) {
				int count1 = 0;
				BufferedReader Bufferederreader = null;
				Bufferederreader = new BufferedReader(new FileReader(file));
				while ((line = Bufferederreader.readLine()) != null) {
					String[] country = line.split(cvsSplitBy);
					System.out.println("-----" + country[0]);
					try {
						al.add(0, country[0]);
					} catch (Exception e) {
						al.add(0, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(1, country[1]);
					} catch (Exception e) {
						al.add(1, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(2, country[2]);
					} catch (Exception e) {
						al.add(2, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(3, country[3]);
					} catch (Exception e) {

						al.add(3, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(4, country[4]);
					} catch (Exception e) {
						al.add(4, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(5, country[5]);
					} catch (Exception e) {
						al.add(5, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(6, country[6]);
					} catch (Exception e) {
						al.add(6, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(7, country[7]);
					} catch (Exception e) {
						al.add(7, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(8, country[8]);
					} catch (Exception e) {
						al.add(8, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(9, country[9]);
					} catch (Exception e) {
						al.add(9, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(10, country[10]);
					} catch (Exception e) {
						al.add(10, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(11, country[11]);
					} catch (Exception e) {
						al.add(11, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(12, country[12]);
					} catch (Exception e) {
						al.add(12, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(13, country[13]);
					} catch (Exception e) {
						al.add(13, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(14, country[14]);
					} catch (Exception e) {
						al.add(14, 0);
						System.out.println("data not added properly");
					}
					try {
						al.add(15, country[15]);
					} catch (Exception e) {
						al.add(15, 0);
						System.out.println("data not added properly");
					}
					// al.add(16, country[16]);

					try {
						b = insertdataintodatabase(al, request, type);

					} catch (Exception ex) {

						System.err.println(
								"exception bcoz not upload proper csv file in customer upload internalpanelcustomer.java"
										+ ex);

					}
					al.clear();
				}
				;
				System.out.println(al);
			}

			else {
				if (name[1].equals("xlsx")) {
					/*
					 * FileInputStream inputStream=null; XSSFWorkbook workbook=null; inputStream =
					 * new FileInputStream(file); workbook=new XSSFWorkbook(inputStream); XSSFSheet
					 * firstSheet = workbook.getSheetAt(0); Iterator<Row> iterator =
					 * firstSheet.iterator(); //row int count1=0;
					 */
				} else {
					FileInputStream inputStream = null;
					HSSFWorkbook workbook = null;
					inputStream = new FileInputStream(file);
					workbook = new HSSFWorkbook(inputStream);
					HSSFSheet firstSheet = workbook.getSheetAt(0);
					Iterator<Row> iterator = firstSheet.rowIterator();
					int count2 = 0;
					String Data;
					while (iterator.hasNext()) {
						HSSFRow nextRow = (HSSFRow) iterator.next();
						Iterator<Cell> cellIterator = nextRow.cellIterator();
						while (cellIterator.hasNext()) {
							HSSFCell cell = (HSSFCell) cellIterator.next();
							try {
								Data = cell.getStringCellValue();
							} catch (Exception e) {
								// TODO: handle exception
								Data = "0";
							}
							if (!((Data.equalsIgnoreCase("Company Name")) || (Data.equalsIgnoreCase("Mr/Ms"))
									|| (Data.equalsIgnoreCase("Company")) || (Data.equalsIgnoreCase("FirstName"))
									|| (Data.equalsIgnoreCase("LastName")) || (Data.equalsIgnoreCase("Address1"))
									|| (Data.equalsIgnoreCase("Address2")) || (Data.equalsIgnoreCase("City"))
									|| (Data.equalsIgnoreCase("State")) || (Data.equalsIgnoreCase("ZipCode"))
									|| (Data.equalsIgnoreCase("Country")) || (Data.equalsIgnoreCase("Phone"))
									|| (Data.equalsIgnoreCase("CellPhone")) || (Data.equalsIgnoreCase("Fax"))
									|| (Data.equalsIgnoreCase("Email")) || (Data.equalsIgnoreCase("DateAdded"))
									|| (Data.equalsIgnoreCase("Active")) || (Data.equalsIgnoreCase("COMPANY NAME")))) {
								System.out.println("cell++++" + Data);
								System.out.println("cell1" + Data);
								if (Data != null) {
									al.add(count2, Data);
									System.out.println(" al.get(0)" + al.get(count2));
									count2++;
								}
							} else {
								al.add(count2, Data);
							}

						}
						al.get(13).toString();
						b = insertdataintodatabase(al, request, type);
						al.clear();
						count2 = 0;
						inputStream.close();
					}

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return b;
	}

	public String getCountryByCode(String code) {
		Statement stmt = null;
		ResultSet rs = null;
		String Code = "";
		Connection con = null;
		try {
			con = db.getConnection();
			stmt = con.createStatement();
			String sql = "Select Country " + " from smd_refcountry " + " Where CountryCode = " + "'" + code + "'";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Code = rs.getString("Country");
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
		return Code;
	}

	public boolean insertdataintodatabase(ArrayList al, HttpServletRequest request, String type) {
		Connection con = null;
		

		Statement stmt = null;
		boolean b = false;
		String sql2 = "";
		int i = 0;
		try {

			if ((al.get(13).toString().equals("Company Name")) || (al.get(0).toString().equals("Company Name"))
					|| (al.get(15).toString().equals("Company Name"))) {

				al.clear();
				return false;
			}

			else {
				int a = 0;
				a = Checkdubliction(al);
				if (a == 1) {
					al.remove(14);
					Date date = new Date();
					al.add(14, "'" + JProjectUtil.getDateFormaterCommon().format(date) + "'");
					int cvId = assignClientVendorId();
					if (type.equals("customer")) {
						sql2 = " INSERT INTO bca_clientvendor ( ClientVendorID, CompanyID, " + "Name, CustomerTitle, "
								+ "FirstName, LastName, Address1, "
								+ "Address2,City,State, ZipCode, Country , Phone, CellPhone, "
								+ "Fax, Email, DateAdded, Active, Status, CVTypeID,Deleted,CustomerGroupID)"
								+ " VALUES ( " + cvId + "," + // ClientVendorID,
								ConstValue.companyId + "," + // CompanyID,
								"'" + al.get(0) + "'" + "," + // Name,
								"'" + al.get(1) + "'" + "," + // CustomerTitle,
								// "'" + al.get(2) + "," + //CustomerTitle,
								"'" + al.get(2) + "'" + "," + // FirstName,
								"'" + al.get(3) + "'" + "," + // LastName,
								"'" + al.get(4) + "'" + "," + // address1
								"'" + al.get(5) + "'" + "," + // Address2,
								"'" + al.get(6) + "'" + "," + // city,
								"'" + al.get(7) + "'" + "," + // State,
								"'" + al.get(8) + "'" + "," + // ,zipcode
								"'" + al.get(9) + "'" + "," + // ,Country
								"'" + al.get(10) + "'" + "," + // ,phone
								"'" + al.get(11) + "'" + "," + // ,CellPhone
								"'" + al.get(12) + "'" + "," + // ,Fax
								"'" + al.get(13) + "'" + "," + // ,Email
								al.get(14) + "," + // ,DAteadded
								"1" + "," + // Active,
								"'N'" + "," + // staty
								"2" + "," + "0" + "," + "0" + ")";
					} else {
						sql2 = " INSERT INTO bca_clientvendor ( ClientVendorID, CompanyID, " + "Name, CustomerTitle, "
								+ "FirstName, LastName, Address1, "
								+ "Address2,City,State, ZipCode, Country , Phone, CellPhone, "
								+ "Fax, Email, DateAdded, Active, Status, CVTypeID,Deleted)" + " VALUES ( " + cvId + ","
								+ // ClientVendorID,
								ConstValue.companyId + "," + // CompanyID,
								"'" + al.get(0) + "'" + "," + // Name,
								"'" + al.get(1) + "'" + "," + // CustomerTitle,
								"'" + al.get(2) + "'" + "," + // FirstName,
								"'" + al.get(3) + "'" + "," + // LastName,
								"'" + al.get(4) + "'" + "," + // address1
								"'" + al.get(5) + "'" + "," + // Address2,
								"'" + al.get(6) + "'" + "," + // city,
								"'" + al.get(7) + "'" + "," + // State,
								"'" + al.get(8) + "'" + "," + // ,zipcode
								"'" + al.get(9) + "'" + "," + // ,Country
								"'" + al.get(10) + "'" + "," + // ,phone
								"'" + al.get(11) + "'" + "," + // ,CellPhone
								"'" + al.get(12) + "'" + "," + // ,Fax
								"'" + al.get(13) + "'" + "," + // ,Email
								al.get(14) + "," + // ,DAteadded
								"1" + "," + // Active,
								"'N'" + "," + // status
								"3" + "," + // cvtypeid
								"0" // deleted
								+ ")";
						String sql1 = " INSERT INTO bca_clientvendor ( ClientVendorID, CompanyID, "
								+ "Name, CustomerTitle, " + "FirstName, LastName, Address1, "
								+ "Address2,City,State, ZipCode, Country , Phone, CellPhone, "
								+ "Fax, Email, DateAdded, Active, Status, CVTypeID,Deleted)" + " VALUES ( " + cvId + ","
								+ ConstValue.companyId + "," + al.get(0) + "," + al.get(1) + "," + al.get(2) + ","
								+ al.get(3) + "," + al.get(4) + "," + al.get(5) + "," + al.get(6) + "," + al.get(7)
								+ "," + al.get(8) + "," + al.get(9) + "," + al.get(10) + "," + al.get(11) + ","
								+ al.get(12) + "," + "" + al.get(13) + "," + al.get(14) + ",";
					}
					con = db.getConnection();
					stmt = con.createStatement();
					i = stmt.executeUpdate(sql2);
					if (i > 0) {
						b = true;
					}
					if (type.equals("customer")) {
						insertClientInfo(al, cvId, request);
					}
					ClientVendor clientVendor = searchClientVendor(cvId, false);
					insertClientVendorAccount(cvId);
					insertBSAddress(cvId);
					determine_FinanceCharges(cvId);
					if (type.equals("customer")) {
						updateDropShipClientVendor(cvId);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
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
		return b;
	}

	public int Checkdubliction(ArrayList al) {
 		Connection con = null;

		Statement stmt = null;
		ResultSet rs = null, rs1 = null;
		String criteria = null;
		try {
			con = db.getConnection();
			stmt = con.createStatement();
			String Sql = "SELECT Name,CustomerTitle,FirstName,LastName,Address1,Address2,City,State,ZipCode,Country,Phone,CellPhone,Fax,Email,DateAdded,Active"
					+ " FROM bca_clientvendor" + " WHERE CVTypeID IN (1,2)" +
					// " AND Active IN(1,0)" + /* added by RG*/
					" AND Status = 'N'" + " AND bca_clientvendor.CompanyID = " + ConstValue.companyId
					+ " AND Deleted=0";

			if (criteria == null) {
				criteria = "ORDER BY Name";

			}
			Sql = Sql + " " + criteria;
			rs = stmt.executeQuery(Sql);
			while (rs.next()) {
				if (((rs.getString("Name")).equalsIgnoreCase(al.get(0).toString()))) {
					if ((rs.getString("CustomerTitle")).equalsIgnoreCase(al.get(1).toString())) {
						if ((rs.getString("FirstName")).equalsIgnoreCase(al.get(2).toString())) {
							if ((rs.getString("LastName")).equalsIgnoreCase(al.get(3).toString())) {
								if ((rs.getString("Address1")).equalsIgnoreCase(al.get(4).toString())) {
									if ((rs.getString("Address2")).equalsIgnoreCase(al.get(5).toString())) {
										if ((rs.getString("City")).equalsIgnoreCase(al.get(6).toString())) {
											if ((rs.getString("State")).equalsIgnoreCase(al.get(7).toString())) {
												if ((rs.getString("ZipCode")).equalsIgnoreCase(al.get(8).toString())) {
													if ((rs.getString("Country"))
															.equalsIgnoreCase(al.get(9).toString())) {
														if ((rs.getString("Phone"))
																.equalsIgnoreCase(al.get(10).toString())) {
															if ((rs.getString("CellPhone"))
																	.equalsIgnoreCase(al.get(11).toString())) {
																if ((rs.getString("Fax"))
																		.equalsIgnoreCase(al.get(12).toString())) {
																	if ((rs.getString("Email"))
																			.equalsIgnoreCase(al.get(13).toString())) {

																		return 0;
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}

			}
			;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					db.close(rs);
				}
				if (rs1 != null) {
					db.close(rs1);
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
		return 1;
	}

	public void updateDropShipClientVendor(int cvId) {
		
		Statement stmt = null;
		Connection con = null;

		String sql = " UPDATE  bca_clientvendor SET " + " ReferenceCustomerID= " + cvId + " WHERE CompanyID = "
				+ ConstValue.companyId + " AND Active=1" + " AND ReferenceCustomerID =-1  ";
		try {
			con = db.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
	}

	public void insertClientVendorAccount(int cvId) {
		
		Connection con = null;

		Statement stmt = null;
		ResultSet rs = null;
		int accountId = -1;
		try {
			String sql = "INSERT INTO bca_account (ParentID,isCategory,Name,"
					+ "Description,AcctTypeID,AcctCategoryID,CompanyID,"
					+ "ClientVendorID,DepositPaymentID,CustomerStartingBalance,"
					+ "CustomerCurrentBalance,VendorStartingBalance,"
					+ "VendorCurrentBalance,Active,DateAdded) VALUES (" + "0" + "," + // ParentID
					"0" + "," + // isCategory,
					"'" + "0" + "'" + "," + // Name,
					"'" + "0" + "'" + "," + // Description,
					"3" + "," + // account.getAccountTypeID() + "," + //AcctTypeID,
					"0" + "," + // AcctCategoryID,
					ConstValue.companyId + "," + // CompanyID," +
					cvId + "," + // "ClientVendorID,
					"0" + "," + // DepositPaymentID,
					"0" + "," + // CustomerStartingBalance,
					"0" + "," + // CustomerCurrentBalance,
					"0" + "," + // VendorStartingBalance," +
					"0" + "," + // "VendorCurrentBalance,
					"1" + "," + // Active,
					"'" + (JProjectUtil.getDateFormaterCommon().format(new java.util.Date())) + "'" + // DateAdded
					")";
			con = db.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			rs = stmt.executeQuery(
					"SELECT Max(AccountID) AS LastID from bca_account where companyid=" + ConstValue.companyId); // stmt.executeQuery("SELECT
																													// @@IDENTITY
																													// AS
																													// LastID");
			if (rs.next()) {
				accountId = rs.getInt("LastID");
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
	}

	public void insertBSAddress(int cvId) {
		
		Connection con = null;

		Statement stmt = null;
		try {

		} catch (Exception e) {
			// TODO: handle exception\
			e.printStackTrace();
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
	}

	public void determine_FinanceCharges(int cvId) {
		
		Connection con = null;

		Statement stmt = null;
		ResultSet rs = null;
		boolean exist = false;
		try {
			String sql = " SELECT ClientVendorID " + " FROM bca_clientvendorfinancecharges "
					+ " WHERE ClientVendorID = " + cvId;
			con = db.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				exist = true;
			}
			if (exist) {
				updateFinanceCharges(cvId);
			} else {
				addFinanceCharges(cvId);
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
	}

	public void updateFinanceCharges(int cvId) {
		
		Connection con = null;

		Statement stmt = null;
		try {
			String sql = " UPDATE bca_clientvendorfinancecharges SET " + " UseIndividual = " + "0" + "," + // UseIndividual,"
																											// +
					" AnnualInterestRate = " + "0" + "," + // "AnnualInterestRate,
					" MinimumFinanceCharge = " + "0" + "," + // MinimumFinanceCharge,
					" GracePeriod = " + "0" + "," + // GracePeriod,
					" AssessFinanceCharge = " + "0" + // AssesFinanceCharge,
					" WHERE  ClientVendorID =  " + cvId;// ClientVendorID,
			con = db.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
	}

	public void addFinanceCharges(int cvId) {
		
		Connection con = null;

		Statement stmt = null;
		try {
			String sql = " INSERT INTO bca_clientvendorfinancecharges (" + "ClientVendorID," + "UseIndividual,"
					+ "AnnualInterestRate,MinimumFinanceCharge,GracePeriod,AssessFinanceCharge)" + " VALUES (" + cvId
					+ "," + // ClientVendorID,
					"0" + "," + // UseIndividual," +
					"0" + "," + // "AnnualInterestRate,
					"0" + "," + // MinimumFinanceCharge,
					"0" + "," + // GracePeriod,
					"0" + // AssesFinanceCharge,
					") ";
			con = db.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
	}

	public int assignClientVendorId() {
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		int cvId = 0;

		try {
			String sql = "select " + " CV.ClientVendorID " + " from bca_clientvendor CV " + " order by "
					+ " CV.ClientVendorID DESC ";
			con = db.getConnection();

			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				cvId = rs.getInt("ClientVendorID");
				cvId++;
			} else {
				cvId = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();

			// TODO: handle exception
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
		return cvId;
	}

	public void insertClientInfo(ArrayList al, int cvId, HttpServletRequest request) {
		
		Statement stmt = null;
		Connection con = null;
		try {
			con = db.getConnection();
			String sql2 = " INSERT INTO smd_cvinfo (ClientVendorID,Company," + "PasswordHint, "
					+ "PasswordAnswer, Newsletter, Subscribe, IsChecked,Status,HomePage,ResellerTaxID,"
					+ "FID,CustomerGroupID,BillingAddressID,ShippingAddressID,"
					+ "AllowMultipleAddress,WWW,SourceInfo,BusinessType,userPhoto,isPhotoPrivate)" + " VALUES ( " + cvId
					+ "," + // ClientVendorID,
					"'" + request.getSession().getAttribute("user") + "'" + "," + // CompanyID,
					// "'" + cv.getPasword() + "'" + "," +//Password
					"'0'" + "," + // PasswordHint
					"'0'" + "," + // PasswordAnswer
					"'Y'" + "," + // Newsletter
					"'0'" + "," + // Subscribe
					"'0'" + "," + // IsChecked
					"'Approved'" + "," + // Status
					"'0'" + "," + // HomePage
					"'0'" + "," + // ResellerTaxID
					// "'" + cv.getTaxable() + "'" + "," +//Taxable
					"'0'" + "," + // FID
					1 + "," + // CustomerGroupID
					"0" + "," + // BillingAddressID
					"0" + "," + // ShippingAddressID
					"'0'" + "," + // AllowMultipleAddress
					"'0'" + "," + // WWW
					"0" + "," + // SourceInfo
					"'0'" + "," + // BusinessType
					"'0'" + "," + // userPhoto
					"0" + ")"; // isPhotoPrivate
			con = db.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(sql2);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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

	}

	public ClientVendor searchClientVendor(int cvID, boolean storageSearch) {
		
		Statement stmt = null;
		ResultSet rs = null;
		Statement stmt_1 = null;
		ResultSet rs_1 = null;
		ResultSet rs_2 = null;
		Statement stmt_2 = null;
		ResultSet rs_3 = null;
		Statement stmt_3 = null;
		String sql = null;
		ClientVendor cv = null;
		Connection con = null;
		try {
			con = db.getConnection();
			stmt = con.createStatement();
			stmt_1 = con.createStatement();
			stmt_2 = con.createStatement();
			stmt_3 = con.createStatement();

			sql = "SELECT * " + " FROM bca_clientvendor " + " WHERE CompanyID = " + ConstValue.companyId
					+ " AND Status IN ('U', 'N' ) " + // " AND (Deleted = 0 OR Active = 1) " +
					" AND ClientVendorID = " + cvID;
			String SqL = "SELECT * FROM bca_clientvendorfinancecharges Where ClientVendorID = " + cvID;

			String SqL1 = "SELECT CallDate FROM bca_clientvendorcontacthistory Where ClientVendorID = " + cvID;

			String Sql2 = "SELECT Password FROM smd_cvinfo where ClientVendorID = " + cvID;

			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				cv = new ClientVendor();
				cv.setCvID(rs.getInt("ClientVendorID"));
				cv.setName(ConstValue.hateNull(rs.getString("Name")));
				cv.setDetail(ConstValue.hateNull(rs.getString("Detail")));
				cv.setCustomerTitle(ConstValue.hateNull(rs.getString("CustomerTitle")));
				cv.setCustomerTitleID(rs.getInt("CustomerTitleID"));
				cv.setFirstName(ConstValue.hateNull(rs.getString("FirstName")));
				cv.setLastName(ConstValue.hateNull(rs.getString("LastName")));
				cv.setBillName(ConstValue.hateNull(rs.getString("BillName")));
				cv.setAddress1(ConstValue.hateNull(rs.getString("Address1")));
				cv.setAddress2(ConstValue.hateNull(rs.getString("Address2")));
				cv.setCity(ConstValue.hateNull(rs.getString("City")));
				cv.setState(ConstValue.hateNull(rs.getString("State")));
				cv.setProvince(ConstValue.hateNull(rs.getString("Province")));
				String country = getCountryByCode((ConstValue.hateNull(rs.getString("Country"))));
				cv.setCountry(country);
				cv.setZipCode(ConstValue.hateNull(rs.getString("ZipCode")));
				cv.setPhone(ConstValue.hateNull(rs.getString("Phone")));
				cv.setCellPhone(ConstValue.hateNull(rs.getString("CellPhone")));
				cv.setFax(ConstValue.hateNull(rs.getString("Fax")));
				cv.setEmail(ConstValue.hateNull(rs.getString("Email")));
				cv.setHomePage(ConstValue.hateNull(rs.getString("HomePage")));
				cv.setResellerTaxID(rs.getString("ResellerTaxID"));
				cv.setTaxable(rs.getInt("Taxable"));
				cv.setCVTypeID(rs.getInt("CVTypeID"));
				cv.setCVCategoryID(rs.getInt("CVCategoryID"));
				cv.setCVCategoryName(ConstValue.hateNull(rs.getString("CVCategoryName")));
				cv.setShipCarrierID(rs.getInt("ShipCarrierID"));
				cv.setPaymentTypeID(rs.getInt("PaymentTypeID"));
				cv.setSalesRepID(rs.getInt("SalesRepID"));
				cv.setTermID(rs.getInt("TermID"));
				cv.setCcTypeID(rs.getInt("CCTypeID"));
				cv.setCustomerOpenDebit(rs.getDouble("CustomerOpenDebit"));
				cv.setCustomerCreditLine(rs.getDouble("CustomerCreditLine"));
				cv.setVendorOpenDebit(rs.getDouble("VendorOpenDebit"));
				cv.setVendorAllowedCredit(rs.getDouble("VendorAllowedCredit"));
				cv.setDeleted(rs.getInt("Deleted"));
				cv.setStatus(rs.getString("Status"));
				cv.setActive(rs.getInt("Active"));
				cv.setDateAdded((java.util.Date) rs.getDate("DateAdded"));
				cv.setPriority(rs.getInt("Priority"));
				cv.setItemPriceLevel(rs.getInt("ItemPriceLevel"));
				cv.setPriceLevelID(rs.getInt("PriceLevelID"));
				cv.setCategoryId(rs.getInt("CategoryID"));
				cv.setPayFromId(rs.getInt("PayFromID"));
				cv.setUseSpecialMessage(rs.getBoolean("UseSpecialMessage"));
				cv.setMessage(ConstValue.hateNull(rs.getString("Message")));
				cv.setCustomerGroupID(rs.getInt("CustomerGroupID"));
				cv.setForm1099Selected(rs.getInt("Form1099"));
				cv.setRemainingCreditAmount(rs.getDouble("RemainingCredit"));
				cv.setLineOfCreditTermID(rs.getInt("LineofCreditTermID"));
				// person has service.
				cv.setHasServices(new ClientVendorHasService(cv));
				cv.setBankAccountID(rs.getInt("BankAccountID"));

				rs_1 = stmt_1.executeQuery(SqL);
				while (rs_1.next()) {
					cv.setUseIndividual(rs_1.getBoolean("UseIndividual"));
					cv.setAnnualInterestRate(rs_1.getDouble("AnnualInterestRate"));
					cv.setMinimumFianceCharge(rs_1.getDouble("MinimumFinanceCharge"));
					cv.setGracePeriod(rs_1.getInt("GracePeriod"));
					cv.setAssessFinanceCharge(rs_1.getBoolean("AssessFinanceCharge"));
				}

				rs_2 = stmt_2.executeQuery(SqL1);
				while (rs_2.next()) {
					Date date = rs_2.getDate("CallDate");
					if (date != null) {
						cv.setLastContactOn(JProjectUtil.dateFormat.format(date));
					}
				}

				rs_3 = stmt_3.executeQuery(Sql2);
				while (rs_3.next()) {
					cv.setSMCPasword(rs_3.getString("Password"));
				}
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
				if (rs_1 != null) {
					db.close(rs_1);
				}
				if (stmt_1 != null) {
					db.close(stmt_1);
				}
				if (rs_2 != null) {
					db.close(rs_2);
				}
				if (stmt_2 != null) {
					db.close(stmt_2);
				}
				if (rs_3 != null) {
					db.close(rs_3);
				}
				if (stmt_3 != null) {
					db.close(stmt_3);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (cv == null) {
			return new ClientVendor();
		} else {
			return cv;
		}

	}

	public boolean exportCustomerList(HttpServletRequest request, String type) {
		
		Connection con = null;

		Statement stmt = null;
		ResultSet rs = null, rs1 = null;
		boolean b = false;
		final String FILE_HEADER = "Company Name,CustomerTitle,FirstName,LastName,Address1,Address2,City,State,ZipCode,Country,Phone,CellPhone,Fax,Email,DateAdded,Active";
		final String NEW_LINE_SEPARATOR = "\n";
		final String COMMA_DELIMITER = ",";
		String home = System.getProperty("user.home");
		String Sql = "SELECT Name,CustomerTitle,FirstName,LastName,Address1,Address2,City,State,ZipCode,Country,Phone,CellPhone,Fax,Email,DateAdded,Active"
				+ " FROM bca_clientvendor" + " WHERE CVTypeID IN (1,2)" + " AND Status = 'N'"
				+ " AND bca_clientvendor.CompanyID = " + ConstValue.companyId + " AND Deleted=0";
		File destinationFile = null;
		File sourcefile = null;
		FileOutputStream fileOutputStream = null;
		if (type.equals("csv")) {
			sourcefile = new File(home + "/Downloads/BCA customerlists.csv");
			String path = home + "/Downloads/BCA customerlists.csv";
			try {
				if (!sourcefile.exists()) {
					sourcefile.createNewFile();
					fileOutputStream = new FileOutputStream(new File(home + "/Downloads/BCA customerlists.csv"));
				} else {
					fileOutputStream = new FileOutputStream(new File(home + "/Downloads/BCA customerlists.csv"));
				}
				FileWriter fileWriter = null;
				fileWriter = new FileWriter(path);
				fileWriter.append(FILE_HEADER.toString());
				fileWriter.append(NEW_LINE_SEPARATOR);
				String sql11 = "SELECT Name FROM bca_company WHERE bca_company.CompanyID = " + ConstValue.companyId;
				CustomercsvBean CustomercsvBean = new CustomercsvBean();
				con = db.getConnection();
				stmt = con.createStatement();
				rs1 = stmt.executeQuery(sql11);
				if (rs1.next()) {
					CustomercsvBean.setCompanyname(rs1.getString("Name"));
				}
				rs = stmt.executeQuery(Sql);
				while (rs.next()) {
					CustomercsvBean.setName(rs.getString("Name"));

					CustomercsvBean.setCustomerTitle(rs.getString("CustomerTitle"));
					CustomercsvBean.setFirstName(rs.getString("FirstName"));
					CustomercsvBean.setLastName(rs.getString("LastName"));
					CustomercsvBean.setAddress1(rs.getString("Address1"));
					CustomercsvBean.setAddress2(rs.getString("Address2"));
					CustomercsvBean.setCity(rs.getString("City"));
					CustomercsvBean.setState(rs.getString("State"));
					CustomercsvBean.setZipCode(rs.getString("ZipCode"));
					CustomercsvBean.setCountry(rs.getString("Country"));
					CustomercsvBean.setPhone(rs.getString("Phone"));
					CustomercsvBean.setCellPhone(rs.getString("CellPhone"));
					CustomercsvBean.setFax(rs.getString("Fax"));
					CustomercsvBean.setEmail(rs.getString("Email"));
					CustomercsvBean.setTimestammp(rs.getTimestamp("DateAdded"));
					CustomercsvBean.setActive(rs.getString("Active"));

					String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
							.format(CustomercsvBean.getTimestammp());

					fileWriter.append(CustomercsvBean.getName());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getCustomerTitle());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getFirstName());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getLastName());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getAddress1());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getAddress2());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getCity());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getState());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getZipCode());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getCountry());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getPhone());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getCellPhone());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getFax());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getEmail());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(timestamp);
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getActive());
					fileWriter.append(NEW_LINE_SEPARATOR);
				}

				fileWriter.flush();
				fileWriter.close();
				fileOutputStream.close();

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
		} else {
			sourcefile = new File(home + "/Downloads/Customerlist.xls");
			File f = new File(home + "/Downloads/Customerlist.xls");
			HSSFWorkbook workbook = null;
			try {
				if (!f.exists()) {
					f.createNewFile();
					fileOutputStream = new FileOutputStream(new File(home + "/Downloads/Customerlist.xls"));
				} else {
					fileOutputStream = new FileOutputStream(new File(home + "/Downloads/Customerlist.xls"));
				}
				workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("customer list");

				String sql11 = "SELECT Name FROM bca_company WHERE bca_company.CompanyID = " + ConstValue.companyId;
				CustomercsvBean CustomercsvBean = new CustomercsvBean();
				con = db.getConnection();
				stmt = con.createStatement();
				rs1 = stmt.executeQuery(sql11);
				if (rs1.next()) {
					CustomercsvBean.setCompanyname(rs1.getString("Name"));
				}
				int rowIndex = 0;
				HSSFRow row = null;
				HSSFCell cell0, cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12,
						cell13, cell14, cell15, cell16;
				row = sheet.createRow(rowIndex++);
				cell0 = row.createCell((short) 0);
				cell0.setCellValue("Company Name");
				cell1 = row.createCell((short) 1);
				cell1.setCellValue("Mr/Ms");
				// cell2 = row.createCell(2);
				// cell2.setCellValue("Company");
				cell3 = row.createCell((short) 2);
				cell3.setCellValue("FirstName");
				cell4 = row.createCell((short) 3);
				cell4.setCellValue("LastName");
				cell5 = row.createCell((short) 4);
				cell5.setCellValue("Address1");
				cell6 = row.createCell((short) 5);
				cell6.setCellValue("Address2");
				cell7 = row.createCell((short) 6);
				cell7.setCellValue("City");
				cell8 = row.createCell((short) 7);
				cell8.setCellValue("State");
				cell9 = row.createCell((short) 8);
				cell9.setCellValue("ZipCode");
				cell10 = row.createCell((short) 9);
				cell10.setCellValue("Country");
				cell11 = row.createCell((short) 10);
				cell11.setCellValue("Phone");
				cell12 = row.createCell((short) 11);
				cell12.setCellValue("CellPhone");
				cell13 = row.createCell((short) 12);
				cell13.setCellValue("Fax");
				cell14 = row.createCell((short) 13);
				cell14.setCellValue("Email");
				cell15 = row.createCell((short) 14);
				cell15.setCellValue("DateAdded");
				cell16 = row.createCell((short) 15);
				cell16.setCellValue("Active");

				rs = stmt.executeQuery(Sql);
				int a = 1;
				while (rs.next()) {

					CustomercsvBean.setName(rs.getString("Name"));

					CustomercsvBean.setCustomerTitle(rs.getString("CustomerTitle"));
					CustomercsvBean.setFirstName(rs.getString("FirstName"));
					CustomercsvBean.setLastName(rs.getString("LastName"));
					CustomercsvBean.setAddress1(rs.getString("Address1"));
					CustomercsvBean.setAddress2(rs.getString("Address2"));
					CustomercsvBean.setCity(rs.getString("City"));
					CustomercsvBean.setState(rs.getString("State"));
					CustomercsvBean.setZipCode(rs.getString("ZipCode"));
					CustomercsvBean.setCountry(rs.getString("Country"));
					CustomercsvBean.setPhone(rs.getString("Phone"));
					CustomercsvBean.setCellPhone(rs.getString("CellPhone"));
					CustomercsvBean.setFax(rs.getString("Fax"));
					CustomercsvBean.setEmail(rs.getString("Email"));
					CustomercsvBean.setTimestammp(rs.getTimestamp("DateAdded"));
					CustomercsvBean.setActive(rs.getString("Active"));

					String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
							.format(CustomercsvBean.getTimestammp());

					row = sheet.createRow(rowIndex++);
					cell0 = row.createCell((short) 0);
					cell0.setCellValue(CustomercsvBean.getName());
					cell1 = row.createCell((short) 1);
					cell1.setCellValue(CustomercsvBean.getCustomerTitle());
					// cell2 = row.createCell(2);
					// cell2.setCellValue(CustomercsvBean.getCompanyname());
					cell2 = row.createCell((short) 2);
					cell2.setCellValue(CustomercsvBean.getFirstName());
					cell3 = row.createCell((short) 3);
					cell3.setCellValue(CustomercsvBean.getLastName());
					cell4 = row.createCell((short) 4);
					cell4.setCellValue(CustomercsvBean.getAddress1());
					cell5 = row.createCell((short) 5);
					cell5.setCellValue(CustomercsvBean.getAddress2());
					cell6 = row.createCell((short) 6);
					cell6.setCellValue(CustomercsvBean.getCity());
					cell7 = row.createCell((short) 7);
					cell7.setCellValue(CustomercsvBean.getState());
					cell8 = row.createCell((short) 8);
					cell8.setCellValue(CustomercsvBean.getZipCode());
					cell9 = row.createCell((short) 9);
					cell9.setCellValue(CustomercsvBean.getCountry());
					cell10 = row.createCell((short) 10);
					cell10.setCellValue(CustomercsvBean.getPhone());
					cell11 = row.createCell((short) 11);
					cell11.setCellValue(CustomercsvBean.getCellPhone());
					cell12 = row.createCell((short) 12);
					cell12.setCellValue(CustomercsvBean.getFax());
					cell13 = row.createCell((short) 13);
					cell13.setCellValue(CustomercsvBean.getEmail());
					cell14 = row.createCell((short) 14);
					cell14.setCellValue(timestamp);
					cell15 = row.createCell((short) 15);
					cell15.setCellValue(CustomercsvBean.getActive());

					a++;

				}
				sheet.setColumnWidth((short) 0, (short) 10000);
				sheet.setColumnWidth((short) 1, (short) 2500);
				sheet.setColumnWidth((short) 2, (short) 10000);
				sheet.setColumnWidth((short) 3, (short) 10000);
				sheet.setColumnWidth((short) 4, (short) 10000);
				sheet.setColumnWidth((short) 5, (short) 10000);
				sheet.setColumnWidth((short) 6, (short) 5000);
				sheet.setColumnWidth((short) 7, (short) 5000);
				sheet.setColumnWidth((short) 8, (short) 5000);
				sheet.setColumnWidth((short) 9, (short) 7000);
				sheet.setColumnWidth((short) 10, (short) 8000);
				sheet.setColumnWidth((short) 11, (short) 1000);
				sheet.setColumnWidth((short) 12, (short) 5000);
				sheet.setColumnWidth((short) 13, (short) 7000);
				sheet.setColumnWidth((short) 14, (short) 7000);
				sheet.setColumnWidth((short) 15, (short) 7000);

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

		}
		try {
			String newName = sourcefile.getName().toString();
			destinationFile = new File(sourcefile.getParent(), newName);
			Files.copy(sourcefile.toPath(), destinationFile.toPath());
			/* sourcefile.delete(); */
		} catch (Exception e) {
			// TODO: handle exception
			if (destinationFile.exists()) {
				System.out.println("com.bzcomposer.modules.internalPan=-=-=-=-=-=-=-=-");
				destinationFile.delete();
				try {
					Files.copy(sourcefile.toPath(), destinationFile.toPath());
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
			}
		}
		b = true;
		return b;
	}

	public boolean exportVendorList(HttpServletRequest request, String type) {
		
		Connection con = null;

		Statement stmt = null;
		ResultSet rs = null, rs1 = null;
		boolean b = false;
		final String FILE_HEADER = "Company Name,CustomerTitle,FirstName,LastName,Address1,Address2,City,State,ZipCode,Country,Phone,CellPhone,Fax,Email,DateAdded,Active";
		final String NEW_LINE_SEPARATOR = "\n";
		final String COMMA_DELIMITER = ",";
		String home = System.getProperty("user.home");
		String Sql = "SELECT Name,CustomerTitle,FirstName,LastName,Address1,Address2,City,State,ZipCode,Country,Phone,CellPhone,Fax,Email,DateAdded,Active"
				+ " FROM bca_clientvendor" + " WHERE CVTypeID IN (3)" + " AND Status = 'N'"
				+ " AND bca_clientvendor.CompanyID = " + ConstValue.companyId + " AND Deleted=0";
		File destinationFile = null;
		File sourcefile = null;
		FileOutputStream fileOutputStream = null;
		if (type.equals("csv")) {
			sourcefile = new File(home + "/Downloads/BCA vendorlist.csv");
			String path = home + "/Downloads/BCA vendorlist.csv";
			try {
				if (!sourcefile.exists()) {
					sourcefile.createNewFile();
					fileOutputStream = new FileOutputStream(new File(home + "/Downloads/BCA vendorlist.csv"));
				} else {
					fileOutputStream = new FileOutputStream(new File(home + "/Downloads/BCA vendorlist.csv"));
				}
				FileWriter fileWriter = null;
				fileWriter = new FileWriter(path);
				fileWriter.append(FILE_HEADER.toString());
				fileWriter.append(NEW_LINE_SEPARATOR);
				String sql11 = "SELECT Name FROM bca_company WHERE bca_company.CompanyID = " + ConstValue.companyId;
				CustomercsvBean CustomercsvBean = new CustomercsvBean();
				con = db.getConnection();
				stmt = con.createStatement();
				rs1 = stmt.executeQuery(sql11);
				if (rs1.next()) {
					CustomercsvBean.setCompanyname(rs1.getString("Name"));
				}
				rs = stmt.executeQuery(Sql);
				while (rs.next()) {
					CustomercsvBean.setName(rs.getString("Name"));

					CustomercsvBean.setCustomerTitle(rs.getString("CustomerTitle"));
					CustomercsvBean.setFirstName(rs.getString("FirstName"));
					CustomercsvBean.setLastName(rs.getString("LastName"));
					CustomercsvBean.setAddress1(rs.getString("Address1"));
					CustomercsvBean.setAddress2(rs.getString("Address2"));
					CustomercsvBean.setCity(rs.getString("City"));
					CustomercsvBean.setState(rs.getString("State"));
					CustomercsvBean.setZipCode(rs.getString("ZipCode"));
					CustomercsvBean.setCountry(rs.getString("Country"));
					CustomercsvBean.setPhone(rs.getString("Phone"));
					CustomercsvBean.setCellPhone(rs.getString("CellPhone"));
					CustomercsvBean.setFax(rs.getString("Fax"));
					CustomercsvBean.setEmail(rs.getString("Email"));
					CustomercsvBean.setTimestammp(rs.getTimestamp("DateAdded"));
					CustomercsvBean.setActive(rs.getString("Active"));

					String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
							.format(CustomercsvBean.getTimestammp());

					fileWriter.append(CustomercsvBean.getName());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getCustomerTitle());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getFirstName());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getLastName());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getAddress1());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getAddress2());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getCity());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getState());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getZipCode());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getCountry());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getPhone());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getCellPhone());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getFax());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getEmail());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(timestamp);
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(CustomercsvBean.getActive());
					fileWriter.append(NEW_LINE_SEPARATOR);
				}

				fileWriter.flush();
				fileWriter.close();
				fileOutputStream.close();

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						db.close(rs);
					}
					if (rs1 != null) {
						db.close(rs1);
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
		} else {
			sourcefile = new File(home + "/Downloads/Vendorlist.xls");
			File f = new File(home + "/Downloads/Vendorlist.xls");
			HSSFWorkbook workbook = null;
			try {
				if (!f.exists()) {
					f.createNewFile();
					fileOutputStream = new FileOutputStream(new File(home + "/Downloads/Vendorlist.xls"));
				} else {
					fileOutputStream = new FileOutputStream(new File(home + "/Downloads/Vendorlist.xls"));
				}
				workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("vendor list");

				String sql11 = "SELECT Name FROM bca_company WHERE bca_company.CompanyID = " + ConstValue.companyId;
				CustomercsvBean CustomercsvBean = new CustomercsvBean();
				con = db.getConnection();
				stmt = con.createStatement();
				rs1 = stmt.executeQuery(sql11);
				if (rs1.next()) {
					CustomercsvBean.setCompanyname(rs1.getString("Name"));
				}
				int rowIndex = 0;
				HSSFRow row = null;
				HSSFCell cell0, cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12,
						cell13, cell14, cell15, cell16;
				row = sheet.createRow(rowIndex++);
				cell0 = row.createCell((short) 0);
				cell0.setCellValue("Company Name");
				cell1 = row.createCell((short) 1);
				cell1.setCellValue("Mr/Ms");
				// cell2 = row.createCell(2);
				// cell2.setCellValue("Company");
				cell3 = row.createCell((short) 2);
				cell3.setCellValue("FirstName");
				cell4 = row.createCell((short) 3);
				cell4.setCellValue("LastName");
				cell5 = row.createCell((short) 4);
				cell5.setCellValue("Address1");
				cell6 = row.createCell((short) 5);
				cell6.setCellValue("Address2");
				cell7 = row.createCell((short) 6);
				cell7.setCellValue("City");
				cell8 = row.createCell((short) 7);
				cell8.setCellValue("State");
				cell9 = row.createCell((short) 8);
				cell9.setCellValue("ZipCode");
				cell10 = row.createCell((short) 9);
				cell10.setCellValue("Country");
				cell11 = row.createCell((short) 10);
				cell11.setCellValue("Phone");
				cell12 = row.createCell((short) 11);
				cell12.setCellValue("CellPhone");
				cell13 = row.createCell((short) 12);
				cell13.setCellValue("Fax");
				cell14 = row.createCell((short) 13);
				cell14.setCellValue("Email");
				cell15 = row.createCell((short) 14);
				cell15.setCellValue("DateAdded");
				cell16 = row.createCell((short) 15);
				cell16.setCellValue("Active");

				rs = stmt.executeQuery(Sql);
				int a = 1;
				while (rs.next()) {

					CustomercsvBean.setName(rs.getString("Name"));

					CustomercsvBean.setCustomerTitle(rs.getString("CustomerTitle"));
					CustomercsvBean.setFirstName(rs.getString("FirstName"));
					CustomercsvBean.setLastName(rs.getString("LastName"));
					CustomercsvBean.setAddress1(rs.getString("Address1"));
					CustomercsvBean.setAddress2(rs.getString("Address2"));
					CustomercsvBean.setCity(rs.getString("City"));
					CustomercsvBean.setState(rs.getString("State"));
					CustomercsvBean.setZipCode(rs.getString("ZipCode"));
					CustomercsvBean.setCountry(rs.getString("Country"));
					CustomercsvBean.setPhone(rs.getString("Phone"));
					CustomercsvBean.setCellPhone(rs.getString("CellPhone"));
					CustomercsvBean.setFax(rs.getString("Fax"));
					CustomercsvBean.setEmail(rs.getString("Email"));
					CustomercsvBean.setTimestammp(rs.getTimestamp("DateAdded"));
					CustomercsvBean.setActive(rs.getString("Active"));

					String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
							.format(CustomercsvBean.getTimestammp());

					row = sheet.createRow(rowIndex++);
					cell0 = row.createCell((short) 0);
					cell0.setCellValue(CustomercsvBean.getName());
					cell1 = row.createCell((short) 1);
					cell1.setCellValue(CustomercsvBean.getCustomerTitle());
					// cell2 = row.createCell(2);
					// cell2.setCellValue(CustomercsvBean.getCompanyname());
					cell2 = row.createCell((short) 2);
					cell2.setCellValue(CustomercsvBean.getFirstName());
					cell3 = row.createCell((short) 3);
					cell3.setCellValue(CustomercsvBean.getLastName());
					cell4 = row.createCell((short) 4);
					cell4.setCellValue(CustomercsvBean.getAddress1());
					cell5 = row.createCell((short) 5);
					cell5.setCellValue(CustomercsvBean.getAddress2());
					cell6 = row.createCell((short) 6);
					cell6.setCellValue(CustomercsvBean.getCity());
					cell7 = row.createCell((short) 7);
					cell7.setCellValue(CustomercsvBean.getState());
					cell8 = row.createCell((short) 8);
					cell8.setCellValue(CustomercsvBean.getZipCode());
					cell9 = row.createCell((short) 9);
					cell9.setCellValue(CustomercsvBean.getCountry());
					cell10 = row.createCell((short) 10);
					cell10.setCellValue(CustomercsvBean.getPhone());
					cell11 = row.createCell((short) 11);
					cell11.setCellValue(CustomercsvBean.getCellPhone());
					cell12 = row.createCell((short) 12);
					cell12.setCellValue(CustomercsvBean.getFax());
					cell13 = row.createCell((short) 13);
					cell13.setCellValue(CustomercsvBean.getEmail());
					cell14 = row.createCell((short) 14);
					cell14.setCellValue(timestamp);
					cell15 = row.createCell((short) 15);
					cell15.setCellValue(CustomercsvBean.getActive());

					a++;

				}
				sheet.setColumnWidth((short) 0, (short) 10000);
				sheet.setColumnWidth((short) 1, (short) 2500);
				sheet.setColumnWidth((short) 2, (short) 10000);
				sheet.setColumnWidth((short) 3, (short) 10000);
				sheet.setColumnWidth((short) 4, (short) 10000);
				sheet.setColumnWidth((short) 5, (short) 10000);
				sheet.setColumnWidth((short) 6, (short) 5000);
				sheet.setColumnWidth((short) 7, (short) 5000);
				sheet.setColumnWidth((short) 8, (short) 5000);
				sheet.setColumnWidth((short) 9, (short) 7000);
				sheet.setColumnWidth((short) 10, (short) 8000);
				sheet.setColumnWidth((short) 11, (short) 1000);
				sheet.setColumnWidth((short) 12, (short) 5000);
				sheet.setColumnWidth((short) 13, (short) 7000);
				sheet.setColumnWidth((short) 14, (short) 7000);
				sheet.setColumnWidth((short) 15, (short) 7000);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						db.close(rs);
					}
					if (rs1 != null) {
						db.close(rs1);
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
		}
		try {
			String newName = sourcefile.getName().toString();
			destinationFile = new File(sourcefile.getParent(), newName);
			Files.copy(sourcefile.toPath(), destinationFile.toPath());
			/* sourcefile.delete(); */
		} catch (Exception e) {
			// TODO: handle exception
			if (destinationFile.exists()) {
				System.out.println("com.bzcomposer.modules.internalPan=-=-=-=-=-=-=-=-");
				destinationFile.delete();
				try {
					Files.copy(sourcefile.toPath(), destinationFile.toPath());
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
			}
		}
		b = true;
		return b;
	}

	public ArrayList<TblStore> getQuickBookStores(int storeTypeId) {
		
		Connection con = null;

		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<TblStore> stores = new ArrayList<>();
		try {
			String sql = "SELECT * FROM bca_store " + " WHERE CompanyID=" + ConstValue.companyId + " AND StoreTypeID = "
					+ storeTypeId;
			con = db.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TblStore store = new TblStore();
				store.setStoreId(rs.getInt("StoreID"));
				store.setStoreName(ConstValue.hateNull(rs.getString("StoreName")));
				store.setStoreTypeId(rs.getInt("StoreTypeID"));
				store.setStoreTypeName(ConstValue.hateNull(rs.getString("StoreTypeName")));
				store.setQbFilePath(rs.getString("quickBookFilePath"));
				store.setLastImportDate(rs.getDate("LastOrderImportDate"));
				stores.add(store);
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
		return stores;
	}

	public int getQBStoreTypeID() {
		int qbStoreID = 0;
		
		Connection con = null;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT StoreTypeID FROM bca_storetype Where StoreTypeName='Quick Book'";
			con = db.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				qbStoreID = rs.getInt("StoreTypeID");
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
		return qbStoreID;
	}

	public void getcmbLoadTemplate(HttpServletRequest request) {
		ArrayList<LabelValueBean> listOfOrderImportFile = new ArrayList<>();
		final String MAPDIR = request.getServletContext().getRealPath("/OrderImport");
		File templateMap = new File(File.separator + MAPDIR);
		if (templateMap.exists()) {
			File[] allTemplates = templateMap.listFiles();
			for (File file : allTemplates) {
				listOfOrderImportFile.add(new LabelValueBean(file.getName(), MAPDIR + "/" + file.getName()));
			}
		}
		request.setAttribute("listOfOrderImportFile", listOfOrderImportFile);
	}

	public void quickBookImport(MultipartFile file, HttpServletRequest request) {
		int storeID = 0;
		if (file != null) {
			File file1 = new File(file.getOriginalFilename());
			storeID = isDuplicateStoreExist(file1.getName());
			TblStore store = new TblStore();
			store.setStoreName(file1.getName());
			store.setStoreTypeId(10);
			store.setStoreTypeName("Quick Book");
			store.setQbFilePath(file1.getAbsolutePath());
			if (storeID == 0) {
				storeID = insertQBStore(store, false);
				request.setAttribute("success", "QuickBook File imported");
			}
		}
	}

	public int insertQBStore(TblStore store, boolean isUpdate) {
		int storeId = -1;
		
		Connection con = null;

		Statement stmt = null;
		ResultSet rs = null;
		try {
			if (!isUpdate) {
				String sql = "INSERT INTO bca_store (StoreName,CompanyID,StoreTypeID,StoreTypeName,quickBookFilePath) VALUES ("
						+ "'" + store.getStoreName().replaceAll("'", "''") + "'" + "," + ConstValue.companyId + ","
						+ store.getStoreTypeId() + "," + "'" + store.getStoreTypeName().replaceAll("'", "''") + "'"
						+ "," + "'" + store.getQbFilePath().replaceAll("'", "''") + "'" + ")";
				con = db.getConnection();
				stmt = con.createStatement();
				stmt.executeUpdate(sql);
				rs = stmt.executeQuery("SELECT MAX(StoreID) AS LastID FROM bca_store");
				if (rs.next()) {
					storeId = rs.getInt("LastID");
				}
			} else {

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
		return storeId;
	}

	public int isDuplicateStoreExist(String storeName) {
		int storID = 0;
		
		Connection con = null;

		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT StoreID FROM bca_store WHERE StoreName=" + "'" + storeName + "' AND CompanyID = "
					+ ConstValue.companyId;

			con = db.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				storID = rs.getInt("StoreID");
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
		return storID;
	}
}
