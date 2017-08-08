package com.cliper.skin.config;

import android.content.Context;

import com.cliper.skin.util.PreferencesUtils;

/**
 *
 *  @author	Cliper.luomz
 * 下午2:13:53
 */
public class SkinConfig {
	public  static final String     NAMESPACE 				=   "http://schemas.android.com/android/skin";
	public 	static final String 	PREF_CUSTOM_SKIN_PATH 	= 	"com_cliper_skin_custom_path";
	public  static final String 	DEFALT_SKIN 			= 	"com_cliper_skin_default";
    public 	static final String 	ATTR_SKIN_ENABLE	    =   "enable";
	
    
    public static final String SKIN_PREFIX ="skin:";
	/**
	 * get path of last skin package path
	 * @param context
	 * @return path of skin package
	 */
	public static String getCustomSkinPath(Context context){
		return PreferencesUtils.getString(context, PREF_CUSTOM_SKIN_PATH, DEFALT_SKIN);
	}
	
	public static void saveSkinPath(Context context, String path){
		PreferencesUtils.putString(context, PREF_CUSTOM_SKIN_PATH, path);
	}
	
	/**
	 * true 是系统默认资源
	 * @param context
	 * @return
	 */
	public static boolean isDefaultSkin(Context context){
		return DEFALT_SKIN.equals(getCustomSkinPath(context));
	}
}
