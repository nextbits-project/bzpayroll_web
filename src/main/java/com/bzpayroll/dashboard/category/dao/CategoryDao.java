package com.bzpayroll.dashboard.category.dao;


import com.bzpayroll.dashboard.accounting.dao.Account;
import com.bzpayroll.dashboard.accounting.forms.CategoryListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Service
public class CategoryDao {

    @Autowired
    private Account accountDao;


    public void getProfitLoss(HttpServletRequest request, HttpServletResponse response, CategoryListDto cform) {
        String compId = (String) request.getSession().getAttribute("CID");
        ArrayList<CategoryListDto> profitLoss= new ArrayList<>();
        String fromDate = cform.getFromDate();
        String toDate = cform.getToDate();
        String soryBy = cform.getSortBy();
        String datesCombo = cform.getDatesCombo();
        profitLoss = accountDao.getProfitLoss(fromDate, toDate, soryBy, compId, request);
        request.setAttribute("profitLoss", profitLoss);
    }

    public void getIncomeStatement(HttpServletRequest request,HttpServletResponse response, CategoryListDto cform) {
        String compId = (String) request.getSession().getAttribute("CID");
        ArrayList<CategoryListDto> profitLoss= new ArrayList<>();
        String datesCombo = cform.getDatesCombo();
        String fromDate = cform.getFromDate();
        String toDate = cform.getToDate();
        String sortBy = cform.getSortBy();
        ArrayList<CategoryListDto> catList = accountDao.getIncomeStatementReport(datesCombo, fromDate, toDate, sortBy, compId, request, cform);
        request.setAttribute("catList", catList);
    }
}
