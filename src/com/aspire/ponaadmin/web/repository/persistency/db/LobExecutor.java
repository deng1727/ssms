package com.aspire.ponaadmin.web.repository.persistency.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.db.DB;
import com.aspire.common.db.Executor;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class LobExecutor extends Executor {
	private String tableName;

	private String keyName;

	private String key;

	private List propertiesNames = new ArrayList();

	private List propertiesValues = new ArrayList();

	private boolean insert = false;

	public boolean isInsert() {
		return insert;
	}

	public void setInsert(boolean insert) {
		this.insert = insert;
	}

	public LobExecutor(String table, String keyName, String key) {
		this.tableName = table;
		this.keyName = keyName;
		this.key = key;
	}

	public String getText(int i) {
		return propertiesValues.get(i) == null ? "" : propertiesValues.get(i)
				.toString();
	}

	public String getColumnName(int i) {
		return this.propertiesNames.get(i).toString();
	}

	public void addPropertyName(String pName, String value) {
		this.propertiesNames.add(pName);
		if (value == null) {
			value = "";
		}
		propertiesValues.add(value);
	}

	public void addPropertyName(String pName, byte[] value) {
		this.propertiesNames.add(pName);
		propertiesValues.add(value);
	}

	public String getQueryLobSql() {
		StringBuffer sb = new StringBuffer("select ");
		for (int i = 0; i < this.propertiesNames.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(propertiesNames.get(i).toString());
		}
		sb.append(" from ").append(this.tableName).append(" where ").append(
				this.keyName).append("=?").append(" for update");
		logger.debug("getQueryLobSql sql : " + sb.toString());
		return sb.toString();
	}

	private static JLogger logger = LoggerFactory.getLogger(LobExecutor.class);

	public String getUpdateLobSql() {
		StringBuffer sb = new StringBuffer();
		sb.append(" update ").append(this.tableName).append(" set ");
		for (int i = 0; i < this.propertiesNames.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(propertiesNames.get(i).toString()).append("=?");
		}
		sb.append(" where ").append(this.keyName).append("=?");
		logger.debug("getUpdateLobSql sql : " + sb.toString());
		return sb.toString();
	}

	public void initList(List rowList, HashMap changeProperties) {
		for (int j = 0; j < rowList.size(); j++) {
			RowCFG row = (RowCFG) rowList.get(j);
			if (PConstants.ROW_TYPE_TEXT.equals(row.getType())) {
				addPropertyName(row.getName(), changeProperties.get(row
						.getClassField()) == null ? "" : changeProperties.get(
						row.getClassField()).toString());
			}
		}
	}

	public String getEmptyClobSql(List colNames) {
		StringBuffer sb = new StringBuffer();
		sb.append(" update ").append(this.tableName).append(" set ");
		for (int i = 0; i < colNames.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(colNames.get(i).toString()).append("=empty_clob()");
		}
		sb.append(" where ").append(this.keyName).append("=?");
		logger.debug(" getEmptyClobSql sql : " + sb.toString());
		return sb.toString();

	}

	public String getEmptyClobSql() {
		StringBuffer sb = new StringBuffer();
		sb.append(" update ").append(this.tableName).append(" set ");
		for (int i = 0; i < this.propertiesNames.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(propertiesNames.get(i).toString())
					.append("=empty_clob()");
		}
		sb.append(" where ").append(this.keyName).append("=?");
		logger.debug(" getEmptyClobSql_all sql : " + sb.toString());
		return sb.toString();

	}

	public String getKey() {
		return key;
	}

	public String getKeyName() {
		return keyName;
	}

	public String getTableName() {
		return tableName;
	}

}
