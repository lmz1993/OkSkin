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
 * 
 *         上午10:33:48
 */
public class ScrollbarThumbVerticalAttr extends SkinAttrHolder {

	@Override
	public void apply(View view) {
		// TODO Auto-generated method stub

		if (view instanceof ScrollView) {
			ScrollView mScrollView = (ScrollView) view;
			if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
				onSVThumbVerticalDrawable(mScrollView);
			} else if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
				try {
					onSVThumbVerticalColor(mScrollView);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// TODO 5.0.1源码 （ListView反射异常BUG）
		if (view instanceof ListView) {
			ListView mListView = (ListView) view;

			if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
			} else if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
				/***
				 * private Drawable mThumbDrawable; private Drawable
				 * mOverlayDrawable; private Drawable mTrackDrawable;
				 */
				try {
					Field f = AbsListView.class.getDeclaredField("mFastScroll");// mFastScroller
																				// 5.1.1
					f.setAccessible(true);
					Object o = f.get(mListView);
					f = f.getType().getDeclaredField("mThumbDrawable"); // mTrackDrawable
					f.setAccessible(true);
					Drawable drawable = (Drawable) f.get(o);
					drawable = new ColorDrawable(SkinManager.getInstance()
							.getColor(attrValueRefId));
					f.set(o, drawable);
				} catch (Exception e) {
					e.printStackTrace();
				}

				//
			}

		}

	}

	/**
	 * 
	 * @param mScrollView
	 */
	private void onSVThumbVerticalColor(ScrollView mScrollView)
			throws Exception {
		// TODO Auto-generated method stub
		int colorBg = SkinManager.getInstance().getColor(attrValueRefId);
		Drawable drawable;
		Field f = View.class.getDeclaredField("mScrollCache");// ScrollabilityCache
		f.setAccessible(true);
		Object scrollabilityCache = f.get(mScrollView); //
		f = f.getType().getDeclaredField("scrollBar");
		f.setAccessible(true);
		Object scrollBarDrawable = f.get(scrollabilityCache);
		f = f.getType().getDeclaredField("mVerticalThumb");
		f.setAccessible(true);
		drawable = (Drawable) f.get(scrollBarDrawable);
		drawable = new ColorDrawable(colorBg);
		f.set(scrollBarDrawable, drawable);
	}

	/**
	 * 
	 * @param mScrollView
	 */
	private void onSVThumbVerticalDrawable(ScrollView mScrollView) {
		// TODO Auto-generated method stub
		Drawable drawable;
		try {
			Field f = View.class.getDeclaredField("mScrollCache");
			f.setAccessible(true);
			Object scrollabilityCache = f.get(mScrollView);
			f = f.getType().getDeclaredField("scrollBar");
			f.setAccessible(true);
			Object scrollBarDrawable = f.get(scrollabilityCache);
			f = f.getType().getDeclaredField("mVerticalThumb"); // mVerticalThumb
			f.setAccessible(true);
			drawable = (Drawable) f.get(scrollBarDrawable);
			drawable = SkinManager.getInstance().getDrawable(attrValueRefId);
			f.set(scrollBarDrawable, drawable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
