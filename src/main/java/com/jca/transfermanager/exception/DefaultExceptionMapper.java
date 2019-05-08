package com.jca.transfermanager.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.jca.transfermanager.dto.ErrorMessageDTO;


@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable>{

	private static final String SERVER_ERROR = "There was an internal server error";
	
	@Override
	public Response toResponse(Throwable exception) {
		
		if (exception instanceof AccountException) {
			return handleAccountException(exception);
		}
		if (exception instanceof NotFoundException) {
			return handleNotFoundException(exception);
		}
		return handleInternalError();
	}
	
	
	private Response handleAccountException(Throwable ex) {
		return Response.status(Status.BAD_REQUEST)
				.entity( new ErrorMessageDTO(ex.getMessage())).build();
	}
	
	private Response handleInternalError() {
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(new ErrorMessageDTO(SERVER_ERROR)).build();
	}
	
	public Response handleNotFoundException(Throwable ex) {
		return Response.status(Status.NOT_FOUND)
				.entity(new ErrorMessageDTO(ex.getMessage())).build();
	}

}
