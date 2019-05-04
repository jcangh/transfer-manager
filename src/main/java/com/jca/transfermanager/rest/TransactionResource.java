package com.jca.transfermanager.rest;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.hibernate.SessionFactory;

import com.jca.transfermanager.dao.impl.AccountDAOImpl;
import com.jca.transfermanager.dao.impl.TransactionDAOImpl;
import com.jca.transfermanager.dao.util.HibernateUtil;
import com.jca.transfermanager.model.Transaction;
import com.jca.transfermanager.service.TransactionService;

@Path("/transactions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	private TransactionService service = new 
			TransactionService(new AccountDAOImpl(sessionFactory),new TransactionDAOImpl(sessionFactory));
	
	@POST
	public Response createTransaction(Transaction transaction, @Context UriInfo uriInfo) {
		String transactionId = service.createTransaction(transaction);
		Transaction txCreated = service.getById(transactionId);
		URI uri = uriInfo.getAbsolutePathBuilder().path(transactionId)
				.build();
		return Response.created(uri).entity(txCreated).build();
	}

}
