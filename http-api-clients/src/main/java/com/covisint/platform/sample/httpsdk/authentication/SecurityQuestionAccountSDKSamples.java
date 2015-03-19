/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.authentication;

import java.util.Arrays;
import java.util.List;

import com.covisint.core.http.service.core.Page;
import com.covisint.platform.authn.client.sdk.SecurityQuestionAccountSDK;
import com.covisint.platform.authn.client.sdk.SecurityQuestionAccountSDK.SecurityQuestionAccountClient;
import com.covisint.platform.authn.client.sdk.SecurityQuestionSDK;
import com.covisint.platform.authn.client.sdk.SecurityQuestionSDK.SecurityQuestionClient;
import com.covisint.platform.authn.core.account.securityquestion.SecurityQuestionAccount;
import com.covisint.platform.authn.core.account.securityquestion.SecurityQuestionAccount.Question;
import com.covisint.platform.authn.core.securityquestion.SecurityQuestion;
import com.covisint.platform.sample.httpsdk.ServiceUrl;

public final class SecurityQuestionAccountSDKSamples {

    private static SecurityQuestionAccountClient createAccountClient() {
        return new SecurityQuestionAccountSDK(ServiceUrl.AUTHN_V2.getValue()).newClient();
    }

    private static SecurityQuestionClient createSecurityQuestionClient() {
        return new SecurityQuestionSDK(ServiceUrl.AUTHN_V2.getValue()).newClient();
    }

    /** Retrieve a person's security question account. */
    public static void getSecurityQuestionAccount() {

        // Create a new instance of the security question account client.
        SecurityQuestionAccountClient client = createAccountClient();

        // The id of the person.
        String personId = "e4d7e969af82";

        // Retrieve the account.
        SecurityQuestionAccount account = client.get(personId).checkedGet();

        System.out.println("Retrieved security question account: " + account);
    }

    /**
     * Sets up a person's security question account. First, searches for available security questions that the person
     * can choose from.
     */
    public static void setSecurityQuestionAccount() {

        // Create the security question and account clients.
        SecurityQuestionClient questionClient = createSecurityQuestionClient();
        SecurityQuestionAccountClient accountClient = createAccountClient();

        // First, let's perform a search on available questions. We will request to those questions defined for the
        // given organization.
        String ownerId = "612aae4bc62a";
        String ownerType = "organization";

        // Retrieve default page of results.
        Page page = Page.DEFAULT;

        // Perform the search on available questions.
        List<SecurityQuestion> availableQuestions = questionClient.search(ownerId, ownerType, page).checkedGet();

        // Let's just assume the person wants to answer the first two questions in the search results.
        String firstQuestionId = availableQuestions.get(0).getId();
        String secondQuestionId = availableQuestions.get(1).getId();

        // The id of the person whose account is being set up.
        String personId = "3792c2000ad5";

        // Build the account object.
        Question firstAnswer = Question.withAnswer(firstQuestionId, "Answer to first question.");
        Question secondAnswer = Question.withAnswer(secondQuestionId, "Answer to second question.");

        SecurityQuestionAccount account = new SecurityQuestionAccount().setId(personId);
        account.setQuestions(Arrays.asList(firstAnswer, secondAnswer));
        account.setVersion(1L);

        // Create or update the account.
        SecurityQuestionAccount updated = accountClient.update(personId, account).checkedGet();

        System.out.println("Updated or specified security question account: " + updated);
    }
}
