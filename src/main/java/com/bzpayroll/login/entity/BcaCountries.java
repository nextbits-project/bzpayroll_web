package com.bzpayroll.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bca_countries")
public class BcaCountries {
	@Id
	@Column(name = "id")
    private int id;
	
	 @Column(name = "sortname")
	 private String sortName;
	 
	 @Column(name = "name")
	 private String name;
	 
	 @Column(name = "phonecode")
	 private int phoneCode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(int phoneCode) {
		this.phoneCode = phoneCode;
	}

}
