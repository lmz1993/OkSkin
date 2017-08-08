package com.cliper.skin.entity;

import android.content.Context;
import android.content.res.Resources;

/**
 *  @author	Cliper.luoz
 */
public class ResourceParseEn {


	private Resources mResources ;
	
	private Context mContext ;
	
	private String packageName;
	
	private boolean isDefaultSkin ;
	
	public ResourceParseEn(Resources mResources, Context mContext,
			String packageName, boolean isDefaultSkin) {
		super();
		this.mResources = mResources;
		this.mContext = mContext;
		this.packageName = packageName;
		this.isDefaultSkin = isDefaultSkin;
	}

	public Resources getmResources() {
		return mResources;
	}

	public void setmResources(Resources mResources) {
		this.mResources = mResources;
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public boolean isDefaultSkin() {
		return isDefaultSkin;
	}

	public void setDefaultSkin(boolean isDefaultSkin) {
		this.isDefaultSkin = isDefaultSkin;
	}


	
}
