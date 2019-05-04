package com.jca.transfermanager.dto;

import java.math.BigDecimal;

import com.jca.transfermanager.model.Account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {

	private Account origin;
	
	private Account destination;
	
	private BigDecimal amount;
	
	public TransactionDTO() {}

	public TransactionDTO(Account origin, Account destination, BigDecimal amount) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.amount = amount;
	}
}
