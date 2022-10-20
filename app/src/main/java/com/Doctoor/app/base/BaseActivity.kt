package com.Doctoor.app.base;


import android.app.Fragment
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.Doctoor.app.R
import com.Doctoor.app.di.components.Injectable
import com.Doctoor.app.ui.lazy.bindView
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


abstract class BaseActivity : AppCompatActivity(), HasFragmentInjector, HasSupportFragmentInjector,
    Injectable {
    public val toolbar: Toolbar? by bindView<Toolbar>(R.id.toolbar)

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

    }

    @LayoutRes
    abstract fun layoutRes(): Int

    fun setupToolbar() {

        toolbar?.title = ""
        toolbar?.visibility = (if (toolbar()) View.VISIBLE else View.GONE)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayShowCustomEnabled(true)
            setHomeAsUpIndicator(getHomeAsUpIndicator())
            if (toolbar()) show() else hide()

        }
    }

    abstract fun getHomeAsUpIndicator(): Int

    protected fun shouldUseDataBinding(): Boolean {
        return false
    }

    abstract fun toolbar(): Boolean

    @Inject
    lateinit var supportFragment: DispatchingAndroidInjector<androidx.fragment.app.Fragment>

    @Inject
    lateinit var frameworkFragment: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = supportFragment


    override fun fragmentInjector() = frameworkFragment

    override fun onOptionsItemSelected(item: MenuItem?) =
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

}