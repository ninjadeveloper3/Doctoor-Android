package com.Doctoor.app.preference

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.Doctoor.app.base.BaseViewModel.Companion.discountPercentage
import com.Doctoor.app.model.response.BaseModel
import com.Doctoor.app.model.response.City
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.model.response.UserResponse
import com.Doctoor.app.utils.AppPreferences
import com.Doctoor.app.utils.Constants.AUTH_TOKEN
import com.Doctoor.app.utils.Constants.DISCOUNT
import com.Doctoor.app.utils.Constants.IS_FIRST_LUNCH_FINISH
import com.Doctoor.app.utils.Constants.USER
import com.Doctoor.app.utils.debug
import com.Doctoor.app.utils.isEmpty
import javax.inject.Inject

/**
 * User repository for storing and retrieving user data from database / shared preference
 */

class UserDataStore @Inject
constructor(val sharedPreferences: AppPreferences, private val mGson: Gson) {

    internal val mUserLiveData: MutableLiveData<UserResponse.User> = MutableLiveData()

    val user: UserResponse.User?
        get() {
            if (mUserLiveData.value != null) {
                return mUserLiveData.value
            }
            val userJson = sharedPreferences.getPreferences(USER, "")
            debug("User profile $userJson")
            return if (userJson != null && userJson != "") fromJson(userJson) else null
        }

    private val discount: Double
        get() {
            return try {
                val discount = sharedPreferences.getPreferences(DISCOUNT, "")!!.toDouble()
                if (discount != 0.0) discount else 0.0
            } catch (e: Exception) {
                0.0
            }
        }

    init {
        mUserLiveData.value = user
        discountPercentage.value = discount
    }

    val userLiveData: MutableLiveData<UserResponse.User>
        get() = mUserLiveData


    /**
     * Save discount to shared preference
     * @param discount [Double] to be saved
     */

    fun setDiscount(discount: Double) {
        sharedPreferences.setPreferences(DISCOUNT, discount.toString())
    }


    /**
     * Save user to shared preference
     * @param user [User] to be saved
     */
    fun setUser(user: UserResponse.User): LiveData<UserResponse.User> {

        sharedPreferences.setPreferences(USER, mGson.toJson(user))
        setUserLiveDataValueSafely(user)
        //        userDao.addOrUpdate(user);
        return mUserLiveData
    }

    /**
     * Update user data by new user if are the same
     * @param newUser new User data
     * @return true if user updated
     */
    fun updateUserIfEquals(newUser: UserResponse.User): Boolean {
        if (newUser == user) {
            setUser(newUser)
            return true
        }
        return false
    }

    fun setUserToken(token: String) {
        sharedPreferences.setPreferences(AUTH_TOKEN, token)

    }

    /**
     * @return saved user token
     */
    fun getUserToken() = sharedPreferences.getPreferences(AUTH_TOKEN, "")

    /**
     * Clear user from database
     */
    fun clearUser() {

        sharedPreferences.clearPreferences()
        setUserLiveDataValueSafely(null)
    }

    /**
     * create User object from json string
     * @param userJson given json
     * @return [User] object
     */
    fun fromJson(userJson: String): UserResponse.User {
        return mGson.fromJson(userJson, UserResponse.User::class.java)
    }

    /**
     * Clone a user
     * @param user given user
     * @return the copy [User] of given user
     */
    fun cloneUser(user: UserResponse.User): UserResponse.User {
        val userJson = mGson.toJson(user)
        return fromJson(userJson)
    }

    fun setFirstTimeLunch(value: Boolean) {
        sharedPreferences.setPreferences(IS_FIRST_LUNCH_FINISH, true)
    }

    fun isFirstLunchFinish() = sharedPreferences.getPreferences(IS_FIRST_LUNCH_FINISH, false)

    fun <T : BaseModel> getModelData(classOfT: Class<T>, key: String): T {
        val json = sharedPreferences.getPreferences(key, "")
        return mGson.fromJson<T>(json, classOfT)
    }

    /**
     * Save any model data in sharedPreferences  as Gson
     * The store model data must be extended with {@link BaseModel}
     * @param key is the sharePreference key
     * @param classOfT is data model class
     *
     */
    fun <T : BaseModel> saveModelData(classOfT: Class<T>, key: String) =
        sharedPreferences.setPreferences(key, mGson.toJson(classOfT))

    fun <T> saveAsList(result: MutableList<T>, key: String) {
        val jSon = mGson.toJson(result)
        sharedPreferences.setPreferences(key, jSon)
    }

    fun <T> getSavedList(classOfT: Class<T>, key: String): MutableList<T> {
        val jSon = sharedPreferences.getPreferences(key, "") ?: return ArrayList()
        val type = object : TypeToken<MutableList<T>>() {

        }.type
        return mGson.fromJson(jSon, type)
    }

    fun getMedicinesCategories(key: String): MutableList<Medicines.Category> {
        val jSon = sharedPreferences.getPreferences(key, "")
        if (isEmpty(jSon))
            return ArrayList()
        val type = object : TypeToken<MutableList<Medicines.Category>>() {

        }.type
        return mGson.fromJson(jSon, type)
    }

    fun getCities(key: String): ArrayList<City> {
        val jSon = sharedPreferences.getPreferences(key, "")
        if (isEmpty(jSon))
            return ArrayList()
        val type = object : TypeToken<ArrayList<City>>() {

        }.type
        return mGson.fromJson(jSon, type)
    }

    /**
     * Safely update value for mUserLiveData
     * in case of can't perform [MutableLiveData.setValue] (not in mainThread),
     * we should use postValue instead
     * @param user user to be set
     */
    private fun setUserLiveDataValueSafely(user: UserResponse.User?) {
        try {
            mUserLiveData.value = user

            // in case of can't perform setValue (not in mainThread), we should use postValueInstead
        } catch (e: Exception) {
            mUserLiveData.postValue(user)
        }

    }
}
