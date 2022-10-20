package com.Doctoor.app.preference

import android.app.NotificationManager
import android.content.Context
import androidx.lifecycle.LiveData
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.remote.UserRestService
import com.Doctoor.app.model.response.UserResponse
import com.onesignal.OneSignal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject
constructor(
    var mContext: Context, // user data store
    val dataStore: UserDataStore, private val service: UserRestService
) {

    val isUserSessionStarted: Boolean
        get() = dataStore.userLiveData.value?.id != null

    /**
     * @return current user data
     */
    val currentUser: UserResponse.User?
        get() = dataStore.user


    val apiService: UserRestService?
        get() = service


    /**
     * Start user session in test environment
     *
     * @param token user token
     */
    fun startUserSession(user: UserResponse.User, token: String): LiveData<UserResponse.User> {
        dataStore.setUserToken(token)
        return dataStore.setUser(user)
    }

    /**
     * Stop user session (clear user data from both memory and shared pref)
     */
    private fun stopUserSession() {
        dataStore.clearUser()
    }

    /*
     * save discount to shared preferences
     */
    fun setDiscount(discount: Double) {
        dataStore.setDiscount(discount)
    }

    /**
     * Check for current user session has started or not
     * Check if has saved user -> start new session
     *
     * @return true if user session started or be able to start new user session (has saved user)
     */
    fun checkForSavedUserAndStartSessionIfHas(): Boolean {
        if (isUserSessionStarted) {
            return true
        }
        val savedUser = dataStore.user
        if (savedUser != null) {
            startUserSession(savedUser, dataStore.getUserToken()!!)
            return true
        }
        return false
    }

    /**
     * Update user data by new user if are the same
     *
     * @param newUser new User data
     * @return true if user updated
     */
    fun updateUserIfEquals(newUser: UserResponse.User) = dataStore.updateUserIfEquals(newUser)

    /*
     * Refresh current user account from server
     * All errors will be ignored
     */
    fun refreshUser() {
        if (isUserSessionStarted) {
            // TODO: 10/18/17 refresh user by calling rest api
            // ApiUtils.makeRequest(mUserComponent.getUserService().updateUser(), false, this::updateUserIfEquals);
        }
    }

    /**
     * Logout user
     */
    fun logout() {
        reset()

    }

    fun reset() {
        logOutApi()

        // stop user session
        stopUserSession()

        // cancel all notification
        val notifManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notifManager.cancelAll()
        OneSignal.clearOneSignalNotifications()
        OneSignal.setSubscription(false)
        OneSignal.deleteTag("Email")
        OneSignal.deleteTag("ID")
        BaseViewModel.isLogin.value = false
    }

    /**
     * Logout by our back-end api
     */
    private fun logOutApi() {
    }

}