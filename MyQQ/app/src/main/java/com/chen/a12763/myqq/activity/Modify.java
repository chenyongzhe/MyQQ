package com.chen.a12763.myqq.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.a12763.myqq.R;
import com.chen.a12763.myqq.action.UserAction;
import com.chen.a12763.myqq.activity.register.RegisterActivity;
import com.chen.a12763.myqq.bean.ApplicationData;
import com.chen.a12763.myqq.bean.TranObject;
import com.chen.a12763.myqq.bean.User;
import com.chen.a12763.myqq.fragment.UserInfoFragment;
import com.chen.a12763.myqq.network.NetConnect;
import com.chen.a12763.myqq.util.PhotoUtils;
import com.chen.a12763.myqq.view.CircleImageView;
import com.chen.a12763.myqq.view.TitleBarView;

import java.io.IOException;
import java.util.ArrayList;

public class Modify extends AppCompatActivity {

    private Context mContext;
    private View mBaseView;
    private TitleBarView mTitleBarView;
    private EditText username;
    private TextView user_id;
    private EditText sex;
    private TextView age;
    private EditText local;
    private EditText qianming;
    private CircleImageView user_photo;
    private Button modify_sent_btn;
    private static  boolean mIsReceived=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        mTitleBarView=(TitleBarView) findViewById(R.id.modify_bar);
        username=(EditText)findViewById(R.id.modify_user_username);
        user_id=(TextView)findViewById(R.id.modify_user_id);
        sex=(EditText) findViewById(R.id.modify_user_sex);
        age=(TextView)findViewById(R.id.modify_user_age);
        local=(EditText)findViewById(R.id.modify_user_local);
        qianming=(EditText)findViewById(R.id.modify_user_qianming);
        user_photo=(CircleImageView) findViewById(R.id.modify_user_photo);
        modify_sent_btn=(Button) findViewById(R.id.modify_sent_button);
        init();
        modify_sent_btn.setOnClickListener(modifyListener);


    }

    public void init(){
        mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
        mTitleBarView.setTitleText("个人信息");
        User user= ApplicationData.getInstance().getUserInfo();
        byte[] photo_byte=user.getPhoto();
        if(photo_byte !=null){
            Bitmap bitmap= PhotoUtils.getBitmap(photo_byte);
            user_photo.setImageBitmap(bitmap);
        }
        username.setText(user.getUserName());
        user_id.setText(user.getAccount());
        if(user.getGender()==0){
            sex.setText("女");
        }else {
            sex.setText("男");
        }
        local.setText(user.getLocation());
        age.setText(user.getAge()+"");
        //System.out.println("年龄"+ user.getAge());


    }


    public void submit_info(User user) throws IOException {

        UserAction.updateinfo(user);
        while (!mIsReceived) {
            System.out.println("d等待更新结果");
        }
        User myuser= ApplicationData.getInstance().getUserInfo();
        myuser.setUserName(user.getUserName());
        myuser.setGender(user.getGender());
        myuser.setLocation(user.getLocation());
        myuser.setUserBriefIntro(user.getUserBriefIntro());

        Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();

        UserInfoFragment.userInfoFragment.init();

    }

    public static void messageArrived(){
        mIsReceived = true;

    }


    private View.OnClickListener modifyListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            User user=new User();
            user.setAccount(user_id.getText().toString());
            user.setUserName(username.getText().toString());
            user.setLocation(local.getText().toString());
            if(sex.getText().toString().equals("男")){
                user.setGender(1);
            }else {
                user.setGender(0);
            }
           user.setUserBriefIntro(qianming.getText().toString());
            try {
                submit_info(user);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };



}

