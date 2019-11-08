package au.com.gsn.callback.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TableClassName {
	
	@JsonProperty("tableName")
	private String tableName;
	@JsonProperty("className")
	private String className;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

}
