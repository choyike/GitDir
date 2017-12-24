package server;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;

import JDBC.connectDB;

public class Server{
	JFrame frame;
	JLabel title=new JLabel("Fate/Grand QQ_Server");
	JLabel close,lessen;

	private Map<String,Socket> usersMap = new HashMap<String,Socket>();

	public Server(){
		frame = new JFrame();
		frame.setBounds((1920-540)/2, (1080-412)/2,540,412);
		frame.setResizable(false);
		
		title.setBounds(5, 2, 200, 30);
		title.setForeground(Color.WHITE);
		frame.getLayeredPane().add(title,0);

		JLabel bkLabel=new JLabel(new ImageIcon("src/GUI/img/login.png"));
		bkLabel.setBounds(0, 0, bkLabel.getIcon().getIconWidth(), bkLabel.getIcon().getIconHeight());
		frame.getLayeredPane().add(bkLabel,1);

		ImageIcon closeImg=new ImageIcon(new ImageIcon("src/GUI/img/close.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		close=new JLabel(closeImg);
		close.setBounds(514, 9, 16, 16);
		frame.getLayeredPane().add(close,0);
		close.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
				close.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/close.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
			}
			public void mousePressed(MouseEvent arg0) {
				close.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/close2.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));	
			}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});


		ImageIcon lessenImg=new ImageIcon(new ImageIcon("src/GUI/img/lessen.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		lessen=new JLabel(lessenImg);
		lessen.setBounds(476,8, 16, 16);
		frame.getLayeredPane().add(lessen,0);
		lessen.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
				lessen.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/lessen.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
			}
			public void mousePressed(MouseEvent arg0) {
				lessen.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/lessen2.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));	
			}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}
		});

		JButton run=new JButton("�򿪷�����");
		run.setFocusPainted(false);
		run.setBounds((frame.getWidth()-220)/2, 275, 200, 50);
		frame.getLayeredPane().add(run,0);
		run.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				startServer();
				run.setEnabled(false);
			}
		});


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Server();
	}

	private void startServer() {
		try {
			ServerSocket server = new ServerSocket(9090);

			new ServerThread(server).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class ServerThread extends Thread{
		ServerSocket server = null;
		connectDB db;
		public ServerThread(ServerSocket server) {
			this.server = server;
		}
		//ר�Ŵ���������Ϣ
		public void run(){
			try {
				while(true){
					Socket socketClient = server.accept();
					//System.out.println("��������accept��"+socketClient);
					BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
					String userName;
					if(!(userName=in.readLine()).equals("")){
						if(isError(userName)){//�ж��û����Ƿ���ڣ�����������û�������ʾ��Ϣ
							PrintWriter pw = new PrintWriter(socketClient.getOutputStream(),true);
							String msg = "login@#error";
							pw.println(msg);
							pw.flush();
							continue;
						}
						//System.out.println(userName);
						//���ݿ����״̬
						db=new connectDB();
						db.Userlog(userName);
						//ά�������˵ĺ����б�
						msgAll(userName);
						//��һ��ר�����ں͸�userName�ͻ���ͨѶ���߳�
						new ClientThread(socketClient);
						//System.out.println("���������߳�:"+userName);
						//�Ѹ��û��ŵ��������û��ء���
						usersMap.put(userName, socketClient);
						//System.out.println("Map�أ�"+usersMap);
					}
				}
			} catch (IOException | SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		//�ж��û����Ƿ��Ѿ�������
		private boolean isError(String userName) {
			return usersMap.containsKey(userName);
		}
		
		private void msgAll(String userName) {  
            Iterator<Socket> it = usersMap.values().iterator();  
            while(it.hasNext()){  
                Socket s = it.next();  
                try {  
                    PrintWriter pw = new PrintWriter(s.getOutputStream(),true);  
                    //���û���ʾ����Ϣ���е�  
                    String msg = "loginMes@#"+userName;
                    pw.println(msg);
                    pw.flush();
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  

		class ClientThread extends Thread{
			private Socket client;

			public ClientThread(Socket client) {
				this.client = client;
				start();
			}

			@SuppressWarnings("resource")
			public void run(){
				try {
					Scanner sc = new Scanner(client.getInputStream());
					while(sc.hasNext()){
						String str = sc.nextLine();
						String[] msgs = str.split("@#");
						//System.out.println("���������ܣ�"+msgs);
						if("Talk".equals(msgs[0])){
							sendMsgToSb(msgs);
						}else if("exit".equals(msgs[0])){
							//�ӡ������û���usersMap����ɾ����ǰ�û�
							usersMap.remove(msgs[1]);
							//�����û��˳�����Ϣ�������������û�
							sendExitMsgToAll(msgs[1]);
						}else if ("Head".equals(msgs[0])) {
							resetHead(msgs[1],msgs[2]);
						}else if ("Delete".equals(msgs[0])) {
							DeletePhoto(msgs[1], msgs[2]);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			private void sendMsgToSb(String[] msgs) throws IOException {
				Socket s = usersMap.get(msgs[2]);
				String str = msgs[0]+"@#"+msgs[1]+"@#"+msgs[2]+"@#"+msgs[3];
				//System.out.println("fwq:"+str);
				PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
				pw.println(str);
				pw.flush();

			}

			private void sendExitMsgToAll(String username) throws IOException {
				Iterator<Socket> it = usersMap.values().iterator();
				while(it.hasNext()){
					Socket s =it.next();
					//���û���ʾ����Ϣ���е�
					String str = "exit@#"+username;
					PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
					pw.println(str);
					pw.flush();
				}
			}
			private void resetHead(String username,String url) throws IOException {
				Socket s = usersMap.get(username);
				PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
				String str="Head@#"+url;
				pw.println(str);
				pw.flush();
			}
			private void DeletePhoto(String username,String url) throws IOException {
				Socket s = usersMap.get(username);
				PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
				String string="Delete@#"+url;
				pw.println(string);
				pw.flush();
			}
		}
	}
}


