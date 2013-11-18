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

@Entity
@Table(name="Admin")
public class SAdmin extends DataObject implements Serializable 
{
	private static final long serialVersionUID = -1701180830233794737L;
	
	private String ID;
	private String username;
	private String realname;
	private String password;
	private Integer loginTimes;
	
	/**
	 * @return the iD
	 */	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "ID", unique = true)
	public String getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(String ID) {
		this.ID = ID;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the realname
	 */
	public String getRealname() {
		return realname;
	}

	/**
	 * @param realname the realname to set
	 */
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
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the loginTimes
	 */
	public Integer getLoginTimes() {
		if (this.loginTimes==null)this.loginTimes=0;
		return this.loginTimes;
	}

	/**
	 * @param loginTimes the loginTimes to set
	 */
	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	/**
	 * @return the updateTime
	 */
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	/**
	 * @return the commitTime
	 */
	public Timestamp getCommitTime() {
		return (Timestamp)this.commitTime;
	}
	
	
	public static SAdmin.Dao dao() {
//		String table_name = Admin.class.getAnnotation(Table.class).name();//表名称
		id_name = "ID";
		class_name = SAdmin.class.getName();
		return SAdmin.Dao.dao();
	}

	/**
	 * 保存或更新当前对象
	 * 
	 * @return 是否保存或更新成功
	 */
	public Boolean saveOrUpdate() 
	{
		if (this.getID()==null)this.setCommitTime(new Timestamp(System.currentTimeMillis()));	
		return super.saveOrUpdate();
	}

	/**
	 * 根据表ID主键获取指定的对象[ID对应的表列]
	 * 
	 * @param Serializable
	 *            id 唯一标识
	 * @return 数据对象
	 */
	public static SAdmin get_by_id(Serializable id) {
		return (SAdmin) dao().get_by_id(id);
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
	public static Map<Serializable, SAdmin> get() {
		List<DataObject> list = dao().get(null, null, null);
		Map<Serializable, SAdmin> result = new LinkedHashMap<Serializable, SAdmin>();
		for (DataObject dataObject : list) {
			SAdmin sadmin = (SAdmin) dataObject;
			result.put(sadmin.getID(), sadmin);
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
	public static Map<Serializable, SAdmin> get(String whereClause, String sort,
			String limit) {
		List<DataObject> list = dao().get(whereClause, sort, limit);
		Map<Serializable, SAdmin> result = new LinkedHashMap<Serializable, SAdmin>();
		for (DataObject dataObject : list) {
			SAdmin sadmin = (SAdmin) dataObject;
			result.put(sadmin.getID(), sadmin);
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
	public static Map<Serializable, SAdmin> queryPage(int startPoint,
			int endPoint) {
		List<DataObject> list = dao().queryPage(startPoint, endPoint);
		Map<Serializable, SAdmin> result = new LinkedHashMap<Serializable, SAdmin>();
		for (DataObject dataObject : list) {
			SAdmin admin = (SAdmin) dataObject;
			result.put(admin.getID(), admin);
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
	public static Map<Serializable, SAdmin> queryPage(int startPoint,
			int endPoint, String whereClause, String sort) {
		List<DataObject> list = dao().queryPage(startPoint, endPoint,
				whereClause, sort);
		Map<Serializable, SAdmin> result = new LinkedHashMap<Serializable, SAdmin>();
		for (DataObject dataObject : list) {
			SAdmin admin = (SAdmin) dataObject;
			result.put(admin.getID(), admin);
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
	public static Map<Serializable, SAdmin> queryPageByPageNo(int pageNo,
			int pageSize) {
		List<DataObject> list = dao().queryPageByPageNo(pageNo, pageSize);
		Map<Serializable, SAdmin> result = new LinkedHashMap<Serializable, SAdmin>();
		for (DataObject dataObject : list) {
			SAdmin admin = (SAdmin) dataObject;
			result.put(admin.getID(), admin);
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
	public static Map<Serializable, SAdmin> queryPageByPageNo(int pageNo,
			int pageSize, String whereClause, String sort) {
		List<DataObject> list = dao().queryPageByPageNo(pageNo, pageSize,
				whereClause, sort);
		Map<Serializable, SAdmin> result = new LinkedHashMap<Serializable, SAdmin>();
		for (DataObject dataObject : list) {
			SAdmin admin = (SAdmin) dataObject;
			result.put(admin.getID(), admin);
		}
		return result;
	}

}
