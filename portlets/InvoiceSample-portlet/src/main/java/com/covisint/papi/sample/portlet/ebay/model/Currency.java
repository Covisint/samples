package com.covisint.papi.sample.portlet.ebay.model;

import com.google.gson.annotations.SerializedName;

public class Currency {
	@SerializedName("@currencyId")
	String currencyId;
	@SerializedName("__value__")
	String value;
	public String getCurrencyId() {
		return currencyId;
	}
	public String getValue() {
		return value;
	}
}
