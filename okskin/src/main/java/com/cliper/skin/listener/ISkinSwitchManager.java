package com.cliper.skin.listener;

import java.util.Map;

import android.view.View;
import android.view.animation.Interpolator;

/**
 * 
 * @author Cliper.luoz
 *
 * 下午3:58:33
 */
public interface ISkinSwitchManager {

	/**
	 * 保存你当前的所有的主题path
	 * @param keySkin
	 * @param skinPath
	 */
	ISkinSwitchManager saveSkinTheme(int keySkin, String skinPath);

	/**
	 * 移除你当前的
	 * @param keySkin
	 */
	ISkinSwitchManager removeSkinTheme(int keySkin);
	
	
	
	/**
	 * 
	 * @param 传入你的选择的skin,真正的一键换肤
	 */
	void switchSkinTheme(int keySkin);
	
	
	/**
	 * 
	 * @param 传入你的选择的skin,真正的一键换肤
	 */
	void switchSkinTheme(int keySkin, ISwitchThemeListener themeListener);
	
	/**
	 * 恢复默认主题
	 */
	void restoreDefaultTheme();
	
	/**
	 *skin 切换Interpolator
	 */
	public void setSkinInterpolator(View view, Interpolator mInterpolator);
	
	
	/**
	 *skin 切换Interpolator
	 */
	public void setSkinInterpolator(View view);
}
