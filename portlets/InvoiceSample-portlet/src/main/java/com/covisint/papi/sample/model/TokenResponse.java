package com.covisint.papi.sample.model;

public class TokenResponse {
	private long creation;
	private String access_token;
	private String expires_in;
	private String token_type;
	public long getCreation() {
		return creation;
	}
	public String getAccess_token() {
		return access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public String getToken_type() {
		return token_type;
	}
	
}
