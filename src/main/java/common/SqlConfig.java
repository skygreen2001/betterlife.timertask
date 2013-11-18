package common;

import java.util.Map;

public class SqlConfig 
{
	/**
	 * 默认mysql sql脚本
	 */
	private Map<String, String> maps;
	/**
	 * sqlserver sql脚本
	 */
	private Map<String, String> sqlserver_maps;
	
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
