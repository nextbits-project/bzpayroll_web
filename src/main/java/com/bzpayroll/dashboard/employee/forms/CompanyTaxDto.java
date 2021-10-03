/*
 * Author : Avibha IT Solutions Copyright 2006 Avibha IT Solutions. All rights
 * reserved. AVIBHA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.avibha.com
 */

package com.bzpayroll.dashboard.employee.forms;

public class CompanyTaxDto {

	private static final long serialVersionUID = 0;

	private String dname;

	private String drate;

	private String damount;

	private String taxExmp;

	private String isRate;

	private String ddId;

	private int srNo;

	public String getDamount() {
		return damount;
	}

	public void setDamount(String damount) {
		this.damount = damount;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getDrate() {
		return drate;
	}

	public void setDrate(String drate) {
		this.drate = drate;
	}

	public String getIsRate() {
		return isRate;
	}

	public void setIsRate(String isRate) {
		this.isRate = isRate;
	}

	public String getTaxExmp() {
		return taxExmp;
	}

	public void setTaxExmp(String taxExmp) {
		this.taxExmp = taxExmp;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getDdId() {
		return ddId;
	}

	public void setDdId(String ddId) {
		this.ddId = ddId;
	}
}
