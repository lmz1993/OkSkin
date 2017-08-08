package com.cliper.skin.listener;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;
/**
 * 
 * @author Cliper.luoz
 *
 * 上午11:34:11
 */
public interface ISkinWindowManager {
	 /**
     * 在框架内增加View的引用，刷新皮肤时会刷新此View及其所有子元素；
     */
	ISkinWindowManager addWindowView(View view);

	
	
	/**
     * 你也可以add  View id ,viewGroup can null
     */
	ISkinWindowManager addWindowViewRef(int viewId, ViewGroup root);
	
    /***
     * 从框架内移除View的引用；
     */
	ISkinWindowManager removeWindowView(View view);

    /***
     * 清空框架内持有的所有View的引用；
     */
	ISkinWindowManager clear();

    /***
     * 对框架内持有的所有View刷新皮肤；
     */
    View applySkinForViews(boolean applyChild);

    /***
     * 获取注册到框架内维护的所有View
     * @return
     */
    List<View> getWindowViewList();
}
