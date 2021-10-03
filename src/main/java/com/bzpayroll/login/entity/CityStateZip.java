package com.bzpayroll.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city_state_zip")
public class CityStateZip {
	
	@Id
	@Column(name = "zip_code")
    private int zipcode;
	
	 @Column(name = "city_name")
	 private String cityName;
	 
	 @Column(name = "state_name")
	 private String stateName;
	 
	 @Column(name = "state_code")
	 private String stateCode;

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	 
	 

}
