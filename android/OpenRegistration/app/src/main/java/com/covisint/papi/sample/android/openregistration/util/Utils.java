package com.covisint.papi.sample.android.openregistration.util;

import android.content.Context;

import com.covisint.papi.sample.android.openregistration.R;
import com.covisint.papi.sample.android.openregistration.model.organization.Organization;
import com.google.gson.Gson;

import com.covisint.papi.sample.android.openregistration.model.AuthToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.util.Base64;
import android.util.Log;

/**
 * Created by Nitin.Khobragade on 2/5/2015.
 */
public class Utils {
    private static ArrayList<String> sCountriesList;
    private static List<String> sTimezoneList;
    private static HashMap<String, String> sCountryCountryCodeMap;
    private static HashMap<String, String> sCountryLanguageCodeMap;
    private static String token;

    synchronized public static List<String> getCountries() {
        if (sCountriesList == null) {
            initialize();
        }
        return sCountriesList;
    }

    synchronized public static HashMap<String, String> getCountryLanguageCodeMap() {
        if (sCountryLanguageCodeMap == null)
            initialize();
        return sCountryLanguageCodeMap;
    }

    synchronized public static HashMap<String, String> getCountryCountryCodeMap() {
        if (sCountryCountryCodeMap == null)
            initialize();
        return sCountryCountryCodeMap;
    }

    private static void initialize() {
        sCountriesList = new ArrayList<>();
        sCountryLanguageCodeMap = new HashMap<>();
        sCountryCountryCodeMap = new HashMap<>();
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !sCountriesList.contains(country)) {
                sCountriesList.add(country);
                String countryCode = locale.getCountry();
                sCountryCountryCodeMap.put(country, countryCode);
                String lang = locale.getLanguage();
                sCountryLanguageCodeMap.put(country, lang);
            }
        }

        String[] tzIds = TimeZone.getAvailableIDs();
        sTimezoneList = Arrays.asList(tzIds);
        Collections.sort(sCountriesList);
    }

   synchronized public static List<String> getTimezones() {
        if(sTimezoneList == null)
            initialize();
        return sTimezoneList;
    }

    public static String getToken(Context context, boolean renew) {
        String clientId = context.getString(R.string.client_id);
        String clientSecret = context.getString(R.string.client_secret);
        String appId = context.getString(R.string.app_id);
        String basicAuthStr = Base64.encodeToString((clientId+":"+clientSecret).getBytes(),Base64.NO_WRAP);
        if(token == null || token.trim().isEmpty() || renew) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost();
                String urlString = context.getString(R.string.token_auth);
                URI uri = new URI(urlString);
                postRequest.setURI(uri);
                postRequest.setHeader("Accept", context.getString(R.string.token_accept));
                postRequest.setHeader("Authorization", "Basic " + basicAuthStr);
                postRequest.setHeader("type", "client_credentials");
                postRequest.setHeader("application-id", appId);
                Log.d("Utils.class", "inutils.");
                Log.d("Utils.class", basicAuthStr);
                HttpResponse httpResponse = httpClient.execute(postRequest);
                Log.d("Utils.class", ""+httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    Gson gson = new Gson();
                    AuthToken authToken = gson.fromJson(new InputStreamReader(httpResponse.getEntity().getContent()), AuthToken.class);
                    token = authToken.accessToken;
                    Log.d("Utils.class", token);
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
                Log.e("Utils.class", e.getMessage(),e);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                Log.e("Utils.class", e.getMessage(),e);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Utils.class", e.getMessage(),e);
            }

        }
        return token;
    }
}
