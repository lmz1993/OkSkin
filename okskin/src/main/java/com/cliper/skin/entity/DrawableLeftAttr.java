package com.cliper.skin.entity;


import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.cliper.skin.config.AttrConfig;
import com.cliper.skin.loader.SkinManager;

/**
 * 
 * @author Cliper.luoz
 *	TextView的drawableLeft属性处理
 * 上午11:51:57
 */
public class DrawableLeftAttr extends SkinAttrHolder{

	@Override
	public void apply(View view) {
		// TODO Auto-generated method stub
		 if(null == view
	                || !(AttrConfig.RES_DRAWABLE_LEFT.equals(attrName))) {
	            return;
	        }

	        if (!(view instanceof TextView)) {
	            return;
	        }

	        Drawable drawable = SkinManager.getInstance().getDrawable(attrValueRefId);

	        if(null != drawable) {
	            ((TextView)view).setCompoundDrawablesWithIntrinsicBounds(
	                    drawable, null, null, null);
	        }
	}

}
