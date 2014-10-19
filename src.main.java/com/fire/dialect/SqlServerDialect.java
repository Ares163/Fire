package com.fire.dialect;

/**
 * The Class SqlServerDialect.
 */
public class SqlServerDialect extends Dialect {

	public String getPageSql(String sql, int skipResults, int maxResults) {
		if (skipResults < 0) {
			skipResults = 0;
		}
		if (maxResults < 0) {
			maxResults = 0;
		}
		StringBuffer pagesql = new StringBuffer();
		if (skipResults / maxResults == 0) {
			pagesql.append("select top ").append(skipResults).append("* from (");
			pagesql.append(sql).append(") as a order by a.id");
		} else {
			pagesql.append("select top ").append(skipResults).append("* from (");
			pagesql.append(sql).append(") as a where a.id not in (");
			pagesql.append("select top ").append(maxResults * (skipResults / maxResults)).append("* from (");
			pagesql.append(sql).append(") as b where b.id) order by a.id");
		}
		return pagesql.toString();
	}

}
