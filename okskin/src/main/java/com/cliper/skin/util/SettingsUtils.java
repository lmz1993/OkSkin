/**
 * Copyright (c) 深圳市众鸿科技股份有限公司
 * @file_name ConfigPreferences.java
 * @class com.zhonghong.sdk.android.logic.systembar.ConfigPreferences
 * @create 下午3:58:36
 */
package com.cliper.skin.util;


import android.content.Intent;
import android.provider.Settings;



/**
 * 
 * <p>
 * </p>
 * 下午3:58:36
 * 
 * @author ZH-SW-Lilc
 * @version 1.0.0
 */
public class SettingsUtils {

	private static SettingsUtils preferences;

	public static synchronized SettingsUtils getInstance() {
		if (preferences == null) {
			preferences = new SettingsUtils();
		}
		return preferences;
	}

	/**
	 * 设置int数据
	 * 
	 * @param key
	 * @param value
	 */
	public void setInt(String action,String key, int value) {
	
		Settings.System.putInt(ZHSkinSdk.mContext.getContentResolver(), key,
				value);
		sendStatusBroadcast(action,key,value);
	}

	/**
	 * 获取int数据
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		return Settings.System.getInt(ZHSkinSdk.mContext.getContentResolver(),
				key, 0);
	}


	/**
	 * 设置Long数据
	 * 
	 * @param key
	 * @param value
	 */
	public void setLong(String action,String key, long value) {
		Settings.System.putLong(ZHSkinSdk.mContext.getContentResolver(), key,
				value);
		sendStatusBroadcast(action);
	}

	/**
	 * 获取Long数据
	 * 
	 * @param key
	 * @return
	 */
	public long getLong(String key) {
		return Settings.System.getLong(ZHSkinSdk.mContext.getContentResolver(),
				key, 0);
	}

	/**
	 * 设置String数据
	 * 
	 * @param key
	 * @param value
	 */
	public void setString(String action,String key, String value) {
		Settings.System.putString(ZHSkinSdk.mContext.getContentResolver(), key,
				value);
		/**
		 * 每次更新完数据发送一条系统广播。其他地方监听到广播，get数据
		 */
		sendStatusBroadcast(action);
	}

	/**
	 * 获取String数据
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return Settings.System.getString(
				ZHSkinSdk.mContext.getContentResolver(), key);
	}
	
	private static void sendStatusBroadcast(String action) {
		Intent intent = new Intent();
		intent.setAction(action);
		ZHSkinSdk.mContext.sendBroadcast(intent);
	}

	private static void sendStatusBroadcast(String action ,String key, int value) {
		Intent intent = new Intent();
		intent.setAction(action);
		intent.putExtra(key, value);
		ZHSkinSdk.mContext.sendBroadcast(intent);
	}

}
