package com.jca.transfermanager.dao.impl;

import org.hibernate.SessionFactory;

import com.jca.transfermanager.dao.TransactionDAO;
import com.jca.transfermanager.model.Transaction;

public class TransactionDAOImpl extends GenericDAOImpl<Transaction,String> implements TransactionDAO{

	public TransactionDAOImpl(SessionFactory sessionFactory) {
		super(Transaction.class,sessionFactory);
	}
}
