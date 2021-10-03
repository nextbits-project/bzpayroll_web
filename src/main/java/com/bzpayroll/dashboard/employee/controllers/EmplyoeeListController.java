package com.bzpayroll.dashboard.employee.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bzpayroll.common.utility.CountryState;
import com.bzpayroll.common.utility.Path;
import com.bzpayroll.dashboard.employee.dao.Employee;
import com.bzpayroll.dashboard.employee.dao.EmployeeType;
import com.bzpayroll.dashboard.employee.dao.FilingState;
import com.bzpayroll.dashboard.employee.dao.FilingStatus;
import com.bzpayroll.dashboard.employee.dao.JobTitle;
import com.bzpayroll.dashboard.employee.dao.PayrollPeriod;
import com.bzpayroll.dashboard.employee.dao.Title;
import com.bzpayroll.dashboard.employee.forms.AddEmployeeForm;

@Controller
public class EmplyoeeListController {

	@Autowired
	private Employee employeeDAL;

	@Autowired
	private Title titleDAL;

	@Autowired
	private JobTitle jobTitleDAL;

	@Autowired
	private CountryState cs;

	@Autowired
	private EmployeeType et;
	
	@Autowired
	private FilingStatus fs;
	
	@Autowired
	private FilingState fstate;
	
	@Autowired
	private PayrollPeriod pp;

	@RequestMapping(value = { "/dashboard/employeelist" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String forward = "/employee/htemployeelist";
 
		String type = request.getParameter("type");
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		Path p = new Path();
		p.setPathvalue(request.getContextPath());
		request.getSession().setAttribute("path", p);
		if (((String) request.getSession().getAttribute("CID")) == null
				|| ((Path) request.getSession().getAttribute("path")) == null) {
			// forward = "Expired";
		} else if (type == null) {
			/* ArrayList arr = e.getEmployeeList("1", "1"); */ // commented on 26-06-2019
			ArrayList<?> arr = employeeDAL.getEmployeeList(compId, "1");
			if (arr.size() > 0)
				request.setAttribute("empList", arr);

			ArrayList titleList = titleDAL.getTitleList(compId);
			request.setAttribute("titleList", titleList);

			// job title List
			ArrayList jobtitleList = jobTitleDAL.getJobTitleList("1");
			request.setAttribute("jtitleList", jobtitleList);

			// country List
			ArrayList cList = cs.getCountry();

			request.setAttribute("cList", cList);

			// Employee Type
			ArrayList emptype = et.getEmployeeTypeList(compId);
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

			forward = "/employee/htemployeelist";
		} else if ("hired".equals(type)) {
			/* ArrayList arr = e.getEmployeeList("1", "1"); */ // commented on 26-06-2019
			ArrayList<?> arr = employeeDAL.getEmployeeList(compId, "1");
			if (arr.size() > 0)
				request.setAttribute("empList", arr);
			forward = "/employee/hired";
		} else if ("hiredDetails".equals(type)) {

			Title t = new Title();
			ArrayList titleList = t.getTitleList(compId);
			request.setAttribute("titleList", titleList);

			// country List
			CountryState cs = new CountryState();
			ArrayList cList = cs.getCountry();

			request.setAttribute("cList", cList);

			// Employee Type
			EmployeeType et = new EmployeeType();
			ArrayList emptype = et.getEmployeeTypeList(compId);
			request.setAttribute("emptypeList", emptype);

			// Filing status
			FilingStatus fs = new FilingStatus();
			ArrayList flist = fs.getFilingStatusList("1");
			request.setAttribute("filingList", flist);

			// State worked
			FilingState fstate = new FilingState();
			ArrayList fstatelist = fstate.getFilingStateList("1");
			request.setAttribute("statewList", fstatelist);

			// periodList
			PayrollPeriod pp = new PayrollPeriod();
			ArrayList pList = pp.getPayrollPeriodList("1");
			request.setAttribute("periodList", pList);

			ArrayList<?> arr1 = employeeDAL.getEmployeeList(compId, "1");
			if (arr1.size() > 0)
				request.setAttribute("empList", arr1);

			String employeeID = request.getParameter("emid");
			String rowId = request.getParameter("SelectedRID");
			ArrayList<?> arr = employeeDAL.getEmployeeDetails(compId, "1", employeeID);
			if (arr.size() > 0)
				request.setAttribute("empList1", arr);
			forward = "/employee/hired";
		} else if ("terminated".equals(type)) {
			/* ArrayList arr = e.getEmployeeList("1", "0"); */ // commented on 26-06-2019
			ArrayList<?> arr = employeeDAL.getEmployeeList(compId, "0");
			if (arr.size() > 0)
				request.setAttribute("empList", arr);
			forward = "/employee/terminated";
		} else if ("terminatedDetails".equals(type)) {

			Title t = new Title();
			ArrayList titleList = t.getTitleList(compId);
			request.setAttribute("titleList", titleList);

			// country List
			CountryState cs = new CountryState();
			ArrayList cList = cs.getCountry();

			request.setAttribute("cList", cList);

			// Employee Type
			EmployeeType et = new EmployeeType();
			ArrayList emptype = et.getEmployeeTypeList(compId);
			request.setAttribute("emptypeList", emptype);

			// Filing status
			FilingStatus fs = new FilingStatus();
			ArrayList flist = fs.getFilingStatusList("1");
			request.setAttribute("filingList", flist);

			// State worked
			FilingState fstate = new FilingState();
			ArrayList fstatelist = fstate.getFilingStateList("1");
			request.setAttribute("statewList", fstatelist);

			// periodList
			PayrollPeriod pp = new PayrollPeriod();
			ArrayList pList = pp.getPayrollPeriodList("1");
			request.setAttribute("periodList", pList);

			ArrayList<?> arr1 = employeeDAL.getEmployeeList(compId, "0");
			if (arr1.size() > 0)
				request.setAttribute("empList", arr1);

			String employeeID = request.getParameter("emid");
			String rowId = request.getParameter("SelectedRID");
			ArrayList<?> arr = employeeDAL.getEmployeeDetails(compId, "0", employeeID);
			if (arr.size() > 0)
				request.setAttribute("empList1", arr);
			forward = "/employee/terminated";
		}

		return forward;
	}

	@ResponseBody
	@PostMapping("/dashboard/EmployeeAjax")
	public Object EmployeeAjaxCall(AddEmployeeForm addEmployeeForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String action = request.getParameter("tabid");
		HttpSession sess = request.getSession();
		String compId = (String) sess.getAttribute("CID");
		String status = "Success";
		ArrayList<AddEmployeeForm> arr = new ArrayList<>();

		if (action.equalsIgnoreCase("sortHired")) {
			System.out.println("------------sortInvoice-------000------");
			int sortById = Integer.parseInt(request.getParameter("SortBy"));
			String cvId = request.getParameter("cvId");
			String rowId = request.getParameter("SelectedRID");
			arr = employeeDAL.getSortedEmployee(request, addEmployeeForm, sortById, "1");
			request.setAttribute("sortById", sortById);
			System.out.println("SortBy:" + sortById);
			return arr;
		} else if (action.equalsIgnoreCase("sortTerminated")) {
			System.out.println("------------sortInvoice-------000------");
			int sortById = Integer.parseInt(request.getParameter("SortBy"));
			String cvId = request.getParameter("cvId");
			String rowId = request.getParameter("SelectedRID");
			arr = employeeDAL.getSortedEmployee(request, addEmployeeForm, sortById, "2");
			request.setAttribute("sortById", sortById);
			System.out.println("SortBy:" + sortById);
			return arr;
		} else if (action.equalsIgnoreCase("loadAll")) {
			int sortById = addEmployeeForm.getSortBy();
			arr = employeeDAL.getSortedEmployee(request, addEmployeeForm, sortById, null);
			return arr;
		} else if (action.equalsIgnoreCase("loadHired")) {
			int sortById = addEmployeeForm.getSortBy();
			arr = employeeDAL.getSortedEmployee(request, addEmployeeForm, sortById, "1");
			return arr;
		} else if (action.equalsIgnoreCase("loadTerminated")) {
			int sortById = addEmployeeForm.getSortBy();
			arr = employeeDAL.getSortedEmployee(request, addEmployeeForm, sortById, "0");
			return arr;
		} else if (action.equalsIgnoreCase("loadEmployee")) {
			int sortById = addEmployeeForm.getSortBy();
			arr = employeeDAL.getEmployeeDetails(compId, null, addEmployeeForm.getEmployeeID());
			return arr;
		}
		return arr;
	}
}
