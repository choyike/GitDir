package GUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.LineBorder;

import JDBC.connectDB;

public class Regist {
	JFrame frame;
	JLabel title=new JLabel("◊¢≤·–¬”√ªß");
	JLabel close,lessen;
	JLabel titleLabel,accountLabel,passwordLabel,nameLabel;
	JLabel accountMes,passwordMes,nameMes;
	JLabel finish,cancel;
	JTextField accountInfo,passwordInfo,nameInfo;
	
	public Regist() {
		frame=new JFrame();
		frame.setBounds((1920-300)/2, (1080-600)/2,300,600);
		frame.setResizable(false);
		
		title.setBounds(10, 5, 200, 25);
		title.setOpaque(false);
		title.setForeground(Color.white);
		frame.getLayeredPane().add(title,0);
		
		JLabel bkLabel=new JLabel(new ImageIcon("src/GUI/img/sys/Regist.png"));
		bkLabel.setBounds(0, 0, bkLabel.getIcon().getIconWidth(), bkLabel.getIcon().getIconHeight());
		frame.getLayeredPane().add(bkLabel,1);
		
		close=new JLabel(new ImageIcon(new ImageIcon("src/GUI/img/sys/close.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
		close.setBounds(274, 9, 16, 16);
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
				frame.dispose();
			}
		});
		
		lessen=new JLabel(new ImageIcon(new ImageIcon("src/GUI/img/sys/lessen.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
		lessen.setBounds(246,8, 16, 16);
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
		
		titleLabel=new JLabel("◊¢≤·’À∫≈");
		titleLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 28));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(bkLabel.getX()+95,50,200,50);
		frame.getLayeredPane().add(titleLabel,1);
		
		accountLabel=Label("’À∫≈£∫", 200);
		accountInfo=field(200);
		accountMes=MesLabel("*«Î ‰»Îƒ„µƒID", 225);
		
		passwordLabel=Label("√‹¬Î£∫", 270);
		passwordInfo=field(270);
		passwordMes=MesLabel("*«Î ‰»Îƒ„µƒ√‹¬Î", 295);
		
		nameLabel=Label("Í«≥∆£∫", 340);
		nameInfo=field(340);
		nameMes=MesLabel("*«Î ‰»Îƒ„œÎ“™µƒÍ«≥∆", 365);
		
		finish=new JLabel(new ImageIcon("src/GUI/img/sys/finish.png"));
		finish.setBounds(25,520, 100, 35);
		finish.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		frame.getLayeredPane().add(finish,1);
		finish.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				try {
					new connectDB().regist_info(accountInfo.getText(),passwordInfo.getText(),nameInfo.getText());
					JOptionPane.showMessageDialog(frame.getContentPane(), "“—◊¢≤·≥…π¶£¨«Î÷ÿ–¬µ«¬º", "œµÕ≥–≈œ¢",
							JOptionPane.INFORMATION_MESSAGE);
					frame.dispose();
				} catch (SQLException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		cancel=new JLabel(new ImageIcon("src/GUI/img/sys/cancel_regist.png"));
		cancel.setBounds(175, 520, 100, 35);
		cancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		frame.getLayeredPane().add(cancel,1);
		cancel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setVisible(true);
	}
	
	public JLabel Label(String mes,int y) {
		JLabel label=new JLabel(mes);
		label.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 18));
		label.setForeground(Color.DARK_GRAY);
		label.setBounds(25, y, 100, 25);
		frame.getLayeredPane().add(label,1);
		return label;
	}
	public JTextField field(int y) {
		JTextField jTextField=new JTextField();
		jTextField.setBorder(new LineBorder(Color.gray,1));
		jTextField.setBounds(80, y, 200, 25);
		jTextField.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 18));
		frame.getLayeredPane().add(jTextField,1);
		
		return jTextField;
	}
	public JLabel MesLabel(String mes,int y) {
		JLabel label=new JLabel(mes);
		label.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		label.setForeground(Color.BLUE);
		label.setBounds(80, y, 200, 20);
		frame.getLayeredPane().add(label,1);
		return label;
	}
}
