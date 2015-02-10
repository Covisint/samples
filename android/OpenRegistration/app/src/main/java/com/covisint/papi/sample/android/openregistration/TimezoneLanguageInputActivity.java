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
import android.widget.Spinner;
import android.widget.TextView;

import com.covisint.papi.sample.android.openregistration.model.error.Error;
import com.covisint.papi.sample.android.openregistration.model.organization.Organization;
import com.covisint.papi.sample.android.openregistration.model.person.Person;
import com.covisint.papi.sample.android.openregistration.util.Constants;
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

        mLanguageSpinner = (Spinner) findViewById(R.id.lang_prefs);

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
        String timeZone = mTimezoneSpinner.getSelectedItem().toString();
        String langPref = mLanguageSpinner.getSelectedItem().toString();

        String lang = Utils.getCountryLanguageCodeMap().get(mPerson.getAddresses()[0].getCountry());
        mPerson.setTimezone(timeZone);
        mPerson.setLanguage(lang);

        showProgress(true);
        mPersonTask = new SubmitPersonTask();
        mPersonTask.execute(mPerson);
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
    public class SubmitPersonTask extends AsyncTask<Person, Void, String> {

        @Override
        protected String doInBackground(Person... persons) {
            String networkResponse = null;
            int count = persons.length;
            if (count > 0) {
                Person person = persons[0];
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost postRequest = new HttpPost();
                    String urlString = getString(R.string.person_base_url);
//                    if (!mName.equalsIgnoreCase("all"))
//                        urlString = urlString + "?name=" + URLEncoder.encode(mName, "UTF-8");

                    URI uri = new URI(urlString);
                    postRequest.setURI(uri);
                    String[] headersArray = getResources().getStringArray(R.array.person_request_headers);
                    for (String header : headersArray) {
                        String[] headers = header.split(",");
                        postRequest.setHeader(headers[0], headers[1]);
                    }
                    postRequest.setHeader("Authorization", "Bearer " + Utils.getToken(getBaseContext(),false));
                    Gson gson = new GsonBuilder().create();
                    String personJson = gson.toJson(mPerson);
                    postRequest.setEntity(new StringEntity(personJson));
                    HttpResponse httpResponse = httpClient.execute(postRequest);
                    StringBuilder stringBuilder = new StringBuilder(1024);
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (statusCode == 201 || statusCode == 400) {
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
                try {
                    Person person = gson.fromJson(networkResponse, Person.class);
                    Intent intent = new Intent(getBaseContext(), PasswordAccountActivity.class);
                    intent.putExtra(Constants.PERSON_JSON, networkResponse);
                    startActivity(intent);
                    finish();
                } catch ( Exception e) {
                    // Try to parse Error
                    Error error = gson.fromJson(networkResponse, Error.class);
                    String errorMessageToDisplay = error.getApiMessage() + "\n" + error.getApiStatusCode();
                    mErrorMessage.setText(errorMessageToDisplay.trim());
                    mErrorMessage.setVisibility(View.VISIBLE);
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