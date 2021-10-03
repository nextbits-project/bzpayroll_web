package com.bzpayroll.dashboard.category.controllers;


import com.bzpayroll.common.EmailSenderDto;
import com.bzpayroll.dashboard.accounting.forms.CategoryListDto;
import com.bzpayroll.dashboard.category.dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sarfrazmalik
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryDao dao;

    @RequestMapping(value = {"/dashboard/Category"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String execute(CategoryListDto categoryListDto, HttpServletRequest request, HttpServletResponse response,
                          Model model) throws IOException, ServletException {
        String forward = "";
        model.addAttribute("categoryListDto", categoryListDto);
        model.addAttribute("emailSenderDto", new EmailSenderDto());
        String action = request.getParameter("tabid");
        if(action.equalsIgnoreCase("ProfitLoss")) {
            dao.getProfitLoss(request, response, categoryListDto);
            forward = "/reports/profitLoss";
        }
        if(action.equalsIgnoreCase("IncomeStatement")) {
             dao.getIncomeStatement(request, response, categoryListDto);
            forward = "/reports/incomeStatementReport";
        }
        return forward;
    }
}
