# User Registration Flow

This guide demonstrates the major steps of a custom user registration.

First, we will import the service SDK library from Maven Central.

```xml
<dependency>
  <groupId>com.covisint.platform.service.client</groupId>
  <artifactId>service-client</artifactId>
  <version>1.0.0.RELEASE</version>
</dependency>
```

We will also need the person and organization SDKs for the search operations, so pull those in as well.

```xml
<dependency>
  <groupId>com.covisint.platform.person.client</groupId>
  <artifactId>person-client</artifactId>
  <version>1.0.0.RELEASE</version>
</dependency>

<dependency>
  <groupId>com.covisint.platform.organization.client</groupId>
  <artifactId>organization-client</artifactId>
  <version>1.0.0.RELEASE</version>
</dependency>
```

The Java SDKs perform auto-authentication with our OAuth servers.  To specify the credentials used to retrieve access tokens, we will create a file named <strong>client.conf</strong> with the following contents, and place it at the root of the application's classpath:

    authServiceBaseUrl=https://api.covapp.io/oauth/v1
    applicationId=<your app id>
    clientId=<your client id>
    clientSecret=<your client secret>
    
Now that we have our dependencies imported and configured, the next step is to ...