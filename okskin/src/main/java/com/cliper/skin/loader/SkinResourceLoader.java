package com.cliper.skin.loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import com.cliper.skin.entity.ResourceParseEn;
import com.cliper.skin.listener.IResourceLoader;
import com.cliper.skin.util.L;


/**
 * 
 * @author Cliper.luoz
 *
 * 下午2:28:56
 */
@SuppressLint("NewApi") public class SkinResourceLoader implements IResourceLoader {
	
	
	private Resources mResources;
	
	private Context context ;
	
	private boolean isDefaultSkin ;
	
	private String skinPackageName ;
	
	public SkinResourceLoader(ResourceParseEn result) {
		// TODO Auto-generated constructor stub
		if (result != null) {
			this.mResources = result.getmResources() ;
			this.context = result.getmContext();
			this.isDefaultSkin = result.isDefaultSkin();
			this.skinPackageName = result.getPackageName() ;
		}else{
			L.e(" ResourceParseEn is null ,  message :load skin error  in SkinResourceParser ");
		}
		
	}

	public SkinResourceLoader(Context context) {
		this.context = context;
	}

	@Override
	public int getColor(int resId) {
		// TODO Auto-generated method stub
		int originColor = context.getResources().getColor(resId);
		if(mResources == null || isDefaultSkin){
			return originColor;
		}
		String resName = context.getResources().getResourceEntryName(resId);
		
		int trueResId = mResources.getIdentifier(resName, "color", skinPackageName);

		int trueColor = 0;
		try{
			trueColor = mResources.getColor(trueResId);
		}catch(NotFoundException e){
			e.printStackTrace();
			trueColor = originColor;
		}
		
		return trueColor;
	}

	@Override
	public ColorStateList convertToColorStateList(int resId) {

		boolean isExtendSkin = true;
		
		if (mResources == null || isDefaultSkin) {
			isExtendSkin = false;
		}
		
		String resName = context.getResources().getResourceEntryName(resId);
		if (isExtendSkin) {
			
			
			int trueResId = mResources.getIdentifier(resName, "color", skinPackageName);
			
			 L.w("SkinTest", "convertToColorStateList&trueResId:"+trueResId);  
			
			ColorStateList trueColorList = null;
			if (trueResId == 0) { 
				try {
					ColorStateList originColorList = context.getResources().getColorStateList(resId);
					return originColorList;
				} catch (NotFoundException e) {
					e.printStackTrace();
					L.e("resName = " + resName + " NotFoundException : "+ e.getMessage());
				}
			} else {
				try {
					trueColorList = mResources.getColorStateList(trueResId);
					L.e("trueColorList = " + trueColorList);
					return trueColorList;
				} catch (NotFoundException e) {
					e.printStackTrace();
					L.w("resName = " + resName + " NotFoundException :" + e.getMessage());
				}
			}
		} else {
			try {
				ColorStateList originColorList = context.getResources().getColorStateList(resId);
				return originColorList;
			} catch (NotFoundException e) {
				e.printStackTrace();
				L.w("resName = " + resName + " NotFoundException :" + e.getMessage());
			}

		}

		int[][] states = new int[1][1];
		return new ColorStateList(states, new int[] { context.getResources().getColor(resId) });
	}

	@Override
	public Drawable getDrawable(int resId) {
		Drawable originDrawable = context.getResources().getDrawable(resId);
		if(mResources == null || isDefaultSkin){
			return originDrawable;
		}
		String resName = context.getResources().getResourceEntryName(resId);
		
		int trueResId = mResources.getIdentifier(resName, "drawable", skinPackageName);
		
		Drawable trueDrawable = null;
		try{
			if(android.os.Build.VERSION.SDK_INT < 22){
				trueDrawable = mResources.getDrawable(trueResId);
			}else{
				trueDrawable = mResources.getDrawable(trueResId, null);
			}
		}catch(NotFoundException e){
			e.printStackTrace();
			trueDrawable = originDrawable;
		}
		
		return trueDrawable;
	}

	@Override
	public StateListDrawable convertToStateListDrawable(int resId) {
		StateListDrawable mStateListDrawable = null ;
		
		
		StateListDrawable originDrawable = (StateListDrawable) context.getResources().getDrawable(resId);
		if(mResources == null || isDefaultSkin){
			return originDrawable;
		}
		String resName = context.getResources().getResourceEntryName(resId);
		
		int trueResId = mResources.getIdentifier(resName, "drawable", skinPackageName);
		
		
		StateListDrawable trueDrawable = null;
		try{
			if(android.os.Build.VERSION.SDK_INT < 22){
				trueDrawable = (StateListDrawable) mResources.getDrawable(trueResId);
			}else{
				trueDrawable = (StateListDrawable) mResources.getDrawable(trueResId, null);
			}
		}catch(NotFoundException e){
			e.printStackTrace();
			trueDrawable = originDrawable;
		}
		
		
		return trueDrawable;
	}

	@Override
	public Drawable getMipmap(int resId) {
		  Drawable originDrawable = context.getResources().getDrawable(resId);
			if(mResources == null || isDefaultSkin){
				return originDrawable;
			}
			String resName = context.getResources().getResourceEntryName(resId);
			
			int trueResId = mResources.getIdentifier(resName, "mipmap", skinPackageName);
			
			Drawable trueDrawable = null;
			try{
				if(android.os.Build.VERSION.SDK_INT < 22){
					trueDrawable = mResources.getDrawable(trueResId);
				}else{
					trueDrawable = mResources.getDrawable(trueResId, null);
				}
			}catch(NotFoundException e){
				e.printStackTrace();
				trueDrawable = originDrawable;
			}
			
			return trueDrawable;
	}

	@Override
	public String getString(int resId) {
		String originString = context.getResources().getString(resId);
		if(mResources == null || isDefaultSkin){
			return originString;
		}
		String resName = context.getResources().getResourceEntryName(resId);
		
		int trueResId = mResources.getIdentifier(resName, "string", skinPackageName);
		
		String trueString = null;
		try{
			 trueString = mResources.getString(trueResId);
		}catch(NotFoundException e){
			e.printStackTrace();
			trueString = originString ;
		}
		
		return trueString;
	}

	@Override
	public Bitmap getBitmap(int resId) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable(resId);
		return  bitmapDrawable.getBitmap();
	}


	@Override
	public void setResources(Resources mResources) {
		// TODO Auto-generated method stub
		this.mResources = mResources;
	}

	@Override
	public Resources getResources() {
		return this.mResources;
	}


}
