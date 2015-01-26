package com.covisint.papi.sample.portlet.ebay.model;

import com.google.gson.annotations.SerializedName;

public class ShippingServiceCost {
	@SerializedName("@currencyId")
	String currencyId;
	@SerializedName("__value__")
	String value;
}
