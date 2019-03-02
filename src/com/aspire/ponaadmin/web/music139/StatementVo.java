package com.aspire.ponaadmin.web.music139;

public class StatementVo {
	String sql;

	Object[] params;

	public StatementVo(String sql, Object[] params) {
		this.sql = sql;
		this.params = params;
	}

	public StatementVo(String sql) {
		this.sql = sql;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
