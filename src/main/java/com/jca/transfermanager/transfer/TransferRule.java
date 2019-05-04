package com.jca.transfermanager.transfer;

import com.jca.transfermanager.dto.TransactionDTO;
import com.jca.transfermanager.exception.AccountException;

public interface TransferRule {

	void validate(TransactionDTO transaction) throws AccountException;
}
