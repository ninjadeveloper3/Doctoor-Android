package com.Doctoor.app.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.Doctoor.app.BuildConfig
import com.Doctoor.app.data.database.AppDatabase
import com.Doctoor.app.data.remote.UserRestService
import com.Doctoor.app.di.qualifiers.ApplicationContext
import com.Doctoor.app.di.qualifiers.DatabaseInfo
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.preference.UserDataStore
import com.Doctoor.app.utils.AppPreferences
import com.Doctoor.app.utils.debug
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideDataStore(
        sharedPreferences: AppPreferences,
        gson: Gson
    ) = UserDataStore(sharedPreferences, gson)

    @Provides
    @Singleton
    fun provideUserManager(
        @ApplicationContext context: Context, userDataStore: UserDataStore,
        githubService: UserRestService
    ) = DataStoreManager(context, userDataStore, githubService)

    @Provides
    @Singleton
    fun provideAppPreferences(@ApplicationContext context: Context) =
        AppPreferences.getInstance(context)

    /**
     * Provides DataBase Name
     *
     * @return
     */
    @Provides
    @DatabaseInfo
    fun providesDatabaseName() = BuildConfig.DB_NAME


    /**
     * Provides DB Version
     * @return
     */
    @Provides
    @DatabaseInfo
    fun providesDatabaseVersion() = BuildConfig.DB_VERION

    /**
     * Creates AppDatabase[AppDatabase] instance
     *
     * @param context
     * @param databaseName
     * @return [AppDatabase]
     */
    @Provides
    @Singleton
    internal fun providesAppDatabase(
        @ApplicationContext context: Context,
        @DatabaseInfo databaseName: String
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    debug("DataBasePath>>" + db.path)
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    debug("DataBasePath>>" + db.path)
                }
            })
            .fallbackToDestructiveMigration().build()
    }


}