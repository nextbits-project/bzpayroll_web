package com.bzpayroll.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bca_preference")
public class BcaPreference {

	@Id
	@Column(name = "PreferenceID")
	private int preferenceId;

	@Column(name = "CompanyID")
	private int companyId;

	@Column(name = "companylogopath")
	private String companyLogoPath;

	public int getPreferenceId() {
		return preferenceId;
	}

	public void setPreferenceId(int preferenceId) {
		this.preferenceId = preferenceId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyLogoPath() {
		return companyLogoPath;
	}

	public void setCompanyLogoPath(String companyLogoPath) {
		this.companyLogoPath = companyLogoPath;
	}

	
	
}
