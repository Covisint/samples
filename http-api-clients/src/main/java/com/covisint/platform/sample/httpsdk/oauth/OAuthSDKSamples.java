/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.covisint.platform.sample.httpsdk.oauth;

import java.util.List;

import com.covisint.core.http.service.core.Page;
import com.covisint.platform.oauth.client.sdk.ClientSecretGrantTypeSDK;
import com.covisint.platform.oauth.client.sdk.ClientSecretGrantTypeSDK.ClientSecretGrantTypeClient;
import com.covisint.platform.oauth.client.sdk.SubjectSDK;
import com.covisint.platform.oauth.client.sdk.SubjectSDK.SubjectClient;
import com.covisint.platform.oauth.client.token.sdk.TokenSDK;
import com.covisint.platform.oauth.client.token.sdk.TokenSDK.TokenClient;
import com.covisint.platform.oauth.core.domain.ClientSecretGrantType;
import com.covisint.platform.oauth.core.domain.Subject;
import com.covisint.platform.oauth.core.token.Token;
import com.covisint.platform.sample.httpsdk.ServiceUrl;

public final class OAuthSDKSamples {

    private static TokenClient createTokenClient() {
        return new TokenSDK(ServiceUrl.OAUTH_V1.getValue()).newClient();
    }

    private static ClientSecretGrantTypeClient createClientSecretGrantClient() {
        return new ClientSecretGrantTypeSDK(ServiceUrl.OAUTH_V1.getValue()).newClient();
    }

    private static SubjectClient createSubjectClient() {
        return new SubjectSDK(ServiceUrl.OAUTH_V1.getValue()).newClient();
    }

    /** Retrieves an OAuth access token for a given set of client credentials. */
    public static void getAccessToken() {

        // Create the token client.
        TokenClient client = createTokenClient();

        String clientId = "023da47902f4";
        String secret = "7ee0419fb281";

        // Exchange the given client id and secret for a token.
        Token token = client.getToken(clientId, secret).checkedGet();

        System.out.println("Retrieved access token: " + token.getEncodedToken());
    }

    /** Demonstrates CRUD on subject resources. */
    public static void subjectCRUD() {

        // First create the subject client.
        SubjectClient client = createSubjectClient();

        // Set up our new oauth subject.
        Subject subject = new Subject();
        subject.setId("jsmith");
        subject.setVersion(1L);

        // Create the subject.
        Subject createdSubject = client.update(subject).checkedGet();

        System.out.println("Created subject:" + createdSubject);

        // Now retrieve the subject from the server.
        Subject retrievedSubject = client.get(createdSubject.getId()).checkedGet();

        System.out.println("Retrieved subject:" + retrievedSubject);

        // Perform a search for all subjects that exist.
        List<Subject> allSubjects = client.search(Page.DEFAULT).checkedGet();

        System.out.println("Found " + allSubjects.size() + " subjects.");

        // Finally, lets delete our subject.
        client.delete(retrievedSubject.getId()).checkedGet();

        System.out.println("Successfully deleted subject.");
    }

    /** Demonstrates CRUD (and also search) on client credential grants to a subject. */
    public static void clientCredentialsGrantCRUD() {

        // Create the client.
        ClientSecretGrantTypeClient client = createClientSecretGrantClient();

        // The id of the subject for whom the grant is being issued.
        String subjectId = "jsmith";

        // The id of the application for whom the grant is being issued.
        String appId = "2b1747568c9d";

        // Set up the grant type resource.
        ClientSecretGrantType grant = new ClientSecretGrantType();
        grant.setSubjectId(subjectId);
        grant.setAppId(appId);

        // Create the new grant for the subject.
        ClientSecretGrantType created = client.add(subjectId, grant).checkedGet();

        System.out.println("Created client id/secret grant type: " + created);

        // Now retrieve it from the server.
        ClientSecretGrantType retrieved = client.get(subjectId, created.getId()).checkedGet();

        System.out.println("Retrieved client id/secret grant type: " + retrieved);

        // Let's search for all grants issued for this subject.
        List<ClientSecretGrantType> allGrants = client.listClientSecretGrants(subjectId, Page.DEFAULT).checkedGet();

        System.out.println("Found " + allGrants.size() + " grants for subject " + subjectId);

        // Delete the grant.
        client.delete(subjectId, retrieved.getId()).checkedGet();

        System.out.println("Successfully deleted client id/secret grant type.");
    }
}
