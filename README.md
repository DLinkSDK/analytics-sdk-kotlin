# analytics-sdk-kotlin

Step 1: Get the Account ID

Register an account at [https://console.dlink.cloud/](https://console.dlink.cloud). After creating an app on the platform, get the corresponding Account ID of the app.

Step 2: Get the SDK

(1) Configure the Maven repository
```kotlin   
repositories {
   maven { url 'https://maven.deeplink.dev/repository/maven-releases/' }
}
```

Note: The Maven repository address needs to be configured in both 'buildscript' and 'allprojects' in the root directory's 'build.gradle'.

(2) If you are using Gradle for integration, add the following code to your project's build.gradle:
```kotlin
implementation 'dev.deeplink:analytics:1.4.3'
```

(3) Please add the following configuration in gradle.properties
```kotlin
android.enableJetifier=true
```

Step 3: Configure AndroidManifest

Find the project configuration file AndroidManifest.xml in your project, and add the following permissions:

```kotlin
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```

Step 4: Initialize the SDK
Please initialize the SDK in the main process. Here is the reference code:
```kotlin
class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val config = AnalyticsConfig.Builder()
            .setAccountId("YOUR_ACCOUNT_ID")
            .setDevToken("YOUR_DEV_TOKEN")
            //[require] Developers need to pass in the key for tracking data.
            //It is strongly recommended that you set different keys for different users.
            .setCryptKey("DYNAMIC_CRYPT_KEY")
            //[optional] The minimum interval for reporting event data, in seconds.Default 10 seconds.
            .setMinReportInterval(10)
            //[optional] The maximum number of event data reported each time.Default 50.
            .setMaxReportNumEachTime(50)
            .build()
        //[require] Call this method to initialize the SDK
        Analytics.setup(this, config)
    }
}
```

Step 5: Logging events
```kotlin
Analytics.log(
    eventName = "button_click_2",
    eventParams = hashMapOf<String, Any>().apply {
        this["button_id"] = "button2"
    }, priority = Event.PRIORITY_HIGH
)
```
