package JDBC;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Methon.FriTreeNode;
import object.USER;

public class connectDB {
	Connection con;// 声明Connection对象
	PreparedStatement preparedStatement;
	ResultSet rs;// ResultSet类，用来存放获取的结果集

	//构造函数
	public connectDB() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/qq?useSSL=true";
		String user = "root";
		String password = "zhangyijie";
		con = DriverManager.getConnection(url, user, password);
	}
	//连接数据库
	public void run() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/qq?useSSL=true";
		String user = "root";
		String password = "zhangyijie";
		con = DriverManager.getConnection(url, user, password); // 连接MySQL数据库
	}
	//停止数据库
	public void stopDB() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//登录验证
	public boolean login_confirm(String qq,String password) throws SQLException {
		String sql = "select * from info where qq=? and password=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1,qq);
		preparedStatement.setString(2,password);
		rs=preparedStatement.executeQuery();
		if (rs.next()) {
			System.out.println(rs.getString(3)+":登录成功");
			return true;
		}
		return false;
	}
	//注册信息
	public void regist_info(String qq,String pw,String name) throws SQLException {
		String sql="insert into info (qq, password, nick_name) values(?,?,?)";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1,Integer.valueOf(qq));
		preparedStatement.setString(2, pw);
		preparedStatement.setString(3, name);
		preparedStatement.executeUpdate();
		System.out.println("insert OK");
	}

	//通过QQ号获得USER
	public USER get_user(int qq) throws SQLException {
		String sql="select * from info where qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, qq);
		rs=preparedStatement.executeQuery();
		return new USER(rs);
	}

	//好友列表读取
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

	//更新个性签名
	public void update_sign(int qq,String new_sign) throws SQLException {
		String sql = "UPDATE info SET sign=? WHERE qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1, new_sign);
		preparedStatement.setInt(2,qq);
		preparedStatement.executeUpdate();
		System.out.println("更新签名成功");		
	}

	//user是否是存在
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

	//是否是好友
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

	//添加好友
	public void add_friend_db(USER user,int qq,FriTreeNode node) throws SQLException {
		if (!isExiest(qq)) {
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "该qq不存在", "系统信息",
					JOptionPane.INFORMATION_MESSAGE);
		}else if (isFriend(user.qq, qq)) {
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "已经是好友了！", "系统信息",
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
			System.out.println("添加好友 OK!");
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "添加成功", "系统信息",
					JOptionPane.INFORMATION_MESSAGE);
			node.addchild(new FriTreeNode(new USER(qq)));
		}
	}

	//User上线
	public void Userlog(String user) throws SQLException {
		String sql = "UPDATE info SET state=? WHERE nickname=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1,"在线");
		preparedStatement.setString(2,user);
		preparedStatement.executeUpdate();
	}

	//User下线
	public void UserExit(String user) throws SQLException {
		String sql = "UPDATE info SET state=? WHERE nickname=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1,"离线");
		preparedStatement.setString(2,user);
		preparedStatement.executeUpdate();
	}

	//user是否在线
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
