package evolution;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import evolution.service.CSV2SQL;
import evolution.util.JDBC;

public class Application {
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		CSV2SQL csv2Sql = new CSV2SQL();
		String[] columnTypies = JDBC.getProperty("columns", String.class).replaceAll(" ", "").split(",");
		csv2Sql.run(new File(JDBC.getProperty("file", String.class)), JDBC.getProperty("table", String.class), JDBC.getColumnClasses(columnTypies));
	}
}
