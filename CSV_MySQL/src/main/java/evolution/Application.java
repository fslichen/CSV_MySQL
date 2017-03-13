package evolution;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import evolution.service.CSV2SQL;

public class Application {
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		CSV2SQL c = new CSV2SQL();
		c.run(new File("D:/Buffer/cool.csv"), "any_table", Integer.class, String.class, String.class, Integer.class, String.class);
	}
}
