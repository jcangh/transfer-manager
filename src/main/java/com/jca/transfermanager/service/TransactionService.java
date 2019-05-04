package com.jca.transfermanager.service;


import java.math.BigDecimal;

import com.jca.transfermanager.dao.AccountDAO;
import com.jca.transfermanager.dao.TransactionDAO;
import com.jca.transfermanager.dto.TransactionDTO;
import com.jca.transfermanager.model.Account;
import com.jca.transfermanager.model.Transaction;
import com.jca.transfermanager.transfer.TransferRuleValidator;

public class TransactionService {

	private TransactionDAO transactionDAO;
	
	private AccountDAO accountDAO;
	
	public TransactionService(AccountDAO accountDAO, TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
		this.accountDAO = accountDAO;
	}
	
	public String createTransaction(Transaction transaction) {
		
		Account originAccount = this.accountDAO.findById(transaction.getOriginAccount());
		Account destinationAccount = this.accountDAO.findById(transaction.getDestinationAccount());
		
		TransactionDTO transactionDTO = new TransactionDTO(originAccount,destinationAccount,transaction.getAmount());
		
		TransferRuleValidator.validate(transactionDTO);
		
		BigDecimal newDestinationBalance = destinationAccount.getBalance().add(transaction.getAmount());
		destinationAccount.setBalance(newDestinationBalance);
		
		BigDecimal newOriginBalance = originAccount.getBalance().subtract(transaction.getAmount());
		originAccount.setBalance(newOriginBalance);
		
		this.accountDAO.update(destinationAccount);
		this.accountDAO.update(originAccount);
		
		this.transactionDAO.save(transaction);
		return  transaction.getId();
	}
	
	public Transaction getById(String id) {
		return this.transactionDAO.findById(id);
	}
	
	
}
