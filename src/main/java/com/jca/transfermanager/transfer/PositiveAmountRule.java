package com.jca.transfermanager.transfer;

import java.math.BigDecimal;

import com.jca.transfermanager.dto.TransactionDTO;
import com.jca.transfermanager.exception.AccountException;

public class PositiveAmountRule implements TransferRule {

	@Override
	public void validate(TransactionDTO transaction) throws AccountException {
		if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
			throw new AccountException("Transaction amount can not be negative");
		}
	}

}
