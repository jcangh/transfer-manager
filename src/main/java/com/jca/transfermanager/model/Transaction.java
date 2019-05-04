package com.jca.transfermanager.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
@Entity
@Table(name = "transaction")
public class Transaction {
	
	@Id
	private String id;
	
	private String originAccount;
	
	private String destinationAccount;
	
	private BigDecimal amount;
	
	public Transaction() {
		this.id = UUID.randomUUID().toString();
	}

}
