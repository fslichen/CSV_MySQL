package evolution.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import evolution.Application;

public class JDBC {
	private Connection connection;
	private Statement statement;
	private static final Set<String> updateKeyWords = new HashSet<>();
	
	public JDBC() {
		updateKeyWords.add("delete");
		updateKeyWords.add("update");
		updateKeyWords.add("insert");
		updateKeyWords.add("create");
		updateKeyWords.add("alter");
		updateKeyWords.add("drop");
		try {
			InputStream inputStream = Application.class.getResourceAsStream("/jdbc.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			String url = (String) properties.get("url");
			String username = (String) properties.get("username");
			String password = (String) properties.get("password");
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(url, username, password);
			this.connection.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public Statement getStatement() {
		return this.statement;
	}
	
	public void run(String sql) {
		try {
			System.out.println(sql);
			for (String updateKeyWord : updateKeyWords) {
				if (sql.contains(updateKeyWord) || sql.contains(updateKeyWord.toUpperCase())) {
					this.connection.createStatement().executeUpdate(sql);
					return;
				}
			}
			this.connection.createStatement().executeQuery(sql);
			this.connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void batch(String sql) {
		try {
			System.out.println(sql);
			if (this.statement == null) {
				this.statement = this.connection.createStatement();
			}
			this.statement.addBatch(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void runAll() {
		try {
			if (this.statement == null) {
				return;
			}
			this.statement.executeBatch();
			this.connection.commit();
			this.statement = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
