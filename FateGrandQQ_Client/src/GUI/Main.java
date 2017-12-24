package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.tree.*;

import Methon.FriTreeNode;
import Methon.FriTreeRender;
import client.ClientThread;
import object.USER;
import JDBC.connectDB;


public class Main{
	USER user;
	public JFrame frame;
	JLabel title=new JLabel("Fate/Grand QQ");
	JLabel bkLabel,close,lessen;
	public JLabel head;
	JLabel name,state;
	JTextArea qianming;
	protected int xOld;
	protected int yOld;
	JPanel FriendOrQunChoice_panel=new JPanel();
	CardLayout FriendOrQunChoice=new CardLayout();
	JLabel friend_label,qun_label;
	JScrollPane friend_jsp=new JScrollPane();
	JScrollPane qun_jsp=new JScrollPane();
	JPanel friend_panel=new JPanel();
	JPanel qun_panel=new JPanel();
	public DefaultTreeModel friend_Model,qun_Model;
	FriTreeNode Friend_Top_Root;
	public FriTreeNode Friend_Root;
	public JTree Friend_tree;
	FriTreeNode Qun_Top_Root;
	FriTreeNode Qun_Root;
	JTree Qun_tree;
	JLabel add_Friend;

	private Socket client;// 声明一个客户端的套接字
	private PrintWriter pw;// 声明一个打印流
	ClientThread clientThread;

	private void connercting(String userName) {
		try {
			// 握手
			client = new Socket("127.0.0.1", 9090);
			pw = new PrintWriter(client.getOutputStream(),true);
			pw.println(userName);
			//System.out.println("握手信息："+pw);
			clientThread = new ClientThread(client);
			clientThread.start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "服务器连接失败！！！");
		}
	}


	public Main(USER user) {
		this.user=user;
		connercting(user.name);
		clientThread.setMain(this);
		
		frame = new JFrame();
		frame.setBounds(1320, 0,364,923);
		frame.setResizable(false);

		title.setBounds(10, 5, 200, 25);
		title.setOpaque(false);
		title.setForeground(Color.white);
		frame.getLayeredPane().add(title,0);

		bkLabel=new JLabel(new ImageIcon("src/GUI/img/sys/Main.png"));
		bkLabel.setBounds(0, 0,364,923);
		bkLabel.setBorder(new LineBorder(Color.DARK_GRAY));
		frame.getLayeredPane().add(bkLabel,2);

		close=new JLabel(new ImageIcon(new ImageIcon("src/GUI/img/sys/close.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
		close.setBounds(335, 14, 16, 16);
		frame.getLayeredPane().add(close,0);
		close.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
				close.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/sys/close.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
			}
			public void mousePressed(MouseEvent arg0) {
				close.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/sys/close2.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));	
			}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				try {
					new connectDB().UserExit(user.name);
				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				String str="exit@#"+user.name;
				pw.println(str);
				System.exit(0);
			}
		});

		lessen=new JLabel(new ImageIcon(new ImageIcon("src/GUI/img/sys/lessen.png").getImage().getScaledInstance(16,16, Image.SCALE_SMOOTH)));
		lessen.setBounds(304,14, 16, 16);
		frame.getLayeredPane().add(lessen,0);
		lessen.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
				lessen.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/sys/lessen.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
			}
			public void mousePressed(MouseEvent arg0) {
				lessen.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/sys/lessen2.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));	
			}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}
		});

		this.head=new JLabel(new ImageIcon(new ImageIcon(user.head).getImage().getScaledInstance(79, 79, Image.SCALE_SMOOTH)));
		head.setBounds(18, 57, 79, 79);
		head.setToolTipText("点击进入个人资料");
		head.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		head.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				try {
					data data = new data(client,user.qq);
					clientThread.setData(data);
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.getLayeredPane().add(this.head,1);

		name=new JLabel(user.name);
		name.setBounds(120, 55, 300, 25);
		name.setFont(new Font("微软雅黑", Font.BOLD, 18));
		name.setForeground(Color.WHITE);
		frame.getLayeredPane().add(name,0);

		qianming=new JTextArea(user.sign);
		qianming.setBounds(120, 85, 180, 80);
		qianming.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		qianming.setForeground(Color.WHITE);
		qianming.setEditable(false);
		qianming.setLineWrap(true);
		qianming.setWrapStyleWord(true);
		qianming.setOpaque(false);
		qianming.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		qianming.setToolTipText("个性签名，点击可编辑");
		frame.getLayeredPane().add(qianming,0);
		qianming.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				qianming.setForeground(Color.black);
				qianming.setEditable(true);
				qianming.setOpaque(true);
			}
		});

		state=new JLabel(new ImageIcon("src/GUI/img/sys/在线.png"));
		state.setBounds(300,58,20,20);
		state.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		state.setToolTipText("当前状态，点击可以切换状态");
		frame.getLayeredPane().add(state,0);

		frame.addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent arg0) {}
			public void mouseDragged(MouseEvent e) {
				int xOnScreen = e.getXOnScreen();
				int yOnScreen = e.getYOnScreen();
				int xx = xOnScreen - xOld;
				int yy = yOnScreen - yOld;
				frame.setLocation(xx, yy);//设置拖拽后，窗口的位置
			}
		});

		FriendOrQunChoice_panel.setLayout(FriendOrQunChoice);
		FriendOrQunChoice_panel.setOpaque(false);
		FriendOrQunChoice_panel.setBounds(1, 235, bkLabel.getIcon().getIconWidth()-2, bkLabel.getIcon().getIconHeight()-237-48);
		frame.getLayeredPane().add(FriendOrQunChoice_panel,2);

		friend_label=new JLabel(new ImageIcon("src/GUI/img/sys/friend.png"));
		friend_label.setBounds(79, 203, friend_label.getIcon().getIconWidth(), friend_label.getIcon().getIconHeight());

		frame.getLayeredPane().add(friend_label,0);

		qun_label=new JLabel(new ImageIcon("src/GUI/img/sys/qun_no_select.png"));
		qun_label.setBounds(257, 199, qun_label.getIcon().getIconWidth(), qun_label.getIcon().getIconHeight());

		frame.getLayeredPane().add(qun_label,0);


		FriendOrQunChoice_panel.add("friend_jsp", friend_jsp);
		FriendOrQunChoice_panel.add("qun_jsp", qun_jsp);
		friend_label.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				friend_label.setIcon(new ImageIcon("src/GUI/img/sys/friend.png"));
				qun_label.setIcon(new ImageIcon("src/GUI/img/sys/qun_no_select.png"));
				FriendOrQunChoice.show(FriendOrQunChoice_panel,"friend_jsp");
			}
		});
		qun_label.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				friend_label.setIcon(new ImageIcon("src/GUI/img/sys/friend_no_select.png"));
				qun_label.setIcon(new ImageIcon("src/GUI/img/sys/qun.png"));
				FriendOrQunChoice.show(FriendOrQunChoice_panel,"qun_jsp");
			}
		});

		frame.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {
				xOld = arg0.getX();
				yOld = arg0.getY();
			}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				if (qianming.isEditable()) {
					qianming.setForeground(Color.WHITE);
					qianming.setEditable(false);
					qianming.setOpaque(false);
					try {
						new connectDB().update_sign(user.qq, qianming.getText());
					} catch (SQLException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});

		//friend实现
		friend_jsp.setBounds(0,45, 362, 688);
		friend_jsp.setBorder(null);
		friend_jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		friend_jsp.setOpaque(false);

		friend_panel.setBounds(0, 0, 361, 1000);
		friend_panel.setLayout(null);
		friend_panel.setBackground(Color.white);

		Friend_Top_Root=new FriTreeNode("好友组");//不显示的，以便可以有很多分组
		Friend_Root=new FriTreeNode("我的好友");//显示的第一个列表
		Friend_Top_Root.addchild(Friend_Root);

		try {
			Friend_Root.addchild(new connectDB().init_friend(user));
		} catch (SQLException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		friend_Model = new DefaultTreeModel(Friend_Top_Root);
		Friend_tree = new JTree(friend_Model);
		Friend_tree.setBounds(0, 0, 361, 1000);
		Friend_tree.setCellRenderer(new FriTreeRender());
		Friend_tree.putClientProperty("JTree.lineStyle", "Horizontal");
		Friend_tree.setFont(new Font(Font.SANS_SERIF, Font.LAYOUT_LEFT_TO_RIGHT, 18));
		Friend_tree.setRowHeight(55);//树节点的高度
		Friend_tree.setToggleClickCount(1); //设置展开节点之前的鼠标单击数为1
		Friend_tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		Friend_tree.setRootVisible(false);// 设置根节点为不可见
		Friend_tree.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==2) {
					if (((FriTreeNode)Friend_tree.getLastSelectedPathComponent()).isLeaf()) {
						try {
							if (new connectDB().isLog(((FriTreeNode)Friend_tree.getLastSelectedPathComponent()).getuName())) {
								Talk talk =	new Talk(client,user.qq,((FriTreeNode)Friend_tree.getLastSelectedPathComponent()).get_qq());
								clientThread.setTalk(talk);
							}else {
								JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "该用户不在线，无法交流", "系统信息",
										JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (HeadlessException | ClassNotFoundException | SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});	

		friend_panel.add(Friend_tree);
		friend_jsp.getViewport().add(friend_panel);

		add_Friend=new JLabel(new ImageIcon("src/GUI/img/sys/add.png"));
		add_Friend.setBounds(316,881, add_Friend.getIcon().getIconWidth(), add_Friend.getIcon().getIconHeight());
		add_Friend.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		frame.getLayeredPane().add(add_Friend,0);
		add_Friend.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				//添加好友
				new AddFriends(user,friend_Model,Friend_tree,Friend_Root);
			}
		});

		qun_jsp.setBounds(0,45, 362, 688);
		qun_jsp.setBorder(null);
		qun_jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		qun_jsp.setOpaque(false);

		qun_panel.setBounds(0, 0, 361, 1000);
		qun_panel.setLayout(null);
		qun_panel.setBackground(Color.white);

		Qun_Top_Root=new FriTreeNode("群组");//不显示的，以便可以有很多分组
		Qun_Root=new FriTreeNode("我的QQ群");//显示的第一个列表
		Qun_Top_Root.addchild(Qun_Root);

		//Qun_Root.addchild(new FriTreeNode());
		Qun_Root.addchild(new FriTreeNode("群功能没有写"));
		//Qun_Root.addchild(new FriTreeNode("测试群没有实现2"));

		qun_Model = new DefaultTreeModel(Qun_Top_Root);
		Qun_tree = new JTree(qun_Model);
		Qun_tree.setBounds(0, 0, 361, 1000);
		Qun_tree.setCellRenderer(new FriTreeRender());
		Qun_tree.putClientProperty("JTree.lineStyle", "Horizontal");
		Qun_tree.setFont(new Font(Font.SANS_SERIF, Font.LAYOUT_LEFT_TO_RIGHT, 18));
		Qun_tree.setRowHeight(60);//树节点的高度
		Qun_tree.setToggleClickCount(1); //设置展开节点之前的鼠标单击数为1
		Qun_tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		Qun_tree.setRootVisible(false);// 设置根节点为不可见
		Qun_tree.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==2) {
					if (((FriTreeNode)Qun_tree.getLastSelectedPathComponent()).isLeaf()) {
						//new Talk(((FriTreeNode)Qun_tree.getLastSelectedPathComponent()).getuName());
						//QQ群在此实现，属于扩展内容
					}
				}
			}
		});	

		qun_panel.add(Qun_tree);
		qun_jsp.getViewport().add(qun_panel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}

	public void reloadFriendTree() {
		Friend_Root.RemoveALLchildren();
		if (Friend_Root.getChildCount()==-1) {
			try {
				Friend_Root.addchild(new connectDB().init_friend(user));
				Friend_tree.updateUI();
			} catch (SQLException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		new Login();
	}

}
