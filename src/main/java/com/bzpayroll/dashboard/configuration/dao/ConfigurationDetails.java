package com.bzpayroll.dashboard.configuration.dao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bzpayroll.common.utility.CountryState;
import com.bzpayroll.dashboard.configuration.forms.ConfigurationDto;

/**
 * This class contains methods to save,delete,update configuration related
 * information (i.e:- footnote,all configuration records).
 */
@Service
public class ConfigurationDetails {

	@Autowired
	private ConfigurationInfo configInfo;
	
	@Autowired
	private CountryState conState;


	// Invoke all the configuration related information (i.e:-
	// invenroty,sales,purchase,etc).
	public void getConfigurationInfo(HttpServletRequest request, ConfigurationDto configDto) {
		String compId = (String) request.getSession().getAttribute("CID");
		/* For the label list */
		request.setAttribute("Labels", configInfo.labelInfo());

		/* For the user group list */
		request.setAttribute("UserGroup", configInfo.userGroupInfo(compId));

		/* For country list */
		request.setAttribute("CountryList", conState.getCountry());

		/* For invoice style List */
		request.setAttribute("InvStyle", configInfo.invoiceStyleList());

		/* For Footnote List */
		request.setAttribute("Footnote", configInfo.footnoteList(compId));

		/* For Job Code List */
		request.setAttribute("JobCodeDetail", configInfo.jobCodeList(compId));

		/* For sales tax List */
		request.setAttribute("SalesTax", configInfo.salesTaxList(compId));

		/* For service type List */
		request.setAttribute("ServiceType", configInfo.serviceTypeList(request));
		configInfo.getCongurationRecord(compId, configDto, request);

	}

	/*
	 * Invoke the existing footnotes information (i.e:- footnot list & description).
	 */
	public void newFootnote(HttpServletRequest request, ConfigurationDto configDto) {
		
		configInfo.footnoteDetails(request);
		configDto.setFootnote(0);
		configDto.setDesc("");
		request.setAttribute("Footnote", configInfo.footnoteList((String) request.getSession().getAttribute("CID")));
	}

	/*
	 * Delete the existing,user selected footnote & the related information.
	 */
	public void deleteFootnote(HttpServletRequest request, ConfigurationDto configDto) {
		
		boolean isDeleted = configInfo.deleteFootnote(configDto, (String) request.getSession().getAttribute("CID"));
		String msg = "";
		if (isDeleted == true) {
			msg = "Footnote is successfully deleted";
		} else {
			msg = "Footnote is not deleted";
		}
		newFootnote(request, configDto);
		request.setAttribute("Status", msg);
	}

	/*
	 * Save the user entered footnote& related information to the database.
	 */
	public void saveFootnote(HttpServletRequest request, ConfigurationDto configDto) {
		
		boolean isSaved = configInfo.saveFootnote(configDto,
				Long.parseLong((String) request.getSession().getAttribute("CID")), request.getParameter("FootName"));
		String msg = "";
		if (isSaved) {
			msg = "Footnote is successfully saved";
		} else {
			msg = "Footnote is not saved";
		}
		newFootnote(request, configDto);
		request.setAttribute("Status", msg);
	}

	/*
	 * Update the user selected footnote & its related information
	 */
	public void updateFootnote(HttpServletRequest request, ConfigurationDto configDto) {
		
		boolean isUpdated = configInfo.updateFootnote(configDto,
				Long.parseLong((String) request.getSession().getAttribute("CID")));

		String msg = "";
		if (isUpdated) {
			msg = "Footnote is successfully updated";
		} else {
			msg = "Footnote is not updated";
		}
		newFootnote(request, configDto);
		request.setAttribute("Status", msg);
	}

	/*
	 * Saves the all configuration records (i.e:- networking,sales,purchase,etc.) to
	 * the database.
	 */
	public void saveRecords(ConfigurationDto configDto, HttpServletRequest request, int multiUserConnection) {
		configDto.setMultiUserConnection(multiUserConnection);
		
		long compId = Long.parseLong((String) request.getSession().getAttribute("CID"));
		configInfo.saveConfigurationRecord(configDto, compId, request);
	}

	public void saveInvoiceStyle(ConfigurationDto configDto, String[] ActiveInvoiceStylelists,
			String[] DeActiveInvoiceStylelists) {
		configDto.setListOfActiveInvoiceStyle(ActiveInvoiceStylelists);
		configDto.setListOfDeActiveInvoiceStyle(DeActiveInvoiceStylelists);
		
		configInfo.saveformCustomization(configDto);
	}

	public void saveRecordsGeneral(ConfigurationDto configDto, HttpServletRequest request, String salesOrder,
			String itemReceived, String itemShipped, String poBoard, int CurrencyID, int weightID, int defaultLabelID,
			String filterOption, int moduleID, String[] moduleslists) {
		configDto.setSalesOrderBoard(salesOrder);
		configDto.setItemReceivedBoard(itemReceived);
		configDto.setItemShippedBoard(itemShipped);
		configDto.setPoboard(poBoard);
		configDto.setCurrencyID(CurrencyID);
		configDto.setWeightID(weightID);

		configDto.setFilterOption(filterOption);
		configDto.setDefaultLabelID(defaultLabelID);
		configDto.setListOfExistingModules1(moduleslists);
		
		long compId = Long.parseLong((String) request.getSession().getAttribute("CID"));
		configInfo.saveConfigurationRecordGeneral(configDto, compId, request);
	}

	public void saveRecordsEstimation(ConfigurationDto configDto, HttpServletRequest request) {
		
		long compId = Long.parseLong((String) request.getSession().getAttribute("CID"));
		configInfo.saveConfigurationRecordEstimation(configDto, compId, request);
	}

	public void saveRecordsBilling(ConfigurationDto configDto, HttpServletRequest request, String printValue,
			String mailCust, String showCombinedBilling) {
		configDto.setShowCombinedBilling(showCombinedBilling);
		configDto.setPrintBills(printValue);
		configDto.setMailToCustomer(mailCust);
		
		long compId = Long.parseLong((String) request.getSession().getAttribute("CID"));
		configInfo.saveConfigurationRecordBilling(configDto, compId);
	}

	public void addRMAReason(ConfigurationDto configDto, String companyID, String reason, int parentReasonID) {
		configDto.setReason(reason);
		configDto.setParentReasonId(parentReasonID);
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.saveRMAReason(configDto, compId);
		if (saved) {
			System.out.println("Reason Added Successfully...");
		} else {
			System.out.println("Error in addRMAReason!!!!!");
		}
	}

	public void deleteRMAReason(ConfigurationDto configDto, String reason, int parentReasonID) {
		configDto.setReason(reason);
		configDto.setParentReasonId(parentReasonID);
		
		boolean saved = configInfo.deleteRMAReason(configDto);
		if (saved) {
			System.out.println("Reason Deleted Successfully...");
		} else {
			System.out.println("Error in deleteRMAReason!!!!!");
		}
	}

	public void updateMAReason(ConfigurationDto configDto, String reason, int reasonId, int parentReasonID) {
		configDto.setReasonId(reasonId);
		configDto.setReason(reason);
		configDto.setParentReasonId(parentReasonID);
		
		boolean saved = configInfo.updateRMAReason(configDto);
		if (saved) {
			System.out.println("Reason Updated Successfully...");
		} else {
			System.out.println("Error in updateRMAReason!!!!!");
		}
	}

	public void saveDefaultBank(ConfigurationDto configDto, HttpServletRequest request) {
		
		int compId = Integer.parseInt((String) request.getSession().getAttribute("CID"));
		boolean updated = configInfo.saveDefaultBankDetails(configDto, compId);
		if (updated) {
			System.out.println("Bank Updated Successfully...");
		} else {
			System.out.println("Error in SavingDefaultBankDetails!!!!!");
		}
	}

	public void saveRecordsInventorySettings(ConfigurationDto configDto, HttpServletRequest request) {
		configDto.setShowReorderPointWarning(request.getParameter("showReorderPointWarning"));
		configDto.setReservedQuantity(request.getParameter("reservedQuantity"));
		configDto.setSalesOrderQty(request.getParameter("salesOrderQty"));
		configDto.setProductTaxable(request.getParameter("productTaxable"));

		
		int compId = Integer.parseInt((String) request.getSession().getAttribute("CID"));
		boolean saved = configInfo.saveConfigurationRecordInventorySettting(configDto, compId);
		if (saved) {
			System.out.println("Record Saved Successfully...");
		} else {
			System.out.println("Error in saveRecordsInventorySettings!!!!!");
		}
	}

	public void saveFinanceCharges(ConfigurationDto configDto, HttpServletRequest request, String companyID,
			String assetFinanceChargeStatus) {
		configDto.setAssessFinanceCharge(assetFinanceChargeStatus);
		
		int compId = Integer.parseInt((String) request.getSession().getAttribute("CID"));
		boolean saved = configInfo.saveFinanceCharges(configDto, compId);
		if (saved) {
			System.out.println("Finance Charges Saved Successfully...");
		} else {
			System.out.println("Error in saveFinanceCharges!!!!!");
		}

	}

	public void saveAccountPaymentDetails(ConfigurationDto configDto, HttpServletRequest request, String companyID) {
		
		int compId = Integer.parseInt((String) request.getSession().getAttribute("CID"));
		boolean saved = configInfo.saveAccountPaymentDetails(configDto, compId);
		if (saved) {
			System.out.println("Payment Details Updated Successfully...");
		} else {
			System.out.println("Error in saveAccountPaymnetDetails!!!!!");
		}

	}

	public void savePerformance(ConfigurationDto configDto, HttpServletRequest request, String companyID) {
		
		int compId = Integer.parseInt((String) request.getSession().getAttribute("CID"));
		boolean saved = configInfo.savePerformance(configDto, compId);
		if (saved) {
			System.out.println("Performance Updated Successfully...");
		} else {
			System.out.println("Error in saveAccountPaymnetDetails!!!!!");
		}
	}

	public void saveDashboardSetting(ConfigurationDto configDto, HttpServletRequest request, String companyID,
			String salesOrder, String itemReceived, String itemShipped, String poBoard) {
		configDto.setSalesOrderBoard(salesOrder);
		configDto.setItemReceivedBoard(itemReceived);
		configDto.setItemShippedBoard(itemShipped);
		configDto.setPoboard(poBoard);

		
		int compId = Integer.parseInt((String) request.getSession().getAttribute("CID"));
		boolean saved = configInfo.saveDashboard(configDto, compId);
		if (saved) {
			System.out.println("Dashboard Updated Successfully...");
		} else {
			System.out.println("Error in saveDashboardSetting!!!!!");
		}
	}

	public void saveReminderSetting(ConfigurationDto configDto, HttpServletRequest request, String companyID,
			String showReminderStatus) {
		configDto.setShowReminder(showReminderStatus);
		
		int compId = Integer.parseInt((String) request.getSession().getAttribute("CID"));
		boolean saved = configInfo.saveReminder(configDto, compId);
		if (saved) {
			System.out.println("Reminder Details Updated Successfully...");
		} else {
			System.out.println("Error in saveDashboardSetting!!!!!");
		}
	}

	/*
	 * public void saveCustomerInvoiceSetting(ConfigurationDto configDto,
	 * HttpServletRequest request, String companyID, String custTaxable, String
	 * isSalesOrder, String addressSettings, String saleShowCountry, String
	 * ratePriceChangable, String saleShowTelephone, String isSalePrefix, String
	 * extraChargeApplicable, String isRefundAllowed)
	 */
	public String saveCustomerInvoiceSetting(ConfigurationDto configDto, HttpServletRequest request, String companyID,
			String custTaxable, String isSalesOrder, String addressSettings, String saleShowCountry,
			String ratePriceChangable, String saleShowTelephone, String isSalePrefix, String extraChargeApplicable,
			String isRefundAllowed, int sortBy, int selectedInvoiceStyleId, int selectedTermId, int selectedSalesRepId,
			int selectedPaymentId, int selectedBankAccountId) {
		configDto.setDefaultPaymentMethodId(selectedBankAccountId);
		configDto.setSelectedTermId(selectedTermId);
		configDto.setSelectedSalesRepId(selectedSalesRepId);
		configDto.setSelectedPaymentId(selectedPaymentId);
		configDto.setCustTaxable(custTaxable);
		configDto.setIsSalesOrder(isSalesOrder);
		configDto.setAddressSettings(addressSettings);

		configDto.setSaleShowCountry(saleShowCountry);
		configDto.setRatePriceChangable(ratePriceChangable);
		configDto.setSaleShowTelephone(saleShowTelephone);
		configDto.setIsSalePrefix(isSalePrefix);
		configDto.setExtraChargeApplicable(extraChargeApplicable);

		configDto.setIsRefundAllowed(isRefundAllowed);
		configDto.setSortBy(sortBy);
		configDto.setInvStyleID(selectedInvoiceStyleId);

		/*
		 * File fileUpload; String fileUploadContentType; String fileUploadFileName;
		 */
		// HttpServletRequest servletRequest;

		// File path = (File) configDto.getInvoiceLocation();
		// String locationPath = path.getAbsolutePath();
		/*
		 * File logoPath = configDto.getSaveImage(); String logo =
		 * logoPath.getAbsolutePath();
		 */
		// String filePath =
		// request.getSession().getServletContext().getRealPath("logoPath");
		// System.out.println("ImagePath:"+path);
		// System.out.println("Image path:" + logo);

		// System.out.println("Invoice Location:" + locationPath);

		
		int compId = Integer.parseInt((String) request.getSession().getAttribute("CID"));
		boolean saved = configInfo.saveCustomerInvoice(configDto, compId);
		if (saved) {
			System.out.println("CustomerInvoice Details Updated Successfully...");
			return "200";
		} else {
			System.out.println("Error in saveCustomerInvoiceSetting!!!!!");
			return "404";
		}
	}

	public void addNewDescription(ConfigurationDto configDto, String companyID, String description) {
		configDto.setDescription(description);
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.saveDescription(configDto, compId);
		if (saved) {
			System.out.println("Description Added Successfully...");
		} else {
			System.out.println("Error in addNewDescription!!!!!");
		}
	}

	public void deleteLocation(String companyID, int descriptionID) {
		// configDto.setDescription(descriptionID);
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.deleteLocation(descriptionID, compId);
		if (saved) {
			System.out.println("Description Deleted Successfully...");
		} else {
			System.out.println("Error in deleteDescription!!!!!");
		}
	}

	public void updateDescription(ConfigurationDto configDto, String companyID, String description, String locationID) {
		configDto.setDescription(description);
		int compId = Integer.parseInt(companyID);
		int locationId = Integer.parseInt(locationID);
		
		boolean saved = configInfo.updateDescription(configDto, compId, locationId);
		if (saved) {
			System.out.println("Description updated Successfully...");
		} else {
			System.out.println("Error in updateDescription!!!!!");
		}
	}

	public void addNewMessage(ConfigurationDto configDto, String companyID, String description) {
		configDto.setDescription(description);
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.saveMessage(configDto, compId);
		if (saved) {
			System.out.println("Message Added Successfully...");
		} else {
			System.out.println("Error in addNewMessage!!!!!");
		}
	}

	public void addNewSalesRep(ConfigurationDto configDto, String companyID, String description) {
		configDto.setDescription(description);
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.saveSalesRep(configDto, compId);
		if (saved) {
			System.out.println("SalesRepresentative Added Successfully...");
		} else {
			System.out.println("Error in addNewSalesRep!!!!!");
		}
	}

	public void addNewTerm(ConfigurationDto configDto, String companyID, String term, int days) {
		configDto.setDescription(term);
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.saveNewTerm(configDto, compId, days);
		if (saved) {
			System.out.println("Term Added Successfully...");
		} else {
			System.out.println("Error in addNewTerm!!!!!");
		}
	}

	public void addNewSalesTax(ConfigurationDto configDto, String companyID, String term, float tax) {
		configDto.setDescription(term);
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.saveNewSalesTax(configDto, compId, tax);
		if (saved) {
			System.out.println("SalesTax Added Successfully...");
		} else {
			System.out.println("Error in addNewSalesTax!!!!!");
		}
	}

	public void addNewCreditTerms(ConfigurationDto configDto, String companyID, String term, int days,
			String isDefault) {
		configDto.setDescription(term);
		configDto.setIsDefaultCreditTerm(isDefault);
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.saveNewCreditTerms(configDto, compId, days);
		if (saved) {
			System.out.println("CreditTerm Added Successfully...");
		} else {
			System.out.println("Error in addNewCreditTerms!!!!!");
		}
	}

	public void updateMessage(ConfigurationDto configDto, String companyID, String message, String messageId) {
		configDto.setDescription(message);
		int compId = Integer.parseInt(companyID);
		int messageID = Integer.parseInt(messageId);
		
		boolean saved = configInfo.updateMessage(configDto, compId, messageID);
		if (saved) {
			System.out.println("Message updated Successfully...");
		} else {
			System.out.println("Error in updateMessage!!!!!");
		}
	}

	public void updateSalesRep(ConfigurationDto configDto, String companyID, String salesRep, String salesRepId) {
		configDto.setDescription(salesRep);
		int compId = Integer.parseInt(companyID);
		int salesRepID = Integer.parseInt(salesRepId);
		
		boolean saved = configInfo.updateSalesRep(configDto, compId, salesRepID);
		if (saved) {
			System.out.println("Sales Representative updated Successfully...");
		} else {
			System.out.println("Error in updateSalesRep!!!!!");
		}
	}

	public void updateTerm(ConfigurationDto configDto, String companyID, String term, String termId, int days) {
		configDto.setDescription(term);
		int compId = Integer.parseInt(companyID);
		int termID = Integer.parseInt(termId);
		
		boolean saved = configInfo.updateTerm(configDto, compId, termID, days);
		if (saved) {
			System.out.println("Description updated Successfully...");
		} else {
			System.out.println("Error in updateTerm!!!!!");
		}
	}

	public void updateSalesTax(ConfigurationDto configDto, String companyID, String salesTax, String salesTaxId,
			float tax) {
		configDto.setDescription(salesTax);
		int compId = Integer.parseInt(companyID);
		int salesTaxID = Integer.parseInt(salesTaxId);
		
		boolean saved = configInfo.updateSalesTax(configDto, compId, salesTaxID, tax);
		if (saved) {
			System.out.println("SalesTax updated Successfully...");
		} else {
			System.out.println("Error in updateSalesTax!!!!!");
		}
	}

	public void updateCreditTerm(ConfigurationDto configDto, String companyID, String creditTerm, String creditTermId,
			String isDefault, String Days) {
		configDto.setDescription(creditTerm);
		configDto.setIsDefaultCreditTerm(isDefault);
		int compId = Integer.parseInt(companyID);
		int creditTermID = Integer.parseInt(creditTermId);
		int days = Integer.parseInt(Days);
		
		boolean saved = configInfo.updateCreditTerm(configDto, compId, creditTermID, days);
		if (saved) {
			System.out.println("CreditTerm updated Successfully...");
		} else {
			System.out.println("Error in updateCreditTerm!!!!!");
		}
	}

	public void deleteMessage(String companyID, int messageID) {
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.deleteMessage(compId, messageID);
		if (saved) {
			System.out.println("Message Deleted Successfully...");
		} else {
			System.out.println("Error in deleteMessage!!!!!");
		}
	}

	public void deleteSalesRep(ConfigurationDto configDto, String companyID, int salesRepId) {
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.deleteSalesRep(compId, salesRepId);
		if (saved) {
			System.out.println("Sales Representative Deleted Successfully...");
		} else {
			System.out.println("Error in deleteSalesRep!!!!!");
		}
	}

	public void deleteTerm(String companyID, int termId) {
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.deleteTerm(compId, termId);
		if (saved) {
			System.out.println("Term Deleted Successfully...");
		} else {
			System.out.println("Error in deleteTerm!!!!!");
		}
	}

	public void deleteSalesTax(String companyID, int salesTaxId) {
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.deleteSalesTax(compId, salesTaxId);
		if (saved) {
			System.out.println("SalesTax Deleted Successfully...");
		} else {
			System.out.println("Error in deleteSalesTax!!!!!");
		}
	}

	public void deleteCreditTerm(String companyID, int creditTermId) {
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.deleteCreditTerm(compId, creditTermId);
		if (saved) {
			System.out.println("Term Deleted Successfully...");
		} else {
			System.out.println("Error in deleteCreditTerm!!!!!");
		}
	}

	public void insertRefundReason(String companyID, String refundReason) {
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.insertRefundReason(compId, refundReason);
		if (saved) {
			System.out.println("Refund Reason Added Successfully...");
		} else {
			System.out.println("Error in insertRefundReason!!!!!");
		}
	}

	public void updateRefundReason(String companyID, int refundReasonId, String newRefundReason) {
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.updateRefundReason(compId, refundReasonId, newRefundReason);
		if (saved) {
			System.out.println("Refund Reason Updated Successfully...");
		} else {
			System.out.println("Error in updateRefundReason!!!!!");
		}
	}

	public void deleteRefundReason(String companyID, int refundReasonId) {
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.deleteRefundReason(compId, refundReasonId);
		if (saved) {
			System.out.println("Refund Reason Deleted Successfully...");
		} else {
			System.out.println("Error in deleteRefundReason!!!!!");
		}
	}

	public void defaultRefundReason(String companyID, int refundReasonId) {
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.defaultRefundReason(compId, refundReasonId);
		if (saved) {
			System.out.println("Refund Reason made Default Successfully...");
		} else {
			System.out.println("Error in defaultRefundReason!!!!!");
		}
	}

	public void addJobCategory(ConfigurationDto configDto, String companyID, String jobCategory,
			String recurringServiceBill) {
		configDto.setRecurringServiceBill(recurringServiceBill);
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.addJobCategory(configDto, compId, jobCategory);
		if (saved) {
			System.out.println("JobCategory Added Successfully...");
		} else {
			System.out.println("Error in addJobCategory!!!!!");
		}
	}

	public void updateJobCategory(ConfigurationDto configDto, String companyID, int jobCategoryId,
			String newJobCategoryName, String recurringServiceBill) {
		configDto.setRecurringServiceBill(recurringServiceBill);
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.updateJobCategory(configDto, compId, jobCategoryId, newJobCategoryName);
		if (saved) {
			System.out.println("JobCategory Updated Successfully...");
		} else {
			System.out.println("Error in updateJobCategory!!!!!");
		}
	}

	public void deleteJobCategory(String companyID, int jCategoryId) {
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.deleteJobCategory(jCategoryId, compId);
		if (saved) {
			System.out.println("JobCategory Deleted Successfully...");
		} else {
			System.out.println("Error in deleteJobCategory!!!!!");
		}
	}

	public void editServiceBillInfo(ConfigurationDto configDto, String companyID, String billName,
			String recurringServiceBill) {
		configDto.setRecurringServiceBill(recurringServiceBill);
		int compId = Integer.parseInt(companyID);
		
		boolean saved = configInfo.editServiceBillInfo(configDto, billName, compId);
		if (saved) {
			System.out.println("JobCategory Deleted Successfully...");
		} else {
			System.out.println("Error in editServiceBillInfo!!!!!");
		}
	}

	public void saveVendorPurchaseValues(ConfigurationDto configDto, String companyID, int sortBy, int selectedStateId1,
			int selectedCountryId1, String vendorProvience, int startPONum, int vendorDefaultFootnoteID,
			String showTelephone, String isPrefix, String showCountry, int shipCarrierId, int selectedTermId,
			int selectedSalesRepId, int selectedPaymentId, int selectedActiveEmployeeId, int selectedCategoryId) {
		int compId = Integer.parseInt(companyID);
		

		configDto.setSortBy(sortBy);
		configDto.setSelectedStateId1(selectedStateId1);
		configDto.setSelectedCountryId1(selectedCountryId1);
		configDto.setVendorProvience(vendorProvience);
		configDto.setStartPONum(startPONum);
		configDto.setVendorDefaultFootnoteID(vendorDefaultFootnoteID);
		configDto.setPoShowTelephone(showTelephone);
		configDto.setIsPurchasePrefix(isPrefix);
		configDto.setPoShowCountry(showCountry);
		configDto.setShipCarrierId(shipCarrierId);
		configDto.setSelectedTermId(selectedTermId);
		configDto.setSelectedSalesRepId(selectedSalesRepId);
		configDto.setSelectedPaymentId(selectedPaymentId);
		configDto.setSelectedActiveEmployeeId(selectedActiveEmployeeId);
		configDto.setSelectedCategoryId(selectedCategoryId);

		boolean saved = configInfo.saveVendorPurchaseValuesInConfigInfo(configDto, compId);
		if (saved) {
			System.out.println("VendorPurchaseValues Saved Successfully...");
		} else {
			System.out.println("Error in saveVendorPurchaseValues!!!!!");
		}
	}
}
