package com.cliper.skin.listener;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

/**
 * 
 * @author Cliper.luoz
 *
 * 下午4:58:36
 */
public interface IResourceLoader {
	
	/**
	 * 
	 * @param resId
	 * @return Color
	 */
	int getColor(int resId);
	
	/**
	 * 
	 * @param resId
	 * @return ColorStateList
	 */
	ColorStateList convertToColorStateList(int resId);
	/**
	 * 
	 * @param resId
	 * @return Drawable
	 */
	Drawable getDrawable(int resId);
	
	/**
	 * 
	 * @param resId
	 * @return
	 */
	StateListDrawable convertToStateListDrawable(int resId);
	
	/**
	 * 
	 * @param resId
	 * @return Drawable
	 */
	Drawable getMipmap(int resId);
	
	/**
	 * 
	 * @param resId
	 * @return String
	 */
	String getString(int resId);

	/**
	 *
	 * @return Bitmap
     */
	Bitmap getBitmap(int resId);


	void setResources(Resources mResources);


	Resources getResources();
}
