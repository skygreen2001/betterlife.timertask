package dao.mysql;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
//import org.springframework.jdbc.core.JdbcTemplate;

import dao.IDao;
import domain.DataObject;

public class Dao implements IDao {
	public SessionFactory sessionFactory;
	// public JdbcTemplate jdbcTemplate;
	protected Session session;

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
			session = this.sessionFactory.openSession();
			Serializable result = this.session.save(dataobject);
			session.flush();
			session.close();
			return result;
		} catch (Exception ex) {
			return false;
		}
	}

	public Boolean saveOrUpdate(DataObject dataobject) {
		try {
			session = this.sessionFactory.openSession();
			session.saveOrUpdate(dataobject);
			session.flush();
			session.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public Boolean update(DataObject dataobject) {
		try {
			session = this.sessionFactory.openSession();
			session.update(dataobject);
			session.flush();
			session.close();
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
				session = this.sessionFactory.openSession();
				String whereClause = id_name + "=" + idList[0];
				for (int i = 1; i < idList.length; i++) {
					whereClause += " or " + id_name + "=" + idList[i];
				}
				String hql = "update " + entityName + " set " + properties
						+ "  where " + whereClause;
				org.hibernate.Query query = session.createQuery(hql);
				int count = query.executeUpdate();
				session.close();
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
			session = this.sessionFactory.openSession();
			if ((whereClause != null) && (whereClause.trim().length() > 0)) {
				allWhere = " where " + whereClause;
			}
			final String hql = "update " + entityName + " set " + properties
					+ " " + allWhere;
			org.hibernate.Query query = session.createQuery(hql);
			int count = query.executeUpdate();
			session.close();
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
			session = this.sessionFactory.openSession();
			if ((whereClause != null) && (whereClause.trim().length() > 0)) {
				allWhere = " where " + whereClause;
			}
			String properties = property_name + "=" + property_name + "+"
					+ incre_value;
			final String hql = "update " + entityName + " set " + properties
					+ allWhere;
			org.hibernate.Query query = session.createQuery(hql);
			int count = query.executeUpdate();
			session.close();
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
			session = this.sessionFactory.openSession();
			if ((whereClause != null) && (whereClause.trim().length() > 0)) {
				allWhere = " where " + whereClause;
			}
			String properties = property_name + "=" + property_name + "-"
					+ decre_value;
			final String hql = "update " + entityName + " set " + properties
					+ allWhere;
			org.hibernate.Query query = session.createQuery(hql);
			int count = query.executeUpdate();
			session.close();
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
		session = this.sessionFactory.openSession();
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}

		final String hql = "delete from " + entityName + allWhere;
		org.hibernate.Query query = session.createQuery(hql);
		int count = query.executeUpdate();
		session.close();
		if (count > 0)
			return true;
		else
			return false;
	}

	public Boolean delete(DataObject dataobject) {
		try {
			session = this.sessionFactory.openSession();
			session.delete(dataobject);
			session.flush();
			session.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public Boolean deleteByID(String entityName, String id_name, Serializable id) {
		session = this.sessionFactory.openSession();
		String hql = "delete from " + entityName + " where " + id_name + "="
				+ id;
		org.hibernate.Query query = session.createQuery(hql);
		int count = query.executeUpdate();
		session.close();
		if (count > 0)
			return true;
		else
			return false;
	}

	public Boolean deleteByIds(String entityName, String id_name, String ids) {
		if ((ids != null) && (ids.trim().length() > 0)) {
			String[] idList = ids.split(",");
			if ((idList != null) && (ids.length() > 0)) {
				session = this.sessionFactory.openSession();
				String whereClause = id_name + "=" + idList[0];
				for (int i = 1; i < idList.length; i++) {
					whereClause += " or " + id_name + "=" + idList[i];
				}
				String hql = "delete from " + entityName + " where "
						+ whereClause;
				org.hibernate.Query query = session.createQuery(hql);
				int count = query.executeUpdate();
				session.close();
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
		session = this.sessionFactory.openSession();
		DataObject result = (DataObject) session.get(entityName, id);
		session.close();
		return result;
	}

	public Boolean existByID(String entityName, String id_name, Serializable id) {
		session = this.sessionFactory.openSession();
		final String hql = "select count(" + id_name + ") from " + entityName
				+ " where " + id_name + "=" + id;
		org.hibernate.Query query = session.createQuery(hql);
		Number result = (Number) query.uniqueResult();
		session.close();
		if (result.intValue() > 0)
			return true;
		else
			return false;
	}

	public Boolean existBy(String entityName, String whereClause) {
		String allWhere = "";
		session = this.sessionFactory.openSession();
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}
		final String hql = "select count(*) from " + entityName + allWhere;

		org.hibernate.Query query = session.createQuery(hql);
		Number result = (Number) query.uniqueResult();
		session.close();
		if (result.intValue() > 0)
			return true;
		else
			return false;
	}

	public List<Object> select(String entityName, String columns,
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
		session = this.sessionFactory.openSession();
		org.hibernate.Query query = session.createQuery(hql);

		if ((limit != null) && (limit.trim().length() > 0)) {
			String[] allLimit = limit.split(",");
			if ((allLimit != null) && (allLimit.length == 2)) {
				query.setFirstResult(Integer.parseInt(allLimit[0]));
				query.setMaxResults(Integer.parseInt(allLimit[1]));
			}
		}
		@SuppressWarnings("unchecked")
		List<Object> result = query.list();
		session.close();
		return result;
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
		session = this.sessionFactory.openSession();
		org.hibernate.Query query = session.createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(1);

		Object result = (Object) query.uniqueResult();
		session.close();
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
		session = this.sessionFactory.openSession();
		org.hibernate.Query query = session.createQuery(hql);

		if ((limit != null) && (limit.trim().length() > 0)) {
			String[] allLimit = limit.split(",");
			if ((allLimit != null) && (allLimit.length == 2)) {
				query.setFirstResult(Integer.parseInt(allLimit[0]));
				query.setMaxResults(Integer.parseInt(allLimit[1]));
			}
		}
		@SuppressWarnings("unchecked")
		List<DataObject> result = query.list();
		session.close();
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
		session = this.sessionFactory.openSession();
		org.hibernate.Query query = session.createQuery(hql);

		DataObject result = (DataObject) query.uniqueResult();
		session.close();
		return result;
	}

	public Number count(String entityName, String whereClause) {
		session = this.sessionFactory.openSession();
		Number result = 0;
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			result = (Number) session.createQuery(
					"select count(*) from " + entityName + " where "
							+ whereClause).uniqueResult();
		} else {
			result = (Number) session.createCriteria(entityName)
					.setProjection(Projections.rowCount()).uniqueResult();
		}
		session.close();
		return result;
	}

	public Number max(String entityName, String column_name, String whereClause) {
		Number result = 0;
		String allWhere = "";
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}
		session = this.sessionFactory.openSession();
		result = (Number) session
				.createQuery(
						"select max(" + column_name + ") from " + entityName
								+ allWhere).uniqueResult();
		session.close();
		return result;

	}

	public Number min(String entityName, String column_name, String whereClause) {
		Number result = 0;
		String allWhere = "";
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}
		session = this.sessionFactory.openSession();
		result = (Number) session
				.createQuery(
						"select min(" + column_name + ") from " + entityName
								+ allWhere).uniqueResult();
		session.close();
		return result;

	}

	public Number sum(String entityName, String column_name, String whereClause) {
		Number result = 0;
		String allWhere = "";
		if ((whereClause != null) && (whereClause.trim().length() > 0)) {
			allWhere = " where " + whereClause;
		}
		session = this.sessionFactory.openSession();
		result = (Number) session
				.createQuery(
						"select sum(" + column_name + ") from " + entityName
								+ allWhere).uniqueResult();
		session.close();
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
		session = this.sessionFactory.openSession();
		org.hibernate.Query query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(limit);

		@SuppressWarnings("unchecked")
		List<DataObject> result = query.list();
		session.close();
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
		session = this.sessionFactory.openSession();
		org.hibernate.Query query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(limit);

		@SuppressWarnings("unchecked")
		List<DataObject> result = query.list();
		session.close();
		return result;
	}

}
