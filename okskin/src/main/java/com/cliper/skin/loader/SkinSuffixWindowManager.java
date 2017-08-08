package com.cliper.skin.loader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cliper.skin.listener.ISkinWindowManager;
import com.cliper.skin.util.L;

import java.util.ArrayList;
import java.util.List;
/**
 * 如果你的dialog，在activity或者fragment则用不到SkinSuffixWindowManager
 * 对独立的窗体的支持（activity之外,service的WM 等）
 * @author Cliper.luoz
 *
 * 上午11:37:24
 */
public class SkinSuffixWindowManager implements ISkinWindowManager {

	private Context mContext;
	/**
	 * application
	 */
	private	SkinManager mSkinManager;
	
	private ArrayList<View> mSkinViewList = new ArrayList<View>();
	
	private SkinSuffixWindowManager(){
		 mSkinManager = SkinManager.getInstance();
	}
	
	private View wmView;
	
	private  static class WindowHolder{
		private final static SkinSuffixWindowManager SKIN_WINDOW_MANAGER = new SkinSuffixWindowManager();
	} 
	
	public static SkinSuffixWindowManager getInstance(){
		return WindowHolder.SKIN_WINDOW_MANAGER;
	}
	
	/**
	 * 
	 * @param context
	 */
	public void init(Context context){
		this.mContext = context;
	}
	
	
	//
	@Override
	public ISkinWindowManager addWindowView(View view) {
		if (view == null) {
			L.e("View is null");
			return this;
		}
		mSkinViewList.add(view);
		
		return this;
	}

	@Override
	public ISkinWindowManager addWindowViewRef(int viewId , ViewGroup root) {
		 wmView = LayoutInflater.from(mContext).inflate(viewId, root, false);
		if (wmView == null) {
			L.e("View is null");
			return this;
		}
		addWindowView(wmView);
		return this;
	}
	
	@Override
	public ISkinWindowManager removeWindowView(View view) {
		if (view != null) {
			mSkinViewList.remove(view);			
		}
		mSkinManager.notifySkinUpdate();
		return this;
	}

	@Override
	public ISkinWindowManager clear() {
		if (!mSkinViewList.isEmpty()) {
				mSkinViewList.clear();
		}
		mSkinManager.notifySkinUpdate();
		return this;
	}

	/**
 	 *遍历所有的View root，childView为true的  
	 */
	@Override
	public View applySkinForViews(boolean applyChild) {
		for (View mView  : mSkinViewList) {
			mSkinManager.applyWMSkin(mView, applyChild);
		}
		mSkinManager.notifySkinUpdate();
	   return wmView;
	}

	@Override
	public List<View> getWindowViewList() {
		// TODO Auto-generated method stub
		return mSkinViewList;
	}


	
	
}
