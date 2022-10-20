package com.Doctoor.app.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.AndroidRuntimeException
import androidx.fragment.app.Fragment
import com.Doctoor.app.R

object  NavigationUtils {

    fun <T : Fragment> createFragmentInstance(fragment: T, bundleConsumer: PlainConsumer<Bundle>): T {
        val bundle = Bundle()
        bundleConsumer.accept(bundle)
        fragment.arguments = bundle
        return fragment
    }

    /**
     * Open google map app with custom query
     *
     * @param context Context
     * @param query   google map query
     */
    fun openGoogleMap(context: Context, query: String) {
        val gmmIntentUri = Uri.parse(query)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        }
    }

    /**
     * Open google map and point to specific address
     *
     * @param context        context
     * @param locationLatLng Lat and Long with format "lat,long"
     * @param address        specific address
     */
    fun openGoogleMap(context: Context, locationLatLng: String, address: String) {
        val query = context.getString(R.string.format_google_map_intent_uri, locationLatLng, locationLatLng, address)
        openGoogleMap(context, query)
    }

    /**
     * Open current application setting page
     *
     * @param activity host activity
     */
    fun openAppSetting(activity: Activity) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }

    /**
     * Open system wifi setting
     *
     * @param context context
     */
    fun openWifiSetting(context: Context) {
        val i = Intent(Settings.ACTION_WIFI_SETTINGS)
        try {
            context.startActivity(i)
        } catch (e: AndroidRuntimeException) {
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }

    }


    fun openLocationSetting(context: Context) {
        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    fun openGalleryPickerForResult(activity: Activity, type: String, requestCode: Int) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.setType(type)
        activity.startActivityForResult(photoPickerIntent, requestCode)
    }

    fun openBrowser(context: Context, url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }


    fun openGalleryImagePickerForResult(activity: Activity, requestCode: Int) {
        openGalleryPickerForResult(activity, "image/*", requestCode)
    }

    fun openAppOnPlayStore(context: Context) {
        val appPackageName = context.getPackageName()
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)))
        } catch (e: android.content.ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)
                )
            )
        }

    }
}
