package dev.deeplink.analytics.demo

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import dev.deeplink.analytics.Analytics
import dev.deeplink.analytics.core.datastore.db.Event

class TestActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        findViewById<Button>(R.id.button1)?.setOnClickListener {
            Analytics.log("pay_fail", hashMapOf<String, Any>().apply {
                this.put("order_id", "ORDER_ID")
                this.put("product_id", "PRODUCT_ID")
            })
        }
        findViewById<Button>(R.id.button2)?.setOnClickListener {
            //[Optional] Set the event priority to Event.PRIORITY_HIGH, and the event will be reported immediately.
            Analytics.log(
                eventName = "button_click",
                eventParams = hashMapOf<String, Any>().apply {
                    this["button_id"] = "BUTTON_ID"
                }, priority = Event.PRIORITY_HIGH
            )
        }
        findViewById<Button>(R.id.button3)?.setOnClickListener {
            //[Optional] Developers can call this method to report tracking data when the page is destroyed or at other times.
            // By default, the SDK will try to report events when the application returns to the foreground or background.
            Analytics.flush()
        }
        var uid = 0
        Analytics.addCustomParams(hashMapOf<String, Any>().apply {
            this["userid"] = uid
        })
        findViewById<Button>(R.id.button4)?.setOnClickListener {
            uid++
            Analytics.updateCustomParam("userid", uid)
        }
    }
}