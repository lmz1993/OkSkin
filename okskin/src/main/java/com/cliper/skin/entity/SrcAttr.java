package com.cliper.skin.entity;


import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.cliper.skin.loader.SkinManager;

/**
 *  if you need src  , you can extends SrcAttr  
 * @author Cliper.luoz
 *
 * 上午11:34:53
 */
public class SrcAttr extends SkinAttrHolder{

	@Override
	public void apply(View view) {
		// TODO Auto-generated method stub
		if (view instanceof ImageView) {
			ImageView iv = (ImageView) view;
			if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
				Drawable bg = SkinManager.getInstance().getDrawable(attrValueRefId);
				if (bg == null) {
					return ;
				}
				iv.setImageDrawable(bg);
			}else if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				ColorDrawable colorDrawable = new ColorDrawable(SkinManager.getInstance().getColor(attrValueRefId));
				iv.setImageDrawable(colorDrawable);
			}
			
		}

		if (view instanceof AppCompatImageView) {
			AppCompatImageView iv = (AppCompatImageView) view;
			if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
				Drawable bg = SkinManager.getInstance().getDrawable(attrValueRefId);
				if (bg == null) {
					return ;
				}
				iv.setImageDrawable(bg);
			}else if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				ColorDrawable colorDrawable = new ColorDrawable(SkinManager.getInstance().getColor(attrValueRefId));
				iv.setImageDrawable(colorDrawable);
			}

		}

		
		if (view instanceof ImageButton) {
			ImageButton imageButton = (ImageButton) view;
			if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
				Drawable bg = SkinManager.getInstance().getDrawable(attrValueRefId);
				if (bg == null) {
					return ;
				}
				imageButton.setImageDrawable(bg);
			}else if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				ColorDrawable colorDrawable = new ColorDrawable(SkinManager.getInstance().getColor(attrValueRefId));
				imageButton.setImageDrawable(colorDrawable);
			}
		}

		if (view instanceof AppCompatImageButton){
			AppCompatImageButton imageButton = (AppCompatImageButton) view;
			if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
				Drawable bg = SkinManager.getInstance().getDrawable(attrValueRefId);
				if (bg == null) {
					return ;
				}
				imageButton.setImageDrawable(bg);
			}else if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				ColorDrawable colorDrawable = new ColorDrawable(SkinManager.getInstance().getColor(attrValueRefId));
				imageButton.setImageDrawable(colorDrawable);
			}
		}

	}

}
