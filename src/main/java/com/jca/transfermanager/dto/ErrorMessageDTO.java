package com.jca.transfermanager.dto;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
public class ErrorMessageDTO {

	private String message;
	
	public ErrorMessageDTO() {}
	
	public ErrorMessageDTO(String message) {
		this.message = message;
	}
}
