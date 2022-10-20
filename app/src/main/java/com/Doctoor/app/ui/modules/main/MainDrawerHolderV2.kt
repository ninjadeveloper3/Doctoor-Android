package com.Doctoor.app.ui.modules.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.Doctoor.app.R
import com.Doctoor.app.navigation.NavigatorHelper
import javax.inject.Inject

class MainDrawerHolderV2 @Inject
constructor(
        private val navigatorHelper: NavigatorHelper
) {
    private lateinit var drawer: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle;
    private lateinit var context: MainActivity;
    fun init(drawerLayout: DrawerLayout, toolbar: Toolbar?, context: MainActivity) {
        drawer = drawerLayout;
        this.context = context;
        setNavigationDrawer(toolbar)
    }

    fun setNavigationDrawer(toolbar: Toolbar?) {
        drawerToggle =
                object : ActionBarDrawerToggle(context, drawer, toolbar, R.string.app_name, R.string.app_name) {

                    override fun onDrawerClosed(drawerView: View) {
                        context.invalidateOptionsMenu();
                    }

                    override fun onDrawerOpened(drawerView: View) {

                        context.invalidateOptionsMenu();
                    }
                }
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(drawerToggle)

        drawerToggle.setToolbarNavigationClickListener {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            }else{
                drawer.openDrawer(GravityCompat.START)
            }
        }

        drawerToggle.syncState()

    }

    fun closeDrawer(): Boolean {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
            return true
        }
        return false
    }

    fun onPostCreate(savedInstanceState: Bundle?) {
        drawerToggle.syncState()
    }

    fun onConfigurationChanged(newConfig: Configuration) {
        drawerToggle.onConfigurationChanged(newConfig)
    }


}