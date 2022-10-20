package com.Doctoor.app.di.ui_modules.activity

import com.Doctoor.app.ui.modules.frame.BottomNavigationFrameActivity
import com.Doctoor.app.ui.modules.frame.BottomNavigationFrameModule
import com.Doctoor.app.ui.modules.frame.FrameActivity
import com.Doctoor.app.ui.modules.frame.FrameActivityModule
import com.Doctoor.app.ui.modules.main.MainActivity
import com.Doctoor.app.ui.modules.main.MainActivityModule
import com.Doctoor.app.ui.modules.splash.SplashActivity
import com.Doctoor.app.ui.modules.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectorsModule {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun splashActivityInjector(): SplashActivity

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivityInjector(): MainActivity

    @ContributesAndroidInjector(modules = [FrameActivityModule::class])
    abstract fun frameActivityInjector(): FrameActivity

    @ContributesAndroidInjector(modules = [BottomNavigationFrameModule::class])
    abstract fun bottomNavigationFrameActivityInjector(): BottomNavigationFrameActivity
}