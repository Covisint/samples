package com.covisint.papi.sample.portlet.ebay.model;

import java.util.ArrayList;

public class Item {
	ArrayList<String> itemId;
	ArrayList<String> title;
	ArrayList<String> globalId;
	ArrayList<PrimaryCategory> primaryCategory;
	ArrayList<String> galleryURL;
	ArrayList<String> viewItemURL;
	ArrayList<ProductId> productId;
	ArrayList<String> paymentMethod;
	ArrayList<Boolean> autoPay;
	ArrayList<String> postalCode;
	ArrayList<String> location;
	ArrayList<String> country;
	ArrayList<ShippingInfo> shippingInfo;
	ArrayList<SellingStatus> sellingStatus;
	ArrayList<ListingInfo> listingInfo;
	ArrayList<Boolean> returnsAccepted;
	ArrayList<Condition> condition;
	ArrayList<Boolean> isMultiVariationListing;
	ArrayList<Boolean> topRatedListing;
	public ArrayList<String> getItemId() {
		return itemId;
	}
	public ArrayList<String> getTitle() {
		return title;
	}
	public ArrayList<String> getGlobalId() {
		return globalId;
	}
	public ArrayList<PrimaryCategory> getPrimaryCategory() {
		return primaryCategory;
	}
	public ArrayList<String> getGalleryURL() {
		return galleryURL;
	}
	public ArrayList<String> getViewItemURL() {
		return viewItemURL;
	}
	public ArrayList<ProductId> getProductId() {
		return productId;
	}
	public ArrayList<String> getPaymentMethod() {
		return paymentMethod;
	}
	public ArrayList<Boolean> getAutoPay() {
		return autoPay;
	}
	public ArrayList<String> getPostalCode() {
		return postalCode;
	}
	public ArrayList<String> getLocation() {
		return location;
	}
	public ArrayList<String> getCountry() {
		return country;
	}
	public ArrayList<ShippingInfo> getShippingInfo() {
		return shippingInfo;
	}
	public ArrayList<SellingStatus> getSellingStatus() {
		return sellingStatus;
	}
	public ArrayList<ListingInfo> getListingInfo() {
		return listingInfo;
	}
	public ArrayList<Boolean> getReturnsAccepted() {
		return returnsAccepted;
	}
	public ArrayList<Condition> getCondition() {
		return condition;
	}
	public ArrayList<Boolean> getIsMultiVariationListing() {
		return isMultiVariationListing;
	}
	public ArrayList<Boolean> getTopRatedListing() {
		return topRatedListing;
	}
	
}
