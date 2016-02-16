package com.lw.smartcenter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePrefereceUtils {
	private static final String NAME = "config";
	private static SharedPreferences sp;

	public static void getSharedPreferences(Context context) {
		if (sp == null) {
			sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		}
	}
	
	public static boolean getBoolean(Context context,String key, boolean initalValue){
		getSharedPreferences(context);
		return sp.getBoolean(key, initalValue);
	}
	
	public static void setBoolean(Context context,String key, boolean setValue){
		getSharedPreferences(context);
		Editor et = sp.edit();
		et.putBoolean(key, setValue);
		et.commit();
	}
	
	
}
