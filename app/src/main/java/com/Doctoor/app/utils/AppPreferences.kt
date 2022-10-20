package com.Doctoor.app.utils


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.Nullable
import com.Doctoor.app.BuildConfig

class AppPreferences @SuppressLint("CommitPrefEdits")
constructor(val context: Context?) {

    /**
     * FEILD OF CLASS *
     */
    private var mPrefName = PREFS_NAME
    private var mSharedPre: SharedPreferences? = null
    private var mEditor: SharedPreferences.Editor? = null

    init {
        if (null != context) {
            if (mSharedPre == null) {
                mSharedPre = context.getSharedPreferences(mPrefName, Context.MODE_PRIVATE)
            }

            mEditor = mSharedPre!!.edit()
        } else {
            mEditor = null
            mSharedPre = null
        }
    }


    /**
     * Set data for String
     *
     * @param preName Preferences name
     * @param value   String input
     */
    fun setPreferences(
        preName: String,
        value: String
    ) {

        if (null != mEditor) {
            mEditor!!.putString(preName, value)
            mEditor!!.commit()
        }
    }

    /**
     * Get data for String
     *
     * @param preName      Preferences name
     * @param defaultValue
     * @return String or 0 if Name not existed
     */
    fun getPreferences(
        preName: String,
        @Nullable defaultValue: String
    ) = mSharedPre!!.getString(preName, defaultValue)

    /**
     * Set data for boolean
     *
     * @param preName Preferences name
     * @param value   boolean input
     */
    fun setPreferences(
        preName: String,
        value: Boolean
    ) {
        mEditor!!.putBoolean(preName, value)
        mEditor!!.commit()
    }

    /**
     * Get data for boolean
     *
     * @param preName      Preferences name
     * @param defaultValue
     * @return boolean or 0 if Name not existed
     */
    fun getPreferences(
        preName: String,
        defaultValue: Boolean
    ): Boolean {

        return mSharedPre!!.getBoolean(preName, defaultValue)


    }

    /**
     * Set data for Integer
     *
     * @param preName Preferences name
     * @param value   Integer input
     */
    @Synchronized
    fun setPreferences(
        preName: String,
        value: Int
    ) {
        mEditor!!.putInt(preName, value)
        mEditor!!.commit()
    }

    /**
     * Get data for Integer
     *
     * @param preName      Preferences name
     * @param defaultValue
     * @return Integer or 0 if Name not existed
     */
    fun getPreferences(
        preName: String,
        defaultValue: Int
    ): Int {
        return mSharedPre!!.getInt(preName, defaultValue)
    }

    /**
     * Set data for Long
     *
     * @param preName Preferences name
     * @param value   Long input
     */
    @Synchronized
    fun setPreferences(
        preName: String,
        value: Long
    ) {
        mEditor!!.putLong(preName, value)
        mEditor!!.commit()
    }

    /**
     * Get data for Long
     *
     * @param preName      Preferences name
     * @param defaultValue
     * @return Long or 0 if Name not existed
     */
    fun getPreferences(
        preName: String,
        defaultValue: Long
    ): Long {
        return mSharedPre!!.getLong(preName, defaultValue)
    }

    /**
     * Set data for Float
     *
     * @param preName Preferences name
     * @param value   Float input
     */
    @Synchronized
    fun setPreferences(
        preName: String,
        value: Float
    ) {
        mEditor!!.putFloat(preName, value)
        mEditor!!.commit()
    }

    fun clearPreferences() {
        /*clear preferences*/

        mEditor!!.clear().apply()

    }

    /**
     * Get data for Float
     *
     * @param preName      Preferences name
     * @param defaultValue
     * @return Float or 0 if Name not existed
     */
    fun getPreferences(
        preName: String,
        defaultValue: Float
    ): Float {
        return mSharedPre!!.getFloat(preName, defaultValue)

    }


    companion object {

        private val PREFS_NAME = BuildConfig.APPLICATION_ID + BuildConfig.BUILD_TYPE + "Doctoor"

        @SuppressLint("StaticFieldLeak")
        private var mInstance: AppPreferences? = null

        @JvmStatic
        fun getInstance(context: Context): AppPreferences {
            if (mInstance == null) {
                mInstance = AppPreferences(context.applicationContext)
            }
            return mInstance as AppPreferences
        }
    }
}
