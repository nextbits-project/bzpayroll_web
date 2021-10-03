package com.bzpayroll.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "bca_user")
public class BcaUser {
	
	    @Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="id")
	    private int id;
	 
	    @Column(name = "LoginID")
	    private String loginId;

	    @Column(name = "Password")
	    private String password;
	    
	    @Column(name = "Confirm_Password")
	    private String confirmPassword;
	    
	    @Column(name = "Email_Address")
	    private String emailAddress;
	    
	    @Column(name = "Company_Name")
	    private String companyName;
	    
	    @Column(name = "Legal_Name")
	    private String legalName;
	    
	    @Column(name = "TaxID")
	    private String taxId;
	    
	    @Column(name = "Address1")
	    private String address1;
	    
	    @Column(name = "Address2")
	    private String address2;
	    
	    @Column(name = "City")
	    private String city;
	    
	    @Column(name = "State")
	    private String state;
	    
	    @Column(name = "Zip")
	    private String zip;
	    
	    @Column(name = "Country")
	    private String country;
	    
	    @Column(name = "Phone")
	    private String phone;

		@Column(name = "CellPhone")
		private String cellPhone;
	    
	    @Column(name = "Fax")
	    private String fax;
	    
	    @Column(name = "Website")
	    private String website;
	    
	    @Column(name = "Province")
	    private String province;
	    
	    @Column(name = "Firstname")
	    private String firstName;
	    
	    @Column(name = "Lastname")
	    private String lastName;
	    
	    @Column(name = "passwordhint")
	    private String passwordHint;
	    
	    @Column(name = "passwordans")
	    private String passwordAns;
	    
	    @Column(name = "webaddress")
	    private String webAddress;
	    
	    @Column(name = "Active")
	    private int active;
	    
	    @Column(name = "CompanyID")
	    private int companyId;
	    
	    @Column(name = "membershiplevel")
	    private String membershipLevel;
	    
	   @Column(name = "jobposition")
	    private String jobPosition;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getLoginId() {
			return loginId;
		}

		public void setLoginId(String loginId) {
			this.loginId = loginId;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getConfirmPassword() {
			return confirmPassword;
		}

		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}

		public String getEmailAddress() {
			return emailAddress;
		}

		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getLegalName() {
			return legalName;
		}

		public void setLegalName(String legalName) {
			this.legalName = legalName;
		}

		public String getTaxId() {
			return taxId;
		}

		public void setTaxId(String taxId) {
			this.taxId = taxId;
		}

		public String getAddress1() {
			return address1;
		}

		public void setAddress1(String address1) {
			this.address1 = address1;
		}

		public String getAddress2() {
			return address2;
		}

		public void setAddress2(String address2) {
			this.address2 = address2;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getCellPhone() { return cellPhone; }

		public void setCellPhone(String cellPhone) { this.cellPhone = cellPhone; }

	public String getFax() {
			return fax;
		}

		public void setFax(String fax) {
			this.fax = fax;
		}

		public String getWebsite() {
			return website;
		}

		public void setWebsite(String website) {
			this.website = website;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		

		public String getPasswordHint() {
			return passwordHint;
		}

		public void setPasswordHint(String passwordHint) {
			this.passwordHint = passwordHint;
		}

		public String getPasswordAns() {
			return passwordAns;
		}

		public void setPasswordAns(String passwordAns) {
			this.passwordAns = passwordAns;
		}

		public String getWebAddress() {
			return webAddress;
		}

		public void setWebAddress(String webAddress) {
			this.webAddress = webAddress;
		}

		public String getJobPosition() {
			return jobPosition;
		}

		public void setJobPosition(String jobPosition) {
			this.jobPosition = jobPosition;
		}

		public int getActive() {
			return active;
		}

		public void setActive(int active) {
			this.active = active;
		}

		public int getCompanyId() {
			return companyId;
		}

		public void setCompanyId(int companyId) {
			this.companyId = companyId;
		}

		
		public String getMembershipLevel() {
			return membershipLevel;
		}

		public void setMembershipLevel(String membershipLevel) {
			this.membershipLevel = membershipLevel;
		}

		

}
