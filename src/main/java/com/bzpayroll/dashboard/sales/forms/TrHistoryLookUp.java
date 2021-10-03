package com.bzpayroll.dashboard.sales.forms;


public class TrHistoryLookUp {

	public String invoiceId;

	public String orderNum;

	public String dateAdded;

	public String total;

	public String balance;

	public String name;

	public String finalTotal;

	/**
	 * @return Returns the balance.
	 */
	public String getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 *            The balance to set.
	 */
	public void setBalance(String balance) {
		this.balance = balance;
	}

	/**
	 * @return Returns the dateAdded.
	 */
	public String getDateAdded() {
		return dateAdded;
	}

	/**
	 * @param dateAdded
	 *            The dateAdded to set.
	 */
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	/**
	 * @return Returns the invoiceId.
	 */
	public String getInvoiceId() {
		return invoiceId;
	}

	/**
	 * @param invoiceId
	 *            The invoiceId to set.
	 */
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the orderNum.
	 */
	public String getOrderNum() {
		return orderNum;
	}

	/**
	 * @param orderNum
	 *            The orderNum to set.
	 */
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * @return Returns the total.
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            The total to set.
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return Returns the finalTotal.
	 */
	public String getFinalTotal() {
		return finalTotal;
	}

	/**
	 * @param finalTotal
	 *            The finalTotal to set.
	 */
	public void setFinalTotal(String finalTotal) {
		this.finalTotal = finalTotal;
	}

}
