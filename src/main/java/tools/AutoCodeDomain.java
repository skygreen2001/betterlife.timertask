package tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;

import org.apache.commons.lang.WordUtils;

import common.log.LogMe;

import dao.DaoManager;

/**
 * 自动生成代码:实体类
 * 
 * @author skygreen2001@gmail.com
 * 
 */
public class AutoCodeDomain {
	private static String modelPath;

	public static void main(String[] args) throws Exception {
		modelPath = System.getProperty("user.dir") + File.separator + "model"
				+ File.separator + "domain" + File.separator;
		 mysql_domain();
		sqlserver_domain();
	}

	/**
	 * 生成基于Sqlserver数据库的实体类
	 */
	private static void sqlserver_domain() {
		Map<String, Map<String, String>> map = DaoManager.dbinfoSqlserver()
				.tableInfoList();
		String package_name, tablename, table_comment, table_comment_name, class_name, field_spec, instance_name;
		Map<String, String> element;
		String modelFileDir;
		FileWriter fw;
		BufferedWriter bw;
		for (Entry<String, Map<String, String>> entry : map.entrySet()) {
			tablename = entry.getKey();
			class_name =  "S"+AutoCodeSqlserver.class_name(tablename);
			package_name = AutoCodeSqlserver.package_name(tablename);
			field_spec = AutoCodeSqlserver.field_spec(tablename, class_name);
			instance_name = AutoCodeSqlserver.instance_name(tablename);
			element = entry.getValue();
			table_comment = element.get("Comment");
			table_comment_name = AutoCodeSqlserver
					.table_comment_name(table_comment);
			table_comment = AutoCodeSqlserver.table_comment(table_comment);
			String modelContent = DomainModel.domainModel_sqlserver(
					package_name, tablename, table_comment, table_comment_name,
					class_name, field_spec, instance_name);
			// System.out.println(modelContent);
			try {
				modelFileDir = modelPath
						+ "sqlserver"
						+ File.separator;
				File currentModeDir = new File(modelFileDir);
				if (!currentModeDir.exists())
					currentModeDir.mkdirs();
				File currentModelFile = new File(modelFileDir,class_name
						+ ".java");
				if (!currentModelFile.exists()) {
					currentModelFile.createNewFile();
				}
				fw = new FileWriter(currentModelFile.getAbsoluteFile());
				bw = new BufferedWriter(fw);
				bw.write(modelContent);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成基于Mysql数据库的实体类
	 */
	private static void mysql_domain() {
		Map<String, Map<String, String>> map = DaoManager.dbinfoMysql()
				.tableInfoList();
		String package_name, tablename, table_comment, table_comment_name, class_name, field_spec, instance_name;
		Map<String, String> element;
		String modelFileDir;
		FileWriter fw;
		BufferedWriter bw;
		for (Entry<String, Map<String, String>> entry : map.entrySet()) {
			tablename = entry.getKey();
			class_name = AutoCodeMysql.class_name(tablename);
			package_name = AutoCodeMysql.package_name(tablename);
			field_spec = AutoCodeMysql.field_spec(tablename, class_name);
			instance_name = AutoCodeMysql.instance_name(tablename);
			element = entry.getValue();
			table_comment = element.get("Comment");
			table_comment_name = AutoCodeMysql
					.table_comment_name(table_comment);
			table_comment = AutoCodeMysql.table_comment(table_comment);
			String modelContent = DomainModel.domainModel_mysql(package_name,
					tablename, table_comment, table_comment_name, class_name,
					field_spec, instance_name);
			// System.out.println(modelContent);
			try {
				modelFileDir = modelPath
						+ "mysql"
						+ File.separator
						+ package_name.replaceAll("\\.",
								Matcher.quoteReplacement(File.separator));
				File currentModeDir = new File(modelFileDir);
				if (!currentModeDir.exists())
					currentModeDir.mkdirs();
				File currentModelFile = new File(modelFileDir, class_name
						+ ".java");
				if (!currentModelFile.exists()) {
					currentModelFile.createNewFile();
				}
				fw = new FileWriter(currentModelFile.getAbsoluteFile());
				bw = new BufferedWriter(fw);
				bw.write(modelContent);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 自动生成代码:基于Mysql数据库
	 * 
	 * @author skygreen2001@gmail.com
	 * 
	 */
	private static class AutoCodeMysql {
		/**
		 * 获取所在的包名
		 * 
		 * @param tablename
		 * @return
		 */
		private static String package_name(String tablename) {
			String[] names = tablename.split("_");
			if ((names != null) && (names.length > 0)) {
				String package_name = "";				
				if (names.length > 1) {
					for (int i = 1; i < names.length - 1; i++) {
						if (names[i].equals("re"))
							names[i] = "relation";
						package_name += names[i] + ".";
					}

					package_name = package_name.substring(0,
							package_name.length() - 1);
				} else {
					package_name = tablename;
				}
				return package_name;
			} else {
				return tablename;
			}

		}

		/**
		 * 获取列规格字符串
		 * 
		 * @param tablename
		 *            表名
		 * @param class_name
		 *            类名
		 * @return
		 */
		private static String field_spec(String tablename, String class_name) {
			String result = "";
			Map<String, Map<String, String>> fields;
			Map<String, String> element;
			fields = DaoManager.dbinfoMysql().fieldInfoList(tablename);
			String column_name, column_comment_name, uccolumn_name, id_annotation = "", column_comment = "", type = "", setgetter = "";
            String id_declare_str="",id_str="",commitTimeStr="",updateTimeStr="",tmpStr,tmpDeclareStr;
            
			for (Entry<String, Map<String, String>> entry : fields.entrySet()) {
				column_name = entry.getKey();
				element = entry.getValue();
				type = column_type(element.get("Type"));
				if (column_name.toUpperCase().equals(class_name.toUpperCase() + "_ID")) {
					column_comment=element.get("Comment");
					if(column_comment==null)column_comment=column_name;
					column_comment_name = column_comment_name(column_comment);
					column_comment = column_comment(column_comment);
					type = "Long";
					id_annotation = "	@Id\r\n"
							+ "	@GeneratedValue(strategy = GenerationType.IDENTITY)\r\n";

				} else {
					id_annotation = "";
					column_comment=element.get("Comment");
					if(column_comment==null)column_comment=column_name;
					column_comment_name = column_comment_name(column_comment);
					column_comment = column_comment(column_comment);
				}
				if ((column_comment_name==null)||(column_comment_name.trim().length()<=0))LogMe.debug(column_name+":无注释");
				uccolumn_name = WordUtils.capitalize(column_name);

				if (column_name.toUpperCase().equals("COMMITTIME")) {
					commitTimeStr = "	/**\r\n" + column_comment + "\r\n"
							+ "	 * @return " + column_name + ":"
							+ column_comment_name + "\r\n" + "	 */\r\n"
							+ "	public " + type + " get" + uccolumn_name
							+ "() {\r\n" + "		return (" + type + ")"
							+ column_name + ";\r\n" + "	}\r\n\r\n";
				} else if (column_name.toUpperCase().equals("UPDATETIME")) {
					updateTimeStr = "	/**\r\n" + column_comment + "\r\n"
							+ "	 * @return " + column_name + ":"
							+ column_comment_name + "\r\n" + "	 */\r\n\r\n"
							+ "	public " + type + " getUpdateTime() {\r\n"
							+ "		return (" + type + ")updateTime;\r\n"
							+ "	}\r\n\r\n";
				} else {
					tmpDeclareStr = "	private " + type + " " + column_name + ";\r\n";
					tmpStr = "	/**\r\n" + column_comment + "\r\n"
							+ "	 * @return " + column_name + ":"
							+ column_comment_name + "\r\n" + "	 */\r\n"
							+ id_annotation + "	public " + type + " get"
							+ uccolumn_name + "() {\r\n" + "		return "
							+ column_name + ";\r\n" + "	}\r\n\r\n" + "	/**\r\n"
							+ column_comment + "\r\n" + "	 * @param "
							+ column_name + ":" + column_comment_name + "\r\n"
							+ "	 */\r\n" + "	public void set" + uccolumn_name
							+ "(" + type + " " + column_name + ") {\r\n"
							+ "		this." + column_name + " = " + column_name
							+ ";\r\n" + "	}\r\n\r\n";

					if (column_name.toUpperCase().equals(class_name.toUpperCase() + "_ID")) {
						id_str=tmpStr;
						id_declare_str=tmpDeclareStr;
					}else{
						setgetter+=tmpStr;
						result+=tmpDeclareStr;
					}
				}
			}
			result = "\r\n"+id_declare_str+result +id_str+ setgetter+commitTimeStr+updateTimeStr;
			result = result.substring(0, result.length() - 2);
			return result;
		}

		/**
		 * 获取表注释第一行关键词说明
		 * 
		 * @param table_comment
		 * @return
		 */
		private static String table_comment_name(String table_comment) {
			table_comment = table_comment.replaceAll("关系表", "");
			String[] table_comment_r = table_comment.split("\\n|\\r");
			table_comment = table_comment_r[0];
			return table_comment;
		}

		/**
		 * 获取表说明
		 * 
		 * @param table_comment
		 * @return
		 */
		private static String table_comment(String table_comment) {
			table_comment = table_comment.replaceAll("关系表", "关系");
			String[] table_comment_r = table_comment.split("\\n|\\r");
			table_comment = "";
			for (String comment : table_comment_r) {
				if ((comment != null) && (comment.trim().length() > 0))
					table_comment += " * " + comment + "\r\n";
			}
			if (table_comment.contains("\r\n")) {
				table_comment = table_comment.substring(0,
						table_comment.length() - 2);
			}
			return table_comment;
		}

		/**
		 * 获取列说明
		 * 
		 * @param column_comment
		 * @return
		 */
		private static String column_comment_name(String column_comment) {
			if ((column_comment!=null)&&(column_comment.trim().length()>0)){
				String[] column_comment_r = column_comment.split("\\n|\\r");
				column_comment = column_comment_r[0];
			}
			return column_comment;
		}

		/**
		 * 获取列说明
		 * 
		 * @param column_comment
		 * @return
		 */
		private static String column_comment(String column_comment) {
			if ((column_comment!=null)&&(column_comment.trim().length()>0)){
				String[] column_comment_r = column_comment.split("\\n|\\r");
				column_comment = "";
				for (String comment : column_comment_r) {
					if ((comment != null) && (comment.trim().length() > 0))
						column_comment += "	 * " + comment + "\r\n";
				}
	
				if (column_comment.contains("\r\n")) {
					column_comment = column_comment.substring(0,
							column_comment.length() - 2);
				}
			}
			return column_comment;
		}

		/**
		 * 根据表类型获取类类型
		 * 
		 * @param table_type
		 * @return
		 */
		private static String column_type(String table_type) {
			if (table_type.contains("(")) {
				String[] types = table_type.split("[()]");
				table_type = types[0];
			}
			if (table_type.equals("integer")) {
				return "Long";
			} else if (table_type.equals("bigint")) {
				return "java.math.BigInteger";
			} else if (table_type.equals("float")) {
				return "Float";
			} else if (table_type.equals("decimal")) {
				return "java.math.BigDecimal";
			} else if (table_type.equals("double")) {
				return "Double";
			} else if ((table_type.equals("tinyint"))
					|| (table_type.equals("smallint"))
					|| (table_type.equals("mediumint"))
					|| (table_type.equals("int"))) {
				return "Integer";
			} else if ((table_type.equals("varchar"))
					|| (table_type.equals("text"))
					|| (table_type.equals("mediumtext"))
					|| (table_type.equals("longtext"))
					|| (table_type.equals("enum"))
					|| (table_type.equals("char"))
					|| (table_type.equals("set"))) {
				return "String";
			} else if (table_type.equals("blob")) {
				return "byte[]";
			} else if (table_type.equals("bit")) {
				return "Boolean";
			} else if ((table_type.equals("date"))
					|| (table_type.equals("year"))) {
				return "java.sql.Date";
			} else if (table_type.equals("time")) {
				return "java.sql.Time";
			} else if ((table_type.equals("datetime"))
					|| (table_type.equals("timestamp"))) {
				return "Timestamp";
			} else {
				return "String";
			}
		}

		/**
		 * 根据表名称获取实体类名称
		 * 
		 * @param tablename
		 *            表名称
		 * @return 类名称
		 */
		private static String class_name(String tablename) {
			String[] names = tablename.split("_");
			if ((names != null) && (names.length > 0)) {
				String class_name = names[names.length - 1];
				class_name = WordUtils.capitalize(class_name);
				return class_name;
			} else {
				return tablename;
			}
		}

		private static String instance_name(String tablename) {
			String[] names = tablename.split("_");
			if ((names != null) && (names.length > 0)) {
				String class_name = names[names.length - 1];
				class_name = class_name.toLowerCase();
				return class_name;
			} else {
				return tablename;
			}

		}
	}

	/**
	 * 自动生成代码:基于Sqlserver数据库
	 * 
	 * @author skygreen2001@gmail.com
	 * 
	 */
	private static class AutoCodeSqlserver {
		/**
		 * 获取所在的包名
		 * 
		 * @param tablename
		 * @return
		 */
		private static String package_name(String tablename) {
			String[] names = tablename.split("_");
			if ((names != null) && (names.length > 0)) {
				String package_name = "";
				if (names.length > 1) {
					for (int i = 1; i < names.length - 1; i++) {
						if (names[i].equals("re"))
							names[i] = "relation";
						package_name += names[i] + ".";
					}

					package_name = package_name.substring(0,
							package_name.length() - 1);
				} else {
					package_name = tablename;
				}
				return package_name;
			} else {
				return tablename;
			}
		}

		/**
		 * 获取列规格字符串
		 * 
		 * @param tablename
		 *            表名
		 * @param class_name
		 *            类名
		 * @return
		 */
		private static String field_spec(String tablename, String class_name) {
			String result = "";
			Map<String, Map<String, String>> fields;
			Map<String, String> element;
			fields = DaoManager.dbinfoSqlserver().fieldInfoList(tablename);
			String column_name, column_comment_name, uccolumn_name, id_annotation = "", column_comment = "", type = "", setgetter = "";
            String id_declare_str="",id_str="",commitTimeStr="",updateTimeStr="",tmpStr,tmpDeclareStr;
			for (Entry<String, Map<String, String>> entry : fields.entrySet()) {
				column_name = entry.getKey();
				element = entry.getValue();
				type = column_type(element.get("Type"));

				if (column_name.toUpperCase().equals("ID")) {
					column_comment=element.get("Comment");
					if(column_comment==null)column_comment=column_name;
					column_comment_name = column_comment_name(column_comment);
					column_comment = column_comment(column_comment);
					type = "String";
					id_annotation = "	@Id\r\n"+ 
									"	@GenericGenerator(name = \"uuid\", strategy = \"uuid2\")\r\n"+ 
									"	@GeneratedValue(generator = \"uuid\")\r\n"+ 
									"	@Column(name = \"ID\", unique = true)\r\n";

				} else {
					id_annotation = "";
					column_comment=element.get("Comment");
					if(column_comment==null)column_comment=column_name;
					column_comment_name = column_comment_name(column_comment);
					column_comment = column_comment(column_comment);
				}
				if ((column_comment_name==null)||(column_comment_name.trim().length()<=0))LogMe.debug(column_name+":无注释");
				uccolumn_name = WordUtils.capitalize(column_name);

				if (column_name.toUpperCase().equals("COMMITTIME")) {
					commitTimeStr = "	/**\r\n" + column_comment + "\r\n"
							+ "	 * @return " + column_name + ":"
							+ column_comment_name + "\r\n" + "	 */\r\n"
							+ "	public " + type + " get" + uccolumn_name
							+ "() {\r\n" + "		return (" + type + ") this.commitTime;\r\n" + "	}\r\n\r\n";
				} else if (column_name.toUpperCase().equals("UPDATETIME")) {
					updateTimeStr = "	/**\r\n" + column_comment + "\r\n"
							+ "	 * @return " + column_name + ":"
							+ column_comment_name + "\r\n" + "	 */\r\n\r\n"
							+ "	public " + type + " getUpdateTime() {\r\n"
							+ "		return updateTime;\r\n" + "	}\r\n\r\n";
				} else {
					if (!column_name.toUpperCase().equals("ID"))column_name=WordUtils.uncapitalize(column_name);
					tmpDeclareStr="	private " + type + " " + column_name + ";\r\n";
					tmpStr  = "	/**\r\n" + column_comment + "\r\n"
							+ "	 * @return " + column_name + ":"
							+ column_comment_name + "\r\n" + "	 */\r\n"
							+ id_annotation + "	public " + type + " get"
							+ uccolumn_name + "() {\r\n" + "		return "
							+ column_name + ";\r\n" + "	}\r\n\r\n" + "	/**\r\n"
							+ column_comment + "\r\n" + "	 * @param "
							+ column_name + ":" + column_comment_name + "\r\n"
							+ "	 */\r\n" + "	public void set" + uccolumn_name
							+ "(" + type + " " + column_name + ") {\r\n"
							+ "		this." + column_name + " = " + column_name
							+ ";\r\n" + "	}\r\n\r\n";
					if (column_name.toUpperCase().equals("ID")) {
						id_str=tmpStr;
						id_declare_str=tmpDeclareStr;
					}else{
						setgetter+=tmpStr;
						result += tmpDeclareStr;
					}
				}
			}
			result = "\r\n"+id_declare_str+result +id_str+ setgetter+commitTimeStr+updateTimeStr;
			result = result.substring(0, result.length() - 2);
			return result;
		}

		/**
		 * 获取表注释第一行关键词说明
		 * 
		 * @param table_comment
		 * @return
		 */
		private static String table_comment_name(String table_comment) {
			table_comment = table_comment.replaceAll("关系表", "");
			String[] table_comment_r = table_comment.split("\\n|\\r");
			table_comment = table_comment_r[0];
			return table_comment;
		}

		/**
		 * 获取表说明
		 * 
		 * @param table_comment
		 * @return
		 */
		private static String table_comment(String table_comment) {
			table_comment = table_comment.replaceAll("关系表", "关系");
			String[] table_comment_r = table_comment.split("\\n|\\r");
			table_comment = "";
			for (String comment : table_comment_r) {
				if ((comment != null) && (comment.trim().length() > 0))
					table_comment += " * " + comment + "\r\n";
			}
			if (table_comment.contains("\r\n")) {
				table_comment = table_comment.substring(0,
						table_comment.length() - 2);
			}
			return table_comment;
		}

		/**
		 * 获取列说明
		 * 
		 * @param column_comment
		 * @return
		 */
		private static String column_comment_name(String column_comment) {
			if ((column_comment!=null)&&(column_comment.trim().length()>0)){
				String[] column_comment_r = column_comment.split("\\n|\\r");
				column_comment = column_comment_r[0];
			}
			return column_comment;
		}

		/**
		 * 获取列说明
		 * 
		 * @param column_comment
		 * @return
		 */
		private static String column_comment(String column_comment) {
			if ((column_comment!=null)&&(column_comment.trim().length()>0)){
				String[] column_comment_r = column_comment.split("\\n|\\r");
				column_comment = "";
				for (String comment : column_comment_r) {
					if ((comment != null) && (comment.trim().length() > 0))
						column_comment += "	 * " + comment + "\r\n";
				}
	
				if (column_comment.contains("\r\n")) {
					column_comment = column_comment.substring(0,
							column_comment.length() - 2);
				}
			}
			return column_comment;
		}

		/**
		 * 根据表类型获取类类型
		 * 
		 * @param table_type
		 * @return
		 */
		private static String column_type(String table_type) {
			if (table_type.contains("(")) {
				String[] types = table_type.split("[()]");
				table_type = types[0];
			}
			if (table_type.equals("bigint")) {
				return "java.math.BigInteger";
			} else if (table_type.equals("float")) {
				return "Float";
			} else if (table_type.equals("decimal")) {
				return "java.math.BigDecimal";
			} else if (table_type.equals("double")) {
				return "Double";
			} else if ((table_type.equals("tinyint"))
					|| (table_type.equals("smallint"))
					|| (table_type.equals("int"))) {
				return "Integer";
			} else if ((table_type.equals("varchar"))
					|| (table_type.equals("nvarchar"))
					|| (table_type.equals("uniqueidentifier"))
					|| (table_type.equals("text"))
					|| (table_type.equals("ntext"))
					|| (table_type.equals("char"))) {
				return "String";
			} else if (table_type.equals("bit")) {
				return "Boolean";
			} else if (table_type.equals("date")) {
				return "java.sql.Date";
			} else if (table_type.equals("time")) {
				return "java.sql.Time";
			} else if ((table_type.equals("datetime"))
					|| (table_type.equals("timestamp"))) {
				return "Timestamp";
			} else {
				return "String";
			}
		}

		/**
		 * 根据表名称获取实体类名称
		 * 
		 * @param tablename
		 *            表名称
		 * @return 类名称
		 */
		private static String class_name(String tablename) {
			String[] names = tablename.split("_");
			if ((names != null) && (names.length > 0)) {
				String class_name = names[names.length - 1];
				class_name = WordUtils.capitalize(class_name);
				return class_name;
			} else {
				return tablename;
			}
		}

		private static String instance_name(String tablename) {
			String[] names = tablename.split("_");
			if ((names != null) && (names.length > 0)) {
				String class_name = names[names.length - 1];
				class_name = class_name.toLowerCase();
				return class_name;
			} else {
				return tablename;
			}

		}

	}
}
