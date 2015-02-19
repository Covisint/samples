package com.covisint.papi.sample.android.openregistration;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.covisint.papi.sample.android.openregistration.model.PasswordAccount;
import com.covisint.papi.sample.android.openregistration.model.contact.Address;
import com.covisint.papi.sample.android.openregistration.model.contact.Phone;
import com.covisint.papi.sample.android.openregistration.model.organization.Organization;
import com.covisint.papi.sample.android.openregistration.model.person.Person;
import com.covisint.papi.sample.android.openregistration.model.person.PersonRequest;
import com.covisint.papi.sample.android.openregistration.model.securityquestion.SecurityQuestion;
import com.covisint.papi.sample.android.openregistration.model.securityquestion.SecurityQuestionAnswer;
import com.covisint.papi.sample.android.openregistration.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by Nitin.Khobragade on 2/17/2015.
 */
public class RegistrationCompleteActivity extends Activity{

    private TextView mNameTextView;
    private TextView mAddressTextView;
    private TextView mContactsTextView;
    private TextView mOrganizationTextView;
    private TextView mUserIdTextView;
    private TextView mLanguageTextView;
    private TextView mTimeZoneTextView;
    private TextView mSecQ1TextView;
    private TextView mSecQ2TextView;
    private TextView mPackagesTextView;
    private Person mPerson;
    private Organization mOrganization;
    private PasswordAccount mPasswordAccount;
    private PersonRequest[] mPersonRequestResponses;
    private SecurityQuestion[] mSecurityQuestion;
    private SecurityQuestionAnswer mSecurityQuestionAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new GsonBuilder().create();
        String personJson = getIntent().getStringExtra(Constants.PERSON_JSON);
        mPerson = gson.fromJson(personJson, Person.class);

        String organizationJson = getIntent().getStringExtra(Constants.ORGANIZATION_JSON);
        mOrganization = gson.fromJson(organizationJson, Organization.class);
        String passwordAccountJson = getIntent().getStringExtra(Constants.PASSWORD_ACCOUNT);
        mPasswordAccount = gson.fromJson(passwordAccountJson, PasswordAccount.class);

        String securityQuestionJson = getIntent().getStringExtra(Constants.SECURITY_QUESTIONS);
        mSecurityQuestion = gson.fromJson(securityQuestionJson, SecurityQuestion[].class);

        String securityQuestionAnswersJson = getIntent().getStringExtra(Constants.SECURITY_QUESTION_ANSWERS);
        mSecurityQuestionAnswer = gson.fromJson(securityQuestionAnswersJson, SecurityQuestionAnswer.class);


        String personRequestResponses = getIntent().getStringExtra(Constants.PERSON_REQUESTS);
        mPersonRequestResponses = gson.fromJson(personRequestResponses, PersonRequest[].class);
        setContentView(R.layout.activity_registration_status);
        mNameTextView = (TextView) findViewById(R.id.name);
        mNameTextView.setText(mPerson.getName().getFullName());

        mAddressTextView = (TextView) findViewById(R.id.address);
        StringBuilder builder = new StringBuilder();
        Address[] addresses = mPerson.getAddresses();
        for (Address address : addresses) {
            builder.append(address.getAddress() + "\n");
        }
        mAddressTextView.setText(builder.toString().trim());

        mContactsTextView = (TextView) findViewById(R.id.contacts);
        builder = new StringBuilder();
        Phone[] phones = mPerson.getPhones();
        for (Phone phone : phones) {
            if (phone != null) {
                builder.append(phone.getType());
                builder.append(" : ");
                builder.append(phone.getNumber());
                builder.append("\n");
            }
        }
        if (mPerson.getEmail() != null && mPerson.getEmail().length() > 0)
            builder.append(mPerson.getEmail());
        mContactsTextView.setText(builder.toString().trim());

        mOrganizationTextView = (TextView) findViewById(R.id.organization);
        mOrganizationTextView.setText(mOrganization.getName());

        mUserIdTextView = (TextView) findViewById(R.id.user_id);
        mUserIdTextView.setText(mPasswordAccount.getUsername());

        mLanguageTextView = (TextView) findViewById(R.id.language);
        mLanguageTextView.setText(mPerson.getLanguage());

        mTimeZoneTextView = (TextView) findViewById(R.id.time_zone);
        mTimeZoneTextView.setText(mPerson.getTimezone());

        // Pass the questions for display
        mSecQ1TextView = (TextView) findViewById(R.id.security_question_1);
        mSecQ1TextView.setText(mSecurityQuestion[0].getQuestions()[0].getText());

        mSecQ2TextView = (TextView) findViewById(R.id.security_question_2);
        mSecQ2TextView.setText(mSecurityQuestion[1].getQuestions()[0].getText());

        mPackagesTextView = (TextView) findViewById(R.id.packages);
        builder = new StringBuilder();
        for (int i = 0; i < mPersonRequestResponses.length; i++) {
            builder.append(mPersonRequestResponses[i].getServicePackageRequest().getPackageId());
            builder.append("\n");
        }
        mPackagesTextView.setText(builder.toString().trim());
    }
}
