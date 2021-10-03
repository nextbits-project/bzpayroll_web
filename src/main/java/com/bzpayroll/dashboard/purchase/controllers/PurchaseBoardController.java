
package com.bzpayroll.dashboard.purchase.controllers;

import com.bzpayroll.common.EmailSenderDto;
import com.bzpayroll.common.log.Loger;
import com.bzpayroll.dashboard.purchase.dao.PurchaseBoardDetails;
import com.bzpayroll.dashboard.purchase.dao.PurchaseBoardInfo;
import com.bzpayroll.dashboard.purchase.dao.PurchaseInfo;
import com.bzpayroll.dashboard.purchase.dao.PurchaseInfoDao;
import com.bzpayroll.dashboard.purchase.forms.PurchaseBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class PurchaseBoardController {

	@Autowired
	private PurchaseBoardDetails pd;
	@Autowired
	private PurchaseBoardInfo purchaseBoardInfo;

	@Autowired
	private PurchaseInfoDao purchaseInfoDao;

	@Autowired
	private PurchaseInfo purchaseInfo;

	@RequestMapping(value = {"/dashboard/PurchaseBoard"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String purchaseBoard(PurchaseBoardDto purchaseBoardDto, HttpServletRequest request, Model model) throws IOException, ServletException {
		String forward = "/purchase/poboard";
		model.addAttribute("purchaseBoardDto", purchaseBoardDto);
		model.addAttribute("emailSenderDto", new EmailSenderDto());
		String action = request.getParameter("tabid");
		if (action.equalsIgnoreCase("ShowList")) {		// For Fname and lname listing
			//Loger.log("value from form ");
			pd.getPurchaseBoardDetails(request, purchaseBoardDto);
			forward = "/purchase/poboard";
		}
		else if (action.equalsIgnoreCase("UpdateRecord")) { // For Fname and lname listing

			
			pd.updateRecord(request);
			pd.getPurchaseBoardDetails(request, purchaseBoardDto);

			forward = "/purchase/poboard";
		}else if (action.equalsIgnoreCase("ShowListCheckPO")) { // For Fname and lname listing
			Loger.log("value from form ");
			
			pd.getPurchaseBoardDetails(request, purchaseBoardDto);
			forward = "/purcPurchaseOrderhase/poboard";
		}
		else if (action.equalsIgnoreCase("ShowReceivedItems")) { // For get Received-Items
			
			pd.getPurchaseBoardDetails(request, purchaseBoardDto);
			forward = "/purchase/ReceivedItems";
		}
		else if (action.equalsIgnoreCase("editReceivedItems")) { // For get Received-Item-details
			
			pd.getPurchaseBoardReceivedItemDetails(request);
			forward = "/purchase/updateReceivedItem";
		}
		else if (action.equalsIgnoreCase("updateReceivedItemDetails")) { // For update Received-Item-details
			
			pd.updateReceivedItemDetails(request);
			forward = "redirect:/PurchaseBoard?tabid=editReceivedItems&invoiceID="+request.getParameter("invoiceID");
		}
		else if (action.equalsIgnoreCase("DeleteReceivedItems")) { // For Fname and lname
			// listing
			//Loger.log("value from form ");
			HttpSession sess = request.getSession();
			String compId = (String) sess.getAttribute("CID");
			String InvoiceID = (String) request.getParameter("InvId");
			boolean check = purchaseBoardInfo.DeleteReceivedItem(compId, InvoiceID);
			forward = "redirect:PurchaseBoard?tabid=ShowReceivedItems";
		}
		else if (action.equalsIgnoreCase("AllVendorList")) { // For Fname and lname
			// listing
			HttpSession sess = request.getSession();
			String compId = (String) sess.getAttribute("CID");
			ArrayList VendorDetails = new ArrayList();
			VendorDetails = purchaseInfo.getVendorsBySort(compId, null);
			
			request.setAttribute("VendorDetails", VendorDetails);

			forward = "/reports/allVendorListReport";
		}
		else if (action.equalsIgnoreCase("VendorPhoneList")) { 
			// listing
			HttpSession sess = request.getSession();
			String compId = (String) sess.getAttribute("CID");

			ArrayList VendorDetails = new ArrayList();
			VendorDetails = purchaseInfo.getVendorsBySort(compId, null);
			
			request.setAttribute("VendorDetails", VendorDetails);
			
			forward = "/reports/VendorPhoneList";
		}
		else if (action.equalsIgnoreCase("VendorContactList")) { 
			// listing date vise search issue
			HttpSession sess = request.getSession();
			String compId = (String) sess.getAttribute("CID");

			
			String datesCombo = purchaseBoardDto.getDatesCombo();
			String fromDate = purchaseBoardDto.getFromDate();
			String toDate = purchaseBoardDto.getToDate();
			String sortBy = purchaseBoardDto.getSortBy();
			
			ArrayList VendorDetails = new ArrayList();
			VendorDetails = purchaseInfoDao.vendorContactList(datesCombo, fromDate, toDate, sortBy, compId, request, purchaseBoardDto);
			request.setAttribute("VendorDetails", VendorDetails);
			forward = "/reports/VendorContactListReport";
		}
		else if (action.equalsIgnoreCase("AllPurchaseOrderList")) { 
			// listing
			
			pd.getAllPurchaseOrderList(request, purchaseBoardDto);			
			forward = "/reports/AllPurchaseOrderReport";
		}
		else if (action.equalsIgnoreCase("VendorBalancedetails")) { 
			// listing
			
			pd.getPurchaseBoardDetails(request, purchaseBoardDto);	
			forward = "/reports/VendorBalanceDetailsReport";
		}
		else if (action.equalsIgnoreCase("VendorBalancesymmary")) { 
			// listing vendor Symmary
			
			pd.getvendorBalanceSymmary(request, purchaseBoardDto);
			forward = "/reports/VendorBalanceSymmaryReport";
		}
		else if(action.equalsIgnoreCase("AllPurchaseBillList"))
		{
			
			pd.getPurchaseBillList(request, purchaseBoardDto);
			forward = "/reports/AllPurchaseBillList";
		}
		else if(action.equalsIgnoreCase("CancelledPurREfBill"))
		{
			
			pd.getCancelledREf(request, purchaseBoardDto);;
			forward = "/reports/CancelledRefList";
		}
		else if(action.equalsIgnoreCase("Vendor1099List"))
		{
			
			pd.getVendor1099List(request, purchaseBoardDto);
			forward = "/reports/Vendor1099List";
		}
		else if(action.equalsIgnoreCase("Vendor1099TransactionSummary"))
		{
			
			pd.getVendor1099TransactionSummary(request, purchaseBoardDto);
			forward = "/reports/vendor1099TransactionSummary";
		}
		else if(action.equalsIgnoreCase("vendor1099TransactionDetail"))
		{
			
			//pd.getVendor1099TransactionSummary(request, form);
			forward = "/reports/vendor1099TransactionDetail";
		}
		else if(action.equalsIgnoreCase("PaidPurchaseBillList"))
		{
			
			//pd.getVendor1099List(request, form);
			forward = "/reports/paidPurchaseBillList";
		}
		else if(action.equalsIgnoreCase("ShowUnPaidPurchaseBills"))
		{
			
			//pd.getVendor1099List(request, form);
			forward = "/reports/unpaidPurchaseBillList";
		}

		return forward;
	}
	@RequestMapping(value = {"/CheckPO"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView checkPO(PurchaseBoardDto purchaseBoardDto,
								HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String forward = "/purchase/poboard";
		String action = request.getParameter("tabid");
		if(request.getSession().isNew()|| ((String) request.getSession().getAttribute("CID"))==null ){
			forward="Expired";
		}else if (action.equalsIgnoreCase("ShowListCheckPO")) { // For Fname and lname
			// listing
			Loger.log("value from form ");
			
			pd.getPurchaseBoardDetails(request, purchaseBoardDto);
			forward = "/purchase/checkPO";
		}else if (action.equalsIgnoreCase("UpdateCheckPO")) { // For Fname and lname
			// listing
			
			pd.updateCheckPORecord(request);
			pd.getPurchaseBoardDetails(request, purchaseBoardDto);

//			forward = "/purchase/checkPO";
			forward = "redirect:PurchaseBoard?tabid=ShowList";
		}
		ModelAndView modelAndView =new ModelAndView(forward);
		return modelAndView;
	}
}

