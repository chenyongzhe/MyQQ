package com.chen.a12763.myqq.client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;

import com.chen.a12763.myqq.bean.TranObjectType;
import com.chen.a12763.myqq.bean.TranObject;

/**
 * 服务器对客户端的监听监听
 * 
 */
public class ClientListenThread implements Runnable {
	private ClientActivity client;
	private ObjectInputStream read;
	private boolean isRunning;

	public ClientListenThread(ObjectInputStream read, ClientActivity client) {
		this.read = read;
		this.client = client;
		isRunning = true;
	}

	@Override
	public void run() {
		SocketAddress s = client.getmClient().getRemoteSocketAddress();
		while (isRunning) {
			readMsg();
		}
	}

	private void readMsg() {
		SocketAddress s = client.getmClient().getRemoteSocketAddress();
		try {
			TranObject tran = (TranObject) read.readObject();
			TranObjectType type = tran.getTranType();
			switch (type) {
			case REGISTER_ACCOUNT:
				String account = (String) tran.getObject();
				System.out.println(account);
				client.checkAccount(account);
				break;
			case REGISTER:
				client.regist(tran);
				break;
			case LOGIN:
				client.login(tran);
				break;
			case SEARCH_FRIEND:
				client.searchFriend(tran);
				break;
			case FRIEND_REQUEST:
				client.friendRequset(tran);
				break;
			case MESSAGE:
				client.sendMessage(tran);
			case UPDATE_INFO:
					client.update_info(tran);
				    break;
			default:
				break;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void close() {
		isRunning = false;
	}

}
