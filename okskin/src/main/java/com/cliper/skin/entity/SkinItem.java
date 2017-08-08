package com.cliper.skin.entity;

import android.view.View;

import com.cliper.skin.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *  @author	Cliper.luoz
 * 上午10:34:02
 */
public class SkinItem {
	
	public View view;
	
	public List<SkinAttrHolder> attrs;
	
	public SkinItem(){
		attrs = new ArrayList<SkinAttrHolder>();
	}
	
	public void apply(){
		if(ListUtils.isEmpty(attrs)){
			return;
		}
		for(SkinAttrHolder at : attrs){
			at.apply(view);
		}
	}
	
	public void clean(){
		if(ListUtils.isEmpty(attrs)){
			return;
		}
		for(SkinAttrHolder at : attrs){
			at = null;
		}
	}

	@Override
	public String toString() {
		return "SkinItem [view=" + view.getClass().getSimpleName() + ", attrs=" + attrs + "]";
	}
}
