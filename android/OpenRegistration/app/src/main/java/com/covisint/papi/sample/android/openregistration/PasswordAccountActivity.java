package com.covisint.papi.sample.android.openregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.covisint.papi.sample.android.openregistration.model.PasswordAccount;
import com.covisint.papi.sample.android.openregistration.model.error.*;
import com.covisint.papi.sample.android.openregistration.model.error.Error;
import com.covisint.papi.sample.android.openregistration.model.organization.Organization;
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
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
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
    private Organization mOrganization;
    private View mPasswordSubmissionFormView;
    private View mProgressView;
    private TextView mErrorMessage;
    private ProgressDisplay mProgressDisplay;
    private PasswordAccountCreationTask mPasswordAccountTask = null;
    private PasswordAccount mPasswordAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_account);

        mPasswordAccount = new PasswordAccount();
        String personJson = getIntent().getStringExtra(Constants.PERSON_JSON);
        Gson gson = new GsonBuilder().create();
        mPerson = gson.fromJson(personJson, Person.class);

        String organizationJson = getIntent().getStringExtra(Constants.ORGANIZATION_JSON);
        mOrganization = gson.fromJson(organizationJson, Organization.class);

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
    }

    private void attemptAddPasswordAccount() {
        String userId = mUserId.getText().toString();
        String password = mPassword.getText().toString();
        String reenterPassword = mReenterPassword.getText().toString();
        int userIdLength = userId.trim().length();
        if (userId != null && userIdLength > 0) {
            if (userIdLength < 4 || userIdLength > 20) {
                mUserId.setError(getString(R.string.error_user_id_length));
                mUserId.requestFocus();
            } else {
                if(password != null && reenterPassword != null) {
                    int passwordLength = password.trim().length();
                    if (passwordLength > 0) {
                        if (password.equals(reenterPassword)) {
                            mPasswordAccount.setUsername(userId);
                            mPasswordAccount.setPassword(password);
                            mPasswordAccount.setAuthenticationPolicyId(mOrganization.getAuthenticationPolicy().getId());
                            mPasswordAccount.setPasswordPolicyId(mOrganization.getPasswordPolicy().getId());
                            mPasswordAccountTask = new PasswordAccountCreationTask();
                            mPasswordAccountTask.execute(false);
                            mProgressDisplay.showProgress(true);
                        } else {
                            mReenterPassword.setError(getString(R.string.error_password_mismatch));
                            mReenterPassword.requestFocus();
                        }
                    } else {
                        mPassword.setError(getString(R.string.error_field_required));
                        mPassword.requestFocus();
                    }
                }
            }
        } else {
            mUserId.setError(getString(R.string.error_field_required));
            mUserId.requestFocus();
        }
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

    private class PasswordAccountCreationTask extends AsyncTask<Boolean, String, NetworkResponse> {

        @Override
        protected NetworkResponse doInBackground(Boolean... flags) {
            Boolean renewToken = flags[0];
            NetworkResponse networkResponse = new NetworkResponse();
            try {
                Gson gson = new GsonBuilder().create();
                String passwordAccountJson = gson.toJson(mPasswordAccount);
                HttpClient httpClient = new DefaultHttpClient();
                HttpPut putRequest = new HttpPut();
                String urlString = String.format(getString(R.string.password_account_url), mPerson.getId());

                URI uri = new URI(urlString);
                putRequest.setURI(uri);
                String[] headersArray = getResources().getStringArray(R.array.password_account_request_headers);
                for (String header : headersArray) {
                    String[] headers = header.split(",");
                    putRequest.setHeader(headers[0], headers[1]);
                }
                putRequest.setHeader("Authorization", "Bearer " + Utils.getToken(getBaseContext(),renewToken));
                putRequest.setEntity(new StringEntity(passwordAccountJson));
                HttpResponse httpResponse = httpClient.execute(putRequest);
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
        protected void onPostExecute(NetworkResponse networkResponse) {
            mPasswordAccountTask = null;
            mProgressDisplay.showProgress(false);
            int statusCode = networkResponse.getStatusLine().getStatusCode();
            Gson gson = new GsonBuilder().create();
            if (statusCode == 200) {
                // Success
                mPasswordAccount = gson.fromJson(networkResponse.getRawData(), PasswordAccount.class);
                // Proceed further...
                Intent intent = new Intent(getBaseContext(), SecurityQuestionActivity.class);
                intent.putExtra(Constants.PERSON_JSON, getIntent().getStringExtra(Constants.PERSON_JSON));
                intent.putExtra(Constants.ORGANIZATION_JSON, getIntent().getStringExtra(Constants.ORGANIZATION_JSON));
                intent.putExtra(Constants.PASSWORD_ACCOUNT, networkResponse.getRawData());
                startActivity(intent);
                finish();
            } else if (statusCode == 400) {
                // Error from server
                Error serverError = gson.fromJson(networkResponse.getRawData(), Error.class);
                mErrorMessage.setText(serverError.toString());
            } else if (statusCode == 401) {
                if ("Access Token Expired".equals(networkResponse.getStatusLine().getReasonPhrase())){
                    mPasswordAccountTask = new PasswordAccountCreationTask();
                    mPasswordAccountTask.execute(true);
                    mProgressDisplay.showProgress(true);
                }
            } else {
                // Any other error
                mErrorMessage.setText(networkResponse.getStatusLine().toString());
            }
        }
    }
}
