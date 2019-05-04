package com.jca.transfermanager.dao.impl;

import org.hibernate.SessionFactory;

import com.jca.transfermanager.dao.AccountDAO;
import com.jca.transfermanager.model.Account;

public class AccountDAOImpl extends GenericDAOImpl<Account, String> implements AccountDAO {
	
	public AccountDAOImpl(SessionFactory sessionFactory) {
		super(Account.class, sessionFactory);
	}

}
