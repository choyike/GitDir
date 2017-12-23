package GUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.*;

import JDBC.connectDB;
import object.USER;

public class Login{
	JFrame frame;
	JLabel title=new JLabel("Fate/Grand QQ");
	JLabel close,lessen;
	JLabel zhanghao,mima;
	JLabel zhuche,zhaohuimima;
	JLabel denglu;
	JTextField zhanghaofield;
	JPasswordField passwordField;
	
	public Login() {
		try {
			new connectDB();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}

		frame = new JFrame();
		frame.setBounds((1920-540)/2, (1080-412)/2,540,412);
		frame.setResizable(false);
		
		title.setBounds(10, 5, 200, 25);
		title.setOpaque(false);
		title.setForeground(Color.white);
		frame.getLayeredPane().add(title,0);
		
		
		JLabel bkLabel=new JLabel(new ImageIcon("src/GUI/img/sys/login.png"));
		bkLabel.setBounds(0, 0, bkLabel.getIcon().getIconWidth(), bkLabel.getIcon().getIconHeight());
		frame.getLayeredPane().add(bkLabel,1);
		
		
		close=new JLabel(new ImageIcon(new ImageIcon("src/GUI/img/sys/close.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
		close.setBounds(514, 9, 16, 16);
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
				System.exit(0);
			}
		});
		
		lessen=new JLabel(new ImageIcon(new ImageIcon("src/GUI/img/sys/lessen.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
		lessen.setBounds(476,8, 16, 16);
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
		
		zhanghao=new JLabel("’À∫≈£∫");
		zhanghao.setBounds(100,245,100,25);
		zhanghao.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
		frame.getLayeredPane().add(zhanghao,0);
		
		zhanghaofield = new JTextField(10);
		zhanghaofield.setBounds(168, 242, 243, 38);
		zhanghaofield.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 18));
		zhanghaofield.setBorder(new LineBorder(Color.lightGray,1));
		frame.getLayeredPane().add(zhanghaofield,0);
		
		mima=new JLabel("√‹¬Î£∫");
		mima.setBounds(100,285,100,25);
		mima.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
		frame.getLayeredPane().add(mima,0);
		
		passwordField = new JPasswordField(20);
		passwordField.setBounds(168, 279, 243, 38);
		passwordField.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 18));
		passwordField.setBorder(new LineBorder(Color.lightGray,1));
		frame.getLayeredPane().add(passwordField,0);
		
		zhuche=new JLabel("◊¢≤·’À∫≈");
		zhuche.setBounds(424, 248, 200, 25);
		zhuche.setForeground(new Color(157, 109, 142));
		zhuche.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		zhuche.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		zhuche.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				new Regist();
			}
		});
		frame.getLayeredPane().add(zhuche,0);
		
		zhaohuimima=new JLabel("’“ªÿ√‹¬Î");
		zhaohuimima.setBounds(424, 286, 200, 25);
		zhaohuimima.setForeground(new Color(157, 109, 142));
		zhaohuimima.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		frame.getLayeredPane().add(zhaohuimima,0);

		denglu=new JLabel(new ImageIcon("src/GUI/img/sys/denglu.png"));
		denglu.setBounds(167, 348, 243, 38);
		denglu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		frame.getLayeredPane().add(denglu,0);
		denglu.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				try {
					if(new connectDB().login_confirm(zhanghaofield.getText(), String.valueOf(passwordField.getPassword()))) {
						frame.dispose();
						new Main(new USER(Integer.valueOf(zhanghaofield.getText())));
					}else {
						JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "µ«¬º ß∞‹£¨«Î÷ÿ ‘", "œµÕ≥–≈œ¢",
								JOptionPane.INFORMATION_MESSAGE);
						zhanghaofield.setText("");
						passwordField.setText("");
					}
				} catch (SQLException | NumberFormatException | HeadlessException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		passwordField.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar()==KeyEvent.VK_ENTER) {
					try {
						if(new connectDB().login_confirm(zhanghaofield.getText(), String.valueOf(passwordField.getPassword()))) {
							frame.dispose();
							new Main(new USER(Integer.valueOf(zhanghaofield.getText())));
						}else {
							JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "µ«¬º ß∞‹£¨«Î÷ÿ ‘", "œµÕ≥–≈œ¢",
									JOptionPane.INFORMATION_MESSAGE);
							zhanghaofield.setText("");
							passwordField.setText("");
						}
					} catch (SQLException | NumberFormatException | HeadlessException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					
				}
			}
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setVisible(true);
	}
	public static void main(String[] args) {
		new Login();
	}
}
