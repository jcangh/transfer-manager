package com.jca.transfermanager.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

import com.jca.transfermanager.dao.AccountDAO;
import com.jca.transfermanager.dao.impl.AccountDAOImpl;
import com.jca.transfermanager.dao.util.HibernateUtil;
import com.jca.transfermanager.model.Account;

public class AccountServiceTest {

	@Spy
	private AccountService service;
	
	private AccountDAO accountDAO;
	
	@Before
	public  void setup() {
		this.accountDAO = new AccountDAOImpl(HibernateUtil.getSessionFactory());
		service = new AccountService(accountDAO);
	}
	
	@Test
	public void testCreateAccountSuccess() {
		//Given
		Account account = new Account();
		account.setBalance(new BigDecimal(12000.00));
		account.setOwner("User-12345");
		
		//When
		String accountId = service.create(account);
		Account createdAccount = service.getById(accountId);
		
		//Then
		String[] ignoredFields = new String[] {"balance"};
		Assert.assertTrue(accountId != null);
		assertThat(account).isEqualToIgnoringGivenFields(createdAccount, ignoredFields);
		Assert.assertEquals(account.getBalance().stripTrailingZeros(), 
				createdAccount.getBalance().stripTrailingZeros());
		
	}
}
