package com.covisint.papi.sample.android.openregistration;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.covisint.papi.sample.android.openregistration.model.AuthenticationPolicy;
import com.covisint.papi.sample.android.openregistration.model.PasswordPolicy;
import com.covisint.papi.sample.android.openregistration.model.person.Person;
import com.covisint.papi.sample.android.openregistration.util.Constants;
import com.covisint.papi.sample.android.openregistration.util.ProgressDisplay;
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


public class PasswordAccountActivity extends Activity {

    private EditText mUserId;
    private EditText mPassword;
    private EditText mReenterPassword;
    private Person mPerson;
    private View mPasswordSubmissionFormView;
    private View mProgressView;
    private TextView mErrorMessage;
    private ProgressDisplay mProgressDisplay;
    private PasswordPolicyFetchTask mPasswordPolicyTask = null;
    private AuthenticationPolicyFetchTask mAuthnPolicyTask = null;
    private PasswordAccountCreationTask mPasswordAccountTask = null;
    private PasswordPolicy[] mPasswordPolicies;
    private AuthenticationPolicy[] mAuthnPolicies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_account);

        String personJson = getIntent().getStringExtra(Constants.PERSON_JSON);
        Gson gson = new GsonBuilder().create();
        mPerson = gson.fromJson(personJson, Person.class);

        mPasswordSubmissionFormView = findViewById(R.id.password_account_submission_form);
        mProgressView = findViewById(R.id.password_account_submission_progress);

        mErrorMessage = (TextView)findViewById(R.id.message);

        mUserId = (EditText) findViewById(R.id.user_id);
        mPassword = (EditText) findViewById(R.id.password);
        mReenterPassword = (EditText) findViewById(R.id.reenter_password);
        Button mProceesButton = (Button) findViewById(R.id.password_account__submission_button);
        mProceesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptAddPasswordAccount();
            }
        });

        if (mProgressDisplay == null)
            mProgressDisplay = new ProgressDisplay(this, mPasswordSubmissionFormView, mProgressView);
        if (mPasswordPolicies == null) {
            mPasswordPolicyTask = new PasswordPolicyFetchTask();
            mPasswordPolicyTask.execute();
            mProgressDisplay.showProgress(true);
        }
    }

    private void attemptAddPasswordAccount() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_password_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class PasswordPolicyFetchTask extends AsyncTask<Void, String, PasswordPolicy[]> {
        @Override
        protected PasswordPolicy[] doInBackground(Void... params) {
            PasswordPolicy[] passwordPolicies = null;
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet getRequest = new HttpGet();
                String urlString = getString(R.string.password_policy_url);

                URI uri = new URI(urlString);
                getRequest.setURI(uri);
                String[] headersArray = getResources().getStringArray(R.array.password_policy_request_headers);
                for (String header : headersArray) {
                    String[] headers = header.split(",");
                    getRequest.setHeader(headers[0], headers[1]);
                }
                getRequest.setHeader("Authorization", "Bearer " + Utils.getToken(getBaseContext(), false));
                HttpResponse httpResponse = httpClient.execute(getRequest);
                StringBuilder stringBuilder = new StringBuilder(1024);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    publishProgress("Reading response...");
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                    String reading;
                    int dots = 0;
                    while ((reading = br.readLine()) != null) {
                        stringBuilder.append(reading);
                        dots %= 3;
                        dots++;
                        switch (dots) {
                            case 3:
                                publishProgress("Reading response...");
                                break;
                            case 2:
                                publishProgress("Reading response..");
                                break;
                            case 1:
                                publishProgress("Reading response.");
                                break;
                        }
                    }
                    String jsonResponse = stringBuilder.toString();
                    publishProgress("Parsing response...");
                    Gson gson = new GsonBuilder().create();
                    passwordPolicies = gson.fromJson(jsonResponse, PasswordPolicy[].class);
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return passwordPolicies;
        }

        @Override
        protected void onPostExecute(PasswordPolicy[] passwordPolicies) {
            mPasswordPolicies = passwordPolicies;
            mPasswordPolicyTask = null;
            mAuthnPolicyTask = new AuthenticationPolicyFetchTask();
            mAuthnPolicyTask.execute();
        }
    }

    public class AuthenticationPolicyFetchTask extends AsyncTask<Void, String, AuthenticationPolicy[]> {
        @Override
        protected AuthenticationPolicy[] doInBackground(Void... params) {
            AuthenticationPolicy[] authnPolicies = null;
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet getRequest = new HttpGet();
                String urlString = getString(R.string.authn_policy_url);

                URI uri = new URI(urlString);
                getRequest.setURI(uri);
                String[] headersArray = getResources().getStringArray(R.array.authn_policy_request_headers);
                for (String header : headersArray) {
                    String[] headers = header.split(",");
                    getRequest.setHeader(headers[0], headers[1]);
                }
                getRequest.setHeader("Authorization", "Bearer " + Utils.getToken(getBaseContext(), false));
                HttpResponse httpResponse = httpClient.execute(getRequest);
                StringBuilder stringBuilder = new StringBuilder(1024);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    publishProgress("Reading response...");
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                    String reading;
                    int dots = 0;
                    while ((reading = br.readLine()) != null) {
                        stringBuilder.append(reading);
                        dots %= 3;
                        dots++;
                        switch (dots) {
                            case 3:
                                publishProgress("Reading response...");
                                break;
                            case 2:
                                publishProgress("Reading response..");
                                break;
                            case 1:
                                publishProgress("Reading response.");
                                break;
                        }
                    }
                    String jsonResponse = stringBuilder.toString();
                    publishProgress("Parsing response...");
                    Gson gson = new GsonBuilder().create();
                    authnPolicies = gson.fromJson(jsonResponse, AuthenticationPolicy[].class);
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return authnPolicies;
        }

        @Override
        protected void onPostExecute(AuthenticationPolicy[] authnPolicies) {
            mAuthnPolicies = authnPolicies;
            mProgressDisplay.showProgress(false);
        }
    }

    private class PasswordAccountCreationTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }
}
