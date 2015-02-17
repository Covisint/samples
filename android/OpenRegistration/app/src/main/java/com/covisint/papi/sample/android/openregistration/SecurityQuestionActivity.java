package com.covisint.papi.sample.android.openregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.covisint.papi.sample.android.openregistration.model.PasswordAccount;
import com.covisint.papi.sample.android.openregistration.model.error.Error;
import com.covisint.papi.sample.android.openregistration.model.organization.Organization;
import com.covisint.papi.sample.android.openregistration.model.person.Person;
import com.covisint.papi.sample.android.openregistration.model.securityquestion.SecurityQuestion;
import com.covisint.papi.sample.android.openregistration.model.securityquestion.SecurityQuestionAnswer;
import com.covisint.papi.sample.android.openregistration.util.Constants;
import com.covisint.papi.sample.android.openregistration.util.NetworkResponse;
import com.covisint.papi.sample.android.openregistration.util.ProgressDisplay;
import com.covisint.papi.sample.android.openregistration.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;


public class SecurityQuestionActivity extends Activity {

    private Organization mOrganization;
    private Person mPerson;
    private PasswordAccount mPasswordAccount;
    private View mProgressView;
    private View mSecurityQuestionsFormView;
    private TextView mErrorMessage;
    private ProgressDisplay mProgressDisplay;
    private FetchSecurityQuestionTask mFetchSecurityQuestionTask;
    private SecurityQuestion[] mSecurityQuestions;
    private TextView mSubmitStatus;
    private Spinner mQuestion1Spinner;
    private Spinner mQuestion2Spinner;
    private ArrayAdapter<SecurityQuestion> mQuestion1Adapter;
    private ArrayList<SecurityQuestion> mQuestion1List;
    private ArrayList<SecurityQuestion> mQuestion2List;
    private ArrayAdapter<SecurityQuestion> mQuestion2Adapter;

    private SecurityQuestionAnswer mSecurityQuestionAnswer;
    private Button mSecQueSubmissionButton;
    private SecurityQuestion mDummyQForSelectOne;
    private EditText mAnswer1Field;
    private EditText mAnswer2Field;
    private SubmitSecurityQuestionTask mSubmitSecurityQuestionTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_question);

        mSecurityQuestionAnswer = new SecurityQuestionAnswer();

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

        String passwordAccountJson = getIntent().getStringExtra(Constants.PASSWORD_ACCOUNT);
        if (passwordAccountJson == null) {
            passwordAccountJson = "{\"id\":\"IHSK63T4\",\"version\":1,\"creator\":\"jlmMCCGIqTl4AOo3qN1xKlb11AZliXGO\",\"creatorAppId\":\"jlmMCCGIqTl4AOo3qN1xKlb11AZliXGO\",\"creation\":1423729454844,\"realm\":\"JWDEMOA-QA\",\"username\":\"NITIN2\",\"passwordPolicyId\":\"2816418c-8409-4866-8d69-a07ab7a79a19\",\"authenticationPolicyId\":\"205fd2f1-5877-4cc6-a091-9cf4a5ff8304\",\"expiration\":1439281454883,\"locked\":false,\"unlockInstant\":0}";
        }
        mPasswordAccount = gson.fromJson(passwordAccountJson, PasswordAccount.class);

        mSecurityQuestionsFormView = findViewById(R.id.security_question_submission_form);
        mProgressView = findViewById(R.id.security_que_submission_progress);
        mErrorMessage = (TextView) findViewById(R.id.message);
        mSubmitStatus = (TextView) findViewById(R.id.submit_sec_que_status);

        mDummyQForSelectOne = new SecurityQuestion();
        SecurityQuestion.Question[] questions = new SecurityQuestion.Question[1];
        questions[0] = mDummyQForSelectOne.new Question();
        questions[0].setText(getString(R.string.select_one));
        mDummyQForSelectOne.setQuestion(questions);
        mQuestion1Spinner = (Spinner) findViewById(R.id.question_1);
        mQuestion1List = new ArrayList<SecurityQuestion>(4);
        mQuestion1List.add(mDummyQForSelectOne);
        mQuestion1Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mQuestion1List);
        mQuestion1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mQuestion1Spinner.setAdapter(mQuestion1Adapter);

        mAnswer1Field = (EditText) findViewById(R.id.answer_1);

        mQuestion2Spinner = (Spinner) findViewById(R.id.question_2);
        mQuestion2List = new ArrayList<SecurityQuestion>(4);
        mQuestion2List.add(mDummyQForSelectOne);
        mQuestion2Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mQuestion2List);
        mQuestion2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mQuestion2Spinner.setAdapter(mQuestion2Adapter);

        mAnswer2Field = (EditText) findViewById(R.id.answer_2);

        mSecQueSubmissionButton = (Button) findViewById(R.id.sec_ques_answer_submission_button);
        mSecQueSubmissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSubmission();
            }
        });

        if (mProgressDisplay == null)
            mProgressDisplay = new ProgressDisplay(this, mSecurityQuestionsFormView, mProgressView);
        if (mFetchSecurityQuestionTask != null) {
            mProgressDisplay.showProgress(true);
        } else {
            if (mSecurityQuestions == null) {
                mFetchSecurityQuestionTask = new FetchSecurityQuestionTask();
                mFetchSecurityQuestionTask.execute(false);
                mProgressDisplay.showProgress(true);
            }
        }
    }

    private void attemptSubmission() {
        SecurityQuestion question1 = (SecurityQuestion) mQuestion1Spinner.getSelectedItem();
        SecurityQuestion question2 = (SecurityQuestion) mQuestion2Spinner.getSelectedItem();
        String answer1 = mAnswer1Field.getText().toString();
        String answer2 = mAnswer2Field.getText().toString();
        // Check whether questions are selected
        if ((question1 == mDummyQForSelectOne) ||
                (question2 == mDummyQForSelectOne)) {
            mErrorMessage.setText("Select a question.");
        } else {
            // Check if the answers are not empty
            if (answer1 != null && answer1.trim().length() > 0) {
                if (answer2 != null && answer2.trim().length() > 0) {
                    mSecurityQuestionAnswer.setId(mPerson.getId());
                    mSecurityQuestionAnswer.setVersion(0L);
                    SecurityQuestionAnswer.SecurityQuestion[] questions = new SecurityQuestionAnswer.SecurityQuestion[2];
                    questions[0] = mSecurityQuestionAnswer.new SecurityQuestion();
                    SecurityQuestionAnswer.Question question = mSecurityQuestionAnswer.new Question();
                    question.setId(question1.getId());
                    question.setType("question");
                    questions[0].setQuestion(question);
                    questions[0].setAnswer(answer1);

                    questions[1] = mSecurityQuestionAnswer.new SecurityQuestion();
                    question = mSecurityQuestionAnswer.new Question();
                    question.setId(question2.getId());
                    question.setType("question");
                    questions[1].setQuestion(question);
                    questions[1].setAnswer(answer2);
                    mSecurityQuestionAnswer.setQuestions(questions);
                    mSubmitSecurityQuestionTask = new SubmitSecurityQuestionTask();
                    mSubmitSecurityQuestionTask.execute(false);
                    mProgressDisplay.showProgress(true);
                } else {
                    mAnswer1Field.setError(getString(R.string.error_field_required));
                }
            } else {
                mAnswer1Field.setError(getString(R.string.error_field_required));
            }
        }
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

    public class FetchSecurityQuestionTask extends AsyncTask<Boolean, String, NetworkResponse> {

        @Override
        protected NetworkResponse doInBackground(Boolean... params) {
            boolean renewToken = params[0];
            NetworkResponse networkResponse = new NetworkResponse();
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet getRequest = new HttpGet();
                String urlString = getString(R.string.security_question_url);

                URI uri = new URI(urlString);
                getRequest.setURI(uri);
                String[] headersArray = getResources().getStringArray(R.array.security_question_request_headers);
                for (String header : headersArray) {
                    String[] headers = header.split(",");
                    getRequest.setHeader(headers[0], headers[1]);
                }
                getRequest.setHeader("Authorization", "Bearer " + Utils.getToken(getBaseContext(), renewToken));
                HttpResponse httpResponse = httpClient.execute(getRequest);
                StringBuilder stringBuilder = new StringBuilder(1024);
                networkResponse.setStatusLine(httpResponse.getStatusLine());
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
                    networkResponse.setRawData(jsonResponse);
                    publishProgress("Parsing response...");
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
            mFetchSecurityQuestionTask = null;
            Gson gson = new GsonBuilder().create();
            int status = networkResponse.getStatusLine().getStatusCode();
            if (status == 200) {
                String jsonResponse = networkResponse.getRawData();
                mSecurityQuestions = gson.fromJson(jsonResponse, SecurityQuestion[].class);
                int questionsLength = mSecurityQuestions.length;
                for (int i = 0; i < questionsLength; i++) {
                    if (i == 0)
                        continue;
                    if (i < questionsLength / 2 + 1) {
                        mQuestion1List.add(mSecurityQuestions[i]);
                    } else {
                        mQuestion2List.add(mSecurityQuestions[i]);
                    }
                }
                mQuestion1Adapter.notifyDataSetChanged();
                mQuestion2Adapter.notifyDataSetChanged();
                mProgressDisplay.showProgress(false);
            } else if (status == 401) {
                if ("Access Token Expired".equals(networkResponse.getStatusLine().getReasonPhrase())) {
                    mSubmitStatus.setText("Token expired! Getting token...");
                    mFetchSecurityQuestionTask = new FetchSecurityQuestionTask();
                    mFetchSecurityQuestionTask.execute(true);
                    mProgressDisplay.showProgress(true);
                }
            } else if (status == 400) {
                // Error from Server
                com.covisint.papi.sample.android.openregistration.model.error.Error error = gson.fromJson(networkResponse.getRawData(), Error.class);
                String errorMessageToDisplay = error.getApiMessage() + "\n" + error.getApiStatusCode();
                mErrorMessage.setText(errorMessageToDisplay.trim());
                mErrorMessage.setVisibility(View.VISIBLE);
            } else {
                // Something went wrong
                mErrorMessage.setText(networkResponse.getStatusLine().toString());
                mErrorMessage.setVisibility(View.VISIBLE);
            }
        }
    }

    public class SubmitSecurityQuestionTask extends AsyncTask<Boolean, String, NetworkResponse> {

        @Override
        protected NetworkResponse doInBackground(Boolean... params) {
            boolean renewToken = params[0];
            NetworkResponse networkResponse = new NetworkResponse();
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPut putRequest = new HttpPut();
                String urlString = String.format(getString(R.string.security_question_submit_url), mPerson.getId());

                URI uri = new URI(urlString);
                putRequest.setURI(uri);
                String[] headersArray = getResources().getStringArray(R.array.security_question_submit_request_headers);
                for (String header : headersArray) {
                    String[] headers = header.split(",");
                    putRequest.setHeader(headers[0], headers[1]);
                }
                putRequest.setHeader("Authorization", "Bearer " + Utils.getToken(getBaseContext(), renewToken));
                Gson gson = new GsonBuilder().create();
                String securityQuestionAnswerJson = gson.toJson(mSecurityQuestionAnswer);
                putRequest.setEntity(new StringEntity(securityQuestionAnswerJson));
                HttpResponse httpResponse = httpClient.execute(putRequest);
                StringBuilder stringBuilder = new StringBuilder(1024);
                networkResponse.setStatusLine(httpResponse.getStatusLine());
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
                networkResponse.setRawData(jsonResponse);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    publishProgress("Parsing response...");
                    SecurityQuestionAnswer securityQuestionResponse = gson.fromJson(jsonResponse, SecurityQuestionAnswer.class);
                    SecurityQuestionAnswer.SecurityQuestion[] returnedQuestions = securityQuestionResponse.getQuestions();
                    SecurityQuestionAnswer.SecurityQuestion[] answeredQuestions = mSecurityQuestionAnswer.getQuestions();
                    for (int i = 0; returnedQuestions != null && i < returnedQuestions.length; i++) {
                        String returnedQuestion = returnedQuestions[i].getQuestion().getId();
                        for (int j = 0; answeredQuestions != null && j < answeredQuestions.length; j++) {
                            String answeredQuestion = answeredQuestions[i].getQuestion().getId();
                            if (returnedQuestion.equals(answeredQuestion)) {
                                returnedQuestions[i].setAnswer(answeredQuestions[j].getAnswer());
                                break;
                            }
                        }
                    }
                    mSecurityQuestionAnswer = securityQuestionResponse;
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
            mProgressDisplay.showProgress(false);
            if (networkResponse == null) {
                mErrorMessage.setText("Something went wrong!");
            } else if (networkResponse.getStatusLine().getStatusCode() == 401) {
                // Token expired.
                if ("Access Token Expired".equals(networkResponse.getStatusLine().getReasonPhrase())) {
                    mSubmitStatus.setText("Token expired! Getting token...");
                    mSubmitSecurityQuestionTask = new SubmitSecurityQuestionTask();
                    mSubmitSecurityQuestionTask.execute(true);
                    mProgressDisplay.showProgress(true);
                }
            } else if (networkResponse.getStatusLine().getReasonPhrase().startsWith("HTTP")) {
                mErrorMessage.setText(networkResponse.getStatusLine().getReasonPhrase());
            } else {
                Gson gson = new GsonBuilder().create();
                try {
                    Intent intent = new Intent(getBaseContext(), PackageSelectionActivity.class);
                    String personJson = getIntent().getStringExtra(Constants.PERSON_JSON);
                    intent.putExtra(Constants.PERSON_JSON, personJson);
                    String organizationJson = getIntent().getStringExtra(Constants.ORGANIZATION_JSON);
                    intent.putExtra(Constants.ORGANIZATION_JSON, organizationJson);
                    String passwordAccountJson = getIntent().getStringExtra(Constants.PASSWORD_ACCOUNT);
                    intent.putExtra(Constants.PASSWORD_ACCOUNT, passwordAccountJson);

                    String securityQuestionJson = gson.toJson(mSecurityQuestionAnswer, SecurityQuestionAnswer.class);
                    intent.putExtra(Constants.SECURITY_QUESTIONS, securityQuestionJson);
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
    }
}