package com.chen.a12763.myqq.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;


import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.chen.a12763.myqq.activity.Modify;
import com.chen.a12763.myqq.activity.SearchFriendActivity;
import com.chen.a12763.myqq.activity.register.StepAccount;
import com.chen.a12763.myqq.activity.register.StepPhoto;
import com.chen.a12763.myqq.bean.ApplicationData;
import com.chen.a12763.myqq.bean.TranObject;

public class ClientListenThread extends Thread {
	private Socket mSocket = null;
	private Context mContext = null;
	private ObjectInputStream mOis;

	private boolean isStart = true;

	public ClientListenThread(Context context, Socket socket) {
		this.mContext = context;
		this.mSocket = socket;
		try {
			mOis = new ObjectInputStream(mSocket.getInputStream());
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setSocket(Socket socket) {
		this.mSocket = socket;
	}

	@Override
	public void run() {
		try {
			isStart = true;
			while (isStart) {
				TranObject mReceived = null;
				//System.out.println("开始接受服务器");
				mReceived = (TranObject) mOis.readObject();
				System.out.println("接受成功");
				System.out.println(mReceived.getTranType());
				System.out.println("接收内容  "+mReceived.getResult());
				switch (mReceived.getTranType()) {
				case REGISTER_ACCOUNT:
					StepAccount.setRegisterInfo(mReceived, true);
					 System.out.println("注册账号成功");
					break;
				case REGISTER:
					StepPhoto.setRegisterInfo(mReceived, true);
					break;
				case LOGIN:
					ApplicationData.getInstance().loginMessageArrived(mReceived);
					System.out.println("跳出LOgin switch");
					break;
				case SEARCH_FRIEND:
					System.out.println("收到朋友查找结果");
					SearchFriendActivity.messageArrived(mReceived);
					break;
				case FRIEND_REQUEST:
					ApplicationData.getInstance().friendRequestArrived(mReceived);
					break;
				case MESSAGE:
					ApplicationData.getInstance().messageArrived(mReceived);
					break;
				case UPDATE_INFO:
					Modify.messageArrived();
					  break;
				default:
					break;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void close() {
		isStart = false;
	}
}
