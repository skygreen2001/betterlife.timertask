package com.betterlife.timertask.domain.mysql;

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

import com.betterlife.timertask.domain.DataObject;
/**
 * 管理员
 * @author skygreen2001@gmail.com
 */
@Entity
@Table(name = "bb_user_admin")
public class Admin extends DataObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long admin_id;
	private String username;
	private String realname;
	private String password;
	private Integer loginTimes;
	
	/**
	 * @return admin_id:标识
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getAdmin_id() {
		return admin_id;
	}

	/**
	 * @param 标识
	 */
	public void setAdmin_id(Long admin_id) {
		this.admin_id = admin_id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the loginTimes
	 */
	public Integer getLoginTimes() {
		if (loginTimes==null)loginTimes=0;
		return loginTimes;
	}

	/**
	 * @param loginTimes the loginTimes to set
	 */
	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	/**
	 * @return updateTime:更新时间
	 */
	public Timestamp getUpdateTime() {
		return (Timestamp)updateTime;
	}

	/**
	 * @return commitTime:创建时间
	 */
	public Integer getCommitTime() {
		return (Integer)commitTime;
	}
	
	public static Admin.Dao dao() {
//		String table_name = Admin.class.getAnnotation(Table.class).name();//表名称
		id_name = Admin.class.getSimpleName().toLowerCase() + "_id";
		class_name = Admin.class.getName();
		return Admin.Dao.dao();
	}

	/**
	 * 保存或更新当前对象
	 * @return 是否保存或更新成功
	 */
	@Override
	public Boolean saveOrUpdate() 
	{
		if (this.getAdmin_id()==null){
			String todayStr = String.valueOf(System.currentTimeMillis()).substring(0,10); //取日期部分
			this.setCommitTime(Integer.parseInt(todayStr));
		}
		return super.saveOrUpdate();
	}

	/**
	 * 根据表ID主键获取指定的对象[ID对应的表列]
	 * @param Serializable 唯一标识
	 * @return 数据对象
	 */
	public static Admin get_by_id(Serializable id) {
		return (Admin) dao().get_by_id(id);
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
	public static Map<Serializable, Admin> get() {
		List<DataObject> list = dao().get(null, null, null);
		Map<Serializable, Admin> result = new LinkedHashMap<Serializable, Admin>();
		for (DataObject dataObject : list) {
			Admin admin = (Admin) dataObject;
			result.put(admin.getAdmin_id(), admin);
		}
		return result;
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
	public static Map<Serializable, Admin> get(String whereClause, String sort,
			String limit) {
		List<DataObject> list = dao().get(whereClause, sort, limit);
		Map<Serializable, Admin> result = new LinkedHashMap<Serializable, Admin>();
		for (DataObject dataObject : list) {
			Admin admin = (Admin) dataObject;
			result.put(admin.getAdmin_id(), admin);
		}
		return result;
	}

	/**
	 * 对象分页
	 * 
	 * @param int startPoint 分页开始记录数
	 * @param int endPoint 分页结束记录数 默认为 id desc 示例如下： 1.id asc; 2.name desc;
	 * @return Map<Serializable,Admin> 对象分页
	 */
	public static Map<Serializable, Admin> queryPage(int startPoint,
			int endPoint) {
		List<DataObject> list = dao().queryPage(startPoint, endPoint);
		Map<Serializable, Admin> result = new LinkedHashMap<Serializable, Admin>();
		for (DataObject dataObject : list) {
			Admin admin = (Admin) dataObject;
			result.put(admin.getAdmin_id(), admin);
		}
		return result;
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
	 * @return Map<Serializable,Admin> 对象分页
	 */
	public static Map<Serializable, Admin> queryPage(int startPoint,
			int endPoint, String whereClause, String sort) {
		List<DataObject> list = dao().queryPage(startPoint, endPoint,
				whereClause, sort);
		Map<Serializable, Admin> result = new LinkedHashMap<Serializable, Admin>();
		for (DataObject dataObject : list) {
			Admin admin = (Admin) dataObject;
			result.put(admin.getAdmin_id(), admin);
		}
		return result;
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
	 * @return Map<Serializable,Admin> 对象分页
	 */
	public static Map<Serializable, Admin> queryPageByPageNo(int pageNo,
			int pageSize) {
		List<DataObject> list = dao().queryPageByPageNo(pageNo, pageSize);
		Map<Serializable, Admin> result = new LinkedHashMap<Serializable, Admin>();
		for (DataObject dataObject : list) {
			Admin admin = (Admin) dataObject;
			result.put(admin.getAdmin_id(), admin);
		}
		return result;
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
	 * @return Map<Serializable,Admin> 对象分页
	 */
	public static Map<Serializable, Admin> queryPageByPageNo(int pageNo,
			int pageSize, String whereClause, String sort) {
		List<DataObject> list = dao().queryPageByPageNo(pageNo, pageSize,
				whereClause, sort);
		Map<Serializable, Admin> result = new LinkedHashMap<Serializable, Admin>();
		for (DataObject dataObject : list) {
			Admin admin = (Admin) dataObject;
			result.put(admin.getAdmin_id(), admin);
		}
		return result;
	}

}
