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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bzpayroll.common.db.SQLExecutor;
import com.bzpayroll.common.log.Loger;
import com.bzpayroll.common.utility.CountryState;
import com.bzpayroll.common.utility.DateInfo;
import com.bzpayroll.common.utility.LabelValueBean;
import com.bzpayroll.dashboard.accounting.dao.AccountingDAO;
import com.bzpayroll.dashboard.employee.dao.Title;
import com.bzpayroll.dashboard.purchase.dao.CreditCard;
import com.bzpayroll.dashboard.purchase.dao.PayMethod;
import com.bzpayroll.dashboard.purchase.dao.PurchaseInfo;
import com.bzpayroll.dashboard.purchase.dao.PurchaseOrderInfoDao;
import com.bzpayroll.dashboard.purchase.dao.Rep;
import com.bzpayroll.dashboard.purchase.dao.Shipping;
import com.bzpayroll.dashboard.purchase.dao.Term;
import com.bzpayroll.dashboard.purchase.dao.VendorCategory;
import com.bzpayroll.dashboard.purchase.forms.PurchaseOrderDto;
import com.bzpayroll.dashboard.sales.forms.EstimationDto;
import com.bzpayroll.dashboard.sales.forms.EstimationForm;
import com.bzpayroll.dashboard.sales.forms.InvoiceDto;
import com.bzpayroll.dashboard.sales.forms.ItemDto;
import com.bzpayroll.dashboard.sales.forms.UpdateInvoiceDto;
import com.bzpayroll.sales.forms.CustomerDto;

@Service
public class SalesDetailsDao {

	@Autowired
	private SalesInfo salesInfo;
	
	@Autowired
	private ItemInfoDao itemInfoDao;


	@Autowired
	private PurchaseOrderInfoDao purchase;
	
	@Autowired
	private EstimationInfoDao estimation;
	
	@Autowired
	private AccountingDAO acd;

	@Autowired
	private PayMethod pmethod;
	@Autowired
	private Shipping ship;
	@Autowired
	private CreditCard cc;
	@Autowired
	private VendorCategory cv;

	@Autowired
	private CustomerInfoDao customer;

	@Autowired
	private SalesInfo sales;

	@Autowired
	private InvoiceInfoDao invoiceInfoDao;
	
	@Autowired
	private EstimationInfo invoiceInfo;

	@Autowired
	private SQLExecutor db;


	public void getdataManager(HttpServletRequest request) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		ArrayList customerTitle = new ArrayList(); // to get customer title
		customerTitle = sales.getCustomerTitle(compId);
		request.setAttribute("customerTitle", customerTitle);

		ArrayList SalesRep = new ArrayList(); // to get sales rep
		SalesRep = sales.getSalesRep(compId);
		request.setAttribute("SalesRep", SalesRep);

		ArrayList salesTerms = new ArrayList(); // to get customer title
		salesTerms = sales.getTerms(compId);
		request.setAttribute("salesTerms", salesTerms);

		ArrayList SalesCatType = new ArrayList(); // to get customer title
		SalesCatType = sales.getCatType(compId);
		request.setAttribute("SalesCatType", SalesCatType);

		ArrayList SalesLocation = new ArrayList(); // to get customer title
		SalesLocation = sales.getLocation(compId);
		request.setAttribute("SalesLocation", SalesLocation);

		ArrayList SalesPaymentMethod = new ArrayList(); // to get customer title
		SalesPaymentMethod = sales.getPaymentType(compId);
		request.setAttribute("SalesPaymentMethod", SalesPaymentMethod);

		ArrayList SalesMessage = new ArrayList(); // to get customer title
		SalesMessage = sales.getMessage(compId);
		request.setAttribute("SalesMessage", SalesMessage);

		ArrayList CreditCardType = new ArrayList(); // to get customer title
		CreditCardType = sales.getCreditCard(compId);
		request.setAttribute("CreditCardType", CreditCardType);

		ArrayList SalesTax = new ArrayList(); // to get customer title
		SalesTax = sales.getTax(compId);
		request.setAttribute("SalesTax", SalesTax);

		/* Via Information */
		ArrayList via = new ArrayList();
		via = sales.getVia(compId);
		request.setAttribute("Via", via);

	}

	public void AddCustomer(HttpServletRequest request, CustomerDto cfrm) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		String istaxable = request.getParameter("isTaxable");
		String isAlsoClient = request.getParameter("isAlsoClient");
		String UseIndividualFinanceCharges = request.getParameter("UseIndividualFinanceCharges");
		String AssessFinanceChk = request.getParameter("AssessFinanceChk");
		String FChargeInvoiceChk = request.getParameter("FChargeInvoiceChk");
		Loger.log("istaxable:" + istaxable);
		Loger.log("isAlsoClient:" + isAlsoClient);
		int istax = 0;
		int isclient = 2; // 2 for vendor in cvtype table
		int indCharge = 0;
		int aFCharge = 0;
		int fICharge = 0;

		if ("on".equalsIgnoreCase(istaxable))
			istax = 1;

		if ("on".equalsIgnoreCase(isAlsoClient))
			isclient = 1;

		if ("on".equalsIgnoreCase(UseIndividualFinanceCharges))
			indCharge = 1;

		if ("on".equalsIgnoreCase(AssessFinanceChk))
			aFCharge = 1;

		if ("on".equalsIgnoreCase(FChargeInvoiceChk))
			fICharge = 1;

		// ..............................................generating new cvId
		PurchaseInfo pinfo = new PurchaseInfo();
		int cvID = pinfo.getLastClientVendorID() + 1;
		// ............................................Id generation finished

		try {
			boolean addCust = customer.insertCustomer(cvID + "", cfrm, compId, istax, isclient, indCharge, aFCharge,
					fICharge, "N");
			if (addCust) {
				request.setAttribute("SaveStatus", new ActionMessage("Customer Information is Successfully Added !."));
			}
		} catch (Exception e) {
			// TODO: handle exception
			request.setAttribute("SaveStatus", new ActionMessage("Customer Information is Not Insert !."));
		}
	}

	public void getAllList(HttpServletRequest request) {
		HttpSession sess = request.getSession();
		String cid = (String) sess.getAttribute("CID");
		// Title List
		Title t = new Title();
		request.setAttribute("titleList", t.getTitleList(cid));

		// country List
		CountryState cs = new CountryState();
		request.setAttribute("cList", cs.getCountry());

		// Term List
		Term tr = new Term();
		request.setAttribute("TermList", tr.getTermList(cid));

		// Rep List
		Rep rap = new Rep();
		request.setAttribute("RepList", rap.getRepList(cid));

		// PayMethod List
		request.setAttribute("PaymentList", pmethod.getPaymentTypeList(cid));

		// ShipCarrier List
		request.setAttribute("ShipCarrierList", ship.getShipCarrierList(cid));

		// CreditCard List
		request.setAttribute("CreditCardList", cc.getCCTypeList(cid));

		// VendorCategoryList List
		request.setAttribute("VendorCategoryList", cv.getCVCategoryList(cid));

		/* Item List */

		String compId = (String) request.getSession().getAttribute("CID");
		ArrayList itemList = new ArrayList();
		itemList = invoiceInfo.getItemList(compId);
		request.setAttribute("ItemList", itemList);

 		customer.getServices(request, cid);
	}

	public void addSupplierDetails(HttpServletRequest request) {
		HttpSession sess = request.getSession();
		String cid = (String) sess.getAttribute("CID");
		// Title List
		Title t = new Title();
		request.setAttribute("titleList", t.getTitleList(cid));

		// country List
		CountryState cs = new CountryState();
		request.setAttribute("cList", cs.getCountry());

		// state List
		CountryState cs1 = new CountryState();
		request.setAttribute("sList", cs1.getStates(cid));

		// Term List
		Term tr = new Term();
		request.setAttribute("TermList", tr.getTermList(cid));

		// Rep List
		Rep rap = new Rep();
		request.setAttribute("RepList", rap.getRepList(cid));

		// PayMethod List
		PayMethod pmethod = new PayMethod();
		request.setAttribute("PaymentList", pmethod.getPaymentTypeList(cid));

		// ShipCarrier List
		Shipping ship = new Shipping();
		request.setAttribute("ShipCarrierList", ship.getShipCarrierList(cid));

		// CreditCard List
		CreditCard cc = new CreditCard();
		request.setAttribute("CreditCardList", cc.getCCTypeList(cid));

		// CreditTerm List
		request.setAttribute("CreditTermList", cc.getCreditTermList(cid));

 		ArrayList accountList = new ArrayList();
		accountList = itemInfoDao.fillAccountList(cid);
		sess.setAttribute("AccountList", accountList);

	}

	public ArrayList addServices(String companyID) {
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
			String sqlString = "SELECT ServiceID,ServiceName,InvoiceStyleID,InventoryID FROM bca_servicetype WHERE CompanyID= ? ORDER BY ServiceName";
			pstmt = con.prepareStatement(sqlString);
			pstmt.setString(1, companyID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				arr.add(new LabelValueBean(rs.getString("ServiceName"), rs.getString("ServiceID")));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException ee) {
			Loger.log(2, "Error in  Class SalesDetail and  method -addServices " + " " + ee.toString());
		} finally {
			db.close(con);

		}

		return arr;
	}

	public void getdataManagerSave(HttpServletRequest request) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		SalesInfo sales = new SalesInfo();
		String sTitleval = request.getParameter("sTitleval");
		String sOldval = request.getParameter("sOldval");
		String sNewval = request.getParameter("sNewval");
		String sNewvalID = request.getParameter("sNewvalID");
		String taxRateVal = "";
		if (request.getParameter("taxRateVal") != null) {
			taxRateVal = request.getParameter("taxRateVal");
		}
		sales.insertSalesData(sNewvalID, sTitleval, sOldval, sNewval, taxRateVal, compId);
	}

	public void getdataManagerUpdate(HttpServletRequest request) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		SalesInfo sales = new SalesInfo();
		String sTitleval = request.getParameter("sTitleval");
		String sOldval = request.getParameter("sOldval");
		String sNewval = request.getParameter("sNewval");
		String sNewvalID = request.getParameter("sNewvalID");
		String taxRateVal = "";
		if (request.getParameter("taxRateVal") != null) {
			taxRateVal = request.getParameter("taxRateVal");
		}
		sales.updateSalesData(sNewvalID, sTitleval, sOldval, sNewval, taxRateVal, compId);
	}

	public void uploadItemFile(HttpServletRequest request, MultipartFile attachFile) {
 		boolean b = itemInfoDao.saveUploadFile(attachFile, request);
		if (b == true) {
			request.setAttribute("ItemUploaded", "successfully");
		}
	}

	public void exportFile(HttpServletRequest request, ItemDto im, String type) {
 		if (type != null && (type.equals("xls") || type.equals("csv"))) {
			boolean b = itemInfoDao.exportItem(request, type);
			if (b == true) {
				if (type.equals("xls")) {
					request.setAttribute("success", "BzComposer.exportitem.itemlistinxlsdownloaded");
					/*
					 * request.setAttribute("success",
					 * "ItemList.xls successfully downloaded at /Downloads");
					 */
				} else {
					request.setAttribute("success", "BzComposer.exportitem.itemlistincsvdownloaded");
					/*
					 * request.setAttribute("success",
					 * "ItemList.csv successfully downloaded at /Downloads");
					 */
				}
			}
		}
	}

	public void getdataManagerDelete(HttpServletRequest request) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		SalesInfo sales = new SalesInfo();
		String sTitleval = request.getParameter("sTitleval");
		String sNewvalID = request.getParameter("sNewvalID");
		Loger.log("IDSS" + sNewvalID);
		sales.DeleteSalesData(sNewvalID, sTitleval, compId);

	}

	public String getCustomerList(HttpServletRequest request) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");

		ArrayList<CustomerDto> customersList = customer.customerDetails(compId);
		request.setAttribute("CustomerDetails", customersList);
		String firstCvID = null;
		if (!customersList.isEmpty()) {
			firstCvID = customersList.get(0).getClientVendorID();
		}
		return firstCvID;
	}

	public ArrayList<CustomerDto> getCustomerSortByFirstName(HttpServletRequest request, CustomerDto frm) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");

		ArrayList<CustomerDto> CustomerDetails = customer.customerDetailsSortByFirstName(compId);
		request.setAttribute("CustomerDetails", CustomerDetails);
		return CustomerDetails;
	}

	public ArrayList<CustomerDto> getCustomerSortByLastName(HttpServletRequest request, CustomerDto frm) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");

		ArrayList<CustomerDto> CustomerDetails = customer.customerDetailsSortByLastName(compId);
		request.setAttribute("CustomerDetails", CustomerDetails);
		return CustomerDetails;
	}

	public void getLabel(HttpServletRequest request, CustomerDto cform) {

		int labelId = Integer.parseInt(request.getParameter("lblId"));
		customer.getLabel(labelId, cform);
	}

	public void getLabelType(HttpServletRequest request) {

		ArrayList labelType = new ArrayList();
		labelType = customer.labelTypeDetails();
		request.setAttribute("LabelTypeList", labelType);
	}

	public void addNewLabel(CustomerDto cform) {
		cform.setLabelName("");
		cform.setTopMargin("0.0");
		cform.setLeftMargin("0.0");
		cform.setHorizon("0.0");
		cform.setVertical("0.0");
		cform.setLabelHeight("0.0");
		cform.setLabelWidth("0.0");
	}

	public boolean saveLabel(HttpServletRequest request, CustomerDto cfrm) {
		boolean result = false;

		int labelID = Integer.parseInt(request.getParameter("LabelID"));
		if (labelID == 0) {
			customer.saveLabel(cfrm);
			result = true;
		} else {
			customer.updateLabel(labelID, cfrm);
			result = false;
		}
		return result;
	}

	public void deleteLabel(HttpServletRequest request, CustomerDto cfrm) {

		int labelID = Integer.parseInt(request.getParameter("LabelID"));
		Loger.log("LABEL   " + labelID);
		customer.deleteLabel(labelID, cfrm);
	}

	public void searchCustomer(String cvId, HttpServletRequest request, CustomerDto form) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");

		ArrayList CustomerDetails = new ArrayList();
		sess.setAttribute("CustID", cvId);
		CustomerDetails = customer.SearchCustomer(compId, cvId, form);
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		invoice.getServices(request, compId, cvId);
		request.setAttribute("CustomerDetails", CustomerDetails);
	}

	// search selected customer base on cvid
	public void searchSelectedCustomer(String cvId, HttpServletRequest request, CustomerDto form) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		// Loger.log("The Client vendor is from sales detail is " + cvId);
		invoice.SearchselectedCustomer(compId, cvId, request);
		invoice.getServices(request, compId, cvId);
		// request.setAttribute("CustomerDetails", CustomerDetails);
	}

	public void UpdateCustomer(HttpServletRequest request, CustomerDto cfrm) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");


		String cvId = (String) sess.getAttribute("editedCVID");

		customer.UpdateCustomer(compId, cvId);

		String istaxable = request.getParameter("isTaxable");
		String isAlsoClient = request.getParameter("isAlsoClient");
		Loger.log("ISC_________________________>>>>>>>>>>>>>>>>>>>>>>>>>>>>>              " + isAlsoClient);
		String UseIndividualFinanceCharges = request.getParameter("UseIndividualFinanceCharges");
		String AssessFinanceChk = request.getParameter("AssessFinanceChk");

		int istax = 0;
		int isclient = 2; // 2 for vendor in cvtype table
		int indCharge = 0;
		int aFCharge = 0;

		if (istaxable == null)
			istax = 0;
		else if ("on".equalsIgnoreCase(istaxable))
			istax = 1;
		else
			istax = 0;

		if (isAlsoClient == null)
			isclient = 2;
		else if ("on".equalsIgnoreCase(isAlsoClient))
			isclient = 1;
		else
			isclient = 2;

		if (UseIndividualFinanceCharges == null)
			indCharge = 0;
		else if ("on".equalsIgnoreCase(UseIndividualFinanceCharges))
			indCharge = 1;
		else
			indCharge = 0;

		if (AssessFinanceChk == null)
			aFCharge = 0;
		else if ("on".equalsIgnoreCase(AssessFinanceChk))
			aFCharge = 1;
		else
			aFCharge = 0;

		cfrm.setAnnualIntrestRate(request.getParameter("AnualRate"));
		cfrm.setMinFCharges(request.getParameter("MinFinance"));
		cfrm.setGracePrd(request.getParameter("GracePeriod"));
		boolean updateCust = customer.updateInsertCustomer(cvId, cfrm, compId, istax, isclient, indCharge, aFCharge,
				"U");
		if (updateCust) {
			request.setAttribute("SaveStatus", "Customer updated successfully!");
		}
		if (cfrm.getDispay_info().equals("ShowAll")) {
			request.setAttribute("RadioVal", "1");

		} else
			request.setAttribute("RadioVal", "2");

		if (request.getParameter("Flag").equals("1")) {
			request.setAttribute("ClientID", cvId);
			/*
			 * request.setAttribute("FromDate",cfrm.getPeriodFrom());
			 * request.setAttribute("ToDate",cfrm.getPeriodTo());
			 */
		}
	}

	public void UpdateCustInfo(HttpServletRequest request, UpdateInvoiceDto uform) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");

		InvoiceInfoDao customer1 = new InvoiceInfoDao();
		// String cvId=(String)sess.getAttribute("CustID");
		String cvId = uform.getCustId();
		customer.UpdateCustomer(compId, cvId);

		String istaxable = uform.getTaxAble();
		Loger.log("The value of taxable is " + uform.getTaxAble());
		String isAlsoClient = uform.getIsclient();
		Loger.log("The Value of isAlsoClient is " + uform.getIsclient());
		String UseIndividualFinanceCharges = request.getParameter("UseIndividualFinanceCharges");

		String AssessFinanceChk = request.getParameter("AssessFinance");
		String FChargeInvoiceChk = request.getParameter("MarkFinanace");
		Loger.log("istaxable:" + istaxable);
		Loger.log("isAlsoClient:" + isAlsoClient);
		int istax = 0;
		int isclient = 2; // 2 for vendor in cvtype table
		int indCharge = 0;
		int aFCharge = 0;
		int fICharge = 0;

		Loger.log("ACCESS_______________________________" + AssessFinanceChk);
		Loger.log("MARK FINANCE_________________________________" + FChargeInvoiceChk);
		if ("on".equalsIgnoreCase(istaxable))
			istax = 1;

		if ("on".equalsIgnoreCase(isAlsoClient))
			isclient = 1;

		if (UseIndividualFinanceCharges == null) {
			indCharge = 0;
			Loger.log("HHH");
		} else if (("on").equalsIgnoreCase(UseIndividualFinanceCharges)) {
			indCharge = 1;
			Loger.log("TTTTTTTTTTTTTTTTTTTTT");
		}

		if (AssessFinanceChk == null) {
			aFCharge = 0;
		} else if (("off").equalsIgnoreCase(AssessFinanceChk)) {
			aFCharge = 1;
		}
		if (FChargeInvoiceChk == null) {
			fICharge = 0;
		} else if (("off").equalsIgnoreCase(FChargeInvoiceChk)) {
			fICharge = 1;
		}
		// UpdateInvoiceForm cfrm= new UpdateInvoiceForm();
		customer1.insertCustomer(cvId, uform, compId, istax, isclient, indCharge, aFCharge, fICharge, "U");
		updateInvoice(cvId, request);
	}

	public ArrayList<ItemDto> sortItemsList(HttpServletRequest request, ItemDto form, int sortById) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		
		ArrayList ItemDetails = new ArrayList();
		if (sortById == 1) {
			ItemDetails = itemInfoDao.sortItemList(compId, "inventoryName");
		} else if (sortById == 2) {
			ItemDetails = itemInfoDao.sortItemList(compId, "InventoryCode");
		}
		sess.setAttribute("ItemDetails", ItemDetails);
		Loger.log("list Size:" + ItemDetails.size());
		return ItemDetails;
	}

	public void getItemDetails(HttpServletRequest request) {
		String compId = (String) request.getSession().getAttribute("CID");
		String inventoryID = request.getParameter("InvId");
		
		request.setAttribute("ItemDetails", itemInfoDao.getItemDetails(compId, inventoryID));
	}

	public void getItemNameList(HttpServletRequest request, ItemDto form) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		
		ArrayList ItemNameList = itemInfoDao.getItemNameList(compId);
		sess.setAttribute("ItemNameList", ItemNameList);
		Loger.log("ItemsList Size:" + ItemNameList.size());
	}

	public void ItemsList(HttpServletRequest request, ItemDto form) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		ArrayList ItemDetails = itemInfoDao.getItemList(compId);
		sess.setAttribute("ItemDetails", ItemDetails);
		Loger.log("ItemsList Size:" + ItemDetails.size());
	}

	public void ItemsDicontinuedList(HttpServletRequest request, ItemDto itemForm) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		
		ArrayList ItemDetails = new ArrayList();
		String datesCombo = itemForm.getDatesCombo();
		String fromDate = itemForm.getFromDate();
		String toDate = itemForm.getToDate();
		String sortBy = itemForm.getSortBy();
		ItemDetails = itemInfoDao.getDicontinuedItemList(datesCombo, fromDate, toDate, sortBy, compId, request, itemForm);
		sess.setAttribute("ItemDetails", ItemDetails);
		Loger.log("list Size:" + ItemDetails.size());
	}

	public void ItemsReportList(HttpServletRequest request, ItemDto itemForm) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		
		ArrayList ItemDetails = new ArrayList();
		String datesCombo = itemForm.getDatesCombo();
		String fromDate = itemForm.getFromDate();
		String toDate = itemForm.getToDate();
		String sortBy = itemForm.getSortBy();
		ItemDetails = itemInfoDao.getReportItemList(datesCombo, fromDate, toDate, sortBy, compId, request, itemForm);
		sess.setAttribute("ItemDetails", ItemDetails);
		Loger.log("list Size:" + ItemDetails.size());
	}

	public ItemDto searchItem(HttpServletRequest request, ItemDto itemDto) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		
		String invId = request.getParameter("InvId");
		ArrayList<ItemDto> ItemDetails = itemInfoDao.SearchItem(compId, invId, itemDto, request);
		itemDto = ItemDetails.isEmpty() ? itemDto : ItemDetails.get(0);
		sess.setAttribute("ItemDetails1", ItemDetails);
		request.setAttribute("itemDto", itemDto);
		Loger.log("list Size:" + ItemDetails.size());
		return itemDto;
	}

	public void DeleteItem(HttpServletRequest request) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		
		String invId = request.getParameter("InvId");
		itemInfoDao.deleteItem(compId, invId);
	}

	public void getDamagedInvenotyList(HttpServletRequest request, ItemDto itemForm) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		
		ArrayList ItemDetails = new ArrayList();
		String datesCombo = itemForm.getDatesCombo();
		String fromDate = itemForm.getFromDate();
		String toDate = itemForm.getToDate();
		String sortBy = itemForm.getSortBy();
		ArrayList damagesItemList = new ArrayList();
		damagesItemList = itemInfoDao.getDamagedInvList(datesCombo, fromDate, toDate, sortBy, compId, request, itemForm);
		request.setAttribute("damagesItemList", damagesItemList);

	}

	public void getMissingInventoryList(HttpServletRequest request, ItemDto itemForm) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		
		ArrayList ItemDetails = new ArrayList();
		String datesCombo = itemForm.getDatesCombo();
		String fromDate = itemForm.getFromDate();
		String toDate = itemForm.getToDate();
		String sortBy = itemForm.getSortBy();
		ArrayList missingInventoryList = new ArrayList();
		missingInventoryList = itemInfoDao.getMissingInventoryList(datesCombo, fromDate, toDate, sortBy, compId, request,
				itemForm);
		request.setAttribute("missingInventoryList", missingInventoryList);

	}

	public void getReturnInventoryList(HttpServletRequest request, ItemDto itemForm) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		
		ArrayList ItemDetails = new ArrayList();

		String datesCombo = itemForm.getDatesCombo();
		String fromDate = itemForm.getFromDate();
		String toDate = itemForm.getToDate();
		String sortBy = itemForm.getSortBy();

		ArrayList returnInventoryList = new ArrayList();
		returnInventoryList = itemInfoDao.getReturnInventoryList(datesCombo, fromDate, toDate, sortBy, compId, request,
				itemForm);
		request.setAttribute("returnInventoryList", returnInventoryList);

	}

	public void getInventoryValuationSummary(HttpServletRequest request, ItemDto form1) {
		HttpSession ss = request.getSession();
		String compId = (String) ss.getAttribute("CID");
 
		String orderDate1 = form1.getOrderDate1();
		String orderDate2 = form1.getOrderDate2();

		String datesCombo = form1.getDatesCombo();
		String fromDate = form1.getFromDate();
		String toDate = form1.getToDate();
		String sortBy = form1.getSortBy();

		ArrayList invValSummaryList = new ArrayList();
		invValSummaryList = itemInfoDao.getInventoryValSummary(datesCombo, fromDate, toDate, sortBy, compId, request, form1);
		request.setAttribute("invValSummaryList", invValSummaryList);
	}

	public void getInventoryValuationDetail(HttpServletRequest request, ItemDto form1) {
		HttpSession ss = request.getSession();
		String compId = (String) ss.getAttribute("CID");
 
		String orderDate1 = form1.getOrderDate1();
		String orderDate2 = form1.getOrderDate2();

		String datesCombo = form1.getDatesCombo();
		String fromDate = form1.getFromDate();
		String toDate = form1.getToDate();
		String sortBy = form1.getSortBy();

		ArrayList getInvValDetail = new ArrayList<>();
		getInvValDetail = itemInfoDao.getInvValDetail(datesCombo, fromDate, toDate, sortBy, compId, request, form1);
		request.setAttribute("getInvValDetail", getInvValDetail);
	}

	public void getInventoryOrderReport(HttpServletRequest request, ItemDto form1) {
		HttpSession ss = request.getSession();
		String compId = (String) ss.getAttribute("CID");
 
		String sortByDay = form1.getSortByDay();
		String orderDate1 = form1.getOrderDate1();
		String orderDate2 = form1.getOrderDate2();

		String datesCombo = form1.getDatesCombo();
		String fromDate = form1.getFromDate();
		String toDate = form1.getToDate();
		String sortBy = form1.getSortBy();

		ArrayList invOrderReport = new ArrayList<>();
		invOrderReport = itemInfoDao.getInvOrderReport(datesCombo, fromDate, toDate, sortBy, compId, request, form1);
		request.setAttribute("invOrderReport", invOrderReport);
	}

	public void getInventoryStatistics(HttpServletRequest request, ItemDto form1) {
		HttpSession ss = request.getSession();
		String compId = (String) ss.getAttribute("CID");
 
		String sortByDay = form1.getSortByDay();
		String orderDate1 = form1.getOrderDate1();
		String orderDate2 = form1.getOrderDate2();

		String datesCombo = form1.getDatesCombo();
		String fromDate = form1.getFromDate();
		String toDate = form1.getToDate();
		String sortBy = form1.getSortBy();

		ArrayList invStatistics = new ArrayList<>();
		invStatistics = itemInfoDao.getInvStatisticReport(datesCombo, fromDate, toDate, sortBy, compId, request, form1);
		request.setAttribute("invStatistics", invStatistics);

	}

	public void getAccountPayableReport(HttpServletRequest request, CustomerDto cform) {
		HttpSession ss = request.getSession();
		String compId = (String) ss.getAttribute("CID");
		String sortByDay = cform.getSortBy();
		String orderDate1 = cform.getFromDate();
		String orderDate2 = cform.getToDate();
		String datesCombo = cform.getDatesCombo();
		String fromDate = cform.getFromDate();
		String toDate = cform.getToDate();
		String sortBy = cform.getSortBy();

		ArrayList<CustomerDto> cList = customer.getAccountPayableReport(compId, request, datesCombo, fromDate, toDate,
				sortBy, cform);
		request.setAttribute("acPayList", cList);
	}

	public void UpdateItem(HttpServletRequest request, ItemDto itemFrm) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		
		itemInfoDao.fillCombo(compId);
		String invId = request.getParameter("InvId");
		// Loger.log("The ItemTypeId ="+itemtypeid);

		Loger.log("The Item Code is" + itemFrm.getItemCode());
		Loger.log("The Itemsale price is" + itemFrm.getSalePrice());
		Loger.log("ISCATEGORY____________________________________" + itemFrm.getIscategory());

		boolean status = itemInfoDao.updateItem(compId, invId, itemFrm);
		if (status) {
			request.setAttribute("SaveStatus", "Item updated successfully");
		}
		Loger.log("ITEM Type-----+" + itemFrm.getItemType());
	}

	public void AddItem(HttpServletRequest request, ItemDto itemFrm) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		
		String itemType = request.getParameter("ItemType");
		// Loger.log("FILE PHOTO _______________________
		// "+itemFrm.getPhotoName().getFileName());
		if (itemType.equals("2")) {
			itemFrm.setItemCode(itemFrm.getItemCodeDis());
			itemFrm.setInvTitle(itemFrm.getInvTitleDis());
			itemFrm.setItemCodeDis("");
			itemFrm.setInvTitleDis("");
		} else if (itemType.equals("3")) {
			itemFrm.setItemCode(itemFrm.getItemCodeSub());
			itemFrm.setItemName(itemFrm.getItemNameSub());
			itemFrm.setItemCodeSub("");
			itemFrm.setItemNameSub("");
		} else if (itemType.equals("4")) {
			itemFrm.setItemCode(itemFrm.getItemCodeSer());
			itemFrm.setItemName(itemFrm.getItemNameSer());
			itemFrm.setTectcmd(itemFrm.getTectcmdSer());
			itemFrm.setInvTitle(itemFrm.getInvTitleSer());
			itemFrm.setTaxable(itemFrm.getTaxableSer());

			itemFrm.setItemCodeSer("");
			itemFrm.setItemNameSer("");
			itemFrm.setTectcmdSer(0);
			itemFrm.setInvTitleSer("");
			Loger.log("CAT_______________________________________" + request.getParameter("iscat"));
			itemFrm.setIscategory(request.getParameter("iscat"));
		}
		String str = "";
		if (itemFrm.getPhotoName() != null) {
			if (itemFrm.getPhotoName().getName() == null || itemFrm.getPhotoName().getName().equals("")) {
				str = "";
			} else {
				Loger.log("FILE______________________________DJDJD__");
				ItemInfo itmInfo = new ItemInfo();
				itmInfo.uploadImage(itemFrm, request);
				str = itemFrm.getPhotoName().getName();
				Loger.log("FILE UPLOADED******");
			}
		}
		try {
			boolean status = itemInfoDao.insertItem(compId, str, itemFrm); // Insert new Item
			itemFrm.setItemCode("");
			itemFrm.setTectcmd(0);
			itemFrm.setDiscountAmt("");
			if (status) {
				request.setAttribute("SaveStatus", "Item saved successfully");
			}
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		Loger.log("item added successfully");
	}

	public void AdjustInventory(HttpServletRequest request, ItemDto form) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		ArrayList Inventory = new ArrayList();
		Inventory = (ArrayList) sess.getAttribute("ItemDetails");
		int invSize = Inventory.size();

		String[][] oldInventory = new String[invSize][3];

		for (int i = 0; i < invSize; i++) {
			ItemDto itemForm = (ItemDto) Inventory.get(i);
			String invId = itemForm.getInventoryId();
			oldInventory[i][0] = invId;
			if (request.getParameter("newQty" + invId) != null) {
				if (request.getParameter("newQty" + invId).trim().length() > 0)
					oldInventory[i][1] = request.getParameter("newQty" + invId);
				else
					oldInventory[i][1] = itemForm.getQty();
				if (request.getParameter("newValue" + invId).trim().length() > 0) {
					oldInventory[i][2] = request.getParameter("newValue" + invId);
				} else {
					oldInventory[i][2] = itemForm.getSalePrice();
				}
			}
		}
		
		itemInfoDao.adjustInventory(compId, oldInventory, invSize);
		ArrayList ItemDetails = new ArrayList();
		ItemDetails = itemInfoDao.getItemList(compId);
		sess.removeAttribute("ItemDetails");
		sess.setAttribute("ItemDetails", ItemDetails);
		Loger.log("list Size:" + ItemDetails.size());

	}

	public void UpdateInventoryQty(HttpServletRequest request) {
		
		boolean status = itemInfoDao.UpdateInventoryQty(request);
	}

	public void updateBillingAddress(InvoiceDto frm, String cId, String billAddressId) {
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		invoice.updateBillingAddress(frm, cId, billAddressId);
	}

	public void updateShippingAddress(InvoiceDto frm, String cId, String billAddressId) {
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		invoice.updateShippingAddress(frm, cId, billAddressId);
	}

	/* Method for getting Invoice information */
	public void getInvoiceInfo(HttpServletRequest request) throws SQLException {
		String compId = (String) request.getSession().getAttribute("CID");
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		
		ArrayList ClientDetails = new ArrayList();
		ClientDetails = invoice.customerDetails(compId, request);
		request.setAttribute("CDetails", ClientDetails);

		ArrayList shAddr = new ArrayList();
		String companyName = (String) request.getSession().getAttribute("user");
		// System.out.println("CompanyName:"+companyName);
		shAddr = invoice.shipAddress(companyName);
		request.setAttribute("ShAddr", shAddr);

		ArrayList billAddr = new ArrayList();
		billAddr = invoice.billAddress(Integer.parseInt(compId), companyName);
		request.getSession().setAttribute("BillAddr", billAddr);

		/* Invoice Style */
		ArrayList InvoiceStyle = new ArrayList();
		InvoiceStyle = invoice.getInvoiceStyle();
		request.setAttribute("InvoiceStyle", InvoiceStyle);

		/* Via Information */
		ArrayList via = new ArrayList();
		via = invoice.getVia(compId);
		request.setAttribute("Via", via);

		/* Rep Information */
		ArrayList rep = new ArrayList();
		rep = invoice.getRep(compId);
		request.getSession().setAttribute("Rep", rep);

		/* Term Information */
		ArrayList term = new ArrayList();
		term = invoice.getTerm(compId);
		request.setAttribute("Term", term);

		/* Term Information */
		ArrayList payMethod = new ArrayList();
		payMethod = invoice.getPayMethod(compId);
		request.setAttribute("PayMethod", payMethod);

		/* Messages */
		ArrayList message = new ArrayList();
		message = invoice.getMessage(compId);
		request.setAttribute("Message", message);

		/* Tax */
		ArrayList tax = new ArrayList();
		tax = invoice.getTaxes(compId);
		request.setAttribute("Tax", tax);

		/* Item List */
		ArrayList itemList = new ArrayList();
		itemList = invoice.getItemList(compId);
		request.setAttribute("ItemList", itemList);

		ArrayList itemDetails = new ArrayList();
		itemDetails = itemInfoDao.getItemList(compId);
		request.setAttribute("ItemDetails", itemList);
	}

	public void getSortedInvoiceInfo(HttpServletRequest request, String sort) throws SQLException {
		String compId = (String) request.getSession().getAttribute("CID");
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		ArrayList ClientDetails = new ArrayList();
		if (sort.equals("Name")) {
			ClientDetails = invoice.customerDetails(compId, request);
			System.out.println("Calling sortByName method and getting data:" + ClientDetails.toString());
		} else {
			ClientDetails = invoice.sortedcustomerDetails(compId, request, sort);
			System.out.println("Calling sortByLastName method and getting data:" + ClientDetails.toString());
		}
		request.setAttribute("CDetails", ClientDetails);

		ArrayList shAddr = new ArrayList();
		String companyName = (String) request.getSession().getAttribute("user");
		// System.out.println("CompanyName:"+companyName);
		shAddr = invoice.shipAddress(companyName);
		request.setAttribute("ShAddr", shAddr);

		ArrayList billAddr = new ArrayList();
		billAddr = invoice.billAddress(Integer.parseInt(compId), companyName);
		request.getSession().setAttribute("BillAddr", billAddr);

		/* Invoice Style */
		ArrayList InvoiceStyle = new ArrayList();
		InvoiceStyle = invoice.getInvoiceStyle();
		request.setAttribute("InvoiceStyle", InvoiceStyle);

		/* Via Information */
		ArrayList via = new ArrayList();
		via = invoice.getVia(compId);
		request.setAttribute("Via", via);

		/* Rep Information */
		ArrayList rep = new ArrayList();
		rep = invoice.getRep(compId);
		request.getSession().setAttribute("Rep", rep);

		/* Term Information */
		ArrayList term = new ArrayList();
		term = invoice.getTerm(compId);
		request.setAttribute("Term", term);

		/* Term Information */
		ArrayList payMethod = new ArrayList();
		payMethod = invoice.getPayMethod(compId);
		request.setAttribute("PayMethod", payMethod);

		/* Messages */
		ArrayList message = new ArrayList();
		message = invoice.getMessage(compId);
		request.setAttribute("Message", message);

		/* Tax */
		ArrayList tax = new ArrayList();
		tax = invoice.getTaxes(compId);
		request.setAttribute("Tax", tax);

		/* Item List */
		ArrayList itemList = new ArrayList();
		itemList = invoice.getItemList(compId);
		request.setAttribute("ItemList", itemList);
	}

	public void FillCombo(HttpServletRequest request, ItemDto itemForm) {
		// TODO Auto-generated method stub
		String compId = (String) request.getSession().getAttribute("CID");
		HttpSession sess = request.getSession();

		
		sess.setAttribute("fillList", itemInfoDao.fillCombo(compId));
		sess.setAttribute("itemCategory", itemInfoDao.fillItemCategory(compId));
		sess.setAttribute("itemSubCategory", itemInfoDao.fillItemSubCategory(compId));
		sess.setAttribute("AccountList", itemInfoDao.fillAccountList(compId));

		sess.setAttribute("weightList", itemInfoDao.fillWeight(compId));
		sess.setAttribute("storeList", itemInfoDao.filleStoreList(compId, itemForm));
		sess.setAttribute("eSalesChannelList", itemInfoDao.filleSalesChannel(itemForm));
		sess.setAttribute("measurementList", itemInfoDao.getMeasurementList(compId));
		sess.setAttribute("unitMeasurementList", itemInfoDao.getUnitMeasurementList(compId));

		ArrayList priceLevelList = itemInfoDao.setPriceLevel(compId, itemForm);
		sess.setAttribute("priceLevelList", priceLevelList);
		sess.setAttribute("priceLevelSize", priceLevelList.size());

		ArrayList eBayProductList = itemInfoDao.eBayProductList(compId, itemForm);
		sess.setAttribute("eBayProductList", eBayProductList);
		sess.setAttribute("eBayProductListSize", eBayProductList.size());

		sess.setAttribute("locationList", itemInfoDao.getExistingLocation(compId, request, itemForm));
		sess.setAttribute("itemStoreList", itemInfoDao.getStoreList(compId));
		sess.setAttribute("productList", itemInfoDao.getActiveProductList(compId));
		request.setAttribute("vendorList", itemInfoDao.getVendorDetails(compId, request));
	}

	public void getInitialize(String ordNo, HttpServletRequest request, InvoiceDto form) {
		String compId = (String) request.getSession().getAttribute("CID");

		long orderNo = Long.parseLong(ordNo);
		invoiceInfoDao.getRecord(request, form, compId, orderNo);

	}

	public void getSalesOrderInitialize(String salesOrderNo, HttpServletRequest request, InvoiceDto form) {
		String compId = (String) request.getSession().getAttribute("CID");

		long soNo = Long.parseLong(salesOrderNo); // Sales Order Num SO Num
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		invoice.getSalesOrderRecord(request, form, compId, soNo);

	}

	public void getInitializeEstimation(String estNo, HttpServletRequest request, EstimationForm form) {
		String compId = (String) request.getSession().getAttribute("CID");

		long estimationNo = Long.parseLong(estNo);
		estimation.getRecord(request, form, compId, estimationNo);

	}

	public void getInitializePurchase(String poNo, HttpServletRequest request, PurchaseOrderDto form) {
		String compId = (String) request.getSession().getAttribute("CID");

		long purchaseNo = Long.parseLong(poNo);
		purchase.getRecord(request, form, compId, purchaseNo);

	}

	public void newSalesOrder(HttpServletRequest request, InvoiceDto form) { // New Sales Order

		InvoiceInfoDao invoice = new InvoiceInfoDao();
		String compId = (String) request.getSession().getAttribute("CID");
		form.setOrderNo(invoice.getNewSalesOrderNo(compId));
		form.setPoNum(invoice.getNewSalesOrderNo(compId));// sales Order SoNum
		DateInfo date = new DateInfo();
		int month = date.getMonth();
		int day = date.getDay();

		String da = "", d = "", m = "";
		if (month >= 1 && month <= 9) {
			m = "0" + month;
		} else
			m = "" + month;
		if (day >= 1 && day <= 9) {
			d = "0" + day;
		} else
			d = "" + day;
		da = m + "-" + d + "-" + (date.getYear());

		form.setOrderDate(da);

		// form.setPoNum("0");
		form.setCustID("0");
		form.setInvoiceStyle(invoice.getDefaultInvoiceStyleNo(compId));
		form.setVia("0");
		form.setTerm("0");
		form.setRep("0");
		form.setPayMethod("0");
		form.setItemID("0");
		form.setMessage("0");
		form.setTaxID("0");
		form.setIsPending("false");
		form.setBalance(0.0);
		form.setWeight(0.0);
		form.setAdjustedtotal(0.0);
		form.setSubtotal(0.0);
		form.setShipping(0.0);
		form.setTotal(0.0);
		form.setItemShipped("false");
		form.setTaxable("false");
		form.setIsPending("false");
		form.setPaid("false");
		form.setBillTo("");
		form.setShipTo("");
		form.setMemo("");
		form.setShipDate(da);
		form.setTax(0.0);
		form.setItemID("0");
		request.setAttribute("IsDisplay", "false");
	}

	public void newInvoice(HttpServletRequest request, InvoiceDto form) {

		InvoiceInfoDao invoice = new InvoiceInfoDao();
		String compId = (String) request.getSession().getAttribute("CID");
		form.setOrderNo(invoice.getNewOrderNo(compId));
		form.setPoNum(invoice.getNewOrderNo(compId));
		DateInfo date = new DateInfo();
		int month = date.getMonth();
		int day = date.getDay();

		String da = "", d = "", m = "";
		if (month >= 1 && month <= 9) {
			m = "0" + month;
		} else
			m = "" + month;
		if (day >= 1 && day <= 9) {
			d = "0" + day;
		} else
			d = "" + day;
		da = m + "-" + d + "-" + (date.getYear());

		form.setOrderDate(da);

		// form.setPoNum("0");
		form.setCustID("0");
		form.setInvoiceStyle(invoice.getDefaultInvoiceStyleNo(compId));
		form.setVia("0");
		form.setTerm("0");
		form.setRep("0");
		form.setPayMethod("0");
		form.setItemID("0");
		form.setMessage("0");
		form.setTaxID("0");
		form.setIsPending("false");
		form.setBalance(0.0);
		form.setWeight(0.0);
		form.setAdjustedtotal(0.0);
		form.setSubtotal(0.0);
		form.setShipping(0.0);
		form.setTotal(0.0);
		form.setItemShipped("");
		form.setTaxable("");
		form.setIsPending("");
		form.setPaid("");
		form.setBillTo("");
		form.setShipTo("");
		form.setMemo("");
		form.setShipDate(da);
		form.setTax(0.0);
		form.setItemID("0");
		request.setAttribute("IsDisplay", "false");
	}

	public void dropShipInvoice(HttpServletRequest request, InvoiceDto form) {

		InvoiceInfoDao invoice = new InvoiceInfoDao();
		String compId = (String) request.getSession().getAttribute("CID");
		form.setOrderNo(invoice.getNewOrderNo(compId));
		DateInfo date = new DateInfo();
		int month = date.getMonth();
		int day = date.getDay();
		String d1 = "", d2 = "", d = "";

		if (month >= 1 && month <= 9)
			d = "0" + month;
		else
			d = "" + month;

		if (day >= 1 && day <= 9)
			d2 = "-0" + day;
		else
			d2 = "-" + day;

		d1 = d + d2 + "-" + (date.getYear());

		form.setPoNum((String) request.getParameter("DShipValue"));
		form.setOrderDate(d1);
		form.setCustID("0");
		form.setInvoiceStyle("0");
		form.setVia("0");
		form.setTerm("0");
		form.setRep("0");
		form.setPayMethod("0");
		form.setItemID("0");
		form.setMessage("0");
		form.setTaxID("0");
		form.setIsPending("false");
		form.setBalance(0.0);
		form.setWeight(0.0);
		form.setAdjustedtotal(0.0);
		form.setSubtotal(0.0);
		form.setShipping(0.0);
		form.setTotal(0.0);
		form.setItemShipped("false");
		form.setTaxable("false");
		form.setIsPending("false");
		form.setPaid("false");
		form.setBillTo("");
		form.setShipTo("");
		form.setShipDate("");
		form.setTax(0.0);
		form.setItemID("0");

	}

	public void saveInvoice(HttpServletRequest request, InvoiceDto form, String custID) {

		InvoiceInfoDao invoice = new InvoiceInfoDao();
		String compId = (String) request.getSession().getAttribute("CID");
		// custID = form.getCustID();
		System.out.println("CustomerId is:" + custID);
		boolean exist = invoice.invoiceExist(compId, form.getOrderNo());
		// int invoiceTypeId = 1; //INVOICE TYPE ID "INVOICE"

		if (exist == true) {
			try {
				int invoiceID = invoice.getInvoiceNo(compId, form.getOrderNo());
				invoice.Update(compId, form, invoiceID, custID);
				newInvoice(request, form);
				getInvoiceInfo(request); // changed from 1132 line to here on 29-08-2019.
				request.setAttribute("SaveStatus", "Invoice is successfully updated.");
				// ActionErrors e=new ActionErrors();
				// e.add("SaveStatus", new
				// ActionMessage("BzComposer.invoice.saveinvoicesuccessmessage"));
				// request.setAttribute("SaveStatus",
				// "BzComposer.invoice.saveinvoicesuccessmessage");
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("SaveStatus", "Invoice is not updated.");
				// ActionErrors e1=new ActionErrors();
				// e1.add("SaveStatus", new
				// ActionMessage("BzComposer.invoice.saveinvoiceerrormessage"));
				// request.setAttribute("SaveStatus",
				// "BzComposer.invoice.saveinvoiceerrormessage");
			}
		} else {
			try {
				int invoiceID = invoice.Save(compId, form, custID);
				newInvoice(request, form);
				getInvoiceInfo(request); // Added here on 29-08-2019
				request.setAttribute("SaveStatus", "Invoice is successfully saved.");
				// ActionErrors e=new ActionErrors();
				// e.add("SaveStatus", new
				// ActionMessage("BzComposer.invoice.saveinvoicesuccessmessage"));
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("SaveStatus", "Invoice is not saved.");
				// ActionErrors e1=new ActionErrors();
				// e1.add("SaveStatus", new
				// ActionMessage("BzComposer.invoice.saveinvoiceerrormessage"));
			}
		}
	}

	public void saveOrder(HttpServletRequest request, InvoiceDto form) throws SQLException {

		InvoiceInfoDao invoice = new InvoiceInfoDao();
		String compId = (String) request.getSession().getAttribute("CID");
		getInvoiceInfo(request);
		boolean exist = invoice.SalesOrderExist(compId, form.getOrderNo());
		int salesOrderType = 7;
		if (exist == true) {
			try {
				int invoiceID = invoice.getSalesInvoiceNo(compId, form.getOrderNo());
				invoice.SalesUpdate(compId, form, salesOrderType, invoiceID);
				newSalesOrder(request, form);
				request.setAttribute("SaveStatus", "Sales Order is successfully updated.");
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("SaveStatus", "Sales Order is not updated.");
			}
		} else {
			try {
				invoice.SaveSalesOrder(compId, form, salesOrderType);
				newSalesOrder(request, form);
				request.setAttribute("SaveStatus", "Sales Order is successfully saved.");
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("SaveStatus", "Sales Order is not saved.");
			}
		}

	}

	public boolean deleteInvoice(HttpServletRequest request, InvoiceDto form, String custID) throws SQLException {

		boolean val = false;
		// boolean exist=invoice.invoiceExist(compId,form.getOrderNo());
		InvoiceInfoDao invoice = new InvoiceInfoDao();

		String compId = (String) request.getSession().getAttribute("CID");
		getInvoiceInfo(request);
		String orderNo = form.getOrderNo();
		boolean exist = invoice.invoiceExist(compId, orderNo);
		if (exist == true) {
			try {
				invoice.Delete(compId, orderNo);
				newInvoice(request, form);
				request.setAttribute("SaveStatus", "Invoice is successfully deleted.");
				val = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			request.setAttribute("SaveStatus", "Invoice is not yet saved.");
			val = false;
			newInvoice(request, form);

		}
		return val;

	}

	public boolean deleteSalesOrder(HttpServletRequest request, InvoiceDto form) throws SQLException {
		boolean val = false;
		// boolean exist=invoice.invoiceExist(compId,form.getOrderNo());
		InvoiceInfoDao invoice = new InvoiceInfoDao();

		String compId = (String) request.getSession().getAttribute("CID");
		getInvoiceInfo(request);
		String orderNo = form.getOrderNo();
		boolean exist = invoice.SalesOrderExist(compId, orderNo);
		if (exist == true) {
			try {
				invoice.DeleteOrder(compId, orderNo);
				newInvoice(request, form);
				request.setAttribute("SaveStatus", "Sales Order is successfully deleted.");
				val = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			request.setAttribute("SaveStatus", "Sales Order is not yet saved.");
			val = false;
			newInvoice(request, form);

		}
		return val;

	}

	public void updateInvoice(String cvId, HttpServletRequest request) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		Loger.log("The Client vendor is from sales detail is " + cvId);

		invoice.SearchCustomer(compId, cvId, request);
		invoice.getServices(request, compId, cvId);
		// request.setAttribute("CustomerDetails",CustomerDetails);
	}

	public void firstInvoice(HttpServletRequest request, InvoiceDto form) {
		ArrayList<InvoiceDto> list = new ArrayList<>();
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		String compId = (String) request.getSession().getAttribute("CID");
		Map<Long, Long> orderNumbersASC = invoice.getInvoiceOrderNumbers(compId, "asc");
		boolean isFound = false;
		for (Long invoiceID : orderNumbersASC.keySet()) {
			System.out.println("first order number: " + orderNumbersASC.get(invoiceID));
			list = invoice.getRecord(request, form, compId, orderNumbersASC.get(invoiceID));
			if (!list.isEmpty() && isInvoiceCustomerIdExists(list.get(0).getCustID(), request)) {
				request.setAttribute("Status", "This is first invoice.");
				// request.setAttribute("Status", "This is no invoice."); commented on
				// 26-11-2019 for checking purpose
				// request.setAttribute("Status", new
				// ActionMessage("BzComposer.invoice.noinvoicemessage"));
				request.setAttribute("Record", list);
				request.setAttribute("Enable", "true");
				/*
				 * request.setAttribute("IsFirst","false"); form.setItemID("0");
				 */
				request.getSession().setAttribute("prevInvoiceID", invoiceID);
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			/* commented on 06-06-2019 */
			/*
			 * newInvoice(request,frm); request.setAttribute("Status",
			 * "There is no invoice.");
			 */

			list = invoice.getRecord(request, form, compId, 0);
			request.setAttribute("Status", "There is no invoice.");
			// request.setAttribute("Status", "This is first invoice."); commented on
			// 26-11-2019 for checking purpose
			// request.setAttribute("Status", new
			// ActionMessage("BzComposer.invoice.firstinvoicemessage"));
			request.setAttribute("Record", list);
			/*
			 * request.setAttribute("Enable", "true");
			 * request.setAttribute("IsFirst","false");
			 */
		}
	}

	public void firstSalesOrder(HttpServletRequest request, InvoiceDto form) {
		ArrayList<InvoiceDto> list = new ArrayList<>();
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		String compId = (String) request.getSession().getAttribute("CID");
		Map<Long, Long> salesOrderNumsASC = invoice.getSalesOrderNumbers(compId, "asc");
		boolean isFound = false;
		for (Long invoiceID : salesOrderNumsASC.keySet()) {
			list = invoice.getSalesOrderRecord(request, form, compId, salesOrderNumsASC.get(invoiceID));
			if (!list.isEmpty() && isInvoiceCustomerIdExists(list.get(0).getCustID(), request)) {
				request.setAttribute("Status", "This is first Sales Order.");
				request.setAttribute("Record", list);
				request.setAttribute("Enable", "true");
				request.setAttribute("IsFirst", "false");
				form.setItemID("0");
				request.getSession().setAttribute("prevInvoiceID", invoiceID);
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			newInvoice(request, form);
			request.setAttribute("Status", "There is no Sales Order.");
		}
	}

	public void lastSalesOrder(HttpServletRequest request, InvoiceDto form) {
		ArrayList<InvoiceDto> list = new ArrayList<>();
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		String compId = (String) request.getSession().getAttribute("CID");
		Map<Long, Long> salesOrderNumsDESC = invoice.getSalesOrderNumbers(compId, "desc");
		boolean isFound = false;
		for (Long invoiceID : salesOrderNumsDESC.keySet()) {
			list = invoice.getSalesOrderRecord(request, form, compId, salesOrderNumsDESC.get(invoiceID));
			if (!list.isEmpty() && isInvoiceCustomerIdExists(list.get(0).getCustID(), request)) {
				request.setAttribute("Status", "This is last Sales Order.");
				request.setAttribute("Record", list);
				request.setAttribute("Enable", "true");
				request.setAttribute("IsLast", "false");
				form.setItemID("0");
				request.getSession().setAttribute("prevInvoiceID", invoiceID);
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			newInvoice(request, form);
			request.setAttribute("Status", "There is no Sales Order.");
		}
	}

	public void lastInvoice(HttpServletRequest request, InvoiceDto form) {
		ArrayList<InvoiceDto> list = new ArrayList<>();
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		String compId = (String) request.getSession().getAttribute("CID");
		Map<Long, Long> orderNumbersDESC = invoice.getInvoiceOrderNumbers(compId, "desc");
		boolean isFound = false;
		for (Long invoiceID : orderNumbersDESC.keySet()) {
			list = invoice.getRecord(request, form, compId, orderNumbersDESC.get(invoiceID));
			if (!list.isEmpty() && isInvoiceCustomerIdExists(list.get(0).getCustID(), request)) {
				request.setAttribute("Status", "This is last invoice.");
				request.setAttribute("Record", list);
				request.setAttribute("Enable", "true");
				request.setAttribute("IsLast", "false");
				form.setItemID("0");
				request.getSession().setAttribute("prevInvoiceID", invoiceID);
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			newInvoice(request, form);
			request.setAttribute("Status", "There is no invoice.");
		}
	}

	public void nextInvoice(HttpServletRequest request, InvoiceDto form) throws SQLException {
		ArrayList<InvoiceDto> list = new ArrayList<>();
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		String compId = (String) request.getSession().getAttribute("CID");
		Map<Long, Long> orderNumbersASC = invoice.getInvoiceOrderNumbers(compId, "asc");
		Long prevInvoiceID = null;
		if (request.getSession().getAttribute("prevInvoiceID") != null) {
			prevInvoiceID = (Long) request.getSession().getAttribute("prevInvoiceID");
		}
		boolean isStart = false;
		boolean isFound = false;
		if (prevInvoiceID == null) {
			isStart = true;
		}
		for (Long invoiceID : orderNumbersASC.keySet()) {
			if (isStart) {
				list = invoice.getRecord(request, form, compId, orderNumbersASC.get(invoiceID));
				if (!list.isEmpty() && isInvoiceCustomerIdExists(list.get(0).getCustID(), request)) {
					request.setAttribute("Record", list);
					request.setAttribute("Enable", "true");
					form.setItemID("0");
					request.getSession().setAttribute("prevInvoiceID", invoiceID);
					isFound = true;
					break;
				}
			}
			if (!isStart && prevInvoiceID != null && prevInvoiceID.equals(invoiceID)) {
				isStart = true;
			}
		}
		if (!isFound) {
			lastInvoice(request, form);
		}
	}

	public void nextSalesOrder(HttpServletRequest request, InvoiceDto form) throws SQLException {
		ArrayList<InvoiceDto> list = new ArrayList<>();
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		String compId = (String) request.getSession().getAttribute("CID");
		getInvoiceInfo(request);
		Map<Long, Long> salesOrderNumsASC = invoice.getSalesOrderNumbers(compId, "asc");
		Long prevInvoiceID = null;
		if (request.getSession().getAttribute("prevInvoiceID") != null) {
			prevInvoiceID = (Long) request.getSession().getAttribute("prevInvoiceID");
		}
		boolean isStart = false;
		boolean isFound = false;
		if (prevInvoiceID == null) {
			isStart = true;
		}
		for (Long invoiceID : salesOrderNumsASC.keySet()) {
			if (isStart) {
				list = invoice.getRecord(request, form, compId, salesOrderNumsASC.get(invoiceID));
				if (!list.isEmpty() && isInvoiceCustomerIdExists(list.get(0).getCustID(), request)) {
					request.setAttribute("Record", list);
					request.setAttribute("Enable", "true");
					form.setItemID("0");
					request.getSession().setAttribute("prevInvoiceID", invoiceID);
					isFound = true;
					break;
				}
			}
			if (!isStart && prevInvoiceID != null && prevInvoiceID.equals(invoiceID)) {
				isStart = true;
			}
		}
		if (!isFound) {
			lastSalesOrder(request, form);
		}
	}

	public void prevoiusInvoice(HttpServletRequest request, InvoiceDto form) throws SQLException {
		ArrayList<InvoiceDto> list = new ArrayList<>();
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		String compId = (String) request.getSession().getAttribute("CID");
		Map<Long, Long> orderNumbersDESC = invoice.getInvoiceOrderNumbers(compId, "desc");
		Long prevInvoiceID = null;
		if (request.getSession().getAttribute("prevInvoiceID") != null) {
			prevInvoiceID = (Long) request.getSession().getAttribute("prevInvoiceID");
		}
		boolean isStart = false;
		boolean isFound = false;
		if (prevInvoiceID == null) {
			isStart = true;
		}
		for (Long invoiceID : orderNumbersDESC.keySet()) {
			if (isStart) {
				list = invoice.getRecord(request, form, compId, orderNumbersDESC.get(invoiceID));
				if (!list.isEmpty() && isInvoiceCustomerIdExists(list.get(0).getCustID(), request)) {
					request.setAttribute("Record", list);
					request.setAttribute("Enable", "true");
					form.setItemID("0");
					request.getSession().setAttribute("prevInvoiceID", invoiceID);
					isFound = true;
					break;
				}
			}
			if (!isStart && prevInvoiceID != null && prevInvoiceID.equals(invoiceID)) {
				isStart = true;
			}
		}
		if (!isFound) {
			firstInvoice(request, form);
		}
	}

	public void prevoiusSalesOrder(HttpServletRequest request, InvoiceDto form) throws SQLException {
		ArrayList<InvoiceDto> list = new ArrayList<>();
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		String compId = (String) request.getSession().getAttribute("CID");
		getInvoiceInfo(request);
		Map<Long, Long> salesOrderNumsDESC = invoice.getSalesOrderNumbers(compId, "desc");
		Long prevInvoiceID = null;
		if (request.getSession().getAttribute("prevInvoiceID") != null) {
			prevInvoiceID = (Long) request.getSession().getAttribute("prevInvoiceID");
		}
		boolean isStart = false;
		boolean isFound = false;
		if (prevInvoiceID == null) {
			isStart = true;
		}
		for (Long invoiceID : salesOrderNumsDESC.keySet()) {
			if (isStart) {
				list = invoice.getRecord(request, form, compId, salesOrderNumsDESC.get(invoiceID));
				if (!list.isEmpty() && isInvoiceCustomerIdExists(list.get(0).getCustID(), request)) {
					request.setAttribute("Record", list);
					request.setAttribute("Enable", "true");
					form.setItemID("0");
					request.getSession().setAttribute("prevInvoiceID", invoiceID);
					isFound = true;
					break;
				}
			}
			if (!isStart && prevInvoiceID != null && prevInvoiceID.equals(invoiceID)) {
				isStart = true;
			}
		}
		if (!isFound) {
			firstSalesOrder(request, form);
		}
	}

	public void payHistory(String cvId, HttpServletRequest request) {
		String compId = (String) request.getSession().getAttribute("CID");
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		invoice.paymentHistory(cvId, compId, request);

	}

	public boolean sendEmailInfo(String ordNo, HttpServletRequest request, String status) {
		boolean result = false;
		String compId = (String) request.getSession().getAttribute("CID");
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		long invoiceID = 0;
		invoiceID = invoice.getInvoiceID(compId, ordNo, status);

		invoice.emailInfo(request, invoiceID, compId, ordNo);
		return result;
	}

	public void sendEmail(HttpServletRequest request, InvoiceDto form) {
		String compId = (String) request.getSession().getAttribute("CID");
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		boolean isSent = invoice.send(compId, form);
		String msg = "";
		if (isSent)
			msg = "Mail send Successfully";
		else
			msg = "Error occurred during sending email";
		request.setAttribute("Result", msg);
	}

	public void getLookup(String cvId, HttpServletRequest request, CustomerDto uform) {

		// HttpSession sess = request.getSession();
		// String compId = (String) sess.getAttribute("CID");
		String cond = null;
		// int flag = 0;
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		ArrayList lookDetails = new ArrayList();
		cond = uform.getDispay_info();
		Loger.log("The Show all" + uform.getDispay_info());
		Loger.log("The Client Vendor Id is" + cvId);
		/*
		 * if(cond.equalsIgnoreCase("ShowAll")) { flag=1; } else { flag=0; }
		 */
		String periodFrom = uform.getPeriodFrom();
		String periodTo = uform.getPeriodTo();
		Loger.log("The From Date is________ " + uform.getPeriodFrom());
		Loger.log("The To date is*******______________ " + uform.getPeriodTo());
		Loger.log("****************cond  " + cond);
		lookDetails = invoice.searchHistory(request, cond, cvId, periodFrom, periodTo);
		request.setAttribute("LookupDetails", lookDetails);

	}

	public void getCustLookup(String cvId, HttpServletRequest request, CustomerDto uform) {

		// HttpSession sess = request.getSession();
		// String compId = (String) sess.getAttribute("CID");
		String cond = null;
		// int flag = 0;
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		ArrayList lookDetails = new ArrayList();

		cond = uform.getDispay_info();
		Loger.log("The Show all" + uform.getDispay_info());
		Loger.log("The Client Vendor Id is" + cvId);
		/*
		 * if(cond.equalsIgnoreCase("ShowAll")) { flag=1; } else { flag=0; }
		 */
		String periodFrom = uform.getPeriodFrom();
		String periodTo = uform.getPeriodTo();
		Loger.log("The From Date is " + uform.getPeriodFrom());
		Loger.log("The To date is " + uform.getPeriodTo());
		lookDetails = invoice.searchHistory(request, cond, cvId, periodFrom, periodTo);
		request.setAttribute("LookupDetails", lookDetails);

	}

	public void newEstimation(HttpServletRequest request, EstimationDto estimationDto) throws SQLException {
		EstimationInfoDao estimation = new EstimationInfoDao();
		String compId = (String) request.getSession().getAttribute("CID");
		String estNum = estimation.getNewEstimationNo(compId);
		estimationDto.setPoNum(estNum);
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		estimationDto.setOrderNo(estNum);
		DateInfo date = new DateInfo();
		int month = date.getMonth();
		int day = date.getDay();

		String da = "", d = "", m = "";
		if (month >= 1 && month <= 9) {
			m = "0" + month;
		} else
			m = "" + month;
		if (day >= 1 && day <= 9) {
			d = "0" + day;
		} else
			d = "" + day;
		da = m + "-" + d + "-" + (date.getYear());

		estimationDto.setOrderDate(da);

		estimationDto.setCustID("0");
		estimationDto.setInvoiceStyle(invoice.getDefaultInvoiceStyleNo(compId));
		estimationDto.setVia("0");
		estimationDto.setTerm("0");
		estimationDto.setRep("0");
		estimationDto.setPayMethod("0");
		estimationDto.setItemID("0");
		estimationDto.setMessage("0");
		estimationDto.setTaxID("0");

		estimationDto.setWeight(0.0);
		estimationDto.setAdjustedtotal(0.0);
		estimationDto.setSubtotal(0.0);
		estimationDto.setShipping(0.0);
		estimationDto.setTotal(0.0);
		estimationDto.setMemo("");

		estimationDto.setCompany("false");
		estimationDto.setTaxable("false");

		estimationDto.setBillTo("");
		estimationDto.setShipTo("");
		estimationDto.setShipDate(da);
		estimationDto.setTax(0.0);
		estimationDto.setItemID("0");
		getInvoiceInfo(request);
		Loger.log("COMPID ==>" + compId);
	}

	public void saveEstimation(HttpServletRequest request, EstimationDto estimationDto) throws SQLException {
		EstimationInfo invoice = new EstimationInfo();
		String compId = (String) request.getSession().getAttribute("CID");
		getInvoiceInfo(request);
		boolean exist = invoice.estimationExist(compId, estimationDto.getOrderNo());
		if (exist == true) {
			try {
				invoice.Update(compId, estimationDto);
				newEstimation(request, estimationDto);
				request.setAttribute("SaveStatus", "Estimation is successfully updated.");
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("SaveStatus", "Estimation is not updated.");
			}
		} else {
			try {
				invoice.Save(compId, estimationDto);
				newEstimation(request, estimationDto);
				request.setAttribute("SaveStatus", "Estimation is successfully saved.");
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("SaveStatus", "Estimation is not saved.");
			}
		}

	}

	public void firstEstimation(HttpServletRequest request, EstimationDto estimationDto) throws SQLException {
		ArrayList<EstimationDto> list = new ArrayList<>();
		String compId = (String) request.getSession().getAttribute("CID");
		getInvoiceInfo(request);
		Map<Long, Long> estNumbersASC = invoiceInfo.getEstimationNumbers(compId, "asc");
		boolean isFound = false;
		for (Long invoiceID : estNumbersASC.keySet()) {
			list = invoiceInfo.getRecord(request, estimationDto, compId, estNumbersASC.get(invoiceID));
			if (!list.isEmpty() && isInvoiceCustomerIdExists(list.get(0).getCustID(), request)) {
				request.setAttribute("Status", "This is first Estimation.");
				request.setAttribute("Record", list);
				request.setAttribute("Enable", "true");
				request.getSession().setAttribute("prevInvoiceID", invoiceID);
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			newEstimation(request, estimationDto);
			request.setAttribute("Status", "There is no estimation.");
		}
	}

	public void lastEstimation(HttpServletRequest request, EstimationDto estimationDto) throws SQLException {
		ArrayList<EstimationDto> list = new ArrayList<>();
		EstimationInfo invoice = new EstimationInfo();
		String compId = (String) request.getSession().getAttribute("CID");
		getInvoiceInfo(request);
		Map<Long, Long> estNumbersDESC = invoice.getEstimationNumbers(compId, "desc");
		boolean isFound = false;
		for (Long invoiceID : estNumbersDESC.keySet()) {
			list = invoice.getRecord(request, estimationDto, compId, estNumbersDESC.get(invoiceID));
			if (!list.isEmpty() && isInvoiceCustomerIdExists(list.get(0).getCustID(), request)) {
				request.setAttribute("Status", "This is last Estimation.");
				request.setAttribute("Record", list);
				request.setAttribute("Enable", "true");
				request.getSession().setAttribute("prevInvoiceID", invoiceID);
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			newEstimation(request, estimationDto);
			request.setAttribute("Status", "There is no estimation.");
		}
	}

	public void nextEstimation(HttpServletRequest request, EstimationDto estimationDto) throws SQLException {
		ArrayList<EstimationDto> list = new ArrayList<>();
		EstimationInfo invoice = new EstimationInfo();
		String compId = (String) request.getSession().getAttribute("CID");
		getInvoiceInfo(request);
		Map<Long, Long> estNumbersASC = invoice.getEstimationNumbers(compId, "asc");
		Long prevInvoiceID = null;
		if (request.getSession().getAttribute("prevInvoiceID") != null) {
			prevInvoiceID = (Long) request.getSession().getAttribute("prevInvoiceID");
		}
		boolean isStart = false;
		boolean isFound = false;
		if (prevInvoiceID == null) {
			isStart = true;
		}
		for (Long invoiceID : estNumbersASC.keySet()) {
			if (isStart) {
				list = invoice.getRecord(request, estimationDto, compId, estNumbersASC.get(invoiceID));
				if (!list.isEmpty() && isInvoiceCustomerIdExists(list.get(0).getCustID(), request)) {
					request.setAttribute("Record", list);
					request.setAttribute("Enable", "true");
					request.getSession().setAttribute("prevInvoiceID", invoiceID);
					isFound = true;
					break;
				}
			}
			if (!isStart && prevInvoiceID != null && prevInvoiceID.equals(invoiceID)) {
				isStart = true;
			}
		}
		if (!isFound) {
			lastEstimation(request, estimationDto);
		}
	}

	public void prevoiusEstimation(HttpServletRequest request, EstimationDto estimationDto) throws SQLException {
		ArrayList<EstimationDto> list = new ArrayList<>();
		EstimationInfo invoice = new EstimationInfo();
		String compId = (String) request.getSession().getAttribute("CID");
		getInvoiceInfo(request);
		Map<Long, Long> estNumbersDESC = invoice.getEstimationNumbers(compId, "desc");
		Long prevInvoiceID = null;
		if (request.getSession().getAttribute("prevInvoiceID") != null) {
			prevInvoiceID = (Long) request.getSession().getAttribute("prevInvoiceID");
		}
		boolean isStart = false;
		boolean isFound = false;
		if (prevInvoiceID == null) {
			isStart = true;
		}
		for (Long invoiceID : estNumbersDESC.keySet()) {
			if (isStart) {
				list = invoice.getRecord(request, estimationDto, compId, estNumbersDESC.get(invoiceID));
				if (!list.isEmpty() && isInvoiceCustomerIdExists(list.get(0).getCustID(), request)) {
					request.setAttribute("Record", list);
					request.setAttribute("Enable", "true");
					request.getSession().setAttribute("prevInvoiceID", invoiceID);
					isFound = true;
					break;
				}
			}
			if (!isStart && prevInvoiceID != null && prevInvoiceID.equals(invoiceID)) {
				isStart = true;
			}
		}
		if (!isFound) {
			firstEstimation(request, estimationDto);
		}
	}

	public boolean deleteEstimation(HttpServletRequest request, EstimationDto estimationDto) throws SQLException {
		boolean val = false;
		EstimationInfo invoice = new EstimationInfo();
		String compId = (String) request.getSession().getAttribute("CID");
		getInvoiceInfo(request);

		String estNo = estimationDto.getOrderNo();
		boolean exist = invoice.estimationExist(compId, estNo);
		if (exist == true) {
			try {
				invoice.Delete(compId, estNo);
				newEstimation(request, estimationDto);
				request.setAttribute("SaveStatus", "Estimation is successfully deleted.");
				val = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			request.setAttribute("SaveStatus", "This invoice is not yet saved.");
			val = false;
			newEstimation(request, estimationDto);

		}
		return val;

	}

	public void getProfitLossDetail(HttpServletRequest request, CustomerDto cForm) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");

		String fromDate = cForm.getFromDate();
		String toDate = cForm.getToDate();
		String sortBy = cForm.getSortBy();
		String datesCombo = cForm.getDatesCombo();
		customer.getProfitLossDetailReport(datesCombo, fromDate, toDate, sortBy, compId, request, cForm);

	}

	public void getProfitLosByItem(HttpServletRequest request, ItemDto iForm) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
 		String fromDate = iForm.getFromDate();
		String toDate = iForm.getToDate();
		String sortBy = iForm.getSortBy();
		String datesCombo = iForm.getDatesCombo();
		ArrayList<ItemDto> profitLossByItem = itemInfoDao.getProfitLossReportByItem(datesCombo, fromDate, toDate, sortBy,
				compId, request, iForm);
		request.setAttribute("profitLossByItem", profitLossByItem);
	}

	public void getAccountPayableGraph(HttpServletRequest request) {
		HttpSession sess = request.getSession();
		//ArrayList<EsalesPOJO> graphDetail = new ArrayList<>();
		String compId = (String) sess.getAttribute("CID");
		// AccountForm aForm = (AccountForm)form;

		// graphDetail = acd.getAccountPayableGraph(compId, request);
	}

	public void getBudgetVsActual(HttpServletRequest request) {
		int year = Calendar.getInstance().get(Calendar.YEAR) + 1;
		System.out.println("Current Year for budget vs actual is:" + year);
		request.setAttribute("Year", year);
	}

	public void getBudgetOverview(HttpServletRequest request) {
		int year = Calendar.getInstance().get(Calendar.YEAR) + 1;
		System.out.println("Current Year for budget overview is:" + year);
		String month[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		for (int i = 0; i < month.length; i++) {
			System.out.println("Budget Year:" + month[i] + year);
		}
		request.setAttribute("Year", year);
	}

	public ArrayList<CustomerDto> getSortedCustomer(HttpServletRequest request, CustomerDto frm, int sortById) {
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");

		ArrayList<CustomerDto> CustomerDetails = new ArrayList<CustomerDto>();
		if (sortById == 1) {
			CustomerDetails = customer.customerDetailsSort(compId, "Name");
		} else if (sortById == 2) {
			CustomerDetails = customer.customerDetailsSort(compId, "FirstName");
		} else if (sortById == 3) {
			CustomerDetails = customer.customerDetailsSort(compId, "LastName");
		}
		request.setAttribute("CustomerDetails", CustomerDetails);
		return CustomerDetails;
	}

	public ArrayList<CustomerDto> getSearchedCustomers(HttpServletRequest request) {
		String compId = (String) request.getSession().getAttribute("CID");
		String venderText = request.getParameter("venderText");

		ArrayList<CustomerDto> CustomerDetails = customer.searchCustomers(compId, venderText);
		request.setAttribute("CustomerDetails", CustomerDetails);
		return CustomerDetails;
	}

	public void setUnitPrice(String companyID, int itemId, double price) {

		customer.setNewUnitPrice(companyID, itemId, price);
	}

	public void setItemName(String companyID, int itemId, String itemName) {

		customer.setNewitemName(companyID, itemId, itemName);
	}

	public void setUnitPriceEstimation(String companyID, int itemId, double price) {

		customer.setUnitPriceEstimation(companyID, itemId, price);
	}

	public void setItemNameEstimation(String companyID, int itemId, String itemName) {

		customer.setNewitemNameEstimation(companyID, itemId, itemName);
	}

	public void getSortedEstimationInfo(HttpServletRequest request, String sort) throws SQLException {
		String compId = (String) request.getSession().getAttribute("CID");
		InvoiceInfoDao invoice = new InvoiceInfoDao();
		ItemInfo item = new ItemInfo();
		ArrayList ClientDetails = new ArrayList();
		if (sort.equals("Name")) {
			ClientDetails = invoice.customerDetails(compId, request);
			System.out.println("Calling sortByName method and getting data:" + ClientDetails.toString());
		} else {
			ClientDetails = invoice.sortedcustomerDetails(compId, request, sort);
			System.out.println("Calling sortByLastName method and getting data:" + ClientDetails.toString());
		}
		request.setAttribute("CDetails", ClientDetails);

		ArrayList shAddr = new ArrayList();
		String companyName = (String) request.getSession().getAttribute("user");
		// System.out.println("CompanyName:"+companyName);
		shAddr = invoice.shipAddress(companyName);
		request.setAttribute("ShAddr", shAddr);

		ArrayList billAddr = new ArrayList();
		billAddr = invoice.billAddress(Integer.parseInt(compId), companyName);
		request.getSession().setAttribute("BillAddr", billAddr);

		/* Invoice Style */
		ArrayList InvoiceStyle = new ArrayList();
		InvoiceStyle = invoice.getInvoiceStyle();
		request.setAttribute("InvoiceStyle", InvoiceStyle);

		/* Via Information */
		ArrayList via = new ArrayList();
		via = invoice.getVia(compId);
		request.setAttribute("Via", via);

		/* Rep Information */
		ArrayList rep = new ArrayList();
		rep = invoice.getRep(compId);
		request.getSession().setAttribute("Rep", rep);

		/* Term Information */
		ArrayList term = new ArrayList();
		term = invoice.getTerm(compId);
		request.setAttribute("Term", term);

		/* Term Information */
		ArrayList payMethod = new ArrayList();
		payMethod = invoice.getPayMethod(compId);
		request.setAttribute("PayMethod", payMethod);

		/* Messages */
		ArrayList message = new ArrayList();
		message = invoice.getMessage(compId);
		request.setAttribute("Message", message);

		/* Tax */
		ArrayList tax = new ArrayList();
		tax = invoice.getTaxes(compId);
		request.setAttribute("Tax", tax);

		/* Item List */
		ArrayList itemList = new ArrayList();
		itemList = invoice.getItemList(compId);
		request.setAttribute("ItemList", itemList);

	}

	private boolean isInvoiceCustomerIdExists(String custID, HttpServletRequest request) {
		boolean isFound = false;
		List<LabelValueBean> customerList = (List<LabelValueBean>) request.getAttribute("CDetails");
		for (LabelValueBean item : customerList) {
			if (custID.equals(item.getValue())) {
				isFound = true;
				break;
			}
		}
		return isFound;
	}
}
