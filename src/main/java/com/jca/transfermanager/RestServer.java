package com.jca.transfermanager;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class RestServer {

	private static final int PORT = 8080;

	public static void main(String[] args) {

		Server server = new Server(PORT);

		ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

		ctx.setContextPath("/");
		server.setHandler(ctx);

		ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/api/*");
		serHol.setInitOrder(0);
		serHol.setInitParameter("jersey.config.server.provider.packages", "com.jca.transfermanager");
		
        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            Logger.getLogger(RestServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            server.destroy();
        }

	}

}
