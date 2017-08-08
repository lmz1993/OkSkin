package com.cliper.skin.entity;

import android.view.View;
/**
 * 
 *  @author	ZH-SW-luoz
 *  Cliper.luoz
 * 上午10:33:56
 * 
 *   所有需要换肤的View属性  都必须继承SkinAttrHolder 
 *   可处理 color ，  drawable  ，dimen  ，string
 *   attrName :{background or textSize or textColor,...}     
 *   attrValueRefId:{2130745655}     
 *   attrValueRefName:{test_bt_background(R.color.test_bt_background)}    
 *   attrValueTypeName:{color , drawable , dimen , string}
 *   
 */
public abstract class SkinAttrHolder {
	
	protected static final String RES_TYPE_NAME_COLOR = "color";
	protected static final String RES_TYPE_NAME_DRAWABLE = "drawable";
	protected static final String RES_TYPE_NAME_DIMEN = "dimen";
	protected static final String RES_TYPE_NAME_STRING = "string";
	protected static final String RES_TYPE_NAME_MIPMAP = "mipmap";
	/**
	 * 暂时不支持
	 */
	protected static final String RES_TYPE_NAME_STYLE = "style";
	/**
	 * name of the attr, ex: background or textSize or textColor
	 */
	public String attrName;
	
	/**
	 * id of the attr value refered to, normally is [2130745655]
	 */
	public int attrValueRefId;
	
	/**
	 * entry name of the value , such as [test_bt_background]
	 */
	public String attrValueRefName;
	
	/**
	 * type of the value , such as color or drawable
	 */
	public String attrValueTypeName;
	
	/**
	 * Use to apply view with new TypedValue
	 * @param view
	 */
	public abstract void apply(View view);

	@Override
	public String toString() {
		return "SkinAttr \n[\nattrName=" + attrName + ", \n"
				+ "attrValueRefId=" + attrValueRefId + ", \n"
				+ "attrValueRefName=" + attrValueRefName + ", \n"
				+ "attrValueTypeName=" + attrValueTypeName
				+ "\n]";
	}
}
