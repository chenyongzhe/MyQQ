package com.chen.a12763.myqq.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.a12763.myqq.R;
import com.chen.a12763.myqq.view.TitleBarView;

public class NearByFragment extends Fragment{
	private Context mContext;
	private View mBaseView;
	private TitleBarView mTitleBarView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		mBaseView = inflater.inflate(R.layout.fragment_nearby, null);
		findView();
		init();
		return mBaseView;
	}
	private void findView(){
		mTitleBarView=(TitleBarView) mBaseView.findViewById(R.id.title_bar);
	}
	
	private void init(){
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
		mTitleBarView.setTitleText("附近的人");
	}
}

