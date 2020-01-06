package com.chen.a12763.myqq.network;

import com.chen.a12763.myqq.bean.TranObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;



public class ClientSendThread {
	private Socket mSocket = null;
	private ObjectOutputStream oos = null;
	public ClientSendThread(Socket socket) {
		this.mSocket = socket;
		try {
			oos = new ObjectOutputStream(mSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void sendMessage(final TranObject t) throws IOException{
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					oos.writeObject(t);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
