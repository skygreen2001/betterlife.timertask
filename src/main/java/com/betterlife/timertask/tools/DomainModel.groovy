package com.betterlife.timertask.tools

class DomainModel {
	private static int uid_numer=0

	public static String domainModel_sqlserver(String package_name,String table_name,String table_comment,String table_comment_name,String class_name,String field_spec,String instance_name){
		uid_numer+=1;
		def result ="""\
package domain.sqlserver;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import domain.DataObject;

/**
${table_comment}
 * @author skygreen2001@gmail.com
 */
@Entity
@Table(name = "${table_name}")
public class ${class_name} extends DataObject implements Serializable {

	private static final long serialVersionUID = ${uid_numer}L;
${field_spec}
	/**
	 * ${table_comment_name}调用数据库持久层的dao对象
	 */
	public static ${class_name}.Dao dao() {
//		String table_name = ${class_name}.class.getAnnotation(Table.class).name();//表名称
		id_name = "ID";
		class_name = ${class_name}.class.getName();
		return ${class_name}.Dao.dao();
	}

	/**
	 * 保存或更新${table_comment_name}
	 * @return 是否保存或更新成功
	 */
	@Override
	public Boolean saveOrUpdate() 
	{
		if (this.getID()==null)this.setCommitTime(new Timestamp(System.currentTimeMillis()));
		return super.saveOrUpdate();
	}

	/**
	 * 根据表ID主键获取指定的${table_comment_name}[ID对应的表列]
	 * @param Serializable 唯一标识
	 * @return 数据对象
	 */
	public static ${class_name} get_by_id(Serializable id) {
		return (${class_name}) dao().get_by_id(id);
	}
	
	/**
	 * 查询${table_comment_name}列表
	 *
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param string
	 *            sort 排序条件 示例如下： 1.id asc; 2.name desc;
	 * @param string
	 *            limit 分页数目:同Mysql limit语法 示例如下： 0,10
	 * @return 对象列表HashMap
	 */
	public static Map<Serializable, ${class_name}> get() {
		List<DataObject> list = dao().get(null, null, null);
		Map<Serializable, ${class_name}> result = new LinkedHashMap<Serializable, ${class_name}>();
		for (DataObject dataObject : list) {
			${class_name} ${instance_name} = (${class_name}) dataObject;
			result.put(${instance_name}.getID(), ${instance_name});
		}
		return result;
	}

	/**
	 * 查询${table_comment_name}列表
	 *
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param string
	 *            sort 排序条件 示例如下： 1.id asc; 2.name desc;
	 * @param string
	 *            limit 分页数目:同Mysql limit语法 示例如下： 0,10
	 * @return 对象列表HashMap
	 */
	public static Map<Serializable, ${class_name}> get(String whereClause, String sort,
			String limit) {
		List<DataObject> list = dao().get(whereClause, sort, limit);
		Map<Serializable, ${class_name}> result = new LinkedHashMap<Serializable, ${class_name}>();
		for (DataObject dataObject : list) {
			${class_name} ${instance_name} = (${class_name}) dataObject;
			result.put(${instance_name}.getID(), ${instance_name});
		}
		return result;
	}

	/**
	 * ${table_comment_name}分页
	 *
	 * @param int startPoint 分页开始记录数
	 * @param int endPoint 分页结束记录数 默认为 id desc 示例如下： 1.id asc; 2.name desc;
	 * @return Map<Serializable,${class_name}> 对象分页
	 */
	public static Map<Serializable, ${class_name}> queryPage(int startPoint,
			int endPoint) {
		List<DataObject> list = dao().queryPage(startPoint, endPoint);
		Map<Serializable, ${class_name}> result = new LinkedHashMap<Serializable, ${class_name}>();
		for (DataObject dataObject : list) {
			${class_name} ${instance_name} = (${class_name}) dataObject;
			result.put(${instance_name}.getID(), ${instance_name});
		}
		return result;
	}

	/**
	 * ${table_comment_name}分页
	 *
	 * @param int startPoint 分页开始记录数
	 * @param int endPoint 分页结束记录数
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param String
	 *            sort 排序条件 默认为 id desc 示例如下： 1.id asc; 2.name desc;
	 * @return Map<Serializable,${class_name}> 对象分页
	 */
	public static Map<Serializable, ${class_name}> queryPage(int startPoint,
			int endPoint, String whereClause, String sort) {
		List<DataObject> list = dao().queryPage(startPoint, endPoint,
				whereClause, sort);
		Map<Serializable, ${class_name}> result = new LinkedHashMap<Serializable, ${class_name}>();
		for (DataObject dataObject : list) {
			${class_name} ${instance_name} = (${class_name}) dataObject;
			result.put(${instance_name}.getID(), ${instance_name});
		}
		return result;
	}

	/**
	 * ${table_comment_name}分页根据当前页数和每页显示记录数
	 *
	 * @param int pageNo 当前页数
	 * @param int pageSize 每页显示记录数
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param String
	 *            sort 排序条件 默认为 id desc 示例如下： 1.id asc; 2.name desc;
	 * @return Map<Serializable,${class_name}> 对象分页
	 */
	public static Map<Serializable, ${class_name}> queryPageByPageNo(int pageNo,
			int pageSize) {
		List<DataObject> list = dao().queryPageByPageNo(pageNo, pageSize);
		Map<Serializable, ${class_name}> result = new LinkedHashMap<Serializable, ${class_name}>();
		for (DataObject dataObject : list) {
			${class_name} ${instance_name} = (${class_name}) dataObject;
			result.put(${instance_name}.getID(), ${instance_name});
		}
		return result;
	}

	/**
	 * ${table_comment_name}分页根据当前页数和每页显示记录数
	 *
	 * @param int pageNo 当前页数
	 * @param int pageSize 每页显示记录数
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param String
	 *            sort 排序条件 默认为 id desc 示例如下： 1.id asc; 2.name desc;
	 * @return Map<Serializable,${class_name}> 对象分页
	 */
	public static Map<Serializable, ${class_name}> queryPageByPageNo(int pageNo,
			int pageSize, String whereClause, String sort) {
		List<DataObject> list = dao().queryPageByPageNo(pageNo, pageSize,
				whereClause, sort);
		Map<Serializable, ${class_name}> result = new LinkedHashMap<Serializable, ${class_name}>();
		for (DataObject dataObject : list) {
			${class_name} ${instance_name} = (${class_name}) dataObject;
			result.put(${instance_name}.getID(), ${instance_name});
		}
		return result;
	}

}
"""
		return result
	}
	
	public static String domainModel_mysql(String package_name,String table_name,String table_comment,String table_comment_name,String class_name,String field_spec,String instance_name){
		uid_numer+=1;
		def result ="""\
package domain.mysql.${package_name};

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import domain.DataObject;

/**
${table_comment}
 * @author skygreen2001@gmail.com
 */
@Entity
@Table(name = "${table_name}")
public class ${class_name} extends DataObject implements Serializable {

	private static final long serialVersionUID = ${uid_numer}L;
${field_spec}
	/**
	 * ${table_comment_name}调用数据库持久层的dao对象
	 */
	public static ${class_name}.Dao dao() {
//		String table_name = ${class_name}.class.getAnnotation(Table.class).name();//表名称
		id_name = ${class_name}.class.getSimpleName().toLowerCase() + "_id";
		class_name = ${class_name}.class.getName();
		return ${class_name}.Dao.dao();
	}

	/**
	 * 保存或更新${table_comment_name}
	 * @return 是否保存或更新成功
	 */
	@Override
	public Boolean saveOrUpdate() 
	{
		if (this.get${class_name}_id()==null){
			String todayStr = String.valueOf(System.currentTimeMillis()).substring(0,10); //取日期部分
			this.setCommitTime(Integer.parseInt(todayStr));
		}
		return super.saveOrUpdate();
	}

	/**
	 * 根据表ID主键获取指定的${table_comment_name}[ID对应的表列]
	 * @param Serializable 唯一标识
	 * @return 数据对象
	 */
	public static ${class_name} get_by_id(Serializable id) {
		return (${class_name}) dao().get_by_id(id);
	}
	
	/**
	 * 查询${table_comment_name}列表
	 * 
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param string
	 *            sort 排序条件 示例如下： 1.id asc; 2.name desc;
	 * @param string
	 *            limit 分页数目:同Mysql limit语法 示例如下： 0,10
	 * @return 对象列表HashMap
	 */
	public static Map<Serializable, ${class_name}> get() {
		List<DataObject> list = dao().get(null, null, null);
		Map<Serializable, ${class_name}> result = new LinkedHashMap<Serializable, ${class_name}>();
		for (DataObject dataObject : list) {
			${class_name} ${instance_name} = (${class_name}) dataObject;
			result.put(${instance_name}.get${class_name}_id(), ${instance_name});
		}
		return result;
	}

	/**
	 * 查询${table_comment_name}列表
	 * 
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param string
	 *            sort 排序条件 示例如下： 1.id asc; 2.name desc;
	 * @param string
	 *            limit 分页数目:同Mysql limit语法 示例如下： 0,10
	 * @return 对象列表HashMap
	 */
	public static Map<Serializable, ${class_name}> get(String whereClause, String sort,
			String limit) {
		List<DataObject> list = dao().get(whereClause, sort, limit);
		Map<Serializable, ${class_name}> result = new LinkedHashMap<Serializable, ${class_name}>();
		for (DataObject dataObject : list) {
			${class_name} ${instance_name} = (${class_name}) dataObject;
			result.put(${instance_name}.get${class_name}_id(), ${instance_name});
		}
		return result;
	}

	/**
	 * ${table_comment_name}分页
	 * 
	 * @param int startPoint 分页开始记录数
	 * @param int endPoint 分页结束记录数 默认为 id desc 示例如下： 1.id asc; 2.name desc;
	 * @return Map<Serializable,${class_name}> 对象分页
	 */
	public static Map<Serializable, ${class_name}> queryPage(int startPoint,
			int endPoint) {
		List<DataObject> list = dao().queryPage(startPoint, endPoint);
		Map<Serializable, ${class_name}> result = new LinkedHashMap<Serializable, ${class_name}>();
		for (DataObject dataObject : list) {
			${class_name} ${instance_name} = (${class_name}) dataObject;
			result.put(${instance_name}.get${class_name}_id(), ${instance_name});
		}
		return result;
	}

	/**
	 * ${table_comment_name}分页
	 * 
	 * @param int startPoint 分页开始记录数
	 * @param int endPoint 分页结束记录数
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param String
	 *            sort 排序条件 默认为 id desc 示例如下： 1.id asc; 2.name desc;
	 * @return Map<Serializable,${class_name}> 对象分页
	 */
	public static Map<Serializable, ${class_name}> queryPage(int startPoint,
			int endPoint, String whereClause, String sort) {
		List<DataObject> list = dao().queryPage(startPoint, endPoint,
				whereClause, sort);
		Map<Serializable, ${class_name}> result = new LinkedHashMap<Serializable, ${class_name}>();
		for (DataObject dataObject : list) {
			${class_name} ${instance_name} = (${class_name}) dataObject;
			result.put(${instance_name}.get${class_name}_id(), ${instance_name});
		}
		return result;
	}

	/**
	 * ${table_comment_name}分页根据当前页数和每页显示记录数
	 * 
	 * @param int pageNo 当前页数
	 * @param int pageSize 每页显示记录数
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param String
	 *            sort 排序条件 默认为 id desc 示例如下： 1.id asc; 2.name desc;
	 * @return Map<Serializable,${class_name}> 对象分页
	 */
	public static Map<Serializable, ${class_name}> queryPageByPageNo(int pageNo,
			int pageSize) {
		List<DataObject> list = dao().queryPageByPageNo(pageNo, pageSize);
		Map<Serializable, ${class_name}> result = new LinkedHashMap<Serializable, ${class_name}>();
		for (DataObject dataObject : list) {
			${class_name} ${instance_name} = (${class_name}) dataObject;
			result.put(${instance_name}.get${class_name}_id(), ${instance_name});
		}
		return result;
	}

	/**
	 * ${table_comment_name}分页根据当前页数和每页显示记录数
	 * 
	 * @param int pageNo 当前页数
	 * @param int pageSize 每页显示记录数
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param String
	 *            sort 排序条件 默认为 id desc 示例如下： 1.id asc; 2.name desc;
	 * @return Map<Serializable,${class_name}> 对象分页
	 */
	public static Map<Serializable, ${class_name}> queryPageByPageNo(int pageNo,
			int pageSize, String whereClause, String sort) {
		List<DataObject> list = dao().queryPageByPageNo(pageNo, pageSize,
				whereClause, sort);
		Map<Serializable, ${class_name}> result = new LinkedHashMap<Serializable, ${class_name}>();
		for (DataObject dataObject : list) {
			${class_name} ${instance_name} = (${class_name}) dataObject;
			result.put(${instance_name}.get${class_name}_id(), ${instance_name});
		}
		return result;
	}

}
"""
		return result
	}
		

}
