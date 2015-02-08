package com.covisint.papi.sample.android.openregistration;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.covisint.papi.sample.android.openregistration.model.PAPIModel;
import com.covisint.papi.sample.android.openregistration.model.organization.Organization;
import com.covisint.papi.sample.android.openregistration.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


public class OrganizationList extends ListActivity {
    public static PAPIModel[] organizations;
    private List<Organization> organizationList;
    private Organization mDummyOrgForBackOperation = new Organization("Search again...");
    private ArrayAdapter<Organization> mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We'll define a custom screen layout here (the one shown above), but
        // typically, you could just use the standard ListActivity layout.
        setContentView(R.layout.activity_organization_list);
        organizationList = new ArrayList<>();
        if (organizations != null) {
            for (PAPIModel organization : organizations) {
                if (organization instanceof Organization)
                    organizationList.add((Organization) organization);
            }
        }
        organizationList.add(mDummyOrgForBackOperation);
        mListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, organizationList);
        setListAdapter(mListAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_organization_list, menu);
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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Organization organization = organizationList.get(position);
        if (organization == mDummyOrgForBackOperation) {
            Intent intent = new Intent(getBaseContext(), SearchOrganization.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(getBaseContext(), UserInformationActivity.class);
            Gson gson = new GsonBuilder().create();
            intent.putExtra(Constants.ORGANIZATION_JSON, gson.toJson(organization));
            startActivity(intent);
            finish();
        }
    }
}
