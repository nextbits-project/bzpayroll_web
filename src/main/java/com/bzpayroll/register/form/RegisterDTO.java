package com.bzpayroll.register.form;

public class RegisterDTO {

	private String first_name;
	private String last_name;
	private String swpm_registration_submit;
	private String email;
	private String loginName;
	private String password;
	private String password_re;
	private String passwordAns;
	private String company_name;
	private String user_name;
	private String address_street;
	private String address_street2;

	//private String province;
	private String address_zipcode;
	private String address_city;
	private String phone;
	private String mobile;
	private String fax;
	private String taxId;

	
	private String  membership_levelDr; 
	private String positionDr;
	private String passwordhintDr;
	private String address_stateDr;
	private String countryDr;

	public String getSwpm_registration_submit() {
		return swpm_registration_submit;
	}

	public void setSwpm_registration_submit(String swpm_registration_submit) {
		this.swpm_registration_submit = swpm_registration_submit;
	}

	public RegisterDTO() {

	}

	public RegisterDTO(String first_name, String last_name, String swpm_registration_submit, String email,
			String loginName, String password, String password_re, String passwordAns, String company_name,
			String user_name, String address_street, String address_street2, String province, String address_zipcode,
			String address_city, String phone, String mobile, String fax, String taxId,
			String membership_levelDr, String positionDr, String passwordhintDr, String address_stateDr, String countryDr) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.loginName = loginName;
		this.password = password;
		this.password_re = password_re;
		this.passwordAns = passwordAns;
		this.company_name = company_name;
		this.user_name = user_name;
		this.address_street = address_street;
		this.address_street2 = address_street2;
		//this.province = province;
		this.address_zipcode = address_zipcode;
		this.address_city = address_city;
		this.phone = phone;
		this.mobile = mobile;
		this.fax = fax;
		this.taxId = taxId;
		this.swpm_registration_submit = swpm_registration_submit;
		this.membership_levelDr = membership_levelDr;
		this.positionDr = positionDr;
		this.passwordhintDr = passwordhintDr;
		this.address_stateDr = address_stateDr;
		this.countryDr = countryDr;
		
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword_re() {
		return password_re;
	}

	public void setPassword_re(String password_re) {
		this.password_re = password_re;
	}

	public String getPasswordAns() {
		return passwordAns;
	}

	public void setPasswordAns(String passwordAns) {
		this.passwordAns = passwordAns;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getAddress_street() {
		return address_street;
	}

	public void setAddress_street(String address_street) {
		this.address_street = address_street;
	}

	public String getAddress_street2() {
		return address_street2;
	}

	public void setAddress_street2(String address_street2) {
		this.address_street2 = address_street2;
	}

//	public String getProvince() {
//		return province;
//	}
//
//	public void setProvince(String province) {
//		this.province = province;
//	}

	public String getAddress_zipcode() {
		return address_zipcode;
	}

	public void setAddress_zipcode(String address_zipcode) {
		this.address_zipcode = address_zipcode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getAddress_city() {
		return address_city;
	}

	public void setAddress_city(String address_city) {
		this.address_city = address_city;
	}

	public String getMembership_levelDr() {
		return membership_levelDr;
	}

	public void setMembership_levelDr(String membership_levelDr) {
		this.membership_levelDr = membership_levelDr;
	}

	public String getPositionDr() {
		return positionDr;
	}

	public void setPositionDr(String positionDr) {
		this.positionDr = positionDr;
	}

	public String getPasswordhintDr() {
		return passwordhintDr;
	}

	public void setPasswordhintDr(String passwordhintDr) {
		this.passwordhintDr = passwordhintDr;
	}

	public String getAddress_stateDr() {
		return address_stateDr;
	}

	public void setAddress_stateDr(String address_stateDr) {
		this.address_stateDr = address_stateDr;
	}

	public String getCountryDr() {
		return countryDr;
	}

	public void setCountryDr(String countryDr) {
		this.countryDr = countryDr;
	}

	@Override
	public String toString() {
		return String.format(
				// "FirstName: %d<br>LastNAme: %s<br>Surname: %s<br>Check In Date: %s<br>Check
				// Out Date: %s<br>E-mail: %s<br>Room ID: %d<br>",
	"FirstName: %d<br>LastName: %s<br>Email: %s<br>LoginName: %s<br>Password: %s<br>"
	+ "Confirm Password: %d<br>Comapny Name: %s<br>Nickname: %s<br>UserName: %s<br>AddressStreet: %s<br>"
	+ "AddresStreet2: %d<br>Province: %s<br>Zipcode: %s<br>City: %s<br>AddressStreet: %s<br>"
	+ "MemberShipLEvel: %d<br>Job Position: %s<br>Password Hint: %s<br>State: %s<br>Country: %s<br>" 
	+ "Phone: %s<br>Mobile: %d<br> ", first_name, last_name,email,
				loginName,
				password,
				password_re,
				passwordAns,
				company_name,
				user_name,
				address_street,
				address_street2,
				address_zipcode,
				address_city,
				phone,
				mobile,
				membership_levelDr,
		        positionDr,
		        passwordhintDr, 
		        address_stateDr,
		        countryDr,
				fax,
				taxId, swpm_registration_submit);

	}

}
