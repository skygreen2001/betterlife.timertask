package com.betterlife.timertask.dao.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 获取Mysql数据库信息
 * 
 * @author skygreen2001@gmail.com
 * 
 */
public class DbInfoMysql {
	static String ShowTables = "SHOW TABLES";
	static String ShowTableStatus = "show table status";
	static String ShowFields = "SHOW FULL FIELDS IN %s";

	public JdbcTemplate jdbcTemplate;

	/**
	 * @param jdbcTemplate
	 *            the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 返回数据库所有的表列表.
	 * 
	 * @return List
	 */
	public List<String> tableList() {
		List<String> result = new ArrayList<String>();
		List<Map<String, Object>> tables = this.jdbcTemplate
				.queryForList(ShowTables);
		for (Map<String, Object> map : tables) {
			for (Object tablename : map.values()) {
				result.add((String) tablename);
			}
		}
		return result;
	}

	/**
	 * 返回数据库表信息列表
	 * 
	 * @return Map 数据库表信息列表
	 */
	public Map<String, Map<String, String>> tableInfoList() {
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();

		List<Map<String, Object>> tables = this.jdbcTemplate
				.queryForList(ShowTableStatus);
		String tablename;
		Map<String, String> element;
		for (Map<String, Object> map : tables) {
			tablename = (String) map.get("Name");
			element = new HashMap<String, String>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				Object value = entry.getValue();
				if (value != null) {
					String type = value.getClass().getName();
					if (type.contains("String")) {
						element.put(entry.getKey(), (String) entry.getValue());
					}
				}
			}
			result.put(tablename, element);
		}
		return result;
	}

	/**
	 * 获取表所有的列信息
	 * 
	 * @param String
	 *            table 表名
	 */
	public Map<String, Map<String, String>> fieldInfoList(String tablename) {
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		
		List<Map<String, Object>> fields = this.jdbcTemplate.queryForList(String.format(ShowFields, tablename));
		String fieldname;
		Map<String, String> element;
		for (Map<String, Object> map : fields) {
			fieldname = (String) map.get("Field");
			element = new HashMap<String, String>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				Object value = entry.getValue();
				if (value != null) {
					String type = value.getClass().getName();
					if (type.contains("String")) {
						element.put(entry.getKey(), (String) entry.getValue());
					}
				}
			}
			result.put(fieldname, element);
		}
		
		
		return result;
	}

}
