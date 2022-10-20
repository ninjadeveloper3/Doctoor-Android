package com.Doctoor.app.onesignal

import com.onesignal.NotificationExtenderService
import com.onesignal.OSNotificationPayload
import com.onesignal.OSNotificationReceivedResult
import com.onesignal.OneSignal

class NotificationReceivedHandler : NotificationExtenderService() {
    override fun onNotificationProcessing(notification: OSNotificationReceivedResult?): Boolean {
        val payload = notification?.payload
        handleNotification(payload, notification?.isAppInFocus)
        return true
    }

    private fun handleNotification(data: OSNotificationPayload?, isAppInFocus: Boolean?) {
        val builder = OneSignal.getCurrentOrNewInitBuilder()
    }
}