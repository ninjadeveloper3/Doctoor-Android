package com.Doctoor.app.ui.modules.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.Doctoor.app.R
import com.Doctoor.app.navigation.NavigatorHelper
import javax.inject.Inject

class MainDrawerHolder @Inject
constructor(
    private val navigatorHelper: NavigatorHelper
) {

    private lateinit var drawer: DrawerLayout
    private var accountsNav: NavigationView? = null
    private lateinit var drawerToggle: ActionBarDrawerToggle;
    private lateinit var context: MainActivity;
    fun init(
        drawerLayout: DrawerLayout,
        toolbar: Toolbar?,
        extrasNav: NavigationView?,
        context: MainActivity
    ) {
        drawer = drawerLayout;
        this.context = context;
        accountsNav = extrasNav;
        setNavigationDrawer(toolbar);

    }

    fun setNavigationDrawer(toolbar: Toolbar?) {
        drawerToggle =
            object : ActionBarDrawerToggle(
                context,
                drawer,
                toolbar,
                R.string.app_name,
                R.string.app_name
            ) {

                override fun onDrawerClosed(drawerView: View) {
                    context.invalidateOptionsMenu();
                }

                override fun onDrawerOpened(drawerView: View) {

                    context.invalidateOptionsMenu();
                }
            }

        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        drawerToggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
        accountsNav?.setNavigationItemSelectedListener(drawerMenuItemClickListener())

    }

    fun drawerMenuItemClickListener() =
        NavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

            }
            true
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
