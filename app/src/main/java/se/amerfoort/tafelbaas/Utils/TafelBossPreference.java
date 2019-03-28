package se.amerfoort.tafelbaas.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TafelBossPreference {
    public static final String PREFERENCE = "TBPreference";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_ID = "USER_ID";
    public static final String QR_CODE = "QR_CODE";

    private static Context mContext;
    private static TafelBossPreference tafelBossPreference;
    private SharedPreferences sharedPreferences;

    private TafelBossPreference() {
        sharedPreferences = mContext.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
    }

    public static TafelBossPreference getInstance() {
        if (tafelBossPreference == null) {
            tafelBossPreference = new TafelBossPreference();
        }
        return tafelBossPreference;
    }

    public static Context getmContext() {
        return mContext;
    }

    public static void setmContext(Context mContext) {
        TafelBossPreference.mContext = mContext;
    }

    public static String getUDID() {
        String id = android.provider.Settings.System.getString(mContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return id;
    }

    public void saveStringData(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public void saveIntData(String key, int value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }

    public String getStringData(String key) {
        String data = null;
        if (sharedPreferences != null) {
            data = sharedPreferences.getString(key, "");
        }
        return data;
    }

    public String getStringData(String key, String defaultValue) {
        String data = null;
        if (sharedPreferences != null) {
            data = sharedPreferences.getString(key, defaultValue);
        }
        if (data.equals(defaultValue)) {
            return defaultValue;
        }
        return data;
    }

    public int getIntData(String key) {
        int data = 0;
        if (sharedPreferences != null) {
            data = sharedPreferences.getInt(key, 0);
        }
        return data;
    }

    public void saveBooleanData(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public boolean getBooleanData(String key) {
        boolean data = false;
        if (sharedPreferences != null) {
            data = sharedPreferences.getBoolean(key, false);
        }
        return data;
    }

}