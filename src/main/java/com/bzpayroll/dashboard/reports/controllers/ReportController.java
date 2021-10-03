package com.bzpayroll.dashboard.reports.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bzpayroll.common.EmailSenderDto;
import com.bzpayroll.common.utility.Utility;
import com.bzpayroll.dashboard.accounting.forms.AccountDto;
import com.bzpayroll.dashboard.employee.dao.Employee;
import com.bzpayroll.dashboard.employee.forms.PayrollDto;
import com.bzpayroll.dashboard.reports.forms.ReportDto;
import com.bzpayroll.dashboard.sales.forms.SalesBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class ReportController {

	@Autowired
	private Employee employee;

 	@RequestMapping(value = { "/dashboard/Reports" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String reports(ReportDto reportDto, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String forward = "reports/reportcenter";
		String action = request.getParameter("tabid");
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		if (action.equalsIgnoreCase("ReportsCenter")) {
			forward = "reports/newreportcenter";
		}else if(action.equalsIgnoreCase("Hired")) {
			SalesBoardDto dto = new SalesBoardDto();
			dto.setFromDate("");
			dto.setToDate("");
			model.addAttribute("salesBoardDto", reportDto);
			model.addAttribute("emailSenderDto", new EmailSenderDto());
			employee.getEmployee(request, reportDto,"1");
			forward = "/reports/employeehired";
		}
		else if(action.equalsIgnoreCase("Terminated")) {

			model.addAttribute("salesBoardDto",reportDto);
			model.addAttribute("emailSenderDto", new EmailSenderDto());
			employee.getEmployee(request, reportDto,"2");
			forward = "/reports/employeeterminated";
		}
		else if(action.equalsIgnoreCase("Monthly")) {
			LocalDate currentdate = LocalDate.now();
			if(reportDto.getMonth() == null || reportDto.getMonth().isEmpty()){
					reportDto.setMonth(Integer.toString(currentdate.getMonthValue()));
			}

			if(reportDto.getYear() == null || reportDto.getYear().isEmpty()){
				reportDto.setYear(Integer.toString(currentdate.getYear()));
			}

			ArrayList<PayrollDto> arr = employee.getPayrollListMonthly(compId, Utility.getMonth(Integer.parseInt(reportDto.getMonth())),Integer.toString(currentdate.getYear()));
			model.addAttribute("salesBoardDto", reportDto);
			model.addAttribute("emailSenderDto", new EmailSenderDto());
 			request.setAttribute("CustomerDetails", arr);

			forward = "/reports/payrollmonthly";
		}
		else if(action.equalsIgnoreCase("Weekly")) {

			LocalDate currentdate = LocalDate.now();
			if(reportDto.getMonth() == null || reportDto.getMonth().isEmpty()){
				reportDto.setMonth(Integer.toString(currentdate.getMonthValue()));
			}

			if(reportDto.getYear() == null || reportDto.getYear().isEmpty()){
				reportDto.setYear(Integer.toString(currentdate.getYear()));
			}

			ArrayList<PayrollDto> arr = employee.getPayrollListMonthly(compId, Utility.getMonth(Integer.parseInt(reportDto.getMonth())),Integer.toString(currentdate.getYear()));
			model.addAttribute("salesBoardDto", reportDto);
			model.addAttribute("emailSenderDto", new EmailSenderDto());
			request.setAttribute("CustomerDetails", arr);

			forward = "/reports/payrollweekly";
		}
		else if(action.equalsIgnoreCase("Quarterly")) {

			LocalDate currentdate = LocalDate.now();
			if(reportDto.getMonth() == null || reportDto.getMonth().isEmpty()){
				reportDto.setMonth(Integer.toString(currentdate.getMonthValue()));
			}

			if(reportDto.getYear() == null || reportDto.getYear().isEmpty()){
				reportDto.setYear(Integer.toString(currentdate.getYear()));
			}

			ArrayList<PayrollDto> arr = employee.getPayrollListMonthly(compId, Utility.getMonth(Integer.parseInt(reportDto.getMonth())),Integer.toString(currentdate.getYear()));
			model.addAttribute("salesBoardDto", reportDto);
			model.addAttribute("emailSenderDto", new EmailSenderDto());
			request.setAttribute("CustomerDetails", arr);

			forward = "/reports/payrollquarterly";
		}
		else if(action.equalsIgnoreCase("Yearly")) {

			LocalDate currentdate = LocalDate.now();


			if(reportDto.getYear() == null || reportDto.getYear().isEmpty()){
				reportDto.setYear(Integer.toString(currentdate.getYear()));
			}

			ArrayList<PayrollDto> arr = employee.getPayrollListYearly(compId, Integer.toString(currentdate.getYear()));
			model.addAttribute("salesBoardDto", reportDto);
			model.addAttribute("emailSenderDto", new EmailSenderDto());
			request.setAttribute("CustomerDetails", arr);
			forward = "/reports/payrollyearly";
		}
		return forward;
	}


}
