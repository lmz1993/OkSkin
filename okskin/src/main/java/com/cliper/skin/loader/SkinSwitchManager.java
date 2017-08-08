package com.cliper.skin.loader;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.cliper.skin.listener.ILoaderListener;
import com.cliper.skin.listener.ISkinSwitchManager;
import com.cliper.skin.listener.ISwitchThemeListener;
import com.cliper.skin.util.L;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 
 * @author Cliper.luoz
 *
 * 下午3:48:56
 * 
 * 多种skin皮肤切换manager
 * 比如：1 星空皮肤
 *      2 ：德玛西亚皮肤
 *        3：海贼王皮肤
 *        对SkinManager简单的处理，并更方便的去切换N种皮肤
 */		
public class SkinSwitchManager implements ISkinSwitchManager {
	
	private ConcurrentHashMap<Integer, String> swtichMap = new ConcurrentHashMap<Integer, String>();
	
	private static int switchTag =  Integer.MAX_VALUE ;
	
	private SkinSwitchManager (){}
	
	private Interpolator mInterpolator ;
	
	private static class SkinSwitchHolder{
		private static final SkinSwitchManager switchManager = new SkinSwitchManager();
	}
	public static SkinSwitchManager getSkinSwitchManager(){
		return SkinSwitchHolder.switchManager;
	}
	
	public SkinManager getSkinManager(){
		return SkinManager.getInstance();
	}
	
	@Override
	public void switchSkinTheme(int keySkin) {
		
		String pathSkin = swtichMap.get(keySkin);
		if (TextUtils.isEmpty(pathSkin)  ) {
			L.w("pathSkin is empty");
			return;
		}
		File file = new File(pathSkin);
		if (file == null || !file.exists()) {
			L.w("SkinLoader", "skin file path is error  , check your path skin");
			return;
		}
		
		if (switchTag == keySkin) {
			return;
		}
		switchTag = keySkin;
		SkinManager.getInstance().loadPath(pathSkin);
	}
	
	

	@Override
	public void switchSkinTheme(int keySkin, final ISwitchThemeListener themeListener) {
		
		String pathSkin = swtichMap.get(keySkin);
		if (TextUtils.isEmpty(pathSkin)) {
			themeListener.onSwitchError("皮肤路径不存在");
			return;
		}
		File file = new File(pathSkin);
		if (file == null || !file.exists()) {
			L.w("SkinLoader", "file path is error");
			themeListener.onSwitchError("请检查skin文件是否存在");
			return;
		}
		
		if (switchTag == keySkin) {
			return;
		}
		switchTag = keySkin;
		
		SkinManager.getInstance().load(pathSkin, new ILoaderListener() {
			
			@Override
			public void onSuccess() {
				themeListener.onSwitchSuccess();
			}
			
			@Override
			public void onStart() {
				themeListener.onSwitchStart();
			}
			
			@Override
			public void onFailed() {
				themeListener.onSwitchError("皮肤应用失败");
			}
		});
	}
	
	
	@Override
	public ISkinSwitchManager saveSkinTheme(int keySkin, String skinPath) {
		return getSkinSwitchManager();
	}


	
	@Override
	public ISkinSwitchManager removeSkinTheme(int keySkin) {
		swtichMap.remove(keySkin);
		return getSkinSwitchManager();
	}

	
	
	@Override
	public void restoreDefaultTheme() {
		switchTag = Integer.MAX_VALUE;
		getSkinManager().restoreDefaultTheme();
	}

	
	
	@Override
	public void setSkinInterpolator(View view) {
		setSkinInterpolator(view , null);
	}
	
	//TODO 未实现
	@SuppressLint("NewApi") @Override
	public void setSkinInterpolator(final View view, final Interpolator mInterpolator) {
		if (mInterpolator == null) {
			this.mInterpolator = new LinearInterpolator();
		}
		this.mInterpolator = mInterpolator ;
		
	}

	
	
}
