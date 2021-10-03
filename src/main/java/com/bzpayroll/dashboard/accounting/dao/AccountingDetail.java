package com.bzpayroll.dashboard.accounting.dao;


import com.bzpayroll.common.db.SQLExecutor;
import com.bzpayroll.dashboard.accounting.forms.AccountDto;
import com.bzpayroll.dashboard.reportcenter.eSales.EsalesPOJO;
import com.bzpayroll.dashboard.sales.forms.InvoiceDto;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
public class AccountingDetail {

    @Autowired
    private SQLExecutor db;

    @Autowired
    private AccountingDAO acd;


    public void getCheckDetail(HttpServletRequest request, AccountDto accountDto) {
        HttpSession sess = request.getSession();
        String compId = (String) sess.getAttribute("CID");

        ArrayList checkDetail = new ArrayList<>();

        String datesCombo = accountDto.getDatesCombo();
        String fromDate = accountDto.getFromDate();
        String toDate = accountDto.getToDate();
        String sortBy = accountDto.getSortBy();

        checkDetail = acd.getCheckDetailList(compId,request,datesCombo,fromDate,toDate,sortBy,accountDto);
        request.setAttribute("checkDetail", checkDetail);
    }

    public void getDepositDetail(HttpServletRequest request, AccountDto accountDto) {
        HttpSession sess = request.getSession();
        String compId = (String) sess.getAttribute("CID");

        ArrayList depositDetail = new ArrayList<>();
        String datesCombo = accountDto.getDatesCombo();
        String fromDate = accountDto.getFromDate();
        String toDate = accountDto.getToDate();
        String sortBy = accountDto.getSortBy();


        depositDetail = acd.getDepositDetailReport(compId, request,datesCombo,fromDate,toDate,sortBy,accountDto);
        request.setAttribute("depositDetail", depositDetail);
    }

    public void getBillDetail(HttpServletRequest request, AccountDto accountDto) {
        HttpSession sess = request.getSession();
        String compId = (String) sess.getAttribute("CID");

        ArrayList billDetail = new ArrayList<>();

        String datesCombo = accountDto.getDatesCombo();
        String fromDate = accountDto.getFromDate();
        String toDate = accountDto.getToDate();
        String sortBy = accountDto.getSortBy();


        billDetail = acd.getBillDetailReport(compId, request,datesCombo,fromDate,toDate,sortBy,accountDto);
        request.setAttribute("billDetail", billDetail);
    }

    public void getARGraph(HttpServletRequest request, AccountDto accountDto) throws JRException {
        HttpSession sess = request.getSession();
        ArrayList<AccountDto> graphDetail = new ArrayList<>();
        String compId = (String) sess.getAttribute("CID");

        graphDetail = acd.getARGraphReport(compId,request);
    }

    public void getIEGraph(HttpServletRequest request, AccountDto accountDto) throws JRException {
        HttpSession sess = request.getSession();
        ArrayList<AccountDto> graphDetail = new ArrayList<>();
        String compId = (String) sess.getAttribute("CID");

        graphDetail = acd.getIEGraphReport(compId,request);
    }

    public void getNWGraph(HttpServletRequest request, AccountDto accountDto) throws JRException
    {
        HttpSession sess = request.getSession();
        ArrayList<AccountDto> graphDetail = new ArrayList<>();
        String compId = (String) sess.getAttribute("CID");

        graphDetail = acd.getNWGraphReport(compId,request);
    }

    public void getBvAGraph(HttpServletRequest request, AccountDto accountDto) throws JRException {
        HttpSession sess = request.getSession();
        ArrayList<AccountDto> graphDetail = new ArrayList<>();
        String compId = (String) sess.getAttribute("CID");

        graphDetail = acd.getBvAGraphReport(compId,request);
    }

    public void getAccountReceivable(HttpServletRequest request, InvoiceDto invoiceDto) {
        HttpSession sess = request.getSession();
        ArrayList<AccountDto> invoiceDetail = new ArrayList<>();
        ArrayList<AccountDto> custDetail = new ArrayList<>();
        String compId = (String) sess.getAttribute("CID");

        String datesCombo = invoiceDto.getDatesCombo();
        String fromDate = invoiceDto.getFromDate();
        String toDate = invoiceDto.getToDate();
        String sortBy = invoiceDto.getSortBy();

        AccountingDAO dao = new AccountingDAO();
        invoiceDetail = dao.getInvoiceDetail(compId, request);
        custDetail = dao.arCustomerDetail(compId, request);
        request.setAttribute("custDetail", custDetail);
        request.setAttribute("invoiceDetail", invoiceDetail);
    }

    public void eSaleSalesGraph(HttpServletRequest request, AccountDto accountDto) throws JRException {
        HttpSession sess = request.getSession();
        ArrayList<EsalesPOJO> graphDetail = new ArrayList<>();
        String compId = (String) sess.getAttribute("CID");

        graphDetail = acd.eSaleSalesGraph(compId,request);
    }
}
