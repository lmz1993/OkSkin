package com.cliper.skin.entity;


import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AbsListView;

import com.cliper.skin.loader.SkinManager;

/**
 *
 *  @author	Cliper.luoz
 * 下午4:57:48
 */
public class ListSelectorAttr extends SkinAttrHolder {

	@Override
	public void apply(View view) {
		if(view instanceof AbsListView){
			AbsListView tv = (AbsListView)view;
			if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				int colorRefId = SkinManager.getInstance().getColor(attrValueRefId);
				tv.setSelector(colorRefId);
			}else if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
				Drawable listDraw = SkinManager.getInstance().getDrawable(attrValueRefId);
				if (listDraw == null) {
					return ;
				}
				tv.setSelector(listDraw);
			}
		}
	}
}
