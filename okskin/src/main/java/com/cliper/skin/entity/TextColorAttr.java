package com.cliper.skin.entity;


import android.view.View;
import android.widget.TextView;

import com.cliper.skin.loader.SkinManager;

/**
 * 
 *  @author	ZH-SW-luoz
 *  Cliper.luoz
 * 上午10:34:11
 */
public class TextColorAttr extends SkinAttrHolder {

	@Override
	public void apply(View view) {
		if(view instanceof TextView){
			TextView tv = (TextView)view;
			if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				tv.setTextColor(SkinManager.getInstance().convertToColorStateList(attrValueRefId));
			}
		}
	}
}
