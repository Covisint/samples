# Granting Service Packages

Service packages may be granted to two different entities in the Covisint Platform - a person and an organization.  Lets see how this is done using the Platform SDKs.  Our pretend story will be that we want to grant a particular service package to the person(s) with username "jsmith" and also to the organization(s) with name "Covisint".

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

The Java SDKs perform auto-authentication with our OAuth servers.  To specify the credentials they will use to fetch an access token, we will create a file named <strong>client.conf</strong> with the following contents, and place it at the root of the application's classpath:

    authServiceBaseUrl=https://api.covapp.io/oauth/v1
    applicationId=<your app id>
    clientId=<your client id>
    clientSecret=<your client secret>
    
Now that we have our dependencies imported and configured, the next step is to search for the grantees.  Lets start by searching for "jsmith" users:

```java
PersonClient personClient = // set up the person client
String username = "jsmith";
List<Person> allJsmiths = personClient.search(null, null, username, null, Page.DEFAULT).checkedGet();
```

We will now assign the package grant to each person returned from the search...

```java
ServicePackageGrantClient grantClient = // set up the package grant client
String packageId = // the id of the package to grant

for (Person person : allJsmiths) {
  grantClient.assignPersonGrant(person.getId(), packageId);
}
```

We have granted this package to all jsmith users.  Next we will issue the grant to the organizations.

```java
OrganizationClient orgClient = // set up the organization client
String orgName = "Covisint";
List<Organization> allOrgs = orgClient.search(Arrays.asList(orgName), null, null, Page.DEFAULT).checkedGet();

for (Organization org : allOrgs) {
    grantClient.assignOrganizationGrant(org.getId(), packageId);
}
```

And thats it.  We have granted the package to all persons with username jsmith as well as organizations named Covisint.  If we now want to retrieve the package grants for a given person or organization, its as simple as:

```java
String personId = allJsmiths.get(0).getId(); // Lets get all grants for the first jsmith in our list above
Iterable<ServicePackageGrant> grants = grantClient.listPersonGrants(personId, Page.DEFAULT).checkedGet();
```

The next release of the Package Grant API will add support for revoking package grants from persons and organizations.  This walkthrough will be updated to show how to do that as well.
