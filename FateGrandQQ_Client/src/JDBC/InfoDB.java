package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoDB {
	Connection con;// 声明Connection对象
	PreparedStatement preparedStatement;
	ResultSet rs;// ResultSet类，用来存放获取的结果集

	//构造函数
	public InfoDB() throws ClassNotFoundException, SQLException{
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
	
	//昵称是否已经输入
	public boolean hasNickname(int qq) throws SQLException {
		String sql="select nickname from data where qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, qq);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			if (rs.wasNull()) {
				return false;
			}else {
				return true;
			}
		}
		return false;
	}
	
	public String getSex(int qq) throws SQLException {
		String sql="select sex from data where qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, qq);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			return rs.getString(1);
		}
		return "null";
	}
	
	public String getAge(int qq) throws SQLException {
		String sql="select age from data where qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, qq);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			return rs.getString(1);
		}
		return "null";
	}
	
	public String getMonth(int qq) throws SQLException {
		String sql="select birthdayMonth from data where qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, qq);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			return rs.getString(1);
		}
		return "null";
	}
	
	public String getDay(int qq) throws SQLException {
		String sql="select birthdayDay from data where qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, qq);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			return rs.getString(1);
		}
		return "null";
	}
	public String getComefrom(int qq) throws SQLException {
		String sql="select comefrom from data where qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, qq);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			return rs.getString(1);
		}
		return "null";
	}
	
	public void updateSex(int qq,String sex) throws SQLException {
		String sql="UPDATE data SET sex=? WHERE qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1, sex);
		preparedStatement.setInt(2, qq);
		preparedStatement.executeUpdate();
	}
	
	public void updateAge(int qq,int age) throws SQLException {
		String sql="UPDATE data SET age=? WHERE qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, age);
		preparedStatement.setInt(2, qq);
		preparedStatement.executeUpdate();
	}
	
	public void updateMonth(int qq,int month) throws SQLException {
		String sql="UPDATE data SET birthdayMonth=? WHERE qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, month);
		preparedStatement.setInt(2, qq);
		preparedStatement.executeUpdate();
	}
	
	public void updateDay(int qq,int day) throws SQLException {
		String sql="UPDATE data SET birthdayDay=? WHERE qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, day);
		preparedStatement.setInt(2, qq);
		preparedStatement.executeUpdate();
	}
	public void updateComefrom(int qq,String comefrom) throws SQLException {
		String sql="UPDATE data SET comefrom=? WHERE qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1, comefrom);
		preparedStatement.setInt(2, qq);
		preparedStatement.executeUpdate();
	}
	
	public String getPic(int qq) throws SQLException {
		String sql="select pic from data where qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, qq);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			return rs.getString(1);
		}
		return "null";
	}
	
	public boolean hasPic(int qq) throws SQLException {
		String sql="select pic from data where qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, qq);
		rs = preparedStatement.executeQuery();
		if (rs.next()) {
			if (rs.getString(1)==null) {
				return false;
			}else {
				return true;
			}
		}
		return false;
	}
	public void updatePic(int qq,String picUrl) throws SQLException {
		String sql="UPDATE data SET pic=? WHERE qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1, picUrl);
		preparedStatement.setInt(2, qq);
		preparedStatement.executeUpdate();
	}
	public void updateHead(int qq, String head) throws SQLException {
		String sql="UPDATE info SET head=? WHERE qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1, head);
		preparedStatement.setInt(2, qq);
		preparedStatement.executeUpdate();
	}
	public void updateName(int qq, String name) throws SQLException {
		String sql="UPDATE info SET nickname=? WHERE qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1, name);
		preparedStatement.setInt(2, qq);
		preparedStatement.executeUpdate();
	}
	public int numPhoto(int qq) throws SQLException {
		String sql="select url from photo where qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, qq);
		rs=preparedStatement.executeQuery();
		int i=0;
		while(rs.next()) {
			i++;
		}
		return i;
	}
	public boolean hasPhoto(int qq,String url) throws SQLException {
		String sql="select url from photo where qq=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, qq);
		rs=preparedStatement.executeQuery();
		int i=1;
		while (rs.next()) {
			if (url==rs.getString(i))
				return true;
			i++;
		}
		return false;
	}
	public void updatePhoto(int qq,String url) throws SQLException {
		String sql="insert into photo (qq, url) values(?,?)";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1,qq);
		preparedStatement.setString(2, url);
		preparedStatement.executeUpdate();
	}
	public String[] getPhoto(int qq) throws SQLException {
		String sql="select url from photo where qq=?";
		String[] urls=new String[6];
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1, qq);
		rs=preparedStatement.executeQuery();
		int i=0;
		while(rs.next()&&i<6) {
			urls[i] = rs.getString(1);
			i++;
		}
		return urls;
	}
	public void delPhoto(int qq,String url) throws SQLException {
		String sql="delete from photo where qq=? and url=?";
		preparedStatement=con.prepareStatement(sql);
		preparedStatement.setInt(1,qq);
		preparedStatement.setString(2, url);
		preparedStatement.execute();
	}
}