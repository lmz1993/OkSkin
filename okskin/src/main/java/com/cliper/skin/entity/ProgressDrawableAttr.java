package com.cliper.skin.entity;


import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.SeekBar;

import com.cliper.skin.loader.SkinManager;

/**
 * 
 *  @author	ZH-SW-luoz
 *  Cliper.luoz
 * 下午4:58:03
 */
public class ProgressDrawableAttr extends SkinAttrHolder {

	@Override
	public void apply(View view) {
		// TODO Auto-generated method stub
		if(view instanceof SeekBar){
			SeekBar mSeekBar = (SeekBar) view;
			if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
				Drawable pdDraw = SkinManager.getInstance().getDrawable(attrValueRefId);
				if (pdDraw == null) {
					return;
				}
				mSeekBar.setProgressDrawable(pdDraw);
			}else if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				int pdColor = SkinManager.getInstance().getColor(attrValueRefId);
				ColorDrawable pdDrawable = new ColorDrawable(pdColor);
				mSeekBar.setProgressDrawable(pdDrawable);
			}
			
		}
	}

}
