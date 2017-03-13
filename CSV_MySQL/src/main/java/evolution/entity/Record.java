package evolution.entity;

public class Record {
	public String createSql(String tableName, MetaData metaData, String[] columns) {
		StringBuilder sql = new StringBuilder("insert into " + tableName + " values(");
		int length = columns.length;
		for (int i = 0; i < length; i++) {
			String className = metaData.getClass(i).getSimpleName();
			if ("String".equals(className)) {
				sql.append("'" + columns[i] + "', ");
			} else {
				sql.append(columns[i] + ", ");
			}
		}
		return sql.substring(0, sql.length() - 2) + ")";
	}
}
