package com.cliper.skin.listener;

import android.view.View;

/**
 * 
 * @author Cliper.luoz
 *
 * 下午4:58:48
 */
public interface ISkinLoader {
	void attach(ISkinUpdate observer);
	void detach(ISkinUpdate observer);
	void notifySkinUpdate();
	
	void  applyWMSkin(View view, boolean child);
	

}
