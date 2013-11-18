package dao;

import java.util.List;
import java.util.Map;

//import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import common.Gc;
/**
 * 执行Sql脚本语句[主要用于多表关联查询]
 * 参考:
 * 	  Spring and Inner class:http://www.javavm.net/spring-and-inner-class/
 * @author 月璞
 *
 */
public class Sqlscript 
{
	public static Mysql mysql;
	
	public static Sqlserver sqlserver;

	static{
		if (mysql==null)mysql=(Mysql)Gc.context.getBean(Gc.CTX_NAME_SQLSCRIPT_MYSQL);
		if (sqlserver==null)sqlserver=(Sqlserver)Gc.context.getBean(Gc.CTX_NAME_SQLSCRIPT_SQLSERVER);	
	}
	
	public static final class Mysql 
	{

//		public SessionFactory sessionFactory;
		public JdbcTemplate jdbcTemplate;
		
//		public void setSessionFactory(SessionFactory sessionFactory) 
//		{ 
//			this.sessionFactory = sessionFactory;
//		}

		/**
		 * @param jdbcTemplate the jdbcTemplate to set
		 */
		public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}
		
		public Boolean execute(String sql)
		{
			int result=this.jdbcTemplate.update(sql);
			if (result>0)
				return true;
			else
				return false;
		}

		public void executeDDL(String sql)
		{
			this.jdbcTemplate.execute(sql);
		}
		
		
		public List<Map<String, Object>> query(String sql)
		{
			return this.jdbcTemplate.queryForList(sql);
		}
		
	}
	
	public static final class Sqlserver
	{
//		public SessionFactory sessionFactory;
		public JdbcTemplate jdbcTemplate;
		
//		public void setSessionFactory(SessionFactory sessionFactory) 
//		{ 
//			this.sessionFactory = sessionFactory;
//		}

		/**
		 * @param jdbcTemplate the jdbcTemplate to set
		 */
		public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}

		public Boolean execute(String sql)
		{
			int result=this.jdbcTemplate.update(sql);
			
			if (result>0)
				return true;
			else
				return false;
		}

		public void executeDDL(String sql)
		{
			this.jdbcTemplate.execute(sql);
		}
		
		public List<Map<String, Object>> query(String sql)
		{
			return this.jdbcTemplate.queryForList(sql);
		}
	}
	

}
