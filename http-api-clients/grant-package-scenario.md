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

Now that we have our dependencies imported, the next step is to search for the grantees.  Lets start with jsmith users:

```java
PersonClient personClient = // set up the person client
String username = "jsmith";
List<Person> allJsmith = personClient.search(null, null, username, null, Page.DEFAULT).checkedGet();
```

We will now assign the package grant to each person returned from the search...

```java
ServicePackageGrantClient grantClient = // set up the package grant client
String packageId = // the id of the package to grant

for (Person person : allJsmith) {
  grantClient.assignPersonGrant(person.getId(), packageId);
}
```

We have granted this package to all jsmith users.  Next we will issue the grant to the organizations.

```java
OrganizationClient orgClient = // set up the organization client
String orgName = "Covisint";
List<Organization> allCovisint = orgClient.search(Arrays.asList(orgName), null, null, Page.DEFAULT).checkedGet();

for (Organization org : allCovisint) {
    grantClient.assignOrganizationGrant(org.getId(), packageId);
}
```
