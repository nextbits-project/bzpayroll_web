package com.bzpayroll.dashboard.sales.controllers;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bzpayroll.common.ConstValue;
import com.bzpayroll.common.EmailSenderDto;
import com.bzpayroll.common.TblCategory;
import com.bzpayroll.common.TblCategoryLoader;
import com.bzpayroll.common.log.Loger;
import com.bzpayroll.common.utility.CountryState;
import com.bzpayroll.common.utility.Path;
import com.bzpayroll.dashboard.accounting.bean.ReceivableListBean;
import com.bzpayroll.dashboard.accounting.bean.TblAccount;
import com.bzpayroll.dashboard.accounting.bean.TblPaymentType;
import com.bzpayroll.dashboard.accounting.dao.ReceivableLIst;
import com.bzpayroll.dashboard.company.dao.ConfigurationDAO;
import com.bzpayroll.dashboard.configuration.dao.ConfigurationInfo;
import com.bzpayroll.dashboard.employee.dao.Label;
import com.bzpayroll.dashboard.file.forms.ClientVendor;
import com.bzpayroll.dashboard.sales.dao.CustomerInfo;
import com.bzpayroll.dashboard.sales.dao.CustomerInfoDao;
import com.bzpayroll.dashboard.sales.dao.InvoiceInfoDao;
import com.bzpayroll.dashboard.sales.dao.SalesDetailsDao;
import com.bzpayroll.dashboard.sales.forms.CustomerForm;
import com.bzpayroll.dashboard.sales.forms.InvoiceDto;
import com.bzpayroll.dashboard.sales.forms.ItemDto;
import com.bzpayroll.dashboard.sales.forms.SalesBoardDto;
import com.bzpayroll.dashboard.sales.forms.SalesOrderBoardForm;
import com.bzpayroll.dashboard.sales.forms.UpdateInvoiceDto;
import com.bzpayroll.dashboard.sales.forms.salesboardForm;
import com.bzpayroll.sales.forms.CustomerDto; 
@Controller
public class SalesController {
	
	@Autowired
	private ReceivableLIst rl;

	@Autowired
	private TblCategoryLoader category;
	
	@Autowired
	private CustomerInfo cinfo;

	@Autowired
	private CustomerInfoDao info;

	@Autowired
	private SalesDetailsDao sdetails;

	 
	@RequestMapping(value = {"/dashboard/Invoice", "/dashboard/Customer", "/dashboard/Item", "/dashboard/SalesOrder" ,"/dashboard/DataManager"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String invoice(CustomerDto customerDto, InvoiceDto invoiceDto, ItemDto itemDto, UpdateInvoiceDto updateInvoiceDto,
						  HttpServletRequest request, Model model) throws Exception {
		String IN_URI = request.getRequestURI();
		String CUSTOMER_URI = "/Customer";
		String ITEM_URI = "/Item";
		String INVOICE_URI = "/Invoice";
		String SALES_ORDER_URI = "/SalesOrder";
		String SALES_MANAGER_URI = "/DataManager";
		String forward = "sales/invoice";
		if (IN_URI.endsWith(CUSTOMER_URI)){
			forward = "sales/customer";
			model.addAttribute("customerDto", customerDto);
			model.addAttribute("emailSenderDto", new EmailSenderDto());
		}else if (IN_URI.endsWith(ITEM_URI)){
			forward = "sales/item";
			model.addAttribute("itemDto", itemDto);
			model.addAttribute("emailSenderDto", new EmailSenderDto());
		}else if (IN_URI.endsWith(INVOICE_URI)){
			forward = "sales/invoice";
			model.addAttribute("invoiceDto", invoiceDto);
		}else if (IN_URI.endsWith(SALES_ORDER_URI)){
			forward = "sales/salesorder";
			model.addAttribute("invoiceDto", invoiceDto);
		}else if (IN_URI.endsWith(SALES_MANAGER_URI)){
			forward = "sales/datamanager";
		}

		int ordernum = 0;
		String action  = request.getParameter("tabid");			//action initialized on 14-06-2019
		// String companyID="1";
		HttpSession sess = request.getSession();
		String companyID = (String) sess.getAttribute("CID");
		String user = (String) sess.getAttribute("username");	//Added on 15-06-2019
		String userRole = (String) sess.getAttribute("userRole");
		System.out.println("User is:"+user);
		ConstValue c = new ConstValue();
		c.setCompanyId(Integer.parseInt(companyID));
		String method = request.getParameter("request_locale");
		HttpSession session = request.getSession();
		if(method == null)
		{
			method = (String) request.getAttribute("org.apache.struts.action.LOCALE");
		}
	 else
	 {
		 if(method.equals("en"))
		 {
			 session.setAttribute("org.apache.struts.action.LOCALE", Locale.ENGLISH);
			 System.out.println("set locale from salesAction:"+session.getAttribute("org.apache.struts.action.LOCALE").toString());
		 }
		 else if(method.equals("zh"))
		 {
			 session.setAttribute("org.apache.struts.action.LOCALE", Locale.CHINESE);
			 System.out.println("set locale from salesAction:"+session.getAttribute("org.apache.struts.action.LOCALE").toString());
		 }
		 else if(method.equals("es"))
		 {
			 //session.setAttribute("org.apache.struts.action.LOCALE", Locale.Spanish);
			 session.setAttribute("org.apache.struts.action.LOCALE", new Locale("es"));
			 System.out.println("set locale from salesAction:"+session.getAttribute("org.apache.struts.action.LOCALE").toString());
		 }
	 }
		/*Locale locale=new Locale("org.apache.struts.action.LOCALE");*/
		/*String locale = (String)sess.getAttribute("selectedLocale");
		System.out.println("selected language for i18n is:"+locale);*/
		/*setLocale(request, locale);*/
		boolean readData;
		if(companyID.equals("2") || companyID.equals("3") || companyID.equals("4"))
		{
			if(userRole.equals("User"))
			{
				request.getSession().setAttribute("username", "user");
				request.setAttribute("readData", true);
				readData= true;
				System.out.println("readData is true");
			}
			else
			{
				request.getSession().setAttribute("username", user);
				request.setAttribute("readData", false);
				readData= false;
				System.out.println("readData is false");
			}
		}
		/*else if(userRole.equals("User")&&companyID.equals("1"))
		{
			System.out.println("This is new condition added for readonly data.");
			request.getSession().setAttribute("username", user);
			request.setAttribute("readData", true);
			readData= true;
			System.out.println("readData is true");
		}
		else
		{
			request.setAttribute("readData", false);
			readData = false;
			System.out.println("readData is false for other compaies");
		}*/
		try 
		{
			action = request.getParameter("tabid");
			if (action.equals("SetInfoForAccountReceiveble")) {
				ordernum = Integer.parseInt(request.getParameter("tabid"));
				action = request.getParameter("tabid");
			}
		} catch (Exception e) {
			action = request.getParameter("tabid");
			e.printStackTrace();
		}
		Path p = new Path();
		p.setPathvalue(request.getContextPath());
		request.getSession().setAttribute("path", p);
		request.getSession().setAttribute("CID", companyID);
		String vendorAction = request.getParameter("customerAction");
		String cvID = null;
		if (vendorAction != null && vendorAction.equalsIgnoreCase("DELETE")) {
			cvID = request.getParameter("cvID");
			CustomerInfo ci = new CustomerInfo();
			if (ci.deleteCustomer(cvID, companyID)) {
			forward = "/sales/invoice";
				Loger.log("\nCustomer DELETE succeeded, id=" + cvID);
			} else {
				// forward = "failureDELETE";
				Loger.log("\nCustomer DELETE failed, id=" + cvID);
			}

			String cvId = request.getParameter("cvId");
			String rowId = request.getParameter("SelectedRID");
			SalesDetailsDao sd = new SalesDetailsDao();
			sd.getCustomerList(request);
			sd.searchSelectedCustomer(cvId, request, customerDto);
			sd.getAllList(request);

			forward = "/sales/invoice";
			if (IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "redirect:Customer?tabid=Customer";
			}
			return forward;
		}

		if (action == null || action == "" || action.trim().length() < 1)
			action = "Load";
		Loger.log("Action -->-->" + action);

		if (action.equalsIgnoreCase("Load")) {
			Loger.log("nothing is called");
		}

		else if (action.equalsIgnoreCase("DataManager")) { // for DataManager tab
			SalesDetailsDao sd = new SalesDetailsDao();
			sd.getdataManager(request);
			if (IN_URI.endsWith(SALES_MANAGER_URI)){
				forward = "sales/datamanager";
			}else{
				forward = "/sales/invoice";
			}
		}
		
		else if (action.equalsIgnoreCase("DM_Save")) { // save of DataManager tab
			SalesDetailsDao sd = new SalesDetailsDao();
			String sTitleval = request.getParameter("sTitleval");
			String sNewval = request.getParameter("sNewval");
			sd.getdataManagerSave(request);
			sd.getdataManager(request);
			if (IN_URI.endsWith(SALES_MANAGER_URI)){
				forward = "redirect:/DataManager?tabid=datamanager";
			}else{
				forward = "/sales/invoice";
			}
		}

		else if (action.equalsIgnoreCase("DM_Delete")) { // save of DataManager tab
			SalesDetailsDao sd = new SalesDetailsDao();
			sd.getdataManagerDelete(request);
			sd.getdataManager(request);
			if (IN_URI.endsWith(SALES_MANAGER_URI)){
				forward = "redirect:/DataManager?tabid=datamanager";
			}else{
				forward = "/sales/invoice";
			}
		}
		else if (action.equalsIgnoreCase("Customer")) { // save of DataManager tab
			String cvId = request.getParameter("cvId");
			String rowId = request.getParameter("SelectedRID");
			SalesDetailsDao sd = new SalesDetailsDao();
			String firstCvID = sd.getCustomerList(request);
			if (cvId == null){
				cvId = firstCvID;
			}
			sd.searchSelectedCustomer(cvId, request, customerDto);
			sd.getAllList(request);

			if (rowId != null) {
				customerDto.setSelectedRowID(rowId);
			}else {
				customerDto.setSelectedRowID("0");
			}
			if (cvId != null) {
				customerDto.setClientVendorID(cvId);
			}else {
				customerDto.setClientVendorID("0");
			}
			if (rowId != null) {
				request.setAttribute("VendorFrm", customerDto.getSelectedRowID());
			}
			if (IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "/sales/customer";
			}else{
				forward = "/sales/invoice";
			}
		}
		
		else if(action.equalsIgnoreCase("openCustomer")) {
			String cvId = request.getParameter("cvId");
			//String rowId = request.getParameter("SelectedRID");
			SalesDetailsDao sd = new SalesDetailsDao();
			String firstCvID = sd.getCustomerList(request);
			if (cvId == null){
				cvId = firstCvID;
			}
			sd.searchSelectedCustomer(cvId, request, customerDto);
			sd.getAllList(request);
			if (IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "/sales/customer";
			}else{
				forward = "/sales/invoice";
			}
		}
		
		else if(action.equalsIgnoreCase("viewCustomerDetails")) {
			String cvId = request.getParameter("cvId");
			String rowId = request.getParameter("selectedRID");
			SalesDetailsDao sd = new SalesDetailsDao();
			sd.getCustomerList(request);
			sd.searchSelectedCustomer(cvId, request, customerDto);
			sd.getAllList(request);

			if (rowId != null)
				customerDto.setSelectedRowID(rowId);
			else
				customerDto.setSelectedRowID("0");
			if (cvId != null)
				customerDto.setClientVendorID(cvId);
			else
				customerDto.setClientVendorID("0");
			if (rowId != null) {
				request.setAttribute("VendorFrm", customerDto.getSelectedRowID());
			}
			if (IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "/sales/customer";
			}else{
				forward = "/sales/invoice";
			}
		}
		else if (action.equalsIgnoreCase("Banking")) {
			forward = "/sales/invoice";
		}
		
		else if(action.equalsIgnoreCase("SortByFirstName"))
		{
			int sortById = Integer.parseInt(request.getParameter("SortBy"));
			String cvId = request.getParameter("cvId");
			String rowId = request.getParameter("SelectedRID");
			SalesDetailsDao sd = new SalesDetailsDao();
			sd.getCustomerSortByFirstName(request,customerDto);
			sd.searchSelectedCustomer(cvId, request, customerDto);
			sd.getAllList(request);
			
			if (rowId != null)
				customerDto.setSelectedRowID(rowId);
			else
				customerDto.setSelectedRowID("0");
			if (cvId != null)
				customerDto.setClientVendorID(cvId);
			else
				customerDto.setClientVendorID("0");
			if (rowId != null) {
				request.setAttribute("VendorFrm", customerDto.getSelectedRowID());
			}
			request.setAttribute("sortById", sortById);
			forward = "/sales/invoice";
		}

		else if(action.equalsIgnoreCase("sortInvoice"))
		{
			int sortById = Integer.parseInt(request.getParameter("SortBy"));
			String cvId = request.getParameter("cvId");
			String rowId = request.getParameter("SelectedRID");
			SalesDetailsDao sd = new SalesDetailsDao();
			sd.getSortedCustomer(request,customerDto,sortById);
			sd.searchSelectedCustomer(cvId, request, customerDto);
			sd.getAllList(request);

			if (rowId != null)
				customerDto.setSelectedRowID(rowId);
			else
				customerDto.setSelectedRowID("0");
			if (cvId != null)
				customerDto.setClientVendorID(cvId);
			else
				customerDto.setClientVendorID("0");
			if (rowId != null) {
				request.setAttribute("VendorFrm", customerDto.getSelectedRowID());
			}
			request.setAttribute("sortById", sortById);
			System.out.println("SortBy:"+sortById);
			//sdetails.getSortedInvoiceInfo(request,request.getParameter("SortBy"));
			forward = "/sales/invoice";
		}
		else if(action.equalsIgnoreCase("SortByLastName"))
		{
			int sortById = Integer.parseInt(request.getParameter("SortBy"));
			String cvId = request.getParameter("cvId");
			String rowId = request.getParameter("SelectedRID");
			SalesDetailsDao sd = new SalesDetailsDao();
			sd.getCustomerSortByLastName(request,customerDto);
			sd.searchSelectedCustomer(cvId, request, customerDto);
			sd.getAllList(request);
			
			if (rowId != null)
				customerDto.setSelectedRowID(rowId);
			else
				customerDto.setSelectedRowID("0");
			if (cvId != null)
				customerDto.setClientVendorID(cvId);
			else
				customerDto.setClientVendorID("0");
			if (rowId != null) {
				request.setAttribute("VendorFrm", customerDto.getSelectedRowID());
			}
			request.setAttribute("sortById", sortById);
			System.out.println("SortBy:"+sortById);
			forward = "/sales/invoice";
		}
		
		else if(action.equalsIgnoreCase("SortInvoice"))
		{

			sdetails.getSortedInvoiceInfo(request,request.getParameter("SortBy"));
			forward = "/sales/invoice";
		}
		
		else if(action.equalsIgnoreCase("saveUnitPrice"))
		{
			int itemId = Integer.parseInt(request.getParameter("itemID"));
			//float price = Float.parseFloat(request.getParameter("price"));
			double p1  = Double.parseDouble(request.getParameter("price"));
			System.out.println("method:saveUnitPrice\nitemId:"+itemId+"\nPrice:"+p1);
			SalesDetailsDao sd = new SalesDetailsDao();
			sd.setUnitPrice(companyID,itemId,p1);
			sd.getInvoiceInfo(request);
			forward = "/sales/invoice";
		}
		
		else if(action.equalsIgnoreCase("saveItemName"))
		{
			int itemId = Integer.parseInt(request.getParameter("itemID"));
			String itemName = request.getParameter("itemName");
			System.out.println("method:saveUnitPrice\nitemId:"+itemId+"\nItemName:"+itemName);
			SalesDetailsDao sd = new SalesDetailsDao();
			sd.setItemName(companyID,itemId,itemName);
			sd.getInvoiceInfo(request);
			forward = "/sales/invoice";
		}
		
		else if(action.equalsIgnoreCase("updateBillingAddress"))
		{
			String cId = request.getParameter("customerID");
			String address = request.getParameter("billingAddress");
			String billAddressId = request.getParameter("billAddressId");
			System.out.println("customerId:"+cId+"\nBillAddressId="+billAddressId+"\nBilling address details:"+address);
			String strArray[] = address.split(",");
			System.out.println("Address converted to array having length:"+strArray.length);
			for(int i=0; i < strArray.length; i++){
				System.out.println(strArray[i]);
			}
			if(strArray.length == 1 || null!=strArray[0])
				invoiceDto.setFullName(strArray[0]);
			if(strArray.length == 2 || null!=strArray[1])
				invoiceDto.setCompanyName(strArray[1]);
			if(strArray.length == 3 || null!=strArray[2])
				invoiceDto.setAddress1(strArray[2]);
			if(strArray.length == 4 || null!=strArray[3])
				invoiceDto.setAddress2(strArray[3]);
			if(strArray.length == 5 || null!=strArray[4])
				invoiceDto.setCity(strArray[4]);
			if(strArray.length == 6 || null!=strArray[5])
				invoiceDto.setZipcode(strArray[5]);
			if(strArray.length == 7 && null!=strArray[6])
				invoiceDto.setCustomerstate(strArray[6]);
			if(strArray.length == 8 && null!=strArray[7])
				invoiceDto.setCountry(strArray[7]);
			String strName[] = strArray[0].split(" ");
			if(null!=strName[0])
				invoiceDto.setFirstName(strName[0]);
			if(null!=strName[1])
				invoiceDto.setLastName(strName[1]);
			System.out.println("FirstName:"+strName[0]+"\nLastName:"+strName[1]+"\n now we'll set array element to fieldValue using setter method..");
			

			sdetails.updateBillingAddress(invoiceDto,cId,billAddressId);
			sdetails.getInvoiceInfo(request);
			forward = "/sales/invoice";
		}
		
		else if(action.equalsIgnoreCase("updateShippingAddress"))
		{
			String cId = request.getParameter("customerID");
			String address = request.getParameter("shippingAddress");
			String billAddressId = request.getParameter("shipAddressId");
			System.out.println("customerId:"+cId+"\nShippingAddressId="+billAddressId+"\nShipping address details:"+address);
			String strArray[] = address.split(",");
			System.out.println("Address converted to array having length:"+strArray.length);
			//print elements of String array
			for(int i=0; i < strArray.length; i++){
				System.out.println(strArray[i]);
			}
			if(strArray.length == 1 || null!=strArray[0])
				invoiceDto.setFullName(strArray[0]);
			if(strArray.length == 2 || null!=strArray[1])
				invoiceDto.setCompanyName(strArray[1]);
			if(strArray.length == 3 || null!=strArray[2])
				invoiceDto.setAddress1(strArray[2]);
			if(strArray.length == 4 || null!=strArray[3])
				invoiceDto.setAddress2(strArray[3]);
			if(strArray.length == 5 || null!=strArray[4])
				invoiceDto.setCity(strArray[4]);
			if(strArray.length == 6 || null!=strArray[5])
				invoiceDto.setZipcode(strArray[5]);
			if(strArray.length == 7 && null!=strArray[6])
				invoiceDto.setCustomerstate(strArray[6]);
			if(strArray.length == 8 && null!=strArray[7])
				invoiceDto.setCountry(strArray[7]);
			String strName[] = strArray[0].split(" ");
			if(null!=strName[0])
				invoiceDto.setFirstName(strName[0]);
			if(null!=strName[1])
				invoiceDto.setLastName(strName[1]);
			System.out.println("FirstName:"+strName[0]+"\nLastName:"+strName[1]+"\n now we'll set array element to fieldValue using setter method..");
			

			sdetails.updateShippingAddress(invoiceDto,cId,billAddressId);
			sdetails.newInvoice(request, invoiceDto);
			sdetails.getInvoiceInfo(request);
			forward = "/sales/invoice";
		}
			
		else if (action.equalsIgnoreCase("AccountReceiveble")) { 
			ArrayList<ReceivableListBean> ReceivableList = rl.getReceivableList(Integer.parseInt(companyID));
			
			ArrayList<TblCategory> categoryforcombo = category.getCategoryForCombo();
			ArrayList<ClientVendor> clientVendorForCombo = rl.getClientVendorForCombo();
			ArrayList<TblPaymentType> paymentType = rl.getPaymentType();
			ArrayList<TblAccount> account = rl.getAccount();
			request.setAttribute("AccountForCombo", account);
			request.setAttribute("PaymentTypeForCombo", paymentType);
			request.setAttribute("CategoryCombo", categoryforcombo);
			request.setAttribute("ReceivableList", ReceivableList);
			request.setAttribute("ClineVendorForCombo", clientVendorForCombo);
			forward = "/sales/invoice";
		}
		else if (action.equals("SetInfoForAccountReceiveble")) {
			request.setAttribute("OrderNum", ordernum);
			forward = "/sales/invoice";
		}

		else if (action.equalsIgnoreCase("PrintLabels")) { // for Vendor category
			SalesDetailsDao sd = new SalesDetailsDao();
			sd.getCustomerList(request);
			// to print lables
			Label lbl = new Label();
			ArrayList labelList = lbl.getLabelList();
			request.setAttribute("Labels", labelList);
			if (IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "/sales/printLabels";
			}else{
				forward = "/sales/updateInvoice";
			}
		}
		else if (action.equalsIgnoreCase("UpdateLabel")) {
			SalesDetailsDao sd = new SalesDetailsDao();
			sd.getLabel(request, customerDto);
			sd.getLabelType(request);
			request.setAttribute("Customer", "customer");
			if (IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "/sales/setUpLabel";
			}else{
				forward = "/sales/invoice";
			}
		}

		else if (action.equalsIgnoreCase("AddNewLabel")) {
			// tab
			SalesDetailsDao sd = new SalesDetailsDao();
			request.setAttribute("Customer", "C");
			sd.addNewLabel(customerDto);
			if (IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "/sales/addLabel";
			}else{
				forward = "/sales/printInvoice";
			}
		}

		else if (action.equalsIgnoreCase("SaveLabel")) {
			SalesDetailsDao sd = new SalesDetailsDao();
			boolean result = sd.saveLabel(request, customerDto);
			String msg = "";
			request.setAttribute("Customer", "c");
			if (result) {
				sd.addNewLabel(customerDto);
				msg = "Label is saved successfully";
				if (IN_URI.endsWith(CUSTOMER_URI)) {
					forward = "/sales/addLabel";
				}else{
					forward = "/sales/printInvoice";
				}
			} else {
				sd.getLabelType(request);
				msg = "Label is updated successfully";
				if (IN_URI.endsWith(CUSTOMER_URI)) {
					forward = "/sales/setUpLabel";
				}else{
					forward = "/sales/invoice";
				}
			}
			request.setAttribute("Status", msg);
		}

		else if (action.equalsIgnoreCase("DeleteLabel")) {
			request.setAttribute("Customer", "c");
			SalesDetailsDao sd = new SalesDetailsDao();
			sd.deleteLabel(request, customerDto);
			sd.getLabelType(request);
			forward = "/sales/invoice";
		}

		else if (action.equalsIgnoreCase("NewCutomer")) { // for Vendor category


			String compId = (String) request.getSession().getAttribute("CID");
			InvoiceInfoDao invoice = new InvoiceInfoDao();
			String cvId = request.getParameter("CustomerID");

			String cdate = invoice.setCurrentDate();
			customerDto.setDateAdded(cdate);

			invoice.set(cvId, request, updateInvoiceDto, compId);
			invoice.getServices(request, compId, cvId);

			sdetails.getInvoiceInfo(request);
			sdetails.getAllList(request);

			ConfigurationDAO dao = new ConfigurationDAO();
			 //ConfigurationDAO dao = new ConfigurationDAO();
			 String membershipLevel = dao.getmembershipLevel(companyID,request);
			 request.setAttribute("membershipLevel", membershipLevel);
			 String CustomerSize = dao.getNumberOfCustomer(companyID,request);
			 request.setAttribute("CustomerSize", CustomerSize);

			 ConfigurationInfo configInfo = new ConfigurationInfo();
			 request.setAttribute("defaultCongurationData", configInfo.getDefaultCongurationData(request));
			if (IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "/sales/addNewCustomer";
			}else{
				forward = "/sales/payHistory";
			}
		}

		else if (action.equalsIgnoreCase("AdjustInventory")) { // for Vendor
			// category

			sdetails.ItemsList(request, itemDto);
			forward = "/sales/adjustInventory";
		}

		else if (action.equalsIgnoreCase("ApplyInventory")) { // for Vendor category

			sdetails.AdjustInventory(request, itemDto);
			forward = "/sales/adjustInventory";
		}
		else if (action.equalsIgnoreCase("editInventoryQty")) { // For get Received-Item-details

			sdetails.getItemDetails(request);
			forward = "/sales/updateInventoryQty";
		}
		else if (action.equalsIgnoreCase("UpdateInventoryQty")) { // for Vendor category

			sdetails.UpdateInventoryQty(request);
			forward = "redirect:Item?tabid=editInventoryQty&InvId="+request.getParameter("InvId");
		}

		else if (action.equalsIgnoreCase("AddCustomer")) { // to add Vendor
			String compId = (String) request.getSession().getAttribute("CID");
			String cvId = request.getParameter("CustId");
			InvoiceInfoDao invoice = new InvoiceInfoDao();

			sdetails.AddCustomer(request, customerDto);
			invoice.getServices(request, compId, cvId);
			sdetails.getAllList(request);
			if (IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "/sales/addNewCustomer";
			}else {
				forward = "/sales/payHistory";
			}
		}

		else if (action.equalsIgnoreCase("SearchCustomer")) { // to update
			// customer
			// information.
			String cvId = request.getParameter("cvId");

			sdetails.searchCustomer(cvId, request, customerDto);

			sdetails.getAllList(request);
			forward = "/sales/sendEMail";
		}
		else if (action.equalsIgnoreCase("UpdateCustomer")) { // to update Vendor

			sdetails.UpdateCustomer(request, customerDto);
			sdetails.getAllList(request);
			forward = "/sales/sendEMail";
		}

		else if (action.equalsIgnoreCase("SalesBoard")) { // get SalesBoard-page data
			salesboardForm salesForm = new salesboardForm();
			salesForm.setOrderDate1("");
			salesForm.setOrderDate2("");
			salesForm.setSaleDate1("");
			salesForm.setSaleDate2("");
			request.setAttribute("BlankValue", salesForm);
			forward = "/sales/sendEMail";
		}

		else if (action.equalsIgnoreCase("SBTS")) { // For Fname and lname listing
			salesboardForm salesForm = new salesboardForm();
			salesForm.setOrderDate1("");
			salesForm.setOrderDate2("");
			salesForm.setSaleDate1("");
			salesForm.setSaleDate2("");
			request.setAttribute("BlankValue", salesForm);
			forward = "/sales/invoice";
		}

		else if (action.equalsIgnoreCase("Item")) { // get items

			sdetails.FillCombo(request, itemDto);
			sdetails.ItemsList(request, itemDto);
			forward = "/sales/item";
		}
		else if (action.equalsIgnoreCase("SearchItemView")) { // get searched items

			sdetails.ItemsList(request, itemDto);
			sdetails.searchItem(request, itemDto);
			sdetails.FillCombo(request, itemDto);
			request.setAttribute("selectedRID", request.getParameter("selectedRID"));
			forward = "/sales/item";
		}
		else if (action.equalsIgnoreCase("ShowAdd")) { // to add new item

			// request.getSession().setAttribute("ItemType", "1");
			// request.setAttribute("ItemType","1");
			sdetails.FillCombo(request, itemDto);
			sdetails.getItemNameList(request, itemDto);
			int showHistoryPanel = Integer.parseInt(request.getParameter("showHistoryPanel"));
			request.setAttribute("showHistoryPanel", showHistoryPanel);
			System.out.println("Show History Panel:"+ showHistoryPanel);

			ConfigurationDAO dao = new ConfigurationDAO();
			//ConfigurationDAO dao = neItemw ConfigurationDAO();
			String membershipLevel = dao.getmembershipLevel(companyID, request);
			request.setAttribute("membershipLevel", membershipLevel);
			String itemSize = dao.getNumberOfItem(companyID, request);
			request.setAttribute("itemSize", itemSize);
			if (IN_URI.endsWith(ITEM_URI)){
				forward = "sales/addItem";
			}else {
				forward = "sales/payHistory";
			}
		}
		else if (action.equalsIgnoreCase("AddItem")) { // to save Items
			Loger.log("ITEM ADDED");

			sdetails.AddItem(request, itemDto);
			sdetails.FillCombo(request, itemDto);
			if (IN_URI.endsWith(ITEM_URI)) {
				forward = "redirect:Item?tabid=ShowAdd&ItemType=1&showHistoryPanel=1";
			}else {
				forward = "/sales/payHistory";
			}
		}

		else if (action.equalsIgnoreCase("SearchItem")) { // get item details to update item

			sdetails.searchItem(request, itemDto);
			sdetails.FillCombo(request, itemDto);
			forward = "/sales/updateItemDetails";	//itemDetails
		}
		else if (action.equalsIgnoreCase("UpdateItem")) { // to update item

			sdetails.UpdateItem(request, itemDto);
			sdetails.searchItem(request, itemDto);
			String invId = request.getParameter("InvId");
			sdetails.FillCombo(request, itemDto);
			forward = "/sales/invoice";
			if (IN_URI.endsWith(ITEM_URI)){
				forward = "redirect:Item?tabid=SearchItem&InvId="+invId;
			}
		}

		else if (action.equalsIgnoreCase("DeleteItem")) { // to delete item

			sdetails.DeleteItem(request);
			sdetails.ItemsList(request, itemDto);
			forward = "/sales/item";
		}

		else if (action.equalsIgnoreCase("Invoice")) {
			try{

			sdetails.newInvoice(request, invoiceDto);
			sdetails.getInvoiceInfo(request);
			forward = "/sales/invoice";
			}
			catch(Exception e){
			System.out.println("Exception in Invoice:"+e.getMessage());
			ActionErrors e1=new ActionErrors();
			e1.add("SaveStatus", new ActionMessage(e.getMessage()));
			e.printStackTrace();
			forward = "/errorPage";
			}
		}

		else if (action.equalsIgnoreCase("NewInvoice")) {
			System.out.println("Inside NewInvoice Condition");

			/*InvoiceInfo.save(companyID,form);*/
			/*InvoiceInfo i = new InvoiceInfo();
			i.Save(companyID,ifrm);*/
			sdetails.newInvoice(request, invoiceDto);
			sdetails.getInvoiceInfo(request);
			forward = "/sales/invoice";
		}
		
		else if (action.equalsIgnoreCase("addSupplier")) 
		{ 
			// to add
			String addressStatus = request.getParameter("status");
			String addressName = request.getParameter("addName");
			String fName = request.getParameter("fName");
			String lName = request.getParameter("lName");
			String add1 = request.getParameter("add1");
			String add2 = request.getParameter("add2");
			
			//fatching old address data
			String addName =  (String) sess.getAttribute("oldAddressName");
			String first = (String) sess.getAttribute("oldFname");
			String last = (String) sess.getAttribute("oldlName");
			String address1 = (String) sess.getAttribute("oldAddress1");
			String address2 = (String) sess.getAttribute("oldAddress2");
			String stat = (String) sess.getAttribute("oldStatus");
			

			sdetails.addSupplierDetails(request);
			ArrayList serviceList = new ArrayList();
			serviceList = sdetails.addServices(companyID);
			request.setAttribute("serviceList", serviceList);
			
			//new address data
			request.setAttribute("addressStatus", addressStatus);
			request.setAttribute("addressName", addressName);
			request.setAttribute("fName", fName);
			request.setAttribute("lName", lName);
			request.setAttribute("add1", add1);
			request.setAttribute("add2", add2);
			
			//old address data 
			request.setAttribute("newAddressName",addName);
			request.setAttribute("newfName",first);
			request.setAttribute("newlName",last);
			request.setAttribute("newAdd1",address1);
			request.setAttribute("newAdd2",address2);
			request.setAttribute("newAddressStatus", stat);
			forward = "success28";
		}
		
		else if(action.equalsIgnoreCase("addNewSupplier"))
		{
			String addressStatus = request.getParameter("status");
			String addressName = request.getParameter("addName");
			String fName = request.getParameter("fName");
			String lName = request.getParameter("lName");
			String add1 = request.getParameter("add1");
			String add2 = request.getParameter("add2");
			
			//fatching old address data
			String addName =  (String) sess.getAttribute("oldAddressName");
			String first = (String) sess.getAttribute("oldFname");
			String last = (String) sess.getAttribute("oldlName");
			String address1 = (String) sess.getAttribute("oldAddress1");
			String address2 = (String) sess.getAttribute("oldAddress2");
			String stat = (String) sess.getAttribute("oldStatus");
			
			System.out.println("New Supplier To be added:"+addressStatus+" "+addressName+" "+fName+" "+lName+" "+add1+" "+add2);
			

			sdetails.addSupplierDetails(request);

			ArrayList serviceList = new ArrayList();
			serviceList = sdetails.addServices(companyID);
			request.setAttribute("serviceList", serviceList);
			
			CustomerForm frm1 = new CustomerForm();
			
			//new address data
			request.setAttribute("addressStatus", addressStatus);
			request.setAttribute("addressName", addressName);
			request.setAttribute("fName", fName);
			request.setAttribute("lName", lName);
			request.setAttribute("add1", add1);
			request.setAttribute("add2", add2);
			
			//old address data 
			request.setAttribute("newAddressName",addName);
			request.setAttribute("newfName",first);
			request.setAttribute("newlName",last);
			request.setAttribute("newAdd1",address1);
			request.setAttribute("newAdd2",address2);
			request.setAttribute("newAddressStatus", stat);
			forward = "success28";
		}
		else if(action.equalsIgnoreCase("addAddress"))
		{
			// country List
			CountryState cs = new CountryState();
			request.setAttribute("cList", cs.getCountry());
			
			//state List
			CountryState cs1 = new CountryState();
			request.setAttribute("sList", cs1.getStates(companyID));
			
			String chkStatus = request.getParameter("chkStatus");
			System.out.println("Status is:"+chkStatus);
			
			request.setAttribute("chkStatus", chkStatus);

			if(customerDto!=null)
			{
				/*frm.setCname(frm.getCname());
				frm.setFirstName(frm.getFirstName());
				frm.setLastName(frm.getLastName());
				frm.setAddress1(frm.getAddress1());
				frm.setAddress2(frm.getAddress2());
				frm.setProvince(frm.getProvince());
				frm.setCity(frm.getCity());
				frm.setState(frm.getState());
				frm.setCountry(frm.getCountry());
				frm.setZipCode(frm.getZipCode());
				frm.setPhone(frm.getPhone());
				frm.setFax(frm.getFax());*/
				if(chkStatus.equals("Default"))
				{
					customerDto.setStatus(chkStatus);
				}
				else
				{
					customerDto.setStatus("");
				}
			}
			
			//Old Address Data..
			//HttpSession s = request.getSession();
			String addressName = request.getParameter("status");
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String add1 = request.getParameter("address1");
			String add2 = request.getParameter("address2");
			String stat = request.getParameter("status");
			
			System.out.println("Old Address data:firstName:"+fname+"\nlastName:"+lname+"\nAddress1:"+add1+"\nAddress2:"+add2+"\nStatus:"+stat+"\nAddressName:"+addressName);
			
			//old address data...
			sess.setAttribute("oldAddressName", addressName);
			sess.setAttribute("oldFname", fname);
			sess.setAttribute("oldlName", lname);
			sess.setAttribute("oldAddress1", add1);
			sess.setAttribute("oldAddress2", add2);
			sess.setAttribute("oldStatus", stat);
			
			forward = "success22";
		}
		
		else if(action.equalsIgnoreCase("editAddress"))
		{
			String fname = request.getParameter("fName");
			String lname = request.getParameter("lName");
			String add1 = request.getParameter("add1");
			String add2 = request.getParameter("add2");
			String stat = request.getParameter("status");
			
			System.out.println("Received data:"+fname+" "+lname+" "+add1+" "+add2+" "+stat);
			
			//Removing old address data..
			sess.removeAttribute("oldAddressName");
			sess.removeAttribute("oldFname");
			sess.removeAttribute("oldlName");
			sess.removeAttribute("oldAddress1");
			sess.removeAttribute("oldAddress2");
			sess.removeAttribute("oldStatus");
			
			// country List
			CountryState cs = new CountryState();
			request.setAttribute("cList", cs.getCountry());
			
			//state List
			CountryState cs1 = new CountryState();
			request.setAttribute("sList", cs1.getStates(companyID));
			
			String chkStatus = request.getParameter("chkStatus");
			System.out.println("Status is:"+chkStatus);
			
			request.setAttribute("chkStatus", chkStatus);

			if(customerDto!=null)
			{
				customerDto.setFirstName(fname);
				customerDto.setLastName(lname);
				customerDto.setAddress1(add1);
				customerDto.setAddress2(add2);
				if(chkStatus.equals("Default"))
				{
					//frm.setStatus(stat);
					customerDto.setStatus("Default");
				}
				else
				{
					customerDto.setStatus("Active");
				}
			}
			forward = "success22";
		}
		
		else if(action.equalsIgnoreCase("editExistingAddress"))
		{
			String status = request.getParameter("status1");
			
			// country List
			CountryState cs = new CountryState();
			request.setAttribute("cList", cs.getCountry());
			
			//state List
			CountryState cs1 = new CountryState();
			request.setAttribute("sList", cs1.getStates(companyID));
			
			String chkStatus = request.getParameter("chkStatus");
			System.out.println("Status is:"+chkStatus);
			
			request.setAttribute("chkStatus", chkStatus);
			

			sdetails.addSupplierDetails(request);

			ArrayList serviceList = new ArrayList();
			serviceList = sdetails.addServices(companyID);
			request.setAttribute("serviceList", serviceList);

			if(customerDto!=null)
			{
				
				request.setAttribute("addressStatus", status);
				request.setAttribute("fName",customerDto.getFirstName());
				request.setAttribute("lName",customerDto.getLastName());
				request.setAttribute("addressName",customerDto.getTitle());
				request.setAttribute("add1",customerDto.getAddress1());
				request.setAttribute("add2",customerDto.getAddress2());
				request.setAttribute("addressStatus",customerDto.getStatus());
			}
			forward = "/sales/invoice";
		}
		
		else if(action.equalsIgnoreCase("addNewAddress"))
		{
			
			String status = request.getParameter("status");
			CountryState cs = new CountryState();
			request.setAttribute("cList", cs.getCountry());
			
			//state List
			CountryState cs1 = new CountryState();
			request.setAttribute("sList", cs1.getStates(companyID));
			

			sdetails.addSupplierDetails(request);

			ArrayList serviceList = new ArrayList();
			serviceList = sdetails.addServices(companyID);
			request.setAttribute("serviceList", serviceList);

		
			String addName =  (String) sess.getAttribute("oldAddressName");
			String first = (String) sess.getAttribute("oldFname");
			String last = (String) sess.getAttribute("oldlName");
			String add1 = (String) sess.getAttribute("oldAddress1");
			String add2 = (String) sess.getAttribute("oldAddress2");
			String stat = (String) sess.getAttribute("oldStatus");
			
			System.out.println("Old Address Data:\nAddressName:"+addName+"\nFirstName:"+first+"\nLastName:"+last+"\nAddress1:"+
			add1+"\nAddress2:"+add2+"\nStatus:"+stat);
			
			/*if(form!=null)
			{*/
				//new address data....
				request.setAttribute("addressStatus", status);
				request.setAttribute("fName",customerDto.getFirstName());
				request.setAttribute("lName",customerDto.getLastName());
				request.setAttribute("addressName",customerDto.getTitle());
				request.setAttribute("add1",customerDto.getAddress1());
				request.setAttribute("add2",customerDto.getAddress2());
				request.setAttribute("companyAddressName",customerDto.getStatus());
				
				//old address data
				request.setAttribute("newAddressName",addName);
				request.setAttribute("newfName",first);
				request.setAttribute("newlName",last);
				request.setAttribute("newAdd1",add1);
				request.setAttribute("newAdd2",add2);
				request.setAttribute("newAddressStatus", stat);
			//}
			System.out.println("New Address Data:");
			System.out.println("addressStatus:"+status+"\nfirstName:"+customerDto.getFirstName()
			+"\nLastName:"+customerDto.getLastName()+"\naddressName:"+customerDto.getTitle()+"\nAddress1:"+customerDto.getAddress1()+"\nAddress2:"+customerDto.getAddress2());
			
			forward = "/sales/invoice";
			
		}
		
		else if (action.equalsIgnoreCase("NewItem")) { // to add



			// request.getSession().setAttribute("ItemType", "1");
			// request.setAttribute("ItemType","1");
			sdetails.FillCombo(request, itemDto);
			forward = "/sales/invoice";
		}

		else if (action.equalsIgnoreCase("editCustomer")) { // Edit Customer Info
			String cvId = request.getParameter("cvId");
			request.getSession().setAttribute("editedCVID", cvId);


			sdetails.updateInvoice(cvId, request);
			sdetails.getInvoiceInfo(request);
			sdetails.getAllList(request);
			forward = "/sales/updateCustomer";
		}

		else if (action.equalsIgnoreCase("SaveInvoice")) {
			try
			{
				String custID = request.getParameter("custID");
				//InvoiceForm frm = (InvoiceForm) form;
				//System.out.println("customer Id:"+frm.getCustID());

				sdetails.saveInvoice(request,invoiceDto,custID);
				forward = "/sales/invoice";
			}
			catch(Exception e)
			{
				System.out.println("my error:");
				System.err.println(e);
				e.printStackTrace();
			}
		}
		else if (action.equalsIgnoreCase("DeleteInvoice")) {

			String customerID = request.getParameter("CustomerID");
			sdetails.deleteInvoice(request, invoiceDto,customerID);
			forward = "/sales/invoice";
		}

		else if (action.equalsIgnoreCase("ShowInvoiceUpdate")) {
			String cvId = request.getParameter("CustId");

			sdetails.updateInvoice(cvId, request);
			sdetails.getInvoiceInfo(request);
			sdetails.getAllList(request);
			forward = "/sales/updateInvoice";

		}
		else if (action.equalsIgnoreCase("UpdateInvoice")) { // to add Vendor
			String cvId = request.getParameter("CustId");

			sdetails.UpdateCustInfo(request, updateInvoiceDto);
			sdetails.updateInvoice(cvId, request);
			sdetails.getAllList(request);
			forward = "/sales/updateInvoice";
		}
		else if (action.equalsIgnoreCase("UpdateCustInfo")) {

			sdetails.UpdateCustInfo(request, updateInvoiceDto);

			sdetails.getAllList(request);
			System.out.println("Updated");
			forward = "/sales/updateInvoice";

		}
		if (action.equalsIgnoreCase("FirstInvoice")) { // to add Vendor
			try{

			sdetails.getInvoiceInfo(request);
			sdetails.firstInvoice(request,invoiceDto);
			sdetails.getAllList(request);
			/*forward ="/sales/invoice";*/
			/*forward = "/sales/invoice";*/
			forward = "/sales/invoice";}
			catch(Exception e){
				System.out.println("Exception in FirstInvoice:"+e.getMessage());
				ActionErrors e1=new ActionErrors();
				e1.add("SaveStatus", new ActionMessage(e.getMessage()));
				e.printStackTrace();
				forward = "/errorPage";
			}
		}
		if (action.equalsIgnoreCase("LastInvoice")) { // to add Vendor
			try{

			sdetails.getInvoiceInfo(request);
			sdetails.lastInvoice(request, invoiceDto);
			sdetails.getAllList(request);
			forward = "/sales/invoice";
			}
			catch(Exception e){
				System.out.println("Exception in LastInvoice:"+e.getMessage());
				ActionErrors e1=new ActionErrors();
				e1.add("SaveStatus", new ActionMessage(e.getMessage()));
				e.printStackTrace();
				forward = "/errorPage";
			}
		}
		if (action.equalsIgnoreCase("NextInvoice")) { // to add Vendor
			try{

			sdetails.getInvoiceInfo(request);
			sdetails.nextInvoice(request, invoiceDto);
			sdetails.getAllList(request);
			forward = "/sales/invoice";
			}
			catch(Exception e){
				System.out.println("Exception in NextInvoice:"+e.getMessage());
				ActionErrors e1=new ActionErrors();
				e1.add("SaveStatus", new ActionMessage(e.getMessage()));
				e.printStackTrace();
				forward = "/errorPage";
			}
		}
		if (action.equalsIgnoreCase("PreviousInvoice")) { // to add Vendor
			try{

			sdetails.getInvoiceInfo(request);
			sdetails.prevoiusInvoice(request, invoiceDto);
			sdetails.getAllList(request);
			forward = "/sales/invoice";
		}
		catch(Exception e){
			System.out.println("Exception in PreviousInvoice:"+e.getMessage());
			ActionErrors e1=new ActionErrors();
			e1.add("SaveStatus", new ActionMessage(e.getMessage()));
			e.printStackTrace();
			forward = "/errorPage";
			}
		}
		if (action.equalsIgnoreCase("PaymentHistory")) {
			String cvId = request.getParameter("CustId");
			Loger.log("CVID" + cvId);

			sdetails.payHistory(cvId, request);
			forward = "/sales/payHistory";

		}
		if (action.equalsIgnoreCase("ShowEmail")) {
			String orderNo = request.getParameter("OrderNo");

			sdetails.sendEmailInfo(orderNo, request, "invoice");
			forward = "/sales/sendEMail";

		}

		if (action.equalsIgnoreCase("SendMail")) {
			String orderNo = request.getParameter("OrderNo");

			sdetails.sendEmail(request, invoiceDto);
			sdetails.sendEmailInfo(orderNo, request, "invoice");
			forward = "/sales/sendEMail";
		}

		if (action.equalsIgnoreCase("SBLU")) { // Action For Look up Button
			// From SalesBoard.jsp
			String orderNo = request.getParameter("order_no");

			sdetails.getInvoiceInfo(request);
			sdetails.getInitialize(orderNo, request, invoiceDto);
			request.setAttribute("Enable", "true");
			forward = "/sales/invoice";
		}
		if (action.equalsIgnoreCase("IBLU")) { // Action Send to invoice it
			// From SalesBoard.jsp
			String orderNo = request.getParameter("order_no");

			sdetails.getInvoiceInfo(request);
			sdetails.getInitialize(orderNo, request, invoiceDto);

			InvoiceInfoDao invoice = new InvoiceInfoDao();
			invoice.invoiceIt(orderNo); // Action Sales Board InInvoice True
			request.setAttribute("Enable", "true");
			forward = "/sales/invoice";
		}

		if (action.equalsIgnoreCase("CUSTLOOKUP")) {
			String cvId = request.getParameter("CustId");

			sdetails.updateInvoice(cvId, request);
			sdetails.getAllList(request);
			sdetails.getCustLookup(cvId, request, customerDto);
			forward = "/sales/sendEMail";
		}

		if (action.equalsIgnoreCase("ShowPrint")) {
			// String ordNo=request.getParameter("OrderNo");

			// SalesDetails sdetails=new SalesDetails();
			// sdetails.payHistory(cvId,request);
			forward = "/sales/printInvoice";
		}

		if (action.equalsIgnoreCase("DropShipInvoice")) {

			sdetails.dropShipInvoice(request, invoiceDto);
			sdetails.getInvoiceInfo(request);
			forward = "/sales/invoice";
		}

		if (action.equalsIgnoreCase("accounting")) {
			String orderNo = request.getParameter("orderno");

			sdetails.getInvoiceInfo(request);
			sdetails.getInitialize(orderNo, request, invoiceDto);
			request.setAttribute("Enable", "true");
			forward = "/accounting/test";
		}

		if (action.equalsIgnoreCase("SalesOrder")) {

			sdetails.newSalesOrder(request, invoiceDto);
			sdetails.getInvoiceInfo(request);
			if (IN_URI.endsWith(SALES_ORDER_URI)){
				forward = "/sales/salesorder";
			}else{
				forward = "/sales/invoice";
			}
		}
		
		if(action.equalsIgnoreCase("SortCustomerOfSalesOrder"))
		{

			sdetails.getSortedInvoiceInfo(request,request.getParameter("SortBy"));
			forward = "/sales/invoice";
		}
		
		if(action.equalsIgnoreCase("saveUnitPriceForSalesOrder"))
		{
			int itemId = Integer.parseInt(request.getParameter("itemID"));
			//float price = Float.parseFloat(request.getParameter("price"));
			double p1  = Double.parseDouble(request.getParameter("price"));
			System.out.println("method:saveUnitPrice\nitemId:"+itemId+"\nPrice:"+p1);
			SalesDetailsDao sd = new SalesDetailsDao();
			sd.setUnitPrice(companyID,itemId,p1);
			sd.getInvoiceInfo(request);
			forward = "successSalesOrder";
		}
		
		if(action.equalsIgnoreCase("saveItemNameForSalesOrder"))
		{
			int itemId = Integer.parseInt(request.getParameter("itemID"));
			String itemName = request.getParameter("itemName");
			System.out.println("method:saveUnitPrice\nitemId:"+itemId+"\nItemName:"+itemName);
			SalesDetailsDao sd = new SalesDetailsDao();
			sd.setItemName(companyID,itemId,itemName);
			sd.getInvoiceInfo(request);
			forward = "successSalesOrder";
		}
		
		if (action.equalsIgnoreCase("SaveOrder")) { // save Sales Order

			sdetails.saveOrder(request, invoiceDto);
			forward = "/sales/invoice";
		}
		if (action.equalsIgnoreCase("FirstSalesOrder")) { // to add Vendor

			sdetails.getInvoiceInfo(request);
			sdetails.firstSalesOrder(request, invoiceDto);
			sdetails.getAllList(request);
			if (IN_URI.endsWith(SALES_ORDER_URI)){
				forward = "/sales/salesorder";
			}else{
				forward = "/sales/invoice";
			}
		}
		if (action.equalsIgnoreCase("LastSalesOrder")) { // to add Vendor

			sdetails.getInvoiceInfo(request);
			sdetails.lastSalesOrder(request, invoiceDto);
			sdetails.getAllList(request);
			if (IN_URI.endsWith(SALES_ORDER_URI)){
				forward = "/sales/salesorder";
			}else{
				forward = "/sales/invoice";
			}
		}
		if (action.equalsIgnoreCase("NextSalesOrder")) { // to add Vendor

			sdetails.getInvoiceInfo(request);
			sdetails.nextSalesOrder(request, invoiceDto);
			sdetails.getAllList(request);
			if (IN_URI.endsWith(SALES_ORDER_URI)){
				forward = "/sales/salesorder";
			}else{
				forward = "/sales/invoice";
			}
		}
		if (action.equalsIgnoreCase("PreviousSalesOrder")) { // to add Vendor

			sdetails.getInvoiceInfo(request);
			sdetails.prevoiusSalesOrder(request, invoiceDto);
			sdetails.getAllList(request);
			if (IN_URI.endsWith(SALES_ORDER_URI)){
				forward = "/sales/salesorder";
			}else{
				forward = "/sales/invoice";
			}
		}
		if (action.equalsIgnoreCase("DeleteSalesOrder")) {

			sdetails.deleteSalesOrder(request, invoiceDto);
			forward = "/sales/invoice";
		}
		if (action.equalsIgnoreCase("SalesOrderBoard")) {
			SalesOrderBoardForm salesorderForm = new SalesOrderBoardForm();
			salesorderForm.setOrderDate1("");
			salesorderForm.setOrderDate2("");
			salesorderForm.setSaleDate1("");
			salesorderForm.setSaleDate2("");
			request.setAttribute("BlankValue", salesorderForm);
			forward = "/sales/invoice";
		}
		if (action.equalsIgnoreCase("SOBLU")) { // Action For Look up Button
			// From SalesOrderBoard.jsp
			String orderNo = request.getParameter("order_no");

			sdetails.getInvoiceInfo(request);
			sdetails.getSalesOrderInitialize(orderNo, request, invoiceDto);
			request.setAttribute("Enable", "true");
			forward = "/sales/invoice";
		}
		if (action.equalsIgnoreCase("InventoryList")) { // for Inventory List Report
			sdetails.ItemsList(request, itemDto);
			if(IN_URI.endsWith(ITEM_URI)){
				forward = "/reports/inventoryList";
			}else{
				forward = "/sales/printInvoice";
			}
		}
		if (action.equalsIgnoreCase("ItemPriceList")) { // for ItemPriceList List Report
 			sdetails.ItemsReportList(request, itemDto);
			forward = "/reports/itempricelist";
		}
		if (action.equalsIgnoreCase("DiscontinuedList")) { // for DiscontinuedList List Report
 			sdetails.ItemsDicontinuedList(request, itemDto);
			forward = "/reports/discontinuedReportList";
		}
		if (action.equalsIgnoreCase("CustomerList")) { // for CustomerList Report
			String compId = (String) sess.getAttribute("CID");
			ArrayList Customerlist = new ArrayList();
			Customerlist = cinfo.customerDetails(compId);
			model.addAttribute("customerlist", Customerlist);
			if(IN_URI.endsWith(CUSTOMER_URI)){
				forward = "reports/CustomerListReport";
			}else{
				forward = "success9";
			}
		}
		if(action.equalsIgnoreCase("CustomerPhoneList")) {
			String compId = (String) sess.getAttribute("CID");
 			ArrayList CustomerPhoneList = new ArrayList();
			CustomerPhoneList = cinfo.customerDetails(compId);
			model.addAttribute("customerphonelist", CustomerPhoneList);
			if(IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "reports/CustomerPhoneList";
			}else{
				forward = "success10";
			}
		}
		if(action.equalsIgnoreCase("CustomerContactList")) {
			String compId = (String) sess.getAttribute("CID");
 			ArrayList customerContactList = new ArrayList();
			customerContactList = cinfo.customerDetails(compId);
			model.addAttribute("customerContactList", customerContactList);
			if(IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "reports/CustomerContactList";
			}else{
				forward = "success11";
			}
		}
		if(action.equalsIgnoreCase("CustomerTransactionList")) {
			String compId = (String) sess.getAttribute("CID");
			String fromDate = customerDto.getFromDate();
			String toDate = customerDto.getToDate();
			String sortBy = customerDto.getSortBy();
			String datesCombo = customerDto.getDatesCombo();
			ArrayList transactionList = new ArrayList();
			transactionList = info.getTransactionList(datesCombo, fromDate, toDate, sortBy, compId, request, customerDto);
			model.addAttribute("customerTransactionList", transactionList);
			if(IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "reports/CustomerTransactionList";
			}else{
				forward = "success12";
			}
		}
		if(action.equalsIgnoreCase("CustomerBalanceSummary")) {
			String compId = (String) sess.getAttribute("CID");
 			String fromDate = customerDto.getFromDate();
			String toDate = customerDto.getToDate();
			String sortBy = customerDto.getSortBy();
			String datesCombo = customerDto.getDatesCombo();
			ArrayList balanceSummanyList =  new ArrayList();
			balanceSummanyList = info.getBalanceSummaryList(datesCombo, fromDate, toDate, sortBy, compId, request, customerDto);
			model.addAttribute("balanceSummanyList", balanceSummanyList);
			if(IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "reports/CustBalSummary";
			}else{
				forward = "success13";
			}
		}
		if(action.equalsIgnoreCase("CustomerBalDetail")) {
			String compId = (String) sess.getAttribute("CID");
 			String fromDate = customerDto.getFromDate();
			String toDate = customerDto.getToDate();
			String sortBy = customerDto.getSortBy();
			String datesCombo = customerDto.getDatesCombo();
			ArrayList balanceDetail = new ArrayList();
			balanceDetail = info.getBalanceDetail(datesCombo, fromDate, toDate, sortBy, compId, request, customerDto);
			model.addAttribute("balanceDetail", balanceDetail);
			if(IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "reports/CustBalDetail";
			}else{
				forward = "success14";
			}
		}
		if(action.equalsIgnoreCase("SalesByCustomerSummary")) {
			String compId = (String) sess.getAttribute("CID");
 			String fromDate = customerDto.getFromDate();
			String toDate = customerDto.getToDate();
			String sortBy = customerDto.getSortBy();
			String datesCombo = customerDto.getDatesCombo();
			ArrayList getSalesCustomerSummary = new ArrayList();
			getSalesCustomerSummary = info.getSalesByCustomerSummary(datesCombo, fromDate, toDate, sortBy, compId, request, customerDto);
			model.addAttribute("getSalesCustomerSummary", getSalesCustomerSummary);
			if(IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "reports/SalesByCustomerSummary";
			}else{
				forward = "success15";
			}
		}
		if(action.equalsIgnoreCase("IncomeCustomerSummary")) {
			String compId = (String) sess.getAttribute("CID");
 			String fromDate = customerDto.getFromDate();
			String toDate = customerDto.getToDate();
			String sortBy = customerDto.getSortBy();
			String datesCombo = customerDto.getDatesCombo();
			ArrayList incomeCustomerSummary = new ArrayList();
			incomeCustomerSummary = info.getIncomeByCustomerSymmary(datesCombo, fromDate, toDate, sortBy, compId, request, customerDto);
			model.addAttribute("incomeCustomerSummary", incomeCustomerSummary);
			if(IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "reports/IncomeCustomerSummary";
			}else{
				forward = "success16";
			}
		}
		if(action.equalsIgnoreCase("DamagedInvList")) {
			String compId = (String) sess.getAttribute("CID");

			sdetails.getDamagedInvenotyList(request, itemDto);
			if(IN_URI.endsWith(ITEM_URI)) {
				forward = "/reports/damagedInventoryList";
			}else {
				forward = "success9";
			}
		}
		if(action.equalsIgnoreCase("MissingInventoryList")) {
			String compId = (String) sess.getAttribute("CID");
 			sdetails.getMissingInventoryList(request, itemDto);
			if(IN_URI.endsWith(ITEM_URI)) {
				forward = "/reports/missingInventoryList";
			}else{
				forward = "success15";
			}
		}
		if(action.equalsIgnoreCase("ReturnInventoryList")) {
			String compId = (String) sess.getAttribute("CID");
 			sdetails.getReturnInventoryList(request, itemDto);
			if(IN_URI.endsWith(ITEM_URI)) {
				forward = "/reports/returnInventoryList";
			}else{
				forward = "success17";
			}
		}
		if(action.equalsIgnoreCase("InventoryValSummary")) {
			String compId = (String) sess.getAttribute("CID");

			sdetails.getInventoryValuationSummary(request, itemDto);
			if(IN_URI.endsWith(ITEM_URI)) {
				forward = "/reports/inventoryValuationSummary";
			}else{
				forward = "success10";
			}
		}
		if(action.equalsIgnoreCase("InvValDetail")) {
			String compId = (String) sess.getAttribute("CID");

			sdetails.getInventoryValuationDetail(request, itemDto);
			if(IN_URI.endsWith(ITEM_URI)) {
				forward = "/reports/inventoryValuationDetail";
			}else{
				forward = "success11";
			}
		}
		if(action.equalsIgnoreCase("InvOrderReport")) {
			String compId = (String) sess.getAttribute("CID");

			sdetails.getInventoryOrderReport(request, itemDto);
			if(IN_URI.endsWith(ITEM_URI)) {
				forward = "/reports/inventoryOrderReport";
			}else{
				forward = "success12";
			}
		}
		if(action.equalsIgnoreCase("InvStatistic")) {
			String compId = (String) sess.getAttribute("CID");

			sdetails.getInventoryStatistics(request, itemDto);
			if(IN_URI.endsWith(ITEM_URI)) {
				forward = "/reports/inventoryStatisticReport";
			}else{
				forward = "success13";
			}
		}
		if(action.equalsIgnoreCase("AccountPayable"))
		{

			sdetails.getAccountPayableReport(request, customerDto);
			if(IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "/reports/AccountPayableReport";
			}else{
				forward = "success17";
			}
		}
		if(action.equalsIgnoreCase("ProfitLossDetail")) {

			sdetails.getProfitLossDetail(request, customerDto);
			if(IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "/reports/profitLossDetail";
			}else{
				forward = "success18";
			}
		}
		if(action.equalsIgnoreCase("BudgetVsActual")) {
			System.out.println("ProfitLossDetail action called....");

			sdetails.getBudgetVsActual(request);
			if(IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "/reports/budgetVsActualReport";
				model.addAttribute("salesBoardDto", new SalesBoardDto());
			}else{
				forward = "success20";
			}
		}
		if(action.equalsIgnoreCase("BudgetOverview")) {
			System.out.println("BudgetOverview action called....");

			sdetails.getBudgetOverview(request);
			if(IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "/reports/budgetOverviewReport";
				model.addAttribute("salesBoardDto", new SalesBoardDto());
			}else {
				forward = "success21";
			}
		}
		if(action.equalsIgnoreCase("ProfitLossByItem")) {

			sdetails.getProfitLosByItem(request, itemDto);
			if(IN_URI.endsWith(ITEM_URI)) {
				forward = "/reports/profitLossByItemReport";
			}else {
				forward = "success14";
			}
		}
		
		if(action.equalsIgnoreCase("AccountPayableGraph")) {

			sdetails.getAccountPayableGraph(request);
			if(IN_URI.endsWith(CUSTOMER_URI)) {
				forward = "/reports/accountPayableGraph";
			}else {
				forward = "success19";
			}
		}
		
		if(action.equalsIgnoreCase("showDamageInventoryList")) {

			//sdetails.getAccountPayableGraph(request, form);
			if(IN_URI.endsWith(ITEM_URI)) {
				forward = "/reports/damageInventoryList";
			}else {
				forward = "success20";
			}
		}
		else if(action.equalsIgnoreCase("showUnknownInventoryList")) {

			//sdetails.getAccountPayableGraph(request, form);
			if(IN_URI.endsWith(ITEM_URI)) {
				forward = "/reports/showUnknownInventoryList";
			}else {
				forward = "success21";
			}
		}
		else if(action.equalsIgnoreCase("showReturnedInventoryList")) {

			//sdetails.getAccountPayableGraph(request, form);
			if(IN_URI.endsWith(ITEM_URI)) {
				forward = "/reports/showReturnedInventoryList";
			}else {
				forward = "success22";
			}
		}
		else if(action.equalsIgnoreCase("showDailyItemSummary")) {

			//sdetails.getAccountPayableGraph(request, form);
			if(IN_URI.endsWith(ITEM_URI)) {
				forward = "/reports/showDailyItemSummary";
			}else {
				forward = "success23";
			}
		}
		else if(action.equalsIgnoreCase("showDailySalesSummary")) {

			//sdetails.getAccountPayableGraph(request, form);
			if(IN_URI.endsWith(ITEM_URI)) {
				forward = "/reports/showDailySalesSummary";
			}else {
				forward = "success24";
			}
		}
		else if(action.equalsIgnoreCase("ShowSalesTaxSummary")) {

			//sdetails.getAccountPayableGraph(request, form);
			forward = "success25";
		}
		else if(action.equalsIgnoreCase("UploadItem")) {
			if(IN_URI.endsWith(ITEM_URI)) {
				forward = "/file/uploadItem";
			}else {
				forward = "success26";
			}
		}
		else if(action.equals("ExportItem")) {

			String type = request.getParameter("type");
			sdetails.exportFile(request, itemDto, type);
			if(IN_URI.endsWith(ITEM_URI)) {
				forward = "/file/exportItem";
			}else {
				forward = "success27";
			}
		}
		return forward;
	}

	@PostMapping("/Customer/AddCustomer")
	public String AddCustomer(CustomerDto customerDto, HttpServletRequest request) throws Exception {
		System.out.println("----------AddCustomer---------");
		System.out.println("DateInput: "+ customerDto.getDateInput());
		System.out.println("TerminatedDate: "+ customerDto.getTerminatedDate());
		System.out.println("Terminated: "+ customerDto.isTerminated());

		String compId = (String) request.getSession().getAttribute("CID");
		String cvId = request.getParameter("CustId");
		InvoiceInfoDao invoice = new InvoiceInfoDao();

		sdetails.AddCustomer(request, customerDto);
		invoice.getServices(request, compId, cvId);
		sdetails.getAllList(request);
		return "redirect:/Customer?tabid=NewCutomer";
	}

	@PostMapping("/ItemFileUpload")
	public String ItemFileUpload(ItemDto itemDto, HttpServletRequest request, @RequestParam("attachFile") MultipartFile attachFile) {
		String forward = "/include/dashboard";
		String action = request.getParameter("tabid");

		System.out.println("--------------SalesController-------ItemFileUpload-------" + request.getMethod());
		System.out.println("tabid: " + action);
		if(action.equalsIgnoreCase("UploadItemFile")) {

			if(!attachFile.isEmpty()) {
				sdetails.uploadItemFile(request, attachFile);
			}
			forward = "redirect:/Item?tabid=UploadItem";
		}
		return forward;
	}

	@ResponseBody
	@PostMapping("/ItemAjax")
	public Object ItemAjaxCall(ItemDto itemDto, HttpServletRequest request) throws Exception {
		String action = request.getParameter("tabid");
		String status = "Success";
		System.out.println("------------ItemAjax-------------action: "+ action);
		if(action.equalsIgnoreCase("sortItem")) {
			int sortById = Integer.parseInt(request.getParameter("SortBy"));
			String cvId = request.getParameter("cvId");
			String rowId = request.getParameter("SelectedRID");

			ArrayList<ItemDto> itemData = sdetails.sortItemsList(request, itemDto, sortById);
			request.setAttribute("sortById", sortById);
			//sdetails.getSortedInvoiceInfo(request,request.getParameter("SortBy"));
			return itemData;
		}
		else if (action.equalsIgnoreCase("loadItemByTemplate")) { // load-Item-By-Template/ID

			return sdetails.searchItem(request, itemDto);
		}
		return status;
	}

	@ResponseBody
	@PostMapping("/CustomerAjax")
	public Object CustomerAjaxCall(CustomerDto customerDto, HttpServletRequest request) throws Exception {
		String action = request.getParameter("tabid");
		String status = "Success";
		SalesDetailsDao sd = new SalesDetailsDao();
		System.out.println("------------CustomerAjax-------------action: "+ action);
		if(action.equalsIgnoreCase("sortInvoice")) {
			int sortById = Integer.parseInt(request.getParameter("SortBy"));
			String cvId = request.getParameter("cvId");
			String rowId = request.getParameter("SelectedRID");
			ArrayList<CustomerDto> CustomerDetails = sd.getSortedCustomer(request,customerDto,sortById);
			sd.searchSelectedCustomer(cvId, request, customerDto);
			sd.getAllList(request);
			if (rowId != null)
				customerDto.setSelectedRowID(rowId);
			else
				customerDto.setSelectedRowID("0");
			if (cvId != null)
				customerDto.setClientVendorID(cvId);
			else
				customerDto.setClientVendorID("0");
			if (rowId != null) {
				request.setAttribute("VendorFrm", customerDto.getSelectedRowID());
			}
			request.setAttribute("sortById", sortById);
			//sdetails.getSortedInvoiceInfo(request,request.getParameter("SortBy"));
			return CustomerDetails;
		}
		if(action.equalsIgnoreCase("searchCustomers")) {
			return sd.getSearchedCustomers(request);
		}
		else if(action.equalsIgnoreCase("getCustomerDetails")) {
			String cvId = request.getParameter("cvId");
			String rowId = request.getParameter("selectedRID");
			sd.getCustomerList(request);
			sd.searchSelectedCustomer(cvId, request, customerDto);
			sd.getAllList(request);
			if (rowId != null)
				customerDto.setSelectedRowID(rowId);
			else
				customerDto.setSelectedRowID("0");
			if (cvId != null)
				customerDto.setClientVendorID(cvId);
			else
				customerDto.setClientVendorID("0");
			if (rowId != null) {
				request.setAttribute("VendorFrm", customerDto.getSelectedRowID());
			}
			UpdateInvoiceDto updateInvoiceDto = (UpdateInvoiceDto)request.getAttribute("CustomerDetails1");
			return updateInvoiceDto;
		}
		else if(action.equalsIgnoreCase("zipcode")){
			String zipcode = request.getParameter("zipcode");
			CountryState cs = new CountryState();
			return cs.getAddressDetailsByZipcode(zipcode);
		}
		return status;
	}

}
