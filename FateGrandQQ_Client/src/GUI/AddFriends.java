package GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultTreeModel;

import JDBC.connectDB;
import Methon.FriTreeNode;
import object.USER;

public class AddFriends {
	JFrame frame;
	JLabel title=new JLabel("ÃÌº”∫√”—");
	JLabel bkLabel;
	JLabel close,lessen;
	JLabel text;
	JTextField input_field;
	JLabel button;
	
	public AddFriends(USER user,DefaultTreeModel model,JTree jTree,FriTreeNode Friend_Root) {
		frame = new JFrame();
		frame.setBounds((1920-540)/2, (1080-412)/2,540,412);
		frame.setResizable(false);
		
		JLabel bkLabel=new JLabel(new ImageIcon("src/GUI/img/sys/login.png"));
		bkLabel.setBounds(0, 0, bkLabel.getIcon().getIconWidth(), bkLabel.getIcon().getIconHeight());
		frame.getLayeredPane().add(bkLabel,1);
		
		title.setBounds(10, 5, 200, 25);
		title.setOpaque(false);
		title.setForeground(Color.white);
		frame.getLayeredPane().add(title,0);
		
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
				frame.dispose();
			}
		});
		
		lessen=new JLabel(new ImageIcon(new ImageIcon("src/GUI/img/sys/lessen.png").getImage().getScaledInstance(16, 2, Image.SCALE_SMOOTH)));
		lessen.setBounds(476,20, 16, 2);
		frame.getLayeredPane().add(lessen,0);
		lessen.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
				lessen.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/sys/lessen.png").getImage().getScaledInstance(16, 2, Image.SCALE_SMOOTH)));
			}
			public void mousePressed(MouseEvent arg0) {
				lessen.setIcon(new ImageIcon(new ImageIcon("src/GUI/img/sys/lessen2.png").getImage().getScaledInstance(16, 2, Image.SCALE_SMOOTH)));	
			}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}
		});
		
		text=new JLabel("ÃÌº”∫√”—£∫");
		text.setBounds(90,285,100,25);
		text.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 20));
		frame.getLayeredPane().add(text,0);
		
		input_field = new JTextField(10);
		input_field.setBounds(227, 279, 243, 38);
		input_field.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 18));
		input_field.setBorder(new LineBorder(Color.lightGray,1));
		frame.getLayeredPane().add(input_field,0);
		
		button=new JLabel(new ImageIcon("src/GUI/img/sys/ok.png"));
		button.setBounds(149, 348, 243, 38);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		frame.getLayeredPane().add(button,0);
		button.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				try {
					new connectDB().add_friend_db(user, Integer.valueOf(input_field.getText()),Friend_Root);
					model.reload();
					jTree.setModel(model);
					jTree.updateUI();
				} catch (NumberFormatException | SQLException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				frame.dispose();
				
			}
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setVisible(true);
	}
}
