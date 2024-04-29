package com.example.mycasino6.model.application

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.onesignal.OneSignal

class OneSignalApplication:Application() {

    private val ONESIGNAL_APP_ID = "2b0b3fd0-bfa0-4cdb-be18-b4488ee12bfc"

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }

}