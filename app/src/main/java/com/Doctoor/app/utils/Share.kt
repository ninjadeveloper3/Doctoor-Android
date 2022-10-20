package com.Doctoor.app.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.pm.PackageManager
import android.net.Uri
import com.Doctoor.app.BuildConfig
import com.Doctoor.app.DoctoorApp


/**
 * Opens the url in the available application
 * @return A boolean representing if the action was successful or not
 */
fun Context.openUrl(url: String, newTask: Boolean = false): Boolean {
    return try {
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
            if (newTask) addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        true
    } catch (e: Exception) {
        false
    }
}

/**
 * Opens the share context menu
 * @return A boolean representing if the action was successful or not
 */
fun Context.share(text: String, subject: String = ""): Boolean {
    val intent = Intent()
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_TEXT, text)
    return try {
        startActivity(createChooser(intent, null))
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}

/**
 * Opens the email application
 * @param email A recipient email
 * @param subject An optional subject of email
 * @param text An option body of the email
 * @return A boolean representing if the action was successful or not
 */
fun Context.sendEmail(email: String, subject: String = "", text: String = ""): Boolean {
    val intent = Intent().apply {
        action = Intent.ACTION_SENDTO
        data = Uri.parse("mailto:")
        putExtra(EXTRA_EMAIL, arrayOf(email))
        if (subject.isNotBlank()) putExtra(Intent.EXTRA_SUBJECT, subject)
        if (text.isNotBlank()) putExtra(Intent.EXTRA_TEXT, text)
    }
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        return true
    }
    return false
}

/**
 * Opens the dialer that handles the given number
 * @param number A phone number to open in the dialer
 * @return A boolean representing if the action was successful or not
 */
fun Context.makeCall(number: String): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        startActivity(intent)
        true
    } catch (e: Exception) {
        false
    }
}

fun OpenWhatsApp() {
    val contact = "+923090850760" // use country code with your phone number
    val url = "https://api.whatsapp.com/send?phone=$contact"
    try {
        val pm = DoctoorApp.instance?.packageManager
        pm?.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
        val i = Intent(Intent.ACTION_VIEW)
        i.flags = FLAG_ACTIVITY_NEW_TASK
        i.data = Uri.parse(url)
        DoctoorApp.instance?.startActivity(i)
    } catch (e: PackageManager.NameNotFoundException) {
        DoctoorApp.instance.longToastNow("WhatsApp app not installed in your phone")
    }

}

/**
 * Opens the SMS application to send an SMS
 * @param number A phone number to send an SMS
 * @param text An optional predefined text message for the SMS
 * @return A boolean representing if the action was successful or not
 */
fun Context.sendSms(number: String, text: String = ""): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$number")).apply {
            putExtra("sms_body", text)
        }
        startActivity(intent)
        true
    } catch (e: Exception) {
        false
    }
}

/**
 * Opens your application page inside the play store
 * @return A boolean representing if the action was successful or not
 */
fun Context.openPlayStore(): Boolean =
    openUrl("market://details?id=${BuildConfig.APPLICATION_ID}") or
            openUrl("http://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")