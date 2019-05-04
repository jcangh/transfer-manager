package com.jca.transfermanager.transfer;

import java.util.ArrayList;
import java.util.List;

import com.jca.transfermanager.dto.TransactionDTO;

public class TransferRuleValidator {
	
	public static void validate(TransactionDTO transaction) {
		
		List<TransferRule> rules = new ArrayList<>();
		rules.add(new PositiveAmountRule());
		rules.add(new SufficientAmountRule());
		
		rules.forEach(rule -> rule.validate(transaction));
	}

}
