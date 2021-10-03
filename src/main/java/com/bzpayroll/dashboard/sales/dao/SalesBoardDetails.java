package com.bzpayroll.dashboard.sales.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bzpayroll.common.db.SQLExecutor;
import com.bzpayroll.common.log.Loger;
import com.bzpayroll.dashboard.sales.forms.SalesBoardDto;

@Service
public class SalesBoardDetails {

	@Autowired
	private SQLExecutor db;
   
	@Autowired
	private SalesBoardInfo SaleInfo;

    public void getSalesBoardDetails(HttpServletRequest request, SalesBoardDto salesBoardDto) {
        HttpSession sess = request.getSession();
        String compId = (String) sess.getAttribute("CID");
        String invoiceReportType = request.getParameter("ilist"); //report Type
        invoiceReportType =(invoiceReportType == null) ?"":invoiceReportType;

        ArrayList SaleDetails = new ArrayList();
        String oDate1 = salesBoardDto.getOrderDate1();
        String oDate2 = salesBoardDto.getOrderDate2();
        String sDate1 = salesBoardDto.getSaleDate1();
        String sDate2 = salesBoardDto.getSaleDate2();
        String searchType = salesBoardDto.getSearchType();
        String searchTxt = salesBoardDto.getSearchTxt();
        String marketID = salesBoardDto.getFilterMarket();

        String sOption1 = salesBoardDto.getSortType1();
        String sOption2 = salesBoardDto.getSortType2();

        Loger.log("oDate1:" + oDate1);
        Loger.log("oDate2:" + oDate2);
        Loger.log("SDate1:" + sDate1);
        Loger.log("SDate2:" + sDate2);
        Loger.log("SOption1:" + sOption1);
        Loger.log("SOption2:" + sOption2);
        Loger.log("searchType:" + searchType);
        Loger.log("searchTxt:" + searchTxt);
        Loger.log("sMarketId:" + marketID);

        SaleDetails = SaleInfo.SalesRecordSearch(compId, oDate1, oDate2,
                sDate1, sDate2, marketID, sOption1, sOption2, searchType, searchTxt, invoiceReportType);

        request.setAttribute("SalesBoardDetails", SaleDetails);
        salesBoardDto.setFilterMarket(marketID);
        request.setAttribute("Market", salesBoardDto.getFilterMarket());
    }

    public void getSalesReportByCustomer(HttpServletRequest request, SalesBoardDto salesBoardDto) {
        HttpSession sess = request.getSession();
        String compId = (String) sess.getAttribute("CID");
        String invoiceReportType = request.getParameter("ilist");
        String invoiceReportType1 = request.getParameter("ilist");
        invoiceReportType1 =(invoiceReportType == null) ?"":invoiceReportType1;

        
        ArrayList salesRBC = new ArrayList();
        String oDate1 = salesBoardDto.getOrderDate1();
        String oDate2 = salesBoardDto.getOrderDate2();
        String sDate1 = salesBoardDto.getSaleDate1();
        String sDate2 = salesBoardDto.getSaleDate2();
        String sTxt = salesBoardDto.getSearchTxt();
        String marketID = salesBoardDto.getFilterMarket();

        String sOption1 = salesBoardDto.getSortType1();
        String sOption2 = salesBoardDto.getSortType2();
        String sType = salesBoardDto.getSearchType();

        salesRBC = SaleInfo.getSaleReportCustomerSearch(compId, oDate1, oDate2,
                sDate1, sDate2, marketID, sOption1, sOption2, sType,invoiceReportType1);
        request.setAttribute("salesRBC", salesRBC);
    }

    public void getSalesReportByRep(HttpServletRequest request, SalesBoardDto salesBoardDto){

        HttpSession sess = request.getSession();
        String compId = (String) sess.getAttribute("CID");

        

        ArrayList SalesReportByRep = new ArrayList();
        String oDate1 = salesBoardDto.getOrderDate1();
        String oDate2 = salesBoardDto.getOrderDate2();

        SalesReportByRep = SaleInfo.SalesReportByRep(compId, oDate1, oDate2);
        request.setAttribute("SalesReportByRep", SalesReportByRep);
        request.setAttribute("Market", salesBoardDto.getFilterMarket());
    }

    public void updateRecord(HttpServletRequest request) {
        boolean result = SaleInfo.update(request);
        String msg = "";
        if (result) {
            msg = "**Update is sucessfully completed";
            Loger.log("Updated " + msg);
        } else {
            msg = "**Record is not updated";
        }
        request.setAttribute("IsUpdated", msg);
    }

    public void getRefundInvoiceReport(HttpServletRequest request, SalesBoardDto salesBoardDto) {
        HttpSession sess = request.getSession();
        String compId = (String) sess.getAttribute("CID");
        
        ArrayList refundInvoiceReport = new ArrayList();
        String oDate1 = salesBoardDto.getOrderDate1();
        String oDate2 = salesBoardDto.getOrderDate2();

        refundInvoiceReport = SaleInfo.getRefundInvoiceReport(compId,oDate1,oDate2);
        request.setAttribute("refundInvoice", refundInvoiceReport);
    }
}
