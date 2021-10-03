package com.bzpayroll.dashboard.sales.controllers;


import com.bzpayroll.common.EmailSenderDto;
import com.bzpayroll.common.log.Loger;
import com.bzpayroll.dashboard.sales.dao.SalesOrderBoardDetails;
import com.bzpayroll.dashboard.sales.forms.SalesBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class SalesOrderBoardController {

    @Autowired
    private SalesOrderBoardDetails sd;

    @RequestMapping(value = {"/dashboard/SalesOrderBoard"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String execute(SalesBoardDto salesBoardDto, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException, ServletException {
        String forward = "/sales/SalesOrderBoard";
        model.addAttribute("emailSenderDto", new EmailSenderDto());
        model.addAttribute("salesBoardDto", salesBoardDto);
        String action = request.getParameter("tabid");
        if (action.equalsIgnoreCase("ShowList")) { // For Fname and lname listing
            Loger.log("value from form ");
            sd.getSalesOrderBoardDetails(request, salesBoardDto);
        }
        else if (action.equalsIgnoreCase("UpdateRecord")) { // For Fname and lname listing
             sd.updateRecord(request);
            sd.getSalesOrderBoardDetails(request, salesBoardDto);
        }
        else if (action.equalsIgnoreCase("ReservedInventoryList")) { // ReservedInventoryList Report
             sd.getSalesOrderBoardDetails(request, salesBoardDto);
            forward = "/reports/reservedInventoryList";
        }
        else if(action.equalsIgnoreCase("DamagedInvList")) {
             sd.getSalesOrderBoardDetails(request, salesBoardDto);
        }
        return forward;
    }
}
