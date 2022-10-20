package com.Doctoor.app.di

import com.google.gson.Gson
import com.Doctoor.app.data.remote.*
import com.Doctoor.app.preference.UserDataStore
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideGithubService(gson: Gson, okHttpClient: OkHttpClient) =
        ServiceFactory.makeService(UserRestService::class.java, gson, okHttpClient)

    @Provides
    @Singleton
    fun provideOkHttpClientNoAuth(userDataStore: UserDataStore) =
        ServiceFactory.makeOkHttpClientBuilder { userDataStore.getUserToken() }.build()

    @Provides
    @Singleton
    fun provideMedicineService(gson: Gson, okHttpClient: OkHttpClient) =
        ServiceFactory.makeService(MedicineRestService::class.java, gson, okHttpClient)

    @Provides
    @Singleton
    fun provideTestsRestService(gson: Gson, okHttpClient: OkHttpClient) =
        ServiceFactory.makeService(TestsRestService::class.java, gson, okHttpClient)

    @Provides
    @Singleton
    fun provideEasyPaisaRestService(gson: Gson, okHttpClient: OkHttpClient) =
        ServiceFactory.makeServiceEasyPaisa(EasyPaisaService::class.java, gson, okHttpClient)

    @Provides
    @Singleton
    fun provideServicesRestService(gson: Gson, okHttpClient: OkHttpClient) =
        ServiceFactory.makeService(ServicesRestService::class.java, gson, okHttpClient)

    @Provides
    @Singleton
    fun provideHomeRestService(gson: Gson, okHttpClient: OkHttpClient) =
        ServiceFactory.makeService(HomeRestService::class.java, gson, okHttpClient)


}