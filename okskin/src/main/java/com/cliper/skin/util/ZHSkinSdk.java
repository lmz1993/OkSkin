package com.cliper.skin.util;

import android.content.Context;




/**
 * SDK主类
 * <p></p>
 * 上午10:30:04
 *
 * @author ZH-SW-Lilc
 * @version 1.0.0
 */
public class ZHSkinSdk {
public static Context mContext;
	
	/**
	 * 初始化SDK
	 * @param context 上下文
	 */
	public static void initSDK(Context context){
		mContext = context;
	}
	
}
