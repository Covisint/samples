package com.covisint.papi.sample.android.openregistration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.covisint.papi.sample.android.openregistration.model.UserInformation;
import com.covisint.papi.sample.android.openregistration.model.organization.Organization;
import com.covisint.papi.sample.android.openregistration.model.person.Name;
import com.covisint.papi.sample.android.openregistration.model.person.Person;
import com.covisint.papi.sample.android.openregistration.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;


/**
 * A login screen that offers login via email/password.
 */
public class UserInformationActivity extends Activity {

    private Organization mOrganization;
    private Button mUserInfoSubmissionButton;
    private Spinner mPrefix;
    private EditText mGivenName;
    private EditText mMiddleName;
    private EditText mSurname;
    private EditText mJobTitle;

    // UI references.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        String organizationJson = getIntent().getStringExtra(Constants.ORGANIZATION_JSON);
        Gson gson = new GsonBuilder().create();
        mOrganization = gson.fromJson(organizationJson, Organization.class);
        TextView orgTitleTextView = (TextView) findViewById(R.id.organization_name);
        orgTitleTextView.setText(mOrganization.getName());
        mPrefix = (Spinner) findViewById(R.id.prefix);
        ArrayAdapter<CharSequence> prefixAdapter = ArrayAdapter.createFromResource(this, R.array.prefix_arrays, android.R.layout.simple_spinner_item);
        prefixAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrefix.setAdapter(prefixAdapter);

        mGivenName = (EditText) findViewById(R.id.first_name);
        mMiddleName = (EditText) findViewById(R.id.middle_name);
        mSurname = (EditText) findViewById(R.id.last_name);
        mJobTitle = (EditText) findViewById(R.id.job_title);
        mUserInfoSubmissionButton = (Button) findViewById(R.id.user_info_submission_button);
        mUserInfoSubmissionButton.setOnClickListener(new OnClickListener() {
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
        Person person = new Person();
        Name name = new Name();
        Object prefix = mPrefix.getSelectedItem();
        name.setPrefix(prefix.toString());
        String firstName = mGivenName.getText().toString();
        name.setGiven(firstName);
        String middleName = mMiddleName.getText().toString();
        name.setMiddle(middleName);
        String lastName = mSurname.getText().toString();
        name.setSurname(lastName);
        person.setName(name);
        person.setTitle(mJobTitle.getText().toString());
        person.setOrganizationId(mOrganization.getId());
        Gson gson = new GsonBuilder().create();
        String personJson = gson.toJson(person);
        Intent intent = new Intent(this, AddressInputActivity.class);
        intent.putExtra(Constants.PERSON_JSON, personJson);
        startActivity(intent);
        finish();
    }
}



