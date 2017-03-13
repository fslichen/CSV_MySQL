package evolution.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import evolution.entity.MetaData;
import evolution.entity.Record;
import evolution.util.JDBC;

public class CSV2SQL {
	public void run(File file, String tableName, Class<?>... columnClasses) {
		try {
			JDBC jdbc = new JDBC();
			jdbc.run("drop table if exists " + tableName);
			Scanner scanner = new Scanner(file);
			MetaData metaData = new MetaData(tableName, scanner.nextLine().split(","), columnClasses);
			jdbc.run(metaData.createSql());
			while (scanner.hasNextLine()) {
				Record record = new Record();
				jdbc.batch(record.createSql(tableName, metaData, scanner.nextLine().split(",")));
			}
			jdbc.runAll();
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
