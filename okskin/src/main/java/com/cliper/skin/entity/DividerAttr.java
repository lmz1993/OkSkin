package com.cliper.skin.entity;


import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ListView;

import com.cliper.skin.loader.SkinManager;

/**
 *
 *  @author	Cliper.luoz
 * 上午10:33:30
 */
public class DividerAttr extends SkinAttrHolder {

	public int dividerHeight = 1;
	
	@Override
	public void apply(View view) {
		if(view instanceof ListView){
			ListView tv = (ListView)view;
			if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				int color = SkinManager.getInstance().getColor(attrValueRefId);
				ColorDrawable sage = new ColorDrawable(color);
				tv.setDivider(sage);
				tv.setDividerHeight(dividerHeight);
			}else if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
				Drawable dividerDrw = SkinManager.getInstance().getDrawable(attrValueRefId);
				tv.setDivider(dividerDrw);
			}
		}
	}
}
