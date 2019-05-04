package com.jca.transfermanager.rest;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.jca.transfermanager.dao.impl.AccountDAOImpl;
import com.jca.transfermanager.dao.util.HibernateUtil;
import com.jca.transfermanager.model.Account;
import com.jca.transfermanager.service.AccountService;

@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

	private AccountService accountSerivce = new AccountService(new AccountDAOImpl(HibernateUtil.getSessionFactory()));
	
	@POST
	public Response createAccount(Account account, @Context UriInfo uriInfo) {
		String accountId = this.accountSerivce.create(account);
		Account createdAccount = this.accountSerivce.getById(accountId);
		URI uri = uriInfo.getAbsolutePathBuilder().path(accountId)
				.build();
		return Response.created(uri).entity(createdAccount).build();
	}
	
	@PUT
	@Path("/{account-id}")
	public Response updateAccount(@PathParam("account-id")String accountId, Account account) {
		this.accountSerivce.update(accountId, account);
		Account accountUpdated = this.accountSerivce.getById(accountId);
		return Response.ok(accountUpdated).build();
	}
	
	@GET
	@Path("/{account-id}")
	public Response getAccount(@PathParam("account-id")String accountId) {
		return Response.ok(this.accountSerivce.getById(accountId)).build();
	}
	
}
