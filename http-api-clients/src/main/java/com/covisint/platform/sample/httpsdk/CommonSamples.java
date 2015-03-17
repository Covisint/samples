/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.covisint.core.http.service.client.CacheSpec;
import com.covisint.core.http.service.client.CacheSpec.ExpirationMode;
import com.covisint.core.http.service.core.Page;
import com.covisint.core.http.service.core.ServiceException;
import com.covisint.platform.group.client.sdk.GroupSDK;
import com.covisint.platform.group.client.sdk.GroupSDK.GroupClient;
import com.covisint.platform.group.client.sdk.GroupSDK.GroupClient.Sort;
import com.covisint.platform.group.core.group.Group;
import com.covisint.platform.oauth.client.token.sdk.AuthConfigurationProvider;
import com.covisint.platform.user.client.sdk.PersonSDK;
import com.covisint.platform.user.client.sdk.PersonSDK.PersonClient;
import com.covisint.platform.user.core.person.Person;
import com.google.common.base.Charsets;
import com.google.common.util.concurrent.CheckedFuture;

public class CommonSamples {

    /** Demonstrates how to configure and instantiate a client through the associated factory. */
    public static void configureClient() {

        /*
         * Create a new, basic SDK for the "person" service. This example, although minimal, is fully configured with
         * preset default values and is ready to create clients.
         */
        PersonSDK sdk = new PersonSDK("https://api.covapp.io/person/v1");

        /*
         * We will configure this client to cache returned entity bodies. If no caching is desired, simply omit the
         * following setup steps.
         */
        CacheSpec.Builder cacheConfigBuilder = CacheSpec.builder();

        /*
         * Expire cache elements exactly 5 minutes after the element was put into the cache. The other alternative is
         * ExpirationMode.AFTER_ACCESS, which will expire the cache 5 minutes after the element was last retrieved from
         * a cache hit.
         */
        cacheConfigBuilder.expiration(300000, ExpirationMode.AFTER_WRITE);

        /*
         * Limit the number of elements in the cache. The strategy to keep an upper bound on the cache elements is
         * undocumented and subject to change at any time.
         */
        cacheConfigBuilder.maxElements(1000);

        /* We are finished, now build the configuration spec and set it into the SDK. */
        CacheSpec cacheConfig = cacheConfigBuilder.build();
        sdk.setCacheSpec(cacheConfig);

        /*
         * Sets the maximum period of inactivity between two consecutive data packets in milliseconds. In this case we
         * want to wait up to 2 seconds before we give up on the response and throw a timeout error.
         */
        sdk.setSocketTimeout(2000);

        /* Set the socket buffer size in bytes. In this example, a 2K buffer will be used. */
        sdk.setSocketBufferSize(2048);

        /* Set the source IP address of outbound connections. */
        sdk.setSourceAddress("0.0.0.0");

        /* Keep an upper bound on the number of concurrent HTTP requests issued by this SDK. */
        sdk.setMaxConcurrentRequests(20);

        /*
         * Set redirect (302) preferences. If redirects should not be followed, just set the option to false. Defaults
         * to true.
         */
        sdk.setFollowRedirects(true);
        sdk.setMaxRedirects(5);

        /* Turn content compression (i.e. gzip) on or off. Defaults to true. */
        sdk.setContentCompressionEnabled(false);

        /* Set the content charset. Defaults to UTF-8. */
        sdk.setContentCharSet(Charsets.UTF_8);

        /* We are done configuring the SDK to our preferences, now call #create to get our person client instance. */
        PersonClient personClient = sdk.newClient();

        /* And now we may invoke its methods. */
        personClient.activate("a15cab31e2a1");
    }

    /** Demonstrates the capabilities of the search APIs. */
    public static void searchOptions() {

        /* Build the client as needed. */
        GroupClient client = new GroupSDK("https://api.covapp.io/group/v1").newClient();

        /*
         * The group search method allows you to filter on group name(s) and description(s). In this example, we will
         * show how to search for a given group name.
         */
        Collection<String> names = Arrays.asList("My Group Name");
        boolean includeEntitlements = false;

        /*
         * Pagination on results is applied with the Page object. The first constructor argument is the page index,
         * which always starts at 1. The second argument is the page size. This example shows how to request
         * "the first 30 results".
         */
        Page page = new Page(1, 30);

        // You could also say page = Page.DEFAULT to return the default initial page of 50 resources.

        /*
         * Now we pass the search criteria to the search method. Note that the trailing method arguments is a varargs of
         * Sort enum values, which allows for sorting of result records.
         */
        List<Group> firstPage = client.search(names, null, includeEntitlements, page, Sort.CREATION.descending())
                .checkedGet();

        if (firstPage.size() == 30) {
            /*
             * If we have more results to show, then continue by fetching the next set of results to display the second
             * page. Make sure to pass the exact same search criteria, and only change the pagination argument.
             */
            List<Group> secondPage = client.search(names, null, includeEntitlements, new Page(2, 30),
                    Sort.CREATION.descending()).checkedGet();
        }
    }

    /** Shows how you can provide your own source of client credentials. */
    public static void customCredentialProvider() {

        /*
         * A custom config provider that can load app id/client credentials from a secure location and supply them to
         * the SDK.
         */
        class MyCustomConfigProvider implements AuthConfigurationProvider {

            public String getApplicationId() {
                return "my-app-id";
            }

            public String getClientId() {
                return "my-client-id";
            }

            public String getClientSecret() {
                return "my-client-secret";
            }

            public URL getAuthServiceBaseUrl() {
                try {
                    return new URL("https://api.covapp.io/oauth/v1");
                } catch (MalformedURLException e) {
                    throw new IllegalStateException(e);
                }
            }

        }

        /* The extended person SDK that allows you to override with your custom configuration provider. */
        class MyPersonSDK extends PersonSDK {

            public MyPersonSDK(String serviceUrl) {
                super(serviceUrl);
            }

            /** {@inheritDoc} */
            protected AuthConfigurationProvider getConfigurationProvider() {
                return new MyCustomConfigProvider();
            }

        }

        /* Finally, simply use your custom SDK to generate the client you need. */
        PersonClient client = new MyPersonSDK("https://api.covapp.io/person/v1").newClient();

    }

    /** Shows how you may catch and work with exceptions through the client. */
    public static void errorHandling() {

        /* Build the client as needed. */
        PersonClient client = new PersonSDK("https://api.covapp.io/person/v1").newClient();

        try {
            /* Each client method (activate, in this case) throws a ServiceException with the details. */
            client.activate("b1acee3510a01").checkedGet();
        } catch (ServiceException e) {
            /* If we get here, there has been a service exception thrown. Let's do something meaningful with it. */

            /* A human-readable message describing the error. */
            System.out.println(e.getMessage());

            /*
             * The service status code is an error code that is defined within the API documentation. Consult the online
             * documentation for the full set of status codes for each API. We will make up a few below just for example
             * sake.
             */
            String statusCode = e.getServiceStatusCode();

            switch (statusCode) {
                case "framework:request:invalid:id":
                    // Do something.
                    break;

                case "framework:request:id:conflict":
                    // Do something.
                    break;

                case "person.illegal.state.change":
                    // Do something.
                    break;
                default:
                    // Just swallow everything else!
                    break;
            }

        }
    }

    /** Demonstrates how to execute multiple calls in parallel and join them later. */
    public static void asyncRequests() {

        /* Build the client as needed. */
        PersonClient client = null;

        /* Execute 3 non-blocking calls to the person service. */
        CheckedFuture<Person, ServiceException> getFuture = client.get("100ae20131cdbe1");

        CheckedFuture<List<Person>, ServiceException> searchFuture = client.search(null, null, "johnsmith", null,
                Page.DEFAULT);

        CheckedFuture<Void, ServiceException> activateFuture = client.activate("55e10ee23cb10de");

        // Now retrieve them asynchronously.
        Person person = getFuture.checkedGet();
        List<Person> results = searchFuture.checkedGet();
        activateFuture.checkedGet();
    }

}
