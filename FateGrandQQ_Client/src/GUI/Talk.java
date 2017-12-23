package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import object.USER;

public class Talk {
	JFrame frame;
	protected int xOld;
	protected int yOld;
	JLabel bkLabel, close, lessen;
	JLabel head, name, text;
	JLabel esc, send;
	JLabel T;
	JPanel T_Panel;
	static JTextArea field;
	static JScrollPane TalkJsp, inputJSP;
	TalkPanel talkPanel;
	JPanel mid;
	Queue<String> queue = new LinkedList<String>();;
	static int flag = 0;
	public USER my_user;
	public USER other_user;
	static int mesInt = 0;
	
	private PrintWriter pw;// 声明一个打印流

	public Talk(Socket client,int My_qq, int other_qq) {
		try {
			pw=new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		my_user = new USER(My_qq);
		other_user = new USER(other_qq);
		
		frame = new JFrame();
		frame.setResizable(false);
		bkLabel = new JLabel(new ImageIcon("src/GUI/img/sys/talk.png"));
		bkLabel.setBounds(0, 0, bkLabel.getIcon().getIconWidth(), bkLabel.getIcon().getIconHeight());
		frame.getLayeredPane().add(bkLabel, 2);
		frame.setBounds((1920 - bkLabel.getIcon().getIconWidth()) / 2, (1080 - bkLabel.getIcon().getIconHeight()) / 2, bkLabel.getIcon().getIconWidth(),
				bkLabel.getIcon().getIconHeight());

		close = new JLabel(new ImageIcon(new ImageIcon("src/GUI/img/sys/close.png").getImage().getScaledInstance(21, 21, Image.SCALE_SMOOTH)));
		close.setBounds(718, 12, 21, 21);
		frame.getLayeredPane().add(close, 0);
		close.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
				close.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/sys/close.png").getImage().getScaledInstance(21, 21,
						Image.SCALE_SMOOTH)));
			}

			public void mousePressed(MouseEvent arg0) {
				close.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/sys/close2.png").getImage().getScaledInstance(21, 21,
						Image.SCALE_SMOOTH)));
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();
			}
		});

		lessen = new JLabel(new ImageIcon(new ImageIcon("src/GUI/img/sys/lessen.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		lessen.setBounds(676, 10, 20, 20);
		frame.getLayeredPane().add(lessen, 0);
		lessen.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
				lessen.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/sys/lessen.png").getImage().getScaledInstance(20,20,
						Image.SCALE_SMOOTH)));
			}

			public void mousePressed(MouseEvent arg0) {
				lessen.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/sys/lessen2.png").getImage().getScaledInstance(20,
						20, Image.SCALE_SMOOTH)));
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent arg0) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}
		});

		head = new JLabel(new ImageIcon(new ImageIcon(other_user.head).getImage().getScaledInstance(86, 86, Image.SCALE_SMOOTH)));
		head.setBounds(39, 28, 86, 86);
		frame.getLayeredPane().add(head, 1);

		name = new JLabel(other_user.name);
		name.setBounds(160, 35, 300, 30);
		name.setFont(new Font("微软雅黑", Font.BOLD, 30));
		name.setForeground(Color.white);
		frame.getLayeredPane().add(name, 1);

		text = new JLabel(other_user.sign);
		text.setBounds(160, 80, 500, 30);
		text.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		text.setForeground(Color.white);
		frame.getLayeredPane().add(text, 1);

		field = new JTextArea();
		field.setBounds(18, 695, 713, 800);
		field.setLineWrap(true);
		field.setWrapStyleWord(true);
		Font defaultFont = new Font("微软雅黑", Font.PLAIN, 20);
		field.setFont(defaultFont);
		field.setOpaque(false);

		inputJSP = new JScrollPane(null);
		inputJSP.setBounds(18, 695, 713, 116);
		inputJSP.setOpaque(false);
		inputJSP.getViewport().setOpaque(false);
		inputJSP.setBorder(null);
		inputJSP.getViewport().add(field);
		frame.getLayeredPane().add(inputJSP, 1);

		esc = new JLabel(new ImageIcon("src/GUI/img/sys/esc.png"));
		esc.setBounds(455, 823, 115, 30);
		frame.getLayeredPane().add(esc, 1);
		esc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		esc.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
				frame.dispose();
			}

			public void mousePressed(MouseEvent arg0) {
				frame.dispose();
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();
			}
		});

		send = new JLabel(new ImageIcon("src/GUI/img/sys/send.png"));
		send.setBounds(600, 823, 115, 30);
		frame.getLayeredPane().add(send, 1);
		send.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		T = new JLabel(new ImageIcon("src/GUI/img/sys/T_label_1.png"));
		T.setBounds(40, 653, 30, 30);

		T_Panel = new JPanel(null);
		T_Panel.setBackground(new Color(152, 156, 185));
		T_Panel.setBounds(0, 617, 748, 35);
		T_Panel.setBorder(new LineBorder(Color.black));

		String font[] = { "微软雅黑", "宋体", "黑体", "苹方" };
		Choice FontChoice = new Choice();
		Choice SizeChoice = new Choice();
		for (int i = 0; i < font.length; i++)
			FontChoice.add(font[i]);
		FontChoice.setBounds(40, 5, 150, 30);
		for (int i = 1; i <= 30; i++) {
			SizeChoice.add(String.valueOf(i));
		}
		SizeChoice.select(19);
		SizeChoice.setBounds(200, 5, 100, 30);
		SizeChoice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				field.setFont(new Font(FontChoice.getSelectedItem(), Font.PLAIN, SizeChoice.getSelectedIndex()));
			}
		});
		FontChoice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				field.setFont(new Font(FontChoice.getSelectedItem(), Font.PLAIN, SizeChoice.getSelectedIndex()));
			}
		});

		T_Panel.add(FontChoice);
		T_Panel.add(SizeChoice);
		T_Panel.setBorder(null);
		frame.getLayeredPane().add(T_Panel, 0);
		T_Panel.setVisible(false);
		T.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
				T.setIcon(new ImageIcon("src/GUI/img/sys/T_label_1.png"));
			}

			public void mouseEntered(MouseEvent e) {
				T.setIcon(new ImageIcon("src/GUI/img/sys/T_label_2.png"));
			}

			public void mouseClicked(MouseEvent e) {
				if (flag == 0) {
					T_Panel.setVisible(true);
					flag = 1;
				} else {
					T_Panel.setVisible(false);
					flag = 0;
				}

			}
		});
		frame.getLayeredPane().add(T, 2);

		TalkJsp = new JScrollPane();
		TalkJsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		TalkJsp.setBounds(0, 128, 748, 523);
		TalkJsp.setOpaque(false);
		TalkJsp.getViewport().setOpaque(false);
		TalkJsp.setBorder(null);
		TalkJsp.doLayout();    // 如果不是有改语句，滚动条会显示在中间


		talkPanel = new TalkPanel(queue);
		frame.getLayeredPane().add(TalkJsp, 2);

		mid = new JPanel(null);
		mid.setOpaque(false);
		TalkJsp.getViewport().add(mid);

		field.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
					frame.dispose();
				}
				if (e.getKeyChar()==KeyEvent.VK_ENTER) {
					send_mes();
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
		});
		send.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				send_mes();
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
			public void mouseClicked(MouseEvent arg0) {}
		});
		frame.addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent arg0) {}
			public void mouseDragged(MouseEvent e) {
				int xOnScreen = e.getXOnScreen();
				int yOnScreen = e.getYOnScreen();
				int xx = xOnScreen - xOld;
				int yy = yOnScreen - yOld;
				frame.setLocation(xx, yy);// 设置拖拽后，窗口的位置
			}
		});


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}

	public void send_mes() {
		String str = field.getText();
		queue.offer(str);
		field.setText("");
		mesInt++;
		mid.setPreferredSize(new Dimension(748, mesInt * 140));
		mid.add(talkPanel.send_mes(queue, my_user));
		TalkJsp.getViewport().add(mid);
		JScrollBar jscrollBar = TalkJsp.getVerticalScrollBar();
		jscrollBar.setValue(jscrollBar.getMaximum());

		String sendmes = "Talk@#" + my_user.name + "@#" + other_user.name + "@#" + str;
		System.out.println("send_mes:"+sendmes);
		pw.println(sendmes);
		pw.flush();
	}

	public void get_mes(String sender, String getter, String str) {
		if (other_user.name.equals(sender) && my_user.name.equals(getter)) {
			mesInt++;
			mid.setPreferredSize(new Dimension(748, mesInt * 140));
			mid.add(talkPanel.get_mes(other_user, str));
			TalkJsp.getViewport().add(mid);
			JScrollBar jscrollBar = TalkJsp.getVerticalScrollBar();
			jscrollBar.setValue(jscrollBar.getMaximum());
		}
	}
	

		
	public static void main(String args[]) {
		new Login();
	}
}

class TalkPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	ArrayList<JPanel> panels = new ArrayList<JPanel>();

	public TalkPanel(Queue<String> queue) {
		this.setLayout(null);
		this.setOpaque(false);

	}
	public JPanel One() {
		JPanel panel = new JPanel(null);
		panel.setBounds(0, 0, 748, 140);
		panel.setPreferredSize(new Dimension(748, 140));
		panel.setOpaque(false);
		return panel;
	}

	public JPanel send_mes(Queue<String> queue, USER user) {
		JPanel panel = One();
		JLabel label = new JLabel();
		JLabel head = new JLabel();
		String str = queue.poll();

		panel.add(label);
		panel.add(head);
		panels.add(panel);
		panel.setBounds(0, (panels.size() - 1) * 140, 748, 140);

		label.setText("   " + str);
		label.setBackground(new Color(217, 218, 249));
		label.setOpaque(true);
		label.setBorder(new LineBorder(Color.white, 2));
		label.setFont(Talk.field.getFont());
		label.setBounds(245, 34, 400, 75);

		head.setIcon(new ImageIcon(new ImageIcon(user.head).getImage().getScaledInstance(54, 54, Image.SCALE_SMOOTH)));
		head.setBounds(672, 42, 54, 54);

		return panel;
	}

	public JPanel get_mes(USER user, String str) {
		JPanel panel = One();
		JLabel label = new JLabel();
		JLabel head = new JLabel();

		panel.add(label);
		panel.add(head);
		panels.add(panel);
		panel.setBounds(0, (panels.size() - 1) * 140, 748, 140);

		label.setText("   " + str);
		label.setBackground(new Color(217, 218, 249));
		label.setOpaque(true);
		label.setBorder(new LineBorder(Color.white, 2));
		label.setFont(Talk.field.getFont());
		label.setBounds(102, 34, 400, 75);

		head.setIcon(new ImageIcon(new ImageIcon(user.head).getImage().getScaledInstance(54, 54, Image.SCALE_SMOOTH)));
		head.setBounds(21, 42, 54, 54);

		return panel;
	}
}
