package com.covisint.papi.sample.android.openregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.covisint.papi.sample.android.openregistration.model.error.Error;
import com.covisint.papi.sample.android.openregistration.model.person.Person;
import com.covisint.papi.sample.android.openregistration.util.Constants;
import com.covisint.papi.sample.android.openregistration.util.NetworkResponse;
import com.covisint.papi.sample.android.openregistration.util.ProgressDisplay;
import com.covisint.papi.sample.android.openregistration.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
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

    private ProgressDisplay mProgressDisplay;
    private TextView mSubmitStatus;

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
        mSubmitStatus = (TextView) findViewById(R.id.submit_person_status);

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

        mLanguageSpinner = (Spinner) findViewById(R.id.lang_prefs);

        mTzLandSubmissionButton = (Button) findViewById(R.id.tz_lang_submission_button);
        mTzLandSubmissionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSubmission();
            }
        });
        if (mProgressDisplay == null)
            mProgressDisplay = new ProgressDisplay(this, mPersonSubmissionFormView, mProgressView);
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
        String timeZone = mTimezoneSpinner.getSelectedItem().toString();
        String langPref = mLanguageSpinner.getSelectedItem().toString();

        String lang = Utils.getCountryLanguageCodeMap().get(mPerson.getAddresses()[0].getCountry());
        mPerson.setTimezone(timeZone);
        mPerson.setLanguage(lang);

        mProgressDisplay.showProgress(true);
        mPersonTask = new SubmitPersonTask();
        mPersonTask.execute(false);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class SubmitPersonTask extends AsyncTask<Boolean, Void, NetworkResponse> {

        @Override
        protected NetworkResponse doInBackground(Boolean... flags) {
            NetworkResponse networkResponse = new NetworkResponse();
            boolean renewToken = flags[0];
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost();
                String urlString = getString(R.string.person_url);
//                    if (!mName.equalsIgnoreCase("all"))
//                        urlString = urlString + "?name=" + URLEncoder.encode(mName, "UTF-8");

                URI uri = new URI(urlString);
                postRequest.setURI(uri);
                String[] headersArray = getResources().getStringArray(R.array.person_request_headers);
                for (String header : headersArray) {
                    String[] headers = header.split(",");
                    postRequest.setHeader(headers[0], headers[1]);
                }
                postRequest.setHeader("Authorization", "Bearer " + Utils.getToken(getBaseContext(),renewToken));
                Gson gson = new GsonBuilder().create();
                String personJson = gson.toJson(mPerson);
                postRequest.setEntity(new StringEntity(personJson));
                HttpResponse httpResponse = httpClient.execute(postRequest);
                StringBuilder stringBuilder = new StringBuilder(1024);
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                String reading;
                while ((reading = br.readLine()) != null) {
                    stringBuilder.append(reading);
                }
                networkResponse.setRawData(stringBuilder.toString());
                networkResponse.setStatusLine(httpResponse.getStatusLine());
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
        protected void onPostExecute(final NetworkResponse networkResponse) {
            mPersonTask = null;
            mProgressDisplay.showProgress(false);
            if (networkResponse == null) {
                mErrorMessage.setText("Something went wrong!");
            } else if (networkResponse.getStatusLine().getStatusCode() == 401) {
                // Token expired.
                if ("Access Token Expired".equals(networkResponse.getStatusLine().getReasonPhrase())){
                    mSubmitStatus.setText("Token expired! Getting token...");
                    mPersonTask = new SubmitPersonTask();
                    mPersonTask.execute(true);
                    mProgressDisplay.showProgress(true);
                }
            } else if (networkResponse.getStatusLine().getReasonPhrase().startsWith("HTTP")) {
                mErrorMessage.setText(networkResponse.getStatusLine().getReasonPhrase());
            } else {
                Gson gson = new GsonBuilder().create();
                try {
                    Person person = gson.fromJson(networkResponse.getRawData(), Person.class);
                    Intent intent = new Intent(getBaseContext(), PasswordAccountActivity.class);
                    intent.putExtra(Constants.PERSON_JSON, networkResponse.getRawData());
                    String organizationJson = getIntent().getStringExtra(Constants.ORGANIZATION_JSON);
                    intent.putExtra(Constants.ORGANIZATION_JSON, organizationJson);
                    startActivity(intent);
                    finish();
                } catch ( Exception e) {
                    // Try to parse Error
                    Error error = gson.fromJson(networkResponse.getRawData(), Error.class);
                    String errorMessageToDisplay = error.getApiMessage() + "\n" + error.getApiStatusCode();
                    mErrorMessage.setText(errorMessageToDisplay.trim());
                    mErrorMessage.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        protected void onCancelled() {
            mPersonTask = null;
            mProgressDisplay.showProgress(false);
        }
    }
}