package GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.LineBorder;

import JDBC.InfoDB;
import object.USER;

public class data {
	public JFrame frame;
	JPanel panel=new JPanel(null);
	JScrollPane jScrollPane;
	JLabel close,lessen,head,pic,sign;
	JLabel data=new JLabel("个人资料");
	JLabel Edite=new JLabel("编辑资料");
	JLabel qq,sex,age,birth;
	JLabel sexLabel,birthLabel,yue,ri;
	Choice sexbox,month,day;
	JLabel comefrom;
	JTextField name,ageField,comefromField;
	JButton button=new JButton("保存");
	JLabel photo,line,photoWall,updatePhoto;
	JPanel left,right,photoPanel;
	public JLabel MainPhoto;
	public JLabel[] photos=new JLabel[6];
	USER user;
	InfoDB db=new InfoDB();
	private PrintWriter pw;
	
	public data(Socket client,int qqNum) throws ClassNotFoundException, SQLException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			pw=new PrintWriter(client.getOutputStream(), true);
		} catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | IOException e1) {
			e1.printStackTrace();
		}
		user=new USER(qqNum);
		frame=new JFrame();
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setBounds((1920-900)/2, (1080-650)/2, 900, 650);
		frame.add(panel);
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 900, 650);
		
		left=new JPanel(null);
		left.setBounds(0, 0, 450, 650);
		left.setBackground(new Color(79, 68, 74));
		panel.add(left);
		
		right=new JPanel(null);
		right.setLocation(450, 30);
		right.setBackground(Color.white);
		
		jScrollPane=new JScrollPane();
		jScrollPane.setBorder(null);
		jScrollPane.getViewport().add(right);
		jScrollPane.setBounds(450, 30, 450, 620);
		panel.add(jScrollPane);
		
		close=new JLabel(new ImageIcon(new ImageIcon("src/GUI/img/sys/close2.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
		close.setBounds(870, 9, 16, 16);
		frame.getLayeredPane().add(close,0);
		close.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();
			}
		});
		
		lessen=new JLabel(new ImageIcon(new ImageIcon("src/GUI/img/sys/lessen2.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
		lessen.setBounds(840,8, 16, 16);
		frame.getLayeredPane().add(lessen,0);
		lessen.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}
		});
		
		pic=new JLabel(new ImageIcon("src/GUI/img/sys/addPic.png"));
		pic.setBounds(0, 0,450,450);
		if (db.hasPic(user.qq)) {
			ImageIcon imageIcon = new ImageIcon(db.getPic(user.qq));
			float rate=imageIcon.getIconHeight()/imageIcon.getIconWidth();
			pic.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(450,(int) (450*rate)+100, Image.SCALE_SMOOTH)));
		}
		pic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pic.setToolTipText("点击可以替换图片");
		pic.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				String url="src/GUI/img/sys/addPic.png";
				JFileChooser jFileChooser=new JFileChooser();
				jFileChooser.setDialogTitle("建议不要选默认文件夹外的图片！！！！");
				jFileChooser.setCurrentDirectory(new File("src/GUI/img/pic"));
				int i = jFileChooser.showOpenDialog(null);
				if (i==JFileChooser.OPEN_DIALOG) {
					url=jFileChooser.getSelectedFile().getPath();
					try {
						db.updatePic(user.qq, url);
						ImageIcon imageIcon = new ImageIcon(db.getPic(user.qq));
						float rate=imageIcon.getIconHeight()/imageIcon.getIconWidth();
						pic.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(450, (int) (450*rate)+100, Image.SCALE_SMOOTH)));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		left.add(pic,-1);
		
		
		head=new JLabel(new ImageIcon(new ImageIcon(user.head).getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		head.setBounds(75, 513, 75, 75);
		head.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		left.add(head);
		head.setToolTipText("点击可以替换头像");
		head.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				String url2="";
				JFileChooser jFileChooser=new JFileChooser();
				jFileChooser.setDialogTitle("不要选默认文件夹外的图片！！！！");
				jFileChooser.setCurrentDirectory(new File("src/GUI/img/head"));
				int i = jFileChooser.showOpenDialog(null);
				if (i==JFileChooser.OPEN_DIALOG) {
					url2=jFileChooser.getSelectedFile().getPath();
					url2=url2.substring(50);
					try {
						db.updateHead(user.qq, url2);
						data.this.user=new USER(user.qq);
						head.setIcon(new ImageIcon(new ImageIcon(data.this.user.head).getImage().getScaledInstance(75,75, Image.SCALE_SMOOTH)));
						//需要在这里向服务器发送跟换了头像的消息
						pw.println("Head@#"+user.name+"@#"+url2);
						pw.flush();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		name=new JTextField();
		name.setText(user.name);
		name.setEditable(false);
		name.setBorder(null);
		name.setBackground(null);
		name.setBounds(160, 520, 280, 40);
		name.setFont(new Font("微软雅黑", Font.BOLD, 30));
		name.setForeground(Color.WHITE);
		left.add(name);
		
		sign=new JLabel(user.sign);
		sign.setBounds(160, 565, 300, 25);
		sign.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		sign.setForeground(Color.WHITE);
		left.add(sign);
		
		data.setBounds(30, 0, 100, 50);
		data.setFont(new Font("微软雅黑", Font.BOLD, 18));
		data.setForeground(Color.gray);
		right.add(data);
		
		qq = setLabel("qq.png","QQ ：      "+String.valueOf(user.qq), 50);
		right.add(qq);
		
		sex=setLabel("sex.png", "性别：", 85);
		right.add(sex);
		sexLabel=new JLabel(db.getSex(user.qq));
		sexLabel.setBounds(189, 88, 100, 25);
		sexLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		right.add(sexLabel);
		String[] sexChoice= {"未知","男","女"};
		sexbox=setChoice(sexChoice, 188, 85, 100);
		sexbox.select(db.getSex(user.qq));
		sexbox.setVisible(false);
		
		age=setLabel("age.png", "年龄：", 120);
		right.add(age);
		ageField=new JTextField(db.getAge(user.qq), 10);
		ageField.setBounds(188, 120, 50, 30);
		ageField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		ageField.setEditable(false);
		ageField.setBorder(null);
		ageField.setBackground(Color.WHITE);
		right.add(ageField);
		
		birth=setLabel("birth.png", "生日：", 155);
		right.add(birth);
		birthLabel=new JLabel(db.getMonth(user.qq)+"月"+db.getDay(user.qq)+"日");
		birthLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		birthLabel.setBounds(188, 159, 200, 25);
		right.add(birthLabel);
		
		
		comefrom=setLabel("loc.png", "来自：", 190);
		right.add(comefrom);
		comefromField=new JTextField(db.getComefrom(user.qq), 10);
		comefromField.setBounds(188, 190, 230, 30);
		comefromField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		comefromField.setEditable(false);
		comefromField.setBorder(null);
		comefromField.setBackground(Color.WHITE);
		right.add(comefromField);
		
		Edite.setBounds(350, 13, 100, 25);
		Edite.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		Edite.setForeground(new Color(120, 207, 252));
		right.add(Edite);
		Edite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Edite.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				if (sexLabel.isVisible()) {
					try {
						EditeData();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		button.setBounds(300, 8, 80, 25);
		button.setFocusPainted(false);
		button.setVisible(false);
		right.add(button);
		
		line=new JLabel(new ImageIcon("src/GUI/img/sys/line.png"));
		line.setBounds(24, 230, line.getIcon().getIconWidth(), line.getIcon().getIconHeight());
		right.add(line);
		
		photoWall=new JLabel("图片墙");
		photoWall.setBounds(30, 240, 200, 25);
		photoWall.setForeground(Color.gray);
		photoWall.setFont(new Font("微软雅黑", Font.BOLD, 18));
		right.add(photoWall);
		
		updatePhoto=new JLabel("上传图片");
		updatePhoto.setBounds(350, 240, 100, 25);
		updatePhoto.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		updatePhoto.setForeground(new Color(120, 207, 252));
		right.add(updatePhoto);
		updatePhoto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		updatePhoto.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				try {
					updatePhoto();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		photoPanel=new JPanel();
		photoPanel.setBackground(Color.white);
		right.add(photoPanel);
		if (db.numPhoto(user.qq)==0) {
			MainPhoto=new JLabel(new ImageIcon("src/GUI/img/sys/noPhoto.jpg"));
			photoPanel.add(MainPhoto,-1);
			photoPanel.setBounds(30, 270, 382, MainPhoto.getIcon().getIconHeight()+100);
			right.setPreferredSize(new Dimension(420, 500));
		}else if (db.numPhoto(user.qq)==1) {
			String[] urls=db.getPhoto(user.qq);
			ImageIcon icon=new ImageIcon(urls[0]);
			float rate=icon.getIconHeight()/icon.getIconWidth();
			MainPhoto=new JLabel(new ImageIcon(icon.getImage().getScaledInstance(380,(int) (380*rate)+100, Image.SCALE_SMOOTH)));
			if (icon.getIconHeight()>500) {
				MainPhoto.setSize(380, 500);
			}
			photoPanel.add(MainPhoto,-1);
			photoPanel.setBounds(30, 270, 382, 600);
			right.setPreferredSize(new Dimension(420, 800));
		}else if (db.numPhoto(user.qq)!=0 && db.numPhoto(user.qq)!=1) {
			String[] urls=db.getPhoto(user.qq);
			int num=db.numPhoto(user.qq);
			ImageIcon[] icon=new ImageIcon[9];
			float[] rate=new float[9];
			for (int i = 0; i < urls.length; i++) {
				icon[i]=new ImageIcon(urls[i]);
				rate[i]=icon[i].getIconHeight()/icon[i].getIconWidth();
			}
			MainPhoto=new JLabel(new ImageIcon(icon[0].getImage().getScaledInstance(380,(int) (380*rate[0])+100, Image.SCALE_SMOOTH)));
			MainPhoto.setName(urls[0]);
			if (icon[0].getIconHeight()>500) {
				MainPhoto.setSize(380, 500);
			}
			MainPhoto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			MainPhoto.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent arg0) {}
				public void mousePressed(MouseEvent arg0) {}
				public void mouseExited(MouseEvent arg0) {
					MainPhoto.setBorder(null);
				}
				public void mouseEntered(MouseEvent arg0) {
					MainPhoto.setBorder(new LineBorder(Color.blue));
				}
				public void mouseClicked(MouseEvent arg0) {
					new Photo(client,user, MainPhoto.getName());
				}
			});
			photoPanel.add(MainPhoto);
			
			for (int i = 0; i < num-1; i++) {
				photos[i]=new JLabel(new ImageIcon(icon[i+1].getImage().getScaledInstance(120,120, Image.SCALE_SMOOTH)));
				photos[i].setName(urls[i+1]);
				photos[i].setPreferredSize(new Dimension(120, 120));
				photoPanel.add(photos[i], -1);
				photos[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				photos[i].addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {}
					public void mousePressed(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {
						((JLabel)e.getSource()).setBorder(null);
					}
					public void mouseEntered(MouseEvent e) {
						((JLabel)e.getSource()).setBorder(new LineBorder(Color.blue));
					}
					public void mouseClicked(MouseEvent e) {
						new Photo(client,user,((JLabel)e.getSource()).getName());
					}
				});
			}
			photoPanel.setBounds(30, 270, 382, 1000);
			right.setPreferredSize(new Dimension(420, 1020));
			
		}
		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setVisible(true);
	}
	
	private JLabel setLabel(String icon,String txt,int y) {
		JLabel label=new JLabel(txt);
		if (icon.equals("/")) {
			icon="default.png";
		}
		label.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/sys/"+icon).getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
		label.setBounds(63, y, 250, 30);
		label.setIconTextGap(20);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		
		return label;
	}
	private Choice setChoice(String txt[],int x,int y,int width) {
		Choice choice=new Choice();
		choice.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		for (int i = 0; i < txt.length; i++) {
			choice.add(txt[i]);
		}
		choice.setFocusable(false);
		choice.setBounds(x, y, width, 20);
		right.add(choice,0);
		return choice;
	}
	
	public void EditeData() throws SQLException {
		Edite.setVisible(false);
		button.setVisible(true);
		
		name.setEditable(true);
		name.setBackground(Color.WHITE);
		name.setBorder(new LineBorder(Color.BLACK,1));
		name.setForeground(Color.black);
		
		sexLabel.setVisible(false);
		sexbox.setVisible(true);
		ageField.setEditable(true);
		ageField.setBorder(new LineBorder(Color.gray,1));
		
		birthLabel.setVisible(false);
		month=new Choice();
		for (int i = 0; i < 12; i++) {
			month.add(String.valueOf(i+1));
		}
		month.select(db.getMonth(user.qq));
		month.setBounds(188,159, 50, 100);
		month.setFocusable(false);
		month.setBackground(Color.white);
		right.add(month,0);
		yue=new JLabel("月");
		yue.setBounds(245, 145, 50, 50);
		yue.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		right.add(yue, 0);
		day=new Choice();
		for (int i = 0; i < 31; i++) {
			day.add(String.valueOf(i+1));
		}
		day.select(db.getDay(user.qq));
		day.setBounds(265,159, 50, 100);
		day.setFocusable(false);
		day.setBackground(Color.white);
		right.add(day,0);
		ri=new JLabel("日");
		ri.setBounds(320, 145, 50, 50);
		ri.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		right.add(ri, 0);
		
		
		comefromField.setEditable(true);
		comefromField.setBorder(new LineBorder(Color.gray,1));
		
		button.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				try {
					saveData();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void saveData() throws SQLException {
		Edite.setVisible(true);
		button.setVisible(false);
		
		name.setEditable(false);
		name.setBackground(null);
		name.setBorder(null);
		name.setForeground(Color.white);
		
		sexbox.setVisible(false);
		sexLabel.setText(sexbox.getSelectedItem());
		sexLabel.setVisible(true);
		
		ageField.setEditable(false);
		ageField.setBorder(null);
		
		birthLabel.setText(month.getSelectedItem()+"月"+day.getSelectedItem()+"日");
		birthLabel.setVisible(true);
		month.setVisible(false);
		yue.setVisible(false);
		day.setVisible(false);
		ri.setVisible(false);
		
		comefromField.setEditable(false);
		comefromField.setBorder(null);
		
		db.updateName(user.qq,name.getText());
		db.updateSex(user.qq, sexbox.getSelectedItem());
		db.updateAge(user.qq, Integer.valueOf(ageField.getText()));
		db.updateMonth(user.qq, Integer.valueOf(month.getSelectedItem()));
		db.updateDay(user.qq, Integer.valueOf(day.getSelectedItem()));
		db.updateComefrom(user.qq, comefromField.getText());
	}

	public void updatePhoto() throws SQLException {
		String url3="";
		JFileChooser jFileChooser=new JFileChooser();
		jFileChooser.setDialogTitle("建议不要选默认文件夹外的图片！！！！");
		jFileChooser.setCurrentDirectory(new File("src/GUI/img/pic"));
		int i = jFileChooser.showOpenDialog(null);
		if (i==JFileChooser.OPEN_DIALOG) {
			url3=jFileChooser.getSelectedFile().getPath();
			if (db.numPhoto(user.qq)==0) {
				db.updatePhoto(user.qq, url3);
				ImageIcon icon=new ImageIcon(url3);
				float rate=icon.getIconHeight()/icon.getIconWidth();
				MainPhoto.setIcon(new ImageIcon(icon.getImage().getScaledInstance(380,(int) (380*rate)+100, Image.SCALE_SMOOTH)));
			}else{
				db.updatePhoto(user.qq, url3);
				JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "请重新打开界面刷新", "系统信息",
						JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
			}
		}
	}
}