package GUI;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.*;

import JDBC.InfoDB;
import object.USER;

public class Photo {
	JFrame frame=new JFrame();
	JPanel panel=new JPanel(null);
	JLabel label,del;
	InfoDB db;
	private PrintWriter pw;
	
	public Photo(Socket client,USER user,String url) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			db=new InfoDB();
			pw=new PrintWriter(client.getOutputStream(), true);
		} catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException | SQLException | IOException e1) {
			e1.printStackTrace();
		}
		
		frame.setResizable(false);
		frame.add(panel);
		
		label=new JLabel(new ImageIcon(url));
		label.setBounds(0, 0, label.getIcon().getIconWidth(), label.getIcon().getIconHeight());
		if (label.getHeight()>1000) {
			label.setSize((int)(label.getWidth()*0.8), (int)(label.getHeight()*0.8));
		}
		panel.add(label);
		panel.setPreferredSize(new Dimension(label.getWidth()-20, label.getHeight()-12));
		
		del=new JLabel();
		del.setBounds(0, 0, 100, 100);
		del.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel.add(del,0);
		del.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				del.setIcon(null);
			}
			public void mouseEntered(MouseEvent arg0) {
				del.setIcon(new ImageIcon("src/GUI/img/sys/delelct.png"));
				del.setToolTipText("É¾³ý¸ÃÍ¼Æ¬");
			}
			public void mouseClicked(MouseEvent arg0) {
				try {
					db.delPhoto(user.qq, url);
					frame.dispose();
					pw.println("Delete@#"+user.name+"@#"+url);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		frame.pack();
		frame.setLocation((1920-frame.getWidth())/2, (1080-frame.getHeight())/2);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
	}
}
