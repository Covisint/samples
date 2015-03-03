/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk;

public enum ServiceUrl {

    PERSON_V1("https://api.covisintrnd.com/person/v1"),

    GROUP_V1("https://api.covisintrnd.com/group/v1"),

    ORG_V1("https://api.covisintrnd.com/organization/v1"),

    OAUTH_V1("https://api.covisintrnd.com/oauth/v1"),

    SERVICE_V1("https://api.covisintrnd.com/service/v1"),

    AUTHN_V2("https://api.covisintrnd.com/authn/v2");

    private final String value;

    private ServiceUrl(String url) {
        value = url;
    }

    public String getValue() {
        return value;
    }

}
