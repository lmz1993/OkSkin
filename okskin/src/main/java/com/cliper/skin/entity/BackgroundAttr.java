package com.cliper.skin.entity;


import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.cliper.skin.loader.SkinManager;

/**
 *
 *  @author	Cliper.luoz
 * 上午10:41:05
 */
public class BackgroundAttr extends SkinAttrHolder {

	@SuppressLint("NewApi") @Override
	public void apply(View view) {
		
		if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
			int colorRefId = SkinManager.getInstance().getColor(attrValueRefId);
			view.setBackgroundColor(colorRefId);
		}else if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
			Drawable bg = SkinManager.getInstance().getDrawable(attrValueRefId);
			view.setBackground(bg);
			Log.i("attr", this.attrValueRefName + " 是否可变换状态? : " + bg.isStateful());
		}else if(RES_TYPE_NAME_MIPMAP.equals(attrValueTypeName)){
			Drawable bg = SkinManager.getInstance().getMipmap(attrValueRefId);
			view.setBackground(bg);
		}
	}
}
