package com.betterlife.timertask.dao.mysql;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.betterlife.timertask.common.log.LogMe;
import com.betterlife.timertask.dao.IDao;
import com.betterlife.timertask.domain.DataObject;

@Transactional
public class Dao implements IDao {

	public SessionFactory sessionFactory;
	// public JdbcTemplate jdbcTemplate;
	protected Session session;

    @Autowired
    private HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @param jdbcTemplate
	 *            the jdbcTemplate to set
	 */
	// public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	// this.jdbcTemplate = jdbcTemplate;
	// }
	
	public Serializable save(DataObject dataobject) {
		try {
			Serializable result = this.hibernateTemplate.save(dataobject);
			return result;
		} catch (Exception ex) {
			return 0;
		}
	}

	public Boolean saveOrUpdate(DataObject dataobject) {
		try {
			this.hibernateTemplate.saveOrUpdate(dataobject);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public Boolean update(DataObject dataobject) {
		try {
			this.hibernateTemplate.update(dataobject);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public Boolean updateProperties(String entityName, String id_name,
			String ids, String properties) {
		if ((ids != null) && (ids.trim().length() > 0)) {
			String[] idList = ids.split(",");
			if ((idList != null) && (ids.length() > 0)) {
				session = this.sessionFactory.getCurrentSession();
				String whereClause = id_name + "=" + idList[0];
				for (int i = 1; i < idList.length; i++) {
					whereClause += " or " + id_name + "=" + idList[i];
				}
				String hql = "update " + entityName + " set " + properties
						+ "  where " + whereClause;
//				Query query = session.createQuery(hql);
				int count = session.createQuery(hql).executeUpdate();
//				session.close();
				if (count > 0)
					return true;
				else
					return false;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Boolean updateBy(String entityName, String whereClause,
			String properties) {
		try {
			String allWhere = "";
			session = this.sessionFactory.getCurrentSession();
			if ((whereClause != null) && (whereClause.trim().length() > 0)) {
				allWhere = " where " + whereClause;
			}
			final String hql = "update " + entityName + " set " + properties
					+ " " + allWhere;
//			Query query = session.createQuery(hql);
			int count = session.createQuery(hql).executeUpdate();
//			session.close();
			if (count > 0)
				return true;
			else
				return false;
		} catch (Exception ex) {
			return false;
		}
	}

	public Boolean increment(String entityName, String whereClause,
			String property_name, int incre_value) {
		try {
			String allWhere = "";
			session = this.sessionFactory.getCurrentSession();
			if ((whereClause != null) && (whereClause.trim().length() > 0)) {
				allWhere = " where " + whereClause;
			}
			String properties = property_name + "=" + property_name + "+"
					+ incre_value;
			final String hql = "update " + entityName + " set " + properties
					+ allWhere;
//			Query query = session.createQuery(hql);
			int count = session.createQuery(hql).executeUpdate();
//			session.close();
			if (count > 0)
				return true;
			else
				return false;
		} catch (Exception ex) {
			return false;
		}
	}

	public Boolean decrement(String entityName, String whereClause,
			String property_name, int decre_value) {
		try {
			String allWhere = "";
			session = this.sessionFactory.getCurrentSession();
			if ((whereClause != null) && (whereClause.trim().length() > 0)) {
				allWhere = " where " + whereClause;
			}
			String properties = property_name + "=" + property_name + "-"
					+ decre_value;
			final String hql = "update " + entityName + " set " + properties
					+ allWhere;
//			Query query = session.createQuery(hql);
			int count = session.createQuery(hql).executeUpdate();
//			session.close();
			if (count > 0)
				return true;
			else
				return false;
		} catch (Exception ex) {
			return false;
		}
	}

	public Boolean deleteBy(String entityName, String whereClause) {
		String allWhere = "";
		session = this.sessionFactory.getCurrentSession();
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}

		final String hql = "delete from " + entityName + allWhere;
//		Query query = session.createQuery(hql);
		int count = session.createQuery(hql).executeUpdate();
//		session.close();
		if (count > 0)
			return true;
		else
			return false;
	}

	public Boolean delete(DataObject dataobject) {
		try {
			this.hibernateTemplate.delete(dataobject);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public Boolean deleteByID(String entityName, String id_name, Serializable id) {
		session = this.sessionFactory.getCurrentSession();
		String hql = "delete from " + entityName + " where " + id_name + "="
				+ id;
//		Query query = session.createQuery(hql);
		int count = session.createQuery(hql).executeUpdate();
//		session.close();
		if (count > 0)
			return true;
		else
			return false;
	}

	public Boolean deleteByIds(String entityName, String id_name, String ids) {
		if ((ids != null) && (ids.trim().length() > 0)) {
			String[] idList = ids.split(",");
			if ((idList != null) && (ids.length() > 0)) {
				session = this.sessionFactory.getCurrentSession();
				String whereClause = id_name + "=" + idList[0];
				for (int i = 1; i < idList.length; i++) {
					whereClause += " or " + id_name + "=" + idList[i];
				}
				String hql = "delete from " + entityName + " where "
						+ whereClause;
//				Query query = session.createQuery(hql);
				int count = session.createQuery(hql).executeUpdate();
//				session.close();
				if (count > 0)
					return true;
				else
					return false;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public DataObject get_by_id(String entityName, Serializable id) {
		session = this.sessionFactory.getCurrentSession();
		DataObject result = (DataObject) session.get(entityName, id);
//		session.close();
		return result;
	}

	public Boolean existByID(String entityName, String id_name, Serializable id) {
		session = this.sessionFactory.getCurrentSession();
		final String hql = "select count(" + id_name + ") from " + entityName
				+ " where " + id_name + "=" + id;
		Query<Number> query = session.createQuery(hql, Number.class);
		Number result = query.uniqueResult();
//		session.close();
		if (result.intValue() > 0)
			return true;
		else
			return false;
	}

	public Boolean existBy(String entityName, String whereClause) {
		String allWhere = "";
		session = this.sessionFactory.getCurrentSession();
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}
		final String hql = "select count(*) from " + entityName + allWhere;

		Query<Number> query = session.createQuery(hql, Number.class);
		Number result = query.uniqueResult();
//		session.close();
		if (result.intValue() > 0)
			return true;
		else
			return false;
	}

	public List<Object[]> select(String entityName, String columns,
			String whereClause, String sort, String limit) {
		String allWhere = "";
		String allSort = "";
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}
		if ((sort != null) && (sort.trim().length() > 0)) {
			allSort = " order by " + sort;
		}

		final String hql = "select " + columns + " from " + entityName
				+ allWhere + allSort;
		session = this.sessionFactory.getCurrentSession();
		Query<Object[]> query = session.createQuery(hql, Object[].class);

		if ((limit != null) && (limit.trim().length() > 0)) {
			String[] allLimit = limit.split(",");
			if ((allLimit != null) && (allLimit.length == 2)) {
				query.setFirstResult(Integer.parseInt(allLimit[0]));
				query.setMaxResults(Integer.parseInt(allLimit[1]));
			}
		}
		List<Object[]> result = query.list();
//		session.close();
		return result;
//		return null;
	}

	public Object select_one(String entityName, String columns,
			String whereClause, String sort) {
		String allWhere = "";
		String allSort = "";
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}
		if ((sort != null) && (sort.trim().length() > 0)) {
			allSort = " order by " + sort;
		}

		final String hql = "select " + columns + " from " + entityName
				+ allWhere + allSort;
		session = this.sessionFactory.getCurrentSession();
		Query<Object> query = session.createQuery(hql, Object.class);
		query.setFirstResult(0);
		query.setMaxResults(1);

		Object result = query.uniqueResult();
//		session.close();
		return result;
	}

	public List<DataObject> get(String entityName, String whereClause,
			String sort, String limit) {
		String allWhere = "";
		String allSort = "";
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}
		if ((sort != null) && (sort.trim().length() > 0)) {
			allSort = " order by " + sort;
		}

		final String hql = "from " + entityName + allWhere + allSort;
		session = this.sessionFactory.getCurrentSession();
		Query<DataObject> query = session.createQuery(hql, DataObject.class);
		
		if ((limit != null) && (limit.trim().length() > 0)) {
			String[] allLimit = limit.split(",");
			if ((allLimit != null) && (allLimit.length == 2)) {
				query.setFirstResult(Integer.parseInt(allLimit[0]));
				query.setMaxResults(Integer.parseInt(allLimit[1]));
			}
		}
		
		List<DataObject> result = query.list();
//		session.close();
		return result;
	}

	public DataObject get_one(String entityName, String whereClause, String sort) {
		String allWhere = "";
		String allSort = "";
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}
		if ((sort != null) && (sort.trim().length() > 0)) {
			allSort = " order by " + sort;
		}

		final String hql = "from " + entityName + allWhere + allSort;
		session = this.sessionFactory.getCurrentSession();
		Query<DataObject> query = session.createQuery(hql, DataObject.class);

		DataObject result = query.uniqueResult();
//		session.close();
		return result;
	}

	public Number count(String entityName, String whereClause) {
		session = this.sessionFactory.getCurrentSession();
		Number result = 0;
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			result = (Number) session.createQuery(
					"select count(*) from " + entityName + " where "
							+ whereClause).uniqueResult();
		} else {
//			result = (Number) session.createCriteria(entityName)
//					.setProjection(Projections.rowCount()).uniqueResult();

//			getHibernateTemplate()..find("select count(*) from " + entityName).
			
			
			// Create CriteriaBuilder
			CriteriaBuilder builder = session.getCriteriaBuilder();
			try {
				// Create CriteriaQuery
				CriteriaQuery<? extends Object> criteria = builder.createQuery(Class.forName(entityName));
				criteria.from(Class.forName(entityName));
				
				result = (Number) session.createQuery(criteria).getResultList().size();
			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
				LogMe.debug(e.getMessage());
			}
			
		}
//		session.close();
		return result;
	}

	public Number max(String entityName, String column_name, String whereClause) {
		Number result = 0;
		String allWhere = "";
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}
		session = this.sessionFactory.getCurrentSession();
		result = (Number) session
				.createQuery(
						"select max(" + column_name + ") from " + entityName
								+ allWhere).uniqueResult();
//		session.close();
		return result;

	}

	public Number min(String entityName, String column_name, String whereClause) {
		Number result = 0;
		String allWhere = "";
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}
		session = this.sessionFactory.getCurrentSession();
		result = (Number) session
				.createQuery(
						"select min(" + column_name + ") from " + entityName
								+ allWhere).uniqueResult();
//		session.close();
		return result;

	}

	public Number sum(String entityName, String column_name, String whereClause) {
		Number result = 0;
		String allWhere = "";
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}
		session = this.sessionFactory.getCurrentSession();
		result = (Number) session
				.createQuery(
						"select sum(" + column_name + ") from " + entityName
								+ allWhere).uniqueResult();
//		session.close();
		return result;
	}

	public List<DataObject> queryPage(String entityName, final int startPoint,
			final int endPoint, String whereClause, String sort) {
		int start = startPoint - 1;
		if (start < 0)
			start = 0;
		int limit = (endPoint - startPoint) + 1;

		String allWhere = "";
		String allSort = "";
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}
		if ((sort != null) && (sort.trim().length() > 0)) {
			allSort = " order by " + sort;
		}

		final String hql = "from " + entityName + allWhere + allSort;
		session = this.sessionFactory.getCurrentSession();
		Query<DataObject> query = session.createQuery(hql, DataObject.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);

		List<DataObject> result = query.list();
//		session.close();
		return result;
	}

	public List<DataObject> queryPageByPageNo(String entityName, int pageNo,
			int pageSize, String whereClause, String sort) {
		int start = (pageNo - 1) * pageSize;
		if (start < 0)
			start = 0;
		int limit = pageSize;

		String allWhere = "";
		String allSort = "";
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}
		if ((sort != null) && (sort.trim().length() > 0)) {
			allSort = " order by " + sort;
		}

		final String hql = "from " + entityName + allWhere + allSort;
		session = this.sessionFactory.getCurrentSession();
		Query<DataObject> query = session.createQuery(hql, DataObject.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);

		
		List<DataObject> result = query.list();
//		session.close();
		return result;
	}

}
