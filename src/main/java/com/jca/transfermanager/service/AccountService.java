package com.jca.transfermanager.service;

import com.jca.transfermanager.dao.AccountDAO;
import com.jca.transfermanager.model.Account;

public class AccountService {

	private AccountDAO accountDAO;
	
	public AccountService(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	
	public String create(Account account) {
		this.accountDAO.save(account);
		return account.getId();
	}
	
	public Account getById(String id) {
		return this.accountDAO.findById(id);
	}
	
	public void update(String id, Account account) {
		Account current = this.getById(id);
		current.setBalance(account.getBalance());
		current.setOwner(account.getOwner());
		this.accountDAO.update(current);
	}
}
