package com.Doctoor.app.onesignal

import android.content.Intent
import com.Doctoor.app.DoctoorApp
import com.Doctoor.app.ui.modules.main.MainActivity
import com.Doctoor.app.ui.modules.myorder.MyOrderFragment
import com.Doctoor.app.ui.modules.notification.NotificationFragment
import com.Doctoor.app.utils.Constants.FRAGMENT_CLASS
import com.Doctoor.app.utils.Constants.INDEX
import com.Doctoor.app.utils.Constants.SHOW_CART_MENU
import com.Doctoor.app.utils.Constants.SHOW_TOOLBAR
import com.onesignal.OSNotificationOpenResult
import com.onesignal.OneSignal


class NotificationOpenedHandler : OneSignal.NotificationOpenedHandler {

    // This fires when a notification is opened by tapping on it.
    override fun notificationOpened(result: OSNotificationOpenResult) {

        val actionType = result.action.type
        openNotifications()

    }

    fun startActivity(type: String) {
//        add this for clear history
        when (type) {
            "report" -> {
                openNotifications()
            }
            "order" -> {
                openOrderHistory()
            }
            else -> {
                openOrderHistory()
            }
        }
    }

    private fun openNotifications() {
        val intent = Intent(DoctoorApp.instance, MainActivity::class.java)

        intent.putExtra(FRAGMENT_CLASS, NotificationFragment::class.java.name)
        intent.putExtra(SHOW_TOOLBAR, false)
        intent.putExtra(SHOW_CART_MENU, true)
        intent.putExtra(INDEX, 1)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        DoctoorApp.instance?.startActivity(intent)
    }

    private fun openOrderHistory() {
        val intent = Intent(DoctoorApp.instance, MainActivity::class.java)

        intent.putExtra(FRAGMENT_CLASS, MyOrderFragment::class.java.name)
        intent.putExtra(SHOW_TOOLBAR, false)
        intent.putExtra(SHOW_CART_MENU, true)
        intent.putExtra(INDEX, 2)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        DoctoorApp.instance?.startActivity(intent)
    }
}
