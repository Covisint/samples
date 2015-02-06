package com.covisint.papi.sample.android.openregistration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.covisint.papi.sample.android.openregistration.model.contact.Address;
import com.covisint.papi.sample.android.openregistration.model.organization.Organization;
import com.covisint.papi.sample.android.openregistration.model.person.Person;
import com.covisint.papi.sample.android.openregistration.util.Constants;
import com.covisint.papi.sample.android.openregistration.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


/**
 * A login screen that offers login via email/password.
 */
public class TimezoneLanguageInputActivity extends Activity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private SubmitPersonTask mPersonTask = null;
    private Button mTzLandSubmissionButton;
    private View mProgressView;
    private View mPersonSubmissionFormView;
    private Person mPerson;
    private Spinner mTimezoneSpinner;
    private Spinner mLanguageSpinner;
    private TextView mErrorMessage;

    // UI references.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timezone_lang_input);

        String personJson = getIntent().getStringExtra(Constants.PERSON_JSON);
        Gson gson = new GsonBuilder().create();
        mPerson = gson.fromJson(personJson, Person.class);

        mPersonSubmissionFormView = findViewById(R.id.tz_lang_submission_form);
        mProgressView = findViewById(R.id.person_submission_progress);

        mErrorMessage = (TextView)findViewById(R.id.message);
        mTimezoneSpinner = (Spinner) findViewById(R.id.time_zone);
        List<String> timezones = Utils.getTimezones();
        ArrayAdapter<String> timezonesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timezones);
        timezonesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TimeZone defaultTZ = TimeZone.getDefault();
        String tzId = defaultTZ.getID();
        mTimezoneSpinner.setAdapter(timezonesAdapter);
        int tzIndex = timezones.indexOf(tzId);
        mTimezoneSpinner.setSelection(tzIndex);

        mTzLandSubmissionButton = (Button) findViewById(R.id.tz_lang_submission_button);
        mTzLandSubmissionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSubmission();
            }
        });
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptSubmission() {
        if (mPersonTask != null) {
            return;
        }
        Intent intent = new Intent(this, ContactsInputActivity.class);
        Gson gson = new GsonBuilder().create();
        String personJson = gson.toJson(mPerson);
        intent.putExtra(Constants.PERSON_JSON, personJson);
        startActivity(intent);
        finish();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mPersonSubmissionFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mPersonSubmissionFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mPersonSubmissionFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mPersonSubmissionFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class SubmitPersonTask extends AsyncTask<Void, Void, String> {

        private final String mName;

        SubmitPersonTask(String name) {
            mName = name;
        }

        @Override
        protected String doInBackground(Void... params) {
            String networkResponse = null;
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet getRequest = new HttpGet();
                String urlString = getString(R.string.org_base_url);
                if (!mName.equalsIgnoreCase("all"))
                    urlString = urlString + "?name=" + URLEncoder.encode(mName, "UTF-8");

                URI uri = new URI(urlString);
                getRequest.setURI(uri);
                getRequest.setHeader("Accept", "application/vnd.com.covisint.platform.organization.v1+json");
                getRequest.setHeader("x-realm", "ALIAS162-DEV");
                getRequest.setHeader("x-requestor", "requestor");
                getRequest.setHeader("x-requestor-app", "app");
                HttpResponse httpResponse = httpClient.execute(getRequest);
                StringBuilder stringBuilder = new StringBuilder(1024);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                    String reading;
                    while ((reading = br.readLine()) != null) {
                        stringBuilder.append(reading);
                    }
                    networkResponse = stringBuilder.toString();
                } else {
                    networkResponse = httpResponse.getStatusLine().toString();
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return networkResponse;
        }

        @Override
        protected void onPostExecute(final String networkResponse) {
            mPersonTask = null;
            showProgress(false);
            if (networkResponse == null) {
                mErrorMessage.setText("Something went wrong!");
            } else if (networkResponse.startsWith("HTTP")) {
                mErrorMessage.setText(networkResponse);
            } else {
                Gson gson = new GsonBuilder().create();
                Organization[] organizations = gson.fromJson(networkResponse, Organization[].class);
                if (organizations.length == 0) {
                    mErrorMessage.setText(getString(R.string.no_org_found));
                } else {
                    Intent intent;
                    if (organizations.length == 1) {
                        intent = new Intent(getBaseContext(), UserInformationActivity.class);
                        intent.putExtra(Constants.ORGANIZATION_JSON, gson.toJson(organizations[0]));
                    } else {
                        intent = new Intent(getBaseContext(), OrganizationList.class);
                        intent.putExtra(Constants.ORGANIZATION_LIST_JSON, networkResponse);
                    }
                    startActivity(intent);
                    finish();
                }
            }
        }

        @Override
        protected void onCancelled() {
            mPersonTask = null;
            showProgress(false);
        }
    }
}