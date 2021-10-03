package com.bzpayroll.dashboard.accounting.controllers;


import com.bzpayroll.common.ConstValue;
import com.bzpayroll.common.EmailSenderDto;
import com.bzpayroll.dashboard.accounting.dao.AccountingDetail;
import com.bzpayroll.dashboard.accounting.forms.AccountDto;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author sarfrazmalik
 */
@Controller
public class AccountingBudgetingController {
    
    @Autowired
    private AccountingDetail ac;

    @RequestMapping(value = {"/dashboard/BankingAccounting"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String execute(AccountDto accountDto, HttpServletRequest request, HttpServletResponse response,
                          Model model) throws IOException, ServletException, JRException {
        String forward = "";
        model.addAttribute("accountDto", accountDto);
        model.addAttribute("emailSenderDto", new EmailSenderDto());
        String action = request.getParameter("tabid");
        HttpSession sess = request.getSession();
        String companyID = (String) sess.getAttribute("CID");
        ConstValue c = new ConstValue();
        c.setCompanyId(Integer.parseInt(companyID));

        if(action.equalsIgnoreCase("CheckDetail")) {
            ac.getCheckDetail(request, accountDto);
            forward = "/reports/CheckDetailReport";
        }
        if(action.equalsIgnoreCase("DepositDetail")) {
            
            ac.getDepositDetail(request, accountDto);
            forward = "/reports/DepositDetailReport";
        }
        if(action.equalsIgnoreCase("BillDetail")) {
            
            ac.getBillDetail(request, accountDto);;
            forward = "/reports/BillDetailReport";
        }
        if(action.equalsIgnoreCase("ARGraph")) {
            
            ac.getARGraph(request, accountDto);
            forward = "/reports/AccountReceivableGraphReport";
        }
        if(action.equalsIgnoreCase("IncomeExpenseGraph")) {
            
            ac.getIEGraph(request, accountDto);
            forward = "/reports/IncomeExpenseGraphReport";
        }
        if(action.equalsIgnoreCase("NetworthGraph")) {
            
            ac.getNWGraph(request, accountDto);
            forward = "/reports/NetworthGraphGraphReport";
        }
        if(action.equalsIgnoreCase("BudgetvsActualGraph")) {
            
            ac.getBvAGraph(request, accountDto);
            forward = "/reports/BudgetvsActualGraph";
        }
        return forward;
    }
}
