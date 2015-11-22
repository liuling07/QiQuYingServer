package com.lling.qiqu.dao.base;

/**   
 * @Title: BaseDAO.java
 * @Package com.bbc.dao
 * @Description: TODO
 * @author guosheng.zhu
 * @date 2011-7-31 下午09:44:33
 * @version V1.0   
 */

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.lling.qiqu.commons.Limit;
import com.lling.qiqu.commons.PageResult;
import com.lling.qiqu.utils.ObjectUtils;

/**
 * @ClassName: BaseDAO
 * @Description: BASE DAO类
 * @author guosheng.zhu
 * @date 2011-7-31 下午09:44:33
 */
@Repository("baseDao")
@SuppressWarnings("unchecked")
public class BaseDAO extends HibernateDaoSupport {
	protected Pattern countPattern = Pattern.compile("(^select)(.*?)( from .*)", Pattern.CASE_INSENSITIVE);
//	protected static final String countRex = "(?i)^select (?:(?!select|from)[\\s\\S])*(\\(select (?:(?!from)[\\s\\S])* from [^\\)]*\\))?(?:(?!select|from)[\\s\\S])*from";
	protected static final String countRex1 = "(?i)^select (?:(?!select|from)[\\s\\S])*(\\(select (?:(?!from)[\\s\\S])* from [^\\)]*\\)(?:(?!select|from)[^\\(])*)*from";
	protected static final String countSql = "select count(1) from ";
	protected static final String groupRex = ".*group\\s+by.*";

	public BaseDAO() {
	}

	/**
	 * @Title: buildParameters
	 * @Description: 组装参数
	 * @param @param query
	 * @param @param params
	 * @return void
	 */
	protected <T> void buildParameters(Query query, Object ... params) {
		int flag = 0;
		if (params == null || params.length == 0) {
			return;
		}
		for (Object item : params) {
			query.setParameter(flag++, item);
		}
	}

	/**
	 * @Title: contains
	 * @Description: 判断session中是否存在该对象
	 * @param @param t
	 * @param @return
	 * @param @throws DataAccessException
	 * @return boolean
	 */
	public <T> boolean contains(T t) throws DataAccessException {
		return getHibernateTemplate().contains(t);
	}

	/**
	 * @Title: load
	 * @Description: 根据其他条件load一条   谨慎使用！
	 * @param @param t
	 * @param @return
	 * @param @throws DataAccessException
	 * @return T
	 * @throws Exception 
	 */
	public <T> T load(T t) throws Exception {
		Class<?> clazz = t.getClass();
		Field[] filed = clazz.getDeclaredFields();
		final List<Object> paramsList = new LinkedList<Object>();
		final StringBuilder hsb = new StringBuilder(" from ");
		hsb.append(clazz.getName());
		hsb.append(" where 1=1 ");
		for (Field field : filed) {
			if (ObjectUtils.isEmpty(field.getAnnotations())) {
				continue;
			}
			String name = field.getName();
			String getMethodName = "get" + ObjectUtils.toFirstLetterUpperCase(name);
			Object value = t.getClass().getMethod(getMethodName).invoke(t);
			if (value == null) {
				continue;
			}
			//System.out.println(value);
			hsb.append(" and ");
			hsb.append(name);
			hsb.append("= ? ");
			paramsList.add(value);
		}
		if (hsb.indexOf("1=1 ") >= hsb.length() - 4) {
			logger.info("没有字段需要更新！");
			return null;
		}
		logger.info(hsb);
		return (T) getHibernateTemplate().execute(new HibernateCallback<T>() {
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hsb.toString());
				buildParameters(query, paramsList.toArray());
				query.setMaxResults(1);
				return (T) query.uniqueResult();
			}
		});
	}

	/**
	 * @Title: countByExample
	 * @Description: 根据模型统计
	 * @param @param entityBean
	 * @param @return
	 * @return int
	 */
	public <T> int countByExample(final T obj) {
		return (Integer) getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session s) throws HibernateException, SQLException {
				// 组装属性
				Criteria criteria = s.createCriteria(obj.getClass()).setProjection(Projections.projectionList().add(Projections.rowCount()))
						.add(Example.create(obj));
				if (getHibernateTemplate().isCacheQueries()) {
					criteria.setCacheable(true);
					if (getHibernateTemplate().getQueryCacheRegion() != null)
						criteria.setCacheRegion(getHibernateTemplate().getQueryCacheRegion());
				}
				if (getHibernateTemplate().getFetchSize() > 0)
					criteria.setFetchSize(getHibernateTemplate().getFetchSize());
				if (getHibernateTemplate().getMaxResults() > 0)
					criteria.setMaxResults(getHibernateTemplate().getMaxResults());
				SessionFactoryUtils.applyTransactionTimeout(criteria, getSessionFactory());
				return (Integer) criteria.uniqueResult();
			}
		});
	}

	/**
	 * @Title: countByHql
	 * @Description: 根据HQL统计查询结果数，多个参数调用
	 * @param @param hql
	 * @param @param params
	 * @param @return
	 * @param @throws DataAccessException
	 * @return int
	 */
	public <T> int countByHql(String hql, Object... params) throws DataAccessException {
		return DataAccessUtils.intResult(getHibernateTemplate().find(hql, params));
	}

	/**
	 * @Title: countBySql
	 * @Description: 根据SQL统计查询结果数，多个参数调用
	 * @param @param hql
	 * @param @param params
	 * @param @return
	 * @param @throws DataAccessException
	 * @return int
	 */
	public <T> int countBySql(String sql, Object... params) throws DataAccessException {
		return DataAccessUtils.intResult(executeSQLQuery("select count(*) " + sql.substring(sql.toLowerCase().indexOf("from")), params));
	}

	/**
	 * @Title: delete
	 * @Description: 根据类名和主键删除，注意：主键属性名必须为id
	 * @param @param objClass
	 * @param @param id
	 * @return void
	 */
	public <T> int delete(Class<T> objClass, Serializable id) {
		return executeUpdate("delete from " + objClass.getName() + " where id=?", id);
	}
	
	public void delete(Class<?> clazz, List<Map<String, Object>> listParams){
		if (clazz == null || listParams == null || listParams.size() == 0) {
			return;
		}
		for (Map<String, Object> params : listParams) {
			if (params == null || params.size() == 0) {
				continue;
			}
			StringBuffer _hql = new StringBuffer("delete from");
			List<Object> _objs = new ArrayList<Object>();
			_hql.append(" " + clazz.getName());
			if (params.size() != 0) {
				_hql.append(" where");
				int m = 0;
				for (Entry<String, Object> entry : params.entrySet()) {
					if(m++>0)_hql.append(" and");
					_hql.append(" "+entry.getKey()+"=?");
					_objs.add(entry.getValue());
				}
			}
			
			String hql = _hql.toString();
			Object[] objs = _objs.toArray();
			executeUpdate(hql, objs);
		}
		
	}

	/**
	 * @Title: delete
	 * @Description: 删除对象
	 * @param @param t
	 * @param @throws DataAccessException
	 * @return void
	 */
	public <T> void delete(T t) throws DataAccessException {
		getHibernateTemplate().delete(t);
	}

	/**
	 * @Title: delete
	 * @Description: 删除对象， 采用加锁机制，对数据安全性要求高的可以采用这个方法
	 * @param @param t
	 * @param @param lockMode
	 * @param @throws DataAccessException
	 * @return void
	 */
	public <T> void delete(T t, LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().delete(t, lockMode);
	}

	/**
	 * @Title: deleteAll
	 * @Description: 批量el删除对象集合
	 * @param @param entities
	 * @param @throws DataAccessException
	 * @return void
	 */
	public <T> int deleteAll(Class<T> objClass) throws DataAccessException {
		return executeUpdate("delete from " + objClass.getName());
	}

	/**
	 * @Title: deleteAll
	 * @Description: 批量el删除对象集合
	 * @param @param entities
	 * @param @throws DataAccessException
	 * @return void
	 */
	public <T> void deleteAll(Collection<Object> entities) throws DataAccessException {
		getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * @Title: executeSQLQuery
	 * @Description: 执行SQL查询，多个参数调用
	 * @param @param sql
	 * @param @param params
	 * @param @return
	 * @return List<T>
	 */
	public <T> List<T> executeSQLQuery(final String sql, final Class<T> objectClass, final Object... params) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql).addEntity("bean", objectClass);
				buildParameters(query, params);
				return query.list();
			}
		});
	}

	/**
	 * @Title: executeSQLQuery
	 * @Description: 执行SQL查询，多个参数调用
	 * @param @param sql
	 * @param @param params
	 * @param @return
	 * @return List<T>
	 */
	public <T> List<T> executeSQLQuery(final String sql, final Object... params) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				buildParameters(query, params);
				return query.list();
			}
		});
	}

	/**
	 * @Title: executeSQLQuery
	 * @Description: 执行SQL查询，多个参数调用
	 * @param @param sql
	 * @param @param params
	 * @param @return
	 * @return List<T>
	 */
	public <T> PageResult<T> executeSQLQuery(final String sql, final Class<T> clazz, final Limit limit, final Object... params) {
		String countSql = this.getCountSql(sql);
		List<T> list = (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.addEntity(clazz);
				query.setMaxResults(limit.getSize());
				query.setFirstResult(limit.getStart());
				buildParameters(query, params);
				return query.list();
			}
		});
		BigInteger bigTotalCount = this.getUniqueSQLResult(countSql, params);
		return new PageResult<T>(bigTotalCount.intValue(), limit, list);
	}

	/**
	 * @Title: executeSQLQuery
	 * @Description: 分页执行sql查询
	 * @param @param sql
	 * @param @param params
	 * @param @param pageSize
	 * @param @param startIndex
	 * @param @return
	 * @return List<T>
	 */
	public <T> List<T> executeSQLQuery(final String sql, final Object[] params, final int pageSize, final int startIndex) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				buildParameters(query, params);
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
				return query.list();
			}
		});
	}

	/**
	 * @Title: executeSQLQuery
	 * @Description: sql分页查询，设置返回对象
	 * @param @param sql
	 * @param @param params
	 * @param @param pageSize
	 * @param @param startIndex
	 * @param @param classObj
	 * @param @return
	 * @return List<T>
	 */
	public <T> List<T> executeSQLQuery(final String sql, final Object[] params, final int pageSize, final int startIndex, final Class<T> objectClass) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql).addEntity("bean", objectClass);
				buildParameters(query, params);
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
				return query.list();
			}
		});
	}

	/**
	 * @Title: executeSQLUpdate
	 * @Description: 执行SQL更新操作，多个参数情况
	 * @param @param sql
	 * @param @param params
	 * @param @return
	 * @return int
	 */
	public <T> int executeSQLUpdate(final String sql, final Object... params) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				buildParameters(query, params);
				return query.executeUpdate();
			}
		});
	}

	/**
	 * @Title: executeUpdate
	 * @Description: 执行HQL更新操作，多个参数情况
	 * @param @param hql
	 * @param @param params
	 * @param @return
	 * @return int
	 */
	public <T> int executeUpdate(final String hql, final Object... params) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				buildParameters(query, params);
				return query.executeUpdate();
			}
		});
	}

	/**
	 * @Title: find
	 * @Description: 执行HQL查询，无参数
	 * @param @param queryString
	 * @param @return
	 * @param @throws DataAccessException
	 * @return List<T>
	 */
	public <T> List<T> find(String queryString) throws DataAccessException {
		return (List<T>) getHibernateTemplate().find(queryString);
	}

	/**
	 * @Title: find
	 * @Description: 执行HQL查询，参数值采用'?'形式，顺序设置到object数组中
	 * @param @param queryString
	 * @param @param values
	 * @param @return
	 * @param @throws DataAccessException
	 * @return List<T>
	 */
	public <T> List<T> find(String queryString, Object... values) throws DataAccessException {
		return (List<T>) getHibernateTemplate().find(queryString, values);
	}
	
	public <T> List<T> find(Class<T> clazz,  Map<String, Object> params){
		if (clazz == null || params == null) {
			return null;
		}
		StringBuffer _hql = new StringBuffer(" from");
		List<Object> _objs = new ArrayList<Object>();
		_hql.append(" " + clazz.getName());
		String _orderBy = null;
		if (params.size() != 0) {
			_hql.append(" where");
			int m = 0;
			for (Entry<String, Object> entry : params.entrySet()) {
				if ("_orderBy".equals(entry.getKey())) {
					_orderBy = (String)entry.getValue();
					continue;
				}
				if(m++>0)_hql.append(" and");
				_hql.append(" "+entry.getKey()+"=?");
				_objs.add(entry.getValue());
			}
		}
		if (_orderBy != null) {
			_hql.append(" order by " + _orderBy);
		}
		
		String hql = _hql.toString();
		Object[] objs = _objs.toArray();
		
		List<T> list = find(hql, objs);
		return list;
	}


	/**
	 * @Title: findByCriteria
	 * @Description: 根据DetachedCriteria动态组装HQL查询 修改：改为protected，不推荐在service层调用 -
	 *               fengli 2011-08-03
	 * @param @param criteria
	 * @param @return
	 * @return List<T>
	 */
	protected <T> List<T> findByCriteria(DetachedCriteria criteria) {
		return getHibernateTemplate().findByCriteria(criteria);
	}

	// public <T> List<T> findByNamedQuery(String queryName) throws DataAccessException
	// {
	// return getHibernateTemplate().findByNamedQuery(queryName);
	// }
	//
	// public <T> List<T> findByNamedQuery(String queryName, Object value) throws
	// DataAccessException {
	// return getHibernateTemplate().findByNamedQuery(queryName, value);
	// }
	//
	// public <T> List<T> findByNamedQuery(String queryName, Object[] values) throws
	// DataAccessException {
	// return getHibernateTemplate().findByNamedQuery(queryName, values);
	// }

	/**
	 * @Title: findByCriteria
	 * @Description: 根据DetachedCriteria动态组装HQL分页查询，
	 *               修改：改为protected，不推荐在service层调用 - fengli 2011-08-03
	 * @param @param criteria
	 * @param @param startIndex
	 * @param @param size
	 * @param @return
	 * @return List<T>
	 */
	protected <T> List<T> findByCriteria(DetachedCriteria criteria, int startIndex, int size) {
		return getHibernateTemplate().findByCriteria(criteria, startIndex, size);
	}

	/**
	 * @Title: findByExample
	 * @Description: 根据对象example查询 ，简单的查询可以采用该方法，不用写HQL，
	 *               避免属性名修改带来的隐性错误，仅限于做=查询，不支持排序控制
	 * @param @param t
	 * @param @return
	 * @param @throws DataAccessException
	 * @return List<T>
	 */
	public <T> List<T> findByExample(T t) throws DataAccessException {
		return getHibernateTemplate().findByExample(t);
	}

	/**
	 * @Title: findByExample
	 * @Description: 根据对象example查询 ，简单的查询可以采用该方法，不用写HQL，
	 *               避免属性名修改带来的隐性错误，仅限于做=查询，不支持排序控制
	 * @param @param t
	 * @param @return
	 * @param @throws DataAccessException
	 * @return List<T>
	 */
	public <T> T findOneByExample(T t) throws DataAccessException {
		List<T> list = getHibernateTemplate().findByExample(t);
		if (ObjectUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * @Title: findByExample
	 * @Description: 根据对象example查询，设置分页，简单的查询可以采用该方法，不用写HQL，
	 *               避免属性名修改带来的隐性错误，仅限于做=查询，不支持排序控制
	 * @param @param t
	 * @param @param startIndex
	 * @param @param size
	 * @param @return
	 * @param @throws DataAccessException
	 * @return List<T>
	 */
	public <T> List<T> findByExample(T t, int startIndex, int size) throws DataAccessException {
		return getHibernateTemplate().findByExample(t, startIndex, size);
	}

	/**
	  * 
	  * @author Jon Chiang
	  * @create_date 2014-5-9 下午2:41:58
	  * @param hql
	  * @param pageSize
	  * @param startIndex
	  * @return
	 */
	public <T> List<T> findBySQL(final String sql, final Limit limit, final Class<T> clazz, final Object... params) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.addEntity("bean", clazz);
				buildParameters(query, params);
				if (null != limit) {
					query.setFirstResult(limit.getStart());
					query.setMaxResults(limit.getSize());
				}
				return query.list();
			}
		});
	}

	/**
	 * 
	 * @author Jon Chiang
	 * @create_date 2014-5-9 下午2:41:58
	 * @param hql
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	public <T> List<T> findBySQL(final String sql, final Class<T> clazz, final Object... params) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.addEntity("bean", clazz);
				buildParameters(query, params);
				return query.list();
			}
		});
	}

	/**
	 * 
	 * @author Jon Chiang
	 * @create_date 2014-5-9 下午2:41:58
	 * @param hql
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	public <T> List<T> findBySQL(final String sql, final Limit limit, final Object... params) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				buildParameters(query, params);
				if (null != limit) {
					query.setFirstResult(limit.getStart());
					query.setMaxResults(limit.getSize());
				}
				return query.list();
			}
		});
	}

	public <T> PageResult<T> findPageBySQL(final String sql, final Limit limit, final Class<T> clazz, final Object... params) {
		List<T> list = (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.addEntity("bean", clazz);
				buildParameters(query, params);
				if (null != limit) {
					query.setFirstResult(limit.getStart());
					query.setMaxResults(limit.getSize());
				}
				return query.list();
			}
		});
		BigInteger totalCount = null;
		String csql = this.getCountSql(sql);
		if (null != csql) {
			totalCount = this.getUniqueSQLResult(csql, params);
		}
		return new PageResult<T>(totalCount == null ? 0 : totalCount.intValue(), limit, list);
	}

	/**
	 * @Title: getUniqueSQLResultByHql
	 * @Description: 查找唯一结果集，多个参数形式
	 * @param @param hql
	 * @param @param objs
	 * @param @return
	 * @return Object
	 */
	public <T> T getUniqueSQLResult(final String sql, final Object... params) {
		return (T) getHibernateTemplate().execute(new HibernateCallback<T>() {
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				String limitSql = sql;
				if (!sql.contains("limit")) {
					limitSql += " limit 1";
				}
				Query query = session.createSQLQuery(limitSql);
				buildParameters(query, params);
				return (T) query.uniqueResult();
			}
		});
	}

	/**
	 * @Title: findMapsBySqlQuery
	 * @Description: 返回map
	 * @param @param hql
	 * @param @param page
	 * @param @return
	 * @return List<T>
	 */
	public <T> List<T> findMapsBySqlQuery(final String sql, final Limit limit, final Object... params) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				buildParameters(query, params);
				if (null != limit) {
					query.setFirstResult(limit.getStart());
					query.setMaxResults(limit.getSize());
				}
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return query.list();
			}
		});
	}
	
	/**
	 * @Title: findMapsBySqlQuery
	 * @Description: 返回map
	 * @param @param hql
	 * @param @param page
	 * @param @return
	 * @return List<T>
	 */
	public <T> List<T> findMapsBySqlQuery(final String sql,final Object... params) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				buildParameters(query, params);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return query.list();
			}
		});
	}

	/**
	 * @Title: findPageByQuery
	 * @Description: 返回map
	 * @param @param hql
	 * @param @param page
	 * @param @return
	 * @return List<T>
	 */
	public <T> List<T> findMapsBySqlQuery(final String sql) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				// 组建参数
				return query.list();
			}
		});
	}

	/**
	 * 自定义的class
	 * @author Jon Chiang
	 * @create_date 2014-5-9 下午2:41:58
	 * @param hql
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	public <T> List<T> findMyObjectBySQL(final String sql, final Class<T> clazz, final Object... params) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setResultTransformer(Transformers.aliasToBean(clazz));
				buildParameters(query, params);
				return query.list();
			}
		});
	}

	/**
	 * 自定义的class
	 * @author Jon Chiang
	 * @create_date 2014-5-9 下午2:41:58
	 * @param hql
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	public <T> List<T> findMyObjectBySQL(final String sql, final Limit limit, final Class<T> clazz, final Object... params) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setResultTransformer(Transformers.aliasToBean(clazz));
				buildParameters(query, params);
				if (null != limit) {
					query.setFirstResult(limit.getStart());
					query.setMaxResults(limit.getSize());
				}
				return query.list();
			}
		});
	}

	/**
	 * @Title: findPageByQuery
	 * @Description: 分页查询，无hql参数
	 * @param @param hql
	 * @param @param pageSize
	 * @param @param startIndex
	 * @param @return
	 * @return List<T>
	 */
	public <T> List<T> findPageByQuery(final String hql, final int pageSize, final int startIndex) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
				return query.list();
			}
		});
	}

	/**
	 * @Title: findPageByQuery
	 * @Description: 分页查询，提供参数集合
	 * @param @param hql
	 * @param @param params
	 * @param @param pageSize
	 * @param @param startIndex
	 * @param @return
	 * @return List<T>
	 */
	public <T> List<T> findPageByQuery(final String hql, final int pageSize, final int startIndex, final Object... params) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				// 组建参数
				buildParameters(query, params);
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
				return query.list();
			}
		});
	}

	/**
	 * @Title: findPageByQuery
	 * @Description: 分页查询，加入PageUtil工具类，无参数查询
	 * @param @param hql
	 * @param @param page
	 * @param @return
	 * @return List<T>
	 */
	public <T> PageResult<T> findPageByQuery(final String hql, final Limit limit, final Object... params) {
		List<T> list = getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				// 组建参数
				query.setFirstResult(limit.getStart());
				query.setMaxResults(limit.getSize());
				buildParameters(query, params);
				return query.list();
			}
		});
		int totalCount = countByHql("select count(*) " + hql.substring(hql.toLowerCase().indexOf("from")), params);
		return new PageResult<T>(totalCount, limit, list);
	}

	/**
	 * @Title: findPageByQuery
	 * @Description: 分页查询，加入PageUtil工具类，无参数查询
	 * @param @param hql
	 * @param @param page
	 * @param @return
	 * @return List<T>
	 */
	public <T> List<T> findPageByQuery(final String hql, final PageResult<T> page) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				page.setTotalCount(countByHql("select count(*) " + hql.substring(hql.toLowerCase().indexOf("from"))));
				Query query = session.createQuery(hql);
				// 组建参数
				query.setFirstResult(page.getFirstResult());
				query.setMaxResults(page.getPageSize());
				return query.list();
			}
		});
	}

	/**
	 * @Title: findPageByQuery
	 * @Description: 分页查询，加入PageUtil工具类，单个参数查询
	 * @param @param hql
	 * @param @param page
	 * @param @param param
	 * @param @return
	 * @return List<T>
	 */
	public <T> List<T> findPageByQuery(final String hql, final PageResult<T> page, final Object param) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				page.setTotalCount(countByHql("select count(*) " + hql.substring(hql.toLowerCase().indexOf("from")), param));
				Query query = session.createQuery(hql);
				// 组建参数
				query.setParameter(0, param);
				query.setFirstResult(page.getFirstResult());
				query.setMaxResults(page.getPageSize());
				return query.list();
			}
		});
	}

	/**
	 * @Title: findPageByQuery
	 * @Description: 分页查询，加入PageUtil工具类，多个参数查询
	 * @param @param hql
	 * @param @param page
	 * @param @param params
	 * @param @return
	 * @return List<T>
	 */
	public <T> List<T> findPageByQuery(final String hql, final PageResult<T> page, final Object[] params) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				page.setTotalCount(countByHql("select count(*) " + hql.substring(hql.toLowerCase().indexOf("from")), params));
				Query query = session.createQuery(hql);
				// 组建参数
				buildParameters(query, params);
				query.setFirstResult(page.getFirstResult());
				query.setMaxResults(page.getPageSize());
				return query.list();
			}
		});
	}

	/**
	 * @Title: get
	 * @Description: 根据类和ID获取对象
	 * @param @param objClass
	 * @param @param id
	 * @param @return
	 * @param @throws DataAccessException
	 * @return Object
	 */
	public <T> T get(Class<T> objClass, Serializable id) throws DataAccessException {
		return getHibernateTemplate().get(objClass, id);
	}

	/**
	 * @Title: getUniqueResultByHql
	 * @Description: 查找唯一结果集，多个参数形式
	 * @param @param hql
	 * @param @param objs
	 * @param @return
	 * @return Object
	 */
	public <T> T getUniqueResult(final String hql, final Object... params) {
		return (T) getHibernateTemplate().execute(new HibernateCallback<T>() {
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				buildParameters(query, params);
				return (T) query.uniqueResult();
			}
		});
	}
	
	public <T> T getUniqueResult(Class<T> clazz,  Map<String, Object> params){
		if (clazz == null || params == null) {
			return null;
		}
		StringBuffer _hql = new StringBuffer(" from");
		List<Object> _objs = new ArrayList<Object>();
		_hql.append(" " + clazz.getName());
		if (params.size() != 0) {
			_hql.append(" where");
			int m = 0;
			for (Entry<String, Object> entry : params.entrySet()) {
				if(m++>0)_hql.append(" and");
				_hql.append(" "+entry.getKey()+"=?");
				_objs.add(entry.getValue());
			}
		}
		
		String hql = _hql.toString();
		Object[] objs = _objs.toArray();
		
		List<T> list = find(hql, objs);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * @Title: getUniqueResultByHql
	 * @Description: 查找唯一结果集，多个参数形式
	 * @param @param hql
	 * @param @param objs
	 * @param @return
	 * @return Object
	 */
	public <T> T getUniqueResultByHql(String hql, Object... objs) {
		List<T> list = find(hql, objs);
		if (list == null || list.size() != 1) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * @Title: getUniqueSQLResultByHql
	 * @Description: 查找唯一结果集，多个参数形式
	 * @param @param hql
	 * @param @param objs
	 * @param @return
	 * @return Object
	 */
	public <T> T getUniqueSQLResult(final String sql, final Class<T> objClass, final Object... params) {
		return (T) getHibernateTemplate().execute(new HibernateCallback<T>() {
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				String limitSql = sql;
				if (!sql.contains("limit")) {
					limitSql += " limit 1";
				}
				Query query = null;
				if (null != objClass) {
					query = session.createSQLQuery(limitSql).addEntity("bean", objClass);
				} else {
					query = session.createSQLQuery(limitSql);
				}
				buildParameters(query, params);
				return (T) query.uniqueResult();
			}
		});
	}

	/**
	 * @Title: refresh
	 * @Description: 刷新持久层对象到session缓存
	 * @param @param t
	 * @param @throws DataAccessException
	 * @return void
	 */
	public <T> void refresh(T t) throws DataAccessException {
		getHibernateTemplate().refresh(t);
	}

	/**
	 * @Title: refresh
	 * @Description: 刷新持久层对象到session缓存，采用加锁机制
	 * @param @param t
	 * @param @param lockMode
	 * @param @throws DataAccessException
	 * @return void
	 */
	public <T> void refresh(T t, LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().refresh(t, lockMode);
	}

	/**
	 * @Title: save
	 * @Description: 新增记录
	 * @param @param t
	 * @param @return
	 * @param @throws DataAccessException
	 * @return Serializable
	 */
	public <T> Serializable save(T t) throws DataAccessException {
		return getHibernateTemplate().save(t);
	}

	/**
	 * @Title: saveOrUpdate
	 * @Description: 新增或更新操作，如果存在ID则更新
	 * @param @param t
	 * @param @throws DataAccessException
	 * @return void
	 */
	public <T> void saveOrUpdate(T t) throws DataAccessException {
		this.getHibernateTemplate().saveOrUpdate(t);
	}

	/**
	  * 
	  * @author Jon Chiang
	  * @create_date 2014-6-5 下午3:31:46
	  * @param t
	  * @throws Exception
	 */
	public <T> void updateSelective(T t) throws Exception {
		Class<?> clazz = t.getClass();
		Field[] filed = clazz.getDeclaredFields();
		List<Object> paramsList = new LinkedList<Object>();
		Integer id = (Integer) clazz.getDeclaredMethod("getId").invoke(t);
		if (ObjectUtils.isEmpty(id)) {
			throw new RuntimeException("更新时id不能为空！");
		}
		StringBuilder hsb = new StringBuilder(" update ");
		hsb.append(clazz.getName());
		hsb.append(" set ");
		for (Field field : filed) {
			if (ObjectUtils.isEmpty(field.getAnnotations())) {
				continue;
			}
			String name = field.getName();
			String getMethodName = "get" + ObjectUtils.toFirstLetterUpperCase(name);
			Object value = t.getClass().getMethod(getMethodName).invoke(t);
			if (value == null) {
				continue;
			}
			//System.out.println(value);
			hsb.append(name);
			hsb.append("= ? ");
			hsb.append(",");
			paramsList.add(value);
		}
		if (hsb.indexOf("set ") >= hsb.length() - 4) {
			logger.info("没有字段需要更新！");
			return;
		} else {
			hsb.replace(hsb.length() - 1, hsb.length(), "");
		}
		hsb.append(" where id = ");
		hsb.append(id);
		logger.info(hsb);
		executeUpdate(hsb.toString(), paramsList.toArray());
	}

	/*public static void main(String[] args) {
		final BaseDAO bd = new BaseDAO();
		final User user = new User();
		user.setId(1);
		user.setStatus((byte) 0x01);
		try {
			bd.updateSelective(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long time1 = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			new Runnable() {
				
				@Override
				public void run() {
					try {
						bd.updateSelective(user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.run();
		}
		long time2 = System.currentTimeMillis();
		System.out.println(time2 - time1);
	}*/

	/**
	 * @Title: saveOrUpdateAll
	 * @Description: 批量新增或更新（ID存在）记录集合
	 * @param @param entities
	 * @param @throws DataAccessException
	 * @return void
	 */
	public <T> void saveOrUpdateAll(Collection<T> entities) throws DataAccessException {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	@Autowired
	public <T> void setSF(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	/**
	 * @Title: update
	 * @Description: 更新操作
	 * @param @param t
	 * @param @throws DataAccessException
	 * @return void
	 */
	public <T> void update(T t) throws DataAccessException {
		getHibernateTemplate().update(t);
		getHibernateTemplate().flush();
	}

	/**
	 * @Title: update
	 * @Description: 更新操作，采用加锁机制
	 * @param @param t
	 * @param @param lockMode
	 * @param @throws DataAccessException
	 * @return void
	 */
	public <T> void update(T t, LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().update(t, lockMode);
	}
	
	/**
	* @Title: update 
	* @Description: 修改操作 
	* @param @param clazz
	* @param @param mapSet
	* @param @param mapWhere  
	* @return void
	 */
	public void update(Class<?> clazz, Map<String, Object> mapSet, Map<String, Object> mapWhere){
		if (clazz == null || mapSet == null || mapSet.size() == 0 || mapWhere == null || mapWhere.size() == 0 ) {
			return;
		}
		StringBuffer _hql = new StringBuffer("update");
		List<Object> _objs = new ArrayList<Object>();
		_hql.append(" " + clazz.getName());
		int m = 0;
		_hql.append(" set");
		for (Entry<String, Object> entry : mapSet.entrySet()) {
			if(m++>0)_hql.append(" ,");
			_hql.append(" "+entry.getKey()+"=?");
			_objs.add(entry.getValue());
		}
		m = 0;
		_hql.append(" where");
		for (Entry<String, Object> entry : mapWhere.entrySet()) {
			if(m++>0)_hql.append(" and");
			_hql.append(" "+entry.getKey()+"=?");
			_objs.add(entry.getValue());
		}
		
		String hql = _hql.toString();
		Object[] objs = _objs.toArray();
		executeUpdate(hql, objs);
		
	}

	protected String getCountSql(String sql) {
		/*Matcher m = countPattern.matcher(sql);
		String csql = null;
		if (m.find()) {
			csql = m.replaceFirst("select count(1) from ");
		}
		return csql;*/

		String doneSql = "";
		if (sql.matches(groupRex)) {
			doneSql = "select count(1) from (" + sql + ") a";
		} else {
			doneSql = sql.replaceAll(countRex1, countSql);
		}
		return doneSql;
	}

	public static void main(String[] args) {
		
	}

	/**
	  * @author Jon Chiang
	  * @create_date 2014-6-18 下午5:58:24
	  * @param string
	  * @param params
	  * @param agent
	  */
	protected void addEqualsCond(StringBuilder sb, List<Object> params, String key, Object value) {
		if (ObjectUtils.isNotEmpty(value)) {
			sb.append(" and ");
			sb.append(key);
			sb.append(" = ? ");
			params.add(value);
		}
	}

	/**
	 * @author Jon Chiang
	 * @create_date 2014-6-18 下午5:58:24
	 * @param string
	 * @param params
	 * @param agent
	 */
	protected void addGtCond(StringBuilder sb, List<Object> params, String key, Object value) {
		if (ObjectUtils.isNotEmpty(value)) {
			sb.append(" and ");
			sb.append(key);
			sb.append(" >= ? ");
			params.add(value);
		}
	}

	/**
	 * @author Jon Chiang
	 * @create_date 2014-6-18 下午5:58:24
	 * @param string
	 * @param params
	 * @param agent
	 */
	protected void addLtCond(StringBuilder sb, List<Object> params, String key, Object value) {
		if (ObjectUtils.isNotEmpty(value)) {
			sb.append(" and ");
			sb.append(key);
			sb.append(" <= ? ");
			params.add(value);
		}
	}
	
	/**
	 * 获取最大值
	   * @author zhr
	   * @create_date 2014-10-10 下午12:19:52
	   * @param sql
	   * @param params
	   * @return
	 */
	public Integer getMaxBySql(String sql, Object... params){
		int totalCount = countBySql("select count(*) " + sql.substring(sql.toLowerCase().indexOf("from")), params);
		if(totalCount==0)
			return 0;
		List<Integer> max = this.executeSQLQuery(sql);			
		return max.get(0);
	}
	
	/**
	 * 获取最大值
	   * @author zhr
	   * @create_date 2014-10-10 下午12:21:39
	   * @param hql
	   * @param params
	   * @return
	 */
	public Integer getMaxByHql(String hql, Object... params){
		int totalCount = countByHql("select count(*) " + hql.substring(hql.toLowerCase().indexOf("from")), params);
		if(totalCount==0)
			return 0;
		List<Integer> max = this.find(hql);			
		return max.get(0);
	}
	
	/**
	 * @Title: find
	 * @Description: 执行HQL查询，无参数
	 * @param @param queryString
	 * @param @return
	 * @param @throws DataAccessException
	 * @return List<T>
	 */
	public <T> List<T> findFirst(String queryString, Object... values) throws DataAccessException {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		hibernateTemplate.setMaxResults(1);
		return (List<T>) hibernateTemplate.find(queryString, values);
	}
	
}
