package com.fire.dialect;

// TODO: Auto-generated Javadoc
/**
 * 数据库方言接口.
 */
public abstract class Dialect {

	/**
	 * The Enum Type.
	 */
	public static enum Type {

		/** The ORACLE. */
		ORACLE {
			public String getValue() {
				return "oracle";
			}
		},

		/** The MYSQL. */
		MYSQL {
			public String getValue() {
				return "mysql";
			}
		},

		/** The SQLSERVER. */
		SQLSERVER {
			public String getValue() {
				return "sqlserver";
			}
		};

		/**
		 * Gets the value.
		 * 
		 * @return the value
		 */
		public abstract String getValue();
	}

	/**
	 * 获取分页SQL.
	 * 
	 * @param sql
	 *            原始查询SQL
	 * @param skipResults
	 *            原始记录索引(从零开始计算)
	 * @param maxResults
	 *            每页记录大小
	 * @return 返回数据库相关的分页查询SQL语句
	 */
	public abstract String getPageSql(String sql, int skipResults, int maxResults);
}
