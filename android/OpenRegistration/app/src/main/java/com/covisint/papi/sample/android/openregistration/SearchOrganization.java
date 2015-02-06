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
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.covisint.papi.sample.android.openregistration.model.organization.Organization;
import com.covisint.papi.sample.android.openregistration.util.Constants;
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
import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class SearchOrganization extends Activity {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private SearchTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mOrgNameView;
    private View mProgressView;
    private View mSearchFormView;
    private TextView mErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_organization);

        // Set up the login form.
        mOrgNameView = (AutoCompleteTextView) findViewById(R.id.org_name);
        mOrgNameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.org_name || id == EditorInfo.IME_NULL) {
                    attemptSearch();
                    return true;
                }
                return false;
            }
        });

        Button mSearchButton = (Button) findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSearch();
            }
        });

        mSearchFormView = findViewById(R.id.name_search_form);
        mProgressView = findViewById(R.id.search_progress);
        mErrorMessage = (TextView) findViewById(R.id.message);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptSearch() {
        if (mAuthTask != null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mOrgNameView.getWindowToken(), 0);
        // Reset errors.
        mOrgNameView.setError(null);

        // Store values at the time of the login attempt.
        String orgName = mOrgNameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(orgName)) {
            mOrgNameView.setError(getString(R.string.error_field_required));
            focusView = mOrgNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new SearchTask(orgName);
            mAuthTask.execute((Void) null);
        }
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

            mSearchFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSearchFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSearchFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mSearchFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void addOrgNameToAutoComplete(List<String> orgNameAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(SearchOrganization.this,
                        android.R.layout.simple_dropdown_item_1line, orgNameAddressCollection);

        mOrgNameView.setAdapter(adapter);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class SearchTask extends AsyncTask<Void, Void, String> {

        private final String mName;

        SearchTask(String name) {
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
            mAuthTask = null;
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
            mAuthTask = null;
            showProgress(false);
        }
    }
}



