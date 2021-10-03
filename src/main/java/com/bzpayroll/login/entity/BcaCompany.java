package com.bzpayroll.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bca_company")
public class BcaCompany {
	
	 	@Id
	 	@Column(name = "CompanyID")
	    private int companyId;
	 	
	 	@Column(name = "Name")
	    private String name;
	    
	    @Column(name = "NickName")
	    private String nickName;	 	
	 	
	 	@Column(name = "FirstName")
	    private String firstName;
	    
	    @Column(name = "LastName")
	    private String lastName;
	    
	    @Column(name = "Detail")
	    private String detail;
	    
	    @Column(name = "Address1")
	    private String address1;
	    
	    @Column(name = "Address2")
	    private String address2;
	    
	    @Column(name = "City")
	    private String city;
	    
	    @Column(name = "State")
	    private String state;
	    
	    @Column(name = "Province")
	    private String province;
	   
	    @Column(name = "Country")
	    private String country;
	    
	    @Column(name = "Zipcode")
	    private String zipCode;
	    
	    @Column(name = "Phone1")
	    private String phone1;
	    
	    @Column(name = "Phone2")
	    private String phone2;
	    
	    @Column(name = "Fax1")
	    private String fax1;
	    
	    @Column(name = "Fax2")
	    private String fax2;
	    
	    @Column(name = "Email")
	    private String email;
	    
	    @Column(name = "Active")
	    private int active;
	    
	    @Column(name = "iscreated")
	    private int isCreated;
	    
	    @Column(name = "businesstypeid")
	    private int businessTypeId;
	    

		public int getActive() {
			return active;
		}

		public void setActive(int active) {
			this.active = active;
		}

		public int getIsCreated() {
			return isCreated;
		}

		public void setIsCreated(int isCreated) {
			this.isCreated = isCreated;
		}

		public int getBusinessTypeId() {
			return businessTypeId;
		}

		public void setBusinessTypeId(int businessTypeId) {
			this.businessTypeId = businessTypeId;
		}

		public int getCompanyId() {
			return companyId;
		}

		public void setCompanyId(int companyId) {
			this.companyId = companyId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
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

		public String getDetail() {
			return detail;
		}

		public void setDetail(String detail) {
			this.detail = detail;
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

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getZipCode() {
			return zipCode;
		}

		public void setZipCode(String zipCode) {
			this.zipCode = zipCode;
		}

		public String getPhone1() {
			return phone1;
		}

		public void setPhone1(String phone1) {
			this.phone1 = phone1;
		}

		public String getPhone2() {
			return phone2;
		}

		public void setPhone2(String phone2) {
			this.phone2 = phone2;
		}

		public String getFax1() {
			return fax1;
		}

		public void setFax1(String fax1) {
			this.fax1 = fax1;
		}

		public String getFax2() {
			return fax2;
		}

		public void setFax2(String fax2) {
			this.fax2 = fax2;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	    

}
