package com.tson.utils.lib.util.sp;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created tangxin
 * Time 2018/10/11 下午1:45
 */
public abstract class BaseSharedPreferencesFactory {

    private Context mContext;
    private int mMode;
    private SharedPreferences mSharedPreferences;

    /**
     * Instantiates a new Base shared preferences factory.
     *
     * @param context the context
     */
    public BaseSharedPreferencesFactory(Context context) {
        this(context, Context.MODE_PRIVATE);
    }

    /**
     * Instantiates a new Base shared preferences factory.
     *
     * @param context the context
     * @param mode    the mode
     */
    public BaseSharedPreferencesFactory(Context context, int mode) {
        mContext = context.getApplicationContext();
        mMode = mode;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    protected abstract String getKey();

    /**
     * Clear data.
     */
    public void clearData() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        editor.apply();
    }

    /**
     * Remove by key.
     *
     * @param key the key
     */
    public void removeByKey(String key) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * Gets shared preferences.
     *
     * @return the shared preferences
     */
    public SharedPreferences getSharedPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = mContext.getSharedPreferences(getKey(), mMode);
        }
        return mSharedPreferences;
    }

    public void clearSp() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * Sets bool value.
     *
     * @param keyName the key name
     * @param value   the value
     */
    public void setBoolValue(String keyName, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(keyName, value);
        editor.apply();
    }

    /**
     * Gets bool value.
     *
     * @param keyName  the key name
     * @param defValue the def value
     * @return the bool value
     */
    public boolean getBoolValue(String keyName, boolean defValue) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getBoolean(keyName, defValue);
    }

    /**
     * Sets string value.
     *
     * @param keyName the key name
     * @param value   the value
     */
    public void setStringValue(String keyName, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(keyName, value);
        editor.apply();
    }

    /**
     * Gets string value.
     *
     * @param keyName the key name
     * @return the string value
     */
    public String getStringValue(String keyName) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getString(keyName, "");
    }

    /**
     * Sets int value.
     *
     * @param keyName the key name
     * @param value   the value
     */
    public void setIntValue(String keyName, int value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(keyName, value);
        editor.apply();
    }

    /**
     * Gets int value.
     *
     * @param keyName  the key name
     * @param defValue the def value
     * @return the int value
     */
    public int getIntValue(String keyName, int defValue) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getInt(keyName, defValue);
    }
}
