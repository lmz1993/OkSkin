package com.cliper.skin.listener;

/**
 * 
 * @author Cliper.luoz
 *
 * 下午4:30:01
 */
public interface ISwitchThemeListener {
	
	void onSwitchStart();

	void onSwitchSuccess();
	
	void onSwitchError(String errorMesg);
	
	
	
}
