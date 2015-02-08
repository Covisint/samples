package com.covisint.papi.sample.android.openregistration.util;

import com.covisint.papi.sample.android.openregistration.model.PAPIModel;

import org.apache.http.StatusLine;

/**
 * Created by Nitin.Khobragade on 2/6/2015.
 */
public class NetworkResponse {
    StatusLine statusLine;
    PAPIModel[] response;
    String rawData;

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public PAPIModel[] getResponse() {
        return response;
    }

    public void setResponse(PAPIModel[] response) {
        this.response = response;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }
}
