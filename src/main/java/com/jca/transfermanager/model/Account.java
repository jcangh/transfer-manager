package com.jca.transfermanager.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "account")
@Getter
@Setter
@XmlRootElement
@ToString
public class Account {

	@Id
	private String id;
	
	private String owner;
	
	private BigDecimal balance;
	
	public Account() {
		this.id = UUID.randomUUID().toString();
	}
}
