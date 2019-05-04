package com.jca.transfermanager.dao.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.jca.transfermanager.model.Account;
import com.jca.transfermanager.model.Transaction;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "org.h2.Driver");
				settings.put(Environment.URL, "jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1;MVCC=TRUE");
				settings.put(Environment.USER, "sa");
				settings.put(Environment.PASS, "");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
				settings.put(Environment.SHOW_SQL, "true");
				settings.put(Environment.POOL_SIZE, "2");
				settings.put(Environment.HBM2DDL_AUTO, "create");
				configuration.setProperties(settings);
				configuration.addAnnotatedClass(Account.class);
				configuration.addAnnotatedClass(Transaction.class);
				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}
