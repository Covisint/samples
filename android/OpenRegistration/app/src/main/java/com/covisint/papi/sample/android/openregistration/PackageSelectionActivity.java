package com.covisint.papi.sample.android.openregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.covisint.papi.sample.android.openregistration.model.organization.Organization;
import com.covisint.papi.sample.android.openregistration.model.packages.Package;
import com.covisint.papi.sample.android.openregistration.model.person.Person;
import com.covisint.papi.sample.android.openregistration.model.person.PersonRequest;
import com.covisint.papi.sample.android.openregistration.model.person.Registrant;
import com.covisint.papi.sample.android.openregistration.model.person.ServicePackageRequest;
import com.covisint.papi.sample.android.openregistration.util.Constants;
import com.covisint.papi.sample.android.openregistration.util.NetworkResponse;
import com.covisint.papi.sample.android.openregistration.util.ProgressDisplay;
import com.covisint.papi.sample.android.openregistration.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class PackageSelectionActivity extends Activity {

    private ListView mPackageListView;
    private Button mSubmitButton;
    private ArrayAdapter<Package> mPackageAdapter;
    private View mProgressView;
    private View mPackageSubmissionFormView;
    private ProgressDisplay mProgressDisplay;
    private PersonRegistrationTask mPersonRegistrationTask;
    private PackageSearchTask mPackageSearchTask;
    private Person mPerson;
    private Organization mOrganization;
    private List<Package> mPackages = new ArrayList<>();

    private List<PersonRequest> mPersonRequests;
    private List<PersonRequest> mPersonRequestResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_selection);

        Gson gson = new GsonBuilder().create();
        String personJson = getIntent().getStringExtra(Constants.PERSON_JSON);
        if (personJson == null) {
            personJson = "{\"id\":\"IHSK63T4\",\"version\":1423729413145,\"creator\":\"jlmMCCGIqTl4AOo3qN1xKlb11AZliXGO\",\"creatorAppId\":\"jlmMCCGIqTl4AOo3qN1xKlb11AZliXGO\",\"creation\":1423729413145,\"realm\":\"JWDEMOA-QA\",\"status\":\"unactivated\",\"name\":{\"prefix\":\"Mr.\",\"given\":\"Nitin\",\"middle\":\"R\",\"surname\":\"Khobragade\"},\"addresses\":[{\"streets\":[\"#128\"],\"city\":\"Bangalore\",\"state\":\"Karnataka\",\"postal\":\"560078\"}],\"language\":\"en\",\"timezone\":\"Asia/Calcutta\",\"phones\":[{\"type\":\"main\",\"number\":\"+919901066033\"},{\"type\":\"mobile\",\"number\":\"+91 9403186961\"}],\"title\":\"Arch\",\"email\":\"nitin.khobragade@happiestminds.com\",\"organizationId\":\"OJWDEMOA-QA102802\"}";
        }
        mPerson = gson.fromJson(personJson, Person.class);

        String organizationJson = getIntent().getStringExtra(Constants.ORGANIZATION_JSON);
        if (organizationJson == null) {
            organizationJson = "{\"url\":\"http://www.covisint.com\",\"accountNumber\":\"999999\",\"addresses\":[{\"city\":\"Southfield\",\"country\":\"US\",\"postal\":\"48076\",\"state\":\"MI\",\"streets\":[\"25800 Northwestern Hwy\"]}],\"authDomain\":\"SSO\",\"authenticationPolicy\":{\"id\":\"205fd2f1-5877-4cc6-a091-9cf4a5ff8304\",\"realm\":\"JWDEMOA-QA\",\"type\":\"authenticationPolicy\"},\"duns\":\"0\",\"name\":\"JWDEMOA-QA\",\"organizationType\":\"SU\",\"parentOrganization\":{\"id\":\"0\",\"realm\":\"JWDEMOA-QA\",\"type\":\"organization\"},\"passwordPolicy\":{\"id\":\"2816418c-8409-4866-8d69-a07ab7a79a19\",\"realm\":\"JWDEMOA-QA\"},\"phones\":[{\"number\":\"313-227-9707\",\"type\":\"main\"},{\"number\":\"313-227-9707\",\"type\":\"fax\"}],\"rootOrganization\":{\"id\":\"102802\",\"realm\":\"JWDEMOA-QA\",\"type\":\"organization\"},\"public\":true,\"version\":1423146903000,\"id\":\"OJWDEMOA-QA102802\",\"realm\":\"JWDEMOA-QA\",\"status\":\"active\",\"creation\":1423146903000}";
        }
        mOrganization = gson.fromJson(organizationJson, Organization.class);
        mPersonRequests = new ArrayList<>();
        mPersonRequestResponses = new ArrayList<>();
        mPackageListView = (ListView) findViewById(R.id.package_list);
        mPackageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, mPackages);
        mPackageListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mPackageListView.setAdapter(mPackageAdapter);
        mSubmitButton = (Button) findViewById(R.id.package_submission_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSubmission();
            }
        });
        mPackageSubmissionFormView = findViewById(R.id.package_submission_form);
        mProgressView = findViewById(R.id.package_submission_progress);
        if (mPackages.size() == 0) {
            mPackageSearchTask = new PackageSearchTask();
            mPackageSearchTask.execute(true);
        }
        if (mProgressDisplay == null)
            mProgressDisplay = new ProgressDisplay(this, mPackageSubmissionFormView, mProgressView);
        if (mPackageSearchTask != null || mPersonRegistrationTask != null) {
            mProgressDisplay.showProgress(true);
        }
    }

    private void attemptSubmission() {
        if (mPersonRegistrationTask != null) {
            return;
        }
        SparseBooleanArray checked = mPackageListView.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i)) {
                Package selectedPackage = mPackageAdapter.getItem(position);
                PersonRequest request = new PersonRequest();
                request.setJustification("Request from Sample App");
                Registrant registrant = new Registrant();
                registrant.setId(mPerson.getId());
                registrant.setType("person");
                request.setRegistrant(registrant);
                ServicePackageRequest servicePackage = new ServicePackageRequest();
                servicePackage.setPackageId(selectedPackage.getServicePackage().getId());
                request.setServicePackageRequest(servicePackage);
                mPersonRequests.add(request);
            }
        }

        mPersonRegistrationTask = new PersonRegistrationTask();
        mPersonRegistrationTask.execute(true);
        mProgressDisplay.showProgress(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_package_selection, menu);
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

    class PackageSearchTask extends AsyncTask<Boolean, String, NetworkResponse> {

        @Override
        protected NetworkResponse doInBackground(Boolean... params) {
            NetworkResponse networkResponse = new NetworkResponse();
            boolean renewToken = params[0];
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet getRequest = new HttpGet();
                String urlString = String.format(getString(R.string.packages_url), mOrganization.getId());

                URI uri = new URI(urlString);
                getRequest.setURI(uri);
                String[] headersArray = getResources().getStringArray(R.array.packages_request_headers);
                for (String header : headersArray) {
                    String[] headers = header.split(",");
                    getRequest.setHeader(headers[0], headers[1]);
                }
                getRequest.setHeader("Authorization", "Bearer " + Utils.getToken(getBaseContext(), renewToken));
                HttpResponse httpResponse = httpClient.execute(getRequest);
                StringBuilder stringBuilder = new StringBuilder(1024);
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                String reading;
                while ((reading = br.readLine()) != null) {
                    stringBuilder.append(reading);
                }
                networkResponse.setRawData(stringBuilder.toString());
                networkResponse.setStatusLine(httpResponse.getStatusLine());
                if (statusCode == 200) {
                    publishProgress("Parsing...");
                    Gson gson = new GsonBuilder().create();
                    Package[] packages = gson.fromJson(stringBuilder.toString(), Package[].class);
                    for (int i = 0; packages != null && i < packages.length; i++) {
                        Package packageI = packages[i];
                        if ("active".equals(packageI.getStatus())) {
                            mPackages.add(packageI);
                        }
                    }
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
        protected void onPostExecute(NetworkResponse networkResponse) {
            mPackageSearchTask = null;
            mProgressDisplay.showProgress(false);
            mPackageAdapter.notifyDataSetChanged();
        }
    }

    class PersonRegistrationTask extends AsyncTask<Boolean, String, NetworkResponse> {

        @Override
        protected NetworkResponse doInBackground(Boolean... params) {
            NetworkResponse networkResponse = new NetworkResponse();
            boolean renewToken = params[0];
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost();
                String urlString = String.format(getString(R.string.register_url), mOrganization.getId());

                URI uri = new URI(urlString);
                postRequest.setURI(uri);
                String[] headersArray = getResources().getStringArray(R.array.register_request_headers);
                for (String header : headersArray) {
                    String[] headers = header.split(",");
                    postRequest.setHeader(headers[0], headers[1]);
                }
                postRequest.setHeader("Authorization", "Bearer " + Utils.getToken(getBaseContext(), renewToken));
                Gson gson = new GsonBuilder().create();
                for (PersonRequest request : mPersonRequests) {
                    String personRequestJson = gson.toJson(request);
                    postRequest.setEntity(new StringEntity(personRequestJson));
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
                    if (statusCode == 201) {
                        publishProgress("Parsing...");
                        PersonRequest personRequestResponse = gson.fromJson(stringBuilder.toString(), PersonRequest.class);
                        mPersonRequestResponses.add(personRequestResponse);
                    }
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
        protected void onPostExecute(NetworkResponse networkResponse) {
            mPersonRegistrationTask = null;
            mProgressDisplay.showProgress(false);
            int statusCode = networkResponse.getStatusLine().getStatusCode();
            if (statusCode == 201) {
                Intent intent = new Intent(getApplicationContext(), RegistrationCompleteActivity.class);
                String personJson = getIntent().getStringExtra(Constants.PERSON_JSON);
                intent.putExtra(Constants.PERSON_JSON, personJson);
                String organizationJson = getIntent().getStringExtra(Constants.ORGANIZATION_JSON);
                intent.putExtra(Constants.ORGANIZATION_JSON, organizationJson);
                String passwordAccountJson = getIntent().getStringExtra(Constants.PASSWORD_ACCOUNT);
                intent.putExtra(Constants.PASSWORD_ACCOUNT, passwordAccountJson);
                String securityQuestionJson = getIntent().getStringExtra(Constants.SECURITY_QUESTIONS);
                intent.putExtra(Constants.SECURITY_QUESTIONS, securityQuestionJson);
                Gson gson = new GsonBuilder().create();
                String personRequestResponses = gson.toJson(mPersonRequestResponses);
                intent.putExtra(Constants.PERSON_REQUESTS, personRequestResponses);
                startActivity(intent);
                finish();
            }
        }
    }
}
