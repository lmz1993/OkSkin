package com.cliper.skin.entity;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.cliper.skin.loader.SkinManager;

import java.lang.reflect.Field;

/**
 * 
 * @author Cliper.luoz
 *ScrollView  , ListView
 * 上午10:33:52
 */
public class ScrollbarTrackVerticalAttr extends SkinAttrHolder {

	@Override
	public void apply(View view) {
		if (view instanceof ScrollView) {
			ScrollView mScrollView = (ScrollView)view;
			if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
					onTrackVerticalDrawable(mScrollView);
			}else if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				try {
					onTrackVerticalColor(mScrollView);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
				//-----------------TODO ListView BUG
				if (view instanceof ListView) {
					ListView mListView = (ListView)view;
					if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
						try {
							Field f = AbsListView.class.getDeclaredField("mFastScroller");

							f.setAccessible(true);

							Object o=f.get(mListView);

							f=f.getType().getDeclaredField("mTrackDrawable");
							f.setAccessible(true);

							Drawable drawable=(Drawable) f.get(o);

							drawable = SkinManager.getInstance().getDrawable(attrValueRefId);
							
							f.set(o,drawable);
						
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						
						}
					}else if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
					
							try {

								Field filed = AbsListView.class.getDeclaredField("mFastScroll");
								
								filed.setAccessible(true);
								
								Object o=filed.get(mListView);
							
								filed=filed.getType().getDeclaredField("mTrackDrawable");
							

								filed.setAccessible(true);

								Drawable drawable=(Drawable) filed.get(o);
									
								drawable = new ColorDrawable(SkinManager.getInstance().getColor(attrValueRefId)); 
								filed.set(o,drawable);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					
					}
				}
		
		//------------------------
	}

	private void onTrackVerticalColor(ScrollView mScrollView) throws Exception{
		int colorBg = SkinManager.getInstance().getColor(attrValueRefId);	
		Drawable drawable;
		Field f = View.class.getDeclaredField("mScrollCache");
		f.setAccessible(true);
		Object scrollabilityCache  = f.get(mScrollView);
		f = f.getType().getDeclaredField("scrollBar");
		f.setAccessible(true); 
		Object scrollBarDrawable = f.get(scrollabilityCache);
		f = f.getType().getDeclaredField("mVerticalTrack"); //mVerticalThumb
		f.setAccessible(true); 
		 drawable = (Drawable) f.get(scrollBarDrawable); 
		 drawable = new ColorDrawable(colorBg);
		f.set(scrollBarDrawable, drawable);
	}

	private void onTrackVerticalDrawable(ScrollView mScrollView) {
		 if(SkinManager.getInstance().getDrawable(attrValueRefId) == null){
			 return;
		 }
		Drawable drawable;
		try {
		Field f = View.class.getDeclaredField("mScrollCache");
		f.setAccessible(true);
		Object scrollabilityCache  = f.get(mScrollView);
		f = f.getType().getDeclaredField("scrollBar");
		f.setAccessible(true); 
		Object scrollBarDrawable = f.get(scrollabilityCache);
		f = f.getType().getDeclaredField("mVerticalTrack"); //mVerticalThumb
		f.setAccessible(true); 
		 drawable = (Drawable) f.get(scrollBarDrawable); 
		 drawable =  SkinManager.getInstance().getDrawable(attrValueRefId);
			 f.set(scrollBarDrawable, drawable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
