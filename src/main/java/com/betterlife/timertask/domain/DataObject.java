package com.betterlife.timertask.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.betterlife.timertask.dao.DaoManager;
import com.betterlife.timertask.dao.IDao;

public class DataObject 
{
	/**
	 * 标识名称
	 */
	public static String id_name;
	/**
	 * 类名称
	 */
	protected static String class_name;
	
	protected static IDao dao;
	
	/**
	 * 是否Mysql数据库
	 */
	private boolean isMysql;
	/**
	 * 是否更新
	 */
	protected boolean isUpdate;
	/**
	 * 创建时间
	 */
	protected Object commitTime;
	/**
	 * 修改时间
	 */
	protected Timestamp updateTime;
	/**
	 * @param commitTime
	 *            the commitTime to set
	 */
	public void setCommitTime(Object commitTime) {
		this.commitTime = commitTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	private static void init_dao(String className) {
		if (className.contains(".mysql.")) {
			dao = DaoManager.dao();
		} else if (className.contains(".sqlserver.")) {
			dao = DaoManager.daos();
		}
	}

	public static void init() {
		String className = class_name;//Thread.currentThread().getStackTrace()[2].getClassName();
		init_dao(className);
	}
	private void init_i() 
	{
		String className =this.getClass().getName();
		init_dao(className);
		if (className.contains(".mysql.")) {
			this.isMysql=true;
		}else{
			this.isMysql=false;
		}
	}
	
	/**
	 * 保存当前对象
	 * 
	 * @return Serializable 数据对象的唯一标识
	 */
	public Serializable save() {
		init_i();
		if (this.isMysql){
			String todayStr = String.valueOf(System.currentTimeMillis()).substring(0,10); //取日期部分
			this.setCommitTime(Integer.parseInt(todayStr));
		}else{
			this.setCommitTime(new Timestamp(System.currentTimeMillis()));
		}
		this.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return dao.save(this);
	}

	/**
	 * 更新当前对象
	 * 
	 * @return Boolean 是否更新成功
	 */
	public Boolean update() {
		init_i();
		this.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return dao.update(this);
	}

	/**
	 * 保存或更新当前对象
	 * 
	 * @return 是否保存或更新成功
	 */
	public Boolean saveOrUpdate() {
		init_i();
		this.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return dao.saveOrUpdate(this);
	}

	/**
	 * 删除当前对象
	 * 
	 * @return Boolean 是否更新成功
	 */
	public Boolean delete() {
		init_i();
		return dao.delete(this);
	}

	public static class Dao {
		private static Dao newInstance;

		public static Dao dao() {
			if (newInstance == null)
				newInstance = new Dao();
			init();
			return newInstance;
		}

		/**
		 * 由标识删除指定ID数据对象
		 * 
		 * @param id
		 *            数据对象编号
		 * @return 是否删除成功
		 */
		public Boolean deleteByID(Serializable id) {
			return dao.deleteByID(class_name, id_name, id);
		}

		/**
		 * 根据主键删除多条记录
		 * 
		 * @param String
		 *            ids 数据对象编号 形式如下:字符串:1,2,3,4
		 * @return Boolean 是否删除成功
		 */
		public Boolean deleteByIds(String ids) {
			return dao.deleteByIds(class_name, id_name, ids);
		}

		/**
		 * 根据条件删除多条记录
		 * 
		 * @param String
		 *            whereClause 查询条件，在where后的条件
		 * @return Boolean 是否删除成功
		 */
		public Boolean deleteBy(String whereClause) {
			return dao.deleteBy(class_name, whereClause);
		}

		/**
		 * 更新数据对象指定的属性
		 * 
		 * @param String
		 *            ids 需更新数据的ID编号 示例如下： 1,2,3
		 * @param String
		 *            properties 指定的属性 示例如下： pass=1,name='sky'
		 * @return boolen 是否更新成功；true为操作正常
		 */
		public Boolean updateProperties(String ids, String properties) {
			String updateTimeStr="updateTime='"+new Timestamp(System.currentTimeMillis())+"'";
			return dao.updateProperties(class_name, id_name,ids, properties+","+updateTimeStr);
		}

		/**
		 * 根据条件更新数据对象指定的属性
		 * 
		 * @param String
		 *            whereClause 查询条件，在where后的条件
		 * @param String
		 *            properties 指定的属性 示例如下： pass=1,name='sky'
		 * @return boolen 是否更新成功；true为操作正常
		 */
		public Boolean updateBy(String whereClause, String properties) {
			String updateTimeStr="updateTime='"+new Timestamp(System.currentTimeMillis())+"'";
			return dao.updateBy(class_name, whereClause, properties+","+updateTimeStr);
		}

		/**
		 * 对属性进行递增
		 * 
		 * @param String
		 *            whereClause 查询条件，在where后的条件
		 * @param String
		 *            property_name 属性名称
		 * @param int incre_value 递增数
		 * @return Boolean 操作是否成功
		 */
		public Boolean increment(String whereClause, String property_name,
				int incre_value) {
			return dao.increment(class_name, whereClause, property_name,
					incre_value);
		}

		/**
		 * 对属性进行递减
		 * 
		 * @param String
		 *            whereClause 查询条件，在where后的条件
		 * @param String
		 *            property_name 属性名称
		 * @param int decre_value 递减数
		 * @return Boolean 操作是否成功
		 */
		public Boolean decrement(String whereClause, String property_name,
				int decre_value) {
			return dao.decrement(class_name, whereClause, property_name,
					decre_value);
		}

		/**
		 * 由标识判断指定ID数据对象是否存在
		 * 
		 * @param Serializable
		 *            id 数据对象编号
		 * @return Boolean 是否存在
		 */
		public Boolean existByID(Serializable id) {
			return dao.existByID(class_name, id_name, id);
		}

		/**
		 * 判断符合条件的数据对象是否存在
		 * 
		 * @param String
		 *            whereClause 查询条件，在where后的条件
		 * @return Boolean 是否存在
		 */
		public Boolean existBy(String whereClause) {
			return dao.existBy(class_name, whereClause);
		}

		/**
		 * 根据主键删除多条记录
		 * 
		 * @param String
		 *            id 数据对象编号
		 * @return Boolean 是否删除成功
		 */
		public DataObject get_by_id(Serializable id) {
			return dao.get_by_id(class_name, id);
		}

		/**
		 * 查询当前对象需显示属性的列表
		 * 
		 * @param String
		 *            columns指定的显示属性，同SQL语句中的Select部分。 示例如下： id,name,commitTime
		 * @param String
		 *            whereClause 查询条件，在where后的条件
		 * @param String
		 *            sort 排序条件 示例如下： 1.id asc; 2.name desc;
		 * @param String
		 *            limit 分页数目:同Mysql limit语法 示例如下： 0,10
		 * @return 查询列数组，当只有一个值的时候如select count(表名_id)，自动从数组中转换出来值字符串
		 */
		public List<Object[]> select(String columns, String whereClause,
				String sort, String limit) {
			if (sort == null)
				sort = id_name+" desc ";
			return dao.select(class_name, columns, whereClause, sort, limit);
		}

		/**
		 * 查询当前对象需显示属性的列表
		 * 
		 * @param String
		 *            columns 指定的显示属性，同SQL语句中的Select部分。 示例如下： id,name,commitTime
		 * @param String
		 *            whereClause 查询条件，在where后的条件
		 * @param string
		 *            sort 排序条件 示例如下： 1.id asc; 2.name desc;
		 * @return 查询列数组，自动从数组中转换出来值字符串,最后只返回一个值
		 */
		public Object select_one(String columns, String whereClause,
				String sort) {
			if (sort == null)
				sort = id_name+" desc ";
			return dao
					.select_one(class_name, columns, whereClause, sort);
		}

		/**
		 * 查询当前对象列表
		 * 
		 * @param String
		 *            whereClause 查询条件，在where后的条件
		 * @param string
		 *            sort 排序条件 示例如下： 1.id asc; 2.name desc;
		 * @param string
		 *            limit 分页数目:同Mysql limit语法 示例如下： 0,10
		 * @return 对象列表HashMap
		 */
		public List<DataObject> get(String whereClause, String sort,String limit) {
			if (sort == null)
				sort = id_name+" desc ";
			return dao.get(class_name, whereClause, sort,limit);
		}

		/**
		 * 查询得到单个对象实体
		 * 
		 * @param String
		 *            whereClause 查询条件，在where后的条件
		 * @param string
		 *            sort 排序条件 示例如下： 1.id asc; 2.name desc;
		 * @return 单个对象实体
		 */
		public DataObject get_one(String whereClause, String sort) {
			if (sort == null)
				sort = id_name+" desc ";
			return dao.get_one(class_name, whereClause, sort);
		}

		/**
		 * 对象总计数
		 * 
		 * @return 对象总计数
		 */
		public Number count() {
			return dao.count(class_name, null);
		}

		/**
		 * 对象总计数
		 * 
		 * @param String
		 *            whereClause 查询条件，在where后的条件
		 * @return 对象总计数
		 */
		public Number count(String whereClause) {
			return dao.count(class_name, whereClause);
		}

		/**
		 * 数据对象标识最大值
		 * 
		 * @return int 数据对象标识最大值
		 */
		public Number max() {
			return this.max(null, null);
		}

		/**
		 * 数据对象标识最大值
		 * 
		 * @param String
		 *            column_name 列名，默认为数据对象标识
		 * @return int 数据对象标识最大值
		 */
		public Number max(String column_name) {
			return this.max(column_name, null);
		}

		/**
		 * 数据对象标识最大值
		 * 
		 * @param String
		 *            column_name 列名，默认为数据对象标识
		 * @param String
		 *            whereClause 查询条件，在where后的条件
		 * @return int 数据对象标识最大值
		 */
		public Number max(String column_name, String whereClause) {
			if (column_name == null)
				column_name = id_name;
			return dao.max(class_name, column_name, whereClause);
		}

		/**
		 * 数据对象指定列名最小值，如未指定列名，为标识最小值
		 * 
		 * @return int 数据对象标识最小值
		 */
		public Number min() {
			return this.min(null, null);
		}

		/**
		 * 数据对象指定列名最小值，如未指定列名，为标识最小值
		 * 
		 * @param String
		 *            column_name 列名，默认为数据对象标识
		 * @return int 数据对象列名最小值，如未指定列名，为标识最小值
		 */
		public Number min(String column_name) {
			return this.min(column_name, null);
		}

		/**
		 * 数据对象指定列名最小值，如未指定列名，为标识最小值
		 * 
		 * @param String
		 *            column_name 列名，默认为数据对象标识
		 * @param String
		 *            whereClause 查询条件，在where后的条件
		 * @return int 数据对象列名最小值，如未指定列名，为标识最小值
		 */
		public Number min(String column_name, String whereClause) {
			if (column_name == null)
				column_name = id_name;
			return dao.min(class_name, column_name, whereClause);
		}

		/**
		 * 数据对象指定列名总数
		 * 
		 * @return int 数据对象列名总数
		 */
		public Number sum() {
			return this.sum(null, null);
		}

		/**
		 * 数据对象指定列名总数
		 * 
		 * @param String
		 *            column_name 列名，默认为数据对象标识
		 * @return int 数据对象列名总数
		 */
		public Number sum(String column_name) {
			return this.sum(column_name, null);
		}

		/**
		 * 数据对象指定列名总数
		 * 
		 * @param String
		 *            column_name 列名，默认为数据对象标识
		 * @param String
		 *            whereClause 查询条件，在where后的条件
		 * @return int 数据对象列名总数
		 */
		public Number sum(String column_name, String whereClause) {
			if (column_name == null)
				column_name = id_name;
			return dao.sum(class_name, column_name, whereClause);
		}

		/**
		 * 对象分页
		 * 
		 * @param int startPoint 分页开始记录数
		 * @param int endPoint 分页结束记录数 默认为 id desc 示例如下： 1.id asc; 2.name desc;
		 * @return List<DataObject> 对象分页
		 */
		public List<DataObject> queryPage(int startPoint, int endPoint) {
			return this.queryPage(startPoint, endPoint, null, null);
		}

		/**
		 * 对象分页
		 * 
		 * @param int startPoint 分页开始记录数
		 * @param int endPoint 分页结束记录数
		 * @param String
		 *            whereClause 查询条件，在where后的条件
		 * @param String
		 *            sort 排序条件 默认为 id desc 示例如下： 1.id asc; 2.name desc;
		 * @return List<DataObject> 对象分页
		 */
		public List<DataObject> queryPage(int startPoint, int endPoint,
				String whereClause, String sort) {
			if (sort == null)
				sort = id_name + " desc ";
			return dao.queryPage(class_name, startPoint, endPoint, whereClause,
					sort);
		}

		/**
		 * 对象分页根据当前页数和每页显示记录数
		 * 
		 * @param int pageNo 当前页数
		 * @param int pageSize 每页显示记录数
		 * @return List<DataObject> 对象分页
		 */
		public List<DataObject> queryPageByPageNo(int pageNo, int pageSize) {
			return this.queryPageByPageNo(pageNo, pageSize, null, null);
		}

		/**
		 * 对象分页根据当前页数和每页显示记录数
		 * 
		 * @param int pageNo 当前页数
		 * @param int pageSize 每页显示记录数
		 * @param String
		 *            whereClause 查询条件，在where后的条件
		 * @param String
		 *            sort 排序条件 默认为 id desc 示例如下： 1.id asc; 2.name desc;
		 * @return List<DataObject> 对象分页
		 */
		public List<DataObject> queryPageByPageNo(int pageNo, int pageSize,
				String whereClause, String sort) {
			if (sort == null)
				sort = id_name + " desc ";
			return dao.queryPageByPageNo(class_name, pageNo, pageSize,
					whereClause, sort);
		}
	}
}
