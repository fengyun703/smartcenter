package com.lw.smartcenter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.webkit.WebSettings.TextSize;

public class SharePrefereceUtils {
	private static final String NAME = "config";
	private static SharedPreferences sp;

	public static void getSharedPreferences(Context context) {
		if (sp == null) {
			sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		}
	}

	public static boolean getBoolean(Context context, String key) {
		return sp.getBoolean(key, false);
	}

	public static boolean getBoolean(Context context, String key,
			boolean initalValue) {
		getSharedPreferences(context);
		return sp.getBoolean(key, initalValue);
	}

	public static void setBoolean(Context context, String key, boolean setValue) {
		getSharedPreferences(context);
		Editor et = sp.edit();
		et.putBoolean(key, setValue);
		et.commit();
	}

	public static String getString(Context context, String key) {
		getSharedPreferences(context);
		return sp.getString(key, null);
	}

	public static String getString(Context context, String key,
			String initalValue) {
		getSharedPreferences(context);
		return sp.getString(key, initalValue);
	}

	public static void setString(Context context, String key, String setValue) {
		getSharedPreferences(context);
		Editor et = sp.edit();
		et.putString(key, setValue);
		et.commit();
	}

	public static long getLong(Context context, String key) {
		getSharedPreferences(context);
		return sp.getLong(key, -1);
	}

	public static long getLong(Context context, String key, long initalValue) {
		getSharedPreferences(context);
		return sp.getLong(key, initalValue);
	}

	public static void setLong(Context context, String key, long setValue) {
		getSharedPreferences(context);
		Editor et = sp.edit();
		et.putLong(key, setValue);
		et.commit();
	}

	public static int getInt(Context context, String key) {
		getSharedPreferences(context);
		return sp.getInt(key, -1);
	}

	public static int getInt(Context context, String key, int initalValue) {
		getSharedPreferences(context);
		return sp.getInt(key, initalValue);
	}

	public static void setInt(Context context, String key, int setValue) {
		getSharedPreferences(context);
		Editor et = sp.edit();
		et.putInt(key, setValue);
		et.commit();
	}


}
