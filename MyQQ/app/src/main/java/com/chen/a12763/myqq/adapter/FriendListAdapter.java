package com.chen.a12763.myqq.adapter;

import java.util.List;



import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.a12763.myqq.R;
import com.chen.a12763.myqq.bean.ApplicationData;
import com.chen.a12763.myqq.bean.User;
import com.chen.a12763.myqq.view.CircleImageView;

public class FriendListAdapter extends BaseAdapter {
	private List<User> mFriendList;
	private LayoutInflater mInflater;

	public FriendListAdapter(Context context, List<User> vector) {
		this.mFriendList = vector;
		mInflater = LayoutInflater.from(context);
		System.out.println("初始化FriendAdapter");
	}
	@Override
	public View getView(int position, View convertView, ViewGroup root) {
		CircleImageView avatarView;
		TextView nameView;
		ImageView isOnline;
		TextView introView;
		User user = mFriendList.get(position);
		Bitmap photo = (ApplicationData.getInstance().getFriendPhotoMap()).get(user.getId());
		String name = user.getUserName();
		String briefIntro = user.getUserBriefIntro();
		convertView = mInflater.inflate(R.layout.friend_list_item,
				null);
		avatarView = (CircleImageView) convertView
				.findViewById(R.id.user_photo);
		nameView = (TextView) convertView
				.findViewById(R.id.friend_list_name);
		isOnline = (ImageView)convertView.findViewById(R.id.stateicon);
		
		introView = (TextView) convertView
				.findViewById(R.id.friend_list_brief);
		
		nameView.setText(name);
		
		if(!user.isOnline()) {
			isOnline.setVisibility(View.GONE);
			
		}
		if (photo != null) {
			Log.d("chen", "头像不为空，开始设置好友头像");
			avatarView.setImageBitmap(photo);
		}else {
			Log.d("chen", "头像为空，啊啊啊");

		}
		introView.setText(briefIntro);
	

		return convertView;
	}

	public int getCount() {
		return mFriendList.size();
	}

	public Object getItem(int position) {
		return mFriendList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

}
