package com.cliper.skin.loader;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;

import com.cliper.skin.config.SkinConfig;
import com.cliper.skin.entity.AttrFactory;
import com.cliper.skin.entity.DynamicAttr;
import com.cliper.skin.entity.SkinAttrHolder;
import com.cliper.skin.entity.SkinItem;
import com.cliper.skin.util.L;
import com.cliper.skin.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一处理 加载 解析 view
 *  @author	ZH-SW-luoz
 *  Cliper.luoz
 * 下午5:37:13
 */
public class SkinInflaterFactory implements Factory {
	
	private static final boolean DEBUG = true;
	

	private List<SkinItem> mSkinItems = new ArrayList<SkinItem>();
	
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		boolean isSkinEnable = attrs.getAttributeBooleanValue(SkinConfig.NAMESPACE, SkinConfig.ATTR_SKIN_ENABLE, false);
        if (!isSkinEnable){
        		return null;
        }
		
		View view = createView(context, name, attrs);
		
		if (view == null){
			return null;
		}
		
		parseSkinAttr(context, attrs, view);
		
		return view;
	}
	
	/**
	 * 
	 * @param context
	 * @param name
	 * @param attrs
	 * @return
	 */
	private View createView(Context context, String name, AttributeSet attrs) {
		View view = null;
		try {
			if (-1 == name.indexOf('.')){
				if ("View".equals(name)) {
					view = LayoutInflater.from(context).createView(name, "android.view.", attrs);
				} 
				if (view == null) {
					view = LayoutInflater.from(context).createView(name, "android.widget.", attrs);
				} 
				if (view == null) {
					view = LayoutInflater.from(context).createView(name, "android.webkit.", attrs);
				} 
			}else {
	            view = LayoutInflater.from(context).createView(name, null, attrs);
	        }
			L.w("SkinLoader", "=="+view.getClass().getName());
		} catch (Exception e) { 
			L.e("error while create " + name + " : " + e.getMessage());
			view = null;
		}
		return view;
	}

	/**
	 * 
	 * @param context
	 * @param attrs
	 * @param view
	 */
	private void parseSkinAttr(Context context, AttributeSet attrs, View view) {
		List<SkinAttrHolder> viewAttrs = new ArrayList<SkinAttrHolder>();
		
		for (int i = 0; i < attrs.getAttributeCount(); i++){
			String attrName = attrs.getAttributeName(i);
			String attrValue = attrs.getAttributeValue(i);
			if(!AttrFactory.isSupportedAttr(attrName)){
				continue;
			}
		    if(attrValue.startsWith("@")){
		    	if (attrValue.startsWith("@style")) {
					// 支持 style坑多  ，后期维护
		    		L.w("SkinLoader","暂不支持style,前方是坑！！！");
				}else{
					try {
						int id = Integer.parseInt(attrValue.substring(1));
						String entryName = context.getResources().getResourceEntryName(id);
						String typeName = context.getResources().getResourceTypeName(id);
						SkinAttrHolder mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
						if (mSkinAttr != null) {
							viewAttrs.add(mSkinAttr);
						}
						L.w("SkinLoader","attrName:"+attrName);
						L.w("SkinLoader","attrValue"+":"+attrValue);
						L.w("SkinLoader","entryName"+":"+entryName);
						L.w("SkinLoader","typeName"+":"+typeName);
					} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (NotFoundException e) {
					e.printStackTrace();
				}
			}
		    }
		}
		
		if(!ListUtils.isEmpty(viewAttrs)){
			SkinItem skinItem = new SkinItem();
			skinItem.view = view;
			skinItem.attrs = viewAttrs;
			mSkinItems.add(skinItem);
			if(SkinManager.getInstance().isExternalSkin()){
				skinItem.apply();
			}
		}
	}
	
	public void applySkin(){
		if(ListUtils.isEmpty(mSkinItems)){
			return;
		}
		
		for(SkinItem si : mSkinItems){
			if(si.view == null){
				continue;
			}
			si.apply();
		}
	}
	
	/**
	 * 动态的添加View  (new TextView(context , attr)  )  多个R.drawable.xxx
	 * @param context
	 * @param view
	 * @param pDAttrs
	 */
	public void dynamicAddSkinEnableView(Context context, View view, List<DynamicAttr> pDAttrs){
		List<SkinAttrHolder> viewAttrs = new ArrayList<SkinAttrHolder>();
		SkinItem skinItem = new SkinItem();
		skinItem.view = view;
		
		for(DynamicAttr dAttr : pDAttrs){
			int id = dAttr.refResId;
			String entryName = context.getResources().getResourceEntryName(id);
			String typeName = context.getResources().getResourceTypeName(id);
			SkinAttrHolder mSkinAttr = AttrFactory.get(dAttr.attrName, id, entryName, typeName);
			viewAttrs.add(mSkinAttr);
		}
		
		skinItem.attrs = viewAttrs;
		addSkinView(skinItem);
	}
	
	/**
	 * 
	 * @param context
	 * @param view
	 * @param attrName
	 * @param attrValueResId
	 */
	public void dynamicAddSkinEnableView(Context context, View view, String attrName, int attrValueResId){	
		int id = attrValueResId;
		String entryName = context.getResources().getResourceEntryName(id);
		String typeName = context.getResources().getResourceTypeName(id);
		SkinAttrHolder mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
		SkinItem skinItem = new SkinItem();
		skinItem.view = view;
		List<SkinAttrHolder> viewAttrs = new ArrayList<SkinAttrHolder>();
		viewAttrs.add(mSkinAttr);
		skinItem.attrs = viewAttrs;
		addSkinView(skinItem);
	}
	
	public void addSkinView(SkinItem item){
		mSkinItems.add(item);
	}
	
	public void clean(){
		if(ListUtils.isEmpty(mSkinItems)){
			return;
		}
		
		for(SkinItem si : mSkinItems){
			if(si.view == null){
				continue;
			}
			si.clean();
		}
	}
}
