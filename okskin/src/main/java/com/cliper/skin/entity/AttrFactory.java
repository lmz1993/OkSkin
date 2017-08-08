package com.cliper.skin.entity;


import com.cliper.skin.config.AttrConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 *
 *  @author	Cliper.luoz
 * 上午10:34:57
 */
public class AttrFactory {
	
	/**
	 * save out attr(attrName , SkinAttrHolder)
	 *
	 */
	private static ConcurrentHashMap<String, SkinAttrHolder> attrMap = new ConcurrentHashMap<String, SkinAttrHolder>();
	
	
	public static SkinAttrHolder get(String attrName, int attrValueRefId, String attrValueRefName, String typeName){
		SkinAttrHolder mSkinAttr = null;
		
		if(AttrConfig.RES_BACKGROUND.equals(attrName)){
			mSkinAttr = new BackgroundAttr();
		}else if(AttrConfig.RES_TEXT_COLOR.equals(attrName)){ 
			mSkinAttr = new TextColorAttr();
		}else if(AttrConfig.RES_LIST_SELECTOR.equals(attrName)){ 
			mSkinAttr = new ListSelectorAttr();
		}else if(AttrConfig.RES_DIVIDER.equals(attrName)){ 
			mSkinAttr = new DividerAttr();
		}else if(AttrConfig.RES_SRC.equals(attrName)){
			mSkinAttr = new SrcAttr();
		}else if(AttrConfig.RES_THUMB.equals(attrName)){
			mSkinAttr = new  ThumbAttr();
		}else if(AttrConfig.RES_PROGRESS_DRAWABLE.equals(attrName)){
			mSkinAttr = new ProgressDrawableAttr();
		}else if(AttrConfig.RES_SCROLLBAR_THUMBVERTICAL.equals(attrName)){
			mSkinAttr = new ScrollbarThumbVerticalAttr();
		}else if(AttrConfig.RES_SCROLLBAR_TRACKVERTICAL.equals(attrName)){
			mSkinAttr = new ScrollbarTrackVerticalAttr();
		}else if(AttrConfig.RES_TEXT_COLOR_HINT.equals(attrName)){
			mSkinAttr = new TextColorHintAttr();
		}else if(AttrConfig.RES_DRAWABLE_LEFT.equals(attrName)){
			mSkinAttr = new DrawableLeftAttr();
		}else {
			mSkinAttr = getRegistAttrHolder(attrName);
		}
		
		mSkinAttr.attrName = attrName;
		mSkinAttr.attrValueRefId = attrValueRefId;
		mSkinAttr.attrValueRefName = attrValueRefName;
		mSkinAttr.attrValueTypeName = typeName;
		return mSkinAttr;
	}
	
	/**
	 * Check whether the attribute is supported
	 * @param attrName
	 * @return true : supported <br>
	 * 		   false: not supported
	 */
	public static boolean isSupportedAttr(String attrName){
		if(AttrConfig.RES_BACKGROUND.equals(attrName)){ 
			return true;
		}
		if(AttrConfig.RES_TEXT_COLOR.equals(attrName)){ 
			return true;
		}
		if(AttrConfig.RES_LIST_SELECTOR.equals(attrName)){ 
			return true;
		}
		if(AttrConfig.RES_DIVIDER.equals(attrName)){ 
			return true;
		}
		if(AttrConfig.RES_SRC.equals(attrName)){
			return true;
		}
		
		if(AttrConfig.RES_THUMB.equals(attrName)){
			return true;
		}
			
		if(AttrConfig.RES_PROGRESS_DRAWABLE.equals(attrName)){
			return true;
		}
			
		if(AttrConfig.RES_SCROLLBAR_THUMBVERTICAL.equals(attrName)){
			return true;
		}
			
		if(AttrConfig.RES_SCROLLBAR_TRACKVERTICAL.equals(attrName)){
			return true;
		}
		
		if(AttrConfig.RES_TEXT_COLOR_HINT.equals(attrName)){
			return true;
		}
		if(AttrConfig.RES_DRAWABLE_LEFT.equals(attrName)){
			return true;
		}
		if(isOutSupportAttr(attrName)){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 是否支持外部属性
	 * @param attrName
	 * @return
	 */
	private static boolean isOutSupportAttr(String attrName) {
		// TODO Auto-generated method stub
		return attrMap.get(attrName) != null ? true : false ;
	}

	
	/**
	 * 
	 * 
	 * 先存属性
	 * @param attrTypeName ="my_text_color"  (@color/my_text_color)
	 * attrTypeName = 	app:circleColor = "@color/my_text_color"    (circleColor)
	 * @param skin extends skinHolder
	 */
	public static void registAttrHolder(String attrName, SkinAttrHolder skin) {
		attrMap.put(attrName, skin);
	}
	
	
	/**
	 * 
	 * 存Map属性
	 * @param attrTypeName ="my_text_color"  (@color/my_text_color)
	 * attrTypeName = 	app:circleColor = "@color/my_text_color"    (circleColor)
	 * @param skin extends skinHolder
	 */
	public static void registAttrHolderMap(Map<String , SkinAttrHolder> attrMaps) {
		attrMap.putAll(attrMaps);
	}
	
	private static SkinAttrHolder getRegistAttrHolder(String attrName){
		if (isOutSupportAttr(attrName)) {
			return attrMap.get(attrName);
		}
		return null;
	}
	/**
	 * 
	 * 移除
	 * @param attrTypeName ="my_text_color"  (@color/my_text_color)
	 * attrTypeName = 	app:circleColor = "@color/my_text_color"    (circleColor)
	 * @param skin extends skinHolder
	 *  
	 */
	public static void removeAttrHolder(String attrName) {
		attrMap.remove(attrName);
	}
	
}
