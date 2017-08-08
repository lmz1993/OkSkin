package com.cliper.skin.entity;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;

import com.cliper.skin.config.AttrConfig;
import com.cliper.skin.loader.SkinManager;

/**
 * 
 * @author Cliper.luoz
 *
 * 上午11:44:46
 */
public class TextColorHintAttr extends SkinAttrHolder{

	@Override
	public void apply(View view) {
		// TODO Auto-generated method stub
		if (null == view
                || null == attrName
                || !(AttrConfig.RES_TEXT_COLOR_HINT.equals(attrName))) {
            return;
        }

        if (!(view instanceof TextView)) {
            return;
        }
        
        TextView tv = (TextView) view;
        if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
            //按照ColorStateList引用来解析；
            //context.getResources().getColor()方法可以取纯颜色，也可以取ColorStateList引用内的颜色，
            //如果取的是ColorStateList，则取其中默认颜色；
            //同时，context.getResources().getColorStateList()方法也可以取纯颜色生成一个ColorStateList
            ColorStateList textHintColor = SkinManager.getInstance().convertToColorStateList(attrValueRefId);
            tv.setHintTextColor(textHintColor);
        }
        
	}

}
