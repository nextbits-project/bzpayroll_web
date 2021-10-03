package com.bzpayroll.common.utility;

public class LabelValueBean {

	private String label;

	private String value;

	public LabelValueBean(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public String getLabel() {
		return this.label;
	}
}
