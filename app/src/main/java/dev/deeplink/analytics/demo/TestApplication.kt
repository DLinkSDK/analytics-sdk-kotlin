package dev.deeplink.analytics.demo

import android.app.Application
import dev.deeplink.analytics.Analytics
import dev.deeplink.analytics.config.AnalyticsConfig
import dev.deeplink.analytics.core.datastore.db.Event
import dev.deeplink.analytics.core.sender.IDataSender
import dev.deeplink.analytics.core.service.DataPackage
import dev.deeplink.analytics.core.service.DefaultDataService
import dev.deeplink.analytics.core.service.IDataService
import kotlin.random.Random


class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val config = AnalyticsConfig.Builder()
            .setAccountId("YOUR_ACCOUNT_ID")
            .setDevToken("YOUR_DEV_TOKEN")
            //[require] Developers need to pass in the key for event data.
            //It is strongly recommended that you set different keys for different users.
            .setCryptKey("DYNAMIC_CRYPT_KEY")
            //[optional] The minimum interval for reporting event data, in seconds.Default 10 seconds.
            .setMinReportInterval(10)
            //[optional] The maximum number of event data reported each time.Default 50.
            .setMaxReportNumEachTime(50)
            .build()
        //[require] Call this method to initialize the SDK
        Analytics.setup(this, config)

        //[optional] Developers can add custom parameters globally
        Analytics.addCustomParams(hashMapOf<String, Any>().apply {
            this["userid"] = "UID"
        })

        //[require] Developers customize IDataSender to report event data to the business server
        Analytics.register(DefaultDataService(sender = object : IDataSender {

            override fun send(dataPackage: DataPackage, finished: (Boolean) -> Unit) {
                // Send DataPackage to your business server
                // val result = api.sendToYourServer(dataPackage)
                val result = Random.nextBoolean()

                //Please pass the report result to SDK after the report is completed.
                finished.invoke(result)
            }
        }))

        Analytics.register(FirebaseDataService())
    }

    private class FirebaseDataService : IDataService {

        override fun handle(event: Event) {
            //Please call the Firebase api to log events here
        }

        override fun flush(force: Boolean) {
            //ignore
        }
    }
}