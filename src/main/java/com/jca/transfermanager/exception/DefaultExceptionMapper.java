package com.jca.transfermanager.exception;

import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.jca.transfermanager.dto.ErrorMessageDTO;


@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable>{

	@Override
	public Response toResponse(Throwable exception) {
		
		if (exception instanceof AccountException) {
			return handleAccountException(exception);
		}
		if (exception instanceof PersistenceException) {
			return HandlePersistenceException(exception);
		}
		return Response.serverError().build();
	}
	
	
	private Response handleAccountException(Throwable ex) {
		return Response.status(Status.BAD_REQUEST)
				.entity( new ErrorMessageDTO(ex.getMessage())).build();
	}
	
	private Response HandlePersistenceException(Throwable ex) {
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(new ErrorMessageDTO(ex.getMessage())).build();
	}

}
