package common;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Gc {
	/**
	 * 是否运行定时任务
	 */
	public static boolean isRunTasks = true;
	/**
	 * 是否运行Email
	 */
	public static boolean isRunEmail = false;
	/**
	 * 是否关闭Mysql相关应用
	 */
	public static boolean isCloseMysql;
	/**
	 * 是否关闭Sql Server相关应用
	 */
	public static boolean isCloseSqlserver;
	/**
	 * Name in spring context xml config:dao for mysql
	 */
	public static String CTX_NAME_DAO_MYSQL = "dao";
	/**
	 * Name in spring context xml config:dao for sqlserver
	 */
	public static String CTX_NAME_DAO_SQLSERVER = "daos";
	/**
	 * Name in spring context xml config:database information for mysql
	 */
	public static String CTX_NAME_DBINFO_MYSQL = "info_mysql";
	/**
	 * Name in spring context xml config:database information for mysql
	 */
	public static String CTX_NAME_DBINFO_SQLSERVER = "info_sqlserver";
	/**
	 * Name in spring context xml config:Sql script
	 */
	public static String CTX_NAME_CONFIG_SQL = "sqlscript";

	public static String CTX_NAME_SQLSCRIPT_MYSQL = "sqlscript_mysql";

	public static String CTX_NAME_SQLSCRIPT_SQLSERVER = "sqlscript_sqlserver";

	public static String CTX_NAME_MAIL_SERVICE = "mailService";

	private static Map<String, String> sqlscript;

	private static Map<String, String> sqlscript_sqlserver;

	// spring的上下文环境
	public static ApplicationContext context;

	static {
		// 在类路径下寻找timer.xml文件
		if (Gc.isRunTasks)
			context = new ClassPathXmlApplicationContext(
					"applicationContext.xml");
	}

	/**
	 * 默认mysql sql脚本
	 */
	public static Map<String, String> sqlscript() {
		if (sqlscript == null) {
			SqlConfig sqlscript_i = (SqlConfig) Gc.context
					.getBean(CTX_NAME_CONFIG_SQL);
			sqlscript = sqlscript_i.getMaps();
		}
		return sqlscript;
	}

	/**
	 * sqlserver sql脚本
	 */
	public static Map<String, String> sqlscript_sqlserver() {
		if (sqlscript_sqlserver == null) {
			SqlConfig sqlscript_i = (SqlConfig) Gc.context
					.getBean(CTX_NAME_CONFIG_SQL);
			sqlscript_sqlserver = sqlscript_i.getSqlserver_maps();
		}
		return sqlscript_sqlserver;
	}
}
