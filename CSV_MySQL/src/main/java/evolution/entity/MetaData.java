package evolution.entity;

import lombok.Data;

@Data
public class MetaData {
	private String tableName;
	private String[] columnNames;
	private Class<?>[] columnClasses;
	
	public MetaData(String tableName, String[] columnNames, Class<?>... columnClasses) {
		this.tableName = tableName;
		this.columnNames = columnNames;
		this.columnClasses = columnClasses;
	}
	
	public Class<?> getClass(int i) {
		return this.columnClasses[i];
	}
	
	public String createSql() {
		StringBuilder sql = new StringBuilder("create table " + this.tableName + "(");
		int length = columnNames.length;
		for (int i = 0; i < length; i++) {
			String key = columnNames[i];
			sql.append(key + " ");
			String className = columnClasses[i].getSimpleName();
			if ("String".equals(className)) {
				sql.append("varchar(100) ");
			} else if ("Double".equals(className)) {
				sql.append("double ");
			} else if ("Integer".equals(className)) {
				sql.append("int ");
			}
			if ("id".equals(key)) {
				sql.append("primary key auto_increment ");
			}
			sql.append(",");
		}
		return (sql.substring(0, sql.length() - 1) + ")").replace(" ,", ", ").replace(" )", ")");
	}
}
