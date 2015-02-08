package com.covisint.papi.sample.android.openregistration.model.error;

/**
 * Created by Nitin.Khobragade on 2/8/2015.
 */
public class Error {
    int status;
    String apiMessage;
    String apiStatusCode;

    public int getStatus() {
        return status;
    }

    public String getApiMessage() {
        return apiMessage;
    }

    public String getApiStatusCode() {
        return apiStatusCode;
    }
}
