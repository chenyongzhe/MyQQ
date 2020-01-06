package com.chen.a12763.myqq.fragment;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chen.a12763.myqq.R;
import com.chen.a12763.myqq.activity.ChatActivity;
import com.chen.a12763.myqq.activity.SearchFriendActivity;
import com.chen.a12763.myqq.adapter.FriendListAdapter;
import com.chen.a12763.myqq.bean.ApplicationData;
import com.chen.a12763.myqq.bean.User;
import com.chen.a12763.myqq.view.TitleBarView;

public class FriendListFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	private TitleBarView mTitleBarView;
	private ListView mFriendListView;
	private List<User> mFriendList;
	private Handler handler;
	private FriendListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		mBaseView = inflater.inflate(R.layout.fragment_friendlist, null);
		System.out.println("初始化friendListFragment");
		findView();
		init();
		return mBaseView;
	}

	private void findView() {
		mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);
		mFriendListView = (ListView)mBaseView.findViewById(R.id.friend_list_listview);
		
	}

	private void init() {
		mFriendList = ApplicationData.getInstance().getFriendList();
		adapter = new FriendListAdapter(mContext, mFriendList);
		mFriendListView.setAdapter(adapter);
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					mFriendListView.setSelection(mFriendList.size());
					break;
				default:
					break;
				}
			}
		};
		ApplicationData.getInstance().setfriendListHandler(handler);
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.VISIBLE);
		mTitleBarView.setTitleText("好友");
		mTitleBarView.setBtnLeft(R.string.control);
		mTitleBarView.setBtnRight(R.drawable.qq_constact);
		
		mFriendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				User friend = mFriendList.get(position);
				Intent intent = new Intent(mContext,ChatActivity.class);
				intent.putExtra("friendName", friend.getUserName());
				intent.putExtra("friendId", friend.getId());
				startActivity(intent);
			}
		});
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,SearchFriendActivity.class);
				startActivity(intent);
			}
		});
		
	}
}
