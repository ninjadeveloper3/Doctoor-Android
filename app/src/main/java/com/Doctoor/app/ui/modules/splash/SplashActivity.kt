package com.Doctoor.app.ui.modules.splash

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelActivity
import com.Doctoor.app.data.source.State
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.databinding.ActivitySplashBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.Constants.SHOW_ALERT
import com.Doctoor.app.utils.debug
import java.security.MessageDigest
import javax.inject.Inject


class SplashActivity : BaseViewModelActivity<ActivitySplashBinding, SplashActivityVM>() {

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<SplashActivityVM>


    override fun layoutRes() = R.layout.activity_splash
    private val mHandler = Handler()

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        printFacebookKeyHash()
        getMyViewModel().isFirstLunchFinished.observe(this, Observer {
            if (it) {
                mHandler.postDelayed(runnable, 3000)
            }
        })
    }

    override fun handleState(state: State?) {
        if (state?.status == Status.NETWORK) {
            AlertUtils.showSnackBarLongMessage(
                    getViewDataBinding().root, state.message, "Setting"
            ) {
                startActivity(Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS))
                finish()
            }
        }
    }

    private val runnable = Runnable {
        //        val cities:List<City> = dataStoreManager.dataStore.getSavedList(City::class.java,City::class.java.name)
//        debug("");
        navigatorHelper.navigateMainActivity(clearAllPrevious = true, isCheckVersion = true)
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(runnable)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mHandler.removeCallbacks(runnable)
        finish()

    }


    private fun printFacebookKeyHash() {

        try {
            val info = packageManager.getPackageInfo(
                    applicationContext.packageName, PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                debug(Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }


    override fun getHomeAsUpIndicator() = R.drawable.ic_drawer

    override fun getBindingVariable() = BR.SplashActivityVM

    override fun getMyViewModel() = viewModel.get()

    override fun toolbar() = false

}