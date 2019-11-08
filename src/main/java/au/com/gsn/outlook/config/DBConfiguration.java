package au.com.gsn.outlook.config;

public class DBConfiguration {
	
	private String dialThreshodInMin;
	private String isOracle;
	private String ocsTableColumn;
	private String tableNamePattern;
	private String ocsTableNameExcluded;
	private String overlapNum;
	private String removeOutlookDaysBefore;
	
	public String getDialThreshodInMin() {
		return dialThreshodInMin;
	}
	public void setDialThreshodInMin(String dialThreshodInMin) {
		this.dialThreshodInMin = dialThreshodInMin;
	}
	public String getOcsTableColumn() {
		return ocsTableColumn;
	}
	public void setOcsTableColumn(String ocsTableColumn) {
		this.ocsTableColumn = ocsTableColumn;
	}
	public String getOcsTableNameExcluded() {
		return ocsTableNameExcluded;
	}
	public void setOcsTableNameExcluded(String ocsTableNameExcluded) {
		this.ocsTableNameExcluded = ocsTableNameExcluded;
	}
	public String getTableNamePattern() {
		return tableNamePattern;
	}
	public void setTableNamePattern(String tableNamePattern) {
		this.tableNamePattern = tableNamePattern;
	}
	public String getIsOracle() {
		return isOracle;
	}
	public void setIsOracle(String isOracle) {
		this.isOracle = isOracle;
	}
	public String getOverlapNum() {
		return overlapNum;
	}
	public void setOverlapNum(String overlapNum) {
		this.overlapNum = overlapNum;
	}
	public String getRemoveOutlookDaysBefore() {
		return removeOutlookDaysBefore;
	}
	public void setRemoveOutlookDaysBefore(String removeOutlookDaysBefore) {
		this.removeOutlookDaysBefore = removeOutlookDaysBefore;
	}
	
}
