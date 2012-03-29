package com.chinarewards.elt.common;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BaseDao<T> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected final String SEARCH = "SEARCH";
	protected final String COUNT = "COUNT";

	@Inject
	private Provider<EntityManager> entityManager;

	/**
	 * 
	 * 
	 * @return
	 */
	public EntityManager getEm() {
		try {
			entityManager.get().flush();
		} catch (Exception e) {
			System.out.println("flush="+e.getMessage());
		}
			
		return entityManager.get();
	}
	public EntityManager getEmNoFlush() {
				
		return entityManager.get();
	}

	/**
	 * persist T to DB .
	 * 
	 * @param t
	 *            one of your entity bean instance
	 */
	public T save(T t) {
		getEm().persist(t);
		return t;
	}
	public T saveNoFlush(T t) {
		getEmNoFlush().persist(t);
		return t;
	}

	/**
	 * meger modification to DB.
	 * 
	 * @param t
	 *            one of your entity bean instance
	 */
	public T update(T t) {
		getEm().merge(t);
		return t;
	}
	public T updateNoFlush(T t) {
		getEmNoFlush().merge(t);
		return t;
	}

	/**
	 * 
	 * delete t to DB.
	 * 
	 * @param t
	 */
	public void delete(T t) {
		getEm().remove(t);
	}
	public void deleteNoFlush(T t) {
		getEmNoFlush().remove(t);
	}

	/**
	 * find object by it's primary key .
	 * 
	 * @param T
	 *            t one of your entity bean instance
	 * @param id
	 *            primary key value
	 * 
	 * @return Object(T)
	 * 
	 */
	public T findById(Class<T> entityClass, Object id) {
		return (T) getEm().find(entityClass, id);
	}
	public T findByIdNoFlush(Class<T> entityClass, Object id) {
		return (T) getEmNoFlush().find(entityClass, id);
	}
}
