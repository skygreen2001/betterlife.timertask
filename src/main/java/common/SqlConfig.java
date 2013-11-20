package common;

import java.util.Map;

public class SqlConfig 
{
	/**
	 * 是否关闭Sql Server相关应用
	 */
	private Boolean isCloseSqlserver;
	/**
	 * 是否关闭Mysql相关应用
	 */
	private Boolean isCloseMysql;
	/**
	 * 默认mysql sql脚本
	 */
	private Map<String, String> maps;
	/**
	 * sqlserver sql脚本
	 */
	private Map<String, String> sqlserver_maps;
	
	/**
	 * @return the isCloseMysql
	 */
	public Boolean getCloseMysql() {
		return isCloseMysql;
	}

	/**
	 * @param isCloseMysql the isCloseMysql to set
	 */
	public void setCloseMysql(Boolean isCloseMysql) {
		this.isCloseMysql = isCloseMysql;
		Gc.isCloseMysql=this.isCloseMysql;
	}

	/**
	 * @return the isCloseSqlserver
	 */
	public Boolean getCloseSqlserver() {
		return isCloseSqlserver;
	}

	/**
	 * @param isCloseSqlserver the isCloseSqlserver to set
	 */
	public void setCloseSqlserver(Boolean isCloseSqlserver) {
		this.isCloseSqlserver = isCloseSqlserver;
		Gc.isCloseSqlserver=this.isCloseSqlserver;
	}

	/**
	 * @return the maps
	 */
	public Map<String, String> getMaps() {
		return maps;
	}

	/**
	 * @param maps the maps to set
	 */
	public void setMaps(Map<String, String> maps) {
		this.maps = maps;
	}

	/**
	 * @return the sqlserver_maps
	 */
	public Map<String, String> getSqlserver_maps() {
		return sqlserver_maps;
	}

	/**
	 * @param sqlserver_maps the sqlserver_maps to set
	 */
	public void setSqlserver_maps(Map<String, String> sqlserver_maps) {
		this.sqlserver_maps = sqlserver_maps;
	}
	

}
