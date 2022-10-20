package com.Doctoor.app

import android.app.Activity
import android.app.Application
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.crashlytics.android.Crashlytics
import com.Doctoor.app.di.components.AppComponent
import com.Doctoor.app.di.components.AppInjector
import com.Doctoor.app.onesignal.NotificationOpenedHandler
import com.Doctoor.app.utils.dimen
import com.Doctoor.app.utils.font.TypefaceUtil
import com.Doctoor.app.utils.font.TypefacesFont.from
import com.onesignal.OneSignal
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import es.dmoral.toasty.Toasty
import io.fabric.sdk.android.Fabric
import javax.inject.Inject


class DoctoorApp : Application(), HasActivityInjector, LifecycleObserver {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    lateinit var sAppComponent: AppComponent

    companion object {
        var instance: DoctoorApp? = null
        var isAppInForeground = false
        fun resources(): Resources {
            return instance!!.resources
        }

        @StringRes
        fun string(resId: Int, vararg args: Any): String {
            return resources().getString(resId, *args)
        }

        @ArrayRes
        fun stringArray(resId: Int): Array<String> {
            return resources().getStringArray(resId)
        }

        @DrawableRes
        fun drawable(resId: Int): Drawable {
            return ResourcesCompat.getDrawable(resources(), resId, instance?.theme)!!
        }

        @ColorInt
        fun color(resId: Int): Int {
            //         return ResourcesCompat.getColor(resources(),resId,context().getTheme());
            return ContextCompat.getColor(instance!!, resId)//.getColor(resId);
        }
    }

    init {
        instance = this;
    }

    fun getAppComponent() = sAppComponent

    override fun onCreate() {
        super.onCreate()
        sAppComponent = AppInjector.init(this)
        Fabric.with(this, Crashlytics())
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        /*Setting logcate level*/
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.NONE)

        OneSignal.startInit(this)
            .setNotificationOpenedHandler(NotificationOpenedHandler())
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .autoPromptLocation(false)
            .init()

        Toasty.Config.getInstance()
            .setToastTypeface(TypefaceUtil.getTypeface(instance, from(1))) // optional
            .allowQueue(true) // optional (prevents several Toastys from queuing)
            .apply() // required
    }

    override fun activityInjector() = activityInjector

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        isAppInForeground = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        isAppInForeground = true
    }
}