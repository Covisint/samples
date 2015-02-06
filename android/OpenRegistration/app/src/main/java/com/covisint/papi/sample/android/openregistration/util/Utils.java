package com.covisint.papi.sample.android.openregistration.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Nitin.Khobragade on 2/5/2015.
 */
public class Utils {
    private static ArrayList<String> sCountriesList;
    private static List<String> sTimezoneList;
    private static HashMap<String, String> sCountryCountryCodeMap;
    private static HashMap<String, String> sCountryLanguageCodeMap;

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
}
