package com.betterlife.timertask.tasks.run;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.betterlife.timertask.common.Gc;
import com.betterlife.timertask.common.log.LogMe;
import com.betterlife.timertask.dao.DaoManager;

/**
 * 显示数据库表列信息
 * 
 * @author skygreen2001@gmail.com
 * 
 */
@EnableScheduling
public class DbInfoTasks {

	@Scheduled(initialDelay = 1000, fixedRate = 60000)
	public void dbInfoMysql() {
		if (Gc.isCloseMysql)return;
		List<String> list = DaoManager.dbinfoMysql().tableList();
		for (String tablename : list) {
			LogMe.debug(tablename);
		}

		Map<String, Map<String, String>> map = DaoManager.dbinfoMysql()
				.tableInfoList();
		Map<String, String> element;
		for (Entry<String, Map<String, String>> entry : map.entrySet()) {
			LogMe.debug("表名称:" + entry.getKey());
			element = entry.getValue();

			for (Entry<String, String> tableInfo : element.entrySet()) {

				LogMe.debug("----" + tableInfo.getKey() + ":"
						+ tableInfo.getValue() + "-------");
			}
		}

		Map<String, Map<String, String>> fields;
		Map<String, String> elementa;
		for (String tablename : list) {
			LogMe.debug("[表名称:" + tablename + "]");
			fields = DaoManager.dbinfoMysql().fieldInfoList(tablename);
			for (Entry<String, Map<String, String>> entry : fields.entrySet()) {
				LogMe.debug("**列名称:" + entry.getKey());
				elementa = entry.getValue();

				for (Entry<String, String> tableInfo : elementa.entrySet()) {

					LogMe.debug("----" + tableInfo.getKey() + ":"
							+ tableInfo.getValue() + "-------");
				}
			}
		}
	}

	@Scheduled(initialDelay = 2000, fixedRate = 70000)
	public void dbInfoSqlserver() {
		if (Gc.isCloseSqlserver)return;
		List<String> list = DaoManager.dbinfoSqlserver().tableList();
		for (String tablename : list) {
			LogMe.debug(tablename);
		}

		Map<String, Map<String, String>> map = DaoManager.dbinfoSqlserver()
				.tableInfoList();
		Map<String, String> element;
		for (Entry<String, Map<String, String>> entry : map.entrySet()) {
			LogMe.debug("表名称:" + entry.getKey());
			element = entry.getValue();

			for (Entry<String, String> tableInfo : element.entrySet()) {

				LogMe.debug("----" + tableInfo.getKey() + ":"
						+ tableInfo.getValue() + "-------");
			}
		}

		Map<String, Map<String, String>> fields;
		Map<String, String> elementa;
		for (String tablename : list) {
			LogMe.debug("[表名称:" + tablename + "]");
			fields = DaoManager.dbinfoSqlserver().fieldInfoList(tablename);
			for (Entry<String, Map<String, String>> entry : fields.entrySet()) {
				LogMe.debug("**列名称:" + entry.getKey());
				elementa = entry.getValue();

				for (Entry<String, String> tableInfo : elementa.entrySet()) {
					LogMe.debug("----" + tableInfo.getKey() + ":"
							+ tableInfo.getValue() + "-------");
				}
			}
		}
	}

}
