package JDBC;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Methon.FriTreeNode;
import object.USER;

public class connectDB {
	Connection con;// ����Connection����
	PreparedStatement preparedStatement;
	ResultSet rs;// ResultSet�࣬������Ż�ȡ�Ľ����

	//���캯��
	public connectDB() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/qq?useSSL=true";
		String user = "root";
		String password = "zhangyijie";
		con = DriverManager.getConnection(url, user, password);
	}
	//�������ݿ�
	public void run() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/qq?useSSL=true";
		String user = "root";
		String password = "zhangyijie";
		con = DriverManager.getConnection(url, user, password); // ����MySQL���ݿ�
	}
	//ֹͣ���ݿ�
	public void stopDB() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//��¼��֤
	public boolean login_confirm(String qq,String password) throws SQLException {
		String sql = "select * from info where qq=? and password=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1,qq);
		preparedStatement.setString(2,password);
		rs=preparedStatement.executeQuery();
		if (rs.next()) {
			System.out.println(rs.getString(3)+":��¼�ɹ�");
			return true;
		}
		return false;
	}
	//ע����Ϣ
	public void regist_info(String qq,String pw,String name) throws SQLException {
		String sql="insert into info (qq, password, nick_name) values(?,?,?)";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1,Integer.valueOf(qq));
		preparedStatement.setString(2, pw);
		preparedStatement.setString(3, name);
		preparedStatement.executeUpdate();
		System.out.println("insert OK");
	}

	//ͨ��QQ�Ż��USER
	public USER get_user(int qq) throws SQLException {
		String sql="select * from info where qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, qq);
		rs=preparedStatement.executeQuery();
		return new USER(rs);
	}

	//�����б��ȡ
	public  ArrayList<USER> init_friend(USER user) throws SQLException {
		String sql="select * from friend where UID=? group by FID ";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, user.qq);
		ResultSet set  = preparedStatement.executeQuery();
		ArrayList<USER> friend = new ArrayList<USER>();
		while(set.next()) {
			friend.add(get_user(set.getInt(2)));
		}
		return friend;
	}

	//���¸���ǩ��
	public void update_sign(int qq,String new_sign) throws SQLException {
		String sql = "UPDATE info SET sign=? WHERE qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1, new_sign);
		preparedStatement.setInt(2,qq);
		preparedStatement.executeUpdate();
		System.out.println("����ǩ���ɹ�");		
	}

	//user�Ƿ��Ǵ���
	public boolean isExiest(int qq) throws SQLException {
		String sql="select * from info where qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, qq);
		rs=preparedStatement.executeQuery();
		if (rs.next()) {
			return true;
		}
		return false;
	}

	//�Ƿ��Ǻ���
	public boolean isFriend(int qq1,int qq2) throws SQLException {
		if (isExiest(qq1)&&isExiest(qq2)) {
			String sql="select * from friend where UID=? and FID=?";
			preparedStatement=con.prepareStatement(sql);
			preparedStatement.setInt(1, qq1);
			preparedStatement.setInt(2, qq2);
			rs=preparedStatement.executeQuery();
			if (rs.next()) {
				return true;
			}
		}
		return false;
	}

	//��Ӻ���
	public void add_friend_db(USER user,int qq,FriTreeNode node) throws SQLException {
		if (!isExiest(qq)) {
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "��qq������", "ϵͳ��Ϣ",
					JOptionPane.INFORMATION_MESSAGE);
		}else if (isFriend(user.qq, qq)) {
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "�Ѿ��Ǻ����ˣ�", "ϵͳ��Ϣ",
					JOptionPane.INFORMATION_MESSAGE);
		}else {
			String sql="insert friend set UID=?,FID=?";
			preparedStatement=con.prepareStatement(sql);
			preparedStatement.setInt(1, user.qq);
			preparedStatement.setInt(2, qq);
			preparedStatement.executeUpdate();
			preparedStatement.setInt(1, qq);
			preparedStatement.setInt(2, user.qq);
			preparedStatement.executeUpdate();
			System.out.println("��Ӻ��� OK!");
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "��ӳɹ�", "ϵͳ��Ϣ",
					JOptionPane.INFORMATION_MESSAGE);
			node.addchild(new FriTreeNode(new USER(qq)));
		}
	}

	//User����
	public void Userlog(String user) throws SQLException {
		String sql = "UPDATE info SET state=? WHERE nickname=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1,"����");
		preparedStatement.setString(2,user);
		preparedStatement.executeUpdate();
	}

	//User����
	public void UserExit(String user) throws SQLException {
		String sql = "UPDATE info SET state=? WHERE nickname=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1,"����");
		preparedStatement.setString(2,user);
		preparedStatement.executeUpdate();
	}

	//user�Ƿ�����
	public boolean isLog(String user) throws SQLException {
		String sql = "select state from info where nickname=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1,user);
		rs=preparedStatement.executeQuery();
		if (rs.next()) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		try {
			new connectDB();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}
}
