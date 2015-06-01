# Platform API Java Code Samples
### Introduction
This project contains code samples showcasing the various Java-based platform SDKs available for use.  For each API, there is a related snippet of code that demonstrates how to set up and make the call, and how to consume the response.  Additionally, there exists code samples that show how to perform common operations with the SDK library, such as constructing requests, configuration, extension, error handling, and so on. 

### Quickstart
The Java SDKs are available in Maven Central, so let's begin by importing one of the SDK libraries...

```xml
<dependencies>
  ...
  <dependency>
    <groupId>com.covisint.platform.user.client</groupId>
    <artifactId>user-client</artifactId>
    <version>1.0.0.RELEASE</version>
  </dependency>
  ...
</dependencies>
```

The Java SDKs perform auto-authentication with our OAuth servers.  To specify the credentials used to retrieve access tokens, we will create a file named <strong>client.conf</strong> with the following contents, and place it at the root of the application's classpath:

    authServiceBaseUrl=https://api.covapp.io/oauth/v1
    applicationId=<your app id>
    clientId=<your client id>
    clientSecret=<your client secret>

**[Click here](https://developer.covisint.com/learn/dp/-/book/developer-portal/managing_instances/manage_applications.html) if you need to locate your API credentials.**

Next, we need to build the client object.  This will be used to issue the API calls.

```java
String url = "https://api.covapp.io/person/v1";
PersonClient client = new PersonSDK(url).newClient();
```

Now that we have our basic client instantiated, we can issue a call to retrieve a resource from the server:

```java
String personId = // id of the person to retrieve
Person person = client.get(personId).checkedGet();
```

Congrats, you just made your first API call!  Make sure to browse the various other code samples in this project to master the different use cases and configuration options offered by the client libraries.

### Additional SDKs
Maven coordinates for all other client libraries are listed below.
```xml
<dependency>
  <groupId>com.covisint.platform.authn.client</groupId>
  <artifactId>authn-client</artifactId>
  <version>2.0.0.RELEASE</version>
</dependency>

<dependency>
  <groupId>com.covisint.platform.group.client</groupId>
  <artifactId>group-client</artifactId>
  <version>1.0.0.RELEASE</version>
</dependency>

<dependency>
  <groupId>com.covisint.platform.service.client</groupId>
  <artifactId>service-client</artifactId>
  <version>1.0.0.RELEASE</version>
</dependency>

<dependency>
  <groupId>com.covisint.platform.organization.client</groupId>
  <artifactId>organization-client</artifactId>
  <version>1.0.0.RELEASE</version>
</dependency>
```
