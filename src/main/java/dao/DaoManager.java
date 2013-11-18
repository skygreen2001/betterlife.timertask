package dao;

import common.Gc;
import dao.mysql.Dao;
import dao.mysql.DbInfoMysql;
import dao.sqlserver.DaoS;
import dao.sqlserver.DbInfoSqlserver;

public class DaoManager 
{
	private static Dao dao;
	private static DaoS daos;
	private static DbInfoMysql dbinfoMysql;
	private static DbInfoSqlserver dbinfoSqlserver;
	
	public static Dao dao()
	{
		if (dao==null) dao = (Dao) Gc.context.getBean(Gc.CTX_NAME_DAO_MYSQL);
		return dao;
	}
	
	public static DaoS daos()
	{
		if (daos==null) daos = (DaoS) Gc.context.getBean(Gc.CTX_NAME_DAO_SQLSERVER);
		return daos;
	}
	
	public static DbInfoMysql dbinfoMysql()
	{
		if (dbinfoMysql==null) dbinfoMysql = (DbInfoMysql) Gc.context.getBean(Gc.CTX_NAME_DBINFO_MYSQL);
		return dbinfoMysql;
	}

	public static DbInfoSqlserver dbinfoSqlserver()
	{
		if (dbinfoSqlserver==null) dbinfoSqlserver = (DbInfoSqlserver) Gc.context.getBean(Gc.CTX_NAME_DBINFO_SQLSERVER);
		return dbinfoSqlserver;
	}
	

}
