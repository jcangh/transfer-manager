package com.jca.transfermanager.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.criterion.Criterion;

public interface GenericDAO<T,PK> {

	T save(T o) throws PersistenceException;
	
	void update(T o) throws PersistenceException;
	
	T findById(PK id) throws PersistenceException;
	
	void delete(T o) throws PersistenceException;
	
	List<T> findAll() throws PersistenceException;
	
	List<T> findByCriteria(List<Criterion> criteria) throws PersistenceException;
}
