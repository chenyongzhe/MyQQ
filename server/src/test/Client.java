package test;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.chen.a12763.myqq.bean.TranObject;
import com.chen.a12763.myqq.bean.TranObjectType;
import com.chen.a12763.myqq.bean.User;


public class Client {
	public static void main(String []args){
		try {
			Socket s =new Socket("192.168.138.46",8399);
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			TranObject tran = new TranObject();
			User user = new User();
			user.setAccount("123456");
			user.setPassword("123456");
			tran.setObject(user);
			tran.setTranType(TranObjectType.LOGIN);
			out.writeObject(tran);
			ObjectInputStream in = new ObjectInputStream(s.getInputStream());
			while(true){}//保持程序不退出
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
