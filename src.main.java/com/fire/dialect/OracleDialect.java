package com.fire.dialect;

/**
 * The Class OracleDialect.
 */
public class OracleDialect extends Dialect {

	public String getPageSql(String sql, int offset, int limit) {

		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);

		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");

		pagingSelect.append(sql);

		pagingSelect.append(" ) row_ where rownum <= ");
		pagingSelect.append(limit + offset);
		pagingSelect.append(" ) where rownum_ >");
		pagingSelect.append(offset);
		if (isForUpdate) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}

}
