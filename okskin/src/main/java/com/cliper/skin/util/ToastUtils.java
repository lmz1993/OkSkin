package com.cliper.skin.util;

import android.widget.Toast;

/**
 * 
 * @author Cliper.luoz
 *
 * 下午6:25:51
 * 防重复show
 * 你必须 先在application初始化  initSDK
 */
public class ToastUtils {
	
	private static Toast mLToast = null;  
	private static Toast mSToast = null; 
	/**
	 * 
	 * @param text
	 */
    public static void showLToast( String text) {  
        if (mLToast == null) {  
        	mLToast = Toast.makeText(ZHSkinSdk.mContext, text, Toast.LENGTH_LONG);  
        } else {  
        	mLToast.setText(text);  
        	mLToast.setDuration(Toast.LENGTH_LONG);  
        }  
  
        mLToast.show();  
    }  
	
    /**
     * 
     * @param text
     */
    public static void showSToast( String text) {  
        if (mSToast == null) {  
        	mSToast = Toast.makeText(ZHSkinSdk.mContext, text, Toast.LENGTH_SHORT);  
        } else {  
        	mSToast.setText(text);  
        	mSToast.setDuration(Toast.LENGTH_SHORT);  
        }  
        mSToast.show();  
    }  
	
    

	
}
