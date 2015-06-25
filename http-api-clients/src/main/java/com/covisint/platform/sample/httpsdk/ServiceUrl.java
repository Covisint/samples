/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk;

public enum ServiceUrl {

    PERSON_V1("https://api.covapp.io/person/v1"),

    GROUP_V1("https://api.covapp.io/group/v1"),

    ORG_V1("https://api.covapp.io/organization/v1"),

    OAUTH_V1("https://api.covapp.io/oauth/v1"),

    SERVICE_V1("https://api.covapp.io/service/v1"),

    AUTHN_V2("https://api.covapp.io/authn/v2"),

    DEVICE_V1("https://api.covapp.io/device/v1");

    private final String value;

    private ServiceUrl(String url) {
        value = url;
    }

    public String getValue() {
        return value;
    }

}
