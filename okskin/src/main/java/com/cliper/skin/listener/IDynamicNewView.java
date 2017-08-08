package com.cliper.skin.listener;

import android.view.View;

import com.cliper.skin.entity.DynamicAttr;

import java.util.List;
/**
 * 
 * @author Cliper.luoz
 *
 * 下午4:58:29
 */
public interface IDynamicNewView {
	void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
