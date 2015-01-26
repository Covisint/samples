package com.covisint.papi.sample.portlet.ebay.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class FindResponse {

	@SerializedName("findItemsByKeywordsResponse")
	ArrayList<ResponseObject> responseObjects;

	public ArrayList<ResponseObject> getResponseObjects() {
		return responseObjects;
	}
	
}
