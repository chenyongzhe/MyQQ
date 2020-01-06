package com.chen.a12763.myqq.activity;

import java.io.IOException;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.chen.a12763.myqq.BaseActivity;
import com.chen.a12763.myqq.R;
import com.chen.a12763.myqq.action.UserAction;
import com.chen.a12763.myqq.activity.register.RegisterActivity;
import com.chen.a12763.myqq.bean.ApplicationData;
import com.chen.a12763.myqq.bean.User;
import com.chen.a12763.myqq.global.Result;
import com.chen.a12763.myqq.network.NetConnect;
import com.chen.a12763.myqq.network.NetService;
import com.chen.a12763.myqq.util.VerifyUtils;

public class LoginActivity extends BaseActivity {

	private Context mContext;
	private RelativeLayout rl_user;
	private Button mLoginButton;
	private Button mRegisterButton;
	private EditText mAccount;
	private EditText mPassword;;
	private EditText server_ip;
	private NetService mNetService = NetService.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = this;
		initViews();
		initEvents();
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		rl_user = (RelativeLayout) findViewById(R.id.rl_user);
		mLoginButton = (Button) findViewById(R.id.login);
		mRegisterButton = (Button) findViewById(R.id.register);
		mAccount = (EditText) findViewById(R.id.account);
		mPassword = (EditText) findViewById(R.id.password);
		server_ip = (EditText) findViewById(R.id.server_ip);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		Animation anim = AnimationUtils.loadAnimation(mContext,
				R.anim.login_anim);
		anim.setFillAfter(true);
		rl_user.startAnimation(anim);

		mLoginButton.setOnClickListener(loginOnClickListener);
		mRegisterButton.setOnClickListener(registerOnClickListener);

	}

	private OnClickListener loginOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String account = mAccount.getText().toString().trim();
			String password = mPassword.getText().toString().trim();
			String server_address = server_ip.getText().toString().trim();
			if (account.equals("")) {
				showCustomToast("请填写账号");
				mAccount.requestFocus();
			}
			else if (server_address.equals("")) {
				showCustomToast("请填写服务器地址");
			}
			else if (password.equals("")) {
				showCustomToast("请填写密码");
			} else if (!VerifyUtils.matchAccount(account)) {
				showCustomToast("账号格式错误");
				mAccount.requestFocus();
			} else if (mPassword.length() < 6) {
				showCustomToast("密码格式错误");
			} else {
				Log.d("地址",server_address);
				NetConnect.SERVER_IP=server_address;
				tryLogin(account, password);
			}
		}
	};

	private void tryLogin(final String account, final String password) {
		new AsyncTask<Void, Void, Integer>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("正在登录,请稍后...");
			}

			@Override
			protected Integer doInBackground(Void... params) {
				try {
					
					mNetService.closeConnection();
					mNetService.onInit(LoginActivity.this);
					mNetService.setupConnection();
					Log.d("network", "set up connection");
					if (!mNetService.isConnected()) {
						return 0;
					}
					
					User user = new User();
					user.setAccount(account);
					user.setPassword(password);
					Log.d("chen",account);
					Log.d("chen",password);
					ApplicationData data = ApplicationData.getInstance();

					data.initData(LoginActivity.this);

					UserAction.loginVerify(user);
					Log.d("chen",account);
					Log.d("chen",password);
					data.start();


					System.out.println(data.getReceivedMessage().getResult()+"  前面是获得的结果");
					if (data.getReceivedMessage().getResult() == Result.LOGIN_SUCCESS)
						return 1;// 登录成功
					else if(data.getReceivedMessage().getResult() == Result.LOGIN_FAILED)
						return 2;// 登录失败
				} catch (IOException e) {
					Log.d("network", "IO异常");
				}
				return 0;

			}

			@Override
			protected void onPostExecute(Integer result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				if (result == 0) {
					showCustomToast("服务器异常");
				} else {
					if (result == 2) {
						showCustomToast("登录失败");
					} else if (result == 1) {
						System.out.println("开始跳转到mainactivity");
						Intent intent = new Intent(mContext, MainActivity.class);
						startActivity(intent);
						finish();
					}
				}
			}
		}.execute();

	}

	private OnClickListener registerOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String server_address = server_ip.getText().toString().trim();
			if (server_address.equals("")) {
				showCustomToast("请填写服务器地址");
			}else {
				NetConnect.SERVER_IP=server_address;
				Intent intent = new Intent(mContext, RegisterActivity.class);
				startActivity(intent);
			}

		}
	};


}
