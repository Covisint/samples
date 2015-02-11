package com.covisint.papi.sample.android.openregistration;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.covisint.papi.sample.android.openregistration.model.PasswordAccount;
import com.covisint.papi.sample.android.openregistration.model.organization.Organization;
import com.covisint.papi.sample.android.openregistration.model.person.Person;
import com.covisint.papi.sample.android.openregistration.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class SecurityQuestionActivity extends Activity {

    private Organization mOrganization;
    private Person mPerson;
    private PasswordAccount mPasswordAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_question);

        Gson gson = new GsonBuilder().create();

        String personJson = getIntent().getStringExtra(Constants.PERSON_JSON);
        mPerson = gson.fromJson(personJson, Person.class);

        String organizationJson = getIntent().getStringExtra(Constants.ORGANIZATION_JSON);
        mOrganization = gson.fromJson(organizationJson, Organization.class);

        String passwordAccountJson = getIntent().getStringExtra(Constants.PASSWORD_ACCOUNT);
        mPasswordAccount = gson.fromJson(passwordAccountJson, PasswordAccount.class);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_security_question, menu);
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
}
