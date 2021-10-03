package com.bzpayroll.dashboard.sales.controllers;

import com.bzpayroll.common.EmailSenderDto;
import com.bzpayroll.common.log.Loger;
import com.bzpayroll.dashboard.sales.dao.EstimationBoardDetails;
import com.bzpayroll.dashboard.sales.forms.EstimationBoardDto;
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
 * @author Sarfraz Malik
 */
@Controller
public class EstimationBoardController {
    @Autowired
    private EstimationBoardDetails ed ;


    @RequestMapping(value = {"/dashboard/EstimationBoard"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String execute(EstimationBoardDto estBoardDto, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException, ServletException {
        String forward = "/sales/Estimationboard";
        String action = request.getParameter("tabid");
        model.addAttribute("estimationBoardDto", estBoardDto);
        model.addAttribute("emailSenderDto", new EmailSenderDto());
        if (action.equalsIgnoreCase("ShowList")) {
            Loger.log("value from form ");
            ed.getEstimationBoardDetails(request, estBoardDto);
        }
        else if (action.equalsIgnoreCase("UpdateRecord")) {
            ed.updateRecord(request);
            ed.getEstimationBoardDetails(request, estBoardDto);
        }
        else if (action.equalsIgnoreCase("AllEstimationList")) {
            Loger.log("value from form ");
            ed.getEstimationBoardDetails(request, estBoardDto);
            forward = "/reports/allEstimationReport";
        }
        return forward;
    }
}
