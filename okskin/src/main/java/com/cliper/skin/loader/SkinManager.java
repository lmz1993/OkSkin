package com.cliper.skin.loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.cliper.skin.config.SkinConfig;
import com.cliper.skin.entity.AttrFactory;
import com.cliper.skin.entity.ResourceParseEn;
import com.cliper.skin.entity.SkinAttrHolder;
import com.cliper.skin.entity.SkinItem;
import com.cliper.skin.listener.ILoaderListener;
import com.cliper.skin.listener.IResourceLoader;
import com.cliper.skin.listener.ISkinLoader;
import com.cliper.skin.listener.ISkinUpdate;
import com.cliper.skin.util.L;
import com.cliper.skin.util.ListUtils;
import com.cliper.skin.util.ZHSkinSdk;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 统一skin管理类
 * 
 * @author ZH-SW-luoz Cliper.luoz 负责load skin （外部资源APK）
 * 
 * 
 *         下午5:29:26
 */
@SuppressLint("NewApi")
public class SkinManager implements ISkinLoader {

	private static final String NOT_INIT_ERROR = "SkinManager must init  Context first in application";
	private static Object synchronizedLock = new Object();
	private static SkinManager instance;

	private List<SkinItem> mSkinItems = new ArrayList<SkinItem>();

	private List<ISkinUpdate> skinObservers;
	private Context context;
	private String skinPackageName;
	private Resources mResources;
	private String skinPath;
	private boolean isDefaultSkin = false;
	private List<SkinAttrHolder> skinAttrs = new ArrayList<SkinAttrHolder>();
	private IResourceLoader skinResourceParser;

	/**
	 * whether the skin being used is from external .skin file
	 * 
	 * @return is external skin = true
	 */
	public boolean isExternalSkin() {
		return !isDefaultSkin && mResources != null;
	}

	/**
	 * 得到当前皮肤的路径
	 * 
	 * @return current skin path
	 */
	public String getSkinPath() {
		return skinPath;
	}

	/**
	 * @return
	 */
	public static SkinManager getInstance() {
		if (instance == null) {
			synchronized (synchronizedLock) {
				if (instance == null) {
					instance = new SkinManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 默认关闭
	 * 
	 * @param isDebug
	 */
	public void isOpenLog(boolean isDebug) {
		L.DEBUG = isDebug;
	}

	/**
	 * if you need setting(SP) send brodCast 一般用不到，除非多个应用换肤（读取skin tag ）
	 * 
	 * @return
	 */
	public SkinManager initSDK(Context mContext) {
		context = mContext.getApplicationContext();
		ZHSkinSdk.initSDK(mContext);

		return instance;
	}

	/**
	 * (一个应用换肤) default init
	 * 
	 * @param ctx
	 */
	public SkinManager init(Context ctx) {
		context = ctx.getApplicationContext();
		skinResourceParser = new SkinResourceLoader(context);
		return instance;
	}

	public String getSkinPackageName() {
		return skinPackageName;
	}

	public Resources getResources() {
		return mResources;
	}

	private SkinManager() {
	}

	public void restoreDefaultTheme() {
		SkinConfig.saveSkinPath(context, SkinConfig.DEFALT_SKIN);
		isDefaultSkin = true;
		mResources = context.getResources();
		skinResourceParser.setResources(mResources);
		notifySkinUpdate();
	}

	/**
	 * 无回调 如果你之前设置过 skin path 直接调用load ，否者得到的是默认skin 建议在application 初始化就可以了
	 */
	public void load() {
		loadCallBack(null);
	}

	/**
	 * 
	 * 无回调方式 直接load path
	 * 
	 * @param skinPath
	 */
	public void loadPath(String skinPath) {
		load(skinPath, null);
	}

	/**
	 * 如果你之前设置过 skin path 直接调用load 建议在application 初始化就可以了 得到一个结果的回调
	 */
	public void loadCallBack(ILoaderListener callback) {
		/**
		 * 直 接从上次load 里面获取路径
		 */
		String skin = SkinConfig.getCustomSkinPath(context);
		if (SkinConfig.isDefaultSkin(context)) {
			return;
		}
		load(skin, callback);
	}

	/**
	 * 
	 * 温馨提示：.3 之定义View attrName最好不要命名一样， 如果命名一样建议使用同一个 skinAttrHolder
	 * (需要在apply() 添加相关逻辑) if(view instance of XXX){...} 。可以参考
	 * skinAttrHolder的实现类 或者继承之前的attrHolder Override apply方法 attrName =
	 * (circleColor) app:circleColor = "@color/my_text_color" (circleColor)
	 * 在setContentView 之前注入属性 ，并且在skinInflatorFactory之前调用
	 * 
	 * @param attrName
	 * @param skin
	 */
	public SkinManager registAttrHolder(String attrName, SkinAttrHolder skin) {
		AttrFactory.registAttrHolder(attrName, skin);
		return instance;
	}

	/**
	 * 移除对某个属性的支持
	 * 
	 * @param attrName
	 * @param skin
	 */
	public void removeAttrHolder(String attrName, SkinAttrHolder skin) {
		AttrFactory.removeAttrHolder(attrName);
		notifySkinUpdate();
	}

	/**
	 * 注入一个attrs 一定要在setContentView之前添加 attrName
	 * 建议不要重复命名，不要跟Android自带attribute一样 如果一样可以继承之前的attrHolder Override apply方法
	 * super（）不可注释掉
	 * 
	 */
	public void registAttrHolderMap(Map<String, SkinAttrHolder> attrMap) {
		AttrFactory.registAttrHolderMap(attrMap);

	}

	/**
	 * 在需要设置换肤的地方load skin
	 * 
	 * @param skinPackagePath
	 * @param callback
	 */
	@SuppressLint("NewApi")
	public void load(String skinPackagePath, final ILoaderListener callback) {
		new AsyncTask<String, Void, ResourceParseEn>() {

			protected void onPreExecute() {
				if (callback != null) {
					callback.onStart();
				}
			};

			@Override
			protected ResourceParseEn doInBackground(String... params) {
				try {
					if (context == null) {
						L.w("SkinLoader", NOT_INIT_ERROR);
						return null;
					}

					if (params.length == 1) {
						String skinPkgPath = params[0];

						File file = new File(skinPkgPath);
						if (file == null || !file.exists()) {
							L.w("SkinLoader", "file path is error");
							return null;
						}

						PackageManager mPm = context.getPackageManager();
						PackageInfo mInfo = mPm.getPackageArchiveInfo(
								skinPkgPath, PackageManager.GET_ACTIVITIES);
						skinPackageName = mInfo.packageName;

						AssetManager assetManager = AssetManager.class
								.newInstance();
						Method addAssetPath = assetManager.getClass()
								.getMethod("addAssetPath", String.class);
						addAssetPath.invoke(assetManager, skinPkgPath);

						Resources superRes = context.getResources();
						Resources skinResource = new Resources(assetManager,
								superRes.getDisplayMetrics(),
								superRes.getConfiguration());

						/**
						 * 保存skin path换肤的资源 (XXX.skin)
						 */
						SkinConfig.saveSkinPath(context, skinPkgPath);

						skinPath = skinPkgPath;
						isDefaultSkin = false;

						return new ResourceParseEn(skinResource, context,
								skinPackageName, isDefaultSkin);
					}
					return null;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			};

			@SuppressWarnings("unused")
			protected void onPostExecute(ResourceParseEn result) {
				if (result != null) {
					mResources = result.getmResources();
				 skinResourceParser =	new SkinResourceLoader(result);
					if (callback != null)
						callback.onSuccess();
						notifySkinUpdate();
				} else {
					isDefaultSkin = true;
					if (callback != null)
						callback.onFailed();
				}
			};

		}.execute(skinPackagePath);
	}

	@Override
	public void attach(ISkinUpdate observer) {
		if (skinObservers == null) {
			skinObservers = new ArrayList<ISkinUpdate>();
		}
		if (!skinObservers.contains(skinObservers)) {
			skinObservers.add(observer);
		}
	}

	@Override
	public void detach(ISkinUpdate observer) {
		if (skinObservers == null)
			return;
		if (skinObservers.contains(observer)) {
			skinObservers.remove(observer);
		}
//		if (skinAttrs.size() >= 0) {
//			skinAttrs.clear();
//			skinAttrs = null;
//		}
//		if (skinResourceParser != null) {
//			skinResourceParser = null;
//		}
//		if(mSkinItems.size() >= 0){
//			mSkinItems.clear();
//			mSkinItems = null;
//		}

	}

	@Override
	public void notifySkinUpdate() {
		if (skinObservers == null)
			return;
		for (ISkinUpdate observer : skinObservers) {
			observer.onThemeUpdate();
		}
	}

	// --------------------------------getResources-----------------------------------

	/**
	 * 
	 * @param resId
	 * @return
	 */
	public int getColor(int resId) {
		return skinResourceParser.getColor(resId);
	}

	@SuppressLint("NewApi")
	public Drawable getDrawable(int resId) {
		if (isCheckInitSkin())
			return skinResourceParser.getDrawable(resId);
		else
			throw new NullPointerException("you must be application init skin");
	}

	/**
	 * 
	 * @param resId
	 * @return
	 */
	@SuppressLint("NewApi")
	public String getString(int resId) {
		if (isCheckInitSkin())
			return skinResourceParser.getString(resId);
		else
			throw new NullPointerException("you must be application init skin");
	}

	/**
	 * Color 文件 加载指定资源颜色drawable,转化为ColorStateList，保证selector类型的Color也能被转换。
	 * 无皮肤包资源返回默认主题颜色
	 * 
	 * @param resId
	 * @return
	 * 
	 */
	public ColorStateList convertToColorStateList(int resId) {
		if (isCheckInitSkin())
			return skinResourceParser.convertToColorStateList(resId);
		throw new NullPointerException("you must be application init skin");
	}

	/**
	 * @drwable/state_list_drawable state_list_drawable.xml -- @drawable/icon
	 *                              state_pressed = true -- @drawable/icon2
	 * @param resId
	 * @return
	 */
	// TODO 对select drawable的支持
	@SuppressLint("NewApi")
	public StateListDrawable convertToStateListDrawable(int resId) {
		if (isCheckInitSkin())
			return skinResourceParser.convertToStateListDrawable(resId);
		throw new NullPointerException("you must be application init skin");
	}

	public Bitmap getBitmap(int resId){
		return skinResourceParser.getBitmap(resId);
	}
	/**
	 * 
	 * @param resId
	 * @return
	 */
	public Drawable getMipmap(int resId) {
		if (isCheckInitSkin())
			return skinResourceParser.getMipmap(resId);
		throw new NullPointerException("you must be application init skin");
	}

	// ----------------------------wm---------------------------
	/**
	 * first load skin
	 */
	@Override
	public void applyWMSkin(View view, boolean applyChild) {
		// TODO Auto-generated method stub
		if (null == view) {
			return;
		}

		if (!isExternalSkin()) {
			return;
		}

		// 递归遍历所有的子View，
		if (applyChild) {

			// view: tag id (view.getTag() ) , tag
			// value(skin:color_red:textColor:color)

			if (view.getTag() instanceof String && view.getTag() != null) {
				parseTag(view, (String) view.getTag());
			}

			if (view instanceof ViewGroup) {
				ViewGroup container = (ViewGroup) view;

				for (int i = 0, n = container.getChildCount(); i < n; i++) {
					View child = container.getChildAt(i);
					applyWMSkin(child, applyChild);
				}
			}
		}

	}

	// skin: left_menu_icon: src: drawable|skin:color_red:textColor:color
	// String attrName, int attrValueRefId, String attrValueRefName, String
	// typeName
	/**
	 * 不基于SkinInflaterFactory attrName:background attrValue:@2131099648
	 * (R.drawable.xxx) entryName:color_app_bg typeName:color
	 * 
	 * @param tagStr
	 * @return
	 */
	private void parseTag(View v, String tagStr) {

		if (TextUtils.isEmpty(tagStr))
			return;
		String[] items = tagStr.split("[|]");
		for (String item : items) {
			if (!item.startsWith(SkinConfig.SKIN_PREFIX))
				continue;
			String[] resItems = item.split(":");
			if (resItems.length != 4)
				continue;
			// skin: left_menu_icon: src:
			// drawable|skin:color_red:textColor:color
			String entryName = resItems[1];
			String attrName = resItems[2];
			String typeName = resItems[3];
			// TODO 需要处理成项目的refId
			try {
				int attrValueRefId = trueRefId(entryName, typeName);
				if (attrValueRefId != -1) {
					if (AttrFactory.isSupportedAttr(attrName)) {
						// attrValueRefId 一定是需要的自己的id
						SkinAttrHolder skinHolder = AttrFactory.get(attrName,
								attrValueRefId, entryName, typeName);
						skinAttrs.add(skinHolder);
					}

					if (!ListUtils.isEmpty(skinAttrs)) {
						SkinItem skinItem = new SkinItem();
						skinItem.view = v;
						skinItem.attrs = skinAttrs;
						mSkinItems.add(skinItem);
						if (isExternalSkin()) {
							skinItem.apply();
						}
					}
				} else {
					L.w(" skin only  support(color / drawable / mipmap)");
				}
			} catch (Exception e) {
				e.getStackTrace();
				L.w(" skin tag config error , not found entryName=" + entryName
						+ " AND " + "typeName" + typeName);
			}
		}

	}

	private int trueRefId(String resName, String typeName) {
		if ("color".equals(typeName)) {
			return context.getResources().getIdentifier(resName, "color",
					context.getPackageName());
		} else if ("drawable".equals(typeName)) {
			return context.getResources().getIdentifier(resName, "drawable",
					context.getPackageName());
		} else if ("mipmap".equals(typeName)) {
			return context.getResources().getIdentifier(resName, "mipmap",
					context.getPackageName());
		}
		return -1;
	}

	public boolean isCheckInitSkin(){
		return context != null? true : false ;
	}
}