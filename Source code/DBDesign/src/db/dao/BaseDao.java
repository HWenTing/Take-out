package db.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("baseDao")
public class BaseDao<T> {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public Serializable save(T o) {
		return this.getCurrentSession().save(o);
	}

	public void delete(T o) {
		this.getCurrentSession().delete(o);
	}

	public void update(T o) {
		this.getCurrentSession().update(o);
	}

	public void saveOrUpdate(T o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	public List<T> find(String hql,Class<T> type) {
		return this.getCurrentSession().createQuery(hql,type).list();
	}
	
	private Query<T> setParam(Query<T> q,Object[] param){
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q;
	}
	
	private Query<T> setParam(Query<T> q,List<Object> param){
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q;
	}

	public List<T> find(String hql, Object[] param,Class<T> type) {
		Query<T> q = this.getCurrentSession().createQuery(hql,type);
		this.setParam(q,param);
		return q.list();
	}

	public List<T> find(String hql, List<Object> param,Class<T> type) {
		Query<T> q = this.getCurrentSession().createQuery(hql,type);
		this.setParam(q,param);
		return q.list();
	}

	public List<T> find(String hql, Object[] param, Integer page, Integer rows,Class<T> type) {
		Query<T> q = this.getCurrentSession().createQuery(hql,type);
		this.setParam(q, param);
		return this.find(q,page,rows);
	}

	public List<T> find(String hql, List<Object> param, Integer page, Integer rows,Class<T> type) {
		Query<T> q = this.getCurrentSession().createQuery(hql,type);
		this.setParam(q, param);
		return this.find(q,page,rows);
	}
	
	private List<T> find(Query<T> q, Integer page, Integer rows) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	
	public T get(Class<T> c, Serializable id) {
		return this.getCurrentSession().get(c, id);
	}

	public T get(String hql, Object[] param,Class<T> type) {
		List<T> l = this.find(hql, param,type);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	public T get(String hql, List<Object> param,Class<T> type) {
		List<T> l = this.find(hql, param,type);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	public Long count(String hql) {
		return (Long) this.getCurrentSession().createQuery(hql).uniqueResult();
	}

	public Long count(String hql, Object[] param) {
		Query<Long> q = this.getCurrentSession().createQuery(hql,Long.class);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return  q.uniqueResult();
	}

	public Long count(String hql, List<Object> param) {
		Query<Long> q = this.getCurrentSession().createQuery(hql,Long.class);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.uniqueResult();
	}

	public Integer executeHql(String hql) {
		return this.getCurrentSession().createQuery(hql).executeUpdate();
	}

	public Integer executeHql(String hql, Object[] param) {
		Query<Void> q = this.getCurrentSession().createQuery(hql,Void.class);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.executeUpdate();
	}

	public Integer executeHql(String hql, List<Object> param) {
		Query<Void> q = this.getCurrentSession().createQuery(hql,Void.class);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.executeUpdate();
	}

}
