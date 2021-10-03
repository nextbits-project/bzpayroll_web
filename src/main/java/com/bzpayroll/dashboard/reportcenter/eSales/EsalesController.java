package com.bzpayroll.dashboard.reportcenter.eSales;


import com.bzpayroll.common.ConstValue;
import com.bzpayroll.common.EmailSenderDto;
import com.bzpayroll.common.log.Loger;
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
import java.util.ArrayList;

/**
 * @author sarfrazmalik
 */
@Controller
public class EsalesController {

    @Autowired
    private EsalesDAO EsalesDAO;
    @Autowired
    private AccountingDetail ac;

    @RequestMapping(value = {"/dashboard/ReportCenterESales"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String execute(AccountDto accountDto, HttpServletRequest request, HttpServletResponse response,
                          Model model) throws IOException, ServletException, JRException {
        Loger.log("INSIDE REPORT CENTER eSales ACTION");
        String forward = "/accounting/accounting";
        HttpSession sess = request.getSession();
        String companyID = (String) sess.getAttribute("CID");
        ConstValue c = new ConstValue();
        c.setCompanyId(Integer.parseInt(companyID));
        String action = request.getParameter("tabid");
        model.addAttribute("accountDto", accountDto);
        model.addAttribute("emailSenderDto", new EmailSenderDto());

        if(action.equalsIgnoreCase("ESalesInvoiceDetail")) {
            String fromDate = accountDto.getFromDate();
            String toDate = accountDto.getToDate();
            String sortBy = accountDto.getSortBy();
            String datesCombo = accountDto.getDatesCombo();
            ArrayList<EsalesPOJO> acList = EsalesDAO.eSalesInvoiceDetail(datesCombo, fromDate, toDate, sortBy, companyID, request, accountDto);
            request.setAttribute("acList", acList);
            forward = "/reports/eSalesInvoiceDetail";
        }

        if(action.equalsIgnoreCase("ESalesRefundDetail")) {
            String fromDate = accountDto.getFromDate();
            String toDate = accountDto.getToDate();
            String sortBy = accountDto.getSortBy();
            String datesCombo = accountDto.getDatesCombo();
            ArrayList<EsalesPOJO> acList = EsalesDAO.eSalesRefundDetail(datesCombo, fromDate, toDate, sortBy, companyID, request, accountDto);
            request.setAttribute("acList", acList);
            forward = "/reports/eSalesRefundDet";
        }

        if(action.equalsIgnoreCase("ESalessaleDetail")) {
            String fromDate = accountDto.getFromDate();
            String toDate = accountDto.getToDate();
            String sortBy = accountDto.getSortBy();
            String datesCombo = accountDto.getDatesCombo();
            ArrayList<EsalesPOJO> acList = EsalesDAO.eSalessaleDetail(datesCombo, fromDate, toDate, sortBy, companyID, request, accountDto);
            request.setAttribute("acList", acList);
            forward = "reports/eSalessaleDetail";
        }

        if(action.equalsIgnoreCase("ESalesInventorySaleStatistics")) {
            String fromDate = accountDto.getFromDate();
            String toDate = accountDto.getToDate();
            String sortBy = accountDto.getSortBy();
            String datesCombo = accountDto.getDatesCombo();
            ArrayList<EsalesPOJO> acList = EsalesDAO.eSalesInventorySaleStatistics(datesCombo, fromDate, toDate, sortBy, companyID, request, accountDto);
            request.setAttribute("acList", acList);
            forward = "reports/eSalesInventorySaleStatistics";
        }

        if(action.equalsIgnoreCase("CrossSellInventoryReport")) {
            String fromDate = accountDto.getFromDate();
            String toDate = accountDto.getToDate();
            String sortBy = accountDto.getSortBy();
            String datesCombo = accountDto.getDatesCombo();
            ArrayList<EsalesPOJO> acList = EsalesDAO.crossSellInventoryReport(datesCombo, fromDate, toDate, sortBy, companyID, request, accountDto);
            request.setAttribute("acList", acList);
            forward = "reports/crossSellInventoryReport";
        }

        if(action.equalsIgnoreCase("ESaleSalesGraph")) {
            ac.eSaleSalesGraph(request, accountDto);
            forward = "reports/ESaleSalesGraph";
        }
        return forward;
    }
}
