package com.cliper.skin.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cliper.skin.entity.DynamicAttr;
import com.cliper.skin.listener.IDynamicNewView;

import java.util.List;

/**
 * 
 @author	Cliper.luoz
 *	如果你想要动态添加view 就继承 BaseFragment
 * 上午10:12:32
 */
public class BaseSkinFragment extends Fragment implements IDynamicNewView {
	
	private IDynamicNewView mIDynamicNewView;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			mIDynamicNewView = (IDynamicNewView)activity;
		}catch(ClassCastException e){
			mIDynamicNewView = null;
		}
	}

	@Override
	public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
		if(mIDynamicNewView == null){
			throw new RuntimeException("IDynamicNewView should be implements !");
		}else{
			mIDynamicNewView.dynamicAddView(view, pDAttrs);
		}
	}
	
}
