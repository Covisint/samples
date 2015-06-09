# User Registration Flow

This guide demonstrates the major steps involved in registering user in the Covisint Platform. Let's see how this is done using the Platform SDKs. Our pretend story will be that we want to register a person with username "jsmith" and also to the organization(s) with name "Covisint".

First, we will import the person SDK library from Maven Central.

```xml
<dependency>
  <groupId>com.covisint.platform.person.client</groupId>
  <artifactId>person-client</artifactId>
  <version>1.0.0.RELEASE</version>
</dependency>
```

We will also need the authn SDK to create password account for the person, so pull those in as well.

```xml
<dependency>
  <groupId>com.covisint.platform.authn.client</groupId>
  <artifactId>authn-client</artifactId>
  <version>2.0.0.RELEASE</version>
</dependency>
```

The Java SDKs perform auto-authentication with our OAuth servers. To specify the credentials used to retrieve access tokens, we will create a file named <strong>client.conf</strong> with the following contents, and place it at the root of the application's classpath:

    authServiceBaseUrl=https://api.covapp.io/oauth/v1
    applicationId=<your app id>
    clientId=<your client id>
    clientSecret=<your client secret>
    
Now that we have our dependencies imported and configured, the next step is to set up the new person being registered.

```java
PersonClient personClient = // set up the person client

Person personToAdd = null; // set up the new person being registered

Person addedPerson = personClient.add(personToAdd).checkedGet();
String newPersonId = addedPerson.getId();

```
Now we will add a password account for the created person.

```java
PasswordAccountClient passwordAccountClient = // set up the password account client
String passwordPolicyId = //The password policy id.
String authenticationPolicyId = //The authentication policy id.
PasswordAccount passwordAccount = new PasswordAccount().setUsername(addedPerson.getUsername())
           .setPassword("$up3r$3creT").setAuthnPolicyId(authenticationPolicyId).setPasswordPolicyId(passwordPolicyId)
           .setVersion(1L); //Set up password account for created person

passwordAccountClient.updatePasswordAccount(newPersonId, passwordAccount); 

```
Now we will create security question account for the created person.

```java
SecurityQuestionAccountClient securityQuestionAccountClient = // set up the security question client

String firstSecurityQuestionId = // first security question id.
String secondSecurityQuestionId = // second security question id.

Question firstAnswer = Question.withAnswer(firstSecurityQuestionId, "Answer to first question.");
Question secondAnswer = Question.withAnswer(secondSecurityQuestionId, "Answer to second question.");

SecurityQuestionAccount account = new SecurityQuestionAccount().setId(personId).setVersion(1L)
                .setQuestions(Arrays.asList(firstAnswer, secondAnswer)); //Create security question account for created person.

securityQuestionAccountClient.update(newPersonId, securityQuestionAccount).checkedGet();

```

And finally activate the created person.

```java
personClient.activate(newPersonId);

```
Now user is registered and activated in the Covisint Platform.