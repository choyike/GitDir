package client;

import java.awt.Image;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import GUI.Main;
import GUI.Talk;
import GUI.data;
import JDBC.connectDB;

public class ClientThread extends Thread {
	Socket client;
	Talk talk;
	Main main;
	data data;
	
	public ClientThread(Socket client){
		this.client=client;
	}
	public void setTalk(Talk talk) {
		this.talk=talk;
	}
	public void setMain(Main main) {
		this.main=main;
	}
	public void setData(data data) {
		this.data=data;
	}
	@SuppressWarnings("resource")
	public void run() {
		try {
			Scanner sc = new Scanner(client.getInputStream());
			while (sc.hasNextLine()) {
				String str = sc.nextLine();
				String[] msgs = str.split("@#");
				// ͨ������Э�飬��������˷���������Ϣ
				if ("login".equals(msgs[0])) {
					if ("error".equals(msgs[1])) {// �ж����ӵ��������Ŀͻ����Ƿ���ͬ��
						JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "�Ѿ���¼����");
						System.exit(0);
						continue;
					} else {
						// ���ݿ����ߴ���
						new connectDB().Userlog(msgs[1]);
					}
				}
				if ("loginMes".equals(msgs[0])||"exit".equals(msgs[0])) {
					main.reloadFriendTree();
				}
				if ("Talk".equals(msgs[0])) {
					if (talk.my_user.name.equals(msgs[2])) {// ��Ϣ��������Լ�
						talk.get_mes(msgs[1], msgs[2], msgs[3]);
					}
				}
				if ("Head".equals(msgs[0])) {
					main.head.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/head/"+msgs[1]).getImage().getScaledInstance(79, 79, Image.SCALE_SMOOTH)));
				}
				if ("Delete".equals(msgs[0])) {
					if (data.MainPhoto.getName().equals(msgs[1])) {
						data.frame.dispose();
						JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "�����´򿪽���ˢ��", "ϵͳ��Ϣ",
								JOptionPane.INFORMATION_MESSAGE);
					}
					for (int i = 0; i < data.photos.length; i++) {
						if (data.photos[i].getName().equals(msgs[1])) {
							data.photos[i].setVisible(false);
							break;
						}
					}
				}
			}
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
