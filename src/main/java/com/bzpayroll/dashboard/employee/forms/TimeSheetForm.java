package com.bzpayroll.dashboard.employee.forms;

import javax.servlet.http.HttpServletRequest;

import com.bzpayroll.common.log.Loger;

public class TimeSheetForm {

	private static final long serialVersionUID = 0;

	private String empid;

	private String timedata;

	private String datedata;

	/**
	 * @return Returns the datedata.
	 */
	public String getDatedata() {
		return datedata;
	}

	/**
	 * @param datedata The datedata to set.
	 */
	public void setDatedata(String datedata) {
		this.datedata = datedata;
	}

	/**
	 * @return Returns the empid.
	 */
	public String getEmpid() {
		return empid;
	}

	/**
	 * @param empid The empid to set.
	 */
	public void setEmpid(String empid) {
		this.empid = empid;
	}

	/**
	 * @return Returns the timedata.
	 */
	public String getTimedata() {
		return timedata;
	}

	/**
	 * @param timedata The timedata to set.
	 */
	public void setTimedata(String timedata) {
		this.timedata = timedata;
	}

}
