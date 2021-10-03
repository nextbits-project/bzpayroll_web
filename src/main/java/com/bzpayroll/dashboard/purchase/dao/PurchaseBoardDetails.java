package com.bzpayroll.dashboard.purchase.dao;


import com.bzpayroll.common.log.Loger;
import com.bzpayroll.dashboard.purchase.forms.PurchaseBoard;
import com.bzpayroll.dashboard.purchase.forms.PurchaseBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
public class PurchaseBoardDetails {
	@Autowired
	private PurchaseBoardInfoDao purchaseInfo;


	public void getPurchaseBoardDetails(HttpServletRequest request, PurchaseBoardDto pform) {
		
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		String action = request.getParameter("tabid");
	
		ArrayList PurchaseDetails = new ArrayList();
		

		String oDate1 = pform.getOrderDate1();
		String oDate2 = pform.getOrderDate2();
		String sDate1 = pform.getSaleDate1();
		String sDate2 = pform.getSaleDate2();

		String searchType = pform.getSearchType();
		String searchTxt = pform.getSearchTxt();
		String marketID = pform.getFilterMarket();
		
		String datesCombo = pform.getDatesCombo();
		String fromDate = pform.getFromDate();
		String toDate = pform.getToDate();
		String sortBy = pform.getSortBy();
		String sOption1 = pform.getSortType1();
		String sOption2 = pform.getSortType2();

		PurchaseDetails = purchaseInfo.PurchaseRecordSearch(request,compId, oDate1, oDate2,
				sDate1, sDate2, marketID, sOption1, sOption2, searchType, searchTxt, action,datesCombo,fromDate,toDate,sortBy,pform);

		request.setAttribute("PurchaseBoardDetails", PurchaseDetails);
		pform.setFilterMarket(marketID);
		request.setAttribute("Market", pform.getFilterMarket());
	}

	public void getPurchaseBoardReceivedItemDetails(HttpServletRequest request) {
		String invoiceID = request.getParameter("invoiceID");

		PurchaseBoard pbDetails = purchaseInfo.getPurchaseBoardReceivedItemDetails(invoiceID);
		request.setAttribute("pbDetails", pbDetails);
	}

	public void updateReceivedItemDetails(HttpServletRequest request) {

		boolean status = purchaseInfo.updateReceivedItemDetails(request);
		if(status) {
			request.setAttribute("SaveStatus", "Item updated successfully");
		}
	}
	
public void getPurchaseBillList(HttpServletRequest request, PurchaseBoardDto pform)
{
	HttpSession sess = request.getSession();
	String compId = (String) sess.getAttribute("CID");
	String action = request.getParameter("tabid");


	ArrayList PurchaseBillDetails = new ArrayList();
	
	String oDate1 = pform.getOrderDate1();
	String oDate2 = pform.getOrderDate2();
	String sDate1 = pform.getSaleDate1();
	String sDate2 = pform.getSaleDate2();
	String sTxt = pform.getSearchTxt();
	String marketID = pform.getFilterMarket();

	String sOption1 = pform.getSortType1();
	String sOption2 = pform.getSortType2();
	
	String datesCombo = pform.getDatesCombo();
	String fromDate = pform.getFromDate();
	String toDate = pform.getToDate();
	String sortBy = pform.getSortBy();

	String sType = pform.getSearchType();
	PurchaseBillDetails = purchaseInfo.getPurchaseBillLists(request, compId, oDate1, oDate2, sDate1, sDate2, marketID, sOption1, sOption2, sType, action,datesCombo,fromDate,toDate,sortBy,pform);
	request.setAttribute("PurchaseBillDetails", PurchaseBillDetails);
}
public void getvendorBalanceSymmary(HttpServletRequest request, PurchaseBoardDto pform) {
		
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		String action = request.getParameter("tabid");
	

		ArrayList vendorbalanceSymmary = new ArrayList();
			
		String sDate1 = pform.getSaleDate1();
		String sDate2 = pform.getSaleDate2();
		
		String datesCombo = pform.getDatesCombo();
		String fromDate = pform.getFromDate();
		String toDate = pform.getToDate();
		String sortBy = pform.getSortBy();

		Loger.log("oDate1:" + sDate1);
		Loger.log("oDate2:" + sDate2);

		
		vendorbalanceSymmary = purchaseInfo.VendorSummaryRecordSearch(request,compId, sDate1, sDate2,action,datesCombo,fromDate,toDate,sortBy,pform);
		
		request.setAttribute("PurchaseBoardDetails", vendorbalanceSymmary);
	}
public void getCancelledREf(HttpServletRequest request, PurchaseBoardDto pform)
{
	HttpSession sess = request.getSession();
	String compId = (String) sess.getAttribute("CID");
	String action = request.getParameter("tabid");


	ArrayList cancelledPuBillRef = new ArrayList();
		
	String sDate1 = pform.getSaleDate1();
	String sDate2 = pform.getSaleDate2();
	
	String oDate1 = pform.getOrderDate1();
	String oDate2 = pform.getOrderDate2();
	String sTxt = pform.getSearchTxt();
	String marketID = pform.getFilterMarket();

	String sOption1 = pform.getSortType1();
	String sOption2 = pform.getSortType2();
	
	String datesCombo = pform.getDatesCombo();
	String fromDate = pform.getFromDate();
	String toDate = pform.getToDate();
	String sortBy = pform.getSortBy();

	String sType = pform.getSearchType();
	cancelledPuBillRef = purchaseInfo.getCancelledPuBillRefList(request, compId, oDate1, oDate2, sDate1, sDate2, marketID, sOption1, sOption2, sType, action,datesCombo,fromDate,toDate,sortBy,pform);
	request.setAttribute("cancelledPuBillRef", cancelledPuBillRef);

}
public void getVendor1099List(HttpServletRequest request, PurchaseBoardDto pform)
{
	HttpSession sess = request.getSession();
	String compId = (String) sess.getAttribute("CID");
	String action = request.getParameter("tabid");


	ArrayList vendorList1099 = new ArrayList();
	

	String sDate1 = pform.getSaleDate1();
	String sDate2 = pform.getSaleDate2();
	
	String oDate1 = pform.getOrderDate1();
	String oDate2 = pform.getOrderDate2();
	String sTxt = pform.getSearchTxt();
	String marketID = pform.getFilterMarket();
	
	String datesCombo = pform.getDatesCombo();
	String fromDate = pform.getFromDate();
	String toDate = pform.getToDate();
	String sortBy = pform.getSortBy();

	String sOption1 = pform.getSortType1();
	String sOption2 = pform.getSortType2();

	String sType = pform.getSearchType();
	vendorList1099 = purchaseInfo.getVendor1099List(request, compId, oDate1, oDate2, sDate1, sDate2, marketID, sOption1, sOption2, sType, action,datesCombo,fromDate,toDate,sortBy,pform);
    request.setAttribute("vendorList1099", vendorList1099);
}

public void getVendor1099TransactionSummary(HttpServletRequest request, PurchaseBoardDto pform)
{
	HttpSession sess = request.getSession();
	String compId = (String) sess.getAttribute("CID");
	String action = request.getParameter("tabid");


	ArrayList vendorList1099 = new ArrayList();
	

	String sDate1 = pform.getSaleDate1();
	String sDate2 = pform.getSaleDate2();
	
	String oDate1 = pform.getOrderDate1();
	String oDate2 = pform.getOrderDate2();
	String sTxt = pform.getSearchTxt();
	String marketID = pform.getFilterMarket();
	
	String datesCombo = pform.getDatesCombo();
	String fromDate = pform.getFromDate();
	String toDate = pform.getToDate();
	String sortBy = pform.getSortBy();

	String sOption1 = pform.getSortType1();
	String sOption2 = pform.getSortType2();

	String sType = pform.getSearchType();
	vendorList1099 = purchaseInfo.getVendor1099TransactionSummary(request, compId, oDate1, oDate2, sDate1, sDate2, marketID, sOption1, sOption2, sType, action,datesCombo,fromDate,toDate,sortBy,pform);
    request.setAttribute("vendorList1099", vendorList1099);
}

	public void updateRecord(HttpServletRequest request) {
		

		boolean result = purchaseInfo.update(request);
		String msg = "";
		if (result) {
			msg = "**Update is sucessfully completed";
			Loger.log("Updated " + msg);
		} else {
			msg = "**Record is not updated";
		}
		request.setAttribute("IsUpdated", msg);
	}
       public void updateCheckPORecord(HttpServletRequest request) {
		

		boolean result = purchaseInfo.updateCheckPO(request);
		String msg = "";
		if (result) {
			msg = "**Item Recevied  Sucessfully completed";
			Loger.log("Updated " + msg);
		} else {
			msg = "**Item Recevied is not updated";
		}
		request.getSession().setAttribute("IsUpdated", msg);
	}
       
    /*get all purchase orders*/
       public void getAllPurchaseOrderList(HttpServletRequest request, PurchaseBoardDto pform) {
   		
   		HttpSession sess = request.getSession();
   		String compId = (String) sess.getAttribute("CID");
   		String action = request.getParameter("tabid");
   	

   		ArrayList PurchaseDetails = new ArrayList();
   		
   		String oDate1 = pform.getOrderDate1();
   		String oDate2 = pform.getOrderDate2();
   		String sDate1 = pform.getSaleDate1();
   		String sDate2 = pform.getSaleDate2();
   		String sTxt = pform.getSearchTxt();
   		String marketID = pform.getFilterMarket();
   		
   		String datesCombo = pform.getDatesCombo();
   		String fromDate = pform.getFromDate();
   		String toDate = pform.getToDate();
   		String sortBy = pform.getSortBy();

   		String sOption1 = pform.getSortType1();
   		String sOption2 = pform.getSortType2();

   		String sType = pform.getSearchType();

   		Loger.log("oDate1:" + oDate1);
   		Loger.log("oDate2:" + oDate2);
   		Loger.log("SDate1:" + sDate1);
   		Loger.log("SDate2:" + sDate2);

   		Loger.log("SOption1:" + sOption1);
   		Loger.log("SOption2:" + sOption2);
   		Loger.log("SType:" + sType);

   		Loger.log("sTxt:" + sTxt);
   		Loger.log("sMarketId:" + marketID);

   		
   		PurchaseDetails = purchaseInfo.getAllPurchaseOrderList(request,compId, oDate1, oDate2,
   				sDate1, sDate2, marketID, sOption1, sOption2, sType,action,datesCombo,fromDate,toDate,sortBy,pform);

   		request.setAttribute("PurchaseBoardDetails", PurchaseDetails);
   		pform.setFilterMarket(marketID);
   		request.setAttribute("Market", pform.getFilterMarket());
   	}   
       
       
     /**/
}
