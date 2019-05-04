package com.jca.transfermanager.transfer;

import java.math.BigDecimal;

import com.jca.transfermanager.dto.TransactionDTO;
import com.jca.transfermanager.exception.AccountException;

public class SufficientAmountRule implements TransferRule {

	@Override
	public void validate(TransactionDTO transaction) throws AccountException {
		BigDecimal originAccountBalance = transaction.getOrigin().getBalance();
		if (transaction.getAmount().compareTo(originAccountBalance) > 0) {
			throw new AccountException("The origin account does not have sufficient funds");
		}
	}

}
