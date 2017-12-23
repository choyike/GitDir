package client;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import GUI.Main;
import GUI.Talk;
import JDBC.connectDB;

public class ClientThread extends Thread {
	Socket client;
	Talk talk;
	Main main;
	public ClientThread(Socket client){
		this.client=client;
	}
	public void setTalk(Talk talk) {
		this.talk=talk;
	}
	public void setMain(Main main) {
		this.main=main;
	}
	@SuppressWarnings("resource")
	public void run() {
		try {
			Scanner sc = new Scanner(client.getInputStream());
			while (sc.hasNextLine()) {
				String str = sc.nextLine();
				String[] msgs = str.split("@#");
				// 通过聊天协议，解析服务端发送来的消息
				if ("login".equals(msgs[0])) {
					if ("error".equals(msgs[1])) {// 判断连接到服务器的客户中是否有同名
						JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "已经登录过了");
						System.exit(0);
						continue;
					} else {
						// 数据库上线处理
						new connectDB().Userlog(msgs[1]);
					}
				}
				if ("loginMes".equals(msgs[0])||"exit".equals(msgs[0])) {
					main.reloadFriendTree();
				}
				if ("Talk".equals(msgs[0])) {
					if (talk.my_user.name.equals(msgs[2])) {// 信息获得人是自己
						System.out.println("sender:::" + msgs);
						talk.get_mes(msgs[1], msgs[2], msgs[3]);
					}
				}
			}
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
