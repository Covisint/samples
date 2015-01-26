package com.covisint.papi.sample.portlet.ebay.model;

import java.util.ArrayList;

public class SellingStatus {
	ArrayList<Currency> currentPrice;
	ArrayList<Currency> convertedCurrentPrice;
	ArrayList<String> bidCount;
	ArrayList<String> sellingState;
	ArrayList<String> timeLeft;
	public ArrayList<Currency> getCurrentPrice() {
		return currentPrice;
	}
	public ArrayList<Currency> getConvertedCurrentPrice() {
		return convertedCurrentPrice;
	}
	public ArrayList<String> getBidCount() {
		return bidCount;
	}
	public ArrayList<String> getSellingState() {
		return sellingState;
	}
	public ArrayList<String> getTimeLeft() {
		return timeLeft;
	}
}
