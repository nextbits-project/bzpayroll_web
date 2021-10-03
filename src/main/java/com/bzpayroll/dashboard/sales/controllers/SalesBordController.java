/*
 * Author : Avibha IT Solutions Copyright 2007 Avibha IT Solutions. All rights
 * reserved. AVIBHA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.avibha.com
 */
package com.bzpayroll.dashboard.sales.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bzpayroll.common.EmailSenderDto;
import com.bzpayroll.common.log.Loger;
import com.bzpayroll.dashboard.sales.dao.SalesBoardDetails;
import com.bzpayroll.dashboard.sales.forms.SalesBoardDto;
import com.itextpdf.io.IOException;

@Controller
public class SalesBordController{
	
	@Autowired
	private SalesBoardDetails sd;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@RequestMapping(value = {"/dashboard/SalesBord"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String SalesBord(SalesBoardDto salesBoardDto, HttpServletRequest request, HttpServletResponse response, Model model)
			throws IOException, ServletException {
		String forward = "/sales/invoiceboard";
		String action = request.getParameter("tabid");
		model.addAttribute("salesBoardDto", salesBoardDto);
		model.addAttribute("emailSenderDto", new EmailSenderDto());
		/*Path p = new Path();
		p.setPathvalue(request.getContextPath());
		request.getSession().setAttribute("path", p);*/
//		if(request.getSession().isNew()|| ((String) request.getSession().getAttribute("CID"))==null || ((Path) request.getSession().getAttribute("path"))==null){
//			forward="Expired";
//		}
		if (action.equalsIgnoreCase("ShowList")) { // For Fname and lname
			// listing
			Loger.log("value from form ");

			sd.getSalesBoardDetails(request, salesBoardDto);
			forward = "/sales/invoiceboard";
		}

		else if (action.equalsIgnoreCase("UpdateRecord")) { // For Fname and lname
			// listing
			
			sd.updateRecord(request);
			sd.getSalesBoardDetails(request, salesBoardDto);
			forward = "/sales/invoiceboard";
		}
		else if (action.equalsIgnoreCase("AllInvoiceList")) {
			
			sd.getSalesBoardDetails(request, salesBoardDto);
			forward = "/reports/allInvoiceReport";
		}
		else if (action.equalsIgnoreCase("PaidInvoiceList")) {
			
			sd.getSalesBoardDetails(request, salesBoardDto);
			forward = "/reports/PaidInvoiceReport";
		}
		else if (action.equalsIgnoreCase("UnPaidInvoiceList")) {
			
			sd.getSalesBoardDetails(request, salesBoardDto);
			forward = "/reports/UnPaidInvoiceReport";
		}
		else if(action.equalsIgnoreCase("refundInvoiceReport")) {
			
			sd.getRefundInvoiceReport(request, salesBoardDto);
			forward = "/reports/refundInvoiceReport";
		}
		else if (action.equalsIgnoreCase("SalesByRepDetail")) { 
			
			sd.getSalesBoardDetails(request, salesBoardDto);
			forward = "/reports/salesByRepDetailReport";
		}
		else if (action.equalsIgnoreCase("SalesReportByRep")) { 
			
			sd.getSalesReportByRep(request, salesBoardDto);
			forward = "/reports/salesReportByRepReport";
		}
		else if(action.equalsIgnoreCase("SalesRBC")) {
			
			sd.getSalesReportByCustomer(request, salesBoardDto);
			forward = "/reports/salesReportByCustomer";
		}
		else if(action.equalsIgnoreCase("SalesRBI")) {
			
			sd.getSalesReportByCustomer(request, salesBoardDto);
			forward = "/reports/salesReportByItem";
		}
		else if(action.equalsIgnoreCase("SalesRID")) {
			
			sd.getSalesReportByCustomer(request, salesBoardDto);
			forward = "/reports/salesReportByItemDetail";
		}

		return forward;
	}

}
