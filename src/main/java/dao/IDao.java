package dao;

import java.io.Serializable;
import java.util.List;

import domain.DataObject;

public interface IDao {
	/**
	 * 保存当前对象
	 * 
	 * @param dataobject
	 *            数据对象
	 * @return Serializable 数据对象的唯一标识
	 */
	public Serializable save(DataObject dataobject);

	/**
	 * 更新当前对象
	 * 
	 * @param dataobject
	 *            数据对象
	 * @return Boolean 是否更新成功
	 */
	public Boolean update(DataObject dataobject);

	/**
	 * 更新数据对象指定的属性
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param String
	 *            ids 需更新数据的ID编号 示例如下： 1,2,3
	 * @param String
	 *            properties 指定的属性 示例如下： pass=1,name='sky'
	 * @return boolen 是否更新成功；true为操作正常
	 */
	public Boolean updateProperties(String entityName, String id_name,
			String ids, String properties);

	/**
	 * 根据条件更新数据对象指定的属性
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param String
	 *            properties 指定的属性 示例如下： pass=1,name='sky'
	 * @return boolen 是否更新成功；true为操作正常
	 */
	public Boolean updateBy(String entityName, String whereClause,
			String properties);

	/**
	 * 对属性进行递增
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param String
	 *            property_name 属性名称
	 * @param int incre_value 递增数
	 * @return Boolean 操作是否成功
	 */
	public Boolean increment(String entityName, String whereClause,
			String property_name, int incre_value);

	/**
	 * 对属性进行递减
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param String
	 *            property_name 属性名称
	 * @param int decre_value 递减数
	 * @return Boolean 操作是否成功
	 */
	public Boolean decrement(String entityName, String whereClause,
			String property_name, int decre_value);

	/**
	 * 保存或更新当前对象
	 * 
	 * @param dataobject
	 *            数据对象
	 * @return 是否保存或更新成功
	 */
	public Boolean saveOrUpdate(DataObject dataobject);

	/**
	 * 删除当前对象
	 * 
	 * @param dataobject
	 *            数据对象
	 * @return Boolean 是否更新成功
	 */
	public Boolean delete(DataObject dataobject);

	/**
	 * 由标识删除指定ID数据对象
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param String
	 *            id_name 数据对象标识名称
	 * @param Serializable
	 *            id 数据对象编号
	 * @return 是否删除成功
	 */
	public Boolean deleteByID(String entityName, String id_name, Serializable id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param String
	 *            id_name 数据对象标识名称
	 * @param String
	 *            ids 数据对象编号 形式如下:字符串:1,2,3,4
	 * @return Boolean 是否删除成功
	 */
	public Boolean deleteByIds(String entityName, String id_name, String ids);

	/**
	 * 根据条件删除多条记录
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @return Boolean 是否删除成功
	 */
	public Boolean deleteBy(String entityName, String whereClause);

	/**
	 * 由标识判断指定ID数据对象是否存在
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param Serializable
	 *            id 数据对象编号
	 * @return Boolean 是否存在
	 */
	public Boolean existByID(String entityName, String id_name, Serializable id);

	/**
	 * 判断符合条件的数据对象是否存在
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @return Boolean 是否存在
	 */
	public Boolean existBy(String entityName, String whereClause);

	/**
	 * 根据表ID主键获取指定的对象[ID对应的表列]
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param Serializable
	 *            id 唯一标识
	 * @return 数据对象
	 */
	public DataObject get_by_id(String entityName, Serializable id);

	/**
	 * 查询当前对象需显示属性的列表
	 * 
	 * @param String
	 *            entityName 数据对象类名
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
	public List<Object> select(String entityName, String columns,
			String whereClause, String sort, String limit);

	/**
	 * 查询当前对象需显示属性的列表
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param String
	 *            columns 指定的显示属性，同SQL语句中的Select部分。 示例如下： id,name,commitTime
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param string
	 *            $sort 排序条件 示例如下： 1.id asc; 2.name desc;
	 */
	public Object select_one(String entityName, String columns,
			String whereClause, String sort);

	/**
	 * 查询当前对象列表
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param string
	 *            sort 排序条件 示例如下： 1.id asc; 2.name desc;
	 * @param string
	 *            limit 分页数目:同Mysql limit语法 示例如下： 0,10
	 * @return 对象列表HashMap
	 */
	public List<DataObject> get(String entityName, String whereClause,
			String sort, String limit);

	/**
	 * 查询得到单个对象实体
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param string
	 *            $sort 排序条件 示例如下： 1.id asc; 2.name desc;
	 * @return 单个对象实体
	 */
	public DataObject get_one(String entityName, String whereClause, String sort);

	/**
	 * 对象总计数
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @return 对象总计数
	 */
	public Number count(String entityName, String whereClause);

	/**
	 * 数据对象标识最大值
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param String
	 *            column_name 列名，默认为数据对象标识
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @return int 数据对象标识最大值
	 */
	public Number max(String entityName, String column_name, String whereClause);

	/**
	 * 数据对象指定列名最小值，如未指定列名，为标识最小值
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param String
	 *            column_name 列名，默认为数据对象标识
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @return int 数据对象列名最小值，如未指定列名，为标识最小值
	 */
	public Number min(String entityName, String column_name, String whereClause);

	/**
	 * 数据对象指定列名总数
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param String
	 *            column_name 列名，默认为数据对象标识
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @return int 数据对象列名总数
	 */
	public Number sum(String entityName, String column_name, String whereClause);

	/**
	 * 对象分页
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param int startPoint 分页开始记录数
	 * @param int endPoint 分页结束记录数
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param String
	 *            sort 排序条件 默认为 id desc 示例如下： 1.id asc; 2.name desc;
	 * @return List<DataObject> 对象分页
	 */
	public List<DataObject> queryPage(String entityName, int startPoint,
			int endPoint, String whereClause, String sort);

	/**
	 * 对象分页根据当前页数和每页显示记录数
	 * 
	 * @param String
	 *            entityName 数据对象类名
	 * @param int pageNo 当前页数
	 * @param int pageSize 每页显示记录数
	 * @param String
	 *            whereClause 查询条件，在where后的条件
	 * @param String
	 *            sort 排序条件 默认为 id desc 示例如下： 1.id asc; 2.name desc;
	 * @return List<DataObject> 对象分页
	 */
	public List<DataObject> queryPageByPageNo(String entityName, int pageNo,
			int pageSize, String whereClause, String sort);

}
