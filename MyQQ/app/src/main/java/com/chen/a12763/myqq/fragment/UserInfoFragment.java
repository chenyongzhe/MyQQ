package com.chen.a12763.myqq.fragment;



import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.a12763.myqq.R;
import com.chen.a12763.myqq.activity.Modify;
import com.chen.a12763.myqq.activity.SearchFriendActivity;
import com.chen.a12763.myqq.bean.ApplicationData;
import com.chen.a12763.myqq.bean.User;
import com.chen.a12763.myqq.util.PhotoUtils;
import com.chen.a12763.myqq.view.CircleImageView;
import com.chen.a12763.myqq.view.TitleBarView;

public class UserInfoFragment extends Fragment{
	private Context mContext;
	private View mBaseView;
	private TitleBarView mTitleBarView;
	private TextView username;
	private TextView user_id;
	private ImageView sex;
	private TextView age;
	private TextView local;
	private TextView qianming;
	private CircleImageView user_photo;
	private Button modifybtn;
	public static UserInfoFragment userInfoFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		mBaseView = inflater.inflate(R.layout.fragment_userinfo, null);
		findView();
		init();
		return mBaseView;
	}
	private void findView(){
		mTitleBarView=(TitleBarView) mBaseView.findViewById(R.id.title_bar);
		username=(TextView)mBaseView.findViewById(R.id.info_user_username);
		user_id=(TextView)mBaseView.findViewById(R.id.info_user_id);
		sex=(ImageView) mBaseView.findViewById(R.id.info_user_sex);
        age=(TextView)mBaseView.findViewById(R.id.info_user_age);
		local=(TextView)mBaseView.findViewById(R.id.info_user_local);
		qianming=(TextView)mBaseView.findViewById(R.id.info_user_qianming);
		user_photo=(CircleImageView) mBaseView.findViewById(R.id.info_user_photo);
		modifybtn=(Button) mBaseView.findViewById(R.id.modify_button);
		modifybtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,Modify.class);
				startActivity(intent);
			}
		});
	}
	
	public void init(){
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
		mTitleBarView.setTitleText("个人信息");
		User user= ApplicationData.getInstance().getUserInfo();
		byte[] photo_byte=user.getPhoto();
		if(photo_byte !=null){
			Bitmap bitmap=PhotoUtils.getBitmap(photo_byte);
			user_photo.setImageBitmap(bitmap);
		}
		username.setText(user.getUserName());
		user_id.setText(user.getAccount());
		if(user.getGender()==0){
			sex.setBackgroundResource(R.drawable.gm8);
		}else {
			sex.setBackgroundResource(R.drawable.gm_);
		}
		local.setText(user.getLocation());
		age.setText(user.getAge()+"");
		userInfoFragment=this;
		//System.out.println("年龄"+ user.getAge());
	}






}

