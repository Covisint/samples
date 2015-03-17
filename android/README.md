Android application for Open Registration user case of user registration.
Steps to follow to utilize this sample code:
* Go into desired directory.
* git clone https://github.com/Covisint/samples.git
* Go into android directory "cd samples/android/OpenRegistration"
* Open file app/src/main/res/values/strings_network_properties.xml
* Change following properties

```xml
    <string name="app_id">jlmMCCGIqTl4AOo3qN1xKlb11AZliXGO</string>
    <string name="client_id">59cf3f49-0a21-4e13-898d-f447441b88ad</string>
    <string name="client_secret">ec898026-0175-45b5-a4a4-8b9d90800b86</string>
```

Also change the base urls for other network requests if required.

* To install and run OpenRegistration

gradlew build

The apt files is available in app/build/outputs/apk folder.

* To build and install the android application on connected device or simulator

gradlew installDebug 
