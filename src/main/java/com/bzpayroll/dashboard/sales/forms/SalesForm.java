/*
 * Author : Avibha IT Solutions Copyright 2007 Avibha IT Solutions. All rights
 * reserved. AVIBHA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.avibha.com
 */

package com.bzpayroll.dashboard.sales.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SalesForm {

	private static final long serialVersionUID = 0;

	private String billTo;

	private String titleID;

	private String shipCarrierID;

	private String shipCarrierName;

	private String title;

	private String salesRepID;

	private String salesRepName;

	private String cvCategoryID;

	private String cvCategoryName;

	private String termId;

	private String termName;

	private String locationId;

	private String locationName;

	private String paymentTypeId;

	private String paymentTypeName;

	private String ccTypeID;

	private String ccTypeName;

	private String messageID;

	private String messageName;

	private String salesTaxID;

	private String state;

	private String salesRate;

	/**
	 * @return the billTo
	 */
	public String getBillTo() {
		return billTo;
	}

	/**
	 * @param billTo the billTo to set
	 */
	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}

	/**
	 * @return the ccTypeID
	 */
	public String getCcTypeID() {
		return ccTypeID;
	}

	/**
	 * @param ccTypeID the ccTypeID to set
	 */
	public void setCcTypeID(String ccTypeID) {
		this.ccTypeID = ccTypeID;
	}

	/**
	 * @return the ccTypeName
	 */
	public String getCcTypeName() {
		return ccTypeName;
	}

	/**
	 * @param ccTypeName the ccTypeName to set
	 */
	public void setCcTypeName(String ccTypeName) {
		this.ccTypeName = ccTypeName;
	}

	/**
	 * @return the cvCategoryID
	 */
	public String getCvCategoryID() {
		return cvCategoryID;
	}

	/**
	 * @param cvCategoryID the cvCategoryID to set
	 */
	public void setCvCategoryID(String cvCategoryID) {
		this.cvCategoryID = cvCategoryID;
	}

	/**
	 * @return the cvCategoryName
	 */
	public String getCvCategoryName() {
		return cvCategoryName;
	}

	/**
	 * @param cvCategoryName the cvCategoryName to set
	 */
	public void setCvCategoryName(String cvCategoryName) {
		this.cvCategoryName = cvCategoryName;
	}

	/**
	 * @return the locationId
	 */
	public String getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * @return the messageID
	 */
	public String getMessageID() {
		return messageID;
	}

	/**
	 * @param messageID the messageID to set
	 */
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	/**
	 * @return the messageName
	 */
	public String getMessageName() {
		return messageName;
	}

	/**
	 * @param messageName the messageName to set
	 */
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	/**
	 * @return the paymentTypeId
	 */
	public String getPaymentTypeId() {
		return paymentTypeId;
	}

	/**
	 * @param paymentTypeId the paymentTypeId to set
	 */
	public void setPaymentTypeId(String paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	/**
	 * @return the paymentTypeName
	 */
	public String getPaymentTypeName() {
		return paymentTypeName;
	}

	/**
	 * @param paymentTypeName the paymentTypeName to set
	 */
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	/**
	 * @return the salesRate
	 */
	public String getSalesRate() {
		return salesRate;
	}

	/**
	 * @param salesRate the salesRate to set
	 */
	public void setSalesRate(String salesRate) {
		this.salesRate = salesRate;
	}

	/**
	 * @return the salesRepID
	 */
	public String getSalesRepID() {
		return salesRepID;
	}

	/**
	 * @param salesRepID the salesRepID to set
	 */
	public void setSalesRepID(String salesRepID) {
		this.salesRepID = salesRepID;
	}

	/**
	 * @return the salesRepName
	 */
	public String getSalesRepName() {
		return salesRepName;
	}

	/**
	 * @param salesRepName the salesRepName to set
	 */
	public void setSalesRepName(String salesRepName) {
		this.salesRepName = salesRepName;
	}

	/**
	 * @return the salesTaxID
	 */
	public String getSalesTaxID() {
		return salesTaxID;
	}

	/**
	 * @param salesTaxID the salesTaxID to set
	 */
	public void setSalesTaxID(String salesTaxID) {
		this.salesTaxID = salesTaxID;
	}

	/**
	 * @return the shipCarrierID
	 */
	public String getShipCarrierID() {
		return shipCarrierID;
	}

	/**
	 * @param shipCarrierID the shipCarrierID to set
	 */
	public void setShipCarrierID(String shipCarrierID) {
		this.shipCarrierID = shipCarrierID;
	}

	/**
	 * @return the shipCarrierName
	 */
	public String getShipCarrierName() {
		return shipCarrierName;
	}

	/**
	 * @param shipCarrierName the shipCarrierName to set
	 */
	public void setShipCarrierName(String shipCarrierName) {
		this.shipCarrierName = shipCarrierName;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * @param termId the termId to set
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	/**
	 * @return the termName
	 */
	public String getTermName() {
		return termName;
	}

	/**
	 * @param termName the termName to set
	 */
	public void setTermName(String termName) {
		this.termName = termName;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the titleID
	 */
	public String getTitleID() {
		return titleID;
	}

	/**
	 * @param titleID the titleID to set
	 */
	public void setTitleID(String titleID) {
		this.titleID = titleID;
	}

}
