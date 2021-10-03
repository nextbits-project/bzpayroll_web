/*
 * Author : Avibha IT Solutions Copyright 2006 Avibha IT Solutions. All rights
 * reserved. AVIBHA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.avibha.com
 */
package com.bzpayroll.dashboard.employee.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bzpayroll.common.log.Loger;
import com.bzpayroll.common.utility.CountryState;
import com.bzpayroll.common.utility.DateInfo;
import com.bzpayroll.common.utility.Path;
import com.bzpayroll.dashboard.employee.dao.Employee;
import com.bzpayroll.dashboard.employee.dao.EmployeeCount;
import com.bzpayroll.dashboard.employee.dao.EmployeeType;
import com.bzpayroll.dashboard.employee.dao.FilingState;
import com.bzpayroll.dashboard.employee.dao.FilingStatus;
import com.bzpayroll.dashboard.employee.dao.JobTitle;
import com.bzpayroll.dashboard.employee.dao.Label;
import com.bzpayroll.dashboard.employee.dao.PayrollPeriod;
import com.bzpayroll.dashboard.employee.dao.TimeSheet;
import com.bzpayroll.dashboard.employee.dao.Title;
import com.bzpayroll.dashboard.employee.forms.AddEmployeeForm;
import com.bzpayroll.dashboard.sales.dao.InvoiceInfo;

@Controller
public class ManageEmployeeController {

	@Autowired
	private InvoiceInfo invoice;
	@Autowired
	private Title title;

	@Autowired
	private JobTitle jobTitle;

	@Autowired
	private CountryState countryState;

	@Autowired
	private EmployeeType et;
	@Autowired
	private FilingStatus fs;

	@Autowired
	private FilingState fstate;

	@Autowired
	private PayrollPeriod pp;
	@Autowired
	private Employee employee;
	 
	 

	@RequestMapping(value = { "/dashboard/manageemployee" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView execute(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String forward = "employee/employee";
		Path p = new Path();
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		p.setPathvalue(request.getContextPath());
		request.getSession().setAttribute("path", p);
		if (request.getSession().isNew() || ((String) request.getSession().getAttribute("CID")) == null
				|| ((Path) request.getSession().getAttribute("path")) == null) {
			forward = "Expired";
		} else {
			String empID = request.getParameter("eid");
			// title List
			String act = request.getParameter("act");
			Loger.log("Action=" + act + "--" + "eid=" + empID);
			if ("add".equals(act) || "edit".equals(act)) {
				ArrayList titleList = title.getTitleList("1");
				request.setAttribute("titleList", titleList);
				// job title List
				ArrayList jobtitleList = jobTitle.getJobTitleList("1");
				request.setAttribute("jtitleList", jobtitleList);

				// country List
				ArrayList cList = countryState.getCountry();

				request.setAttribute("cList", cList);

				// Employee Type
				ArrayList emptype = et.getEmployeeTypeList("1");
				request.setAttribute("emptypeList", emptype);

				// Filing status

				ArrayList flist = fs.getFilingStatusList("1");
				request.setAttribute("filingList", flist);

				// State worked

				ArrayList fstatelist = fstate.getFilingStateList("1");
				request.setAttribute("statewList", fstatelist);

				// periodList

				ArrayList pList = pp.getPayrollPeriodList("1");
				request.setAttribute("periodList", pList);
				// action -add new employee
				if ("add".equals(act)) {
					AddEmployeeForm f = new AddEmployeeForm();
					String cdate = invoice.setCurrentDate();
					f.setDos(cdate);
					request.setAttribute("employee", f);
					forward = "/employee/addemployee";
				}

				// edit a employee
				if ("edit".equals(act)) {
					forward = "/employee/editemployee";

					AddEmployeeForm f = employee.getEmployee(empID);
					f.setStatus("U");
					request.setAttribute("employee", f);

				}
			}
			// terminate a employee
			if ("terminate".equals(act)) {

				employee.terminateEmployee(empID);
				forward = "redirect:/dashboard/employeelist";

			}
			// delete a employee
			if ("delete".equals(act)) {
				Loger.log("Deleting employee");
				employee.deleteEmployee(empID);
				forward = "redirect:employeelist?type=terminated";

			}

			// show timesheet page
			if ("timesheet".equals(act)) {

				String empid = request.getParameter("empid");
				ArrayList<TimeSheet> timesheet = employee.getTimeSheet(empid);
				ArrayList arr = employee.getEmployeeList(compId, "1");
				System.out.println("ListSize" + arr.size());
				request.setAttribute("empList", arr);
				forward = "/employee/timesheet";
			}
			// show printlabel page
			if ("print".equals(act)) {

				Label lbl = new Label();
				ArrayList labelList = lbl.getLabelList();
				request.setAttribute("Labels", labelList);
				request.setAttribute("lSize", labelList.size());
				forward = "/employee/printlabel";
			}

			// search employye
			if ("search".equals(act)) {
				ArrayList cList = countryState.getCountry();
				request.setAttribute("cList", cList);
				forward = "/employee/search";
			}

			// set no hired and no of terminated employees

			EmployeeCount ec = new EmployeeCount();
			ec = employee.getEmployeeCount("1");
			request.setAttribute("Count", ec);

		}
		ModelAndView modelAndView = new ModelAndView(forward);
		return modelAndView;
	}

	public String setCurrentDate() {

		DateInfo date = new DateInfo();
		int month = date.getMonth();
		int day = date.getDay();

		String da = "", d = "", m = "";
		if (month >= 1 && month <= 9) {
			m = "0" + month;
		} else
			m = "" + month;
		if (day >= 1 && day <= 9) {
			d = "0" + day;
		} else
			d = "" + day;
		da = m + "-" + d + "-" + (date.getYear());
		return da;
	}

	
	@RequestMapping(value = {"/dashboard/editemployee"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView editEmployee(AddEmployeeForm form, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String forward = "redirect:/dashboard/employee";
		System.out.println("inseide edit employee");
		if (request.getSession().isNew()
				|| ((String) request.getSession().getAttribute("CID")) == null
				|| ((Path) request.getSession().getAttribute("path")) == null) {
			forward = "Expired";
		} else {
			Loger.log("Edit action");
			AddEmployeeForm eform = (AddEmployeeForm) form;
 			String empid = eform.getEmployeeID();
			employee.updateEmployee(empid, eform);
			forward = "redirect:/dashboard/employeelist";
		}
		ModelAndView modelAndView =new ModelAndView(forward);
		return modelAndView;
	}
	
	@RequestMapping(value = {"/dashboard/addemployee"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView addEmployee( AddEmployeeForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String forward = "redirect:/dashboard/employee";
		if (request.getSession().isNew()
				|| ((String) request.getSession().getAttribute("CID")) == null
				|| ((Path) request.getSession().getAttribute("path")) == null) {
			forward = "Expired";
		} else {

			AddEmployeeForm eform = (AddEmployeeForm) form;
 			employee.insertEmployee(eform);
			// eform.reset(mapping, request);  
			//eform.reset(mapping, request);
		//	e.newEmployee(eform); //clear All fields
			forward = "redirect:/dashboard/employeelist";
		}
		ModelAndView modelAndView =new ModelAndView(forward);
		return modelAndView;
	}


	@ResponseBody
	@GetMapping(value = {"/GetState/{countryId}"})
	public ArrayList loadStateTaxOtherByYear(@PathVariable("countryId") Long stateId, HttpServletRequest request) {
		return countryState.getCStates(""	+ stateId);
 	}
	
	
	@ResponseBody
	@GetMapping(value = { "/CountryAndState/{zip}" })
	public String[] loadCountryAndState(@PathVariable("zip") String zip, HttpServletRequest request) {
		return countryState.getCityState(zip);
	}
}
