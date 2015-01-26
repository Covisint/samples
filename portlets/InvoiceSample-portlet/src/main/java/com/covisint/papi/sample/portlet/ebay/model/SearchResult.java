package com.covisint.papi.sample.portlet.ebay.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SearchResult {
	@SerializedName("@count")
	String count;
	ArrayList<Item> item;
	public String getCount() {
		return count;
	}
	public ArrayList<Item> getItem() {
		return item;
	}
}
