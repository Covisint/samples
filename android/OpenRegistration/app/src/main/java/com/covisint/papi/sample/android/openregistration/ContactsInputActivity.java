package com.covisint.papi.sample.android.openregistration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.covisint.papi.sample.android.openregistration.model.UserInformation;
import com.covisint.papi.sample.android.openregistration.model.contact.Phone;
import com.covisint.papi.sample.android.openregistration.model.contact.PhoneType;
import com.covisint.papi.sample.android.openregistration.model.person.Person;
import com.covisint.papi.sample.android.openregistration.util.Constants;
import com.covisint.papi.sample.android.openregistration.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;


/**
 * A login screen that offers login via email/password.
 */
public class ContactsInputActivity extends Activity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private Button mContactsSubmissionButton;
    private EditText mIsdPhoneNumber;
    private EditText mPhoneNumber;
    private EditText mIsdMobileNumber;
    private EditText mMobileNumber;
    private EditText mFaxNumber;
    private EditText mEmail;
    private EditText mReenterEmail;
    private Person mPerson;

    // UI references.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_input);

        // Get the person from previous activity
        String personJson = getIntent().getStringExtra(Constants.PERSON_JSON);
        Gson gson = new GsonBuilder().create();
        mPerson = gson.fromJson(personJson, Person.class);

        // initialize all ui elements
        mIsdPhoneNumber = (EditText) findViewById(R.id.isd_phone_number);
        mPhoneNumber = (EditText) findViewById(R.id.phone_number);
        mIsdMobileNumber = (EditText) findViewById(R.id.isd_mobile_number);
        mMobileNumber = (EditText) findViewById(R.id.mobile_number);
        mFaxNumber = (EditText) findViewById(R.id.fax_number);
        mEmail = (EditText) findViewById(R.id.email);
        mReenterEmail = (EditText) findViewById(R.id.reenter_email);

        mContactsSubmissionButton = (Button) findViewById(R.id.contacts_submission_button);
        mContactsSubmissionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSubmission();
            }
        });

        // pre populate some ui elements
        String country = mPerson.getAddresses().getCountry();
        HashMap<String, String> countryCountryCodeMap = Utils.getCountryCountryCodeMap();
        String countryCode = countryCountryCodeMap.get(country);
        String[] countryLangIsdSamples = getResources().getStringArray(R.array.countries_lang_isd_sample_number);
        for (String countryLangIsdSample : countryLangIsdSamples) {
            String[] splits = countryLangIsdSample.split(",");
            if (splits[0] != null && splits[0].equalsIgnoreCase(countryCode)) {
                switch (splits.length) {
                    case 3:
                        mPhoneNumber.setHint(splits[2]);
                        mMobileNumber.setHint(splits[2]);
                    case 2:
                        mIsdPhoneNumber.setText(splits[1]);
                        mIsdMobileNumber.setText(splits[1]);
                }
                break;
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptSubmission() {
        String isdPhoneNumber = mIsdPhoneNumber.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();
        String isdMobileNumber = mIsdMobileNumber.getText().toString();
        String mobileNumber = mMobileNumber.getText().toString();
        String faxNumber = mFaxNumber.getText().toString();
        String email = mEmail.getText().toString();
        String reenterEmail = mReenterEmail.getText().toString();

        ArrayList<Phone> phones = new ArrayList<>(3);
        if(phoneNumber != null && phoneNumber.trim().length() > 0) {
            Phone phone = new Phone(PhoneType.main, isdPhoneNumber + " " + phoneNumber);
            phones.add(phone);
        }
        if(mobileNumber != null && mobileNumber.trim().length() > 0) {
            Phone phone = new Phone(PhoneType.mobile, isdMobileNumber + " " + mobileNumber);
            phones.add(phone);
        }
        if(faxNumber != null && faxNumber.trim().length() > 0) {
            Phone phone = new Phone(PhoneType.fax, faxNumber);
            phones.add(phone);
        }
        mPerson.setPhones(phones.toArray(new Phone[phones.size()]));

        Intent intent = new Intent(this, TimezoneLanguageInputActivity.class);
        Gson gson = new GsonBuilder().create();
        String personJson = gson.toJson(mPerson);
        intent.putExtra(Constants.PERSON_JSON, personJson);
        startActivity(intent);
        finish();

    }

}



