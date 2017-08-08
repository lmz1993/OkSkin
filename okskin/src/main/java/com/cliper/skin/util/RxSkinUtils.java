package com.cliper.skin.util;


import com.cliper.skin.config.SkinSwitchConstracts;

/**
 * 
 * 
 * 皮肤资源切换utils
 *  @author	ZH-SW-luoz
 *  Cliper.luoz
 * TODO
	下午4:36:06
	多个应用共享skinTAG
 */
public class RxSkinUtils {
	
	
	private RxSkinUtils(){}
	
	private static class RxSkinHolder{
		private final static RxSkinUtils mRxSkinUtils = new RxSkinUtils();
	}
	
	/**
	 * return a global static instance of 
	 * @return
	 */
	public static RxSkinUtils getInstance() {
		return RxSkinHolder.mRxSkinUtils ;
	}
	
	
	/**
	 * 得到当前应用的皮肤
	 * @return
	 */
	public  int getCurrentSelectSkin(){
		return SettingsUtils.getInstance().getInt(SkinSwitchConstracts.SAVE_SKIN_SELECT);
	}
	/**
	 * 设置显示的皮肤资源
	 * @param vauleSkin
	 */
	public  void setCurrentSelectSkin(String action , String key , int vauleSkin){
		 SettingsUtils.getInstance().setInt( action , SkinSwitchConstracts.SAVE_SKIN_SELECT ,vauleSkin);
	}
	
	
}
