package com.cliper.skin.entity;


import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.SeekBar;

import com.cliper.skin.loader.SkinManager;

/**
 *  @author	ZH-SW-luoz
 *  Cliper.luoz
 * 上午10:48:47
 */
public class ThumbAttr extends SkinAttrHolder{

	@Override
	public void apply(View view) {
		
		if(view instanceof SeekBar){
			SeekBar mSeekBar = (SeekBar) view;
			if(RES_TYPE_NAME_DRAWABLE.equals(attrValueRefName)){
				Drawable thumbDrawable = SkinManager.getInstance().getDrawable(attrValueRefId);
				if (thumbDrawable == null) {
					return;
				}
				mSeekBar.setThumb(thumbDrawable);
			
			}
			if(RES_TYPE_NAME_COLOR.equals(attrValueRefName)){
				int colorId = SkinManager.getInstance().getColor(attrValueRefId);
				ColorDrawable thumbColor = new ColorDrawable(colorId);
				if (colorId == 0 || thumbColor == null) {
					return;
				}
				
				mSeekBar.setThumb(thumbColor);
			}
		}
		
		
		
	}

}
