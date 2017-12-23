package object;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import JDBC.connectDB;

public class USER implements Serializable {
	private static final long serialVersionUID = 1L;
	public int qq;
	public String password;
	public String name;
	public String sign;
	public String head;
	public String state;

	
	public USER(int qq) {
		try {
			USER user = new connectDB().get_user(qq);
			this.qq = user.qq;
			this.password = user.password;
			this.name = user.name;
			this.state = user.state;
			this.sign = user.sign;
			this.head = user.head;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public USER(ResultSet rs) {
		try {
			if (rs.next()) {
				qq = rs.getInt(1);
				password = rs.getString(2);
				name = rs.getString(3);
				state = rs.getString(4);
				sign = rs.getString(5);
				head = "src/GUI/img/head/" + rs.getString(6);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
