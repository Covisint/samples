package com.covisint.papi.sample.portlet.ebay.model;

import java.util.ArrayList;

public class ResponseObject {
	ArrayList<String> ack;
	ArrayList<String> version;
	ArrayList<String> timestamp;
	ArrayList<SearchResult> searchResult;
	ArrayList<PaginationOutput> paginationOutput;
	ArrayList<String> itemSearchURL;
	public ArrayList<String> getAck() {
		return ack;
	}
	public ArrayList<String> getVersion() {
		return version;
	}
	public ArrayList<String> getTimestamp() {
		return timestamp;
	}
	public ArrayList<SearchResult> getSearchResult() {
		return searchResult;
	}
	public ArrayList<PaginationOutput> getPaginationOutput() {
		return paginationOutput;
	}
	public ArrayList<String> getItemSearchURL() {
		return itemSearchURL;
	}
}
