package dev.deeplink.analytics.demo

import android.app.Application
import dev.deeplink.analytics.Analytics
import dev.deeplink.analytics.config.AnalyticsConfig
import dev.deeplink.analytics.core.sender.IDataSender
import dev.deeplink.analytics.core.service.DataPackage
import dev.deeplink.analytics.core.service.DefaultDataService


class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val config = AnalyticsConfig.Builder().setAccountId("YOUR_ACCOUNT_ID")
            .setDevToken("YOUR_DEV_TOKEN")
            .setMinReportInterval(10) //The minimum interval for reporting event data, in seconds
            .setMaxReportNumEachTime(50) //The maximum number of event data reported each time
            .build()
        Analytics.setup(this, config)
        //Developers customize IDataSender to report event data to the business server
        Analytics.register(DefaultDataService(sender = object : IDataSender {

            override fun send(dataPackage: DataPackage, finished: (Boolean) -> Unit) {
                //Send event data to your business server
                // val result = api.send(dataPackage)
                val result = true
                finished.invoke(result)
            }
        }))
    }
}