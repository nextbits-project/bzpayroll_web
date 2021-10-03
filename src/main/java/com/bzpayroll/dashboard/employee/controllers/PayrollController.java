package com.bzpayroll.dashboard.employee.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bzpayroll.dashboard.employee.dao.Employee;
import com.bzpayroll.dashboard.employee.dao.PayrollPeriod;
import com.bzpayroll.dashboard.employee.dao.PdfGeneration;
import com.bzpayroll.dashboard.employee.dao.TimeSheet;
import com.bzpayroll.dashboard.employee.forms.AddEmployeeForm;
import com.bzpayroll.dashboard.employee.forms.PayrollDto;
import com.lowagie.text.DocumentException;

@Controller
public class PayrollController {

	@Autowired
	private PayrollPeriod payrollPeriod;
	
	@Autowired
	private Employee employee;

	@RequestMapping(value = {"/dashboard/Payroll"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView Payroll(ModelMap model, PayrollDto payrollDto, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String forward = "redirect:/employee";
		String action = request.getParameter("tabid");
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		if (request.getSession().isNew()
				|| ((String) request.getSession().getAttribute("CID")) == null) {
			forward = "Expired";
		} else if(action.equalsIgnoreCase("Payroll")) {
			// periodList
			ArrayList pList = payrollPeriod.getPayrollPeriodList("1");
			request.setAttribute("periodList", pList);
 			ArrayList<?> arr = employee.getEmployeeList(compId, "1");
			if (arr.size() > 0)
				request.setAttribute("empList", arr);
			forward = "/employee/createpayroll";
		} else if(action.equalsIgnoreCase("createList")) {
			String month[]={"month","January" ,"February" ,"March" ,"April" ,"May" ,"June" ,"July" ,"August" ,"September" ,"October" ,"November" ,"December"};
			ArrayList<?> arr = employee.getEmployeeList(compId, "1");
			if (arr.size() > 0)
				request.setAttribute("empList", arr);

			boolean CheckPayroll = employee.CheckPayroll(payrollDto, compId);
			if (CheckPayroll){
				model.addAttribute("status", false);
				model.addAttribute("message", "You already Created for "+month[Integer.parseInt(payrollDto.getMonth())]+" "+payrollDto.getYear()+" Payroll");
			}else{
				boolean check = employee.createPayroll(payrollDto, compId);
				model.addAttribute("status", true);
				model.addAttribute("message",month[Integer.parseInt(payrollDto.getMonth())]+" "+payrollDto.getYear()+" Payroll Created Successfully");
			}
			forward = "/employee/createpayroll";
		}else if(action.equalsIgnoreCase("payrollList")) {
			ArrayList<PayrollDto> arr = employee.getPayrollList(compId);
			if (arr.size() > 0)
				request.setAttribute("payrollList", arr);


			forward = "/employee/payrolllist";
		}else if(action.equalsIgnoreCase("loadpayrollList")) {
			String week = request.getParameter("week");
			String month = request.getParameter("month");
			String year = request.getParameter("year");
 			ArrayList<PayrollDto> arr = employee.getPayrollList(compId,week,month,year);
			if (arr.size() > 0)
				request.setAttribute("payrollList", arr);


			forward = "/employee/payrolllist";
		}
		else if(action.equalsIgnoreCase("deletePayroll")) {
			String PayrollID = request.getParameter("payrollid");
 			boolean c = employee.deletePayroll(compId, PayrollID);
			model.addAttribute("message","Payroll Deleted Successfully");
			forward = "/employee/payrolllist";
		}

		ModelAndView modelAndView =new ModelAndView(forward);
		return modelAndView;
	}


	@RequestMapping(value = {"/dashboard/GetPayroll"}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ArrayList<PayrollDto> execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ParseException {
 		String empid = request.getParameter("empid");
		String payperiodid = request.getParameter("payperiodid");
		String week = request.getParameter("week");
		String month = request.getParameter("month");
		String year = request.getParameter("year");
 		String day = request.getParameter("day");


		String startDate = null;
		String endDate = null;
		if(!StringUtils.isEmpty(day)){
			startDate = year+"-"+month+"-"+day;
			endDate = year+"-"+month+"-"+day;
		}else if(!StringUtils.isEmpty(week)){
			startDate = year+"-"+month+"-01";
			endDate = year+"-"+month+"-31";
		}else {
			startDate = year+"-"+month+"-01";
			endDate = year+"-"+month+"-31";
		}

		ArrayList<PayrollDto> payroll = employee.findPayroll(empid, payperiodid, startDate, endDate);
		System.out.println(payroll);
		return payroll;
	}

	@RequestMapping(value = {"/dashboard/DownloadPayroll"}, method = {RequestMethod.GET, RequestMethod.POST})
	public void exportToPDF(HttpServletRequest request, HttpServletResponse response) throws DocumentException, IOException, ServletException, ParseException  {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Payroll_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);
		HttpSession sess = request.getSession();
		String ComapnyID = (String) sess.getAttribute("CID");
		String PayrollID = request.getParameter("payrollID");

 		ArrayList<PayrollDto> payrollDetails = employee.downloadPayroll(PayrollID,ComapnyID);
		ArrayList<TimeSheet> timeSheetList = employee.getPayrollTimeSheet(ComapnyID,payrollDetails.get(0).getEmployeeID(),payrollDetails.get(0).getYear(), payrollDetails.get(0).getMonth());
		ArrayList<AddEmployeeForm> employeeDetails = employee.getPayrollEmployeeDetails(ComapnyID, payrollDetails.get(0).getEmployeeID());
		PdfGeneration exporter = new PdfGeneration(payrollDetails,employeeDetails,timeSheetList);
		exporter.generatePdfForPayRoll(response);

	}
}
