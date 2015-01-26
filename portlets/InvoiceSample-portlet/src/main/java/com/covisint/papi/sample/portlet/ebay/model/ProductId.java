package com.covisint.papi.sample.portlet.ebay.model;

import com.google.gson.annotations.SerializedName;

public class ProductId {
	@SerializedName("@type")
	String type;
	@SerializedName("__value__")
	String value;
}
