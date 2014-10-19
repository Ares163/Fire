package com.fire.dialect;

/**
 * The Class MySql5Dialect.
 */
public class MySql5Dialect extends Dialect {

	public String getPageSql(String sql, int offset, int limit) {
		return sql.replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ") + " limit " + offset + " ," + limit;
	}

}
