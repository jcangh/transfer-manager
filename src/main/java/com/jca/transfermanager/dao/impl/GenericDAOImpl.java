package com.jca.transfermanager.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;

import com.jca.transfermanager.dao.GenericDAO;

public class GenericDAOImpl<T,PK extends Serializable> implements GenericDAO<T, PK> {

	private Class<T> baseClass;
	
	private SessionFactory session=null;
	
	public GenericDAOImpl(Class<T> baseClass, SessionFactory session) {
		super();
		this.baseClass = baseClass;
		this.session = session;
	}
	
	@Override
	public T save(T o) throws PersistenceException {
		Transaction transaction = null;
		try (Session session = this.session.openSession()){
			transaction = session.beginTransaction();
			session.save(o);
			transaction.commit();
		}catch(final Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
			throw new PersistenceException("Error saving ",e);
		}
		return o;
	}

	@Override
	public T findById(PK id) throws PersistenceException {
		try (Session session = this.session.openSession()){
			T o = session.find(baseClass, id);
			if (null == o) {
				throw new PersistenceException(baseClass.getSimpleName()+" "+id+" not found");
			}
			return (T) session.find(baseClass, id);
		}catch(final HibernateException e) {
			throw new PersistenceException("Error finding "+baseClass.getName(),e);
		}
	}

	@Override
	public void delete(T o) throws PersistenceException {
		try (Session session = this.session.openSession()){
			session.delete(o);
		}catch(final HibernateException e) {
			throw new PersistenceException("Error deleting ",e);
		}
	}

	@Override
	public List<T> findAll() throws PersistenceException {
		try (Session session = this.session.openSession()){
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> criteria = builder.createQuery(baseClass);
			criteria.from(baseClass);
			return session.createQuery(criteria).getResultList();
		}catch(final HibernateException e) {
			throw new PersistenceException("Error getting all ",e);
		}
	}

	@Override
	public List<T> findByCriteria(List<Criterion> criteriaList) throws PersistenceException {
		return null;
	}

	@Override
	public void update(T o) throws PersistenceException {
		Transaction transaction = null;
		try (Session session = this.session.openSession()){
			transaction = session.beginTransaction();
			session.merge(o);
			transaction.commit();
		}catch(final Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
			throw new PersistenceException("Error updating ",e);
		}
	}

}
