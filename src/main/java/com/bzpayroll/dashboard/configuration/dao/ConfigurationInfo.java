package com.bzpayroll.dashboard.configuration.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bzpayroll.common.db.SQLExecutor;
import com.bzpayroll.common.log.Loger;
import com.bzpayroll.common.utility.LabelValueBean;
import com.bzpayroll.dashboard.configuration.forms.ConfigurationDto;

@Service
public class ConfigurationInfo {

	@Autowired
	private SQLExecutor executor;

	/* Label List with id & name */
	public ArrayList labelInfo() {
		Connection con = null;
		ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
		PreparedStatement pstmtLabel = null;
		ResultSet rsLabel = null;
		if (executor == null)
			return null;
		con = executor.getConnection();
		if (con == null)
			return null;
		try {
			String labelQuery = "select ID,LabelType from bca_label";
			pstmtLabel = con.prepareStatement(labelQuery);
			rsLabel = pstmtLabel.executeQuery();
			while (rsLabel.next()) {
				labelList.add(new LabelValueBean(rsLabel.getString("LabelType"), rsLabel.getString("ID")));
			}
			pstmtLabel.close();
			rsLabel.close();
		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "labelInfo " + ex.toString());
		} finally {
			executor.close(con);
		}

		return labelList;
	}

	/* User griup information with id & name */
	public ArrayList userGroupInfo(String compId) {
		Connection con = null;
		ArrayList<ConfigurationDto> labelList = new ArrayList<ConfigurationDto>();
		
		PreparedStatement pstmtGroup = null;
		ResultSet rsGroup = null;
		if (executor == null)
			return null;
		con = executor.getConnection();
		if (con == null)
			return null;
		try {
			String groupQuery = "select GroupID,UserGroupName from bca_usergroup where CompanyID=? and Active=1 ";
			pstmtGroup = con.prepareStatement(groupQuery);
			pstmtGroup.setString(1, compId);
			rsGroup = pstmtGroup.executeQuery();
			while (rsGroup.next()) {
				ConfigurationDto cForm = new ConfigurationDto();
				cForm.setGroupID(rsGroup.getInt("GroupID"));
				cForm.setGroupNm(rsGroup.getString("UserGroupName"));
				labelList.add(cForm);
			}
			pstmtGroup.close();
			rsGroup.close();
		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "userGroupInfo " + ex.toString());
		}

		finally {
			executor.close(con);
		}
		return labelList;
	}

	/* Invoice Style List with id & name */
	public ArrayList invoiceStyleList() {
		Connection con = null;
		ArrayList<LabelValueBean> invStyleList = new ArrayList<LabelValueBean>();
		
		PreparedStatement pstmtLabel = null;
		ResultSet rsLabel = null;
		if (executor == null)
			return null;
		con = executor.getConnection();
		if (con == null)
			return null;
		try {
			String labelQuery = "select InvoiceStyleID,Name from bca_invoicestyle where Active=1";
			pstmtLabel = con.prepareStatement(labelQuery);
			rsLabel = pstmtLabel.executeQuery();
			while (rsLabel.next()) {
				invStyleList.add(new LabelValueBean(rsLabel.getString("Name"), rsLabel.getString("InvoiceStyleID")));
			}
			pstmtLabel.close();
			rsLabel.close();
		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "invoiceStyleList " + ex.toString());
		} finally {
			executor.close(con);
		}

		return invStyleList;
	}

	/* Footnote List with id & name */
	public ArrayList footnoteList(String compId) {
		Connection con = null;
		ArrayList<LabelValueBean> footnoteList = new ArrayList<LabelValueBean>();
		
		PreparedStatement pstmtFootnote = null;
		ResultSet rsFootnote = null;
		if (executor == null)
			return null;
		con = executor.getConnection();
		if (con == null)
			return null;
		try {
			String footnoteQuery = "select FootNoteID,Name from bca_footnote where CompanyID=? and Active=1  "
					+ "order by Name";
			pstmtFootnote = con.prepareStatement(footnoteQuery);
			pstmtFootnote.setString(1, compId);
			rsFootnote = pstmtFootnote.executeQuery();
			while (rsFootnote.next()) {
				footnoteList.add(new LabelValueBean(rsFootnote.getString("Name"), rsFootnote.getString("FootNoteID")));
			}
			pstmtFootnote.close();
			rsFootnote.close();
		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "footnoteList " + ex.toString());
		} finally {
			executor.close(con);
		}

		return footnoteList;
	}

	/* Job code List with id,name,cost & description */
	public ArrayList jobCodeList(String compId) {
		Connection con = null;
		ArrayList<ConfigurationDto> jobCodeList = new ArrayList<ConfigurationDto>();
		
		PreparedStatement pstmtJobCode = null;
		ResultSet rsJobCode = null;
		if (executor == null)
			return null;
		con = executor.getConnection();
		if (con == null)
			return null;
		try {
			String jobCodeQuery = "select JobID,Name,Cost,Description from bcp_jobcode where CompanyID=?"
					+ "order by Name";
			pstmtJobCode = con.prepareStatement(jobCodeQuery);
			pstmtJobCode.setString(1, compId);
			rsJobCode = pstmtJobCode.executeQuery();
			while (rsJobCode.next()) {
				ConfigurationDto configForm = new ConfigurationDto();
				configForm.setJobCodeID(rsJobCode.getInt("JobID"));
				configForm.setJob(rsJobCode.getString("Name"));
				configForm.setCost(rsJobCode.getDouble("Cost"));
				configForm.setDescription(rsJobCode.getString("Description"));
				jobCodeList.add(configForm);
			}
			pstmtJobCode.close();
			rsJobCode.close();
		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "jobCodeList " + ex.toString());
		} finally {
			executor.close(con);
		}

		return jobCodeList;
	}

	/* Sales Tax List with id & name */
	public ArrayList salesTaxList(String compId) {
		Connection con = null;
		ArrayList<LabelValueBean> taxList = new ArrayList<LabelValueBean>();
		
		PreparedStatement pstmtTax = null;
		ResultSet rsTax = null;
		if (executor == null)
			return null;
		con = executor.getConnection();
		if (con == null)
			return null;
		try {
			String footnoteQuery = "select SalesTaxID,State from bca_salestax where Active=1 and CompanyID=?";
			pstmtTax = con.prepareStatement(footnoteQuery);
			pstmtTax.setString(1, compId);
			rsTax = pstmtTax.executeQuery();
			while (rsTax.next()) {
				taxList.add(new LabelValueBean(rsTax.getString("State"), rsTax.getString("SalesTaxID")));
			}
			pstmtTax.close();
			rsTax.close();
		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "salesTaxList " + ex.toString());
		} finally {
			executor.close(con);
		}

		return taxList;
	}

	/* Service Type List with id,name,invoicestyle id */
	public ArrayList serviceTypeList(HttpServletRequest request) {
		String serviceList = "";
		Connection con = null;
		ArrayList<ConfigurationDto> serviceTypeList = new ArrayList<ConfigurationDto>();
		
		PreparedStatement pstmtServiceType = null;
		PreparedStatement pstmtInvStyle = null;
		ResultSet rsServiceType = null;
		ResultSet rsInvStyle = null;
		if (executor == null)
			return null;
		con = executor.getConnection();
		if (con == null)
			return null;
		try {
			String serviceTypeQuery = "select * from bca_servicetype order by ServiceName";
			pstmtServiceType = con.prepareStatement(serviceTypeQuery);
			rsServiceType = pstmtServiceType.executeQuery();
			String invStyleQuery = "select Name from bca_invoicestyle where InvoiceStyleID=? and Active=1";
			while (rsServiceType.next()) {
				ConfigurationDto configForm = new ConfigurationDto();
				configForm.setServiceID(rsServiceType.getInt("ServiceID"));
				configForm.setServiceName(rsServiceType.getString("ServiceName"));
				configForm.setInvStyleID(rsServiceType.getInt("InvoiceStyleID"));

				pstmtInvStyle = con.prepareStatement(invStyleQuery);
				pstmtInvStyle.setInt(1, configForm.getInvStyleID());
				rsInvStyle = pstmtInvStyle.executeQuery();
				if (rsInvStyle.next()) {
					configForm.setInvName(rsInvStyle.getString("Name"));

				} else
					configForm.setInvName("");
				pstmtInvStyle.close();
				rsInvStyle.close();
				serviceTypeList.add(configForm);
				serviceList += configForm.getServiceID() + "/*/" + configForm.getServiceName() + "/*/";
			}
			request.setAttribute("ServList", serviceList);
			pstmtServiceType.close();
			rsServiceType.close();
		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "jobCodeList " + ex.toString());
		} finally {
			executor.close(con);
		}

		return serviceTypeList;
	}

	public ConfigurationDto getDefaultCongurationData(HttpServletRequest request) {
		Connection con = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HttpSession session = request.getSession();
		String companyID = (String) session.getAttribute("companyID");
		ConfigurationDto cForm = new ConfigurationDto();
		if (executor == null)
			return cForm;
		con = executor.getConnection();
		if (con == null)
			return cForm;
		try {
			String recordQuery = "select PreferenceID,copyAddress,SalesViaID,POTermID,SalesRepID,SalesPayMethodID from bca_preference where CompanyID="
					+ companyID;
			pstmt = con.prepareStatement(recordQuery);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cForm.setPreferenceID(rs.getInt("PreferenceID"));
				cForm.setAddressSettings(rs.getString("copyAddress").equals("1") ? "on" : "off");
				cForm.setSelectedTermId(rs.getInt("POTermID"));
				cForm.setSelectedSalesRepId(rs.getInt("SalesRepID"));
				cForm.setSelectedPaymentId(rs.getInt("SalesPayMethodID"));
				cForm.setCustomerShippingId(rs.getInt("SalesViaID"));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			Loger.log("Exception in the class ConfigurationInfo and in method " + "getCongurationRecord "
					+ ex.toString());
		} finally {
			executor.close(con);
		}
		return cForm;
	}

	/*
	 * Invoke all the records required for configuration i.e:- information related
	 * to networking,sales & customer,etc.
	 */
	public void getCongurationRecord(String compId, ConfigurationDto cForm, HttpServletRequest request) {
		Connection con = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HttpSession session = request.getSession();
		String companyID = (String) session.getAttribute("companyID");
		if (executor == null)
			return;
		con = executor.getConnection();
		if (con == null)
			return;
		try {
			String recordQuery = "select CompanyLogoPath,CurrencyID,WeightID,LabelSizeID,defaultModule,FilterOption,StartingEstimationNumber,"
					+ "StartingBillNumber,PrintBills,MailToCustomer,showCombinedBilling,showBillingStatStyle,DEFAULTRMACheckingBankID,DefaultBankTransferAccID,"
					+ "defaultARCategoryID,defaultARCategoryIDforac,defaultARCategoryIDforpo,defaultARCategoryIDforbp,defaultdepositoforac,defaultdepositoforpo,"
					+ "defaultdepositoforbp,defaultReceivedforac,defaultReceivedforpo,defaultReceivedforbp,AutoPaymentDuration,DefaultReimbusrementSetting,"
					+ "showReorderPointList,showReorderPointWarring,reservedQuantity,salesOrderQty,AdminPassword,Multimode,DEFAULTCustomerSortID,DEFAULTCustomerGroupID,"
					+ "CustomerCountryID,CustomerTaxable,showSalesOrder,CustomerProvience,SalesViaID,SalesTermID,SalesRepID,SalesPayMethodID,"
					+ "StartingInvoiceNumber,copyAddress,CustomerStateID,ShippingFeeMethod,"
					+ "DefaultPackingSlipStyleID,SalesPOPrefix,InvoiceFootnoteID,SaleShowCountry,IsRatePriceChangeble,"
					+ "SaleShowTelephone,IsSalePrefix,ExtraCharge,ChargeAmount,OrderAmount,HowOftenSalestax,DropShipCharge,SalesTaxCode,SalesTaxRate,"
					+ "DropShipCharge,ShowDropShipItems,isRefundAllowed,"
					+ "VendorCountryID,StartingPONumber,POStyleID,POFootnoteID,InvoiceStyleID,InvoiceFootnoteID,UseProductWeight,UseShippingTable,"
					+ "DefaultVendorrSortID,VendorCountryID,VendorStateID,VendorProvience,StartingPONumber,POFootnoteID,POViaID,POTermID,PORepID,POPayMethodID,EmployeeInChargeID,"
					+ "POShowCountry,POShowTelephone,IsPurchasePrefix,"
					+ "StartingRINumber,ProductTaxable,EmployeeStateID,EmployeeCountryID,TimeSheetSet,ChargeSalestax,HowOftenSalestax,SalesTaxID,ShowReminder,"
					+ "InvoiceMemo,InvoiceMemoDays,OverdueInvoice,OverdueInvoiceDays,InventoryOrder,InventoryOrderDays,BillstoPay,BillstoPayDays,"
					+ "EstimationMemo,EstimationMemoDays,POMemo,PoMemoDays,ServiceBillsMemo,ServiceBillsMemoDays,MemoBill,MemoBillDays,Charge_interest,"
					+ "Charge_minimum,Charge_grace,Charge_reassess,BudgetStartMonth,BudgetEndMonth,Performance,Mail_senderEmail,Mailserver,"
					+ "Mail_username,Mail_password,Mail_Auth,poboard,itemsReceivedBoard,itemsShippedBoard,SalesOrderBoard"
					+ " from bca_preference where CompanyID=" + companyID;
			pstmt = con.prepareStatement(recordQuery);
//			pstmt.setString(1, compId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String logoPath = rs.getString("CompanyLogoPath");
				cForm.setFileName(logoPath);
				request.setAttribute("Image", logoPath);
				Loger.log("Image =>" + logoPath);
				/* General */
				cForm.setCurrencyID(rs.getInt("CurrencyID"));
				cForm.setWeightID(rs.getInt("WeightID"));
				cForm.setDefaultLabelID(rs.getInt("LabelSizeID"));
				cForm.setModuleID(rs.getInt("defaultModule"));
				cForm.setFilterOption(rs.getString("FilterOption"));

				/* Estimation */
				cForm.setStartingEstimationNumber(rs.getInt("StartingEstimationNumber"));

				/* Billing */
				cForm.setStartingBillNumber(rs.getInt("StartingBillNumber"));
				cForm.setPrintBills(rs.getString("PrintBills").equals("1") ? "on" : "off");
				cForm.setMailToCustomer(rs.getString("MailToCustomer").equals("1") ? "on" : "off");
				cForm.setShowCombinedBilling(rs.getString("showCombinedBilling").equals("1") ? "on" : "off");
				cForm.setShowBillingStatStyle(rs.getInt("showBillingStatStyle"));

				/* RMA */
				cForm.setSelectedAccountId(rs.getInt("DEFAULTRMACheckingBankID"));

				/* Account&Payment */
				// cForm.setSelectedCategoryId(rs.getInt("defaultARCategoryID"));
				cForm.setDefaultPaymentMethodId(rs.getInt("DefaultBankTransferAccID"));
				cForm.setDefaultDepositToId(rs.getInt("DefaultBankTransferAccID"));
				cForm.setDefaultCategoryId(rs.getInt("defaultARCategoryID"));
				cForm.setDefaultDepositToId(rs.getInt("DefaultBankTransferAccID"));
				cForm.setArCategory(rs.getInt("defaultARCategoryIDforac"));
				cForm.setPoCategory(rs.getInt("defaultARCategoryIDforpo"));
				cForm.setBpCategory(rs.getInt("defaultARCategoryIDforbp"));
				cForm.setArDepositTo(rs.getInt("defaultdepositoforac"));
				cForm.setPoDepositTo(rs.getInt("defaultdepositoforpo"));
				cForm.setBpDepositTo(rs.getInt("defaultdepositoforbp"));
				cForm.setArReceivedType(rs.getInt("defaultReceivedforac"));
				cForm.setPoReceivedType(rs.getInt("defaultReceivedforpo"));
				cForm.setBpReceivedType(rs.getInt("defaultReceivedforbp"));
				cForm.setScheduleDays(rs.getInt("AutoPaymentDuration"));
				cForm.setReimbursementSettings(rs.getInt("DefaultReimbusrementSetting"));
				/* Inventory Setting */
				cForm.setShowReorderPointList(rs.getString("showReorderPointList").equals("1") ? "on" : "off");
				cForm.setShowReorderPointWarning(rs.getString("showReorderPointWarring").equals("1") ? "on" : "off");
				cForm.setReservedQuantity(rs.getString("reservedQuantity").equals("1") ? "on" : "off");
				cForm.setSalesOrderQty(rs.getString("salesOrderQty").equals("1") ? "on" : "off");

				/* Networking & Security */
				// cForm.setPassword(rs.getString("AdminPassword"));
				cForm.setMultiUserConnection(rs.getInt("Multimode"));

				/* Sales & Customer */
				cForm.setSortBy(rs.getInt("DEFAULTCustomerSortID"));
				cForm.setCustomerGroup(rs.getInt("DEFAULTCustomerGroupID"));
				cForm.setCustDefaultCountryID(rs.getInt("CustomerCountryID"));
				cForm.setCustTaxable(rs.getString("CustomerTaxable").equals("1") ? "on" : "off");
				cForm.setIsSalesOrder(rs.getString("showSalesOrder").equals("1") ? "on" : "off");
				cForm.setCustomerProvince(rs.getString("CustomerProvience"));
				cForm.setCustomerShippingId((rs.getInt("SalesViaID")));

				cForm.setStartInvoiceNo(rs.getLong("StartingInvoiceNumber"));
				cForm.setAddressSettings(rs.getString("copyAddress").equals("1") ? "on" : "off");
				cForm.setSelectedStateId(rs.getInt("CustomerStateID"));
				cForm.setSelectedShippingId(rs.getInt("ShippingFeeMethod"));
				cForm.setSelectedSalesRepId(rs.getInt("SalesRepID"));
				cForm.setSelectedPaymentId(rs.getInt("SalesPayMethodID"));

				cForm.setPackingSlipTemplateId(rs.getInt("DefaultPackingSlipStyleID"));
				cForm.setPoNumPrefix(rs.getString("SalesPOPrefix"));
				// added by tulsi
				cForm.setInvStyleID(rs.getInt("InvoiceStyleID"));
				cForm.setSelectedMessageId(rs.getInt("InvoiceFootnoteID"));
				cForm.setSaleShowCountry(rs.getString("SaleShowCountry").equals("1") ? "on" : "off");
				cForm.setRatePriceChangable(rs.getString("IsRatePriceChangeble").equals("1") ? "on" : "off");
				cForm.setSaleShowTelephone(rs.getString("SaleShowTelephone").equals("1") ? "on" : "off");
				cForm.setIsSalePrefix(rs.getString("IsSalePrefix").equals("1") ? "on" : "off");
				cForm.setExtraChargeApplicable(rs.getString("ExtraCharge").equals("1") ? "on" : "off");
				cForm.setChargeAmount(rs.getInt("ChargeAmount"));
				cForm.setOrderAmount(rs.getInt("OrderAmount"));
				cForm.setHowOftenSalestax(rs.getInt("HowOftenSalestax"));
				cForm.setDropShipCharge(rs.getInt("DropShipCharge"));
				cForm.setSalesTaxCode(rs.getString("SalesTaxCode"));
				cForm.setSaleTaxRate(rs.getDouble("SalesTaxRate"));
				cForm.setDropShipCharge(rs.getInt("DropShipCharge"));
				cForm.setIsShowDropShipItems(rs.getInt("ShowDropShipItems"));
				cForm.setIsRefundAllowed(rs.getString("isRefundAllowed").equals("1") ? "on" : "off");
				String r = rs.getString("isRefundAllowed").equals("1") ? "on" : "off";
				/* Purchase & Vendor */
				/*
				 * cForm.setVendorDefaultCountryID(rs.getInt("VendorCountryID"));
				 * cForm.setStartPONum(rs.getLong("StartingPONumber"));
				 * cForm.setPoStyleID(rs.getInt("POStyleID"));
				 * cForm.setVendorDefaultFootnoteID(rs.getInt("POFootnoteID"));
				 * cForm.setInvStyleID(rs.getInt("InvoiceStyleID"));
				 * cForm.setDefaultFootnoteID(rs.getInt("InvoiceFootnoteID"));
				 * cForm.setVendorDefaultFootnoteID(rs.getInt("InvoiceFootnoteID"));
				 * cForm.setIsProductWeight(rs.getString("UseProductWeight").equals("1") ?
				 * "true" : "false");
				 * cForm.setIsCompanyName(rs.getString("UseShippingTable").equals("1") ? "true"
				 * : "false");
				 */

				cForm.setSortBy(rs.getInt("DefaultVendorrSortID"));
				cForm.setSelectedCountryId1(rs.getInt("vendorCountryID"));
				cForm.setSelectedStateId1(rs.getInt("vendorStateID"));
				cForm.setVendorProvience(rs.getString("VendorProvience"));
				cForm.setStartPONum(rs.getLong("StartingPONumber"));
				cForm.setVendorDefaultFootnoteID(rs.getInt("POFootnoteID"));
				cForm.setShipCarrierId(rs.getInt("POViaID"));
				cForm.setSelectedTermId(rs.getInt("POTermID"));
				cForm.setSelectedSalesRepId(rs.getInt("PORepID"));
				cForm.setSelectedPaymentId(rs.getInt("POPayMethodID"));
				cForm.setSelectedActiveEmployeeId(rs.getInt("EmployeeInChargeID"));
				cForm.setPoShowCountry(rs.getString("POShowCountry").equals("1") ? "on" : "off");
				cForm.setPoShowTelephone(rs.getString("POShowTelephone").equals("1") ? "on" : "off");
				cForm.setIsPurchasePrefix(rs.getString("IsPurchasePrefix").equals("1") ? "on" : "off");

				/* Inventory */
				cForm.setStartRINum(rs.getLong("StartingRINumber"));
				cForm.setProductTaxable(rs.getString("ProductTaxable").equals("1") ? "on" : "off");

				/* Employee */
				cForm.setEmpStateID(rs.getInt("EmployeeStateID"));
				request.setAttribute("EmpState", String.valueOf(cForm.getEmpStateID()));

				cForm.setEmpCountryID(rs.getInt("EmployeeCountryID"));
				cForm.setTimeSheet(rs.getLong("TimeSheetSet"));

				/* Tax */
				cForm.setChargeSalesTax(rs.getString("ChargeSalestax").equals("1") ? "true" : "false");
				cForm.setHowOftenSalesTax(rs.getInt("HowOftenSalestax"));
				cForm.setSalesTaxID(rs.getInt("SalesTaxID"));

				/* Reminders */
				cForm.setShowReminder(rs.getString("ShowReminder").equals("1") ? "on" : "off");
				cForm.setInvoiceMemo(rs.getInt("InvoiceMemo"));
				cForm.setInvoiceMemoDays(rs.getInt("InvoiceMemoDays"));
				cForm.setOverdueInvoice(rs.getInt("OverdueInvoice"));
				cForm.setOverdueInvoiceDays(rs.getInt("OverdueInvoiceDays"));
				cForm.setInventoryOrder(rs.getInt("InventoryOrder"));
				cForm.setInventoryOrderDays(rs.getInt("InventoryOrderDays"));
				cForm.setBillsToPay(rs.getInt("BillstoPay"));
				cForm.setBillsToPayDays(rs.getInt("BillstoPayDays"));
				cForm.setMemorizeEstimation(rs.getInt("EstimationMemo"));
				cForm.setMemorizeEstimationDays(rs.getInt("EstimationMemoDays"));
				cForm.setMemorizeBill(rs.getInt("POMemo"));
				cForm.setMemorizeBillDays(rs.getInt("PoMemoDays"));
				cForm.setServiceBilling(rs.getInt("ServiceBillsMemo"));
				cForm.setServiceBillingDays(rs.getInt("ServiceBillsMemoDays"));
				cForm.setMemorizePurchaseOrder(rs.getInt("MemoBill"));
				cForm.setMemorizePurchaseOrderDays(rs.getInt("MemoBillDays"));

				/* Finance Charge */
				cForm.setAnnualInterestRate(rs.getDouble("Charge_interest"));
				cForm.setMinCharge(rs.getDouble("Charge_minimum"));
				cForm.setGracePeriod(rs.getInt("Charge_grace"));
				cForm.setAssessFinanceCharge(rs.getString("Charge_reassess").equals("1") ? "on" : "off");
				cForm.setStartMonth(rs.getInt("BudgetStartMonth"));
				cForm.setEndMonth(rs.getInt("BudgetEndMonth"));

				/* Performance */
				long perform = rs.getLong("Performance");
				if (perform != 2000 && perform != 5000 && perform != 10000) {
					cForm.setPerformance(1);
					cForm.setUserDefinePerform(perform);
				} else {
					cForm.setPerformance((int) perform);
					cForm.setUserDefinePerform(20000);
				}

				/* SMTP setup */
				cForm.setSenderEmail(rs.getString("Mail_senderEmail"));
				cForm.setMailServer(rs.getString("Mailserver"));
				cForm.setMailUserName(rs.getString("Mail_username"));
				cForm.setMailPassword(rs.getString("Mail_password"));
				cForm.setMailAuth(rs.getString("Mail_Auth").equals("1") ? "true" : "false");

				/* Dashboard */
				cForm.setPoboard(rs.getString("poboard").equals("1") ? "on" : "off");
				cForm.setItemReceivedBoard(rs.getString("itemsReceivedBoard").equals("1") ? "on" : "off");
				cForm.setItemShippedBoard(rs.getString("itemsShippedBoard").equals("1") ? "on" : "off");
				cForm.setSalesOrderBoard(rs.getString("SalesOrderBoard").equals("1") ? "on" : "off");
			}
			pstmt.close();
			rs.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			Loger.log("Exception in the class ConfigurationInfo and in method " + "getCongurationRecord "
					+ ex.toString());
		} finally {
			executor.close(con);
		}
	}

	public void getAdministratorDetails(String compId, String emailAddress, String modalNewPassword) {
		Connection con = null;
		
		PreparedStatement pstmt = null;
		con = executor.getConnection();
		try {
			String recordQuery = "update bca_user set Password='" + modalNewPassword + "' where Email_Address='"
					+ emailAddress + "'  and CompanyID  = '" + compId + "'";
			pstmt = con.prepareStatement(recordQuery);
			int Check = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			executor.close(con);
		}
	}

	public void getAdministratorDetails(String compId, ConfigurationDto cForm, String emailAddress) {
		System.out.println("cid=" + compId);
		System.out.println("emailAddress=" + emailAddress);
		Connection con = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = executor.getConnection();
		try {
			String recordQuery = "Select * from bca_user where Email_Address= ?";
			pstmt = con.prepareStatement(recordQuery);
			pstmt.setString(1, emailAddress);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cForm.setPassword(rs.getString("Password"));
				cForm.setUserName(rs.getString("LoginID"));
				cForm.setEmailAddress(rs.getString("Email_Address"));
			}
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			executor.close(con);
		}

	}

	/*
	 * Saves the information required for the application configuration to the
	 * database.
	 */
	public boolean saveConfigurationRecord(ConfigurationDto cForm, long compId, HttpServletRequest request) {
		Connection con = null;
		
		PreparedStatement pstmt = null;
		boolean isSaved = false;

		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String insertRecord = "update bca_preference set  CurrencyID = ?,CurrencyText = ?,WeightID = ?,"
					+ "Weight = ?,LabelSizeID = ?,LabelSize = ?,AdminUsername = ?,"
					+ "AdminPassword = ?,Multimode = ?,CustomerCountryID = ?,CustomerCountry = ?,"
					+ "CustomerTaxable = ?,StartingInvoiceNumber = ?,InvoiceStyleID = ?,InvoiceFootnoteID = ?,"
					+ "UseProductWeight = ?,UseShippingTable = ?,VendorCountry = ?,VendorCountryID = ?,"
					+ "StartingPONumber = ?,POStyleID = ?,POFootnoteID = ?,POUseCountry = ?,"
					+ "StartingRINumber = ?,ProductTaxable = ?,EmployeeState = ?,EmployeeStateID = ?,"
					+ "EmployeeCountry = ?,EmployeeCountryID = ?,ChargeSalestax = ?,HowOftenSalestax = ?,"
					+ "SalesTaxID = ?,ShowReminder = ?,InvoiceMemo = ?,InvoiceMemoDays = ?,OverdueInvoice = ?,"
					+ "OverdueInvoiceDays = ?,InventoryOrder = ?,InventoryOrderDays = ?,BillstoPay = ?,"
					+ "BillstoPayDays = ?,CompanyLogoPath = ?,"
					+ "Charge_interest = ?,Charge_minimum =  ?,Charge_grace =  ?,Charge_reassess =  ?,"
					+ "Charge_name_display =  ?,TimeSheetSet = ? ,Performance=? ,"
					+ "Mailserver = ?, Mail_username = ?, Mail_password = ?,Mail_Auth = ?, Mail_senderEmail =? "
					+ " where CompanyID = ?";

			pstmt = con.prepareStatement(insertRecord);
			pstmt.setInt(1, cForm.getCurrencyID());
			pstmt.setString(2, "");
			pstmt.setInt(3, cForm.getWeightID());
			pstmt.setString(4, "");
			pstmt.setInt(5, cForm.getDefaultLabelID());
			pstmt.setString(6, cForm.getLabelName());
			pstmt.setString(7, "Admin");
			pstmt.setString(8, cForm.getPassword());
			pstmt.setInt(9, cForm.getMultiUserConnection());
			pstmt.setInt(10, cForm.getCustDefaultCountryID());
			pstmt.setString(11, "");

			if (cForm.getCustTaxable() == null)
				pstmt.setString(12, "0");
			else
				pstmt.setString(12, cForm.getCustTaxable().equals("on") ? "1" : "0");
			pstmt.setLong(13, cForm.getStartInvoiceNo());
			pstmt.setInt(14, cForm.getInvStyleID());
			pstmt.setInt(15, cForm.getDefaultFootnoteID());
			if (cForm.getIsProductWeight() == null)
				pstmt.setString(16, "0");
			else
				pstmt.setString(16, cForm.getIsProductWeight().equals("on") ? "1" : "0");
			if (cForm.getIsCompanyName() == null)
				pstmt.setString(17, "0");
			else
				pstmt.setString(17, cForm.getIsCompanyName().equals("on") ? "1" : "0");
			pstmt.setString(18, "");
			pstmt.setInt(19, cForm.getVendorDefaultCountryID());
			pstmt.setLong(20, cForm.getStartPONum());
			pstmt.setInt(21, cForm.getPoStyleID());
			pstmt.setInt(22, cForm.getVendorDefaultFootnoteID());
			pstmt.setInt(23, -1);
			pstmt.setLong(24, cForm.getStartRINum());
			if (cForm.getProductTaxable() == null)
				pstmt.setString(25, "0");
			else
				pstmt.setString(25, cForm.getProductTaxable().equals("on") ? "1" : "0");
			pstmt.setString(26, "");
			pstmt.setInt(27, cForm.getEmpStateID());
			pstmt.setString(28, "");
			pstmt.setInt(29, cForm.getEmpCountryID());
			if (cForm.getChargeSalesTax() == null)
				pstmt.setString(30, "0");
			else
				pstmt.setString(30, cForm.getChargeSalesTax().equals("on") ? "1" : "0");
			pstmt.setInt(31, cForm.getHowOftenSalesTax());
			pstmt.setInt(32, cForm.getSalesTaxID());
			if (cForm.getShowReminder() == null)
				pstmt.setString(33, "0");
			else
				pstmt.setString(33, cForm.getShowReminder().equals("on") ? "1" : "0");
			pstmt.setInt(34, cForm.getInvoiceMemo());
			pstmt.setInt(35, cForm.getInvoiceMemoDays());
			pstmt.setInt(36, cForm.getOverdueInvoice());
			pstmt.setInt(37, cForm.getOverdueInvoiceDays());
			pstmt.setInt(38, cForm.getInventoryOrder());
			pstmt.setInt(39, cForm.getInventoryOrderDays());
			pstmt.setInt(40, cForm.getBillsToPay());
			pstmt.setInt(41, cForm.getBillsToPayDays());

			// Company logo
			/*
			 * if (cForm.getInvoiceDefaultLogo().getFileName() == null ||
			 * cForm.getInvoiceDefaultLogo().getFileName().equals("")) { if
			 * (cForm.getFileName() == null || cForm.getFileName().equals(""))
			 * pstmt.setString(42, ""); else pstmt.setString(42, cForm.getFileName()); }
			 * else pstmt .setString(42, cForm.getInvoiceDefaultLogo() .getFileName());
			 */
			pstmt.setString(42, "");
			pstmt.setDouble(43, cForm.getAnnualInterestRate());
			pstmt.setDouble(44, cForm.getMinCharge());
			pstmt.setLong(45, cForm.getGracePeriod());

			if (cForm.getAssessFinanceCharge() == null)
				pstmt.setInt(46, 0);
			else
				pstmt.setInt(46, cForm.getAssessFinanceCharge().equals("on") ? 1 : 0);

			pstmt.setInt(47, 3);
			pstmt.setLong(48, cForm.getTimeSheet());

			// Performance
			if (cForm.getPerformance() == 1) {
				pstmt.setLong(49, cForm.getUserDefinePerform());
			} else {
				pstmt.setInt(49, cForm.getPerformance());
			}

			// SMTP
			pstmt.setString(50, cForm.getMailServer());
			pstmt.setString(51, cForm.getMailUserName());
			pstmt.setString(52, cForm.getMailPassword());
			if (cForm.getMailAuth() == null)
				pstmt.setInt(53, 0);
			else
				pstmt.setInt(53, cForm.getMailAuth().equals("on") ? 1 : 0);
			pstmt.setString(54, cForm.getSenderEmail());
			pstmt.setLong(55, compId);

			int saved = pstmt.executeUpdate();
			if (saved > 0) {
				isSaved = true;
				uploadImage(cForm, request);
			}

			pstmt.close();

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "saveConfigurationRecord "
					+ ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public void saveformCustomization(ConfigurationDto cForm) {
		Connection con = null;
		
		PreparedStatement pstmt = null, pstmt1 = null;
		String[] ActiveInvoiceStyleList = cForm.getListOfActiveInvoiceStyle();
		String[] DeActiveInvoiceStyleList = cForm.getListOfDeActiveInvoiceStyle();
		int i = ActiveInvoiceStyleList.length;
		int i1 = DeActiveInvoiceStyleList.length;
		try {
			con = executor.getConnection();

			for (i = 0; i < ActiveInvoiceStyleList.length; i++) {
				String updateActiveInvoiceStyle = "update bca_invoicestyle set Active = 1 where InvoiceStyleID='"
						+ ActiveInvoiceStyleList[i] + "'";
				pstmt = con.prepareStatement(updateActiveInvoiceStyle);
				int saved = pstmt.executeUpdate();
			}
			for (i1 = 0; i1 < DeActiveInvoiceStyleList.length; i1++) {
				String updateDeActiveInvoiceStyle = "update bca_invoicestyle set Active = 0 where InvoiceStyleID='"
						+ DeActiveInvoiceStyleList[i1] + "'";
				pstmt1 = con.prepareStatement(updateDeActiveInvoiceStyle);
				int saved = pstmt1.executeUpdate();
			}
			pstmt.close();
			pstmt1.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			executor.close(con);
		}
	}

	public boolean saveConfigurationRecordGeneral(ConfigurationDto cForm, long compId, HttpServletRequest request) {
		Connection con = null;
		
		PreparedStatement pstmt = null, pstmt1 = null, pstmt2 = null;
		boolean isSaved = false;
		HttpSession session = request.getSession();
		String companyID = (String) session.getAttribute("companyID");
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		// int CurrencyID= cForm.getCurrencyID();
		int CurrencyID = cForm.getCurrencyID();
		int WeightID = cForm.getWeightID();
		int DefaultLabelID = cForm.getDefaultLabelID();
		int ModuleID = cForm.getModuleID();

		String FilterOption = cForm.getFilterOption();
		String Poboard = cForm.getPoboard().equals("on") ? "1" : "0";
		String ItemReceivedBoard = cForm.getItemReceivedBoard().equals("on") ? "1" : "0";
		String ItemShippedBoard = cForm.getItemShippedBoard().equals("on") ? "1" : "0";
		String SalesOrderBoard = cForm.getSalesOrderBoard().equals("on") ? "1" : "0";
		try {
			String insertRecord = "update bca_preference set CurrencyID = " + CurrencyID + ",WeightID = " + WeightID
					+ ",LabelSizeID = " + DefaultLabelID + "" + ",defaultModule=" + ModuleID + ",FilterOption='"
					+ FilterOption + "'" + ",poboard=" + Poboard + ",itemsReceivedBoard=" + ItemReceivedBoard
					+ ",itemsShippedBoard=" + ItemShippedBoard + "" + ",SalesOrderBoard=" + SalesOrderBoard
					+ " where CompanyID =" + companyID;

			/*
			 * String insertRecord =
			 * "update bca_preference set CurrencyID = ?,WeightID = ?,LabelSizeID = ?,defaultModule=?,FilterOption=?"
			 * +
			 * ",poboard=?,itemsReceivedBoard=?,itemsShippedBoard=?,SalesOrderBoard=? where CompanyID = ?"
			 * ;
			 * 
			 * pstmt = con.prepareStatement(insertRecord); pstmt.setInt(1,
			 * cForm.getCurrencyID()); //pstmt.setString(2,""); pstmt.setInt(2,
			 * cForm.getWeightID()); //pstmt.setString(4, ""); pstmt.setInt(3,
			 * cForm.getDefaultLabelID()); //pstmt.setString(6, ""); //int mId =
			 * Integer.parseInt(cForm.getModuleName()); pstmt.setInt(4,cForm.getModuleID());
			 * //String filterOp = cForm.getFilterOption(); pstmt.setString(5,
			 * cForm.getFilterOption());
			 * 
			 * pstmt.setString(6, cForm.getPoboard().equals("on")?"1":"0");
			 * pstmt.setString(7, cForm.getItemReceivedBoard().equals("on")?"1":"0");
			 * pstmt.setString(8, cForm.getItemShippedBoard().equals("on")?"1":"0");
			 * pstmt.setString(9,cForm.getSalesOrderBoard().equals("on")?"1":"0");
			 * 
			 * pstmt.setLong(10, compId);
			 */
			pstmt = con.prepareStatement(insertRecord);
			int saved = pstmt.executeUpdate();
			if (saved > 0) {
				isSaved = true;
				// uploadImage(cForm, request);
			}

			pstmt.close();

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "saveConfigurationRecord "
					+ ex.toString());
		}

		String[] Modules = cForm.getListOfExistingModules1();
		int i = Modules.length;
//		String i = null;
		try {
			String DeleteModules = "delete FROM bca_businessmodules  where CompanyID=" + compId;
			pstmt1 = con.prepareStatement(DeleteModules);
			int check = pstmt1.executeUpdate();
			pstmt1.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		try {

			for (i = 0; i < Modules.length; i++) {
				String insertmodules = " insert into bca_businessmodules (ModuleName,Active,CompanyID) values('"
						+ Modules[i] + "',1," + compId + ")";
				pstmt2 = con.prepareStatement(insertmodules);
			}
			pstmt2.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean saveConfigurationRecordEstimation(ConfigurationDto cForm, long compId, HttpServletRequest request) {
		Connection con = null;
		
		PreparedStatement pstmt = null;
		boolean isSaved = false;

		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String insertRecord = "update bca_preference set StartingEstimationNumber=? where CompanyID = ?";

			pstmt = con.prepareStatement(insertRecord);

			pstmt.setInt(1, cForm.getStartingEstimationNumber());
			pstmt.setLong(2, compId);

			int saved = pstmt.executeUpdate();
			if (saved > 0) {
				isSaved = true;
				// uploadImage(cForm, request);
			}

			pstmt.close();

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "saveConfigurationRecord "
					+ ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean saveConfigurationRecordBilling(ConfigurationDto cForm, long compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;
		boolean isSaved = false;

		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String insertRecord = "update bca_preference set StartingBillNumber = ?,showCombinedBilling=?,"
					+ "showBillingStatStyle=?,PrintBills=?,MailToCustomer=?  where CompanyID = ?";

			pstmt = con.prepareStatement(insertRecord);

			pstmt.setLong(1, cForm.getStartingBillNumber());
			pstmt.setString(2, cForm.getShowCombinedBilling().equals("on") ? "1" : "0");
			pstmt.setInt(3, cForm.getShowBillingStatStyle());
			if (cForm.getPrintBills().equals("on")) {
				pstmt.setString(4, "1");
			} else /*
					 * if(cForm.getPrintBills().equals("off") ||
					 * cForm.getPrintBills().equals("false"))
					 */
			{
				pstmt.setString(4, "0");
			}
			if (cForm.getMailToCustomer().equals("on")) {
				pstmt.setString(5, "1");
			} else /*
					 * if(cForm.getMailToCustomer().equals("off") ||
					 * cForm.getMailToCustomer().equals("false"))
					 */
			{
				pstmt.setString(5, "0");
			}
			pstmt.setLong(6, compId);

			int saved = pstmt.executeUpdate();
			if (saved > 0) {
				isSaved = true;
				// uploadImage(cForm, request);
			}

			pstmt.close();

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "saveConfigurationRecord "
					+ ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	/*
	 * Upload(Saves) the image required for the default invoice logo. Also save the
	 * images to the uploadImages folder of the application.
	 */
	public void uploadImage(ConfigurationDto configFrm, HttpServletRequest request) {
		String fileSep = System.getProperty("file.separator");
		FileOutputStream fo = null;
		try {
			MultipartFile f = configFrm.getInvoiceDefaultLogo();

			Loger.log("value of f: " + f);
			String contentType = "";
			String filename = "";
			if (f != null) {
				contentType = f.getContentType();
				Loger.log(contentType);

				filename = f.getOriginalFilename();
			}

			StringTokenizer st = new StringTokenizer(contentType, "/");
			if (st.hasMoreTokens()) {
				String val = st.nextToken("/");
				if ("image".equals(val) == true) {

					Loger.log(request.getServletContext().getRealPath("/"));
					if (filename.length() > 0) {

						String s = request.getServletContext().getRealPath("uploadedImages") + fileSep + filename;
						byte[] contentArray = f.getBytes();

						if (contentArray != null || contentArray.length > 0) {
							File tosave = new File(s);
							fo = new FileOutputStream(tosave);
							fo.write(contentArray);
						}
					}

				}
			}

		} catch (IOException ee) {
			Loger.log(2, "error in execute() in PhotoAction class" + ee);
		} catch (Exception eee) {
			Loger.log(2, "error in execute() in PhotoAction class" + eee);
		} finally {
			try {
				if (fo != null)
					fo.close();
			} catch (Exception eeee) {
				Loger.log(2, "File Not Stored Properly in PhotoAction class" + eeee);
			}
		}

	}

	/*
	 * Invoke(get) the footnote id,name & description. So it is useful for the
	 * manupulation such as update,save new footnote & delete footnote.
	 */
	public void footnoteDetails(HttpServletRequest request) {
		Connection con = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ConfigurationDto> list = new ArrayList<ConfigurationDto>();
		if (executor == null)
			return;
		con = executor.getConnection();
		if (con == null)
			return;

		try {
			String compId = (String) request.getSession().getAttribute("CID");
			String footnoteDetail = "select FootNoteID,Name,Description from bca_footnote where Active=1 "
					+ "and CompanyID=? order by Name";
			pstmt = con.prepareStatement(footnoteDetail);
			pstmt.setString(1, compId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ConfigurationDto cForm = new ConfigurationDto();
				cForm.setFootnote(rs.getInt("FootNoteID"));
				cForm.setFootnoteName(rs.getString("Name"));
				cForm.setDesc(rs.getString("Description"));
				list.add(cForm);
			}
			request.setAttribute("FoootNoteDetails", list);
		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "footnoteDetails " + ex.toString());
		} finally {
			executor.close(con);
		}
	}

	/*
	 * The method deletes the footnote that selected by user It delete the footnote
	 * by footnote id.
	 */
	public boolean deleteFootnote(ConfigurationDto cForm, String compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isRecordDeleted = false;
		if (executor == null)
			return isRecordDeleted;
		con = executor.getConnection();
		if (con == null)
			return isRecordDeleted;

		try {
			String deleteQuery = "update bca_footnote set Active=0 where FootNoteID=? and CompanyID=?";
			pstmt = con.prepareStatement(deleteQuery);
			pstmt.setInt(1, cForm.getFootnote());
			pstmt.setString(2, compId);
			int deleted = pstmt.executeUpdate();
			if (deleted > 0) {
				isRecordDeleted = true;
			}
			pstmt.close();

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "deleteFootnote " + ex.toString());

		} finally {
			executor.close(con);
		}
		return isRecordDeleted;
	}

	/*
	 * Save the new footnote to the database. It save the footnote & its description
	 * to the database & generate id for it.
	 */
	public boolean saveFootnote(ConfigurationDto cForm, long compId, String footnotName) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String insertQuery = "insert into bca_footnote(CompanyID,Name,Description,Active) values(?,?,?,?)";
			pstmt = con.prepareStatement(insertQuery);
			pstmt.setLong(1, compId);
			pstmt.setString(2, footnotName);
			pstmt.setString(3, cForm.getDesc());
			pstmt.setInt(4, 1);
			int inserted = pstmt.executeUpdate();
			if (inserted > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "saveFootnote " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	/*
	 * The method update the footnote selected by user in the existing footnote.It
	 * update the footnote name & its description.
	 *
	 */
	public boolean updateFootnote(ConfigurationDto cForm, long compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isUpdated = false;
		if (executor == null)
			return isUpdated;
		con = executor.getConnection();
		if (con == null)
			return isUpdated;

		try {
			String updateQuery = "update bca_footnote set Description=?,Active=? where FootNoteID=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getDesc());
			pstmt.setInt(2, 1);
			pstmt.setInt(3, cForm.getFootnote());
			pstmt.setLong(4, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isUpdated = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "updateFootnote " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isUpdated;
	}

	/*
	 * The method add the new job code to the database with cost & optional
	 * description.
	 */
	public boolean addJobCodeTimesheet(HttpServletRequest request) {
		Connection con = null;
		
		PreparedStatement pstmt = null;
		boolean added = false;
		if (executor == null)
			return added;
		con = executor.getConnection();
		if (con == null)
			return added;
		try {
			String jName = request.getParameter("code");
			String jCost = request.getParameter("cost");
			String jDesc = request.getParameter("desc");
			long compId = Long.parseLong((String) request.getSession().getAttribute("CID"));
			String insertQuery = "insert into bcp_jobcode(Name,Cost,Description,CompanyID) value(?,?,?,?)";
			pstmt = con.prepareStatement(insertQuery);
			pstmt.setString(1, jName);
			pstmt.setString(2, jCost);
			pstmt.setString(3, jDesc);
			pstmt.setLong(4, compId);
			int isAdded = pstmt.executeUpdate();
			if (isAdded > 0) {
				added = true;
			}

		} catch (SQLException ex) {
			Loger.log(
					"Exception in the class ConfigurationInfo and in method " + "addJobCodeTimesheet " + ex.toString());
		} finally {
			executor.close(con);
		}
		return added;
	}

	/*
	 * Edit (update) the job code selected by user in existing job code with cost &
	 * optional description.
	 *
	 */
	public boolean editJobCodeTimesheet(HttpServletRequest request) {
		Connection con = null;
		
		PreparedStatement pstmt = null;
		boolean edited = false;
		if (executor == null)
			return edited;
		con = executor.getConnection();
		if (con == null)
			return edited;
		try {
			int jobId = Integer.parseInt(request.getParameter("jobId"));
			String jName = request.getParameter("code");
			String jCost = request.getParameter("cost");
			String jDesc = request.getParameter("desc");
			long compId = Long.parseLong((String) request.getSession().getAttribute("CID"));
			String insertQuery = "update bcp_jobcode set Name=?,Cost=?,Description=? where CompanyID=? and JobID=?";
			pstmt = con.prepareStatement(insertQuery);
			pstmt.setString(1, jName);
			pstmt.setString(2, jCost);
			pstmt.setString(3, jDesc);
			pstmt.setLong(4, compId);
			pstmt.setInt(5, jobId);

			int isupdated = pstmt.executeUpdate();
			if (isupdated > 0) {
				edited = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "editJobCodeTimesheet "
					+ ex.toString());
		} finally {
			executor.close(con);
		}
		return edited;
	}

	/*
	 * Remove the job code selected by user in existing job code from database.
	 */
	public boolean removeJobCodeTimesheet(HttpServletRequest request) {
		Connection con = null;
		
		PreparedStatement pstmt = null;
		boolean deleted = false;
		if (executor == null)
			return deleted;
		con = executor.getConnection();
		if (con == null)
			return deleted;
		try {
			int jobId = Integer.parseInt(request.getParameter("jobId"));
			long compId = Long.parseLong((String) request.getSession().getAttribute("CID"));
			String insertQuery = "delete from bcp_jobcode where JobID=? and CompanyID=?";
			pstmt = con.prepareStatement(insertQuery);
			pstmt.setInt(1, jobId);
			pstmt.setLong(2, compId);
			int isDeleted = pstmt.executeUpdate();
			if (isDeleted > 0) {
				deleted = true;
			}
			pstmt.close();

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "removeJobCodeTimesheet "
					+ ex.toString());
		} finally {
			executor.close(con);
		}
		return deleted;
	}

	/*
	 * Add the new service type to the database with invoice style id.
	 */
	public boolean addServiceType(String sName, int invStyleId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;
		boolean added = false;
		if (executor == null)
			return added;
		con = executor.getConnection();
		if (con == null)
			return added;
		try {
			String insertService = "insert into bca_servicetype(ServiceName,InvoiceStyleID) values(?,?)";
			pstmt = con.prepareStatement(insertService);
			pstmt.setString(1, sName);
			pstmt.setInt(2, invStyleId);
			int isAdded = pstmt.executeUpdate();
			if (isAdded > 0) {
				added = true;
			}
			pstmt.close();
		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "addServiceType " + ex.toString());
		} finally {
			executor.close(con);
		}
		return added;
	}

	/*
	 * Edit the existing service type with invoice style id.
	 */
	public boolean editServiceType(String sName, int invStyleId, int serviceId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;
		boolean edited = false;

		if (executor == null)
			return edited;
		con = executor.getConnection();
		if (con == null)
			return edited;

		try {
			String editService = "update bca_servicetype set ServiceName=?,InvoiceStyleID=? where ServiceID=?";
			pstmt = con.prepareStatement(editService);
			pstmt.setString(1, sName);
			pstmt.setInt(2, invStyleId);
			pstmt.setInt(3, serviceId);
			int isEdited = pstmt.executeUpdate();
			if (isEdited > 0) {
				edited = true;
			}
			pstmt.close();
		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "editServiceType " + ex.toString());
		} finally {
			executor.close(con);
		}
		return edited;
	}

	/*
	 * Delete the service type selected by user in the existing service type list
	 * from database. *
	 */
	public boolean deleteServiceType(int serviceId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;
		boolean deleted = false;

		if (executor == null)
			return deleted;
		con = executor.getConnection();
		if (con == null)
			return deleted;

		try {
			String deleteService = "delete from bca_servicetype where ServiceID=?";
			pstmt = con.prepareStatement(deleteService);
			pstmt.setInt(1, serviceId);
			int isDeleted = pstmt.executeUpdate();
			if (isDeleted > 0) {
				deleted = true;
			}
			pstmt.close();

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "deleteServiceType " + ex.toString());
		} finally {
			executor.close(con);
		}
		return deleted;
	}

	/*
	 * Get the service type id of newly added service type.
	 */
	public int getServiceId() {
		Connection con = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int serviceid = 0;
		if (executor == null)
			return 0;
		con = executor.getConnection();
		if (con == null)
			return 0;
		try {
			String serviceID = "select ServiceID from bca_servicetype order by ServiceID desc";
			pstmt = con.prepareStatement(serviceID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				serviceid = rs.getInt("ServiceID");
			}
			pstmt.close();
			rs.close();
		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "getServiceId " + ex.toString());
		} finally {
			executor.close(con);
		}
		return serviceid;
	}

	public boolean saveRMAReason(ConfigurationDto cForm, int compId) {

		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "insert into bca_rmareason(rmaReason,parentReasonID,CompanyID,Active) values(?,?,?,?)";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getReason());
			pstmt.setInt(2, cForm.getParentReasonId());
			pstmt.setInt(3, compId);
			pstmt.setInt(4, 1);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "saveRMAReason " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean deleteRMAReason(ConfigurationDto cForm) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isUpdated = false;
		if (executor == null)
			return isUpdated;
		con = executor.getConnection();
		if (con == null)
			return isUpdated;

		try {
			String updateQuery = "Update bca_rmareason set Active = 0 " + " Where rmaReason=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getReason());

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isUpdated = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "deleteRMAReason " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isUpdated;
	}

	public boolean updateRMAReason(ConfigurationDto cForm) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isUpdated = false;
		if (executor == null)
			return isUpdated;
		con = executor.getConnection();
		if (con == null)
			return isUpdated;

		try {
			String updateQuery = "Update bca_rmareason set rmaReason=?, parentReasonId = ? where ReasonID = ?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getReason());
			pstmt.setInt(2, cForm.getParentReasonId());
			pstmt.setInt(3, cForm.getReasonId());

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isUpdated = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "updateRMAReason " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isUpdated;
	}

	public boolean saveDefaultBankDetails(ConfigurationDto cForm, int compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isUpdated = false;
		if (executor == null)
			return isUpdated;
		con = executor.getConnection();
		if (con == null)
			return isUpdated;

		try {
			String updateQuery = "Update bca_preference set DefaultRMACheckingBankID=? where companyID = ?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setInt(1, cForm.getSelectedBankAccountId());
			pstmt.setInt(2, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isUpdated = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "saveDefaultBankDetails "
					+ ex.toString());
		} finally {
			executor.close(con);
		}
		return isUpdated;

	}

	public boolean saveConfigurationRecordInventorySettting(ConfigurationDto cForm, int compId) {
		Connection con = null;
		
		PreparedStatement pstmt, pstmt1 = null;

		boolean isUpdated = false;
		if (executor == null)
			return isUpdated;
		con = executor.getConnection();
		if (con == null)
			return isUpdated;

		try {
			String updateQuery = "Update bca_preference set showReorderPointWarring=?,reservedQuantity=?,salesOrderQty=?,productTaxable=? where companyID = ?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getShowReorderPointWarning().equals("on") ? "1" : "0");
			pstmt.setString(2, cForm.getReservedQuantity().equals("on") ? "1" : "0");
			pstmt.setString(3, cForm.getSalesOrderQty().equals("on") ? "1" : "0");
			pstmt.setString(4, cForm.getProductTaxable().equals("on") ? "1" : "0");
			pstmt.setInt(5, compId);
			int updated = pstmt.executeUpdate();
			int inserted = 0;
			executor.close(pstmt);

//			String query = "insert into bca_scheduleTimes(ScheduleDate,CompanyID) values(?,?)";
//			pstmt1 = con.prepareStatement(query);
//			pstmt1.setString(1, cForm.getScheduleTimes());
//			pstmt1.setInt(2,compId);
			executor.close(pstmt1);
			if (updated > 0 && inserted > 0) {
				isUpdated = true;
			}
		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method "
					+ "saveConfigurationRecordInventorySettting " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isUpdated;
	}

	public boolean saveFinanceCharges(ConfigurationDto cForm, int compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isUpdated = false;
		if (executor == null)
			return isUpdated;
		con = executor.getConnection();
		if (con == null)
			return isUpdated;

		try {
			String updateQuery = "Update bca_preference set Charge_interest=?,Charge_minimum=?,"
					+ "Charge_grace=?,Charge_reassess=? where companyID = ?";

			pstmt = con.prepareStatement(updateQuery);
			pstmt.setDouble(1, cForm.getAnnualInterestRate());
			pstmt.setDouble(2, cForm.getMinCharge());
			pstmt.setInt(3, cForm.getGracePeriod());
			pstmt.setString(4, cForm.getAssessFinanceCharge().equals("on") ? "1" : "0");
			pstmt.setInt(5, compId);

			int updated = pstmt.executeUpdate();

			if (updated > 0) {
				isUpdated = true;
			}

		} catch (SQLException ex) {
			Loger.log(
					"Exception in the class ConfigurationInfo and in method " + "saveFinanceCharges " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isUpdated;
	}

	public boolean saveAccountPaymentDetails(ConfigurationDto cForm, int compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isUpdated = false;
		if (executor == null)
			return isUpdated;
		con = executor.getConnection();
		if (con == null)
			return isUpdated;

		try {
			System.out.println("Inside query...");
			// String updateQuery = "Update bca_preference set StartingBillNumber=? where
			// companyID = ?";
			String updateQuery = "Update bca_preference set Charge_interest=?,Charge_minimum=?,"
					+ "Charge_grace=?,Charge_reassess=?,defaultBankTransferAccID=?,defaultARCategoryID=?,DefaultReimbusrementSetting=?,BudgetStartMonth=?,BudgetEndMonth=?,AutoPaymentDuration=?, "
					+ "defaultARCategoryIDforac=?,defaultARCategoryIDforpo=?,defaultARCategoryIDforbp=?,defaultdepositoforac=?,defaultdepositoforpo=?,"
					+ "defaultdepositoforbp=?,defaultReceivedforac=?,defaultReceivedforpo=?,defaultReceivedforbp=?,StartingBillNumber=? where companyID = ?";

			System.out.println("Hello\n" + updateQuery);
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setDouble(1, cForm.getAnnualInterestRate());
			pstmt.setDouble(2, cForm.getMinCharge());
			pstmt.setInt(3, cForm.getGracePeriod());
			pstmt.setString(4,
					(cForm.getAssessFinanceCharge() != null && cForm.getAssessFinanceCharge().equals("on")) ? "1"
							: "0");
			pstmt.setInt(5, cForm.getDefaultPaymentMethodId());
			pstmt.setInt(6, cForm.getDefaultCategoryId());
			pstmt.setInt(7, cForm.getReimbursementSettings());
			pstmt.setInt(8, cForm.getStartMonth());
			pstmt.setInt(9, cForm.getEndMonth());
			pstmt.setInt(10, cForm.getScheduleDays());
			pstmt.setInt(11, cForm.getArCategory());
			pstmt.setInt(12, cForm.getPoCategory());
			pstmt.setInt(13, cForm.getBpCategory());
			pstmt.setInt(14, cForm.getArDepositTo());
			pstmt.setInt(15, cForm.getPoDepositTo());
			pstmt.setInt(16, cForm.getBpDepositTo());
			pstmt.setInt(17, cForm.getArReceivedType());
			pstmt.setInt(18, cForm.getPoReceivedType());
			pstmt.setInt(19, cForm.getBpReceivedType());

			pstmt.setLong(20, cForm.getStartingBillNumber());

			// pstmt.setInt(20, compId);
			pstmt.setInt(21, compId);

			/*
			 * pstmt.setLong(1, cForm.getStartingBillNumber()); pstmt.setInt(2, compId);
			 */

			int updated = pstmt.executeUpdate();

			if (updated > 0) {
				isUpdated = true;
			}

		} catch (SQLException ex) {
			/*
			 * Loger.log("Exception in the class ConfigurationInfo and in method " +
			 * "saveFinanceCharges " + ex.toString());
			 */
			System.out.println("Exception in the class ConfigurationInfo and in method " + "saveAccountPaymentDetails "
					+ ex.toString());
		} finally {
			executor.close(con);
		}
		return isUpdated;
	}

	public boolean savePerformance(ConfigurationDto cForm, int compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isUpdated = false;
		if (executor == null)
			return isUpdated;
		con = executor.getConnection();
		if (con == null)
			return isUpdated;

		try {
			String updateQuery = "Update bca_preference set Performance=? where companyID = ?";

			pstmt = con.prepareStatement(updateQuery);
			pstmt.setDouble(1, cForm.getPerformance());
			pstmt.setInt(2, compId);

			int updated = pstmt.executeUpdate();

			if (updated > 0) {
				isUpdated = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "savePerformance " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isUpdated;
	}

	public boolean saveDashboard(ConfigurationDto cForm, int compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isUpdated = false;
		if (executor == null)
			return isUpdated;
		con = executor.getConnection();
		if (con == null)
			return isUpdated;

		try {
			String updateQuery = "Update bca_preference set poboard=?,itemsReceivedBoard=?,itemsShippedBoard=?,SalesOrderBoard=? where companyID = ?";

			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getPoboard().equals("on") ? "1" : "0");
			pstmt.setString(2, cForm.getItemReceivedBoard().equals("on") ? "1" : "0");
			pstmt.setString(3, cForm.getItemShippedBoard().equals("on") ? "1" : "0");
			pstmt.setString(4, cForm.getSalesOrderBoard().equals("on") ? "1" : "0");
			pstmt.setInt(5, compId);

			int updated = pstmt.executeUpdate();

			if (updated > 0) {
				isUpdated = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "saveDashboard " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isUpdated;
	}

	public boolean saveReminder(ConfigurationDto cForm, int compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isUpdated = false;
		if (executor == null)
			return isUpdated;
		con = executor.getConnection();
		if (con == null)
			return isUpdated;

		try {
			String updateQuery = "Update bca_preference set ShowReminder=?,InvoiceMemo=?,InvoiceMemoDays=?,OverdueInvoice=?,OverdueinvoiceDays=?,"
					+ "InventoryOrder=?,InventoryOrderDays=?,BillstoPay=?,BillstoPayDays=?,EstimationMemo=?,EstimationMemoDays=?,"
					+ "POMemo=?,POMemoDays=?,MemoBill=?,MemoBillDays=?,ServiceBillsMemo=?,ServiceBillsMemoDays=? where companyID = ?";

			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getShowReminder().equals("on") ? "1" : "0");
			pstmt.setInt(2, cForm.getInvoiceMemo()); // InvoiceMemo
			pstmt.setInt(3, cForm.getInvoiceMemoDays());
			pstmt.setInt(4, cForm.getOverdueInvoice()); // overDue Invoice
			pstmt.setInt(5, cForm.getOverdueInvoiceDays());
			pstmt.setInt(6, cForm.getInventoryOrder()); // inventory order
			pstmt.setInt(7, cForm.getInventoryOrderDays());
			pstmt.setInt(8, cForm.getBillsToPay()); // BillsToPay
			pstmt.setInt(9, cForm.getBillsToPayDays());
			pstmt.setInt(10, cForm.getMemorizeEstimation()); // MemorizeEstimation
			pstmt.setInt(11, cForm.getMemorizeEstimationDays());
			pstmt.setInt(12, cForm.getMemorizePurchaseOrder()); // PoMemo
			pstmt.setInt(13, cForm.getMemorizePurchaseOrderDays());
			pstmt.setInt(14, cForm.getMemorizeBill()); // MemoBill
			pstmt.setInt(15, cForm.getMemorizeBillDays());
			pstmt.setInt(16, cForm.getServiceBilling()); // Service Billing
			pstmt.setInt(17, cForm.getServiceBillingDays());
			pstmt.setInt(18, compId);

			int updated = pstmt.executeUpdate();

			if (updated > 0) {
				isUpdated = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "saveReminder " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isUpdated;
	}

	public boolean saveCustomerInvoice(ConfigurationDto cForm, int compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isUpdated = false;
		if (executor == null)
			return isUpdated;
		con = executor.getConnection();
		if (con == null)
			return isUpdated;

		try {
			String updateQuery = "Update bca_preference set DEFAULTCustomerSortID=?,DEFAULTCustomerGroupID=?,CustomerCountryID=?,CustomerStateID=?,"
					+ "CustomerTaxable=?,showSalesOrder=?,CustomerProvience=?,SalesViaID=?,SalesTermID=?,SalesRepID=?,SalesPayMethodID=?,copyAddress=?,"
					+ "StartingInvoiceNumber=?,DefaultPackingSlipStyleID=?,SalesPOPrefix=?,InvoiceFootnoteID=?,SaleShowCountry=?,IsRatePriceChangeble=?,"
					+ "SaleShowTelephone=?,IsSalePrefix=?,ExtraCharge=?,ChargeAmount=?,OrderAmount=?,HowOftenSalestax=?,DropShipCharge=?,SalesTaxCode=?,SalesTaxRate=?,"
					+ "DropShipCharge=?,ShowDropShipItems=?,isRefundAllowed=?,StartingEstimationNumber=?,InvoiceStyleID =?,POTermID=?, SalesRepID=?, POPayMethodID=?, DEFAULTBankTransferAccID=? "
					+ " where companyID = ?";
			int r = cForm.getSortBy();
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setInt(1, cForm.getSortBy());
			pstmt.setInt(2, cForm.getCustomerGroup());
			pstmt.setInt(3, cForm.getCustDefaultCountryID());
			pstmt.setInt(4, cForm.getSelectedStateId());
			pstmt.setString(5, cForm.getCustTaxable().equals("on") ? "1" : "0");
			pstmt.setString(6, cForm.getIsSalesOrder().equals("on") ? "1" : "0");
			pstmt.setString(7, cForm.getCustomerProvince());
			pstmt.setInt(8, cForm.getCustomerShippingId());
			pstmt.setInt(9, cForm.getSelectedTermId());
			pstmt.setInt(10, cForm.getSelectedSalesRepId());
			pstmt.setInt(11, cForm.getSelectedPaymentId());
			pstmt.setString(12, cForm.getAddressSettings().equals("on") ? "1" : "0");

			pstmt.setLong(13, cForm.getStartInvoiceNo());
			pstmt.setInt(14, cForm.getPackingSlipTemplateId());
			pstmt.setString(15, cForm.getPoNumPrefix());
			pstmt.setInt(16, cForm.getSelectedMessageId());
			pstmt.setString(17, cForm.getSaleShowCountry().equals("on") ? "1" : "0");
			pstmt.setString(18, cForm.getRatePriceChangable().equals("on") ? "1" : "0");
			pstmt.setString(19, cForm.getSaleShowTelephone().equals("on") ? "1" : "0");
			pstmt.setString(20, cForm.getIsSalePrefix().equals("on") ? "1" : "0");
			pstmt.setString(21, cForm.getExtraChargeApplicable().equals("on") ? "1" : "0");
			pstmt.setInt(22, cForm.getChargeAmount());
			pstmt.setInt(23, cForm.getOrderAmount());
			pstmt.setInt(24, cForm.getHowOftenSalestax());
			pstmt.setInt(25, cForm.getDropShipCharge());
			pstmt.setString(26, cForm.getSalesTaxCode());
			pstmt.setDouble(27, cForm.getSaleTaxRate());
			pstmt.setInt(28, cForm.getDropShipCharge());
			pstmt.setInt(29, cForm.getIsShowDropShipItems());
			pstmt.setString(30, cForm.getIsRefundAllowed().equals("on") ? "1" : "0");
			pstmt.setInt(31, cForm.getStartingEstimationNumber());
			pstmt.setInt(32, cForm.getInvStyleID());
			pstmt.setInt(33, cForm.getSelectedTermId());
			pstmt.setInt(34, cForm.getSelectedSalesRepId());
			pstmt.setInt(35, cForm.getSelectedPaymentId());
			pstmt.setInt(36, cForm.getDefaultPaymentMethodId());
			pstmt.setInt(37, compId); // Added on 01-05-2020

			int updated = pstmt.executeUpdate();

			if (updated > 0) {
				isUpdated = true;
			}

		} catch (SQLException ex) {
			Loger.log(
					"Exception in the class ConfigurationInfo and in method " + "saveCustomerInvoice " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isUpdated;
	}

	public boolean saveDescription(ConfigurationDto cForm, int compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "insert into bca_location(Name,CompanyID,Active) values(?,?,?)";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getDescription());
			pstmt.setInt(2, compId);
			pstmt.setInt(3, 1);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "saveDescription " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean deleteLocation(int descriptionID, int compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_location set Active=0 where LocationId=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setInt(1, descriptionID);
			pstmt.setInt(2, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "deleteDescription " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean updateDescription(ConfigurationDto cForm, int compId, int locationId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_location set  Name=? where LocationId=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getDescription());
			pstmt.setInt(2, locationId);
			pstmt.setInt(3, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "updateDescription " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean saveMessage(ConfigurationDto cForm, int compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "insert into bca_message(Name,CompanyID,Active) values(?,?,?)";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getDescription());
			pstmt.setInt(2, compId);
			pstmt.setInt(3, 1);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "saveMessage " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean saveSalesRep(ConfigurationDto cForm, int compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "insert into bca_salesrep(Name,CompanyID,Active) values(?,?,?)";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getDescription());
			pstmt.setInt(2, compId);
			pstmt.setInt(3, 1);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "saveSalesRep " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean saveNewTerm(ConfigurationDto cForm, int compId, int days) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "insert into bca_term(Name,CompanyID,Active,Days) values(?,?,?,?)";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getDescription());
			pstmt.setInt(2, compId);
			pstmt.setInt(3, 1);
			pstmt.setInt(4, days);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "saveNewTerm " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean saveNewSalesTax(ConfigurationDto cForm, int compId, float tax) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "insert into bca_salestax(State,CompanyID,Active,Rate) values(?,?,?,?)";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getDescription());
			pstmt.setInt(2, compId);
			pstmt.setInt(3, 1);
			pstmt.setFloat(4, tax);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "saveNewSalesTax " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean saveNewCreditTerms(ConfigurationDto cForm, int compId, int days) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "insert into bca_lineofcreditterm(Name,CompanyID,Active,Days,isDefault) values(?,?,?,?,?)";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getDescription());
			pstmt.setInt(2, compId);
			pstmt.setInt(3, 1);
			pstmt.setInt(4, days);
			pstmt.setInt(5, cForm.getIsDefaultCreditTerm().equals("on") ? 1 : 0);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log(
					"Exception in the class ConfigurationInfo and in method " + "saveNewCreditTerms " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean updateMessage(ConfigurationDto cForm, int compId, int messageID) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_Message set Name=? where MessageId=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getDescription());
			pstmt.setInt(2, messageID);
			pstmt.setInt(3, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "updateMessage " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean updateSalesRep(ConfigurationDto cForm, int compId, int salesRepID) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_salesrep set Name=? where SalesRepID=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getDescription());
			pstmt.setInt(2, salesRepID);
			pstmt.setInt(3, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "updateSalesRep " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean updateTerm(ConfigurationDto cForm, int compId, int termID, int days) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_term set Name=?,Days=? where TermID=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getDescription());
			pstmt.setInt(2, days);
			pstmt.setInt(3, termID);
			pstmt.setInt(4, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "updateTerm " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean updateSalesTax(ConfigurationDto cForm, int compId, int salesTaxID, float tax) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_salestax set State=?,Rate=? where SalesTaxID=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getDescription());
			pstmt.setFloat(2, tax);
			pstmt.setInt(3, salesTaxID);
			pstmt.setInt(4, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "updateSalesTax " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean updateCreditTerm(ConfigurationDto cForm, int compId, int creditTermID, int days) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_lineofcreditterm set Name=?,Days=?,isDefault=? where CreditTermId=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, cForm.getDescription());
			pstmt.setFloat(2, days);
			pstmt.setInt(3, cForm.getIsDefaultCreditTerm().equals("on") ? 1 : 0);
			pstmt.setInt(4, creditTermID);
			pstmt.setInt(5, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "updateCreditTerm " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean deleteMessage(int compId, int messageID) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_Message set Active=? where MessageId=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setInt(1, 0);
			pstmt.setInt(2, messageID);
			pstmt.setInt(3, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "deleteMessage " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean deleteSalesRep(int compId, int salesRepId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_salesrep set Active=? where SalesRepID=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setInt(1, 0);
			pstmt.setInt(2, salesRepId);
			pstmt.setInt(3, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "deleteSalesRep " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean deleteTerm(int compId, int termId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_term set Active=? where TermID=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setInt(1, 0);
			pstmt.setInt(2, termId);
			pstmt.setInt(3, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "deleteTerm " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean deleteSalesTax(int compId, int salesTaxId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_salestax set Active=? where SalesTaxID=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setInt(1, 0);
			pstmt.setInt(2, salesTaxId);
			pstmt.setInt(3, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "deleteSalesTax " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean deleteCreditTerm(int compId, int creditTermId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_lineofcreditterm set Active=? where CreditTermId=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setInt(1, 0);
			pstmt.setInt(2, creditTermId);
			pstmt.setInt(3, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "deleteCreditTerm " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean insertRefundReason(int compId, String refundReason) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "insert into bca_refundreason (RefundReason,Active,IsDefaultReason,CompanyID) values(?,?,?,?)";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, refundReason);
			pstmt.setInt(2, 1);
			pstmt.setInt(3, 0);
			pstmt.setInt(4, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log(
					"Exception in the class ConfigurationInfo and in method " + "insertRefundReason " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean updateRefundReason(int compId, int refundReasonId, String newRefundReason) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_refundreason set RefundReason=?,Active=?,IsDefaultReason=? where ReasonID=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, newRefundReason);
			pstmt.setInt(2, 1);
			pstmt.setInt(3, 0);
			pstmt.setInt(4, refundReasonId);
			pstmt.setInt(5, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log(
					"Exception in the class ConfigurationInfo and in method " + "updateRefundReason " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean deleteRefundReason(int compId, int refundReasonId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_refundreason set Active=? where ReasonID=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setInt(1, 0);
			pstmt.setInt(2, refundReasonId);
			pstmt.setInt(3, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log(
					"Exception in the class ConfigurationInfo and in method " + "deleteRefundReason " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean defaultRefundReason(int compId, int refundReasonId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_refundreason set IsDefaultReason=? where ReasonID=? and CompanyID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, refundReasonId);
			pstmt.setInt(3, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log(
					"Exception in the class ConfigurationInfo and in method " + "defaultRefundReason " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean addJobCategory(ConfigurationDto cForm, int compId, String jobCategory) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "insert into bca_jobcategory (Name,CompanyID,isRecurringServiceJob,Active) VALUES (?,?,?,?)";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, jobCategory);
			pstmt.setInt(2, compId);
			pstmt.setInt(3, 0);
			pstmt.setInt(4, 1);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "addJobCategory " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean updateJobCategory(ConfigurationDto cForm, int compId, int jobCategoryId, String newJobCategoryName) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_jobcategory set Name=?,isRecurringServiceJob=? where CompanyID=? and JobCategoryID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, newJobCategoryName);
			pstmt.setInt(2, cForm.getRecurringServiceBill().equals("on") ? 1 : 0);
			pstmt.setInt(3, compId);
			pstmt.setInt(4, jobCategoryId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "updateJobCategory " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean deleteJobCategory(int jCategoryId, int compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "update bca_jobcategory set Active=? where CompanyID=? and JobCategoryID=?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setInt(1, 0);
			pstmt.setInt(2, compId);
			pstmt.setInt(3, jCategoryId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method " + "deleteJobCategory " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean editServiceBillInfo(ConfigurationDto cForm, String billName, int compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "UPDATE bca_jobcategory SET Name = ?,Active = ? WHERE isRecurringServiceJob  = ? "
					+ " AND JobCategoryID = ?" + " AND CompanyID = ?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, billName);
			pstmt.setInt(2, cForm.getRecurringServiceBill().equals("on") ? 1 : 0);
			pstmt.setInt(3, 1);
			pstmt.setInt(4, -1);
			pstmt.setInt(5, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log(
					"Exception in the class ConfigurationInfo and in method " + "editServiceBillInfo " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}

	public boolean saveVendorPurchaseValuesInConfigInfo(ConfigurationDto cForm, int compId) {
		Connection con = null;
		
		PreparedStatement pstmt = null;

		boolean isSaved = false;
		if (executor == null)
			return isSaved;
		con = executor.getConnection();
		if (con == null)
			return isSaved;

		try {
			String updateQuery = "UPDATE bca_preference SET DefaultVendorrSortID = ?,VendorCountryID = ?,VendorStateID=?,VendorProvience=?,StartingPONumber=?,"
					+ "POFootnoteID=?,POViaID=?,POTermID=?,PORepID=?,POPayMethodID=?,EmployeeInChargeID=?,POShowCountry=?,POShowTelephone=?,IsPurchasePrefix=?"
					+ ",DEFAULTARCategoryID=? where CompanyID = ?";
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setInt(1, cForm.getSortBy());
			pstmt.setInt(2, cForm.getSelectedCountryId1());
			pstmt.setInt(3, cForm.getSelectedStateId1());
			pstmt.setString(4, cForm.getVendorProvience());
			pstmt.setLong(5, cForm.getStartPONum());
			pstmt.setInt(6, cForm.getVendorDefaultFootnoteID());
			pstmt.setInt(7, cForm.getShipCarrierId());
			pstmt.setInt(8, cForm.getSelectedTermId());
			pstmt.setInt(9, cForm.getSelectedSalesRepId());
			pstmt.setInt(10, cForm.getSelectedPaymentId());
			pstmt.setInt(11, cForm.getSelectedActiveEmployeeId());
			pstmt.setString(12, cForm.getPoShowCountry().equals("on") ? "1" : "0");
			pstmt.setString(13, cForm.getPoShowTelephone().equals("on") ? "1" : "0");
			pstmt.setString(14, cForm.getIsPurchasePrefix().equals("on") ? "1" : "0");
			pstmt.setInt(15, cForm.getSelectedCategoryId());
			pstmt.setInt(16, compId);

			int updated = pstmt.executeUpdate();
			if (updated > 0) {
				isSaved = true;
			}

		} catch (SQLException ex) {
			Loger.log("Exception in the class ConfigurationInfo and in method "
					+ "saveVendorPurchaseValuesInConfigInfo " + ex.toString());
		} finally {
			executor.close(con);
		}
		return isSaved;
	}
}
