package com.Doctoor.app.di.components

import com.Doctoor.app.DoctoorApp
import com.Doctoor.app.di.DataModule
import com.Doctoor.app.di.NetworkModule
import com.Doctoor.app.di.ui_modules.AppModule
import com.Doctoor.app.di.ui_modules.activity.ActivityInjectorsModule
import com.Doctoor.app.di.ui_modules.fragment.FragmentInjectorsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityInjectorsModule::class,
        FragmentInjectorsModule::class,
        NetworkModule::class,
        DataModule::class,
        AppModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: DoctoorApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: DoctoorApp)

}