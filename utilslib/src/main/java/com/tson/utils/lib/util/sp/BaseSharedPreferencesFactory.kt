package com.tson.utils.lib.util.sp

import android.content.Context
import android.content.SharedPreferences


/**
 * Created tangxin
 * Time 2018/10/11 下午1:45
 */
abstract class BaseSharedPreferencesFactory
/**
 * Instantiates a new Base shared preferences factory.
 *
 * @param context the context
 * @param mode    the mode
 */
@JvmOverloads constructor(context: Context, private val mMode: Int = Context.MODE_PRIVATE) {

    private val mContext: Context
    private lateinit var mSharedPreferences: SharedPreferences

    /**
     * Gets key.
     *
     * @return the key
     */
    protected abstract val key: String

    /**
     * Gets shared preferences.
     *
     * @return the shared preferences
     */
    val sharedPreferences: SharedPreferences
        get() {
            if (mSharedPreferences == null) {
                mSharedPreferences = mContext.getSharedPreferences(key, mMode)
            }
            return mSharedPreferences
        }

    init {
        mContext = context.applicationContext
    }

    /**
     * Clear data.
     */
    fun clearData() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    /**
     * Remove by key.
     *
     * @param key the key
     */
    fun removeByKey(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    /**
     * Sets bool value.
     *
     * @param keyName the key name
     * @param value   the value
     */
    fun setBoolValue(keyName: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(keyName, value)
        editor.apply()
    }

    /**
     * Gets bool value.
     *
     * @param keyName  the key name
     * @param defValue the def value
     * @return the bool value
     */
    fun getBoolValue(keyName: String, defValue: Boolean): Boolean {
        val sp = sharedPreferences
        return sp.getBoolean(keyName, defValue)
    }

    /**
     * Sets string value.
     *
     * @param keyName the key name
     * @param value   the value
     */
    fun setStringValue(keyName: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(keyName, value)
        editor.apply()
    }

    /**
     * Gets string value.
     *
     * @param keyName the key name
     * @return the string value
     */
    fun getStringValue(keyName: String): String? {
        val sp = sharedPreferences
        return sp.getString(keyName, "")
    }

    /**
     * Sets int value.
     *
     * @param keyName the key name
     * @param value   the value
     */
    fun setIntValue(keyName: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(keyName, value)
        editor.apply()
    }

    /**
     * Gets int value.
     *
     * @param keyName  the key name
     * @param defValue the def value
     * @return the int value
     */
    fun getIntValue(keyName: String, defValue: Int): Int {
        val sp = sharedPreferences
        return sp.getInt(keyName, defValue)
    }
}
/**
 * Instantiates a new Base shared preferences factory.
 *
 * @param context the context
 */
