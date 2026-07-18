package com.example.aplikasipatjol.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            Log.d("SmsReceiver", "SMS Received! Triggering preprocessing & TFLite inference...")
            // TODO: Extract SMS data, run Preprocessing, and invoke AI model here.
        }
    }
}
