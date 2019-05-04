package com.jca.transfermanager.service;

import java.math.BigDecimal;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

import com.jca.transfermanager.dao.AccountDAO;
import com.jca.transfermanager.dao.TransactionDAO;
import com.jca.transfermanager.dao.impl.AccountDAOImpl;
import com.jca.transfermanager.dao.impl.TransactionDAOImpl;
import com.jca.transfermanager.dao.util.HibernateUtil;
import com.jca.transfermanager.model.Account;
import com.jca.transfermanager.model.Transaction;

public class TransactionServiceTest {

	@Spy
	private TransactionService service;
	
	private AccountDAO accountDAO;
	
	private TransactionDAO transaccionDAO;
	
	private Account originAccount;
	
	private Account destinationAccount;
	
	@Before
	public void init() {
		SessionFactory session = HibernateUtil.getSessionFactory();
		accountDAO = new AccountDAOImpl(session);
		transaccionDAO = new TransactionDAOImpl(session);
		service = new TransactionService(accountDAO,transaccionDAO);
		
		originAccount = new Account();
		originAccount.setBalance(new BigDecimal(12000));
		originAccount.setOwner("User-12345");
		
		destinationAccount = new Account();
		destinationAccount.setBalance(new BigDecimal(10000));
		destinationAccount.setOwner("User-00934");
		
		accountDAO.save(originAccount);
		accountDAO.save(destinationAccount);
	}
	
	
	@Test
	public void transferAmountSuccess() {
		//Given
		Transaction tx = new Transaction();
		tx.setAmount(new BigDecimal(1000));
		tx.setOriginAccount(originAccount.getId());
		tx.setDestinationAccount(destinationAccount.getId());
		BigDecimal newOriginBalance = originAccount.getBalance().subtract(tx.getAmount());
		BigDecimal newDestinationBalance = destinationAccount.getBalance().add(tx.getAmount());
		
		//When
		String transactionId = service.createTransaction(tx);
		Account updatedOriginAccount = accountDAO.findById(originAccount.getId());
		Account updatedDestinationAccount = accountDAO.findById(destinationAccount.getId());
		
		//Then
		Assert.assertTrue(transactionId != null);
		Assert.assertEquals("Expected balance in origin account", newOriginBalance.stripTrailingZeros(),updatedOriginAccount.getBalance().stripTrailingZeros());
		Assert.assertEquals("Expected balance in destination account", newDestinationBalance.stripTrailingZeros(),updatedDestinationAccount.getBalance().stripTrailingZeros());
	}
	
	@Test
	public void transferNegativeAmount() {
		//Given
		Transaction tx = new Transaction();
		tx.setAmount(new BigDecimal(-1000));
		tx.setOriginAccount(originAccount.getId());
		tx.setDestinationAccount(destinationAccount.getId());
		String expectedExceptionMessage = "Transaction amount can not be negative";
		String actualExceptionMessge = null;
		
		//When
		try {
			service.createTransaction(tx);
		}catch(final Exception e) {
			actualExceptionMessge = e.getMessage();
		}
		
		//Then
		Assert.assertEquals(expectedExceptionMessage, actualExceptionMessge);
		
	}
	
	@Test
	public void transferWithInsufficientFunds() {
		//Given
		Transaction tx = new Transaction();
		tx.setAmount(new BigDecimal(1000000));
		tx.setOriginAccount(originAccount.getId());
		tx.setDestinationAccount(destinationAccount.getId());
		String expectedExceptionMessage = "The origin account does not have sufficient funds";
		String actualExceptionMessge = null;
		
		//When
		try {
			service.createTransaction(tx);
		}catch(final Exception e) {
			actualExceptionMessge = e.getMessage();
		}
		
		//Then
		Assert.assertEquals(expectedExceptionMessage, actualExceptionMessge);
		
	}
	
	
}
