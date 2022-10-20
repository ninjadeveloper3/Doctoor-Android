package com.Doctoor.app.di.ui_modules

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.Doctoor.app.DoctoorApp
import com.Doctoor.app.data.remote.GsonProvider
import com.Doctoor.app.di.qualifiers.ApplicationContext

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(app: DoctoorApp): Application = app

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(app: DoctoorApp): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonProvider.makeGson()
    }
}