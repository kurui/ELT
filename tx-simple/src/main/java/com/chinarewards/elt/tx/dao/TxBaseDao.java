package com.chinarewards.elt.tx.dao;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class TxBaseDao<T> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected final String SEARCH = "SEARCH";
	protected final String COUNT = "COUNT";

	@Inject
	private EntityManager entityManager;

	/**
	 * 
	 * 
	 * @return
	 */
	public EntityManager getEm() {
		return entityManager;
	}

	/**
	 * persist T to DB .
	 * 
	 * @param t
	 *            one of your entity bean instance
	 */
	public T save(T t) {
		entityManager = getEm();
		entityManager.persist(t);

		return t;
	}

	/**
	 * meger modification to DB.
	 * 
	 * @param t
	 *            one of your entity bean instance
	 */
	public T update(T t) {
		entityManager = getEm();
		entityManager.merge(t);

		return t;
	}

	/**
	 * 
	 * delete t to DB.
	 * 
	 * @param t
	 */
	public void delete(T t) {
		entityManager = getEm();
		entityManager.remove(t);
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
		entityManager = getEm();
		return (T) entityManager.find(entityClass, id);
	}
}
